package com.ltsllc.miranda;

import com.ltsllc.miranda.cluster.states.ClusterFileGettingVersionState;
import com.ltsllc.miranda.miranda.StopMessage;
import com.ltsllc.miranda.test.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Clark on 3/28/2017.
 */
public class TestState extends TestCase {
    public static class TestBlockingQueue extends LinkedBlockingQueue<Message> {
        private InterruptedException interruptedException;

        public void setInterruptedException(InterruptedException interruptedException) {
            this.interruptedException = interruptedException;
        }

        public void put (Message message) throws InterruptedException {
            if (null != interruptedException)
                throw interruptedException;

            super.put(message);
        }
    }
    @Mock
    private Consumer mockConsumer;

    private BlockingQueue<Message> queue;
    private State state;

    public State getState() {
        return state;
    }

    public Consumer getMockConsumer() {
        return mockConsumer;
    }

    public BlockingQueue<Message> getQueue() {
        return queue;
    }

    public void reset () {
        super.reset();

        mockConsumer = null;
        queue = null;
        state = null;
    }

    @Before
    public void setup () {
        reset();

        super.setup();

        mockConsumer = mock(Consumer.class);
        queue = new LinkedBlockingQueue<Message>();

        state = new ClusterFileGettingVersionState(mockConsumer, queue);
    }

    @Test
    public void testProcessStopMessage () {
        StopMessage stopMessage = new StopMessage(null, this);

        State nextState = getState().processMessage(stopMessage);

        assert (nextState instanceof StopState);
    }

    @Test
    public void testEquals () {
        assert (getState().equals(getState()));
        assert (!getState().equals(null));
    }

    @Test
    public void testStart () {
        setuplog4j();

        State nextState = getState().start();

        assert (nextState == getState());
    }

    @Test
    public void testSendSuccess () {
        Message message = new Message(Message.Subjects.WriteFailed,null, this);
        BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

        getState().send(queue, message);

        assert (contains(Message.Subjects.WriteFailed, queue));
    }

    @Test
    public void testSendException () {
        setuplog4j();
        setupMockMiranda();
        Message message = new Message(Message.Subjects.WriteFailed, null, this);
        TestBlockingQueue testBlockingQueue = new TestBlockingQueue();
        InterruptedException interruptedException = new InterruptedException("test");
        testBlockingQueue.setInterruptedException(interruptedException);

        getState().send(testBlockingQueue, message);

        verify(getMockMiranda(), atLeastOnce()).panic(Matchers.any(Panic.class));
    }
}