package com.yikuni.robot.client.service.impl

import com.alibaba.fastjson.JSONObject
import com.yikuni.robot.akarin.client.YikuniHttpClient
import com.yikuni.robot.akarin.config.YikuniClientConfig.Msg
import com.yikuni.robot.client.service.QqUserService
import com.yikuni.robot.common.domain.CommonResult
import com.yikuni.robot.common.domain.QqUser
import com.yikuni.robot.common.domain.YikuniBot
import love.forte.simbot.ID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class QqUserServiceImpl : QqUserService {

    @Autowired
    lateinit var yikuniBot: YikuniBot

    @Autowired
    lateinit var client: YikuniHttpClient

    @Value("\${yikuni.server}")
    lateinit var serverUrl: String


    @Autowired
    lateinit var msg: Msg

    override fun changeUserState(qqUserAccount: Long, changer: ID, status: Int):String {
        val url = serverUrl + "/qquser/changeQqUserStatus/${qqUserAccount}/${yikuniBot.account}/${changer}/${status}"
        val result = client.getResult(url)
        return try{
            when(result.status){
                CommonResult.OK ->{
                    msg.permission
                }
                CommonResult.NO_PERMISSION ->{
                    msg.noPermission
                }
                CommonResult.SERVLET_BUSY ->{
                    msg.busy
                }
                CommonResult.SERVLET_ERROR ->{
                    msg.error
                }
                else ->{
                    msg.clientError
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            msg.clientError
        }
    }

    override fun selectQqUserIntimacy(qqUserAccount: ID): String {
        val url = serverUrl + "/qquser/selectQqUserByQqUserAccountAccurate/${qqUserAccount}/${yikuniBot.account}"
        println(url)
        val result = client.getResult(url)
        var reply = "awa";
        try{
            when(result.status){
                CommonResult.OK ->{
                    val jsonObject:JSONObject = result.result as JSONObject
                    val qqUser: QqUser = jsonObject.toJavaObject(QqUser::class.java)
                    reply = msg.intimacy.prefix + qqUser.intimacy + msg.intimacy.suffix;
                }
                CommonResult.USER_NOT_FOUND ->{
                    reply = msg.noUser
                }
                CommonResult.SERVLET_BUSY ->{
                    reply = msg.busy
                }
                CommonResult.SERVLET_ERROR ->{
                    reply = msg.error
                }
                else ->{
                    reply = msg.error
                }

            }
        }catch (e:Exception){
            e.printStackTrace()
            reply = msg.clientError
        }
        return reply
    }
    override fun selectQqUserIntimacy(qqUserAccount: Long): String {
        val url = serverUrl + "/qquser/selectQqUserByQqUserAccountAccurate/${qqUserAccount}/${yikuniBot.account}"
        val result = client.getResult(url)
        var reply = "awa";
        try{
            when(result.status){
                CommonResult.OK ->{
                    val jsonObject:JSONObject = result.result as JSONObject
                    val qqUser: QqUser = jsonObject.toJavaObject(QqUser::class.java)
                    reply = msg.intimacy.prefix + qqUser.intimacy + msg.intimacy.suffix;
                }
                CommonResult.USER_NOT_FOUND ->{
                    reply = msg.noUser
                }
                CommonResult.SERVLET_BUSY ->{
                    reply = msg.busy
                }
                CommonResult.SERVLET_ERROR ->{
                    reply = msg.error
                }
                else ->{
                    reply = msg.error
                }

            }
        }catch (e:Exception){
            e.printStackTrace()
            reply = msg.clientError
        }
        return reply
    }

    override fun selectQqUser(qqUserAccount: Long): String {
        val url = serverUrl + "/qquser/selectQqUserByQqUserAccountAccurate/${qqUserAccount}/${yikuniBot.account}"
        val result = client.getResult(url)
        var reply = "awa";
        try{
            when(result.status){
                CommonResult.OK ->{
                    val jsonObject:JSONObject = result.result as JSONObject
                    val qqUser: QqUser = jsonObject.toJavaObject(QqUser::class.java)
                    val statusStr = when(qqUser.status){
                        QqUser.BANNED ->{
                            msg.status.banned
                        }
                        QqUser.GUEST ->{
                            msg.status.guest
                        }
                        QqUser.INSERTABLE ->{
                            msg.status.insertable
                        }
                        QqUser.UPDATABLE ->{
                            msg.status.updatable
                        }
                        QqUser.OP ->{
                            msg.status.op
                        }
                        QqUser.OWNER ->{
                            msg.status.owner
                        }
                        else ->{
                            "未知权限组"
                        }
                    }
                    reply = "用户查询结果:\n用户等级:${statusStr}\n亲密度:${qqUser.intimacy}"
                }

                CommonResult.USER_NOT_FOUND ->{
                    reply = msg.noUser
                }
                CommonResult.SERVLET_BUSY ->{
                    reply = msg.busy
                }
                CommonResult.SERVLET_ERROR ->{
                    reply = msg.error
                }
                else ->{
                    reply = msg.error
                }

            }
        }catch (e:Exception){
            e.printStackTrace()
            reply = msg.clientError
        }
        return reply
    }

}