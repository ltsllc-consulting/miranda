/*
 * Copyright 2017 Long Term Software LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ltsllc.miranda.actions;

import com.google.gson.reflect.TypeToken;
import com.ltsllc.miranda.LoadResponseMessage;
import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.Version;
import com.ltsllc.miranda.clientinterface.MirandaException;
import com.ltsllc.miranda.clientinterface.basicclasses.NodeElement;
import com.ltsllc.miranda.cluster.ClusterFile;
import com.ltsllc.miranda.cluster.messages.*;
import com.ltsllc.miranda.file.SingleFile;
import com.ltsllc.miranda.file.states.SingleFileReadyState;
import com.ltsllc.miranda.miranda.Miranda;
import com.ltsllc.miranda.node.messages.GetClusterFileMessage;
import com.ltsllc.miranda.property.MirandaProperties;
import com.ltsllc.miranda.writer.WriteFailedMessage;
import com.ltsllc.miranda.writer.WriteMessage;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clark on 8/6/2020
 *
 * ReadyState
 *
 * A generic ready state.
 * Clients are meant to override this class and define the actions that should be called as a result of the psuedo
 * messages Start and Stop arriving.
 * The class also defines some minutia surrounding loggers
 */
public class ReadyState extends State {
    private static Logger logger = Logger.getLogger(ReadyState.class);

    public ReadyState() throws MirandaException {
    }


    public static void setLogger(Logger logger) {
        ReadyState.logger = logger;
    }

    public static Logger getLogger () {
        return logger;
    }

    /*
     * processMessage --- process a message
     *
     * Clients are not expected to call this.
     * This method calls the various methods associated with an action
     * The method looks out for the psuedo-messages: Start, and Stop and calls start and stop based on this.
     */
    @Override
    public State processMessage(Message message) throws MirandaException {
        State nextState = this;

        switch (message.getSubject()) {
            case Start:
                nextState = start(message);
                break;

            case Stop:
                nextState = stop(message);
                break;

            default:
                nextState = super.processMessage(message);
                break;
        }
        return nextState;
    }

    public State start (Message message) {
        State unknown = new UnknownState();
        return unknown;
    }

    public State stop (Message message) {
        State unknown = new UnknownState();
        return unknown;
    }





    @Override
    public String toString() {
        return "ReadyState";
    }
}
