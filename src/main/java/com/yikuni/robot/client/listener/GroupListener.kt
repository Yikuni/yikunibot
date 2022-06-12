package com.yikuni.robot.client.listener

import com.yikuni.robot.akarin.config.YikuniClientConfig.Msg
import com.yikuni.robot.client.games.GroupMessageCache
import com.yikuni.robot.client.image.AmiyaImageTemplate
import com.yikuni.robot.client.image.ImageManager
import com.yikuni.robot.client.replier.GameReplier
import com.yikuni.robot.client.replier.NormalTalkReplier
import com.yikuni.robot.client.replier.QqUserReplier
import com.yikuni.robot.client.replier.TopicReplier
import com.yikuni.robot.common.utils.ListUtil
import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.FilterValue
import love.forte.simboot.annotation.Listener
import love.forte.simboot.filter.MatchType
import love.forte.simbot.ID
import love.forte.simbot.LongID
import love.forte.simbot.definition.Group
import love.forte.simbot.event.GroupMessageEvent
import love.forte.simbot.event.MemberChangedEvent
import love.forte.simbot.event.MemberDecreaseEvent
import love.forte.simbot.event.MemberIncreaseEvent
import love.forte.simbot.message.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GroupListener {
    @Autowired
    lateinit var normalTalkReplier: NormalTalkReplier

    @Autowired
    lateinit var qqUserReplier: QqUserReplier

    @Autowired
    lateinit var restTemplate:RestTemplate

    @Autowired
    lateinit var gameReplier: GameReplier

    @Autowired
    lateinit var topicReplier:TopicReplier

    @Autowired
    lateinit var msg:Msg

    @Autowired
    lateinit var memberIncreaseMessageMap:HashMap<LongID, String>

    @Autowired
    lateinit var memberDecreaseMessageMap:HashMap<LongID, String>

    val imageManager = ImageManager.getInstance()

    @Listener
    @Filter(value = ".", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun normalTalk(event: GroupMessageEvent){

        val content = event.messageContent.plainText
        val strs = content.split('.')
        val qqAccount = event.author().id
        var reply = "awa";

        reply = normalTalkReplier.getReply(strs, qqAccount)

        if(reply == "awa"){
            reply = ListUtil.getRandom(msg.noReply) as String
            event.source().send(reply)
            if (imageManager.hasGroup("default")) {
                imageManager.sendRandom(event, "default")
            }
        }else{
            event.source().send(reply)
        }
    }
    @Listener
    @Filter(value = "=", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun qqUser(event: GroupMessageEvent){
        val content = event.messageContent.plainText
        val strs = content.split('=')
        val qqAccount = event.author().id
        var reply = "awa";
        reply = qqUserReplier.getReply(strs, qqAccount)

        println(reply)
        val result = event.group().send(reply)
        println(result.id)
        println(event.group().previous())
    }

    @Listener
    @Filter(value = "助战", matchType = MatchType.TEXT_EQUALS)
    suspend fun getAmiyaImage(event: GroupMessageEvent){
        val url = event.author().avatar.toString()
        AmiyaImageTemplate().sendImage(event, url)
    }

    @Listener
    @Filter(value = "今日人品", matchType = MatchType.TEXT_EQUALS)
    suspend fun GroupMessageEvent.listen(){
        val reply = gameReplier.getLucky(author().id)

        group().send(At(author().id) + ("\n" + reply).toText())
    }

    @Listener
    suspend fun repeatMessage(event: GroupMessageEvent){
        val text = event.messageContent.plainText
        if(text.startsWith('#') || text.startsWith('.') || text.startsWith('=') || text.equals("今日人品") || text.equals("助战")) return
        val messagePair = com.yikuni.robot.client.util.Pair<ID, ReceivedMessageContent>(event.author().id, event.messageContent)
        val repeatMessage = GroupMessageCache.compareAndAdd(event.group().id, messagePair, event.bot.id)
        if(repeatMessage != null){
            // 如果要回复
            val content = event.messageContent.plainText
            if(content.contains("机器人")) {
                event.group().send(content.replace("机器人", if(event.author().nickname != "") event.author().nickname else event.author().username))
            }else if(content.contains("姬弃人")){
                event.group().send(content.replace("姬弃人", if(event.author().nickname != "") event.author().nickname else event.author().username))
            }else{
                event.group().send(event.messageContent)
            }
            val botMessage = com.yikuni.robot.client.util.Pair<ID, ReceivedMessageContent>(event.bot.id, event.messageContent)
            GroupMessageCache.addMessage(event.group().id, botMessage)
        }
    }

    @Listener
    @Filter(value = "#", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun getTopic(event: GroupMessageEvent){
        val content = event.messageContent.plainText
        val strs = content.split('#')
        val qqAccount = event.author.id
        var reply = "awa";
        reply = topicReplier.getReply(strs, qqAccount)

        if(reply.length > 1500){
            do{
                event.source().send(reply.substring(0, 1500))
                reply = reply.substring(1500)
            }while (reply.length > 1500)
        }else{
            event.source().send(reply)
        }
    }

    /**
     * 入群欢迎
     */
    @Listener
    suspend fun welcome(event: MemberChangedEvent){
        if(event.user().id == event.bot.id) return
        val nickname = event.user().nickname
        val source = event.source() as Group

        if (event is MemberIncreaseEvent){
            if(memberIncreaseMessageMap.contains(source.id)){
                val builder = MessagesBuilder()
                val build = builder.at(event.user().id).append("  " + memberIncreaseMessageMap.get(source.id)).build()
                source.send(build)
            }
        }else if(event is MemberDecreaseEvent){
            // 成员减少
            if(memberDecreaseMessageMap.contains(source.id)){
                val builder = MessagesBuilder()
                val build = builder.at(event.user().id).append("  " + memberDecreaseMessageMap.get(source.id)).build()
                source.send(build)
            }
        }

    }

    @Listener
    @Filter("入群欢迎 {{msg}}")
    suspend fun setWelcome(event: GroupMessageEvent, @FilterValue("msg") msg:String){
        val id = event.group().id
        if(msg != "禁用"){
            if(memberIncreaseMessageMap.contains(id)){
                // 如果有
                memberIncreaseMessageMap.set(id as LongID, msg);
            }else{
                memberIncreaseMessageMap.put(id as LongID, msg);
            }
        }else{
            if(memberIncreaseMessageMap.contains(id)){
                // 如果有
                memberIncreaseMessageMap.remove(id)
            }
        }
        event.group().send("设置成功")
    }

    @Listener
    @Filter("离群提醒 {{msg}}")
    suspend fun setDecrease(event: GroupMessageEvent, @FilterValue("msg") msg:String){
        val id = event.group().id
        if(msg != "禁用"){
            if(memberDecreaseMessageMap.contains(id)){
                // 如果有
                memberDecreaseMessageMap.set(id as LongID, msg);
            }else{
                memberDecreaseMessageMap.put(id as LongID, msg);
            }
        }else{
            if(memberDecreaseMessageMap.contains(id)){
                // 如果有
                memberDecreaseMessageMap.remove(id)
            }
        }
        event.group().send("设置成功")
    }

    @Listener
    suspend fun commonListener(event: GroupMessageEvent) {
        val msg = event.messageContent.plainText
        if (imageManager.hasGroup(msg)) {
            imageManager.sendRandom(event, msg)
        }
    }

}