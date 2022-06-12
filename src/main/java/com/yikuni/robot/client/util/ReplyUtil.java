package com.yikuni.robot.client.util;

import com.yikuni.robot.client.image.ImageManager;

public class ReplyUtil {
    public static boolean isNormalFunction(String msg){
        boolean result = msg.startsWith(".") || msg.startsWith("=") || msg.startsWith("#")
                || msg.equals("助战") || msg.equals("今日人品");
        if (result) return result;
        ImageManager manager = ImageManager.getInstance();
        if(manager.hasGroup(msg)) return true;
        return result;
    }

    public static boolean isRemind(String msg){
        return !isNormalFunction(msg) && msg.contains("我")
                && ((msg.contains("分钟") || msg.contains("小时") || msg.contains("天")) && msg.contains("后") || msg.contains("点"))
                || msg.contains("明天") || msg.contains("后天");
    }

    public static int getDigit(String msg){
        int index = msg.length() - 1;
        char[] chars = msg.toCharArray();
        while(index > -1 && chars[index] >= '0' && chars[index] <= '9'){
            index --;
        }
        if(index == msg.length() - 1){
            return 0;
        }else{
            return Integer.parseInt(msg.substring(index + 1));
        }
    }

    public static int getDigitAfter(String msg){
        int index = 0;
        char[] chars = msg.toCharArray();
        while(index < msg.length() && chars[index] >= '0' && chars[index] <= '9'){
            index ++;
        }
        if(index == 0){
            return 0;
        }else{
            return Integer.parseInt(msg.substring(0, index));
        }
    }
}
