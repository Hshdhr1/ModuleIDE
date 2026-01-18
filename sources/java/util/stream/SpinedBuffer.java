package java.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class SpinedBuffer extends AbstractSpinedBuffer implements Consumer, Iterable {
    private static final int SPLITERATOR_CHARACTERISTICS = 16464;
    protected Object[] curChunk;
    protected Object[][] spine;

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.-CC.$default$andThen(this, consumer);
    }

    SpinedBuffer(int i) {
        super(i);
        this.curChunk = new Object[1 << this.initialChunkPower];
    }

    SpinedBuffer() {
        this.curChunk = new Object[1 << this.initialChunkPower];
    }

    protected long capacity() {
        if (this.spineIndex == 0) {
            return this.curChunk.length;
        }
        return this.priorElementCount[this.spineIndex] + this.spine[this.spineIndex].length;
    }

    private void inflateSpine() {
        if (this.spine == null) {
            this.spine = new Object[8][];
            this.priorElementCount = new long[8];
            this.spine[0] = this.curChunk;
        }
    }

    protected final void ensureCapacity(long j) {
        long capacity = capacity();
        if (j <= capacity) {
            return;
        }
        inflateSpine();
        int i = this.spineIndex;
        while (true) {
            i++;
            if (j <= capacity) {
                return;
            }
            Object[][] objArr = this.spine;
            if (i >= objArr.length) {
                int length = objArr.length * 2;
                this.spine = (Object[][]) Arrays.copyOf(objArr, length);
                this.priorElementCount = Arrays.copyOf(this.priorElementCount, length);
            }
            int chunkSize = chunkSize(i);
            this.spine[i] = new Object[chunkSize];
            this.priorElementCount[i] = this.priorElementCount[i - 1] + this.spine[r6].length;
            capacity += chunkSize;
        }
    }

    protected void increaseCapacity() {
        ensureCapacity(capacity() + 1);
    }

    public Object get(long j) {
        if (this.spineIndex == 0) {
            if (j < this.elementIndex) {
                return this.curChunk[(int) j];
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        if (j >= count()) {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        for (int i = 0; i <= this.spineIndex; i++) {
            long j2 = this.priorElementCount[i];
            Object[] objArr = this.spine[i];
            if (j < j2 + objArr.length) {
                return objArr[(int) (j - this.priorElementCount[i])];
            }
        }
        throw new IndexOutOfBoundsException(Long.toString(j));
    }

    public void copyInto(Object[] objArr, int i) {
        long j = i;
        long count = count() + j;
        if (count > objArr.length || count < j) {
            throw new IndexOutOfBoundsException("does not fit");
        }
        if (this.spineIndex == 0) {
            System.arraycopy(this.curChunk, 0, objArr, i, this.elementIndex);
            return;
        }
        for (int i2 = 0; i2 < this.spineIndex; i2++) {
            Object[] objArr2 = this.spine[i2];
            System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
            i += this.spine[i2].length;
        }
        if (this.elementIndex > 0) {
            System.arraycopy(this.curChunk, 0, objArr, i, this.elementIndex);
        }
    }

    public Object[] asArray(IntFunction intFunction) {
        long count = count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) count);
        copyInto(objArr, 0);
        return objArr;
    }

    public void clear() {
        Object[][] objArr = this.spine;
        if (objArr != null) {
            this.curChunk = objArr[0];
            int i = 0;
            while (true) {
                Object[] objArr2 = this.curChunk;
                if (i >= objArr2.length) {
                    break;
                }
                objArr2[i] = null;
                i++;
            }
            this.spine = null;
            this.priorElementCount = null;
        } else {
            for (int i2 = 0; i2 < this.elementIndex; i2++) {
                this.curChunk[i2] = null;
            }
        }
        this.elementIndex = 0;
        this.spineIndex = 0;
    }

    public Iterator iterator() {
        return Spliterators.iterator(spliterator());
    }

    public void forEach(Consumer consumer) {
        for (int i = 0; i < this.spineIndex; i++) {
            for (Object obj : this.spine[i]) {
                consumer.accept(obj);
            }
        }
        for (int i2 = 0; i2 < this.elementIndex; i2++) {
            consumer.accept(this.curChunk[i2]);
        }
    }

    public void accept(Object obj) {
        if (this.elementIndex == this.curChunk.length) {
            inflateSpine();
            int i = this.spineIndex + 1;
            Object[][] objArr = this.spine;
            if (i >= objArr.length || objArr[this.spineIndex + 1] == null) {
                increaseCapacity();
            }
            this.elementIndex = 0;
            this.spineIndex++;
            this.curChunk = this.spine[this.spineIndex];
        }
        Object[] objArr2 = this.curChunk;
        int i2 = this.elementIndex;
        this.elementIndex = i2 + 1;
        objArr2[i2] = obj;
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        forEach(new SpinedBuffer$$ExternalSyntheticLambda0(arrayList));
        return "SpinedBuffer:" + arrayList.toString();
    }

    class 1Splitr implements Spliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final int lastSpineElementFence;
        final int lastSpineIndex;
        Object[] splChunk;
        int splElementIndex;
        int splSpineIndex;

        public int characteristics() {
            return 16464;
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

        1Splitr(int i, int i2, int i3, int i4) {
            this.splSpineIndex = i;
            this.lastSpineIndex = i2;
            this.splElementIndex = i3;
            this.lastSpineElementFence = i4;
            this.splChunk = SpinedBuffer.this.spine == null ? SpinedBuffer.this.curChunk : SpinedBuffer.this.spine[i];
        }

        public long estimateSize() {
            if (this.splSpineIndex == this.lastSpineIndex) {
                return this.lastSpineElementFence - this.splElementIndex;
            }
            return ((SpinedBuffer.this.priorElementCount[this.lastSpineIndex] + this.lastSpineElementFence) - SpinedBuffer.this.priorElementCount[this.splSpineIndex]) - this.splElementIndex;
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            int i = this.splSpineIndex;
            int i2 = this.lastSpineIndex;
            if (i >= i2 && (i != i2 || this.splElementIndex >= this.lastSpineElementFence)) {
                return false;
            }
            Object[] objArr = this.splChunk;
            int i3 = this.splElementIndex;
            this.splElementIndex = i3 + 1;
            consumer.accept(objArr[i3]);
            if (this.splElementIndex == this.splChunk.length) {
                this.splElementIndex = 0;
                this.splSpineIndex++;
                if (SpinedBuffer.this.spine != null && this.splSpineIndex <= this.lastSpineIndex) {
                    this.splChunk = SpinedBuffer.this.spine[this.splSpineIndex];
                }
            }
            return true;
        }

        public void forEachRemaining(Consumer consumer) {
            int i;
            consumer.getClass();
            int i2 = this.splSpineIndex;
            int i3 = this.lastSpineIndex;
            if (i2 < i3 || (i2 == i3 && this.splElementIndex < this.lastSpineElementFence)) {
                int i4 = this.splElementIndex;
                while (true) {
                    i = this.lastSpineIndex;
                    if (i2 >= i) {
                        break;
                    }
                    Object[] objArr = SpinedBuffer.this.spine[i2];
                    while (i4 < objArr.length) {
                        consumer.accept(objArr[i4]);
                        i4++;
                    }
                    i2++;
                    i4 = 0;
                }
                Object[] objArr2 = this.splSpineIndex == i ? this.splChunk : SpinedBuffer.this.spine[this.lastSpineIndex];
                int i5 = this.lastSpineElementFence;
                while (i4 < i5) {
                    consumer.accept(objArr2[i4]);
                    i4++;
                }
                this.splSpineIndex = this.lastSpineIndex;
                this.splElementIndex = this.lastSpineElementFence;
            }
        }

        public Spliterator trySplit() {
            int i = this.splSpineIndex;
            int i2 = this.lastSpineIndex;
            if (i < i2) {
                SpinedBuffer spinedBuffer = SpinedBuffer.this;
                1Splitr r0 = spinedBuffer.new 1Splitr(i, i2 - 1, this.splElementIndex, spinedBuffer.spine[this.lastSpineIndex - 1].length);
                this.splSpineIndex = this.lastSpineIndex;
                this.splElementIndex = 0;
                this.splChunk = SpinedBuffer.this.spine[this.splSpineIndex];
                return r0;
            }
            if (i != i2) {
                return null;
            }
            int i3 = this.lastSpineElementFence;
            int i4 = this.splElementIndex;
            int i5 = (i3 - i4) / 2;
            if (i5 == 0) {
                return null;
            }
            Spliterator spliterator = Arrays.spliterator(this.splChunk, i4, i4 + i5);
            this.splElementIndex += i5;
            return spliterator;
        }
    }

    public Spliterator spliterator() {
        return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
    }

    static abstract class OfPrimitive extends AbstractSpinedBuffer implements Iterable {
        Object curChunk;
        Object[] spine;

        protected abstract void arrayForEach(Object obj, int i, int i2, Object obj2);

        protected abstract int arrayLength(Object obj);

        public abstract void forEach(Consumer consumer);

        public abstract Iterator iterator();

        public abstract Object newArray(int i);

        protected abstract Object[] newArrayArray(int i);

        OfPrimitive(int i) {
            super(i);
            this.curChunk = newArray(1 << this.initialChunkPower);
        }

        OfPrimitive() {
            this.curChunk = newArray(1 << this.initialChunkPower);
        }

        public Spliterator spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        protected long capacity() {
            if (this.spineIndex == 0) {
                return arrayLength(this.curChunk);
            }
            return this.priorElementCount[this.spineIndex] + arrayLength(this.spine[this.spineIndex]);
        }

        private void inflateSpine() {
            if (this.spine == null) {
                this.spine = newArrayArray(8);
                this.priorElementCount = new long[8];
                this.spine[0] = this.curChunk;
            }
        }

        protected final void ensureCapacity(long j) {
            long capacity = capacity();
            if (j <= capacity) {
                return;
            }
            inflateSpine();
            int i = this.spineIndex;
            while (true) {
                i++;
                if (j <= capacity) {
                    return;
                }
                Object[] objArr = this.spine;
                if (i >= objArr.length) {
                    int length = objArr.length * 2;
                    this.spine = Arrays.copyOf(objArr, length);
                    this.priorElementCount = Arrays.copyOf(this.priorElementCount, length);
                }
                int chunkSize = chunkSize(i);
                this.spine[i] = newArray(chunkSize);
                this.priorElementCount[i] = this.priorElementCount[i - 1] + arrayLength(this.spine[r6]);
                capacity += chunkSize;
            }
        }

        protected void increaseCapacity() {
            ensureCapacity(capacity() + 1);
        }

        protected int chunkFor(long j) {
            if (this.spineIndex == 0) {
                if (j < this.elementIndex) {
                    return 0;
                }
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            if (j >= count()) {
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            for (int i = 0; i <= this.spineIndex; i++) {
                if (j < this.priorElementCount[i] + arrayLength(this.spine[i])) {
                    return i;
                }
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }

        public void copyInto(Object obj, int i) {
            long j = i;
            long count = count() + j;
            if (count > arrayLength(obj) || count < j) {
                throw new IndexOutOfBoundsException("does not fit");
            }
            if (this.spineIndex == 0) {
                System.arraycopy(this.curChunk, 0, obj, i, this.elementIndex);
                return;
            }
            for (int i2 = 0; i2 < this.spineIndex; i2++) {
                Object obj2 = this.spine[i2];
                System.arraycopy(obj2, 0, obj, i, arrayLength(obj2));
                i += arrayLength(this.spine[i2]);
            }
            if (this.elementIndex > 0) {
                System.arraycopy(this.curChunk, 0, obj, i, this.elementIndex);
            }
        }

        public Object asPrimitiveArray() {
            long count = count();
            if (count >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            Object newArray = newArray((int) count);
            copyInto(newArray, 0);
            return newArray;
        }

        protected void preAccept() {
            if (this.elementIndex == arrayLength(this.curChunk)) {
                inflateSpine();
                int i = this.spineIndex + 1;
                Object[] objArr = this.spine;
                if (i >= objArr.length || objArr[this.spineIndex + 1] == null) {
                    increaseCapacity();
                }
                this.elementIndex = 0;
                this.spineIndex++;
                this.curChunk = this.spine[this.spineIndex];
            }
        }

        public void clear() {
            Object[] objArr = this.spine;
            if (objArr != null) {
                this.curChunk = objArr[0];
                this.spine = null;
                this.priorElementCount = null;
            }
            this.elementIndex = 0;
            this.spineIndex = 0;
        }

        public void forEach(Object obj) {
            for (int i = 0; i < this.spineIndex; i++) {
                Object obj2 = this.spine[i];
                arrayForEach(obj2, 0, arrayLength(obj2), obj);
            }
            arrayForEach(this.curChunk, 0, this.elementIndex, obj);
        }

        abstract class BaseSpliterator implements Spliterator.OfPrimitive {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            final int lastSpineElementFence;
            final int lastSpineIndex;
            Object splChunk;
            int splElementIndex;
            int splSpineIndex;

            abstract void arrayForOne(Object obj, int i, Object obj2);

            abstract Spliterator.OfPrimitive arraySpliterator(Object obj, int i, int i2);

            public int characteristics() {
                return 16464;
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

            abstract Spliterator.OfPrimitive newSpliterator(int i, int i2, int i3, int i4);

            BaseSpliterator(int i, int i2, int i3, int i4) {
                this.splSpineIndex = i;
                this.lastSpineIndex = i2;
                this.splElementIndex = i3;
                this.lastSpineElementFence = i4;
                this.splChunk = OfPrimitive.this.spine == null ? OfPrimitive.this.curChunk : OfPrimitive.this.spine[i];
            }

            public long estimateSize() {
                if (this.splSpineIndex == this.lastSpineIndex) {
                    return this.lastSpineElementFence - this.splElementIndex;
                }
                return ((OfPrimitive.this.priorElementCount[this.lastSpineIndex] + this.lastSpineElementFence) - OfPrimitive.this.priorElementCount[this.splSpineIndex]) - this.splElementIndex;
            }

            public boolean tryAdvance(Object obj) {
                obj.getClass();
                int i = this.splSpineIndex;
                int i2 = this.lastSpineIndex;
                if (i >= i2 && (i != i2 || this.splElementIndex >= this.lastSpineElementFence)) {
                    return false;
                }
                Object obj2 = this.splChunk;
                int i3 = this.splElementIndex;
                this.splElementIndex = i3 + 1;
                arrayForOne(obj2, i3, obj);
                if (this.splElementIndex == OfPrimitive.this.arrayLength(this.splChunk)) {
                    this.splElementIndex = 0;
                    this.splSpineIndex++;
                    if (OfPrimitive.this.spine != null && this.splSpineIndex <= this.lastSpineIndex) {
                        this.splChunk = OfPrimitive.this.spine[this.splSpineIndex];
                    }
                }
                return true;
            }

            public void forEachRemaining(Object obj) {
                int i;
                obj.getClass();
                int i2 = this.splSpineIndex;
                int i3 = this.lastSpineIndex;
                if (i2 < i3 || (i2 == i3 && this.splElementIndex < this.lastSpineElementFence)) {
                    int i4 = this.splElementIndex;
                    while (true) {
                        i = this.lastSpineIndex;
                        if (i2 >= i) {
                            break;
                        }
                        Object obj2 = OfPrimitive.this.spine[i2];
                        OfPrimitive ofPrimitive = OfPrimitive.this;
                        ofPrimitive.arrayForEach(obj2, i4, ofPrimitive.arrayLength(obj2), obj);
                        i2++;
                        i4 = 0;
                    }
                    OfPrimitive.this.arrayForEach(this.splSpineIndex == i ? this.splChunk : OfPrimitive.this.spine[this.lastSpineIndex], i4, this.lastSpineElementFence, obj);
                    this.splSpineIndex = this.lastSpineIndex;
                    this.splElementIndex = this.lastSpineElementFence;
                }
            }

            public Spliterator.OfPrimitive trySplit() {
                int i = this.splSpineIndex;
                int i2 = this.lastSpineIndex;
                if (i < i2) {
                    int i3 = this.splElementIndex;
                    OfPrimitive ofPrimitive = OfPrimitive.this;
                    Spliterator.OfPrimitive newSpliterator = newSpliterator(i, i2 - 1, i3, ofPrimitive.arrayLength(ofPrimitive.spine[this.lastSpineIndex - 1]));
                    this.splSpineIndex = this.lastSpineIndex;
                    this.splElementIndex = 0;
                    this.splChunk = OfPrimitive.this.spine[this.splSpineIndex];
                    return newSpliterator;
                }
                if (i != i2) {
                    return null;
                }
                int i4 = this.lastSpineElementFence;
                int i5 = this.splElementIndex;
                int i6 = (i4 - i5) / 2;
                if (i6 == 0) {
                    return null;
                }
                Spliterator.OfPrimitive arraySpliterator = arraySpliterator(this.splChunk, i5, i6);
                this.splElementIndex += i6;
                return arraySpliterator;
            }
        }
    }

    static class OfInt extends OfPrimitive implements IntConsumer {
        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        OfInt() {
        }

        OfInt(int i) {
            super(i);
        }

        public void forEach(Consumer consumer) {
            if (consumer instanceof IntConsumer) {
                forEach((IntConsumer) consumer);
                return;
            }
            if (Tripwire.ENABLED) {
                Tripwire.trip(getClass(), "{0} calling SpinedBuffer.OfInt.forEach(Consumer)");
            }
            spliterator().forEachRemaining(consumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public int[][] newArrayArray(int i) {
            return new int[i][];
        }

        public int[] newArray(int i) {
            return new int[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public int arrayLength(int[] iArr) {
            return iArr.length;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void arrayForEach(int[] iArr, int i, int i2, IntConsumer intConsumer) {
            while (i < i2) {
                intConsumer.accept(iArr[i]);
                i++;
            }
        }

        public void accept(int i) {
            preAccept();
            int[] iArr = (int[]) this.curChunk;
            int i2 = this.elementIndex;
            this.elementIndex = i2 + 1;
            iArr[i2] = i;
        }

        public int get(long j) {
            int chunkFor = chunkFor(j);
            if (this.spineIndex == 0 && chunkFor == 0) {
                return ((int[]) this.curChunk)[(int) j];
            }
            return ((int[][]) this.spine)[chunkFor][(int) (j - this.priorElementCount[chunkFor])];
        }

        public PrimitiveIterator.OfInt iterator() {
            return Spliterators.iterator(spliterator());
        }

        class 1Splitr extends OfPrimitive.BaseSpliterator implements Spliterator.OfInt {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
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

            1Splitr(int i, int i2, int i3, int i4) {
                super(i, i2, i3, i4);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public 1Splitr newSpliterator(int i, int i2, int i3, int i4) {
                return OfInt.this.new 1Splitr(i, i2, i3, i4);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public void arrayForOne(int[] iArr, int i, IntConsumer intConsumer) {
                intConsumer.accept(iArr[i]);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public Spliterator.OfInt arraySpliterator(int[] iArr, int i, int i2) {
                return Arrays.spliterator(iArr, i, i2 + i);
            }
        }

        public Spliterator.OfInt spliterator() {
            return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
        }

        public String toString() {
            int[] iArr = (int[]) asPrimitiveArray();
            if (iArr.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.spineIndex), Arrays.toString(iArr));
            }
            return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.spineIndex), Arrays.toString(Arrays.copyOf(iArr, 200)));
        }
    }

    static class OfLong extends OfPrimitive implements LongConsumer {
        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        OfLong() {
        }

        OfLong(int i) {
            super(i);
        }

        public void forEach(Consumer consumer) {
            if (consumer instanceof LongConsumer) {
                forEach((LongConsumer) consumer);
                return;
            }
            if (Tripwire.ENABLED) {
                Tripwire.trip(getClass(), "{0} calling SpinedBuffer.OfLong.forEach(Consumer)");
            }
            spliterator().forEachRemaining(consumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public long[][] newArrayArray(int i) {
            return new long[i][];
        }

        public long[] newArray(int i) {
            return new long[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public int arrayLength(long[] jArr) {
            return jArr.length;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void arrayForEach(long[] jArr, int i, int i2, LongConsumer longConsumer) {
            while (i < i2) {
                longConsumer.accept(jArr[i]);
                i++;
            }
        }

        public void accept(long j) {
            preAccept();
            long[] jArr = (long[]) this.curChunk;
            int i = this.elementIndex;
            this.elementIndex = i + 1;
            jArr[i] = j;
        }

        public long get(long j) {
            int chunkFor = chunkFor(j);
            if (this.spineIndex == 0 && chunkFor == 0) {
                return ((long[]) this.curChunk)[(int) j];
            }
            return ((long[][]) this.spine)[chunkFor][(int) (j - this.priorElementCount[chunkFor])];
        }

        public PrimitiveIterator.OfLong iterator() {
            return Spliterators.iterator(spliterator());
        }

        class 1Splitr extends OfPrimitive.BaseSpliterator implements Spliterator.OfLong {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
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

            1Splitr(int i, int i2, int i3, int i4) {
                super(i, i2, i3, i4);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public 1Splitr newSpliterator(int i, int i2, int i3, int i4) {
                return OfLong.this.new 1Splitr(i, i2, i3, i4);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public void arrayForOne(long[] jArr, int i, LongConsumer longConsumer) {
                longConsumer.accept(jArr[i]);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public Spliterator.OfLong arraySpliterator(long[] jArr, int i, int i2) {
                return Arrays.spliterator(jArr, i, i2 + i);
            }
        }

        public Spliterator.OfLong spliterator() {
            return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
        }

        public String toString() {
            long[] jArr = (long[]) asPrimitiveArray();
            if (jArr.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.spineIndex), Arrays.toString(jArr));
            }
            return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.spineIndex), Arrays.toString(Arrays.copyOf(jArr, 200)));
        }
    }

    static class OfDouble extends OfPrimitive implements DoubleConsumer {
        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        OfDouble() {
        }

        OfDouble(int i) {
            super(i);
        }

        public void forEach(Consumer consumer) {
            if (consumer instanceof DoubleConsumer) {
                forEach((DoubleConsumer) consumer);
                return;
            }
            if (Tripwire.ENABLED) {
                Tripwire.trip(getClass(), "{0} calling SpinedBuffer.OfDouble.forEach(Consumer)");
            }
            spliterator().forEachRemaining(consumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public double[][] newArrayArray(int i) {
            return new double[i][];
        }

        public double[] newArray(int i) {
            return new double[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public int arrayLength(double[] dArr) {
            return dArr.length;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void arrayForEach(double[] dArr, int i, int i2, DoubleConsumer doubleConsumer) {
            while (i < i2) {
                doubleConsumer.accept(dArr[i]);
                i++;
            }
        }

        public void accept(double d) {
            preAccept();
            double[] dArr = (double[]) this.curChunk;
            int i = this.elementIndex;
            this.elementIndex = i + 1;
            dArr[i] = d;
        }

        public double get(long j) {
            int chunkFor = chunkFor(j);
            if (this.spineIndex == 0 && chunkFor == 0) {
                return ((double[]) this.curChunk)[(int) j];
            }
            return ((double[][]) this.spine)[chunkFor][(int) (j - this.priorElementCount[chunkFor])];
        }

        public PrimitiveIterator.OfDouble iterator() {
            return Spliterators.iterator(spliterator());
        }

        class 1Splitr extends OfPrimitive.BaseSpliterator implements Spliterator.OfDouble {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
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

            1Splitr(int i, int i2, int i3, int i4) {
                super(i, i2, i3, i4);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public 1Splitr newSpliterator(int i, int i2, int i3, int i4) {
                return OfDouble.this.new 1Splitr(i, i2, i3, i4);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public void arrayForOne(double[] dArr, int i, DoubleConsumer doubleConsumer) {
                doubleConsumer.accept(dArr[i]);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public Spliterator.OfDouble arraySpliterator(double[] dArr, int i, int i2) {
                return Arrays.spliterator(dArr, i, i2 + i);
            }
        }

        public Spliterator.OfDouble spliterator() {
            return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
        }

        public String toString() {
            double[] dArr = (double[]) asPrimitiveArray();
            if (dArr.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.spineIndex), Arrays.toString(dArr));
            }
            return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.spineIndex), Arrays.toString(Arrays.copyOf(dArr, 200)));
        }
    }
}
