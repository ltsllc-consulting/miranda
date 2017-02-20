package com.ltsllc.miranda.timer;

import com.ltsllc.miranda.Message;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Clark on 2/19/2017.
 */
public class SchedulePeriodicMessage extends ScheduleMessage {
    private long period;

    public SchedulePeriodicMessage (BlockingQueue<Message> senderQueue, Object sender, Message message, long period) {
        super(Subjects.SchedulePeriodic, senderQueue, sender, message);

        this.period = period;
    }

    public long getPeriod() {
        return period;
    }
}
