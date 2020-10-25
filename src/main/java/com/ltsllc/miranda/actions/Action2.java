package com.ltsllc.miranda.actions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;

/*
 * An action to be performed when taking a transition or entering or leaving a state
 */
abstract public class Action2 {
    public Object companion;

    public Action2 (Object companion)
    {
        setCompanion(companion);
    }

    public Object getCompanion() {
        return companion;
    }

    public void setCompanion(Object companion) {
        this.companion = companion;
    }

    /*
     * The action
     */
    abstract public State action(State state, Message message);
}
