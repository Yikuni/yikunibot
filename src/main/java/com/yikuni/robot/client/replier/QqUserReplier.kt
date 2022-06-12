package com.yikuni.robot.client.replier

import com.yikuni.robot.client.service.QqUserService
import love.forte.simbot.ID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QqUserReplier {
    @Autowired
    lateinit var service: QqUserService



    fun getReply(strs:List<String>, qqAccount:ID): String{
        var reply = "awa"
        when(strs.size){
            2 ->{
                // 如果是查询自己的亲密度, 或查询某个用户
                if(strs[1] == "亲密度"){
                    // 查询自己的亲密度
                    reply = service.selectQqUserIntimacy(qqAccount)
                }else{
                    // 如果是查询某个用户
                    reply = try{
                        service.selectQqUser(strs[1].toLong())
                    }catch (e:Exception){
                        // 如果输入的不是qq号
                        "格式错误, 正确格式: =qq号"
                    }
                }
            }
            3 ->{
                // 如果是修改权限, 或查询某人的亲密度, 或看亲密度排行
                if(strs[1] == "亲密度"){
                    if(strs[2] == "排行"){
                        // 查看亲密度排行
                        // TODO
                        reply = "排行功能正在开发中"
                    }else{
                        reply = try{
                            service.selectQqUserIntimacy(strs[2].toLong())
                        }catch (e:Exception){
                            // 如果输入的不能转换为long
                            "格式错误, 正确格式: =亲密度=qq号"
                        }
                    }
                }else{
                    // 如果是修改权限
                    reply = try{
                        service.changeUserState(strs[1].toLong(), qqAccount, strs[2].toInt())
                    }catch (e: Exception){
                        "格式错误, 正确格式: =qq号=权限"
                    }
                }
            }
            else ->{
                // 如果格式错误
                reply = "格式错误"
            }
        }
        return reply
    }
}