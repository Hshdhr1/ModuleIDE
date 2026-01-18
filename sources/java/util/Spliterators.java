package java.util;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Spliterators {
    private static final Spliterator EMPTY_SPLITERATOR = new EmptySpliterator.OfRef();
    private static final Spliterator.OfInt EMPTY_INT_SPLITERATOR = new EmptySpliterator.OfInt();
    private static final Spliterator.OfLong EMPTY_LONG_SPLITERATOR = new EmptySpliterator.OfLong();
    private static final Spliterator.OfDouble EMPTY_DOUBLE_SPLITERATOR = new EmptySpliterator.OfDouble();

    private Spliterators() {
    }

    public static Spliterator emptySpliterator() {
        return EMPTY_SPLITERATOR;
    }

    public static Spliterator.OfInt emptyIntSpliterator() {
        return EMPTY_INT_SPLITERATOR;
    }

    public static Spliterator.OfLong emptyLongSpliterator() {
        return EMPTY_LONG_SPLITERATOR;
    }

    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return EMPTY_DOUBLE_SPLITERATOR;
    }

    public static Spliterator spliterator(Object[] objArr, int i) {
        objArr.getClass();
        return new ArraySpliterator(objArr, i);
    }

    public static Spliterator spliterator(Object[] objArr, int i, int i2, int i3) {
        objArr.getClass();
        checkFromToBounds(objArr.length, i, i2);
        return new ArraySpliterator(objArr, i, i2, i3);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i) {
        iArr.getClass();
        return new IntArraySpliterator(iArr, i);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i, int i2, int i3) {
        iArr.getClass();
        checkFromToBounds(iArr.length, i, i2);
        return new IntArraySpliterator(iArr, i, i2, i3);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i) {
        jArr.getClass();
        return new LongArraySpliterator(jArr, i);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i, int i2, int i3) {
        jArr.getClass();
        checkFromToBounds(jArr.length, i, i2);
        return new LongArraySpliterator(jArr, i, i2, i3);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i) {
        dArr.getClass();
        return new DoubleArraySpliterator(dArr, i);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i, int i2, int i3) {
        dArr.getClass();
        checkFromToBounds(dArr.length, i, i2);
        return new DoubleArraySpliterator(dArr, i, i2, i3);
    }

    private static void checkFromToBounds(int i, int i2, int i3) {
        if (i2 <= i3) {
            if (i2 < 0) {
                throw new ArrayIndexOutOfBoundsException(i2);
            }
            if (i3 > i) {
                throw new ArrayIndexOutOfBoundsException(i3);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("origin(" + i2 + ") > fence(" + i3 + ")");
    }

    public static Spliterator spliterator(Collection collection, int i) {
        collection.getClass();
        return new IteratorSpliterator(collection, i);
    }

    public static Spliterator spliterator(Iterator it, long j, int i) {
        it.getClass();
        return new IteratorSpliterator(it, j, i);
    }

    public static Spliterator spliteratorUnknownSize(Iterator it, int i) {
        it.getClass();
        return new IteratorSpliterator(it, i);
    }

    public static Spliterator.OfInt spliterator(PrimitiveIterator.OfInt ofInt, long j, int i) {
        ofInt.getClass();
        return new IntIteratorSpliterator(ofInt, j, i);
    }

    public static Spliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt ofInt, int i) {
        ofInt.getClass();
        return new IntIteratorSpliterator(ofInt, i);
    }

    public static Spliterator.OfLong spliterator(PrimitiveIterator.OfLong ofLong, long j, int i) {
        ofLong.getClass();
        return new LongIteratorSpliterator(ofLong, j, i);
    }

    public static Spliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong ofLong, int i) {
        ofLong.getClass();
        return new LongIteratorSpliterator(ofLong, i);
    }

    public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble ofDouble, long j, int i) {
        ofDouble.getClass();
        return new DoubleIteratorSpliterator(ofDouble, j, i);
    }

    public static Spliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble ofDouble, int i) {
        ofDouble.getClass();
        return new DoubleIteratorSpliterator(ofDouble, i);
    }

    class 1Adapter implements Iterator, Consumer {
        Object nextElement;
        final /* synthetic */ Spliterator val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        1Adapter(Spliterator spliterator) {
            this.val$spliterator = spliterator;
        }

        public void accept(Object obj) {
            this.valueReady = true;
            this.nextElement = obj;
        }

        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        public Object next() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static Iterator iterator(Spliterator spliterator) {
        spliterator.getClass();
        return new 1Adapter(spliterator);
    }

    class 2Adapter implements PrimitiveIterator.OfInt, IntConsumer {
        int nextElement;
        final /* synthetic */ Spliterator.OfInt val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            PrimitiveIterator.OfInt.-CC.$default$forEachRemaining(this, obj);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfInt.-CC.$default$forEachRemaining((PrimitiveIterator.OfInt) this, consumer);
        }

        public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            PrimitiveIterator.OfInt.-CC.$default$forEachRemaining((PrimitiveIterator.OfInt) this, intConsumer);
        }

        public /* synthetic */ Integer next() {
            return PrimitiveIterator.OfInt.-CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        public /* bridge */ /* synthetic */ Object next() {
            return PrimitiveIterator.OfInt.-CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        2Adapter(Spliterator.OfInt ofInt) {
            this.val$spliterator = ofInt;
        }

        public void accept(int i) {
            this.valueReady = true;
            this.nextElement = i;
        }

        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance((IntConsumer) this);
            }
            return this.valueReady;
        }

        public int nextInt() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static PrimitiveIterator.OfInt iterator(Spliterator.OfInt ofInt) {
        ofInt.getClass();
        return new 2Adapter(ofInt);
    }

    class 3Adapter implements PrimitiveIterator.OfLong, LongConsumer {
        long nextElement;
        final /* synthetic */ Spliterator.OfLong val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            PrimitiveIterator.OfLong.-CC.$default$forEachRemaining(this, obj);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfLong.-CC.$default$forEachRemaining((PrimitiveIterator.OfLong) this, consumer);
        }

        public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
            PrimitiveIterator.OfLong.-CC.$default$forEachRemaining((PrimitiveIterator.OfLong) this, longConsumer);
        }

        public /* synthetic */ Long next() {
            return PrimitiveIterator.OfLong.-CC.$default$next((PrimitiveIterator.OfLong) this);
        }

        public /* bridge */ /* synthetic */ Object next() {
            return PrimitiveIterator.OfLong.-CC.$default$next((PrimitiveIterator.OfLong) this);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        3Adapter(Spliterator.OfLong ofLong) {
            this.val$spliterator = ofLong;
        }

        public void accept(long j) {
            this.valueReady = true;
            this.nextElement = j;
        }

        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance((LongConsumer) this);
            }
            return this.valueReady;
        }

        public long nextLong() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static PrimitiveIterator.OfLong iterator(Spliterator.OfLong ofLong) {
        ofLong.getClass();
        return new 3Adapter(ofLong);
    }

    class 4Adapter implements PrimitiveIterator.OfDouble, DoubleConsumer {
        double nextElement;
        final /* synthetic */ Spliterator.OfDouble val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            PrimitiveIterator.OfDouble.-CC.$default$forEachRemaining(this, obj);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfDouble.-CC.$default$forEachRemaining((PrimitiveIterator.OfDouble) this, consumer);
        }

        public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
            PrimitiveIterator.OfDouble.-CC.$default$forEachRemaining((PrimitiveIterator.OfDouble) this, doubleConsumer);
        }

        public /* synthetic */ Double next() {
            return PrimitiveIterator.OfDouble.-CC.$default$next((PrimitiveIterator.OfDouble) this);
        }

        public /* bridge */ /* synthetic */ Object next() {
            return PrimitiveIterator.OfDouble.-CC.$default$next((PrimitiveIterator.OfDouble) this);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        4Adapter(Spliterator.OfDouble ofDouble) {
            this.val$spliterator = ofDouble;
        }

        public void accept(double d) {
            this.valueReady = true;
            this.nextElement = d;
        }

        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance((DoubleConsumer) this);
            }
            return this.valueReady;
        }

        public double nextDouble() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static PrimitiveIterator.OfDouble iterator(Spliterator.OfDouble ofDouble) {
        ofDouble.getClass();
        return new 4Adapter(ofDouble);
    }

    private static abstract class EmptySpliterator {
        public int characteristics() {
            return 16448;
        }

        public long estimateSize() {
            return 0L;
        }

        public Spliterator trySplit() {
            return null;
        }

        EmptySpliterator() {
        }

        public boolean tryAdvance(Object obj) {
            obj.getClass();
            return false;
        }

        public void forEachRemaining(Object obj) {
            obj.getClass();
        }

        private static final class OfRef extends EmptySpliterator implements Spliterator {
            public /* synthetic */ Comparator getComparator() {
                return Spliterator.-CC.$default$getComparator(this);
            }

            public /* synthetic */ long getExactSizeIfKnown() {
                return Spliterator.-CC.$default$getExactSizeIfKnown(this);
            }

            public /* synthetic */ boolean hasCharacteristics(int i) {
                return Spliterator.-CC.$default$hasCharacteristics(this, i);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
                super.forEachRemaining((Object) consumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return super.tryAdvance((Object) consumer);
            }

            OfRef() {
            }
        }

        private static final class OfInt extends EmptySpliterator implements Spliterator.OfInt {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
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

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfInt() {
            }
        }

        private static final class OfLong extends EmptySpliterator implements Spliterator.OfLong {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
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

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfLong() {
            }
        }

        private static final class OfDouble extends EmptySpliterator implements Spliterator.OfDouble {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
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

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfDouble() {
            }
        }
    }

    static final class ArraySpliterator implements Spliterator {
        private final Object[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public ArraySpliterator(Object[] objArr, int i) {
            this(objArr, 0, objArr.length, i);
        }

        public ArraySpliterator(Object[] objArr, int i, int i2, int i3) {
            this.array = objArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        public Spliterator trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            Object[] objArr = this.array;
            this.index = i2;
            return new ArraySpliterator(objArr, i, i2, this.characteristics);
        }

        public void forEachRemaining(Consumer consumer) {
            int i;
            consumer.getClass();
            Object[] objArr = this.array;
            int length = objArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    consumer.accept(objArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            Object[] objArr = this.array;
            this.index = i + 1;
            consumer.accept(objArr[i]);
            return true;
        }

        public long estimateSize() {
            return this.fence - this.index;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class IntArraySpliterator implements Spliterator.OfInt {
        private final int[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
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

        public IntArraySpliterator(int[] iArr, int i) {
            this(iArr, 0, iArr.length, i);
        }

        public IntArraySpliterator(int[] iArr, int i, int i2, int i3) {
            this.array = iArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        public Spliterator.OfInt trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            int[] iArr = this.array;
            this.index = i2;
            return new IntArraySpliterator(iArr, i, i2, this.characteristics);
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            int i;
            intConsumer.getClass();
            int[] iArr = this.array;
            int length = iArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    intConsumer.accept(iArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            intConsumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            int[] iArr = this.array;
            this.index = i + 1;
            intConsumer.accept(iArr[i]);
            return true;
        }

        public long estimateSize() {
            return this.fence - this.index;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class LongArraySpliterator implements Spliterator.OfLong {
        private final long[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
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

        public LongArraySpliterator(long[] jArr, int i) {
            this(jArr, 0, jArr.length, i);
        }

        public LongArraySpliterator(long[] jArr, int i, int i2, int i3) {
            this.array = jArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        public Spliterator.OfLong trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            long[] jArr = this.array;
            this.index = i2;
            return new LongArraySpliterator(jArr, i, i2, this.characteristics);
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            int i;
            longConsumer.getClass();
            long[] jArr = this.array;
            int length = jArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    longConsumer.accept(jArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            longConsumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            long[] jArr = this.array;
            this.index = i + 1;
            longConsumer.accept(jArr[i]);
            return true;
        }

        public long estimateSize() {
            return this.fence - this.index;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class DoubleArraySpliterator implements Spliterator.OfDouble {
        private final double[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
        }

        public DoubleArraySpliterator(double[] dArr, int i) {
            this(dArr, 0, dArr.length, i);
        }

        public DoubleArraySpliterator(double[] dArr, int i, int i2, int i3) {
            this.array = dArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        public Spliterator.OfDouble trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            double[] dArr = this.array;
            this.index = i2;
            return new DoubleArraySpliterator(dArr, i, i2, this.characteristics);
        }

        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            int i;
            doubleConsumer.getClass();
            double[] dArr = this.array;
            int length = dArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    doubleConsumer.accept(dArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            double[] dArr = this.array;
            this.index = i + 1;
            doubleConsumer.accept(dArr[i]);
            return true;
        }

        public long estimateSize() {
            return this.fence - this.index;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    public static abstract class AbstractSpliterator implements Spliterator {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

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

        protected AbstractSpliterator(long j, int i) {
            this.est = j;
            this.characteristics = (i & 64) != 0 ? i | 16384 : i;
        }

        static final class HoldingConsumer implements Consumer {
            Object value;

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.-CC.$default$andThen(this, consumer);
            }

            HoldingConsumer() {
            }

            public void accept(Object obj) {
                this.value = obj;
            }
        }

        public Spliterator trySplit() {
            HoldingConsumer holdingConsumer = new HoldingConsumer();
            long j = this.est;
            if (j <= 1 || !tryAdvance(holdingConsumer)) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            Object[] objArr = new Object[i];
            int i2 = 0;
            do {
                objArr[i2] = holdingConsumer.value;
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (tryAdvance(holdingConsumer));
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new ArraySpliterator(objArr, 0, i2, characteristics());
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }
    }

    public static abstract class AbstractIntSpliterator implements Spliterator.OfInt {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            Spliterator.OfInt.-CC.$default$forEachRemaining(this, obj);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
        }

        public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, intConsumer);
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

        public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
            return Spliterator.OfInt.-CC.$default$tryAdvance(this, obj);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
        }

        protected AbstractIntSpliterator(long j, int i) {
            this.est = j;
            this.characteristics = (i & 64) != 0 ? i | 16384 : i;
        }

        static final class HoldingIntConsumer implements IntConsumer {
            int value;

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            HoldingIntConsumer() {
            }

            public void accept(int i) {
                this.value = i;
            }
        }

        public Spliterator.OfInt trySplit() {
            HoldingIntConsumer holdingIntConsumer = new HoldingIntConsumer();
            long j = this.est;
            if (j <= 1 || !tryAdvance((IntConsumer) holdingIntConsumer)) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            int[] iArr = new int[i];
            int i2 = 0;
            do {
                iArr[i2] = holdingIntConsumer.value;
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (tryAdvance((IntConsumer) holdingIntConsumer));
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new IntArraySpliterator(iArr, 0, i2, characteristics());
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }
    }

    public static abstract class AbstractLongSpliterator implements Spliterator.OfLong {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            Spliterator.OfLong.-CC.$default$forEachRemaining(this, obj);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
        }

        public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, longConsumer);
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

        public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
            return Spliterator.OfLong.-CC.$default$tryAdvance(this, obj);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
        }

        protected AbstractLongSpliterator(long j, int i) {
            this.est = j;
            this.characteristics = (i & 64) != 0 ? i | 16384 : i;
        }

        static final class HoldingLongConsumer implements LongConsumer {
            long value;

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            HoldingLongConsumer() {
            }

            public void accept(long j) {
                this.value = j;
            }
        }

        public Spliterator.OfLong trySplit() {
            HoldingLongConsumer holdingLongConsumer = new HoldingLongConsumer();
            long j = this.est;
            if (j <= 1 || !tryAdvance((LongConsumer) holdingLongConsumer)) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            long[] jArr = new long[i];
            int i2 = 0;
            do {
                jArr[i2] = holdingLongConsumer.value;
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (tryAdvance((LongConsumer) holdingLongConsumer));
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new LongArraySpliterator(jArr, 0, i2, characteristics());
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }
    }

    public static abstract class AbstractDoubleSpliterator implements Spliterator.OfDouble {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining(this, obj);
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, doubleConsumer);
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

        public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
            return Spliterator.OfDouble.-CC.$default$tryAdvance(this, obj);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
        }

        protected AbstractDoubleSpliterator(long j, int i) {
            this.est = j;
            this.characteristics = (i & 64) != 0 ? i | 16384 : i;
        }

        static final class HoldingDoubleConsumer implements DoubleConsumer {
            double value;

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            HoldingDoubleConsumer() {
            }

            public void accept(double d) {
                this.value = d;
            }
        }

        public Spliterator.OfDouble trySplit() {
            HoldingDoubleConsumer holdingDoubleConsumer = new HoldingDoubleConsumer();
            long j = this.est;
            if (j <= 1 || !tryAdvance((DoubleConsumer) holdingDoubleConsumer)) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            double[] dArr = new double[i];
            int i2 = 0;
            do {
                dArr[i2] = holdingDoubleConsumer.value;
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (tryAdvance((DoubleConsumer) holdingDoubleConsumer));
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new DoubleArraySpliterator(dArr, 0, i2, characteristics());
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }
    }

    static class IteratorSpliterator implements Spliterator {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private final Collection collection;
        private long est;
        private Iterator it;

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public IteratorSpliterator(Collection collection, int i) {
            this.collection = collection;
            this.it = null;
            this.characteristics = (i & 4096) == 0 ? i | 16448 : i;
        }

        public IteratorSpliterator(Iterator it, long j, int i) {
            this.collection = null;
            this.it = it;
            this.est = j;
            this.characteristics = (i & 4096) == 0 ? i | 16448 : i;
        }

        public IteratorSpliterator(Iterator it, int i) {
            this.collection = null;
            this.it = it;
            this.est = Long.MAX_VALUE;
            this.characteristics = i & (-16449);
        }

        public Spliterator trySplit() {
            long j;
            Iterator it = this.it;
            if (it == null) {
                it = this.collection.iterator();
                this.it = it;
                j = this.collection.size();
                this.est = j;
            } else {
                j = this.est;
            }
            if (j <= 1 || !it.hasNext()) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            Object[] objArr = new Object[i];
            int i2 = 0;
            do {
                objArr[i2] = it.next();
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (it.hasNext());
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new ArraySpliterator(objArr, 0, i2, this.characteristics);
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            Iterator it = this.it;
            if (it == null) {
                it = this.collection.iterator();
                this.it = it;
                this.est = this.collection.size();
            }
            it.forEachRemaining(consumer);
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            if (this.it == null) {
                this.it = this.collection.iterator();
                this.est = this.collection.size();
            }
            if (!this.it.hasNext()) {
                return false;
            }
            consumer.accept(this.it.next());
            return true;
        }

        public long estimateSize() {
            if (this.it == null) {
                this.it = this.collection.iterator();
                long size = this.collection.size();
                this.est = size;
                return size;
            }
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class IntIteratorSpliterator implements Spliterator.OfInt {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;
        private PrimitiveIterator.OfInt it;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
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

        public IntIteratorSpliterator(PrimitiveIterator.OfInt ofInt, long j, int i) {
            this.it = ofInt;
            this.est = j;
            this.characteristics = (i & 4096) == 0 ? i | 16448 : i;
        }

        public IntIteratorSpliterator(PrimitiveIterator.OfInt ofInt, int i) {
            this.it = ofInt;
            this.est = Long.MAX_VALUE;
            this.characteristics = i & (-16449);
        }

        public Spliterator.OfInt trySplit() {
            PrimitiveIterator.OfInt ofInt = this.it;
            long j = this.est;
            if (j <= 1 || !ofInt.hasNext()) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            int[] iArr = new int[i];
            int i2 = 0;
            do {
                iArr[i2] = ofInt.nextInt();
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (ofInt.hasNext());
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new IntArraySpliterator(iArr, 0, i2, this.characteristics);
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            intConsumer.getClass();
            this.it.forEachRemaining(intConsumer);
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            intConsumer.getClass();
            if (!this.it.hasNext()) {
                return false;
            }
            intConsumer.accept(this.it.nextInt());
            return true;
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class LongIteratorSpliterator implements Spliterator.OfLong {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;
        private PrimitiveIterator.OfLong it;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
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

        public LongIteratorSpliterator(PrimitiveIterator.OfLong ofLong, long j, int i) {
            this.it = ofLong;
            this.est = j;
            this.characteristics = (i & 4096) == 0 ? i | 16448 : i;
        }

        public LongIteratorSpliterator(PrimitiveIterator.OfLong ofLong, int i) {
            this.it = ofLong;
            this.est = Long.MAX_VALUE;
            this.characteristics = i & (-16449);
        }

        public Spliterator.OfLong trySplit() {
            PrimitiveIterator.OfLong ofLong = this.it;
            long j = this.est;
            if (j <= 1 || !ofLong.hasNext()) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            long[] jArr = new long[i];
            int i2 = 0;
            do {
                jArr[i2] = ofLong.nextLong();
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (ofLong.hasNext());
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new LongArraySpliterator(jArr, 0, i2, this.characteristics);
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            longConsumer.getClass();
            this.it.forEachRemaining(longConsumer);
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            longConsumer.getClass();
            if (!this.it.hasNext()) {
                return false;
            }
            longConsumer.accept(this.it.nextLong());
            return true;
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class DoubleIteratorSpliterator implements Spliterator.OfDouble {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;
        private PrimitiveIterator.OfDouble it;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
        }

        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble ofDouble, long j, int i) {
            this.it = ofDouble;
            this.est = j;
            this.characteristics = (i & 4096) == 0 ? i | 16448 : i;
        }

        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble ofDouble, int i) {
            this.it = ofDouble;
            this.est = Long.MAX_VALUE;
            this.characteristics = i & (-16449);
        }

        public Spliterator.OfDouble trySplit() {
            PrimitiveIterator.OfDouble ofDouble = this.it;
            long j = this.est;
            if (j <= 1 || !ofDouble.hasNext()) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > j) {
                i = (int) j;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            double[] dArr = new double[i];
            int i2 = 0;
            do {
                dArr[i2] = ofDouble.nextDouble();
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (ofDouble.hasNext());
            this.batch = i2;
            long j2 = this.est;
            if (j2 != Long.MAX_VALUE) {
                this.est = j2 - i2;
            }
            return new DoubleArraySpliterator(dArr, 0, i2, this.characteristics);
        }

        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            this.it.forEachRemaining(doubleConsumer);
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            if (!this.it.hasNext()) {
                return false;
            }
            doubleConsumer.accept(this.it.nextDouble());
            return true;
        }

        public long estimateSize() {
            return this.est;
        }

        public int characteristics() {
            return this.characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }
}
