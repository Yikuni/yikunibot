package com.yikuni.robot.client.service.impl

import com.alibaba.fastjson.JSONObject
import com.yikuni.robot.akarin.client.YikuniHttpClient
import com.yikuni.robot.akarin.config.YikuniClientConfig
import com.yikuni.robot.client.service.TopicService
import com.yikuni.robot.client.util.getTopicString
import com.yikuni.robot.common.domain.CommonResult
import com.yikuni.robot.common.domain.TopicAndMessage
import com.yikuni.robot.common.domain.TopicMessage
import com.yikuni.robot.common.domain.YikuniBot
import love.forte.simbot.ID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TopicServiceImpl : TopicService {
    @Autowired
    lateinit var yikuniBot: YikuniBot

    @Autowired
    lateinit var client: YikuniHttpClient

    @Value("\${yikuni.server}")
    lateinit var serverUrl: String

    @Autowired
    lateinit var msg: YikuniClientConfig.Msg

    override fun getMessage(qq: ID, content: String): String {
        val url = serverUrl + "/topic/selectTopic/${yikuniBot.account}/${qq.toString()}/${content}"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val jsonObject: JSONObject = result.result as JSONObject
                val message:TopicMessage = jsonObject.toJavaObject(TopicMessage::class.java)
                reply = message.reply
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun addReply(qq: ID, content: String, reply: String): String {
        val url = serverUrl + "/topic/insertTopicByContent/${yikuniBot.account}/${qq.toString()}/${content}/${reply}"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.insert1 else msg.topic.insert0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            CommonResult.TOPIC_MESSAGE_REPEATED->{
                reply = msg.topic.repeatedMessage
            }
            CommonResult.BAD_INPUT->{
                reply = msg.forbidden
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun deleteMessage(qq: ID, id: Int): String {
        val url = serverUrl + "/topic/changeTopicMessageStatus/${yikuniBot.account}/${qq.toString()}/${id}/false"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.delete1 else msg.topic.delete0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun recoverMessage(qq: ID, id: Int): String {
        val url = serverUrl + "/topic/changeTopicMessageStatus/${yikuniBot.account}/${qq.toString()}/${id}/true"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.recover1 else msg.topic.recover0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun changeMessage(qq: ID, id: Int, content: String): String {
        val url = serverUrl + "/topic/changeTopicMessageReply/${yikuniBot.account}/${qq.toString()}/${id}/${content}"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.recover1 else msg.topic.recover0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun searchTopic(qq: ID, content: String): String {
        val url = serverUrl + "/topic/searchTopic/${yikuniBot.account}/${qq.toString()}/${content}"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val jsonObject: JSONObject = result.result as JSONObject
                val topicAndMessage = jsonObject.toJavaObject(TopicAndMessage::class.java)
                reply = getTopicString(topicAndMessage)
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun deleteTopic(qq: ID, id: Int): String {
        val url = serverUrl + "/topic/changeTopicMessageStatus/${yikuniBot.account}/${qq.toString()}/${id}/false"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.delete1 else msg.topic.delete0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun recoverTopic(qq: ID, id: Int): String {
        val url = serverUrl + "/topic/changeTopicMessageStatus/${yikuniBot.account}/${qq.toString()}/${id}/true"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.recover1 else msg.topic.recover0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun changeTopic(qq: ID, id: Int, content: String): String {
        val url = serverUrl + "/topic/updateTopicName/${yikuniBot.account}/${qq.toString()}/${id}/${content}"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.update1 else msg.topic.update0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.NO_TOPIC_FOUND->{
                reply = msg.topic.noTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }

    override fun createTopic(qq: ID, content: String): String {
        val url = serverUrl + "/topic/createTopic/${yikuniBot.account}/${qq.toString()}/${content}"
        val result = client.getResult(url)
        var reply = "awa"
        when(result.status){
            CommonResult.OK->{
                val boo:Boolean = result.result as Boolean
                reply = if(boo) msg.topic.create1 else msg.topic.create0
            }
            CommonResult.NO_PERMISSION->{
                reply = msg.noPermission
            }
            CommonResult.SERVLET_ERROR->{
                reply = msg.error
            }
            CommonResult.SERVLET_BUSY->{
                reply = msg.busy
            }
            CommonResult.TOPIC_REPEATED->{
                reply = msg.topic.repeatedTopic
            }
            else->{
                reply = "出现未知错误"
            }
        }
        return reply
    }
}