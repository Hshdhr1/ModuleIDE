package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
interface Sink extends Consumer {
    void accept(double d);

    void accept(int i);

    void accept(long j);

    void begin(long j);

    boolean cancellationRequested();

    void end();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static void $default$begin(Sink _this, long j) {
        }

        public static boolean $default$cancellationRequested(Sink _this) {
            return false;
        }

        public static void $default$end(Sink _this) {
        }

        public static void $default$accept(Sink _this, int i) {
            throw new IllegalStateException("called wrong accept method");
        }

        public static void $default$accept(Sink _this, long j) {
            throw new IllegalStateException("called wrong accept method");
        }

        public static void $default$accept(Sink _this, double d) {
            throw new IllegalStateException("called wrong accept method");
        }
    }

    public interface OfInt extends Sink, IntConsumer {
        void accept(int i);

        void accept(Integer num);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$accept(OfInt _this, Object obj) {
                _this.accept((Integer) obj);
            }

            public static void $default$accept(OfInt _this, Integer num) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Sink.OfInt.accept(Integer)");
                }
                _this.accept(num.intValue());
            }
        }
    }

    public interface OfLong extends Sink, LongConsumer {
        void accept(long j);

        void accept(Long l);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$accept(OfLong _this, Object obj) {
                _this.accept((Long) obj);
            }

            public static void $default$accept(OfLong _this, Long l) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Sink.OfLong.accept(Long)");
                }
                _this.accept(l.longValue());
            }
        }
    }

    public interface OfDouble extends Sink, DoubleConsumer {
        void accept(double d);

        void accept(Double d);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$accept(OfDouble _this, Object obj) {
                _this.accept((Double) obj);
            }

            public static void $default$accept(OfDouble _this, Double d) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Sink.OfDouble.accept(Double)");
                }
                _this.accept(d.doubleValue());
            }
        }
    }

    public static abstract class ChainedReference implements Sink {
        protected final Sink downstream;

        public /* synthetic */ void accept(double d) {
            -CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            -CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            -CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public ChainedReference(Sink sink) {
            sink.getClass();
            this.downstream = sink;
        }

        public void begin(long j) {
            this.downstream.begin(j);
        }

        public void end() {
            this.downstream.end();
        }

        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }
    }

    public static abstract class ChainedInt implements OfInt {
        protected final Sink downstream;

        public /* synthetic */ void accept(double d) {
            -CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(long j) {
            -CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Integer num) {
            OfInt.-CC.$default$accept((OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public ChainedInt(Sink sink) {
            sink.getClass();
            this.downstream = sink;
        }

        public void begin(long j) {
            this.downstream.begin(j);
        }

        public void end() {
            this.downstream.end();
        }

        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }
    }

    public static abstract class ChainedLong implements OfLong {
        protected final Sink downstream;

        public /* synthetic */ void accept(double d) {
            -CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            -CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(Long l) {
            OfLong.-CC.$default$accept((OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public ChainedLong(Sink sink) {
            sink.getClass();
            this.downstream = sink;
        }

        public void begin(long j) {
            this.downstream.begin(j);
        }

        public void end() {
            this.downstream.end();
        }

        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }
    }

    public static abstract class ChainedDouble implements OfDouble {
        protected final Sink downstream;

        public /* synthetic */ void accept(int i) {
            -CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            -CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Double d) {
            OfDouble.-CC.$default$accept((OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public ChainedDouble(Sink sink) {
            sink.getClass();
            this.downstream = sink;
        }

        public void begin(long j) {
            this.downstream.begin(j);
        }

        public void end() {
            this.downstream.end();
        }

        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }
    }
}
