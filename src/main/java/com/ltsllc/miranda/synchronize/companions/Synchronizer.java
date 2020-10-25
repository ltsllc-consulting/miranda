package com.ltsllc.miranda.synchronize.companions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.StopState;
import com.ltsllc.miranda.actions.Action2;
import com.ltsllc.miranda.actions.Predicate;
import com.ltsllc.miranda.clientinterface.Version;
import com.ltsllc.miranda.node.messages.VersionMessage;
import com.ltsllc.miranda.synchronize.Peer;

import java.util.concurrent.LinkedBlockingQueue;

/*
 * A class that knows how to synchronize two objects
 */
public class Synchronizer extends Peer {
    public RemoteSynchronizer remote;

    public RemoteSynchronizer getRemote() {
        return remote;
    }

    public void setRemote(RemoteSynchronizer remote) {
        this.remote = remote;
    }

    public Synchronizer (Object companion) {
        super(Synchronizer.buildMachine(companion), companion, new LinkedBlockingQueue());
    }


    public static StateMachine buildMachine(Object companion) {
        State start = new ConcreteState();
        State stop = new ConcreteState();

        Action2 action = new Action2(companion) {
            @Override
            public State action(State state, Message message) {
                Synchronizer synchronizer = (Synchronizer) getCompanion();
                synchronizer.getRemote().askForVersion();
                return null;
            }
        };

        Predicate predicate = new Predicate() {
            @Override
            public boolean shouldTake(State state, Message message) {
                Synchronizer synchronizer = (Synchronizer) getCompanion();
                return synchronizer.versionIsOlder (message);
            }
        };

        Action2 waitingAction = new Action2(companion) {
            @Override
            public State action(State state, Message message) {
                return null;
            }
        };

        start.setEntryAction(action);
        return new StateMachine();
    }

    public boolean versionIsOlder (Message message) {
        VersionMessage versionMessage = (VersionMessage) message;
        return versionMessage.getVersion().isOlderThan (getVersion());
    }
}
