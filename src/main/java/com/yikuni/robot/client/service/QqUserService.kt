package com.yikuni.robot.client.service

import com.yikuni.robot.common.domain.QqUser
import love.forte.simbot.ID

interface QqUserService {
    fun changeUserState(qqUserAccount:Long, changer:ID, status:Int): String
    fun selectQqUserIntimacy(qqUserAccount: ID): String
    fun selectQqUserIntimacy(qqUserAccount: Long): String
    fun selectQqUser(qqUserAccount: Long): String
}