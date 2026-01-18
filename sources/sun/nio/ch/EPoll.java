package sun.nio.ch;

import java.io.IOException;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class EPoll {
    static final int EPOLLIN = 1;
    static final int EPOLLONESHOT = 1073741824;
    static final int EPOLLOUT = 4;
    static final int EPOLL_CTL_ADD = 1;
    static final int EPOLL_CTL_DEL = 2;
    static final int EPOLL_CTL_MOD = 3;
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final int SIZEOF_EPOLLEVENT = eventSize();
    private static final int OFFSETOF_EVENTS = eventsOffset();
    private static final int OFFSETOF_FD = dataOffset();

    static native int create() throws IOException;

    static native int ctl(int i, int i2, int i3, int i4);

    private static native int dataOffset();

    private static native int eventSize();

    private static native int eventsOffset();

    static native int wait(int i, long j, int i2, int i3) throws IOException;

    private EPoll() {
    }

    static {
        IOUtil.load();
    }

    static long allocatePollArray(int i) {
        return unsafe.allocateMemory(i * SIZEOF_EPOLLEVENT);
    }

    static void freePollArray(long j) {
        unsafe.freeMemory(j);
    }

    static long getEvent(long j, int i) {
        return j + (SIZEOF_EPOLLEVENT * i);
    }

    static int getDescriptor(long j) {
        return unsafe.getInt(j + OFFSETOF_FD);
    }

    static int getEvents(long j) {
        return unsafe.getInt(j + OFFSETOF_EVENTS);
    }
}
