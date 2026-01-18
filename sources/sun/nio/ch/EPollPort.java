package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import sun.nio.ch.Port;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class EPollPort extends Port {
    private static final int ENOENT = 2;
    private static final int MAX_EPOLL_EVENTS = 512;
    private final Event EXECUTE_TASK_OR_SHUTDOWN;
    private final Event NEED_TO_POLL;
    private final long address;
    private boolean closed;
    private final int epfd;
    private final ArrayBlockingQueue queue;
    private final int[] sp;
    private final AtomicInteger wakeupCount;

    static /* bridge */ /* synthetic */ Event -$$Nest$fgetEXECUTE_TASK_OR_SHUTDOWN(EPollPort ePollPort) {
        return ePollPort.EXECUTE_TASK_OR_SHUTDOWN;
    }

    static /* bridge */ /* synthetic */ Event -$$Nest$fgetNEED_TO_POLL(EPollPort ePollPort) {
        return ePollPort.NEED_TO_POLL;
    }

    static /* bridge */ /* synthetic */ long -$$Nest$fgetaddress(EPollPort ePollPort) {
        return ePollPort.address;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$fgetepfd(EPollPort ePollPort) {
        return ePollPort.epfd;
    }

    static /* bridge */ /* synthetic */ ArrayBlockingQueue -$$Nest$fgetqueue(EPollPort ePollPort) {
        return ePollPort.queue;
    }

    static /* bridge */ /* synthetic */ int[] -$$Nest$fgetsp(EPollPort ePollPort) {
        return ePollPort.sp;
    }

    static /* bridge */ /* synthetic */ AtomicInteger -$$Nest$fgetwakeupCount(EPollPort ePollPort) {
        return ePollPort.wakeupCount;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$mimplClose(EPollPort ePollPort) {
        ePollPort.implClose();
    }

    static class Event {
        final Port.PollableChannel channel;
        final int events;

        Event(Port.PollableChannel pollableChannel, int i) {
            this.channel = pollableChannel;
            this.events = i;
        }

        Port.PollableChannel channel() {
            return this.channel;
        }

        int events() {
            return this.events;
        }
    }

    EPollPort(AsynchronousChannelProvider asynchronousChannelProvider, ThreadPool threadPool) throws IOException {
        super(asynchronousChannelProvider, threadPool);
        this.wakeupCount = new AtomicInteger();
        Event event = new Event(null, 0);
        this.NEED_TO_POLL = event;
        this.EXECUTE_TASK_OR_SHUTDOWN = new Event(null, 0);
        int create = EPoll.create();
        this.epfd = create;
        this.address = EPoll.allocatePollArray(512);
        try {
            long makePipe = IOUtil.makePipe(true);
            int[] iArr = {(int) (makePipe >>> 32), (int) makePipe};
            this.sp = iArr;
            EPoll.ctl(create, 1, iArr[0], 1);
            ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(512);
            this.queue = arrayBlockingQueue;
            arrayBlockingQueue.offer(event);
        } catch (IOException e) {
            EPoll.freePollArray(this.address);
            FileDispatcherImpl.closeIntFD(this.epfd);
            throw e;
        }
    }

    EPollPort start() {
        startThreads(new EventHandlerTask(this, null));
        return this;
    }

    private void implClose() {
        synchronized (this) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            try {
                FileDispatcherImpl.closeIntFD(this.epfd);
            } catch (IOException unused) {
            }
            try {
                FileDispatcherImpl.closeIntFD(this.sp[0]);
            } catch (IOException unused2) {
            }
            try {
                FileDispatcherImpl.closeIntFD(this.sp[1]);
            } catch (IOException unused3) {
            }
            EPoll.freePollArray(this.address);
        }
    }

    private void wakeup() {
        if (this.wakeupCount.incrementAndGet() == 1) {
            try {
                IOUtil.write1(this.sp[1], (byte) 0);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    void executeOnHandlerTask(Runnable runnable) {
        synchronized (this) {
            if (this.closed) {
                throw new RejectedExecutionException();
            }
            offerTask(runnable);
            wakeup();
        }
    }

    void shutdownHandlerTasks() {
        int threadCount = threadCount();
        if (threadCount == 0) {
            implClose();
            return;
        }
        while (true) {
            int i = threadCount - 1;
            if (threadCount <= 0) {
                return;
            }
            wakeup();
            threadCount = i;
        }
    }

    void startPoll(int i, int i2) {
        int i3 = i2 | 1073741824;
        int ctl = EPoll.ctl(this.epfd, 3, i, i3);
        if (ctl == 2) {
            ctl = EPoll.ctl(this.epfd, 1, i, i3);
        }
        if (ctl != 0) {
            throw new AssertionError();
        }
    }

    private class EventHandlerTask implements Runnable {
        /* synthetic */ EventHandlerTask(EPollPort ePollPort, EPollPort-IA r2) {
            this();
        }

        private EventHandlerTask() {
        }

        private Event poll() throws IOException {
            while (true) {
                try {
                    int wait = EPoll.wait(EPollPort.-$$Nest$fgetepfd(EPollPort.this), EPollPort.-$$Nest$fgetaddress(EPollPort.this), 512, -1);
                    if (wait != -3) {
                        EPollPort.this.fdToChannelLock.readLock().lock();
                        while (true) {
                            int i = wait - 1;
                            if (wait <= 0) {
                                break;
                            }
                            try {
                                long event = EPoll.getEvent(EPollPort.-$$Nest$fgetaddress(EPollPort.this), i);
                                int descriptor = EPoll.getDescriptor(event);
                                if (descriptor == EPollPort.-$$Nest$fgetsp(EPollPort.this)[0]) {
                                    if (EPollPort.-$$Nest$fgetwakeupCount(EPollPort.this).decrementAndGet() == 0) {
                                        while (IOUtil.drain1(EPollPort.-$$Nest$fgetsp(EPollPort.this)[0]) == -3) {
                                        }
                                    }
                                    if (i <= 0) {
                                        return EPollPort.-$$Nest$fgetEXECUTE_TASK_OR_SHUTDOWN(EPollPort.this);
                                    }
                                    EPollPort.-$$Nest$fgetqueue(EPollPort.this).offer(EPollPort.-$$Nest$fgetEXECUTE_TASK_OR_SHUTDOWN(EPollPort.this));
                                } else {
                                    Port.PollableChannel pollableChannel = (Port.PollableChannel) EPollPort.this.fdToChannel.get(Integer.valueOf(descriptor));
                                    if (pollableChannel != null) {
                                        Event event2 = new Event(pollableChannel, EPoll.getEvents(event));
                                        if (i <= 0) {
                                            return event2;
                                        }
                                        EPollPort.-$$Nest$fgetqueue(EPollPort.this).offer(event2);
                                    } else {
                                        continue;
                                    }
                                }
                                wait = i;
                            } finally {
                                EPollPort.this.fdToChannelLock.readLock().unlock();
                            }
                        }
                    }
                } finally {
                    EPollPort.-$$Nest$fgetqueue(EPollPort.this).offer(EPollPort.-$$Nest$fgetNEED_TO_POLL(EPollPort.this));
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x0070, code lost:
        
            r4.channel().onEvent(r4.events(), r3);
         */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0051 A[Catch: all -> 0x0083, TRY_LEAVE, TryCatch #3 {all -> 0x0083, blocks: (B:20:0x0016, B:50:0x002a, B:22:0x0049, B:24:0x0051, B:41:0x0070, B:53:0x0030), top: B:19:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0070 A[EDGE_INSN: B:40:0x0070->B:41:0x0070 BREAK  A[LOOP:1: B:6:0x000c->B:28:0x006e], EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x002a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r6 = this;
                sun.nio.ch.Invoker$GroupAndInvokeCount r0 = sun.nio.ch.Invoker.getGroupAndInvokeCount()
                r1 = 0
                r2 = 1
                if (r0 == 0) goto La
                r3 = 1
                goto Lb
            La:
                r3 = 0
            Lb:
                r4 = 0
            Lc:
                if (r3 == 0) goto L16
                r0.resetInvokeCount()     // Catch: java.lang.Throwable -> L12
                goto L16
            L12:
                r0 = move-exception
                r1 = r4
                goto L84
            L16:
                sun.nio.ch.EPollPort r4 = sun.nio.ch.EPollPort.this     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                java.util.concurrent.ArrayBlockingQueue r4 = sun.nio.ch.EPollPort.-$$Nest$fgetqueue(r4)     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                java.lang.Object r4 = r4.take()     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                sun.nio.ch.EPollPort$Event r4 = (sun.nio.ch.EPollPort.Event) r4     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                sun.nio.ch.EPollPort r5 = sun.nio.ch.EPollPort.this     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                sun.nio.ch.EPollPort$Event r5 = sun.nio.ch.EPollPort.-$$Nest$fgetNEED_TO_POLL(r5)     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                if (r4 != r5) goto L49
                sun.nio.ch.EPollPort$Event r4 = r6.poll()     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                goto L49
            L2f:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L83 java.lang.InterruptedException -> L9a
                sun.nio.ch.EPollPort r0 = sun.nio.ch.EPollPort.this
                int r0 = r0.threadExit(r6, r1)
                if (r0 != 0) goto L6a
                sun.nio.ch.EPollPort r0 = sun.nio.ch.EPollPort.this
                boolean r0 = r0.isShutdown()
                if (r0 == 0) goto L6a
            L43:
                sun.nio.ch.EPollPort r0 = sun.nio.ch.EPollPort.this
                sun.nio.ch.EPollPort.-$$Nest$mimplClose(r0)
                goto L6a
            L49:
                sun.nio.ch.EPollPort r5 = sun.nio.ch.EPollPort.this     // Catch: java.lang.Throwable -> L83
                sun.nio.ch.EPollPort$Event r5 = sun.nio.ch.EPollPort.-$$Nest$fgetEXECUTE_TASK_OR_SHUTDOWN(r5)     // Catch: java.lang.Throwable -> L83
                if (r4 != r5) goto L70
                sun.nio.ch.EPollPort r4 = sun.nio.ch.EPollPort.this     // Catch: java.lang.Throwable -> L83
                java.lang.Runnable r4 = r4.pollTask()     // Catch: java.lang.Throwable -> L83
                if (r4 != 0) goto L6b
                sun.nio.ch.EPollPort r0 = sun.nio.ch.EPollPort.this
                int r0 = r0.threadExit(r6, r1)
                if (r0 != 0) goto L6a
                sun.nio.ch.EPollPort r0 = sun.nio.ch.EPollPort.this
                boolean r0 = r0.isShutdown()
                if (r0 == 0) goto L6a
                goto L43
            L6a:
                return
            L6b:
                r4.run()     // Catch: java.lang.Throwable -> L80
                r4 = 1
                goto Lc
            L70:
                sun.nio.ch.Port$PollableChannel r5 = r4.channel()     // Catch: java.lang.RuntimeException -> L7c java.lang.Error -> L7e java.lang.Throwable -> L83
                int r4 = r4.events()     // Catch: java.lang.RuntimeException -> L7c java.lang.Error -> L7e java.lang.Throwable -> L83
                r5.onEvent(r4, r3)     // Catch: java.lang.RuntimeException -> L7c java.lang.Error -> L7e java.lang.Throwable -> L83
                goto Lb
            L7c:
                r0 = move-exception
                throw r0     // Catch: java.lang.Throwable -> L80
            L7e:
                r0 = move-exception
                throw r0     // Catch: java.lang.Throwable -> L80
            L80:
                r0 = move-exception
                r1 = 1
                goto L84
            L83:
                r0 = move-exception
            L84:
                sun.nio.ch.EPollPort r2 = sun.nio.ch.EPollPort.this
                int r1 = r2.threadExit(r6, r1)
                if (r1 != 0) goto L99
                sun.nio.ch.EPollPort r1 = sun.nio.ch.EPollPort.this
                boolean r1 = r1.isShutdown()
                if (r1 == 0) goto L99
                sun.nio.ch.EPollPort r1 = sun.nio.ch.EPollPort.this
                sun.nio.ch.EPollPort.-$$Nest$mimplClose(r1)
            L99:
                throw r0
            L9a:
                goto Lb
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.EPollPort.EventHandlerTask.run():void");
        }
    }
}
