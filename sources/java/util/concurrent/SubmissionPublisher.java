package java.util.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class SubmissionPublisher implements Flow.Publisher, AutoCloseable {
    private static final Executor ASYNC_POOL;
    static final int BUFFER_CAPACITY_LIMIT = 1073741824;
    static final int INITIAL_CAPACITY = 32;
    BufferedSubscription clients;
    volatile boolean closed;
    volatile Throwable closedException;
    final Executor executor;
    final int maxBufferCapacity;
    final BiConsumer onNextHandler;
    Thread owner;
    boolean subscribed;

    static final int roundCapacity(int i) {
        int i2 = i - 1;
        int i3 = i2 | (i2 >>> 1);
        int i4 = i3 | (i3 >>> 2);
        int i5 = i4 | (i4 >>> 4);
        int i6 = i5 | (i5 >>> 8);
        int i7 = i6 | (i6 >>> 16);
        if (i7 <= 0) {
            return 1;
        }
        if (i7 >= 1073741824) {
            return 1073741824;
        }
        return i7 + 1;
    }

    static {
        ASYNC_POOL = ForkJoinPool.getCommonPoolParallelism() > 1 ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();
    }

    private static final class ThreadPerTaskExecutor implements Executor {
        ThreadPerTaskExecutor() {
        }

        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    public SubmissionPublisher(Executor executor, int i, BiConsumer biConsumer) {
        executor.getClass();
        if (i <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.executor = executor;
        this.onNextHandler = biConsumer;
        this.maxBufferCapacity = roundCapacity(i);
    }

    public SubmissionPublisher(Executor executor, int i) {
        this(executor, i, null);
    }

    public SubmissionPublisher() {
        this(ASYNC_POOL, Flow.defaultBufferSize(), null);
    }

    public void subscribe(Flow.Subscriber subscriber) {
        subscriber.getClass();
        int i = this.maxBufferCapacity;
        BufferedSubscription bufferedSubscription = new BufferedSubscription(subscriber, this.executor, this.onNextHandler, new Object[i < 32 ? i : 32], i);
        synchronized (this) {
            if (!this.subscribed) {
                this.subscribed = true;
                this.owner = Thread.currentThread();
            }
            BufferedSubscription bufferedSubscription2 = this.clients;
            BufferedSubscription bufferedSubscription3 = null;
            while (true) {
                if (bufferedSubscription2 == null) {
                    bufferedSubscription.onSubscribe();
                    Throwable th = this.closedException;
                    if (th != null) {
                        bufferedSubscription.onError(th);
                    } else if (this.closed) {
                        bufferedSubscription.onComplete();
                    } else if (bufferedSubscription3 == null) {
                        this.clients = bufferedSubscription;
                    } else {
                        bufferedSubscription3.next = bufferedSubscription;
                    }
                } else {
                    BufferedSubscription bufferedSubscription4 = bufferedSubscription2.next;
                    if (bufferedSubscription2.isClosed()) {
                        bufferedSubscription2.next = null;
                        if (bufferedSubscription3 == null) {
                            this.clients = bufferedSubscription4;
                        } else {
                            bufferedSubscription3.next = bufferedSubscription4;
                        }
                    } else {
                        if (subscriber.equals(bufferedSubscription2.subscriber)) {
                            bufferedSubscription2.onError(new IllegalStateException("Duplicate subscribe"));
                            break;
                        }
                        bufferedSubscription3 = bufferedSubscription2;
                    }
                    bufferedSubscription2 = bufferedSubscription4;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0062 A[LOOP:0: B:21:0x0025->B:29:0x0062, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0045 A[EDGE_INSN: B:30:0x0045->B:31:0x0045 BREAK  A[LOOP:0: B:21:0x0025->B:29:0x0062], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int doOffer(java.lang.Object r13, long r14, java.util.function.BiPredicate r16) {
        /*
            r12 = this;
            r13.getClass()
            monitor-enter(r12)
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L67
            java.util.concurrent.SubmissionPublisher$BufferedSubscription r2 = r12.clients     // Catch: java.lang.Throwable -> L67
            java.lang.Thread r3 = r12.owner     // Catch: java.lang.Throwable -> L67
            r4 = 1
            r9 = 0
            if (r3 == r0) goto L12
            r0 = 1
            goto L13
        L12:
            r0 = 0
        L13:
            r5 = 0
            if (r0 == 0) goto L1a
            if (r3 == 0) goto L1a
            r12.owner = r5     // Catch: java.lang.Throwable -> L67
        L1a:
            if (r2 != 0) goto L21
            boolean r0 = r12.closed     // Catch: java.lang.Throwable -> L67
            r9 = r0
            r0 = 0
            goto L56
        L21:
            r6 = r5
            r7 = r6
            r3 = 0
            r8 = 0
        L25:
            java.util.concurrent.SubmissionPublisher$BufferedSubscription r10 = r2.next     // Catch: java.lang.Throwable -> L67
            int r11 = r2.offer(r13, r0)     // Catch: java.lang.Throwable -> L67
            if (r11 != 0) goto L38
            r2.nextRetry = r5     // Catch: java.lang.Throwable -> L67
            if (r6 != 0) goto L33
            r7 = r2
            goto L35
        L33:
            r6.nextRetry = r2     // Catch: java.lang.Throwable -> L67
        L35:
            r6 = r2
            r2 = r7
            goto L42
        L38:
            if (r11 >= 0) goto L3d
            r2 = r7
            r8 = 1
            goto L42
        L3d:
            r2 = r7
            if (r11 <= r3) goto L42
            r7 = r11
            goto L43
        L42:
            r7 = r3
        L43:
            if (r10 != 0) goto L62
            if (r2 != 0) goto L4c
            if (r8 == 0) goto L4a
            goto L4c
        L4a:
            r0 = r7
            goto L56
        L4c:
            r1 = r12
            r3 = r14
            r5 = r16
            r6 = r2
            r2 = r13
            int r0 = r1.retryOffer(r2, r3, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L67
        L56:
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L67
            if (r9 != 0) goto L5a
            return r0
        L5a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Closed"
            r0.<init>(r1)
            throw r0
        L62:
            r1 = r2
            r3 = r7
            r2 = r10
            r7 = r1
            goto L25
        L67:
            r0 = move-exception
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L67
            goto L6b
        L6a:
            throw r0
        L6b:
            goto L6a
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.SubmissionPublisher.doOffer(java.lang.Object, long, java.util.function.BiPredicate):int");
    }

    private int retryOffer(Object obj, long j, BiPredicate biPredicate, BufferedSubscription bufferedSubscription, int i, boolean z) {
        while (bufferedSubscription != null) {
            BufferedSubscription bufferedSubscription2 = bufferedSubscription.nextRetry;
            bufferedSubscription.nextRetry = null;
            if (j > 0) {
                bufferedSubscription.awaitSpace(j);
            }
            int retryOffer = bufferedSubscription.retryOffer(obj);
            if (retryOffer == 0 && biPredicate != null && biPredicate.test(bufferedSubscription.subscriber, obj)) {
                retryOffer = bufferedSubscription.retryOffer(obj);
            }
            if (retryOffer == 0) {
                i = i >= 0 ? -1 : i - 1;
            } else if (retryOffer < 0) {
                z = true;
            } else if (i >= 0 && retryOffer > i) {
                i = retryOffer;
            }
            bufferedSubscription = bufferedSubscription2;
        }
        if (z) {
            cleanAndCount();
        }
        return i;
    }

    private int cleanAndCount() {
        BufferedSubscription bufferedSubscription = this.clients;
        int i = 0;
        BufferedSubscription bufferedSubscription2 = null;
        while (bufferedSubscription != null) {
            BufferedSubscription bufferedSubscription3 = bufferedSubscription.next;
            if (bufferedSubscription.isClosed()) {
                bufferedSubscription.next = null;
                if (bufferedSubscription2 == null) {
                    this.clients = bufferedSubscription3;
                } else {
                    bufferedSubscription2.next = bufferedSubscription3;
                }
            } else {
                i++;
                bufferedSubscription2 = bufferedSubscription;
            }
            bufferedSubscription = bufferedSubscription3;
        }
        return i;
    }

    public int submit(Object obj) {
        return doOffer(obj, Long.MAX_VALUE, null);
    }

    public int offer(Object obj, BiPredicate biPredicate) {
        return doOffer(obj, 0L, biPredicate);
    }

    public int offer(Object obj, long j, TimeUnit timeUnit, BiPredicate biPredicate) {
        long nanos = timeUnit.toNanos(j);
        if (nanos == Long.MAX_VALUE) {
            nanos--;
        }
        return doOffer(obj, nanos, biPredicate);
    }

    public void close() {
        BufferedSubscription bufferedSubscription;
        if (this.closed) {
            return;
        }
        synchronized (this) {
            bufferedSubscription = this.clients;
            this.clients = null;
            this.owner = null;
            this.closed = true;
        }
        while (bufferedSubscription != null) {
            BufferedSubscription bufferedSubscription2 = bufferedSubscription.next;
            bufferedSubscription.next = null;
            bufferedSubscription.onComplete();
            bufferedSubscription = bufferedSubscription2;
        }
    }

    public void closeExceptionally(Throwable th) {
        BufferedSubscription bufferedSubscription;
        th.getClass();
        if (this.closed) {
            return;
        }
        synchronized (this) {
            bufferedSubscription = this.clients;
            if (!this.closed) {
                this.closedException = th;
                this.clients = null;
                this.owner = null;
                this.closed = true;
            }
        }
        while (bufferedSubscription != null) {
            BufferedSubscription bufferedSubscription2 = bufferedSubscription.next;
            bufferedSubscription.next = null;
            bufferedSubscription.onError(th);
            bufferedSubscription = bufferedSubscription2;
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public Throwable getClosedException() {
        return this.closedException;
    }

    public boolean hasSubscribers() {
        boolean z;
        synchronized (this) {
            BufferedSubscription bufferedSubscription = this.clients;
            while (true) {
                if (bufferedSubscription == null) {
                    z = false;
                    break;
                }
                BufferedSubscription bufferedSubscription2 = bufferedSubscription.next;
                if (!bufferedSubscription.isClosed()) {
                    z = true;
                    break;
                }
                bufferedSubscription.next = null;
                this.clients = bufferedSubscription2;
                bufferedSubscription = bufferedSubscription2;
            }
        }
        return z;
    }

    public int getNumberOfSubscribers() {
        int cleanAndCount;
        synchronized (this) {
            cleanAndCount = cleanAndCount();
        }
        return cleanAndCount;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public int getMaxBufferCapacity() {
        return this.maxBufferCapacity;
    }

    public List getSubscribers() {
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            BufferedSubscription bufferedSubscription = this.clients;
            BufferedSubscription bufferedSubscription2 = null;
            while (bufferedSubscription != null) {
                BufferedSubscription bufferedSubscription3 = bufferedSubscription.next;
                if (bufferedSubscription.isClosed()) {
                    bufferedSubscription.next = null;
                    if (bufferedSubscription2 == null) {
                        this.clients = bufferedSubscription3;
                    } else {
                        bufferedSubscription2.next = bufferedSubscription3;
                    }
                } else {
                    arrayList.add(bufferedSubscription.subscriber);
                    bufferedSubscription2 = bufferedSubscription;
                }
                bufferedSubscription = bufferedSubscription3;
            }
        }
        return arrayList;
    }

    public boolean isSubscribed(Flow.Subscriber subscriber) {
        subscriber.getClass();
        if (this.closed) {
            return false;
        }
        synchronized (this) {
            BufferedSubscription bufferedSubscription = this.clients;
            BufferedSubscription bufferedSubscription2 = null;
            while (bufferedSubscription != null) {
                BufferedSubscription bufferedSubscription3 = bufferedSubscription.next;
                if (bufferedSubscription.isClosed()) {
                    bufferedSubscription.next = null;
                    if (bufferedSubscription2 == null) {
                        this.clients = bufferedSubscription3;
                    } else {
                        bufferedSubscription2.next = bufferedSubscription3;
                    }
                } else {
                    if (subscriber.equals(bufferedSubscription.subscriber)) {
                        return true;
                    }
                    bufferedSubscription2 = bufferedSubscription;
                }
                bufferedSubscription = bufferedSubscription3;
            }
            return false;
        }
    }

    public long estimateMinimumDemand() {
        long j;
        boolean z;
        synchronized (this) {
            BufferedSubscription bufferedSubscription = this.clients;
            j = Long.MAX_VALUE;
            z = false;
            BufferedSubscription bufferedSubscription2 = null;
            while (bufferedSubscription != null) {
                BufferedSubscription bufferedSubscription3 = bufferedSubscription.next;
                int estimateLag = bufferedSubscription.estimateLag();
                if (estimateLag < 0) {
                    bufferedSubscription.next = null;
                    if (bufferedSubscription2 == null) {
                        this.clients = bufferedSubscription3;
                    } else {
                        bufferedSubscription2.next = bufferedSubscription3;
                    }
                } else {
                    long j2 = bufferedSubscription.demand - estimateLag;
                    if (j2 < j) {
                        j = j2;
                    }
                    z = true;
                    bufferedSubscription2 = bufferedSubscription;
                }
                bufferedSubscription = bufferedSubscription3;
            }
        }
        if (z) {
            return j;
        }
        return 0L;
    }

    public int estimateMaximumLag() {
        int i;
        synchronized (this) {
            BufferedSubscription bufferedSubscription = this.clients;
            i = 0;
            BufferedSubscription bufferedSubscription2 = null;
            while (bufferedSubscription != null) {
                BufferedSubscription bufferedSubscription3 = bufferedSubscription.next;
                int estimateLag = bufferedSubscription.estimateLag();
                if (estimateLag < 0) {
                    bufferedSubscription.next = null;
                    if (bufferedSubscription2 == null) {
                        this.clients = bufferedSubscription3;
                    } else {
                        bufferedSubscription2.next = bufferedSubscription3;
                    }
                } else {
                    if (estimateLag > i) {
                        i = estimateLag;
                    }
                    bufferedSubscription2 = bufferedSubscription;
                }
                bufferedSubscription = bufferedSubscription3;
            }
        }
        return i;
    }

    public CompletableFuture consume(Consumer consumer) {
        consumer.getClass();
        CompletableFuture completableFuture = new CompletableFuture();
        subscribe(new ConsumerSubscriber(completableFuture, consumer));
        return completableFuture;
    }

    static final class ConsumerSubscriber implements Flow.Subscriber {
        final Consumer consumer;
        final CompletableFuture status;
        Flow.Subscription subscription;

        ConsumerSubscriber(CompletableFuture completableFuture, Consumer consumer) {
            this.status = completableFuture;
            this.consumer = consumer;
        }

        public final void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.status.whenComplete((BiConsumer) new SubmissionPublisher$ConsumerSubscriber$$ExternalSyntheticLambda0(subscription));
            if (this.status.isDone()) {
                return;
            }
            subscription.request(Long.MAX_VALUE);
        }

        static /* synthetic */ void lambda$onSubscribe$0(Flow.Subscription subscription, Void r1, Throwable th) {
            subscription.cancel();
        }

        public final void onError(Throwable th) {
            this.status.completeExceptionally(th);
        }

        public final void onComplete() {
            this.status.complete(null);
        }

        public final void onNext(Object obj) {
            try {
                this.consumer.accept(obj);
            } catch (Throwable th) {
                this.subscription.cancel();
                this.status.completeExceptionally(th);
            }
        }
    }

    static final class ConsumerTask extends ForkJoinTask implements Runnable, CompletableFuture.AsynchronousCompletionTask {
        final BufferedSubscription consumer;

        public final Void getRawResult() {
            return null;
        }

        public final void setRawResult(Void r1) {
        }

        ConsumerTask(BufferedSubscription bufferedSubscription) {
            this.consumer = bufferedSubscription;
        }

        public final boolean exec() {
            this.consumer.consume();
            return false;
        }

        public final void run() {
            this.consumer.consume();
        }
    }

    static final class BufferedSubscription implements Flow.Subscription, ForkJoinPool.ManagedBlocker {
        static final int ACTIVE = 2;
        static final int CLOSED = 1;
        static final int COMPLETE = 16;
        static final VarHandle CTL;
        static final VarHandle DEMAND;
        static final int ERROR = 8;
        static final long INTERRUPTED = -1;
        static final int OPEN = 64;
        static final VarHandle QA;
        static final int REQS = 4;
        static final int RUN = 32;
        Object[] array;
        volatile int ctl;
        volatile long demand;
        Executor executor;
        int head;
        final int maxCapacity;
        BufferedSubscription next;
        BufferedSubscription nextRetry;
        final BiConsumer onNextHandler;
        Throwable pendingError;
        final Flow.Subscriber subscriber;
        int tail;
        long timeout;
        Thread waiter;
        volatile int waiting;

        BufferedSubscription(Flow.Subscriber subscriber, Executor executor, BiConsumer biConsumer, Object[] objArr, int i) {
            this.subscriber = subscriber;
            this.executor = executor;
            this.onNextHandler = biConsumer;
            this.array = objArr;
            this.maxCapacity = i;
        }

        final boolean weakCasCtl(int i, int i2) {
            SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        final int getAndBitwiseOrCtl(int i) {
            SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return 0;
        }

        final long subtractDemand(int i) {
            long j = -i;
            SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return j;
        }

        final boolean casDemand(long j, long j2) {
            SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        final boolean isClosed() {
            return (this.ctl & 1) != 0;
        }

        final int estimateLag() {
            int i = this.ctl;
            int i2 = this.tail - this.head;
            if ((i & 1) != 0) {
                return -1;
            }
            if (i2 < 0) {
                return 0;
            }
            return i2;
        }

        final int offer(Object obj, boolean z) {
            boolean z2;
            Object[] objArr = this.array;
            int i = 0;
            int length = objArr == null ? 0 : objArr.length;
            int i2 = this.tail;
            int i3 = i2 + 1;
            int i4 = i3 - this.head;
            if (length > 0) {
                if (i4 >= length && length < this.maxCapacity) {
                    z2 = growAndOffer(obj, objArr, i2);
                } else if (i4 >= length || z) {
                    SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    z2 = false;
                } else {
                    SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    z2 = true;
                }
                if (z2) {
                    this.tail = i3;
                    i = i4;
                }
            }
            return startOnOffer(i);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0015  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0014 A[RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final boolean growAndOffer(java.lang.Object r4, java.lang.Object[] r5, int r6) {
            /*
                r3 = this;
                r0 = 0
                r1 = 0
                if (r5 == 0) goto L10
                int r5 = r5.length
                if (r5 <= 0) goto L11
                int r2 = r5 << 1
                if (r2 <= 0) goto L12
                java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch: java.lang.OutOfMemoryError -> Le
                goto L12
            Le:
                goto L12
            L10:
                r5 = 0
            L11:
                r2 = 0
            L12:
                if (r1 != 0) goto L15
                return r0
            L15:
                r0 = 1
                int r2 = r2 - r0
                r6 = r6 & r2
                r1[r6] = r4
                int r5 = r5 - r0
                if (r5 < 0) goto L22
                java.lang.String r4 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
                java.util.concurrent.SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m(r4)
            L22:
                r3.array = r1
                java.lang.invoke.VarHandle.releaseFence()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.SubmissionPublisher.BufferedSubscription.growAndOffer(java.lang.Object, java.lang.Object[], int):boolean");
        }

        final int retryOffer(Object obj) {
            Object[] objArr = this.array;
            if (objArr != null && objArr.length > 0) {
                SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            return startOnOffer(0);
        }

        final int startOnOffer(int i) {
            int i2 = this.ctl;
            if ((i2 & 6) == 4) {
                i2 = getAndBitwiseOrCtl(34);
                if ((i2 & 33) == 0) {
                    tryStart();
                    return i;
                }
            }
            if ((i2 & 1) != 0) {
                return -1;
            }
            return i;
        }

        final void tryStart() {
            try {
                ConsumerTask consumerTask = new ConsumerTask(this);
                Executor executor = this.executor;
                if (executor != null) {
                    executor.execute(consumerTask);
                }
            } catch (Error | RuntimeException e) {
                getAndBitwiseOrCtl(9);
                throw e;
            }
        }

        final void startOnSignal(int i) {
            if ((this.ctl & i) == i || (getAndBitwiseOrCtl(i) & 33) != 0) {
                return;
            }
            tryStart();
        }

        final void onSubscribe() {
            startOnSignal(34);
        }

        final void onComplete() {
            startOnSignal(50);
        }

        final void onError(Throwable th) {
            if (th != null) {
                this.pendingError = th;
            }
            int andBitwiseOrCtl = getAndBitwiseOrCtl(42);
            if ((andBitwiseOrCtl & 1) == 0) {
                if ((andBitwiseOrCtl & 32) == 0) {
                    tryStart();
                    return;
                }
                Object[] objArr = this.array;
                if (objArr != null) {
                    Arrays.fill(objArr, (Object) null);
                }
            }
        }

        public final void cancel() {
            onError(null);
        }

        public final void request(long j) {
            long j2;
            long j3;
            if (j > 0) {
                do {
                    j2 = this.demand;
                    j3 = j2 + j;
                    if (j3 < j2) {
                        j3 = Long.MAX_VALUE;
                    }
                } while (!casDemand(j2, j3));
                startOnSignal(38);
                return;
            }
            onError(new IllegalArgumentException("non-positive subscription request"));
        }

        final void consume() {
            Flow.Subscriber subscriber = this.subscriber;
            if (subscriber == null) {
                return;
            }
            subscribeOnOpen(subscriber);
            long j = this.demand;
            int i = this.head;
            int i2 = this.tail;
            while (true) {
                int i3 = this.ctl;
                if ((i3 & 8) != 0) {
                    closeOnError(subscriber, null);
                    return;
                }
                int takeItems = takeItems(subscriber, j, i);
                if (takeItems > 0) {
                    i += takeItems;
                    this.head = i;
                    j = subtractDemand(takeItems);
                } else {
                    j = this.demand;
                    if (j == 0 && (i3 & 4) != 0) {
                        weakCasCtl(i3, i3 & (-5));
                    } else if (j != 0 && (i3 & 4) == 0) {
                        weakCasCtl(i3, i3 | 4);
                    } else {
                        int i4 = this.tail;
                        if (i2 == i4) {
                            boolean z = i4 == i;
                            if (z && (i3 & 16) != 0) {
                                closeOnComplete(subscriber);
                                return;
                            } else if (z || j == 0) {
                                int i5 = (i3 & 2) != 0 ? 2 : 32;
                                if (weakCasCtl(i3, (i5 ^ (-1)) & i3) && i5 == 32) {
                                    return;
                                }
                            }
                        }
                        i2 = i4;
                    }
                }
            }
        }

        final int takeItems(Flow.Subscriber subscriber, long j, int i) {
            int length;
            Object[] objArr = this.array;
            if (objArr != null && (length = objArr.length) > 0) {
                int i2 = ((length - 1) >>> 3) + 1;
                if (j < i2) {
                    i2 = (int) j;
                }
                if (i2 > 0) {
                    SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    if (this.waiting != 0) {
                        signalWaiter();
                    }
                }
            }
            return 0;
        }

        final boolean consumeNext(Flow.Subscriber subscriber, Object obj) {
            if (subscriber == null) {
                return true;
            }
            try {
                subscriber.onNext(obj);
                return true;
            } catch (Throwable th) {
                handleOnNext(subscriber, th);
                return false;
            }
        }

        final void handleOnNext(Flow.Subscriber subscriber, Throwable th) {
            try {
                BiConsumer biConsumer = this.onNextHandler;
                if (biConsumer != null) {
                    biConsumer.accept(subscriber, th);
                }
            } catch (Throwable unused) {
            }
            closeOnError(subscriber, th);
        }

        final void subscribeOnOpen(Flow.Subscriber subscriber) {
            if ((this.ctl & 64) == 0 && (getAndBitwiseOrCtl(64) & 64) == 0) {
                consumeSubscribe(subscriber);
            }
        }

        final void consumeSubscribe(Flow.Subscriber subscriber) {
            if (subscriber != null) {
                try {
                    subscriber.onSubscribe(this);
                } catch (Throwable th) {
                    closeOnError(subscriber, th);
                }
            }
        }

        final void closeOnComplete(Flow.Subscriber subscriber) {
            if ((1 & getAndBitwiseOrCtl(1)) == 0) {
                consumeComplete(subscriber);
            }
        }

        final void consumeComplete(Flow.Subscriber subscriber) {
            if (subscriber != null) {
                try {
                    subscriber.onComplete();
                } catch (Throwable unused) {
                }
            }
        }

        final void closeOnError(Flow.Subscriber subscriber, Throwable th) {
            if ((getAndBitwiseOrCtl(9) & 1) == 0) {
                if (th == null) {
                    th = this.pendingError;
                }
                this.pendingError = null;
                this.executor = null;
                signalWaiter();
                consumeError(subscriber, th);
            }
        }

        final void consumeError(Flow.Subscriber subscriber, Throwable th) {
            if (th == null || subscriber == null) {
                return;
            }
            try {
                subscriber.onError(th);
            } catch (Throwable unused) {
            }
        }

        final void signalWaiter() {
            this.waiting = 0;
            Thread thread = this.waiter;
            if (thread != null) {
                LockSupport.unpark(thread);
            }
        }

        public final boolean isReleasable() {
            if ((this.ctl & 1) == 0) {
                Object[] objArr = this.array;
                if (objArr == null || objArr.length <= 0) {
                    return false;
                }
                SubmissionPublisher$BufferedSubscription$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            return true;
        }

        final void awaitSpace(long j) {
            if (isReleasable()) {
                return;
            }
            ForkJoinPool.helpAsyncBlocker(this.executor, this);
            if (isReleasable()) {
                return;
            }
            this.timeout = j;
            try {
                ForkJoinPool.managedBlock(this);
            } catch (InterruptedException unused) {
                this.timeout = -1L;
            }
            if (this.timeout == -1) {
                Thread.currentThread().interrupt();
            }
        }

        public final boolean block() {
            long j = this.timeout;
            boolean z = j < Long.MAX_VALUE;
            long nanoTime = z ? System.nanoTime() + j : 0L;
            while (!isReleasable()) {
                if (Thread.interrupted()) {
                    this.timeout = -1L;
                    if (z) {
                        break;
                    }
                } else {
                    if (z) {
                        j = nanoTime - System.nanoTime();
                        if (j <= 0) {
                            break;
                        }
                    }
                    if (this.waiter == null) {
                        this.waiter = Thread.currentThread();
                    } else if (this.waiting == 0) {
                        this.waiting = 1;
                    } else if (z) {
                        LockSupport.parkNanos(this, j);
                    } else {
                        LockSupport.park(this);
                    }
                }
            }
            this.waiter = null;
            this.waiting = 0;
            return true;
        }

        static {
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                CTL = lookup.findVarHandle(BufferedSubscription.class, "ctl", Integer.TYPE);
                DEMAND = lookup.findVarHandle(BufferedSubscription.class, "demand", Long.TYPE);
                QA = MethodHandles.arrayElementVarHandle(Object[].class);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }
}
