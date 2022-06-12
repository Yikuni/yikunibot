package com.yikuni.robot.client.listener

import com.yikuni.robot.client.games.GroupMessageCache
import com.yikuni.robot.client.replier.GameReplier
import com.yikuni.robot.client.replier.NormalTalkReplier
import com.yikuni.robot.client.replier.QqUserReplier
import com.yikuni.robot.client.replier.TopicReplier
import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.Listener
import love.forte.simboot.filter.MatchType
import love.forte.simbot.ID
import love.forte.simbot.event.ChannelMessageEvent
import love.forte.simbot.message.At
import love.forte.simbot.message.ReceivedMessageContent
import love.forte.simbot.message.plus
import love.forte.simbot.message.toText
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ChannelListener {
    @Autowired
    lateinit var normalTalkReplier: NormalTalkReplier

    @Autowired
    lateinit var qqUserReplier: QqUserReplier

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var gameReplier: GameReplier

    @Autowired
    lateinit var topicReplier: TopicReplier

    @Listener
    @Filter(value = ".", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun normalTalk(event: ChannelMessageEvent){
        println("awa")
        val content = event.messageContent.plainText
        val strs = content.split('.')
        val qqAccount = event.author().id
        var reply = "awa";

        reply = normalTalkReplier.getReply(strs, qqAccount)

        println(event.messageContent.plainText)
        event.channel().send(reply)
    }
    @Listener
    @Filter(value = "=", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun qqUser(event: ChannelMessageEvent){
        val content = event.messageContent.plainText
        val strs = content.split('=')
        val qqAccount = event.author().id
        var reply = "awa";
        reply = qqUserReplier.getReply(strs, qqAccount)

        println(reply)
        val result = event.channel().send(reply)
    }

/*    @Listener
    @Filter(value = "助战", matchType = MatchType.TEXT_EQUALS)
    suspend fun getAmiyaImage(event: ChannelMessageEvent){
        val url = event.author().avatar.toString()
        AmiyaImageTemplate().sendImage(event, url)
    }*/

    @Listener
    @Filter(value = "今日人品", matchType = MatchType.TEXT_EQUALS)
    suspend fun ChannelMessageEvent.listen(){
        val reply = gameReplier.getLucky(author().id)

        channel().send(At(author().id) + ("\n" + reply).toText())
    }

    @Listener
    suspend fun repeatMessage(event: ChannelMessageEvent){
        val text = event.messageContent.plainText
        if(text.startsWith('#') || text.startsWith('.') || text.startsWith('=') || text.equals("今日人品") || text.equals("助战")) return
        val messagePair = com.yikuni.robot.client.util.Pair<ID, ReceivedMessageContent>(event.author().id, event.messageContent)
        val repeatMessage = GroupMessageCache.compareAndAdd(event.channel().id, messagePair, event.bot.id)
        if(repeatMessage != null){
            // 如果要回复
            val content = event.messageContent.plainText
            if(content.contains("机器人")) {
                event.channel().send(content.replace("机器人", if(event.author().nickname != "") event.author().nickname else event.author().username))
            }else if(content.contains("姬弃人")){
                event.channel().send(content.replace("姬弃人", if(event.author().nickname != "") event.author().nickname else event.author().username))
            }else{
                event.channel().send(event.messageContent)
            }
            val botMessage = com.yikuni.robot.client.util.Pair<ID, ReceivedMessageContent>(event.bot.id, event.messageContent)
            GroupMessageCache.addMessage(event.channel().id, botMessage)
        }
    }

    @Listener
    @Filter(value = "#", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun getTopic(event: ChannelMessageEvent){
        val content = event.messageContent.plainText
        val strs = content.split('#')
        val qqAccount = event.author().id
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
}