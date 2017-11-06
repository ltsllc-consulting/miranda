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
import com.ltsllc.miranda.Panic;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.clientinterface.MirandaException;
import com.ltsllc.miranda.file.FileWatcherService;
import com.ltsllc.miranda.file.messages.StopWatchingMessage;
import com.ltsllc.miranda.file.messages.WatchDirectoryMessage;
import com.ltsllc.miranda.file.messages.WatchFileMessage;
import com.ltsllc.miranda.miranda.Miranda;

import java.io.IOException;

/**
 * Created by Clark on 2/25/2017.
 */
public class FileWatcherReadyState extends State {
    private FileWatcherService fileWatcherService;

    public FileWatcherService getFileWatcherService() {
        return fileWatcherService;
    }

    public FileWatcherReadyState (FileWatcherService fileWatcherService) throws MirandaException {
        super(fileWatcherService);

        this.fileWatcherService = fileWatcherService;
    }

    @Override
    public State processMessage(Message message) throws MirandaException {
        State nextState = this;

        switch (message.getSubject()) {
            case WatchFile: {
                WatchFileMessage watchFileMessage = (WatchFileMessage) message;
                nextState = processWatchFileMessage(watchFileMessage);
                break;
            }

            case WatchDirectory: {
                WatchDirectoryMessage watchDirectoryMessage = (WatchDirectoryMessage) message;
                nextState = processWatchDirectoryMessage (watchDirectoryMessage);
                break;
            }

            case StopWatching: {
                StopWatchingMessage stopWatchingMessage = (StopWatchingMessage) message;
                nextState = processStopWatchingMessage(stopWatchingMessage);
                break;
            }

            default: {
                nextState = super.processMessage(message);
                break;
            }
        }

        return nextState;
    }


    public State processWatchFileMessage (WatchFileMessage watchFileMessage) {
        try {
            getFileWatcherService().watchFile(watchFileMessage.getFile(), watchFileMessage.getListener());
        } catch (IOException e) {
            String message = "Exception watching " + watchFileMessage.getFile();
            Panic panic = new Panic (message, e, Panic.Reasons.ExceptionInProcessMessage);
            Miranda.getInstance().panic(panic);
        }

        return getFileWatcherService().getCurrentState();
    }


    public State processWatchDirectoryMessage (WatchDirectoryMessage watchDirectoryMessage) {
        try {
            getFileWatcherService().watchDirectory(watchDirectoryMessage.getDirectory(), watchDirectoryMessage.getListener());
        } catch (IOException e) {
            String message = "Exception watching " + watchDirectoryMessage.getDirectory();
            Panic panic = new Panic(message, e, Panic.Reasons.ExceptionInProcessMessage);
            Miranda.panicMiranda(panic);
        }

        return getFileWatcherService().getCurrentState();
    }

    public State processStopWatchingMessage (StopWatchingMessage stopWatchingMessage) {
        getFileWatcherService().stopWatching(stopWatchingMessage.getFile(), stopWatchingMessage.getListener());

        return getFileWatcherService().getCurrentState();
    }
}
