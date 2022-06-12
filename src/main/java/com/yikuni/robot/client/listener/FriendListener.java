package com.yikuni.robot.client.listener;

import com.yikuni.robot.akarin.config.YikuniClientConfig;
import com.yikuni.robot.client.reminder.Reminder;
import com.yikuni.robot.client.reminder.ReminderQueue;
import com.yikuni.robot.client.util.DateUtil;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.event.FriendMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class FriendListener {

    private final ReminderQueue queue = ReminderQueue.getQueue();

    @Autowired
    private YikuniClientConfig.Msg msg;

    @Listener
    @Filter(value = "我", matchType = MatchType.REGEX_CONTAINS)
    public void remind(FriendMessageEvent event){
        String msg = event.getMessageContent().getPlainText();
        Reminder reminder = DateUtil.readTime(msg);
        reminder.setFriend(event.getFriend());
        queue.addReminder(reminder);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        event.getUser().sendBlocking( this.msg.getOther().getReminderAdd() + ": 提醒目标: " + reminder.getMsg() + ", 提醒时间: " + format.format(reminder.getDate()) );

    }

    @Listener
    @Filter(value = "取消提醒", matchType = MatchType.TEXT_EQUALS)
    public void cancelReminder(FriendMessageEvent event){
        queue.removeReminder(event.getFriend());
        event.getSource().sendBlocking(msg.getOther().getCancelReminder());
    }

    @Listener
    @Filter(value = "取消上一个提醒", matchType = MatchType.TEXT_EQUALS)
    public void cancelPreReminder(FriendMessageEvent event){
        queue.removePreReminder(event.getFriend());
        event.getSource().sendBlocking(msg.getOther().getCancelReminder());
    }



}
