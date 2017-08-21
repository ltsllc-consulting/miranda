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

package com.ltsllc.miranda.file.states;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.file.FileWatcherService;
import com.ltsllc.miranda.file.messages.UnwatchFileMessage;
import com.ltsllc.miranda.file.messages.WatchFileMessage;
import com.ltsllc.miranda.miranda.Miranda;
import com.ltsllc.miranda.test.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Clark on 2/25/2017.
 */
public class TestFileWatcherReadyState extends TestCase {
    private static final String ROOT = "testdir";

    private static final String[][] FILE_SYSTEM_SPEC = {
            {"whatever", "random file"},
            {"new", "directory"},
            {"new/20170220-001.msg", "event file"},
            {"old", "directory"},
            {"old/whatever", "random file"},
    };

    private FileWatcherService fileWatcherService;
    private BlockingQueue<Message> queue;

    public BlockingQueue<Message> getQueue() {
        return queue;
    }

    public FileWatcherService getFileWatcherService() {
        return fileWatcherService;
    }

    public void reset() {
        super.reset();

        super.setup();

        fileWatcherService = null;
        queue = null;
    }

    @Before
    public void setup() {
        reset();

        setuplog4j();
        setupMirandaProperties();

        setupFileWatcher(100);

        createFileSystem(ROOT, FILE_SYSTEM_SPEC);

        this.queue = new LinkedBlockingQueue<Message>();
        this.fileWatcherService = Miranda.fileWatcher;
    }


    public boolean contains (Map map, String entry) {
        return map.containsKey(entry);
    }

    @After
    public void cleanup() {
        deleteDirectory(ROOT);
    }

    private static final String FILE_NAME = "testdir/new/20170220-001.msg";

    @Test
    public void testProcessUnwatchFileMessage() {
        File file = new File(FILE_NAME);
        UnwatchFileMessage unwatchFileMessage = new UnwatchFileMessage(getQueue(), this, file);
        WatchFileMessage watchFileMessage = new WatchFileMessage(getQueue(), this, file, getQueue());

        send(watchFileMessage, getFileWatcherService().getQueue());

        pause(500);

        touch(file);

        pause(500);

        assert (contains(Message.Subjects.FileChanged, getQueue()));
        send(unwatchFileMessage, getFileWatcherService().getQueue());

        pause(250);
        getQueue().clear();

        touch(file);

        pause(250);

        assert (!contains(Message.Subjects.FileChanged, getQueue()));
    }
}
