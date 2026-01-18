package java.util.concurrent;

import java.util.Collection;
import java.util.Queue;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BlockingQueue extends Queue {
    boolean add(Object obj);

    boolean contains(Object obj);

    int drainTo(Collection collection);

    int drainTo(Collection collection, int i);

    boolean offer(Object obj);

    boolean offer(Object obj, long j, TimeUnit timeUnit) throws InterruptedException;

    Object poll(long j, TimeUnit timeUnit) throws InterruptedException;

    void put(Object obj) throws InterruptedException;

    int remainingCapacity();

    boolean remove(Object obj);

    Object take() throws InterruptedException;
}
