package java.util.stream;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.SpinedBuffer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class StreamSpliterators {
    StreamSpliterators() {
    }

    private static abstract class AbstractWrappingSpliterator implements Spliterator {
        AbstractSpinedBuffer buffer;
        Sink bufferSink;
        boolean finished;
        final boolean isParallel;
        long nextToConsume;
        final PipelineHelper ph;
        BooleanSupplier pusher;
        Spliterator spliterator;
        private Supplier spliteratorSupplier;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        abstract void initPartialTraversalState();

        abstract AbstractWrappingSpliterator wrap(Spliterator spliterator);

        AbstractWrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
            this.ph = pipelineHelper;
            this.spliteratorSupplier = supplier;
            this.spliterator = null;
            this.isParallel = z;
        }

        AbstractWrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
            this.ph = pipelineHelper;
            this.spliteratorSupplier = null;
            this.spliterator = spliterator;
            this.isParallel = z;
        }

        final void init() {
            if (this.spliterator == null) {
                this.spliterator = (Spliterator) this.spliteratorSupplier.get();
                this.spliteratorSupplier = null;
            }
        }

        final boolean doAdvance() {
            AbstractSpinedBuffer abstractSpinedBuffer = this.buffer;
            if (abstractSpinedBuffer == null) {
                if (this.finished) {
                    return false;
                }
                init();
                initPartialTraversalState();
                this.nextToConsume = 0L;
                this.bufferSink.begin(this.spliterator.getExactSizeIfKnown());
                return fillBuffer();
            }
            long j = this.nextToConsume + 1;
            this.nextToConsume = j;
            boolean z = j < abstractSpinedBuffer.count();
            if (z) {
                return z;
            }
            this.nextToConsume = 0L;
            this.buffer.clear();
            return fillBuffer();
        }

        public Spliterator trySplit() {
            if (!this.isParallel || this.buffer != null || this.finished) {
                return null;
            }
            init();
            Spliterator trySplit = this.spliterator.trySplit();
            if (trySplit == null) {
                return null;
            }
            return wrap(trySplit);
        }

        private boolean fillBuffer() {
            while (this.buffer.count() == 0) {
                if (this.bufferSink.cancellationRequested() || !this.pusher.getAsBoolean()) {
                    if (this.finished) {
                        return false;
                    }
                    this.bufferSink.end();
                    this.finished = true;
                }
            }
            return true;
        }

        public final long estimateSize() {
            init();
            return this.spliterator.estimateSize();
        }

        public final long getExactSizeIfKnown() {
            init();
            if (StreamOpFlag.SIZED.isKnown(this.ph.getStreamAndOpFlags())) {
                return this.spliterator.getExactSizeIfKnown();
            }
            return -1L;
        }

        public final int characteristics() {
            init();
            int characteristics = StreamOpFlag.toCharacteristics(StreamOpFlag.toStreamFlags(this.ph.getStreamAndOpFlags()));
            return (characteristics & 64) != 0 ? (characteristics & (-16449)) | (this.spliterator.characteristics() & 16448) : characteristics;
        }

        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        public final String toString() {
            return String.format("%s[%s]", getClass().getName(), this.spliterator);
        }
    }

    static final class WrappingSpliterator extends AbstractWrappingSpliterator {
        WrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
            super(pipelineHelper, supplier, z);
        }

        WrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
            super(pipelineHelper, spliterator, z);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public WrappingSpliterator wrap(Spliterator spliterator) {
            return new WrappingSpliterator(this.ph, spliterator, this.isParallel);
        }

        void initPartialTraversalState() {
            SpinedBuffer spinedBuffer = new SpinedBuffer();
            this.buffer = spinedBuffer;
            this.bufferSink = this.ph.wrapSink(new StreamSpliterators$WrappingSpliterator$$ExternalSyntheticLambda0(spinedBuffer));
            this.pusher = new StreamSpliterators$WrappingSpliterator$$ExternalSyntheticLambda1(this);
        }

        /* synthetic */ boolean lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$WrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            boolean doAdvance = doAdvance();
            if (doAdvance) {
                consumer.accept(((SpinedBuffer) this.buffer).get(this.nextToConsume));
            }
            return doAdvance;
        }

        public void forEachRemaining(Consumer consumer) {
            if (this.buffer == null && !this.finished) {
                consumer.getClass();
                init();
                PipelineHelper pipelineHelper = this.ph;
                consumer.getClass();
                pipelineHelper.wrapAndCopyInto(new StreamSpliterators$WrappingSpliterator$$ExternalSyntheticLambda2(consumer), this.spliterator);
                this.finished = true;
                return;
            }
            while (tryAdvance(consumer)) {
            }
        }
    }

    static final class IntWrappingSpliterator extends AbstractWrappingSpliterator implements Spliterator.OfInt {
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
        }

        IntWrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
            super(pipelineHelper, supplier, z);
        }

        IntWrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
            super(pipelineHelper, spliterator, z);
        }

        AbstractWrappingSpliterator wrap(Spliterator spliterator) {
            return new IntWrappingSpliterator(this.ph, spliterator, this.isParallel);
        }

        void initPartialTraversalState() {
            SpinedBuffer.OfInt ofInt = new SpinedBuffer.OfInt();
            this.buffer = ofInt;
            this.bufferSink = this.ph.wrapSink(new StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda1(ofInt));
            this.pusher = new StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda2(this);
        }

        /* synthetic */ boolean lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$IntWrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt) super.trySplit();
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            intConsumer.getClass();
            boolean doAdvance = doAdvance();
            if (doAdvance) {
                intConsumer.accept(((SpinedBuffer.OfInt) this.buffer).get(this.nextToConsume));
            }
            return doAdvance;
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            if (this.buffer == null && !this.finished) {
                intConsumer.getClass();
                init();
                PipelineHelper pipelineHelper = this.ph;
                intConsumer.getClass();
                pipelineHelper.wrapAndCopyInto(new StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda0(intConsumer), this.spliterator);
                this.finished = true;
                return;
            }
            while (tryAdvance(intConsumer)) {
            }
        }
    }

    static final class LongWrappingSpliterator extends AbstractWrappingSpliterator implements Spliterator.OfLong {
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
        }

        LongWrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
            super(pipelineHelper, supplier, z);
        }

        LongWrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
            super(pipelineHelper, spliterator, z);
        }

        AbstractWrappingSpliterator wrap(Spliterator spliterator) {
            return new LongWrappingSpliterator(this.ph, spliterator, this.isParallel);
        }

        void initPartialTraversalState() {
            SpinedBuffer.OfLong ofLong = new SpinedBuffer.OfLong();
            this.buffer = ofLong;
            this.bufferSink = this.ph.wrapSink(new StreamSpliterators$LongWrappingSpliterator$$ExternalSyntheticLambda1(ofLong));
            this.pusher = new StreamSpliterators$LongWrappingSpliterator$$ExternalSyntheticLambda2(this);
        }

        /* synthetic */ boolean lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$LongWrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        public Spliterator.OfLong trySplit() {
            return (Spliterator.OfLong) super.trySplit();
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            longConsumer.getClass();
            boolean doAdvance = doAdvance();
            if (doAdvance) {
                longConsumer.accept(((SpinedBuffer.OfLong) this.buffer).get(this.nextToConsume));
            }
            return doAdvance;
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            if (this.buffer == null && !this.finished) {
                longConsumer.getClass();
                init();
                PipelineHelper pipelineHelper = this.ph;
                longConsumer.getClass();
                pipelineHelper.wrapAndCopyInto(new StreamSpliterators$LongWrappingSpliterator$$ExternalSyntheticLambda0(longConsumer), this.spliterator);
                this.finished = true;
                return;
            }
            while (tryAdvance(longConsumer)) {
            }
        }
    }

    static final class DoubleWrappingSpliterator extends AbstractWrappingSpliterator implements Spliterator.OfDouble {
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
        }

        DoubleWrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
            super(pipelineHelper, supplier, z);
        }

        DoubleWrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
            super(pipelineHelper, spliterator, z);
        }

        AbstractWrappingSpliterator wrap(Spliterator spliterator) {
            return new DoubleWrappingSpliterator(this.ph, spliterator, this.isParallel);
        }

        void initPartialTraversalState() {
            SpinedBuffer.OfDouble ofDouble = new SpinedBuffer.OfDouble();
            this.buffer = ofDouble;
            this.bufferSink = this.ph.wrapSink(new StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda1(ofDouble));
            this.pusher = new StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda2(this);
        }

        /* synthetic */ boolean lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$DoubleWrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        public Spliterator.OfDouble trySplit() {
            return (Spliterator.OfDouble) super.trySplit();
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            boolean doAdvance = doAdvance();
            if (doAdvance) {
                doubleConsumer.accept(((SpinedBuffer.OfDouble) this.buffer).get(this.nextToConsume));
            }
            return doAdvance;
        }

        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            if (this.buffer == null && !this.finished) {
                doubleConsumer.getClass();
                init();
                PipelineHelper pipelineHelper = this.ph;
                doubleConsumer.getClass();
                pipelineHelper.wrapAndCopyInto(new StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda0(doubleConsumer), this.spliterator);
                this.finished = true;
                return;
            }
            while (tryAdvance(doubleConsumer)) {
            }
        }
    }

    static class DelegatingSpliterator implements Spliterator {
        private Spliterator s;
        private final Supplier supplier;

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        DelegatingSpliterator(Supplier supplier) {
            this.supplier = supplier;
        }

        Spliterator get() {
            if (this.s == null) {
                this.s = (Spliterator) this.supplier.get();
            }
            return this.s;
        }

        public Spliterator trySplit() {
            return get().trySplit();
        }

        public boolean tryAdvance(Consumer consumer) {
            return get().tryAdvance(consumer);
        }

        public void forEachRemaining(Consumer consumer) {
            get().forEachRemaining(consumer);
        }

        public long estimateSize() {
            return get().estimateSize();
        }

        public int characteristics() {
            return get().characteristics();
        }

        public Comparator getComparator() {
            return get().getComparator();
        }

        public long getExactSizeIfKnown() {
            return get().getExactSizeIfKnown();
        }

        public String toString() {
            return getClass().getName() + "[" + get() + "]";
        }

        static class OfPrimitive extends DelegatingSpliterator implements Spliterator.OfPrimitive {
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfPrimitive(Supplier supplier) {
                super(supplier);
            }

            public boolean tryAdvance(Object obj) {
                return ((Spliterator.OfPrimitive) get()).tryAdvance(obj);
            }

            public void forEachRemaining(Object obj) {
                ((Spliterator.OfPrimitive) get()).forEachRemaining(obj);
            }
        }

        static final class OfInt extends OfPrimitive implements Spliterator.OfInt {
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            OfInt(Supplier supplier) {
                super(supplier);
            }
        }

        static final class OfLong extends OfPrimitive implements Spliterator.OfLong {
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            OfLong(Supplier supplier) {
                super(supplier);
            }
        }

        static final class OfDouble extends OfPrimitive implements Spliterator.OfDouble {
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            OfDouble(Supplier supplier) {
                super(supplier);
            }
        }
    }

    static abstract class SliceSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        long fence;
        long index;
        Spliterator s;
        final long sliceFence;
        final long sliceOrigin;

        protected abstract Spliterator makeSpliterator(Spliterator spliterator, long j, long j2, long j3, long j4);

        SliceSpliterator(Spliterator spliterator, long j, long j2, long j3, long j4) {
            this.s = spliterator;
            this.sliceOrigin = j;
            this.sliceFence = j2;
            this.index = j3;
            this.fence = j4;
        }

        public Spliterator trySplit() {
            long j = this.sliceOrigin;
            long j2 = this.fence;
            if (j >= j2 || this.index >= j2) {
                return null;
            }
            while (true) {
                Spliterator trySplit = this.s.trySplit();
                if (trySplit == null) {
                    return null;
                }
                long estimateSize = this.index + trySplit.estimateSize();
                long min = Math.min(estimateSize, this.sliceFence);
                long j3 = this.sliceOrigin;
                if (j3 >= min) {
                    this.index = min;
                } else {
                    long j4 = this.sliceFence;
                    if (min >= j4) {
                        this.s = trySplit;
                        this.fence = min;
                    } else {
                        long j5 = this.index;
                        if (j5 >= j3 && estimateSize <= j4) {
                            this.index = min;
                            return trySplit;
                        }
                        this.index = min;
                        return makeSpliterator(trySplit, j3, j4, j5, min);
                    }
                }
            }
        }

        public long estimateSize() {
            long j = this.sliceOrigin;
            long j2 = this.fence;
            if (j < j2) {
                return j2 - Math.max(j, this.index);
            }
            return 0L;
        }

        public int characteristics() {
            return this.s.characteristics();
        }

        static final class OfRef extends SliceSpliterator implements Spliterator {
            static /* synthetic */ void lambda$forEachRemaining$1(Object obj) {
            }

            static /* synthetic */ void lambda$tryAdvance$0(Object obj) {
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

            OfRef(Spliterator spliterator, long j, long j2) {
                this(spliterator, j, j2, 0L, Math.min(spliterator.estimateSize(), j2));
            }

            private OfRef(Spliterator spliterator, long j, long j2, long j3, long j4) {
                super(spliterator, j, j2, j3, j4);
            }

            protected Spliterator makeSpliterator(Spliterator spliterator, long j, long j2, long j3, long j4) {
                return new OfRef(spliterator, j, j2, j3, j4);
            }

            public boolean tryAdvance(Consumer consumer) {
                consumer.getClass();
                if (this.sliceOrigin >= this.fence) {
                    return false;
                }
                while (this.sliceOrigin > this.index) {
                    this.s.tryAdvance(new StreamSpliterators$SliceSpliterator$OfRef$$ExternalSyntheticLambda0());
                    this.index++;
                }
                if (this.index >= this.fence) {
                    return false;
                }
                this.index++;
                return this.s.tryAdvance(consumer);
            }

            public void forEachRemaining(Consumer consumer) {
                consumer.getClass();
                if (this.sliceOrigin < this.fence && this.index < this.fence) {
                    if (this.index >= this.sliceOrigin && this.index + this.s.estimateSize() <= this.sliceFence) {
                        this.s.forEachRemaining(consumer);
                        this.index = this.fence;
                        return;
                    }
                    while (this.sliceOrigin > this.index) {
                        this.s.tryAdvance(new StreamSpliterators$SliceSpliterator$OfRef$$ExternalSyntheticLambda1());
                        this.index++;
                    }
                    while (this.index < this.fence) {
                        this.s.tryAdvance(consumer);
                        this.index++;
                    }
                }
            }
        }

        static abstract class OfPrimitive extends SliceSpliterator implements Spliterator.OfPrimitive {
            /* synthetic */ OfPrimitive(Spliterator.OfPrimitive ofPrimitive, long j, long j2, long j3, long j4, StreamSpliterators-IA r10) {
                this(ofPrimitive, j, j2, j3, j4);
            }

            protected abstract Object emptyConsumer();

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

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfPrimitive(Spliterator.OfPrimitive ofPrimitive, long j, long j2) {
                this(ofPrimitive, j, j2, 0L, Math.min(ofPrimitive.estimateSize(), j2));
            }

            private OfPrimitive(Spliterator.OfPrimitive ofPrimitive, long j, long j2, long j3, long j4) {
                super(ofPrimitive, j, j2, j3, j4);
            }

            public boolean tryAdvance(Object obj) {
                obj.getClass();
                if (this.sliceOrigin >= this.fence) {
                    return false;
                }
                while (this.sliceOrigin > this.index) {
                    ((Spliterator.OfPrimitive) this.s).tryAdvance(emptyConsumer());
                    this.index++;
                }
                if (this.index >= this.fence) {
                    return false;
                }
                this.index++;
                return ((Spliterator.OfPrimitive) this.s).tryAdvance(obj);
            }

            public void forEachRemaining(Object obj) {
                obj.getClass();
                if (this.sliceOrigin < this.fence && this.index < this.fence) {
                    if (this.index >= this.sliceOrigin && this.index + ((Spliterator.OfPrimitive) this.s).estimateSize() <= this.sliceFence) {
                        ((Spliterator.OfPrimitive) this.s).forEachRemaining(obj);
                        this.index = this.fence;
                        return;
                    }
                    while (this.sliceOrigin > this.index) {
                        ((Spliterator.OfPrimitive) this.s).tryAdvance(emptyConsumer());
                        this.index++;
                    }
                    while (this.index < this.fence) {
                        ((Spliterator.OfPrimitive) this.s).tryAdvance(obj);
                        this.index++;
                    }
                }
            }
        }

        static final class OfInt extends OfPrimitive implements Spliterator.OfInt {
            static /* synthetic */ void lambda$emptyConsumer$0(int i) {
            }

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

            OfInt(Spliterator.OfInt ofInt, long j, long j2) {
                super(ofInt, j, j2);
            }

            OfInt(Spliterator.OfInt ofInt, long j, long j2, long j3, long j4) {
                super(ofInt, j, j2, j3, j4, null);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt, long j, long j2, long j3, long j4) {
                return new OfInt(ofInt, j, j2, j3, j4);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public IntConsumer emptyConsumer() {
                return new StreamSpliterators$SliceSpliterator$OfInt$$ExternalSyntheticLambda0();
            }
        }

        static final class OfLong extends OfPrimitive implements Spliterator.OfLong {
            static /* synthetic */ void lambda$emptyConsumer$0(long j) {
            }

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

            OfLong(Spliterator.OfLong ofLong, long j, long j2) {
                super(ofLong, j, j2);
            }

            OfLong(Spliterator.OfLong ofLong, long j, long j2, long j3, long j4) {
                super(ofLong, j, j2, j3, j4, null);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong, long j, long j2, long j3, long j4) {
                return new OfLong(ofLong, j, j2, j3, j4);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public LongConsumer emptyConsumer() {
                return new StreamSpliterators$SliceSpliterator$OfLong$$ExternalSyntheticLambda0();
            }
        }

        static final class OfDouble extends OfPrimitive implements Spliterator.OfDouble {
            static /* synthetic */ void lambda$emptyConsumer$0(double d) {
            }

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

            OfDouble(Spliterator.OfDouble ofDouble, long j, long j2) {
                super(ofDouble, j, j2);
            }

            OfDouble(Spliterator.OfDouble ofDouble, long j, long j2, long j3, long j4) {
                super(ofDouble, j, j2, j3, j4, null);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble, long j, long j2, long j3, long j4) {
                return new OfDouble(ofDouble, j, j2, j3, j4);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public DoubleConsumer emptyConsumer() {
                return new StreamSpliterators$SliceSpliterator$OfDouble$$ExternalSyntheticLambda0();
            }
        }
    }

    static abstract class UnorderedSliceSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CHUNK_SIZE = 128;
        protected final int chunkSize;
        private final AtomicLong permits;
        protected final Spliterator s;
        private final long skipThreshold;
        protected final boolean unlimited;

        enum PermitStatus {
            NO_MORE,
            MAYBE_MORE,
            UNLIMITED
        }

        protected abstract Spliterator makeSpliterator(Spliterator spliterator);

        UnorderedSliceSpliterator(Spliterator spliterator, long j, long j2) {
            this.s = spliterator;
            this.unlimited = j2 < 0;
            this.skipThreshold = j2 >= 0 ? j2 : 0L;
            this.chunkSize = 128;
            this.permits = new AtomicLong(j2 >= 0 ? j + j2 : j);
        }

        UnorderedSliceSpliterator(Spliterator spliterator, UnorderedSliceSpliterator unorderedSliceSpliterator) {
            this.s = spliterator;
            this.unlimited = unorderedSliceSpliterator.unlimited;
            this.permits = unorderedSliceSpliterator.permits;
            this.skipThreshold = unorderedSliceSpliterator.skipThreshold;
            this.chunkSize = unorderedSliceSpliterator.chunkSize;
        }

        protected final long acquirePermits(long j) {
            long j2;
            long min;
            do {
                j2 = this.permits.get();
                if (j2 == 0) {
                    if (this.unlimited) {
                        return j;
                    }
                    return 0L;
                }
                min = Math.min(j2, j);
                if (min <= 0) {
                    break;
                }
            } while (!this.permits.compareAndSet(j2, j2 - min));
            if (this.unlimited) {
                return Math.max(j - min, 0L);
            }
            long j3 = this.skipThreshold;
            return j2 > j3 ? Math.max(min - (j2 - j3), 0L) : min;
        }

        protected final PermitStatus permitStatus() {
            if (this.permits.get() > 0) {
                return PermitStatus.MAYBE_MORE;
            }
            return this.unlimited ? PermitStatus.UNLIMITED : PermitStatus.NO_MORE;
        }

        public final Spliterator trySplit() {
            Spliterator trySplit;
            if (this.permits.get() == 0 || (trySplit = this.s.trySplit()) == null) {
                return null;
            }
            return makeSpliterator(trySplit);
        }

        public final long estimateSize() {
            return this.s.estimateSize();
        }

        public final int characteristics() {
            return this.s.characteristics() & (-16465);
        }

        static final class OfRef extends UnorderedSliceSpliterator implements Spliterator, Consumer {
            Object tmpSlot;

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.-CC.$default$andThen(this, consumer);
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

            OfRef(Spliterator spliterator, long j, long j2) {
                super(spliterator, j, j2);
            }

            OfRef(Spliterator spliterator, OfRef ofRef) {
                super(spliterator, ofRef);
            }

            public final void accept(Object obj) {
                this.tmpSlot = obj;
            }

            public boolean tryAdvance(Consumer consumer) {
                consumer.getClass();
                while (permitStatus() != PermitStatus.NO_MORE && this.s.tryAdvance(this)) {
                    if (acquirePermits(1L) == 1) {
                        consumer.accept(this.tmpSlot);
                        this.tmpSlot = null;
                        return true;
                    }
                }
                return false;
            }

            public void forEachRemaining(Consumer consumer) {
                consumer.getClass();
                ArrayBuffer.OfRef ofRef = null;
                while (true) {
                    PermitStatus permitStatus = permitStatus();
                    if (permitStatus == PermitStatus.NO_MORE) {
                        return;
                    }
                    if (permitStatus == PermitStatus.MAYBE_MORE) {
                        if (ofRef == null) {
                            ofRef = new ArrayBuffer.OfRef(this.chunkSize);
                        } else {
                            ofRef.reset();
                        }
                        long j = 0;
                        while (this.s.tryAdvance(ofRef)) {
                            j++;
                            if (j >= this.chunkSize) {
                                break;
                            }
                        }
                        if (j == 0) {
                            return;
                        } else {
                            ofRef.forEach(consumer, acquirePermits(j));
                        }
                    } else {
                        this.s.forEachRemaining(consumer);
                        return;
                    }
                }
            }

            protected Spliterator makeSpliterator(Spliterator spliterator) {
                return new OfRef(spliterator, this);
            }
        }

        static abstract class OfPrimitive extends UnorderedSliceSpliterator implements Spliterator.OfPrimitive {
            protected abstract void acceptConsumed(Object obj);

            protected abstract ArrayBuffer.OfPrimitive bufferCreate(int i);

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

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfPrimitive(Spliterator.OfPrimitive ofPrimitive, long j, long j2) {
                super(ofPrimitive, j, j2);
            }

            OfPrimitive(Spliterator.OfPrimitive ofPrimitive, OfPrimitive ofPrimitive2) {
                super(ofPrimitive, ofPrimitive2);
            }

            public boolean tryAdvance(Object obj) {
                obj.getClass();
                while (permitStatus() != PermitStatus.NO_MORE && ((Spliterator.OfPrimitive) this.s).tryAdvance(this)) {
                    if (acquirePermits(1L) == 1) {
                        acceptConsumed(obj);
                        return true;
                    }
                }
                return false;
            }

            public void forEachRemaining(Object obj) {
                obj.getClass();
                ArrayBuffer.OfPrimitive ofPrimitive = null;
                while (true) {
                    PermitStatus permitStatus = permitStatus();
                    if (permitStatus == PermitStatus.NO_MORE) {
                        return;
                    }
                    if (permitStatus == PermitStatus.MAYBE_MORE) {
                        if (ofPrimitive == null) {
                            ofPrimitive = bufferCreate(this.chunkSize);
                        } else {
                            ofPrimitive.reset();
                        }
                        long j = 0;
                        while (((Spliterator.OfPrimitive) this.s).tryAdvance(ofPrimitive)) {
                            j++;
                            if (j >= this.chunkSize) {
                                break;
                            }
                        }
                        if (j == 0) {
                            return;
                        } else {
                            ofPrimitive.forEach(obj, acquirePermits(j));
                        }
                    } else {
                        ((Spliterator.OfPrimitive) this.s).forEachRemaining(obj);
                        return;
                    }
                }
            }
        }

        static final class OfInt extends OfPrimitive implements Spliterator.OfInt, IntConsumer {
            int tmpValue;

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

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

            OfInt(Spliterator.OfInt ofInt, long j, long j2) {
                super(ofInt, j, j2);
            }

            OfInt(Spliterator.OfInt ofInt, OfInt ofInt2) {
                super(ofInt, ofInt2);
            }

            public void accept(int i) {
                this.tmpValue = i;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public void acceptConsumed(IntConsumer intConsumer) {
                intConsumer.accept(this.tmpValue);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public ArrayBuffer.OfInt bufferCreate(int i) {
                return new ArrayBuffer.OfInt(i);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
                return new OfInt(ofInt, this);
            }
        }

        static final class OfLong extends OfPrimitive implements Spliterator.OfLong, LongConsumer {
            long tmpValue;

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

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

            OfLong(Spliterator.OfLong ofLong, long j, long j2) {
                super(ofLong, j, j2);
            }

            OfLong(Spliterator.OfLong ofLong, OfLong ofLong2) {
                super(ofLong, ofLong2);
            }

            public void accept(long j) {
                this.tmpValue = j;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public void acceptConsumed(LongConsumer longConsumer) {
                longConsumer.accept(this.tmpValue);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public ArrayBuffer.OfLong bufferCreate(int i) {
                return new ArrayBuffer.OfLong(i);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
                return new OfLong(ofLong, this);
            }
        }

        static final class OfDouble extends OfPrimitive implements Spliterator.OfDouble, DoubleConsumer {
            double tmpValue;

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

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

            OfDouble(Spliterator.OfDouble ofDouble, long j, long j2) {
                super(ofDouble, j, j2);
            }

            OfDouble(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }

            public void accept(double d) {
                this.tmpValue = d;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public void acceptConsumed(DoubleConsumer doubleConsumer) {
                doubleConsumer.accept(this.tmpValue);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public ArrayBuffer.OfDouble bufferCreate(int i) {
                return new ArrayBuffer.OfDouble(i);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
                return new OfDouble(ofDouble, this);
            }
        }
    }

    static final class DistinctSpliterator implements Spliterator, Consumer {
        private static final Object NULL_VALUE = new Object();
        private final Spliterator s;
        private final ConcurrentHashMap seen;
        private Object tmpSlot;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        DistinctSpliterator(Spliterator spliterator) {
            this(spliterator, new ConcurrentHashMap());
        }

        private DistinctSpliterator(Spliterator spliterator, ConcurrentHashMap concurrentHashMap) {
            this.s = spliterator;
            this.seen = concurrentHashMap;
        }

        public void accept(Object obj) {
            this.tmpSlot = obj;
        }

        private Object mapNull(Object obj) {
            return obj != null ? obj : NULL_VALUE;
        }

        public boolean tryAdvance(Consumer consumer) {
            while (this.s.tryAdvance(this)) {
                if (this.seen.putIfAbsent(mapNull(this.tmpSlot), Boolean.TRUE) == null) {
                    consumer.accept(this.tmpSlot);
                    this.tmpSlot = null;
                    return true;
                }
            }
            return false;
        }

        public void forEachRemaining(Consumer consumer) {
            this.s.forEachRemaining(new StreamSpliterators$DistinctSpliterator$$ExternalSyntheticLambda0(this, consumer));
        }

        /* synthetic */ void lambda$forEachRemaining$0$java-util-stream-StreamSpliterators$DistinctSpliterator(Consumer consumer, Object obj) {
            if (this.seen.putIfAbsent(mapNull(obj), Boolean.TRUE) == null) {
                consumer.accept(obj);
            }
        }

        public Spliterator trySplit() {
            Spliterator trySplit = this.s.trySplit();
            if (trySplit != null) {
                return new DistinctSpliterator(trySplit, this.seen);
            }
            return null;
        }

        public long estimateSize() {
            return this.s.estimateSize();
        }

        public int characteristics() {
            return (this.s.characteristics() & (-16469)) | 1;
        }

        public Comparator getComparator() {
            return this.s.getComparator();
        }
    }

    static abstract class InfiniteSupplyingSpliterator implements Spliterator {
        long estimate;

        public int characteristics() {
            return 1024;
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

        protected InfiniteSupplyingSpliterator(long j) {
            this.estimate = j;
        }

        public long estimateSize() {
            return this.estimate;
        }

        static final class OfRef extends InfiniteSupplyingSpliterator {
            final Supplier s;

            OfRef(long j, Supplier supplier) {
                super(j);
                this.s = supplier;
            }

            public boolean tryAdvance(Consumer consumer) {
                consumer.getClass();
                consumer.accept(this.s.get());
                return true;
            }

            public Spliterator trySplit() {
                if (this.estimate == 0) {
                    return null;
                }
                long j = this.estimate >>> 1;
                this.estimate = j;
                return new OfRef(j, this.s);
            }
        }

        static final class OfInt extends InfiniteSupplyingSpliterator implements Spliterator.OfInt {
            final IntSupplier s;

            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                Spliterator.OfInt.-CC.$default$forEachRemaining(this, obj);
            }

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
            }

            public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, intConsumer);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
            }

            OfInt(long j, IntSupplier intSupplier) {
                super(j);
                this.s = intSupplier;
            }

            public boolean tryAdvance(IntConsumer intConsumer) {
                intConsumer.getClass();
                intConsumer.accept(this.s.getAsInt());
                return true;
            }

            public Spliterator.OfInt trySplit() {
                if (this.estimate == 0) {
                    return null;
                }
                long j = this.estimate >>> 1;
                this.estimate = j;
                return new OfInt(j, this.s);
            }
        }

        static final class OfLong extends InfiniteSupplyingSpliterator implements Spliterator.OfLong {
            final LongSupplier s;

            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                Spliterator.OfLong.-CC.$default$forEachRemaining(this, obj);
            }

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
            }

            public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, longConsumer);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
            }

            OfLong(long j, LongSupplier longSupplier) {
                super(j);
                this.s = longSupplier;
            }

            public boolean tryAdvance(LongConsumer longConsumer) {
                longConsumer.getClass();
                longConsumer.accept(this.s.getAsLong());
                return true;
            }

            public Spliterator.OfLong trySplit() {
                if (this.estimate == 0) {
                    return null;
                }
                long j = this.estimate >>> 1;
                this.estimate = j;
                return new OfLong(j, this.s);
            }
        }

        static final class OfDouble extends InfiniteSupplyingSpliterator implements Spliterator.OfDouble {
            final DoubleSupplier s;

            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining(this, obj);
            }

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
            }

            public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, doubleConsumer);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
            }

            OfDouble(long j, DoubleSupplier doubleSupplier) {
                super(j);
                this.s = doubleSupplier;
            }

            public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                doubleConsumer.getClass();
                doubleConsumer.accept(this.s.getAsDouble());
                return true;
            }

            public Spliterator.OfDouble trySplit() {
                if (this.estimate == 0) {
                    return null;
                }
                long j = this.estimate >>> 1;
                this.estimate = j;
                return new OfDouble(j, this.s);
            }
        }
    }

    static abstract class ArrayBuffer {
        int index;

        ArrayBuffer() {
        }

        void reset() {
            this.index = 0;
        }

        static final class OfRef extends ArrayBuffer implements Consumer {
            final Object[] array;

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.-CC.$default$andThen(this, consumer);
            }

            OfRef(int i) {
                this.array = new Object[i];
            }

            public void accept(Object obj) {
                Object[] objArr = this.array;
                int i = this.index;
                this.index = i + 1;
                objArr[i] = obj;
            }

            public void forEach(Consumer consumer, long j) {
                for (int i = 0; i < j; i++) {
                    consumer.accept(this.array[i]);
                }
            }
        }

        static abstract class OfPrimitive extends ArrayBuffer {
            int index;

            abstract void forEach(Object obj, long j);

            OfPrimitive() {
            }

            void reset() {
                this.index = 0;
            }
        }

        static final class OfInt extends OfPrimitive implements IntConsumer {
            final int[] array;

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            OfInt(int i) {
                this.array = new int[i];
            }

            public void accept(int i) {
                int[] iArr = this.array;
                int i2 = this.index;
                this.index = i2 + 1;
                iArr[i2] = i;
            }

            public void forEach(IntConsumer intConsumer, long j) {
                for (int i = 0; i < j; i++) {
                    intConsumer.accept(this.array[i]);
                }
            }
        }

        static final class OfLong extends OfPrimitive implements LongConsumer {
            final long[] array;

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            OfLong(int i) {
                this.array = new long[i];
            }

            public void accept(long j) {
                long[] jArr = this.array;
                int i = this.index;
                this.index = i + 1;
                jArr[i] = j;
            }

            public void forEach(LongConsumer longConsumer, long j) {
                for (int i = 0; i < j; i++) {
                    longConsumer.accept(this.array[i]);
                }
            }
        }

        static final class OfDouble extends OfPrimitive implements DoubleConsumer {
            final double[] array;

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            OfDouble(int i) {
                this.array = new double[i];
            }

            public void accept(double d) {
                double[] dArr = this.array;
                int i = this.index;
                this.index = i + 1;
                dArr[i] = d;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public void forEach(DoubleConsumer doubleConsumer, long j) {
                for (int i = 0; i < j; i++) {
                    doubleConsumer.accept(this.array[i]);
                }
            }
        }
    }
}
