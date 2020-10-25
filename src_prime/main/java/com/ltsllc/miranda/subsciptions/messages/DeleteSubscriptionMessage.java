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

package com.ltsllc.miranda.subsciptions.messages;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.SessionMessage;
import com.ltsllc.miranda.session.Session;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Clark on 4/22/2017.
 */
public class DeleteSubscriptionMessage extends SessionMessage {
    private String subscriptionName;

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public DeleteSubscriptionMessage(BlockingQueue<Message> senderQueue, Object sender, Session session, String subscriptionName) {
        super(Subjects.DeleteSubscription, senderQueue, sender, session);

        this.subscriptionName = subscriptionName;
    }
}
