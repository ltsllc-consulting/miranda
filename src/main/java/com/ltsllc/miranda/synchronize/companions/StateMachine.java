package com.ltsllc.miranda.synchronize.companions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.actions.Action;
import com.ltsllc.miranda.clientinterface.MirandaException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * a state machine capable of processing messages
 */
public class StateMachine {
    public BlockingQueue queue;
    public Object companion;
    public State currentState;
    public State startState;

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
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

    /*
     * Start the state machine with a specifying action, companion, and a message
     */
    public State launch  (Action startAction, Object companion, Message startMessage) {
        setCompanion(companion);
        return startAction.start(startMessage);
    }

    /*
     * launch a state machine with a default instance of a state and a specified companion class.
     *
     * Subclasses that define this message should override getCompanion and getCurrentState to
     * return the appropriate classes.  The method assumes that somebody has put a start message
     * on the queue.
     *
     * param: blockingQueue should already have a message on it.
     */
    public void launch (BlockingQueue blockingQueue)
            throws MirandaException {
        setQueue(blockingQueue);
        try {
            setCurrentState(getCurrentState().processMessage((Message) getQueue().take()));
        } catch (InterruptedException e) {
            throw new MirandaException(e);
        }
    }
}
