package sun.nio.ch;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.ShutdownChannelGroupException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class Port extends AsynchronousChannelGroupImpl {
    protected final Map fdToChannel;
    protected final ReadWriteLock fdToChannelLock;

    interface PollableChannel extends Closeable {
        void onEvent(int i, boolean z);
    }

    protected void preUnregister(int i) {
    }

    abstract void startPoll(int i, int i2);

    Port(AsynchronousChannelProvider asynchronousChannelProvider, ThreadPool threadPool) {
        super(asynchronousChannelProvider, threadPool);
        this.fdToChannelLock = new ReentrantReadWriteLock();
        this.fdToChannel = new HashMap();
    }

    final void register(int i, PollableChannel pollableChannel) {
        this.fdToChannelLock.writeLock().lock();
        try {
            if (isShutdown()) {
                throw new ShutdownChannelGroupException();
            }
            this.fdToChannel.put(Integer.valueOf(i), pollableChannel);
        } finally {
            this.fdToChannelLock.writeLock().unlock();
        }
    }

    final void unregister(int i) {
        preUnregister(i);
        this.fdToChannelLock.writeLock().lock();
        try {
            this.fdToChannel.remove(Integer.valueOf(i));
            if (this.fdToChannel.isEmpty() && isShutdown()) {
                try {
                    shutdownNow();
                } catch (IOException unused) {
                }
            }
        } finally {
            this.fdToChannelLock.writeLock().unlock();
        }
    }

    final boolean isEmpty() {
        this.fdToChannelLock.writeLock().lock();
        try {
            return this.fdToChannel.isEmpty();
        } finally {
            this.fdToChannelLock.writeLock().unlock();
        }
    }

    class 1 implements PollableChannel {
        final /* synthetic */ Channel val$channel;

        public void onEvent(int i, boolean z) {
        }

        1(Channel channel) {
            this.val$channel = channel;
        }

        public void close() throws IOException {
            this.val$channel.close();
        }
    }

    final Object attachForeignChannel(Channel channel, FileDescriptor fileDescriptor) {
        int fdVal = IOUtil.fdVal(fileDescriptor);
        register(fdVal, new 1(channel));
        return Integer.valueOf(fdVal);
    }

    final void detachForeignChannel(Object obj) {
        unregister(((Integer) obj).intValue());
    }

    final void closeAllChannels() {
        int i;
        PollableChannel[] pollableChannelArr = new PollableChannel[128];
        do {
            this.fdToChannelLock.writeLock().lock();
            try {
                Iterator it = this.fdToChannel.keySet().iterator();
                i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    int i2 = i + 1;
                    pollableChannelArr[i] = (PollableChannel) this.fdToChannel.get((Integer) it.next());
                    if (i2 >= 128) {
                        i = i2;
                        break;
                    }
                    i = i2;
                }
                for (int i3 = 0; i3 < i; i3++) {
                    try {
                        pollableChannelArr[i3].close();
                    } catch (IOException unused) {
                    }
                }
            } finally {
                this.fdToChannelLock.writeLock().unlock();
            }
        } while (i > 0);
    }
}
