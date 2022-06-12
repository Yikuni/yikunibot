package com.yikuni.robot.client.runner;

import com.alibaba.fastjson.JSONObject;
import com.yikuni.robot.client.config.DataConfig;
import com.yikuni.robot.client.util.FileUtil;
import love.forte.simbot.ID;
import love.forte.simbot.LongID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.HashMap;

@Component
public class DestroyRunner{

    @Autowired
    public HashMap<LongID, String> memberDecreaseMessageMap;

    @Autowired
    public HashMap<LongID, String> memberIncreaseMessageMap;

    @PreDestroy
    public void save(){
        saveGroupMemberMessage();

    }

    public void saveGroupMemberMessage(){
        String url = DataConfig.getConfig().getConfUrl();
        FileUtil.writeJSONString(new File(url, "/memberIncreaseMessageMap.json"), memberIncreaseMessageMap);
        FileUtil.writeJSONString(new File(url, "/memberDecreaseMessageMap.json"), memberDecreaseMessageMap);
    }
}
