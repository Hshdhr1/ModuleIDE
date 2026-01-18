package java.util.concurrent;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Flow {
    static final int DEFAULT_BUFFER_SIZE = 256;

    public interface Processor extends Subscriber, Publisher {
    }

    @FunctionalInterface
    public interface Publisher {
        void subscribe(Subscriber subscriber);
    }

    public interface Subscriber {
        void onComplete();

        void onError(Throwable th);

        void onNext(Object obj);

        void onSubscribe(Subscription subscription);
    }

    public interface Subscription {
        void cancel();

        void request(long j);
    }

    public static int defaultBufferSize() {
        return 256;
    }

    private Flow() {
    }
}
