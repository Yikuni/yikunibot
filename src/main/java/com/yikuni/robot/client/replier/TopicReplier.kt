package com.yikuni.robot.client.replier

import com.yikuni.robot.client.service.TopicService
import love.forte.simbot.ID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TopicReplier {
    @Autowired
    lateinit var topicService: TopicService

    fun getReply(strs:List<String>, qq:ID):String{
        var reply = "awa"
        if(strs.size == 2){
            reply = topicService.getMessage(qq, strs[1])
        }else if(strs.size == 3){
            when(strs[1]){
                "删除"->{
                    try{
                        val id = strs[2].toInt()
                        reply = topicService.deleteMessage(qq, id)
                    }catch (e:Exception){
                        reply = "格式错误, 请确保id可以被转化为整数, 正确格式: #删除#id"
                    }
                }
                "恢复"->{
                    try{
                        val id = strs[2].toInt()
                        reply = topicService.recoverMessage(qq, id)
                    }catch (e:Exception){
                        reply = "格式错误, 请确保id可以被转化为整数, 正确格式: #恢复#id"
                    }
                }
                "删除topic"->{
                    try{
                        val id = strs[2].toInt()
                        reply = topicService.deleteTopic(qq, id)
                    }catch (e:Exception){
                        reply = "格式错误, 请确保id可以被转化为整数, 正确格式: #删除topic#id"
                    }
                }
                "恢复topic"->{
                    try{
                        val id = strs[2].toInt()
                        reply = topicService.recoverTopic(qq, id)
                    }catch (e:Exception){
                        reply = "格式错误, 请确保id可以被转化为整数, 正确格式: #恢复topic#id"
                    }
                }
                "查询"->{
                    reply = topicService.searchTopic(qq, strs[2])
                }
                "创建词条"->{
                    reply = topicService.createTopic(qq, strs[2])
                }
                else->{
                    // 增加reply
                    reply = topicService.addReply(qq, strs[1], strs[2])
                }
            }
        }else if(strs.size == 4){
            when(strs[1]){
                "修改"->{
                    try{
                        val id = strs[2].toInt()
                        reply = topicService.changeMessage(qq, id, strs[3])
                    }catch (e:Exception){
                        reply = "格式错误, 请确保id可以被转化为整数, 正确格式: #修改#id#新内容"
                    }
                }
                "修改topic"->{
                    try{
                        val id = strs[2].toInt()
                        reply = topicService.changeTopic(qq, id, strs[3])
                    }catch (e:Exception){
                        reply = "格式错误, 请确保id可以被转化为整数, 正确格式: #修改topic#id#新内容"
                    }
                }
                else->{
                    reply = "格式错误"
                }
            }
        }else{
            reply = "格式错误"
        }
        return reply
    }
}