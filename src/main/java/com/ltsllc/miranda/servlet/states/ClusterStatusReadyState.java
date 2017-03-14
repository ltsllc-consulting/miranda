package com.ltsllc.miranda.servlet.states;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.servlet.GetStatusResponseMessage;
import com.ltsllc.miranda.servlet.holder.ClusterStatus;

/**
 * Created by Clark on 3/10/2017.
 */
public class ClusterStatusReadyState extends State {
    public ClusterStatusReadyState (ClusterStatus clusterStatus) {
        super(clusterStatus);
    }

    public ClusterStatus getClusterStatus () {
        return (ClusterStatus) getContainer();
    }

    @Override
    public State processMessage(Message message) {
        State nextState = this;

        switch (message.getSubject()) {
            case GetStatus: {
                GetStatusResponseMessage getStatusResponseMessage = (GetStatusResponseMessage) message;
                nextState = processGetStatusMessage(getStatusResponseMessage);
                break;
            }

            case GetStatusResponse: {
                GetStatusResponseMessage getStatusResponseMessage = (GetStatusResponseMessage) message;
                nextState = processGetStatusResponseMessage (getStatusResponseMessage);
                break;
            }
            default: {
                nextState = super.processMessage(message);
                break;
            }
        }
        return nextState;
    }

    private State processGetStatusMessage (GetStatusResponseMessage message) {
        getClusterStatus().receivedClusterStatus(message);

        return this;
    }

    private State processGetStatusResponseMessage (GetStatusResponseMessage getStatusResponseMessage) {
        getClusterStatus().receivedClusterStatus(getStatusResponseMessage);

        return this;
    }
}