package com.yikuni.robot.client.service

import love.forte.simbot.ID
import org.springframework.stereotype.Component

@Component
interface TopicService {
    fun getMessage(qq:ID, content:String):String
    fun addReply(qq: ID, content: String, reply:String):String
    fun deleteMessage(qq: ID, id:Int):String
    fun recoverMessage(qq: ID, id:Int):String
    fun changeMessage(qq: ID, id: Int, content: String):String
    fun searchTopic(qq: ID, content: String):String
    fun deleteTopic(qq: ID, id:Int):String
    fun recoverTopic(qq: ID, id: Int):String
    fun changeTopic(qq: ID, id: Int, content: String):String
    fun createTopic(qq: ID, content: String):String
}