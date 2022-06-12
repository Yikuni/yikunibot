package com.yikuni.robot.client.replier

import com.yikuni.robot.akarin.config.YikuniClientConfig.Msg
import com.yikuni.robot.client.games.TodayLucky
import love.forte.simbot.ID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GameReplier {
    val todayLucky = TodayLucky.getInstance()

    @Autowired
    lateinit var msg:Msg

    fun getLucky(qq: ID): String{
        val lucky = todayLucky.searchLucky(qq)
        return "姐姐大人今天的运气指数是: ${lucky.status}\n${msg.game.luckReply[lucky.status - 1]}"
    }
}