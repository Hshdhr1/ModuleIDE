package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.StreamSpliterators;
import java.util.stream.Streams;
import java.util.stream.WhileOps;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface IntStream extends BaseStream {
    boolean allMatch(IntPredicate intPredicate);

    boolean anyMatch(IntPredicate intPredicate);

    DoubleStream asDoubleStream();

    LongStream asLongStream();

    OptionalDouble average();

    Stream boxed();

    Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer);

    long count();

    IntStream distinct();

    IntStream dropWhile(IntPredicate intPredicate);

    IntStream filter(IntPredicate intPredicate);

    OptionalInt findAny();

    OptionalInt findFirst();

    IntStream flatMap(IntFunction intFunction);

    void forEach(IntConsumer intConsumer);

    void forEachOrdered(IntConsumer intConsumer);

    PrimitiveIterator.OfInt iterator();

    IntStream limit(long j);

    IntStream map(IntUnaryOperator intUnaryOperator);

    DoubleStream mapToDouble(IntToDoubleFunction intToDoubleFunction);

    LongStream mapToLong(IntToLongFunction intToLongFunction);

    Stream mapToObj(IntFunction intFunction);

    OptionalInt max();

    OptionalInt min();

    boolean noneMatch(IntPredicate intPredicate);

    IntStream parallel();

    IntStream peek(IntConsumer intConsumer);

    int reduce(int i, IntBinaryOperator intBinaryOperator);

    OptionalInt reduce(IntBinaryOperator intBinaryOperator);

    IntStream sequential();

    IntStream skip(long j);

    IntStream sorted();

    Spliterator.OfInt spliterator();

    int sum();

    IntSummaryStatistics summaryStatistics();

    IntStream takeWhile(IntPredicate intPredicate);

    int[] toArray();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ Iterator $default$iterator(IntStream _this) {
            return _this.iterator();
        }

        public static /* bridge */ /* synthetic */ BaseStream $default$parallel(IntStream _this) {
            return _this.parallel();
        }

        public static /* bridge */ /* synthetic */ BaseStream $default$sequential(IntStream _this) {
            return _this.sequential();
        }

        public static /* bridge */ /* synthetic */ Spliterator $default$spliterator(IntStream _this) {
            return _this.spliterator();
        }

        public static IntStream $default$takeWhile(IntStream _this, IntPredicate intPredicate) {
            intPredicate.getClass();
            return (IntStream) StreamSupport.intStream(new WhileOps.UnorderedWhileSpliterator.OfInt.Taking(_this.spliterator(), true, intPredicate), _this.isParallel()).onClose(new IntStream$$ExternalSyntheticLambda0(_this));
        }

        public static IntStream $default$dropWhile(IntStream _this, IntPredicate intPredicate) {
            intPredicate.getClass();
            return (IntStream) StreamSupport.intStream(new WhileOps.UnorderedWhileSpliterator.OfInt.Dropping(_this.spliterator(), true, intPredicate), _this.isParallel()).onClose(new IntStream$$ExternalSyntheticLambda0(_this));
        }

        public static Builder builder() {
            return new Streams.IntStreamBuilderImpl();
        }

        public static IntStream empty() {
            return StreamSupport.intStream(Spliterators.emptyIntSpliterator(), false);
        }

        public static IntStream of(int i) {
            return StreamSupport.intStream(new Streams.IntStreamBuilderImpl(i), false);
        }

        public static IntStream of(int... iArr) {
            return Arrays.stream(iArr);
        }

        public static IntStream iterate(int i, IntUnaryOperator intUnaryOperator) {
            intUnaryOperator.getClass();
            return StreamSupport.intStream(new 1(Long.MAX_VALUE, 1296, intUnaryOperator, i), false);
        }

        public static IntStream iterate(int i, IntPredicate intPredicate, IntUnaryOperator intUnaryOperator) {
            intUnaryOperator.getClass();
            intPredicate.getClass();
            return StreamSupport.intStream(new 2(Long.MAX_VALUE, 1296, intUnaryOperator, i, intPredicate), false);
        }

        public static IntStream generate(IntSupplier intSupplier) {
            intSupplier.getClass();
            return StreamSupport.intStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfInt(Long.MAX_VALUE, intSupplier), false);
        }

        public static IntStream range(int i, int i2) {
            return i >= i2 ? empty() : StreamSupport.intStream(new Streams.RangeIntSpliterator(i, i2, false), false);
        }

        public static IntStream rangeClosed(int i, int i2) {
            return i > i2 ? empty() : StreamSupport.intStream(new Streams.RangeIntSpliterator(i, i2, true), false);
        }

        public static IntStream concat(IntStream intStream, IntStream intStream2) {
            intStream.getClass();
            intStream2.getClass();
            return (IntStream) StreamSupport.intStream(new Streams.ConcatSpliterator.OfInt(intStream.spliterator(), intStream2.spliterator()), intStream.isParallel() || intStream2.isParallel()).onClose(Streams.composedClose(intStream, intStream2));
        }
    }

    class 1 extends Spliterators.AbstractIntSpliterator {
        int prev;
        boolean started;
        final /* synthetic */ IntUnaryOperator val$f;
        final /* synthetic */ int val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, IntUnaryOperator intUnaryOperator, int i2) {
            super(j, i);
            this.val$f = intUnaryOperator;
            this.val$seed = i2;
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            int i;
            intConsumer.getClass();
            if (this.started) {
                i = this.val$f.applyAsInt(this.prev);
            } else {
                i = this.val$seed;
                this.started = true;
            }
            this.prev = i;
            intConsumer.accept(i);
            return true;
        }
    }

    class 2 extends Spliterators.AbstractIntSpliterator {
        boolean finished;
        int prev;
        boolean started;
        final /* synthetic */ IntPredicate val$hasNext;
        final /* synthetic */ IntUnaryOperator val$next;
        final /* synthetic */ int val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(long j, int i, IntUnaryOperator intUnaryOperator, int i2, IntPredicate intPredicate) {
            super(j, i);
            this.val$next = intUnaryOperator;
            this.val$seed = i2;
            this.val$hasNext = intPredicate;
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            int i;
            intConsumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                i = this.val$next.applyAsInt(this.prev);
            } else {
                i = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(i)) {
                this.finished = true;
                return false;
            }
            this.prev = i;
            intConsumer.accept(i);
            return true;
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            intConsumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            int applyAsInt = this.started ? this.val$next.applyAsInt(this.prev) : this.val$seed;
            while (this.val$hasNext.test(applyAsInt)) {
                intConsumer.accept(applyAsInt);
                applyAsInt = this.val$next.applyAsInt(applyAsInt);
            }
        }
    }

    public interface Builder extends IntConsumer {
        void accept(int i);

        Builder add(int i);

        IntStream build();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static Builder $default$add(Builder _this, int i) {
                _this.accept(i);
                return _this;
            }
        }
    }
}
