@file:JvmName("ListUtil")

package com.yikuni.robot.client.util

import com.yikuni.robot.common.domain.NormalTalk
import com.yikuni.robot.common.domain.TopicAndMessage
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

fun getTopicString(topicAndMessage: TopicAndMessage):String{
    val format = SimpleDateFormat("yyyy-MM-dd")
    var result = "查询结果共${topicAndMessage.topicMessageList.size}条:"
    val topic = topicAndMessage.topic
    val messages = topicAndMessage.topicMessageList
    result += "\n===================\n"
    result += "词条id: ${topic.id}"
    result += "词条创建人qq号: ${topic.qq}\n"
    result += "词条创建日期: ${format.format(topic.date)}"
    if(!topic.status) result += "词条已被删除\n"
    for(message in messages){
        result += "\n=====================\n"
        result += "消息id: ${message.id}\n消息创建人qq: ${message.qq}\n消息内容: ${message.reply}\n消息创建日期: ${format.format(message.date)}"
        if(!message.status) result += "消息已被删除"
    }
    return result
}
