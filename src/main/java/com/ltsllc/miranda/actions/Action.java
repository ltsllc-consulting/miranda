package com.ltsllc.miranda.actions;

import com.ltsllc.miranda.Consumer;
import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.http.StartMessage;

/*
 *
 * Action
 *
 * This class represents an independent consumer that tries to accomplish some action like synchronizing two nodes, or
 * having a quorum vote.
 *
 * The action has two methods that consumers are interested in: start and stop
 */
abstract public class Action extends Consumer {
    private Object companion;

    public Object getCompanion() {
        return companion;
    }

    public void setCompanion(Object companion) {
        this.companion = companion;
    }

    /*
     ^ startup the action
     */
    public void launch () {
        Message message = new StartMessage(getQueue(), getCompanion());
        sendToMe(message);
    }

    /*
     * take an action when the class starts up.
     *
     * This method is for those Actions that want to take some action when the class starts up.
     */
    abstract public State start (Message message);

    /*
     * take an action when the class shuts down.
     *
     * This method is for those Actions that want to take some action when the class shuts down.
     */
    abstract public State stop (Message message);

}
