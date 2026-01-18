package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.stream.Sink;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
interface Node {
    Object[] asArray(IntFunction intFunction);

    void copyInto(Object[] objArr, int i);

    long count();

    void forEach(Consumer consumer);

    Node getChild(int i);

    int getChildCount();

    StreamShape getShape();

    Spliterator spliterator();

    Node truncate(long j, long j2, IntFunction intFunction);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static int $default$getChildCount(Node _this) {
            return 0;
        }

        public static /* synthetic */ void lambda$truncate$0(Object obj) {
        }

        public static Node $default$getChild(Node _this, int i) {
            throw new IndexOutOfBoundsException();
        }

        public static Node $default$truncate(Node _this, long j, long j2, IntFunction intFunction) {
            if (j == 0 && j2 == _this.count()) {
                return _this;
            }
            Spliterator spliterator = _this.spliterator();
            long j3 = j2 - j;
            Builder builder = Nodes.builder(j3, intFunction);
            builder.begin(j3);
            for (int i = 0; i < j && spliterator.tryAdvance(new Node$$ExternalSyntheticLambda0()); i++) {
            }
            if (j2 == _this.count()) {
                spliterator.forEachRemaining(builder);
            } else {
                for (int i2 = 0; i2 < j3 && spliterator.tryAdvance(builder); i2++) {
                }
            }
            builder.end();
            return builder.build();
        }

