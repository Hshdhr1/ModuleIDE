package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.StreamSpliterators;
import java.util.stream.Streams;
import java.util.stream.WhileOps;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface LongStream extends BaseStream {
    boolean allMatch(LongPredicate longPredicate);

    boolean anyMatch(LongPredicate longPredicate);

    DoubleStream asDoubleStream();

    OptionalDouble average();

    Stream boxed();

    Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer);

    long count();

    LongStream distinct();

    LongStream dropWhile(LongPredicate longPredicate);

    LongStream filter(LongPredicate longPredicate);

    OptionalLong findAny();

    OptionalLong findFirst();

    LongStream flatMap(LongFunction longFunction);

    void forEach(LongConsumer longConsumer);

    void forEachOrdered(LongConsumer longConsumer);

    PrimitiveIterator.OfLong iterator();

    LongStream limit(long j);

    LongStream map(LongUnaryOperator longUnaryOperator);

    DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction);

    IntStream mapToInt(LongToIntFunction longToIntFunction);

    Stream mapToObj(LongFunction longFunction);

    OptionalLong max();

    OptionalLong min();

    boolean noneMatch(LongPredicate longPredicate);

    LongStream parallel();

    LongStream peek(LongConsumer longConsumer);

    long reduce(long j, LongBinaryOperator longBinaryOperator);

    OptionalLong reduce(LongBinaryOperator longBinaryOperator);

    LongStream sequential();

    LongStream skip(long j);

    LongStream sorted();

    Spliterator.OfLong spliterator();

    long sum();

    LongSummaryStatistics summaryStatistics();

    LongStream takeWhile(LongPredicate longPredicate);

    long[] toArray();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ Iterator $default$iterator(LongStream _this) {
            return _this.iterator();
        }

        public static /* bridge */ /* synthetic */ BaseStream $default$parallel(LongStream _this) {
            return _this.parallel();
        }

        public static /* bridge */ /* synthetic */ BaseStream $default$sequential(LongStream _this) {
            return _this.sequential();
        }

        public static /* bridge */ /* synthetic */ Spliterator $default$spliterator(LongStream _this) {
            return _this.spliterator();
        }

        public static LongStream $default$takeWhile(LongStream _this, LongPredicate longPredicate) {
            longPredicate.getClass();
            return (LongStream) StreamSupport.longStream(new WhileOps.UnorderedWhileSpliterator.OfLong.Taking(_this.spliterator(), true, longPredicate), _this.isParallel()).onClose(new LongStream$$ExternalSyntheticLambda1(_this));
        }

        public static LongStream $default$dropWhile(LongStream _this, LongPredicate longPredicate) {
            longPredicate.getClass();
            return (LongStream) StreamSupport.longStream(new WhileOps.UnorderedWhileSpliterator.OfLong.Dropping(_this.spliterator(), true, longPredicate), _this.isParallel()).onClose(new LongStream$$ExternalSyntheticLambda1(_this));
        }

        public static Builder builder() {
            return new Streams.LongStreamBuilderImpl();
        }

        public static LongStream empty() {
            return StreamSupport.longStream(Spliterators.emptyLongSpliterator(), false);
        }

        public static LongStream of(long j) {
            return StreamSupport.longStream(new Streams.LongStreamBuilderImpl(j), false);
        }

        public static LongStream of(long... jArr) {
            return Arrays.stream(jArr);
        }

        public static LongStream iterate(long j, LongUnaryOperator longUnaryOperator) {
            longUnaryOperator.getClass();
            return StreamSupport.longStream(new 1(Long.MAX_VALUE, 1296, longUnaryOperator, j), false);
        }

        public static LongStream iterate(long j, LongPredicate longPredicate, LongUnaryOperator longUnaryOperator) {
            longUnaryOperator.getClass();
            longPredicate.getClass();
            return StreamSupport.longStream(new 2(Long.MAX_VALUE, 1296, longUnaryOperator, j, longPredicate), false);
        }

        public static LongStream generate(LongSupplier longSupplier) {
            longSupplier.getClass();
            return StreamSupport.longStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfLong(Long.MAX_VALUE, longSupplier), false);
        }

        public static LongStream range(long j, long j2) {
            if (j >= j2) {
                return empty();
            }
            long j3 = j2 - j;
            if (j3 >= 0) {
                return StreamSupport.longStream(new Streams.RangeLongSpliterator(j, j2, false), false);
            }
            long m = LongStream$$ExternalSyntheticBackport0.m(j3, 2L) + j + 1;
            return concat(range(j, m), range(m, j2));
        }

        public static LongStream rangeClosed(long j, long j2) {
            if (j > j2) {
                return empty();
            }
            long j3 = j2 - j;
            if (j3 + 1 > 0) {
                return StreamSupport.longStream(new Streams.RangeLongSpliterator(j, j2, true), false);
            }
            long m = LongStream$$ExternalSyntheticBackport0.m(j3, 2L) + j + 1;
            return concat(range(j, m), rangeClosed(m, j2));
        }

        public static LongStream concat(LongStream longStream, LongStream longStream2) {
            longStream.getClass();
            longStream2.getClass();
            return (LongStream) StreamSupport.longStream(new Streams.ConcatSpliterator.OfLong(longStream.spliterator(), longStream2.spliterator()), longStream.isParallel() || longStream2.isParallel()).onClose(Streams.composedClose(longStream, longStream2));
        }
    }

    class 1 extends Spliterators.AbstractLongSpliterator {
        long prev;
        boolean started;
        final /* synthetic */ LongUnaryOperator val$f;
        final /* synthetic */ long val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, LongUnaryOperator longUnaryOperator, long j2) {
            super(j, i);
            this.val$f = longUnaryOperator;
            this.val$seed = j2;
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            long j;
            longConsumer.getClass();
            if (this.started) {
                j = this.val$f.applyAsLong(this.prev);
            } else {
                j = this.val$seed;
                this.started = true;
            }
            this.prev = j;
            longConsumer.accept(j);
            return true;
        }
    }

    class 2 extends Spliterators.AbstractLongSpliterator {
        boolean finished;
        long prev;
        boolean started;
        final /* synthetic */ LongPredicate val$hasNext;
        final /* synthetic */ LongUnaryOperator val$next;
        final /* synthetic */ long val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(long j, int i, LongUnaryOperator longUnaryOperator, long j2, LongPredicate longPredicate) {
            super(j, i);
            this.val$next = longUnaryOperator;
            this.val$seed = j2;
            this.val$hasNext = longPredicate;
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            long j;
            longConsumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                j = this.val$next.applyAsLong(this.prev);
            } else {
                j = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(j)) {
                this.finished = true;
                return false;
            }
            this.prev = j;
            longConsumer.accept(j);
            return true;
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            longConsumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            long applyAsLong = this.started ? this.val$next.applyAsLong(this.prev) : this.val$seed;
            while (this.val$hasNext.test(applyAsLong)) {
                longConsumer.accept(applyAsLong);
                applyAsLong = this.val$next.applyAsLong(applyAsLong);
            }
        }
    }

    public interface Builder extends LongConsumer {
        void accept(long j);

        Builder add(long j);

        LongStream build();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static Builder $default$add(Builder _this, long j) {
                _this.accept(j);
                return _this;
            }
        }
    }
}
