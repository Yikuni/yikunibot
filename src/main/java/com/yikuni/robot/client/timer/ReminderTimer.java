package com.yikuni.robot.client.timer;

import com.yikuni.robot.client.reminder.ReminderQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReminderTimer {

    private ReminderQueue queue = ReminderQueue.getQueue();

    @Scheduled(fixedDelay = 1000 * 60)
    public void remind(){
        queue.send();
    }
}
