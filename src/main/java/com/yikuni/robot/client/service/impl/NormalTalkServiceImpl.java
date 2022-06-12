package com.yikuni.robot.client.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yikuni.robot.akarin.client.YikuniHttpClient;
import com.yikuni.robot.akarin.config.YikuniClientConfig;
import com.yikuni.robot.client.service.NormalTalkService;
import com.yikuni.robot.client.util.NormalTalkUtil;
import com.yikuni.robot.common.domain.CommonResult;
import com.yikuni.robot.common.domain.NormalTalk;
import com.yikuni.robot.common.domain.YikuniBot;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NormalTalkServiceImpl implements NormalTalkService {

    @Autowired
    private YikuniBot yikuniBot;

    @Autowired
    private YikuniHttpClient client;

    @Value("${yikuni.server}")
    private String serverUrl;

    @Autowired
    private YikuniClientConfig.Msg msg;

    @Override
    public String getNormalTalk(String content, ID qqAccount) {
        String url = serverUrl + "/normalTalk/select/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + content;
        CommonResult result = client.getResult(url);
        if(result != null){
            // 如果成功拿到result
            if(result.getStatus() == CommonResult.OK){
                // 如果成功找到
                JSONObject jsonObject = (JSONObject) result.getResult();
                NormalTalk normalTalk = jsonObject.toJavaObject(NormalTalk.class);
                return normalTalk.getNtReply();
            }else if(result.getStatus() == CommonResult.NT_NOT_FIND){
                // 如果没有找到
                return "awa";
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }
        }
        return msg.getClientError();
    }

    @Override
    public String deleteNormalTalk(int id, ID qqAccount) {
        String url = serverUrl + "/normalTalk/delete/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + id;
        CommonResult result = client.getResult(url);
        if(result != null){
            if(result.getStatus() == CommonResult.OK){
                // 如果删除成功
                return msg.getDeleteOk();
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }else if(result.getStatus() == CommonResult.ID_NOT_CORRECT){
                return msg.getDeleteFail();
            }
        }
        return msg.getClientError();
    }

    @Override
    public String recoverNormalTalk(int id, ID qqAccount) {
        String url = serverUrl + "/normalTalk/recover/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + id;
        CommonResult result = client.getResult(url);
        if(result != null){
            if(result.getStatus() == CommonResult.OK){
                // 如果恢复成功
                return msg.getRecoverOk();
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }else if(result.getStatus() == CommonResult.ID_NOT_CORRECT){
                return msg.getRecoverFail();
            }
        }
        return msg.getClientError();
    }


    @Override
    public String updateNormalTalk(int id, String content, ID qqAccount) {
        String url = serverUrl + "/normalTalk/update/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + id + "/" + content;

        CommonResult result = client.getResult(url);
        if(result != null){
            if(result.getStatus() == CommonResult.OK){
                // 如果修改成功
                return msg.getUpdateOk();
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }else if(result.getStatus() == CommonResult.ID_NOT_CORRECT){
                // 如果没有权限修改
                return msg.getUpdateFail();
            }else if(result.getStatus() == CommonResult.BAD_INPUT){
                // 如果修改不合法
                return msg.getForbidden();
            }
        }
        return msg.getClientError();

    }

    @Override
    public String searchByQq(Long ownerQq, ID qqAccount) {
        String url = serverUrl + "/normalTalk/searchByQq/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + ownerQq;
        CommonResult result = client.getResult(url);
        if(result != null){
            if(result.getStatus() == CommonResult.OK){
                // 如果查询成功
                JSONArray jsonArray = (JSONArray) result.getResult();
                List<NormalTalk> normalTalks = jsonArray.toJavaList(NormalTalk.class);
                return NormalTalkUtil.getNormalTalkString(normalTalks);
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }
        }
        return msg.getClientError();
    }

    @Override
    public String searchByContent(String content, ID qqAccount) {
        String url = serverUrl + "/normalTalk/searchByContent/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + content;
        CommonResult result = client.getResult(url);
        if(result != null){
            if(result.getStatus() == CommonResult.OK){
                // 如果查询成功
                JSONArray jsonArray = (JSONArray) result.getResult();
                List<NormalTalk> normalTalks = jsonArray.toJavaList(NormalTalk.class);
                return NormalTalkUtil.getNormalTalkString(normalTalks);
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }else if(result.getStatus() == CommonResult.NT_NOT_FIND){
                return msg.getSearch();
            }
        }
        return msg.getClientError();
    }

    @Override
    public String searchByQqAndContent(String content, Long ownerQq, ID qqAccount) {
        return null;
    }

    @Override
    public String insertNormalTalk(String content, String reply, ID qqAccount) {
        String url = serverUrl + "/normalTalk/insert/" + yikuniBot.getAccount() + "/" + qqAccount + "/" + content + "/" + reply;
        CommonResult result = client.getResult(url);
        if(result != null){
            if(result.getStatus() == CommonResult.OK){
                // 如果插入成功
                return msg.getInsert();
            }else if(result.getStatus() == CommonResult.BAD_INPUT){
                return msg.getForbidden();
            }else if(result.getStatus() == CommonResult.NO_PERMISSION){
                return msg.getNoPermission();
            }else if(result.getStatus() == CommonResult.SERVLET_BUSY){
                return msg.getBusy();
            }else if(result.getStatus() == CommonResult.SERVLET_ERROR){
                return msg.getError();
            }else if(result.getStatus() == CommonResult.REPEATED_NT_CONTENT){
                return msg.getRepeated();
            }
        }
        return msg.getClientError();
    }
}
