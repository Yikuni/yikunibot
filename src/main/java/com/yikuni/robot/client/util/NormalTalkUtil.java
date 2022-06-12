package com.yikuni.robot.client.util;

import com.yikuni.robot.common.domain.NormalTalk;

import java.util.List;

public class NormalTalkUtil {
    public static String getNormalTalkString(List<NormalTalk> normalTalkList){
        StringBuilder builder = new StringBuilder();
        builder.append("查询结果共");
        builder.append(normalTalkList.size());
        builder.append("条:");
        for(NormalTalk normalTalk: normalTalkList){
            builder.append("\n================\n");
            builder.append("id: ").append(normalTalk.getNtId());
            builder.append("\n消息内容: ").append(normalTalk.getNtContent());
            builder.append("\n消息回复: ").append(normalTalk.getNtReply());
            builder.append("\n消息使用次数: ").append(normalTalk.getNtCount());
            builder.append("\n消息更改日期: ").append(normalTalk.getNtDate());
        }
        return builder.toString();
    }
}
