package com.yikuni.robot.client.replier

import com.yikuni.robot.client.service.NormalTalkService
import love.forte.simbot.ID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NormalTalkReplier {
    @Autowired
    lateinit var normalTalkService: NormalTalkService

    fun getReply(strs:List<String>, qqAccount:ID): String{
        var reply = "awa";
        if(strs.size == 2){
            // 如果是普通的对话
            reply = normalTalkService.getNormalTalk(strs.get(1), qqAccount)

        }else if(strs.size == 3) {
            // 如果是插入或查询或删除等
            when (strs[1]) {
                "查询" -> {
                    reply = try {
                        val ownerQq = strs[2].toLong()
                        normalTalkService.searchByQq(ownerQq, qqAccount)
                    } catch (e: Exception) {
                        // 不能被转换, 那就是通过内容查询
                        normalTalkService.searchByContent(strs[2], qqAccount)
                    }

                }
                "删除" -> {
                    reply = try {
                        val id = strs[2].toInt()
                        normalTalkService.deleteNormalTalk(id, qqAccount)
                    } catch (e: Exception) {
                        "格式错误或无法连接服务器"
                    }
                }
                "恢复" -> {
                    reply = try {
                        val id = strs[2].toInt()
                        normalTalkService.recoverNormalTalk(id, qqAccount)
                    } catch (e: Exception) {
                        "格式错误或无法连接服务器"
                    }
                }
                else -> {
                    // 插入
                    reply = normalTalkService.insertNormalTalk(strs[1], strs[2], qqAccount)
                }
            }
        }else if(strs.size == 4){
            if("修改" == strs[1]){
                // 如果是修改
                reply = try{
                    val id = strs[2].toInt()
                    normalTalkService.updateNormalTalk(id, strs[3], qqAccount)
                }catch (e: Exception){
                    e.printStackTrace()
                    // 如果id无法转换为int
                    "格式错误或无法连接服务器"
                }
            }

        }else{
            // 如果格式不正确
            reply = "格式不正确, 请检查格式"
        }
        return reply
    }

}