package com.yikuni.robot.client.listener

import love.forte.simboot.annotation.Listener
import love.forte.simbot.component.mirai.event.MiraiFriendNudgeEvent
import love.forte.simbot.event.Event
import love.forte.simbot.event.FriendAddRequestEvent
import love.forte.simbot.event.GroupJoinRequestEvent
import love.forte.simbot.event.GuildJoinRequestEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AutoAccept {

    val log:Logger = LoggerFactory.getLogger(AutoAccept::class.java)

    @Listener
    suspend fun addFriend(event: FriendAddRequestEvent){
        log.info("自动添加好友: ${event.user().id} 好友昵称: ${event.user().username}")
        event.accept()
    }
    @Listener
    suspend fun addGroup(event: GroupJoinRequestEvent){
        log.info("自动添加群聊: ${event.group().id} 群聊昵称: ${event.group().name}")
        event.accept()
    }
    @Listener
    suspend fun addChannel(event:GuildJoinRequestEvent){
        log.info("自动加入频道: ${event.guild().name} | ${event.guild().id}")
        event.accept()
    }



}