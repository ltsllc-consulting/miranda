package com.ltsllc.miranda.synchronize.companions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.clientinterface.Version;
import com.ltsllc.miranda.node.networkMessages.NetworkMessage;
import com.ltsllc.miranda.node.networkMessages.WireMessage;
import com.ltsllc.miranda.synchronize.Peer;

import java.util.concurrent.BlockingQueue;

/*
 * A remote version of an object to be synchronized
 */
public class RemoteSynchronizer extends Peer {
    public Object companion;

    public RemoteSynchronizer (StateMachine stateMachine, Object companion, BlockingQueue queue) {
        super(stateMachine, companion, queue);
    }

    public void askForVersion () {
        WireMessage wireMessage = new WireMessage(WireMessage.WireSubjects.GetVersion);
        NetworkMessage networkMessage = new NetworkMessage(Message.Subjects.NetworkMessage, getQueue(), wireMessage);
        getQueue().add(networkMessage);
    }
}
