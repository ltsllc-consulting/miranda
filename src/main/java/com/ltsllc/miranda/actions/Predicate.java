package com.ltsllc.miranda.actions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
/*
 * A guard that determines if a transition should be taken
 */
abstract public class Predicate {
    public Object companion;

    public Object getCompanion() {
        return companion;
    }

    public void setCompanion(Object companion) {
        this.companion = companion;
    }

    public Predicate (Object companion)
    {
        setCompanion(companion);
    }

    /*
     * The guard
     */
    abstract public boolean shouldTake(State state, Message message);
}
