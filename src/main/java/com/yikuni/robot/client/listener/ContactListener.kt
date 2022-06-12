package com.yikuni.robot.client.listener

import com.yikuni.robot.akarin.config.YikuniClientConfig
import com.yikuni.robot.client.image.AmiyaImageTemplate
import com.yikuni.robot.client.image.ImageManager
import com.yikuni.robot.client.replier.GameReplier
import com.yikuni.robot.client.replier.NormalTalkReplier
import com.yikuni.robot.client.replier.QqUserReplier
import com.yikuni.robot.client.replier.TopicReplier
import com.yikuni.robot.common.utils.ListUtil
import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.Listener
import love.forte.simboot.filter.MatchType
import love.forte.simbot.event.ContactMessageEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ContactListener {
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

    @Autowired
    lateinit var msg: YikuniClientConfig.Msg

    val imageManager = ImageManager.getInstance()

    @Listener
    @Filter(value = ".", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun normalTalk(event: ContactMessageEvent){
        println(event.messageContent.plainText)
        val content = event.messageContent.plainText
        val strs = content.split('.')
        val qqAccount = event.user().id
        var reply = "awa";

        reply = normalTalkReplier.getReply(strs, qqAccount)

        if(reply == "awa"){
            reply = ListUtil.getRandom(msg.noReply) as String
            event.user().send(reply)

            if (imageManager.hasGroup("default")) {
                imageManager.sendRandom(event, "default")
            }
        }else{
            event.source().send(reply)
        }

    }

    @Listener
    @Filter(value = "=", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun qqUser(event: ContactMessageEvent){
        val content = event.messageContent.plainText
        val strs = content.split('=')
        val qqAccount = event.user().id
        var reply = "awa";
        reply = qqUserReplier.getReply(strs, qqAccount)

        event.user().send(reply)

    }

    @Listener
    @Filter(value = "助战", matchType = MatchType.TEXT_EQUALS)
    suspend fun getAmiyaImage(event: ContactMessageEvent){
        val url = event.user().avatar.toString()
        AmiyaImageTemplate().sendImage(event, url)

    }

    @Listener
    @Filter(value = "今日人品", matchType = MatchType.TEXT_EQUALS)
    suspend fun getTodayLucky(event: ContactMessageEvent){
        val reply = gameReplier.getLucky(event.user().id)
        event.user().send(reply)
    }

    @Listener
    @Filter(value = "#", matchType = MatchType.TEXT_STARTS_WITH)
    suspend fun getTopic(event: ContactMessageEvent){
        val content = event.messageContent.plainText
        val strs = content.split('#')
        val qqAccount = event.user().id
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

    @Listener
    suspend fun commonListener(event: ContactMessageEvent) {
        val msg = event.messageContent.plainText
        if (imageManager.hasGroup(msg)) {
            imageManager.sendRandom(event, msg)
        }
    }

}