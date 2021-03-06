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

package com.ltsllc.miranda.node.networkMessages;

import com.ltsllc.miranda.node.NameVersion;

import java.util.List;

/**
 * Created by Clark on 2/6/2017.
 */
public class VersionsWireMessage extends WireMessage {
    private List<NameVersion> versions;

    public List<NameVersion> getVersions() {
        return versions;
    }

    public VersionsWireMessage(List<NameVersion> versions) {
        super(WireSubjects.Versions);
        this.versions = versions;
    }
}
