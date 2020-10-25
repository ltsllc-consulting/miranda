package com.ltsllc.miranda.actions;

import com.ltsllc.miranda.Consumer;

/*
 *
 * Action
 *
 * This class represents an independent consumer that tries to accomplish some action like synchronizing two nodes, or
 * having a quorum vote.
 *
 * The action has two methods that consumers are interested in: launch and do
 */
public class Action extends Consumer {
    public void launch () {
        setCurrentState();
    }
}
