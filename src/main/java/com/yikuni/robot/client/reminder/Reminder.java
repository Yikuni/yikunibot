package com.yikuni.robot.client.reminder;

import com.yikuni.robot.akarin.config.YikuniClientConfig;
import com.yikuni.robot.client.util.SpringUtil;
import love.forte.simbot.definition.Friend;

import java.util.Date;

public class Reminder {
    private Friend friend;
    private String msg;
    private Date date;

    private static YikuniClientConfig.Msg.Other other = ((YikuniClientConfig.Msg) SpringUtil.getBean("msg")).getOther();

    /**
     * 判断是否要送信, 并执行送信
     * @return true: 要送信, false, 不需要送信
     */
    public boolean compareAndSend(){
        if(new Date().after(this.date)){
            // 如果要送信
            friend.sendBlocking(other.getReminderPrefix() + msg + other.getReminderSuffix());
            return true;
        }else{
            return false;
        }
    }

    public Reminder() {
    }

    public Reminder(Friend friend, String msg, Date date) {
        this.friend = friend;
        this.msg = msg;
        this.date = date;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "friend=" + friend +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}
