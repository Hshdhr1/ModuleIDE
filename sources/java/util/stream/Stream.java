package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSpliterators;
import java.util.stream.Streams;
import java.util.stream.WhileOps;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Stream extends BaseStream {
    boolean allMatch(Predicate predicate);

    boolean anyMatch(Predicate predicate);

    Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2);

    Object collect(Collector collector);

    long count();

    Stream distinct();

    Stream dropWhile(Predicate predicate);

    Stream filter(Predicate predicate);

    Optional findAny();

    Optional findFirst();

    Stream flatMap(Function function);

    DoubleStream flatMapToDouble(Function function);

    IntStream flatMapToInt(Function function);

    LongStream flatMapToLong(Function function);

    void forEach(Consumer consumer);

    void forEachOrdered(Consumer consumer);

    Stream limit(long j);

    Stream map(Function function);

    DoubleStream mapToDouble(ToDoubleFunction toDoubleFunction);

    IntStream mapToInt(ToIntFunction toIntFunction);

    LongStream mapToLong(ToLongFunction toLongFunction);

    Optional max(Comparator comparator);

    Optional min(Comparator comparator);

    boolean noneMatch(Predicate predicate);

    Stream peek(Consumer consumer);

    Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator);

    Object reduce(Object obj, BinaryOperator binaryOperator);

    Optional reduce(BinaryOperator binaryOperator);

    Stream skip(long j);

    Stream sorted();

    Stream sorted(Comparator comparator);

    Stream takeWhile(Predicate predicate);

    Object[] toArray();

    Object[] toArray(IntFunction intFunction);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Stream $default$takeWhile(Stream _this, Predicate predicate) {
            predicate.getClass();
            return (Stream) StreamSupport.stream(new WhileOps.UnorderedWhileSpliterator.OfRef.Taking(_this.spliterator(), true, predicate), _this.isParallel()).onClose(new Stream$$ExternalSyntheticLambda0(_this));
        }

        public static Stream $default$dropWhile(Stream _this, Predicate predicate) {
            predicate.getClass();
            return (Stream) StreamSupport.stream(new WhileOps.UnorderedWhileSpliterator.OfRef.Dropping(_this.spliterator(), true, predicate), _this.isParallel()).onClose(new Stream$$ExternalSyntheticLambda0(_this));
        }

        public static Builder builder() {
            return new Streams.StreamBuilderImpl();
        }

        public static Stream empty() {
            return StreamSupport.stream(Spliterators.emptySpliterator(), false);
        }

        public static Stream of(Object obj) {
            return StreamSupport.stream(new Streams.StreamBuilderImpl(obj), false);
        }

        public static Stream ofNullable(Object obj) {
            return obj == null ? empty() : StreamSupport.stream(new Streams.StreamBuilderImpl(obj), false);
        }

        @SafeVarargs
        public static Stream of(Object... objArr) {
            return Arrays.stream(objArr);
        }

        public static Stream iterate(Object obj, UnaryOperator unaryOperator) {
            unaryOperator.getClass();
            return StreamSupport.stream(new 1(Long.MAX_VALUE, 1040, unaryOperator, obj), false);
        }

        public static Stream iterate(Object obj, Predicate predicate, UnaryOperator unaryOperator) {
            unaryOperator.getClass();
            predicate.getClass();
            return StreamSupport.stream(new 2(Long.MAX_VALUE, 1040, unaryOperator, obj, predicate), false);
        }

        public static Stream generate(Supplier supplier) {
            supplier.getClass();
            return StreamSupport.stream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef(Long.MAX_VALUE, supplier), false);
        }

        public static Stream concat(Stream stream, Stream stream2) {
            stream.getClass();
            stream2.getClass();
            return (Stream) StreamSupport.stream(new Streams.ConcatSpliterator.OfRef(stream.spliterator(), stream2.spliterator()), stream.isParallel() || stream2.isParallel()).onClose(Streams.composedClose(stream, stream2));
        }
    }

    class 1 extends Spliterators.AbstractSpliterator {
        Object prev;
        boolean started;
        final /* synthetic */ UnaryOperator val$f;
        final /* synthetic */ Object val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, UnaryOperator unaryOperator, Object obj) {
            super(j, i);
            this.val$f = unaryOperator;
            this.val$seed = obj;
        }

        public boolean tryAdvance(Consumer consumer) {
            Object obj;
            consumer.getClass();
            if (this.started) {
                obj = this.val$f.apply(this.prev);
            } else {
                obj = this.val$seed;
                this.started = true;
            }
            this.prev = obj;
            consumer.accept(obj);
            return true;
        }
    }

    class 2 extends Spliterators.AbstractSpliterator {
        boolean finished;
        Object prev;
        boolean started;
        final /* synthetic */ Predicate val$hasNext;
        final /* synthetic */ UnaryOperator val$next;
        final /* synthetic */ Object val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(long j, int i, UnaryOperator unaryOperator, Object obj, Predicate predicate) {
            super(j, i);
            this.val$next = unaryOperator;
            this.val$seed = obj;
            this.val$hasNext = predicate;
        }

        public boolean tryAdvance(Consumer consumer) {
            Object obj;
            consumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                obj = this.val$next.apply(this.prev);
            } else {
                obj = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(obj)) {
                this.prev = null;
                this.finished = true;
                return false;
            }
            this.prev = obj;
            consumer.accept(obj);
            return true;
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            Object apply = this.started ? this.val$next.apply(this.prev) : this.val$seed;
            this.prev = null;
            while (this.val$hasNext.test(apply)) {
                consumer.accept(apply);
                apply = this.val$next.apply(apply);
            }
        }
    }

    public interface Builder extends Consumer {
        void accept(Object obj);

        Builder add(Object obj);

        Stream build();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static Builder $default$add(Builder _this, Object obj) {
                _this.accept(obj);
                return _this;
            }
        }
    }
}
