package java.util.concurrent;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface TransferQueue extends BlockingQueue {
    int getWaitingConsumerCount();

    boolean hasWaitingConsumer();

    void transfer(Object obj) throws InterruptedException;

    boolean tryTransfer(Object obj);

    boolean tryTransfer(Object obj, long j, TimeUnit timeUnit) throws InterruptedException;
}
