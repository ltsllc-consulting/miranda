package com.ltsllc.miranda.synchronize.companions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.actions.Companion;
import com.ltsllc.miranda.synchronize.RemoteSynchronizingPeer;
import com.ltsllc.miranda.synchronize.messages.SynchronizingMessage;

/*
 * The companion class that gets started in response to a request to synchronize
 */
public class SynchronizingCompanion extends Companion {
    /*
     * who we are synchronizing against
     */
    public RemoteSynchronizingPeer peer;

    /*
     * Tell the peer when the last time we were modified.
     *
     * We must be told who we are synchronizing against
     */
    public State start (State currentState, Message message) {
        SynchronizingMessage peerMessage = (SynchronizingMessage) message;

        peer = peerMessage.remoteSynchronizingPeer;

        return currentState;
    }
}
