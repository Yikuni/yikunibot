package com.yikuni.robot.client.reminder;

import love.forte.simbot.definition.Friend;

import java.util.ArrayList;
import java.util.List;

public class ReminderQueue {
    private static final ReminderQueue queue = new ReminderQueue();
    private List<Reminder> reminderList = new ArrayList<>();

    private ReminderQueue(){}

    public static ReminderQueue getQueue(){
        return queue;
    }

    /**
     * 增加提醒事项
     * @param reminder 提醒事项
     */
    public void addReminder(Reminder reminder){
        reminderList.add(reminder);
    }

    /**
     * 删除某个人的提醒事项
     * @param friend    好友
     */
    public void removeReminder(Friend friend){
        queue.getReminderList().removeIf(reminder -> reminder.getFriend().getId().equals(friend.getId()));
    }

    /**
     * 删除上一个提醒事项
     * @param friend    好友
     * @return  true, 删除成功, false, 删除失败
     */
    public boolean removePreReminder(Friend friend){
        for(int i = reminderList.size() - 1; i >= 0; i--){
            if(reminderList.get(i).getFriend().getId().equals(friend.getId())){
                reminderList.remove(i);
                return true;
            }
        }
        return false;
    }

    public void send(){
        queue.getReminderList().removeIf(Reminder::compareAndSend);
    }

    private List<Reminder> getReminderList() {
        return reminderList;
    }

    private void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }



    @Override
    public String toString() {
        return "ReminderQueue{" +
                "reminderList=" + reminderList +
                '}';
    }
}