        public static StreamShape $default$getShape(Node _this) {
            return StreamShape.REFERENCE;
        }
    }

    public interface Builder extends Sink {
        Node build();

        public interface OfInt extends Builder, Sink.OfInt {
            OfInt build();

            @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
            public final /* synthetic */ class -CC {
                public static /* bridge */ /* synthetic */ Node $default$build(OfInt _this) {
                    return _this.build();
                }
            }
        }

        public interface OfLong extends Builder, Sink.OfLong {
            OfLong build();

            @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
            public final /* synthetic */ class -CC {
                public static /* bridge */ /* synthetic */ Node $default$build(OfLong _this) {
                    return _this.build();
                }
            }
        }

        public interface OfDouble extends Builder, Sink.OfDouble {
            OfDouble build();

            @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
            public final /* synthetic */ class -CC {
                public static /* bridge */ /* synthetic */ Node $default$build(OfDouble _this) {
                    return _this.build();
                }
            }
        }
    }

    public interface OfPrimitive extends Node {
        Object[] asArray(IntFunction intFunction);

        Object asPrimitiveArray();

        void copyInto(Object obj, int i);

        void forEach(Object obj);

        OfPrimitive getChild(int i);

        Object newArray(int i);

        Spliterator.OfPrimitive spliterator();

        OfPrimitive truncate(long j, long j2, IntFunction intFunction);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ Node $default$getChild(OfPrimitive _this, int i) {
                return _this.getChild(i);
            }

            public static /* bridge */ /* synthetic */ Spliterator $default$spliterator(OfPrimitive _this) {
                return _this.spliterator();
            }

            public static /* bridge */ /* synthetic */ Node $default$truncate(OfPrimitive _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static OfPrimitive $default$getChild(OfPrimitive _this, int i) {
                throw new IndexOutOfBoundsException();
            }

            public static Object[] $default$asArray(OfPrimitive _this, IntFunction intFunction) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfPrimitive.asArray");
                }
                if (_this.count() >= 2147483639) {
                    throw new IllegalArgumentException("Stream size exceeds max array size");
                }
                Object[] objArr = (Object[]) intFunction.apply((int) _this.count());
                _this.copyInto(objArr, 0);
                return objArr;
            }
        }
    }

    public interface OfInt extends OfPrimitive {
        void copyInto(Integer[] numArr, int i);

        void forEach(Consumer consumer);

        StreamShape getShape();

        int[] newArray(int i);

        OfInt truncate(long j, long j2, IntFunction intFunction);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* synthetic */ void lambda$truncate$0(int i) {
            }

            public static /* bridge */ /* synthetic */ void $default$copyInto(OfInt _this, Object[] objArr, int i) {
                _this.copyInto((Integer[]) objArr, i);
            }

            public static /* bridge */ /* synthetic */ Object $default$newArray(OfInt _this, int i) {
                return _this.newArray(i);
            }

            public static /* bridge */ /* synthetic */ OfPrimitive $default$truncate(OfInt _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static /* bridge */ /* synthetic */ Node $default$truncate(OfInt _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static void $default$forEach(OfInt _this, Consumer consumer) {
                if (consumer instanceof IntConsumer) {
                    _this.forEach((IntConsumer) consumer);
                    return;
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
                }
                ((Spliterator.OfInt) _this.spliterator()).forEachRemaining(consumer);
            }

            public static void $default$copyInto(OfInt _this, Integer[] numArr, int i) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
                }
                int[] iArr = (int[]) _this.asPrimitiveArray();
                for (int i2 = 0; i2 < iArr.length; i2++) {
                    numArr[i + i2] = Integer.valueOf(iArr[i2]);
                }
            }

            public static OfInt $default$truncate(OfInt _this, long j, long j2, IntFunction intFunction) {
                if (j == 0 && j2 == _this.count()) {
                    return _this;
                }
                long j3 = j2 - j;
                Spliterator.OfInt ofInt = (Spliterator.OfInt) _this.spliterator();
                Builder.OfInt intBuilder = Nodes.intBuilder(j3);
                intBuilder.begin(j3);
                for (int i = 0; i < j && ofInt.tryAdvance((IntConsumer) new Node$OfInt$$ExternalSyntheticLambda0()); i++) {
                }
                if (j2 == _this.count()) {
                    ofInt.forEachRemaining((IntConsumer) intBuilder);
                } else {
                    for (int i2 = 0; i2 < j3 && ofInt.tryAdvance((IntConsumer) intBuilder); i2++) {
                    }
                }
                intBuilder.end();
                return intBuilder.build();
            }

            public static int[] $default$newArray(OfInt _this, int i) {
                return new int[i];
            }

            public static StreamShape $default$getShape(OfInt _this) {
                return StreamShape.INT_VALUE;
            }
        }
    }

    public interface OfLong extends OfPrimitive {
        void copyInto(Long[] lArr, int i);

        void forEach(Consumer consumer);

        StreamShape getShape();

        long[] newArray(int i);

        OfLong truncate(long j, long j2, IntFunction intFunction);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* synthetic */ void lambda$truncate$0(long j) {
            }

            public static /* bridge */ /* synthetic */ void $default$copyInto(OfLong _this, Object[] objArr, int i) {
                _this.copyInto((Long[]) objArr, i);
            }

            public static /* bridge */ /* synthetic */ Object $default$newArray(OfLong _this, int i) {
                return _this.newArray(i);
            }

            public static /* bridge */ /* synthetic */ OfPrimitive $default$truncate(OfLong _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static /* bridge */ /* synthetic */ Node $default$truncate(OfLong _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static void $default$forEach(OfLong _this, Consumer consumer) {
                if (consumer instanceof LongConsumer) {
                    _this.forEach((LongConsumer) consumer);
                    return;
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                }
                ((Spliterator.OfLong) _this.spliterator()).forEachRemaining(consumer);
            }

            public static void $default$copyInto(OfLong _this, Long[] lArr, int i) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
                }
                long[] jArr = (long[]) _this.asPrimitiveArray();
                for (int i2 = 0; i2 < jArr.length; i2++) {
                    lArr[i + i2] = Long.valueOf(jArr[i2]);
                }
            }

            public static OfLong $default$truncate(OfLong _this, long j, long j2, IntFunction intFunction) {
                if (j == 0 && j2 == _this.count()) {
                    return _this;
                }
                long j3 = j2 - j;
                Spliterator.OfLong ofLong = (Spliterator.OfLong) _this.spliterator();
                Builder.OfLong longBuilder = Nodes.longBuilder(j3);
                longBuilder.begin(j3);
                for (int i = 0; i < j && ofLong.tryAdvance((LongConsumer) new Node$OfLong$$ExternalSyntheticLambda0()); i++) {
                }
                if (j2 == _this.count()) {
                    ofLong.forEachRemaining((LongConsumer) longBuilder);
                } else {
                    for (int i2 = 0; i2 < j3 && ofLong.tryAdvance((LongConsumer) longBuilder); i2++) {
                    }
                }
                longBuilder.end();
                return longBuilder.build();
            }

            public static long[] $default$newArray(OfLong _this, int i) {
                return new long[i];
            }

            public static StreamShape $default$getShape(OfLong _this) {
                return StreamShape.LONG_VALUE;
            }
        }
    }

    public interface OfDouble extends OfPrimitive {
        void copyInto(Double[] dArr, int i);

        void forEach(Consumer consumer);

        StreamShape getShape();

        double[] newArray(int i);

        OfDouble truncate(long j, long j2, IntFunction intFunction);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* synthetic */ void lambda$truncate$0(double d) {
            }

            public static /* bridge */ /* synthetic */ void $default$copyInto(OfDouble _this, Object[] objArr, int i) {
                _this.copyInto((Double[]) objArr, i);
            }

            public static /* bridge */ /* synthetic */ Object $default$newArray(OfDouble _this, int i) {
                return _this.newArray(i);
            }

            public static /* bridge */ /* synthetic */ OfPrimitive $default$truncate(OfDouble _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static /* bridge */ /* synthetic */ Node $default$truncate(OfDouble _this, long j, long j2, IntFunction intFunction) {
                return _this.truncate(j, j2, intFunction);
            }

            public static void $default$forEach(OfDouble _this, Consumer consumer) {
                if (consumer instanceof DoubleConsumer) {
                    _this.forEach((DoubleConsumer) consumer);
                    return;
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                }
                ((Spliterator.OfDouble) _this.spliterator()).forEachRemaining(consumer);
            }

            public static void $default$copyInto(OfDouble _this, Double[] dArr, int i) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
                }
                double[] dArr2 = (double[]) _this.asPrimitiveArray();
                for (int i2 = 0; i2 < dArr2.length; i2++) {
                    dArr[i + i2] = Double.valueOf(dArr2[i2]);
                }
            }

            public static OfDouble $default$truncate(OfDouble _this, long j, long j2, IntFunction intFunction) {
                if (j == 0 && j2 == _this.count()) {
                    return _this;
                }
                long j3 = j2 - j;
                Spliterator.OfDouble ofDouble = (Spliterator.OfDouble) _this.spliterator();
                Builder.OfDouble doubleBuilder = Nodes.doubleBuilder(j3);
                doubleBuilder.begin(j3);
                for (int i = 0; i < j && ofDouble.tryAdvance((DoubleConsumer) new Node$OfDouble$$ExternalSyntheticLambda0()); i++) {
                }
                if (j2 == _this.count()) {
                    ofDouble.forEachRemaining((DoubleConsumer) doubleBuilder);
                } else {
                    for (int i2 = 0; i2 < j3 && ofDouble.tryAdvance((DoubleConsumer) doubleBuilder); i2++) {
                    }
                }
                doubleBuilder.end();
                return doubleBuilder.build();
            }

            public static double[] $default$newArray(OfDouble _this, int i) {
                return new double[i];
            }

            public static StreamShape $default$getShape(OfDouble _this) {
                return StreamShape.DOUBLE_VALUE;
            }
        }
    }
}
