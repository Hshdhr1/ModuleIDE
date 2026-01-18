package java.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Deque extends Queue {
    boolean add(Object obj);

    boolean addAll(Collection collection);

    void addFirst(Object obj);

    void addLast(Object obj);

    boolean contains(Object obj);

    Iterator descendingIterator();

    Object element();

    Object getFirst();

    Object getLast();

    Iterator iterator();

    boolean offer(Object obj);

    boolean offerFirst(Object obj);

    boolean offerLast(Object obj);

    Object peek();

    Object peekFirst();

    Object peekLast();

    Object poll();

    Object pollFirst();

    Object pollLast();

    Object pop();

    void push(Object obj);

    Object remove();

    boolean remove(Object obj);

    Object removeFirst();

    boolean removeFirstOccurrence(Object obj);

    Object removeLast();

    boolean removeLastOccurrence(Object obj);

    int size();
}
