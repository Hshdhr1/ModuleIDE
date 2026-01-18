package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Spliterator {
    public static final int CONCURRENT = 4096;
    public static final int DISTINCT = 1;
    public static final int IMMUTABLE = 1024;
    public static final int NONNULL = 256;
    public static final int ORDERED = 16;
    public static final int SIZED = 64;
    public static final int SORTED = 4;
    public static final int SUBSIZED = 16384;

    int characteristics();

    long estimateSize();

    void forEachRemaining(Consumer consumer);

    Comparator getComparator();

    long getExactSizeIfKnown();

    boolean hasCharacteristics(int i);

    boolean tryAdvance(Consumer consumer);

    Spliterator trySplit();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static void $default$forEachRemaining(Spliterator _this, Consumer consumer) {
            while (_this.tryAdvance(consumer)) {
            }
        }

        public static long $default$getExactSizeIfKnown(Spliterator _this) {
            if ((_this.characteristics() & 64) == 0) {
                return -1L;
            }
            return _this.estimateSize();
        }

        public static boolean $default$hasCharacteristics(Spliterator _this, int i) {
            return (_this.characteristics() & i) == i;
        }

        public static Comparator $default$getComparator(Spliterator _this) {
            throw new IllegalStateException();
        }
    }

    public interface OfPrimitive extends Spliterator {
        void forEachRemaining(Object obj);

        boolean tryAdvance(Object obj);

        OfPrimitive trySplit();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ Spliterator $default$trySplit(OfPrimitive _this) {
                return _this.trySplit();
            }

            public static void $default$forEachRemaining(OfPrimitive _this, Object obj) {
                while (_this.tryAdvance(obj)) {
                }
            }
        }
    }

    public interface OfInt extends OfPrimitive {
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(IntConsumer intConsumer);

        boolean tryAdvance(Consumer consumer);

        boolean tryAdvance(IntConsumer intConsumer);

        OfInt trySplit();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfInt _this, Object obj) {
                _this.forEachRemaining((IntConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ boolean $default$tryAdvance(OfInt _this, Object obj) {
                return _this.tryAdvance((IntConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ OfPrimitive $default$trySplit(OfInt _this) {
                return _this.trySplit();
            }

            public static /* bridge */ /* synthetic */ Spliterator $default$trySplit(OfInt _this) {
                return _this.trySplit();
            }

            public static void $default$forEachRemaining(OfInt _this, IntConsumer intConsumer) {
                while (_this.tryAdvance(intConsumer)) {
                }
            }

            public static boolean $default$tryAdvance(OfInt _this, Consumer consumer) {
                if (consumer instanceof IntConsumer) {
                    return _this.tryAdvance((IntConsumer) consumer);
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
                }
                consumer.getClass();
                return _this.tryAdvance((IntConsumer) new Spliterator$OfInt$$ExternalSyntheticLambda0(consumer));
            }

            public static void $default$forEachRemaining(OfInt _this, Consumer consumer) {
                if (consumer instanceof IntConsumer) {
                    _this.forEachRemaining((IntConsumer) consumer);
                    return;
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                }
                consumer.getClass();
                _this.forEachRemaining((IntConsumer) new Spliterator$OfInt$$ExternalSyntheticLambda0(consumer));
            }
        }
    }

    public interface OfLong extends OfPrimitive {
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(LongConsumer longConsumer);

        boolean tryAdvance(Consumer consumer);

        boolean tryAdvance(LongConsumer longConsumer);

        OfLong trySplit();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfLong _this, Object obj) {
                _this.forEachRemaining((LongConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ boolean $default$tryAdvance(OfLong _this, Object obj) {
                return _this.tryAdvance((LongConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ OfPrimitive $default$trySplit(OfLong _this) {
                return _this.trySplit();
            }

            public static /* bridge */ /* synthetic */ Spliterator $default$trySplit(OfLong _this) {
                return _this.trySplit();
            }

            public static void $default$forEachRemaining(OfLong _this, LongConsumer longConsumer) {
                while (_this.tryAdvance(longConsumer)) {
                }
            }

            public static boolean $default$tryAdvance(OfLong _this, Consumer consumer) {
                if (consumer instanceof LongConsumer) {
                    return _this.tryAdvance((LongConsumer) consumer);
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
                }
                consumer.getClass();
                return _this.tryAdvance((LongConsumer) new Spliterator$OfLong$$ExternalSyntheticLambda0(consumer));
            }

            public static void $default$forEachRemaining(OfLong _this, Consumer consumer) {
                if (consumer instanceof LongConsumer) {
                    _this.forEachRemaining((LongConsumer) consumer);
                    return;
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                }
                consumer.getClass();
                _this.forEachRemaining((LongConsumer) new Spliterator$OfLong$$ExternalSyntheticLambda0(consumer));
            }
        }
    }

    public interface OfDouble extends OfPrimitive {
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(DoubleConsumer doubleConsumer);

        boolean tryAdvance(Consumer consumer);

        boolean tryAdvance(DoubleConsumer doubleConsumer);

        OfDouble trySplit();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfDouble _this, Object obj) {
                _this.forEachRemaining((DoubleConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ boolean $default$tryAdvance(OfDouble _this, Object obj) {
                return _this.tryAdvance((DoubleConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ OfPrimitive $default$trySplit(OfDouble _this) {
                return _this.trySplit();
            }

            public static /* bridge */ /* synthetic */ Spliterator $default$trySplit(OfDouble _this) {
                return _this.trySplit();
            }

            public static void $default$forEachRemaining(OfDouble _this, DoubleConsumer doubleConsumer) {
                while (_this.tryAdvance(doubleConsumer)) {
                }
            }

            public static boolean $default$tryAdvance(OfDouble _this, Consumer consumer) {
                if (consumer instanceof DoubleConsumer) {
                    return _this.tryAdvance((DoubleConsumer) consumer);
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
                }
                consumer.getClass();
                return _this.tryAdvance((DoubleConsumer) new Spliterator$OfDouble$$ExternalSyntheticLambda0(consumer));
            }

            public static void $default$forEachRemaining(OfDouble _this, Consumer consumer) {
                if (consumer instanceof DoubleConsumer) {
                    _this.forEachRemaining((DoubleConsumer) consumer);
                    return;
                }
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                }
                consumer.getClass();
                _this.forEachRemaining((DoubleConsumer) new Spliterator$OfDouble$$ExternalSyntheticLambda0(consumer));
            }
        }
    }
}
