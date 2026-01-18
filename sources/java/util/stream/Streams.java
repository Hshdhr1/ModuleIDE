package java.util.stream;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.SpinedBuffer;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class Streams {
    private Streams() {
        throw new Error("no instances");
    }

    static final class RangeIntSpliterator implements Spliterator.OfInt {
        private static final int BALANCED_SPLIT_THRESHOLD = 16777216;
        private static final int RIGHT_BALANCED_SPLIT_RATIO = 8;
        private int from;
        private int last;
        private final int upTo;

        public int characteristics() {
            return 17749;
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
        }

        public Comparator getComparator() {
            return null;
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
        }

        RangeIntSpliterator(int i, int i2, boolean z) {
            this(i, i2, z ? 1 : 0);
        }

        private RangeIntSpliterator(int i, int i2, int i3) {
            this.from = i;
            this.upTo = i2;
            this.last = i3;
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            intConsumer.getClass();
            int i = this.from;
            if (i < this.upTo) {
                this.from = i + 1;
                intConsumer.accept(i);
                return true;
            }
            if (this.last <= 0) {
                return false;
            }
            this.last = 0;
            intConsumer.accept(i);
            return true;
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            intConsumer.getClass();
            int i = this.from;
            int i2 = this.upTo;
            int i3 = this.last;
            this.from = i2;
            this.last = 0;
            while (i < i2) {
                intConsumer.accept(i);
                i++;
            }
            if (i3 > 0) {
                intConsumer.accept(i);
            }
        }

        public long estimateSize() {
            return (this.upTo - this.from) + this.last;
        }

        public Spliterator.OfInt trySplit() {
            long estimateSize = estimateSize();
            if (estimateSize <= 1) {
                return null;
            }
            int i = this.from;
            int splitPoint = splitPoint(estimateSize) + i;
            this.from = splitPoint;
            return new RangeIntSpliterator(i, splitPoint, 0);
        }

        private int splitPoint(long j) {
            return (int) (j / (j < 16777216 ? 2 : 8));
        }
    }

    static final class RangeLongSpliterator implements Spliterator.OfLong {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long BALANCED_SPLIT_THRESHOLD = 16777216;
        private static final long RIGHT_BALANCED_SPLIT_RATIO = 8;
        private long from;
        private int last;
        private final long upTo;

        public int characteristics() {
            return 17749;
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
        }

        public Comparator getComparator() {
            return null;
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
        }

        RangeLongSpliterator(long j, long j2, boolean z) {
            this(j, j2, z ? 1 : 0);
        }

        private RangeLongSpliterator(long j, long j2, int i) {
            this.from = j;
            this.upTo = j2;
            this.last = i;
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            longConsumer.getClass();
            long j = this.from;
            if (j < this.upTo) {
                this.from = 1 + j;
                longConsumer.accept(j);
                return true;
            }
            if (this.last <= 0) {
                return false;
            }
            this.last = 0;
            longConsumer.accept(j);
            return true;
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            longConsumer.getClass();
            long j = this.from;
            long j2 = this.upTo;
            int i = this.last;
            this.from = j2;
            this.last = 0;
            while (j < j2) {
                longConsumer.accept(j);
                j = 1 + j;
            }
            if (i > 0) {
                longConsumer.accept(j);
            }
        }

        public long estimateSize() {
            return (this.upTo - this.from) + this.last;
        }

        public Spliterator.OfLong trySplit() {
            long estimateSize = estimateSize();
            if (estimateSize <= 1) {
                return null;
            }
            long j = this.from;
            long splitPoint = splitPoint(estimateSize) + j;
            this.from = splitPoint;
            return new RangeLongSpliterator(j, splitPoint, 0);
        }

        private long splitPoint(long j) {
            return j / (j < 16777216 ? 2L : 8L);
        }
    }

    private static abstract class AbstractStreamBuilderImpl implements Spliterator {
        int count;

        /* synthetic */ AbstractStreamBuilderImpl(Streams-IA r1) {
            this();
        }

        public int characteristics() {
            return 17488;
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public /* synthetic */ Comparator getComparator() {
            return Spliterator.-CC.$default$getComparator(this);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public Spliterator trySplit() {
            return null;
        }

        private AbstractStreamBuilderImpl() {
        }

        public long estimateSize() {
            return (-this.count) - 1;
        }
    }

    static final class StreamBuilderImpl extends AbstractStreamBuilderImpl implements Stream.Builder {
        SpinedBuffer buffer;
        Object first;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        StreamBuilderImpl() {
            super(null);
        }

        StreamBuilderImpl(Object obj) {
            super(null);
            this.first = obj;
            this.count = -2;
        }

        public void accept(Object obj) {
            if (this.count == 0) {
                this.first = obj;
                this.count++;
            } else {
                if (this.count > 0) {
                    if (this.buffer == null) {
                        SpinedBuffer spinedBuffer = new SpinedBuffer();
                        this.buffer = spinedBuffer;
                        spinedBuffer.accept(this.first);
                        this.count++;
                    }
                    this.buffer.accept(obj);
                    return;
                }
                throw new IllegalStateException();
            }
        }

        public Stream.Builder add(Object obj) {
            accept(obj);
            return this;
        }

        public Stream build() {
            int i = this.count;
            if (i >= 0) {
                this.count = (-this.count) - 1;
                return i < 2 ? StreamSupport.stream(this, false) : StreamSupport.stream(this.buffer.spliterator(), false);
            }
            throw new IllegalStateException();
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            if (this.count != -2) {
                return false;
            }
            consumer.accept(this.first);
            this.count = -1;
            return true;
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            if (this.count == -2) {
                consumer.accept(this.first);
                this.count = -1;
            }
        }
    }

    static final class IntStreamBuilderImpl extends AbstractStreamBuilderImpl implements IntStream.Builder, Spliterator.OfInt {
        SpinedBuffer.OfInt buffer;
        int first;

        public /* synthetic */ IntStream.Builder add(int i) {
            return IntStream.Builder.-CC.$default$add(this, i);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
        }

        public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt) super.trySplit();
        }

        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        IntStreamBuilderImpl() {
            super(null);
        }

        IntStreamBuilderImpl(int i) {
            super(null);
            this.first = i;
            this.count = -2;
        }

        public void accept(int i) {
            if (this.count == 0) {
                this.first = i;
                this.count++;
            } else {
                if (this.count > 0) {
                    if (this.buffer == null) {
                        SpinedBuffer.OfInt ofInt = new SpinedBuffer.OfInt();
                        this.buffer = ofInt;
                        ofInt.accept(this.first);
                        this.count++;
                    }
                    this.buffer.accept(i);
                    return;
                }
                throw new IllegalStateException();
            }
        }

        public IntStream build() {
            int i = this.count;
            if (i >= 0) {
                this.count = (-this.count) - 1;
                return i < 2 ? StreamSupport.intStream(this, false) : StreamSupport.intStream(this.buffer.spliterator(), false);
            }
            throw new IllegalStateException();
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            intConsumer.getClass();
            if (this.count != -2) {
                return false;
            }
            intConsumer.accept(this.first);
            this.count = -1;
            return true;
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            intConsumer.getClass();
            if (this.count == -2) {
                intConsumer.accept(this.first);
                this.count = -1;
            }
        }
    }

    static final class LongStreamBuilderImpl extends AbstractStreamBuilderImpl implements LongStream.Builder, Spliterator.OfLong {
        SpinedBuffer.OfLong buffer;
        long first;

        public /* synthetic */ LongStream.Builder add(long j) {
            return LongStream.Builder.-CC.$default$add(this, j);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
        }

        public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
            return (Spliterator.OfLong) super.trySplit();
        }

        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        LongStreamBuilderImpl() {
            super(null);
        }

        LongStreamBuilderImpl(long j) {
            super(null);
            this.first = j;
            this.count = -2;
        }

        public void accept(long j) {
            if (this.count == 0) {
                this.first = j;
                this.count++;
            } else {
                if (this.count > 0) {
                    if (this.buffer == null) {
                        SpinedBuffer.OfLong ofLong = new SpinedBuffer.OfLong();
                        this.buffer = ofLong;
                        ofLong.accept(this.first);
                        this.count++;
                    }
                    this.buffer.accept(j);
                    return;
                }
                throw new IllegalStateException();
            }
        }

        public LongStream build() {
            int i = this.count;
            if (i >= 0) {
                this.count = (-this.count) - 1;
                return i < 2 ? StreamSupport.longStream(this, false) : StreamSupport.longStream(this.buffer.spliterator(), false);
            }
            throw new IllegalStateException();
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            longConsumer.getClass();
            if (this.count != -2) {
                return false;
            }
            longConsumer.accept(this.first);
            this.count = -1;
            return true;
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            longConsumer.getClass();
            if (this.count == -2) {
                longConsumer.accept(this.first);
                this.count = -1;
            }
        }
    }

    static final class DoubleStreamBuilderImpl extends AbstractStreamBuilderImpl implements DoubleStream.Builder, Spliterator.OfDouble {
        SpinedBuffer.OfDouble buffer;
        double first;

        public /* synthetic */ DoubleStream.Builder add(double d) {
            return DoubleStream.Builder.-CC.$default$add(this, d);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
        }

        public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
            return (Spliterator.OfDouble) super.trySplit();
        }

        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        DoubleStreamBuilderImpl() {
            super(null);
        }

        DoubleStreamBuilderImpl(double d) {
            super(null);
            this.first = d;
            this.count = -2;
        }

        public void accept(double d) {
            if (this.count == 0) {
                this.first = d;
                this.count++;
            } else {
                if (this.count > 0) {
                    if (this.buffer == null) {
                        SpinedBuffer.OfDouble ofDouble = new SpinedBuffer.OfDouble();
                        this.buffer = ofDouble;
                        ofDouble.accept(this.first);
                        this.count++;
                    }
                    this.buffer.accept(d);
                    return;
                }
                throw new IllegalStateException();
            }
        }

        public DoubleStream build() {
            int i = this.count;
            if (i >= 0) {
                this.count = (-this.count) - 1;
                return i < 2 ? StreamSupport.doubleStream(this, false) : StreamSupport.doubleStream(this.buffer.spliterator(), false);
            }
            throw new IllegalStateException();
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            if (this.count != -2) {
                return false;
            }
            doubleConsumer.accept(this.first);
            this.count = -1;
            return true;
        }

        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            if (this.count == -2) {
                doubleConsumer.accept(this.first);
                this.count = -1;
            }
        }
    }

    static abstract class ConcatSpliterator implements Spliterator {
        protected final Spliterator aSpliterator;
        protected final Spliterator bSpliterator;
        boolean beforeSplit = true;
        final boolean unsized;

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public ConcatSpliterator(Spliterator spliterator, Spliterator spliterator2) {
            this.aSpliterator = spliterator;
            this.bSpliterator = spliterator2;
            this.unsized = spliterator.estimateSize() + spliterator2.estimateSize() < 0;
        }

        public Spliterator trySplit() {
            Spliterator trySplit = this.beforeSplit ? this.aSpliterator : this.bSpliterator.trySplit();
            this.beforeSplit = false;
            return trySplit;
        }

        public boolean tryAdvance(Consumer consumer) {
            if (this.beforeSplit) {
                boolean tryAdvance = this.aSpliterator.tryAdvance(consumer);
                if (tryAdvance) {
                    return tryAdvance;
                }
                this.beforeSplit = false;
                return this.bSpliterator.tryAdvance(consumer);
            }
            return this.bSpliterator.tryAdvance(consumer);
        }

        public void forEachRemaining(Consumer consumer) {
            if (this.beforeSplit) {
                this.aSpliterator.forEachRemaining(consumer);
            }
            this.bSpliterator.forEachRemaining(consumer);
        }

        public long estimateSize() {
            if (this.beforeSplit) {
                long estimateSize = this.aSpliterator.estimateSize() + this.bSpliterator.estimateSize();
                if (estimateSize >= 0) {
                    return estimateSize;
                }
                return Long.MAX_VALUE;
            }
            return this.bSpliterator.estimateSize();
        }

        public int characteristics() {
            if (this.beforeSplit) {
                return this.aSpliterator.characteristics() & this.bSpliterator.characteristics() & (((this.unsized ? 16448 : 0) | 5) ^ (-1));
            }
            return this.bSpliterator.characteristics();
        }

        public Comparator getComparator() {
            if (this.beforeSplit) {
                throw new IllegalStateException();
            }
            return this.bSpliterator.getComparator();
        }

        static class OfRef extends ConcatSpliterator {
            OfRef(Spliterator spliterator, Spliterator spliterator2) {
                super(spliterator, spliterator2);
            }
        }

        private static abstract class OfPrimitive extends ConcatSpliterator implements Spliterator.OfPrimitive {
            /* synthetic */ OfPrimitive(Spliterator.OfPrimitive ofPrimitive, Spliterator.OfPrimitive ofPrimitive2, Streams-IA r3) {
                this(ofPrimitive, ofPrimitive2);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            private OfPrimitive(Spliterator.OfPrimitive ofPrimitive, Spliterator.OfPrimitive ofPrimitive2) {
                super(ofPrimitive, ofPrimitive2);
            }

            public boolean tryAdvance(Object obj) {
                if (this.beforeSplit) {
                    boolean tryAdvance = ((Spliterator.OfPrimitive) this.aSpliterator).tryAdvance(obj);
                    if (tryAdvance) {
                        return tryAdvance;
                    }
                    this.beforeSplit = false;
                    return ((Spliterator.OfPrimitive) this.bSpliterator).tryAdvance(obj);
                }
                return ((Spliterator.OfPrimitive) this.bSpliterator).tryAdvance(obj);
            }

            public void forEachRemaining(Object obj) {
                if (this.beforeSplit) {
                    ((Spliterator.OfPrimitive) this.aSpliterator).forEachRemaining(obj);
                }
                ((Spliterator.OfPrimitive) this.bSpliterator).forEachRemaining(obj);
            }
        }

        static class OfInt extends OfPrimitive implements Spliterator.OfInt {
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            OfInt(Spliterator.OfInt ofInt, Spliterator.OfInt ofInt2) {
                super(ofInt, ofInt2, null);
            }
        }

        static class OfLong extends OfPrimitive implements Spliterator.OfLong {
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            OfLong(Spliterator.OfLong ofLong, Spliterator.OfLong ofLong2) {
                super(ofLong, ofLong2, null);
            }
        }

        static class OfDouble extends OfPrimitive implements Spliterator.OfDouble {
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            OfDouble(Spliterator.OfDouble ofDouble, Spliterator.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2, null);
            }
        }
    }

    class 1 implements Runnable {
        final /* synthetic */ Runnable val$a;
        final /* synthetic */ Runnable val$b;

        1(Runnable runnable, Runnable runnable2) {
            this.val$a = runnable;
            this.val$b = runnable2;
        }

        public void run() {
            try {
                this.val$a.run();
                this.val$b.run();
            } catch (Throwable th) {
                try {
                    this.val$b.run();
                } catch (Throwable th2) {
                    try {
                        Streams$1$$ExternalSyntheticBackport0.m(th, th2);
                    } catch (Throwable unused) {
                    }
                }
                throw th;
            }
        }
    }

    static Runnable composeWithExceptions(Runnable runnable, Runnable runnable2) {
        return new 1(runnable, runnable2);
    }

    class 2 implements Runnable {
        final /* synthetic */ BaseStream val$a;
        final /* synthetic */ BaseStream val$b;

        2(BaseStream baseStream, BaseStream baseStream2) {
            this.val$a = baseStream;
            this.val$b = baseStream2;
        }

        public void run() {
            try {
                this.val$a.close();
                this.val$b.close();
            } catch (Throwable th) {
                try {
                    this.val$b.close();
                } catch (Throwable th2) {
                    try {
                        Streams$2$$ExternalSyntheticBackport0.m(th, th2);
                    } catch (Throwable unused) {
                    }
                }
                throw th;
            }
        }
    }

    static Runnable composedClose(BaseStream baseStream, BaseStream baseStream2) {
        return new 2(baseStream, baseStream2);
    }
}
