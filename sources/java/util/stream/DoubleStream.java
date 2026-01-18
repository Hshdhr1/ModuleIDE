package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.StreamSpliterators;
import java.util.stream.Streams;
import java.util.stream.WhileOps;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DoubleStream extends BaseStream {
    boolean allMatch(DoublePredicate doublePredicate);

    boolean anyMatch(DoublePredicate doublePredicate);

    OptionalDouble average();

    Stream boxed();

    Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer);

    long count();

    DoubleStream distinct();

    DoubleStream dropWhile(DoublePredicate doublePredicate);

    DoubleStream filter(DoublePredicate doublePredicate);

    OptionalDouble findAny();

    OptionalDouble findFirst();

    DoubleStream flatMap(DoubleFunction doubleFunction);

    void forEach(DoubleConsumer doubleConsumer);

    void forEachOrdered(DoubleConsumer doubleConsumer);

    PrimitiveIterator.OfDouble iterator();

    DoubleStream limit(long j);

    DoubleStream map(DoubleUnaryOperator doubleUnaryOperator);

    IntStream mapToInt(DoubleToIntFunction doubleToIntFunction);

    LongStream mapToLong(DoubleToLongFunction doubleToLongFunction);

    Stream mapToObj(DoubleFunction doubleFunction);

    OptionalDouble max();

    OptionalDouble min();

    boolean noneMatch(DoublePredicate doublePredicate);

    DoubleStream parallel();

    DoubleStream peek(DoubleConsumer doubleConsumer);

    double reduce(double d, DoubleBinaryOperator doubleBinaryOperator);

    OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator);

    DoubleStream sequential();

    DoubleStream skip(long j);

    DoubleStream sorted();

    Spliterator.OfDouble spliterator();

    double sum();

    DoubleSummaryStatistics summaryStatistics();

    DoubleStream takeWhile(DoublePredicate doublePredicate);

    double[] toArray();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ Iterator $default$iterator(DoubleStream _this) {
            return _this.iterator();
        }

        public static /* bridge */ /* synthetic */ BaseStream $default$parallel(DoubleStream _this) {
            return _this.parallel();
        }

        public static /* bridge */ /* synthetic */ BaseStream $default$sequential(DoubleStream _this) {
            return _this.sequential();
        }

        public static /* bridge */ /* synthetic */ Spliterator $default$spliterator(DoubleStream _this) {
            return _this.spliterator();
        }

        public static DoubleStream $default$takeWhile(DoubleStream _this, DoublePredicate doublePredicate) {
            doublePredicate.getClass();
            return (DoubleStream) StreamSupport.doubleStream(new WhileOps.UnorderedWhileSpliterator.OfDouble.Taking(_this.spliterator(), true, doublePredicate), _this.isParallel()).onClose(new DoubleStream$$ExternalSyntheticLambda0(_this));
        }

        public static DoubleStream $default$dropWhile(DoubleStream _this, DoublePredicate doublePredicate) {
            doublePredicate.getClass();
            return (DoubleStream) StreamSupport.doubleStream(new WhileOps.UnorderedWhileSpliterator.OfDouble.Dropping(_this.spliterator(), true, doublePredicate), _this.isParallel()).onClose(new DoubleStream$$ExternalSyntheticLambda0(_this));
        }

        public static Builder builder() {
            return new Streams.DoubleStreamBuilderImpl();
        }

        public static DoubleStream empty() {
            return StreamSupport.doubleStream(Spliterators.emptyDoubleSpliterator(), false);
        }

        public static DoubleStream of(double d) {
            return StreamSupport.doubleStream(new Streams.DoubleStreamBuilderImpl(d), false);
        }

        public static DoubleStream of(double... dArr) {
            return Arrays.stream(dArr);
        }

        public static DoubleStream iterate(double d, DoubleUnaryOperator doubleUnaryOperator) {
            doubleUnaryOperator.getClass();
            return StreamSupport.doubleStream(new 1(Long.MAX_VALUE, 1296, doubleUnaryOperator, d), false);
        }

        public static DoubleStream iterate(double d, DoublePredicate doublePredicate, DoubleUnaryOperator doubleUnaryOperator) {
            doubleUnaryOperator.getClass();
            doublePredicate.getClass();
            return StreamSupport.doubleStream(new 2(Long.MAX_VALUE, 1296, doubleUnaryOperator, d, doublePredicate), false);
        }

        public static DoubleStream generate(DoubleSupplier doubleSupplier) {
            doubleSupplier.getClass();
            return StreamSupport.doubleStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfDouble(Long.MAX_VALUE, doubleSupplier), false);
        }

        public static DoubleStream concat(DoubleStream doubleStream, DoubleStream doubleStream2) {
            doubleStream.getClass();
            doubleStream2.getClass();
            return (DoubleStream) StreamSupport.doubleStream(new Streams.ConcatSpliterator.OfDouble(doubleStream.spliterator(), doubleStream2.spliterator()), doubleStream.isParallel() || doubleStream2.isParallel()).onClose(Streams.composedClose(doubleStream, doubleStream2));
        }
    }

    class 1 extends Spliterators.AbstractDoubleSpliterator {
        double prev;
        boolean started;
        final /* synthetic */ DoubleUnaryOperator val$f;
        final /* synthetic */ double val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, DoubleUnaryOperator doubleUnaryOperator, double d) {
            super(j, i);
            this.val$f = doubleUnaryOperator;
            this.val$seed = d;
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            double d;
            doubleConsumer.getClass();
            if (this.started) {
                d = this.val$f.applyAsDouble(this.prev);
            } else {
                d = this.val$seed;
                this.started = true;
            }
            this.prev = d;
            doubleConsumer.accept(d);
            return true;
        }
    }

    class 2 extends Spliterators.AbstractDoubleSpliterator {
        boolean finished;
        double prev;
        boolean started;
        final /* synthetic */ DoublePredicate val$hasNext;
        final /* synthetic */ DoubleUnaryOperator val$next;
        final /* synthetic */ double val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(long j, int i, DoubleUnaryOperator doubleUnaryOperator, double d, DoublePredicate doublePredicate) {
            super(j, i);
            this.val$next = doubleUnaryOperator;
            this.val$seed = d;
            this.val$hasNext = doublePredicate;
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            double d;
            doubleConsumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                d = this.val$next.applyAsDouble(this.prev);
            } else {
                d = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(d)) {
                this.finished = true;
                return false;
            }
            this.prev = d;
            doubleConsumer.accept(d);
            return true;
        }

        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            double applyAsDouble = this.started ? this.val$next.applyAsDouble(this.prev) : this.val$seed;
            while (this.val$hasNext.test(applyAsDouble)) {
                doubleConsumer.accept(applyAsDouble);
                applyAsDouble = this.val$next.applyAsDouble(applyAsDouble);
            }
        }
    }

    public interface Builder extends DoubleConsumer {
        void accept(double d);

        Builder add(double d);

        DoubleStream build();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static Builder $default$add(Builder _this, double d) {
                _this.accept(d);
                return _this;
            }
        }
    }
}
