package java.util.concurrent;

import java.util.Deque;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BlockingDeque extends BlockingQueue, Deque {
    boolean add(Object obj);

    void addFirst(Object obj);

    void addLast(Object obj);

    boolean contains(Object obj);

    Object element();

    Iterator iterator();

    boolean offer(Object obj);

    boolean offer(Object obj, long j, TimeUnit timeUnit) throws InterruptedException;

    boolean offerFirst(Object obj);

    boolean offerFirst(Object obj, long j, TimeUnit timeUnit) throws InterruptedException;

    boolean offerLast(Object obj);

    boolean offerLast(Object obj, long j, TimeUnit timeUnit) throws InterruptedException;

    Object peek();

    Object poll();

    Object poll(long j, TimeUnit timeUnit) throws InterruptedException;

    Object pollFirst(long j, TimeUnit timeUnit) throws InterruptedException;

    Object pollLast(long j, TimeUnit timeUnit) throws InterruptedException;

    void push(Object obj);

    void put(Object obj) throws InterruptedException;

    void putFirst(Object obj) throws InterruptedException;

    void putLast(Object obj) throws InterruptedException;

    Object remove();

    boolean remove(Object obj);

    boolean removeFirstOccurrence(Object obj);

    boolean removeLastOccurrence(Object obj);

    int size();

    Object take() throws InterruptedException;

    Object takeFirst() throws InterruptedException;

    Object takeLast() throws InterruptedException;
}
