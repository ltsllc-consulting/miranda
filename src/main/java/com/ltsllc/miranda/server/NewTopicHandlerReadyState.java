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

package com.ltsllc.miranda.server;

import com.ltsllc.miranda.Consumer;
import com.ltsllc.miranda.clientinterface.MirandaException;
import com.ltsllc.miranda.clientinterface.basicclasses.Topic;
import com.ltsllc.miranda.topics.TopicsFile;

import java.lang.reflect.Type;

/**
 * Created by Clark on 2/18/2017.
 */
public class NewTopicHandlerReadyState extends NewObjectHandlerReadyState<TopicsFile, NewTopicHandler> {
    public Type getBasicType() {
        return Topic.class;
    }

    public NewTopicHandlerReadyState(Consumer consumer, TopicsFile file, NewTopicHandler handler) throws MirandaException {
        super(consumer, file, handler);
    }
}
