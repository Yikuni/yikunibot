package com.yikuni.robot.client.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yikuni.robot.client.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.ID;
import love.forte.simbot.LongID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@Configuration
@Slf4j
public class GroupConfiguration {

    @Bean
    public HashMap<LongID, String> memberIncreaseMessageMap(){
        String url = DataConfig.getConfig().getConfUrl() + "/memberIncreaseMessageMap.json";
        File file = new File(url);
        if(file.exists()){
            String s = FileUtil.readFileToString(file);
            HashMap<LongID, String> map = JSONObject.parseObject(s, new TypeReference<HashMap<LongID, String>>(){});
            log.info("Starting Loading Group Welcome Messages");
            map.forEach((k, v) ->{
                log.info("Loaded Group Welcome Message: " + k.getNumber() + " | " + v);
            });
            log.info("Loaded All Group Welcome Messages, total num is: " + map.size());
            return map;
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    @Bean
    public HashMap<LongID, String> memberDecreaseMessageMap(){
        String url = DataConfig.getConfig().getConfUrl() + "/memberDecreaseMessageMap.json";
        File file = new File(url);
        if(file.exists()){
            String s = FileUtil.readFileToString(file);
            HashMap<LongID, String> map = JSONObject.parseObject(s, new TypeReference<HashMap<LongID, String>>(){});
            log.info("Starting Loading Group Leave Messages");
            map.forEach((k, v) ->{
                log.info("Loaded Group Leave Message: " + k.getNumber() + " | " + v);
            });
            log.info("Loaded All Group Leave Messages, total num is: " + map.size());
            return map;
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }
}
