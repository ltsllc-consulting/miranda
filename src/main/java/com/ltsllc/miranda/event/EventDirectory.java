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

package com.ltsllc.miranda.event;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ltsllc.miranda.clientinterface.MirandaException;
import com.ltsllc.miranda.clientinterface.basicclasses.Event;
import com.ltsllc.miranda.directory.MirandaDirectory;
import com.ltsllc.miranda.directory.MirandaDirectoryLoadingState;
import com.ltsllc.miranda.reader.Reader;
import com.ltsllc.miranda.writer.Writer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Clark on 5/13/2017.
 */
public class EventDirectory extends MirandaDirectory<Event> {
    private static Gson gson = new Gson();

    public byte[] getBytes () {
        throw new RuntimeException("not implemented");
    }

    public List getData () {
        return new ArrayList(getMap().values());
    }

    public EventDirectory (String directoryName, int objectLimit, Reader reader, Writer writer) throws IOException, MirandaException {
        super(directoryName, objectLimit, reader, writer);

        MirandaDirectoryLoadingState mirandaDirectoryLoadingState = new MirandaDirectoryLoadingState(this);
        setCurrentState(mirandaDirectoryLoadingState);
    }

    public static final String EVENT_FILE = ".event";

    public boolean isInteresting (String name) {
        return name.endsWith(EVENT_FILE);
    }

    public Type getListType () {
        return new TypeToken<List<Event>>(){}.getType();
    }

    public void addFile (String filename, byte[] data) {
        if (getMap().size() >= getObjectLimit())
            return;

        String json = new String(data);
        List<Event> list = gson.fromJson(json, getListType());
        for (Event event : list) {
            add(event);
        }

        File file = new File(filename);
        getFiles().add(file);

        fireFileLoaded();
    }
}
