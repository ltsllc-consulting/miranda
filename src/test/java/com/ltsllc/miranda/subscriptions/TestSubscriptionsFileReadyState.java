package com.ltsllc.miranda.subscriptions;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.Version;
import com.ltsllc.miranda.node.messages.GetFileMessage;
import com.ltsllc.miranda.node.messages.GetVersionMessage;
import com.ltsllc.miranda.subsciptions.SubscriptionsFile;
import com.ltsllc.miranda.subsciptions.SubscriptionsFileReadyState;
import com.ltsllc.miranda.test.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Clark on 3/24/2017.
 */
public class TestSubscriptionsFileReadyState extends TestCase {
    @Mock
    private SubscriptionsFile mockSubscriptionsFile;

    private SubscriptionsFileReadyState readyState;

    public SubscriptionsFileReadyState getReadyState() {
        return readyState;
    }

    @Override
    public SubscriptionsFile getMockSubscriptionsFile() {
        return mockSubscriptionsFile;
    }

    public void reset () {
        super.reset();

        mockSubscriptionsFile = null;
        readyState = null;
    }

    @Before
    public void setup () {
        reset();

        super.setup();

        setuplog4j();

        mockSubscriptionsFile = mock(SubscriptionsFile.class);
        readyState = new SubscriptionsFileReadyState(mockSubscriptionsFile);
    }

    @Test
    public void testProcessGetVersionMessage () {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
        GetVersionMessage getVersionMessage = new GetVersionMessage(null, this, queue);

        Version version = new Version();
        when(getMockSubscriptionsFile().getVersion()).thenReturn(version);

        State nextState = getReadyState().processMessage(getVersionMessage);

        assert (nextState instanceof SubscriptionsFileReadyState);
        assert (contains(Message.Subjects.Version, queue));
    }

    @Test
    public void testProcessGetFileMessage () {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
        GetFileMessage getFileMessage = new GetFileMessage(queue, this, "whatever");

        byte[] data = "whatever".getBytes();
        when(getMockSubscriptionsFile().getBytes()).thenReturn(data);

        State nextState = getReadyState().processMessage(getFileMessage);

        assert (nextState instanceof SubscriptionsFileReadyState);
        assert (contains(Message.Subjects.GetFileResponse, queue));
    }
}
