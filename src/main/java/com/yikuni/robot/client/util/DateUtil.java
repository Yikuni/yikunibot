package com.yikuni.robot.client.util;

import com.yikuni.robot.client.reminder.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static boolean isSameDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if(cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static Reminder readTime(String msg){
        Date date = null;
        String remindMsg = null;
        if(ReplyUtil.isRemind(msg)){
            if(msg.contains("分钟")){
                // 如果是几分钟后提醒
                long time = System.currentTimeMillis();
                time += ReplyUtil.getDigit(msg.substring(0, msg.indexOf("分钟"))) * 1000 * 60L;
                date = new Date(time);
                remindMsg = msg.substring(msg.indexOf("我") + 1);
            }else if(msg.contains("小时")){
                // 如果是几小时后提醒
                long time = System.currentTimeMillis();
                time += ReplyUtil.getDigit(msg.substring(0, msg.indexOf("小时"))) * 1000 * 3600L;
                date = new Date(time);
                remindMsg = msg.substring(msg.indexOf("我") + 1);
            }else if(msg.contains("点")){
                // 如果是一个时间段提醒的话
                Calendar cal1 = Calendar.getInstance();
                cal1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

                if(msg.contains("明")) {
                    cal1.add(Calendar.DATE, 1);
                }else if(msg.contains("后天")){
                    cal1.add(Calendar.DATE, 2);
                }
                if(msg.contains("下午") || msg.contains("晚")){
                    cal1.set(Calendar.HOUR_OF_DAY, ReplyUtil.getDigit((msg.substring(0, msg.indexOf("点")))) + 12);
                    if(msg.contains("分")){
                        cal1.set(Calendar.MINUTE, ReplyUtil.getDigit(msg.substring(0, msg.indexOf("分"))));
                    }else if (msg.contains("半")){
                        cal1.set(Calendar.MINUTE, 30);
                    }else{
                        cal1.set(Calendar.MINUTE, ReplyUtil.getDigitAfter(msg.substring(msg.indexOf("点") + 1)));
                    }
                }else{
                    cal1.set(Calendar.HOUR_OF_DAY, ReplyUtil.getDigit((msg.substring(0, msg.indexOf("点")))));
                    if(msg.contains("分")){
                        cal1.set(Calendar.MINUTE, ReplyUtil.getDigit(msg.substring(0, msg.indexOf("分"))));
                    }else if (msg.contains("半")){
                        cal1.set(Calendar.MINUTE, 30);
                    }else{
                        cal1.set(Calendar.MINUTE, ReplyUtil.getDigitAfter(msg.substring(msg.indexOf("点") + 1)));
                    }
                }

                date = cal1.getTime();
                remindMsg = msg.substring(msg.indexOf("我") + 1);
            }else if(msg.contains("天") || msg.contains("明") || msg.contains("后")){
                // 如果是几天后提醒
                Calendar cal1 = Calendar.getInstance();
                cal1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                if(msg.contains("明")){
                    cal1.set(Calendar.DATE, cal1.get(Calendar.DATE) + 1);
                }else if(msg.contains("后")){
                    cal1.set(Calendar.DATE, cal1.get(Calendar.DATE) + 1);
                }
                date = cal1.getTime();
                remindMsg = msg.substring(msg.indexOf("我") + 1);
            }
        }
        if(remindMsg != null && date != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Reminder reminder = new Reminder();
            reminder.setDate(date);
            reminder.setMsg(remindMsg);
            return reminder;
        }else{
            return null;
        }
    }
}
