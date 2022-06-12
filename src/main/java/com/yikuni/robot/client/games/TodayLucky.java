package com.yikuni.robot.client.games;

import com.yikuni.robot.client.util.DateUtil;
import love.forte.simbot.ID;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TodayLucky {
    private final Map<Long, Lucky> luckyMap = new HashMap<>();
    private final Random random = new Random();
    public static final TodayLucky todayLucky = new TodayLucky();

    private TodayLucky(){

    }

    public static TodayLucky getInstance(){
        return todayLucky;
    }

    public Lucky addLucky(Long qq){
        int luckyDegree = random.nextInt(10) + 1;
        Date date = new Date();
        Lucky lucky = new Lucky(luckyDegree, date);
        luckyMap.put(qq, lucky);
        return lucky;
    }

    public Lucky searchLucky(ID qqId){
        Long qq = Long.parseLong(qqId.toString());
        Lucky lucky = luckyMap.get(qq);
        if(lucky != null){
            // 如果有
            if(DateUtil.isSameDay(new Date(), lucky.getDate())){
                // 如果今天已经有记录了
                return lucky;
            }else{
                // 如果记录过时了
                luckyMap.remove(qq);
                lucky = addLucky(qq);
                return lucky;
            }
        }else{
            // 如果没有, 创建
            lucky = addLucky(qq);
            return lucky;
        }
    }
}
