package com.ltsllc.miranda.synchronize;

import com.ltsllc.miranda.synchronize.companions.StateMachine;

import java.util.concurrent.BlockingQueue;

/*
 * A class that represents the combination of a state machine, a companion class and a message queue
 */
public class Peer {
    public StateMachine stateMachine;
    public Object companion;
    public BlockingQueue queue;

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }


    public BlockingQueue getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue queue) {
        this.queue = queue;
    }

    public Object getCompanion() {
        return companion;
    }

    public void setCompanion(Object companion) {
        this.companion = companion;
    }

    public Peer (StateMachine stateMachine, Object companion, BlockingQueue queue) {
        setStateMachine(stateMachine);
        setCompanion(companion);
        setQueue(queue);
    }
}
