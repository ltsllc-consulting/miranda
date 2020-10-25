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

package com.ltsllc.miranda.clientinterface.requests;

import com.ltsllc.miranda.clientinterface.basicclasses.Topic;

/**
 * Created by Clark on 4/28/2017.
 */
public class TopicRequest extends Request {
    private Topic topic;

    public TopicRequest(String sessionIdString, Topic topic) {
        super(sessionIdString);
        this.topic = topic;
    }

    public TopicRequest(String sessionId, Object object) {
        super(sessionId);
        Topic topic = (Topic) object;
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
