package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface PrimitiveIterator extends Iterator {
    void forEachRemaining(Object obj);

    public interface OfInt extends PrimitiveIterator {
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(IntConsumer intConsumer);

        Integer next();

        int nextInt();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfInt _this, Object obj) {
                _this.forEachRemaining((IntConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ Object $default$next(OfInt _this) {
                return _this.next();
            }

            public static void $default$forEachRemaining(OfInt _this, IntConsumer intConsumer) {
                intConsumer.getClass();
                while (_this.hasNext()) {
                    intConsumer.accept(_this.nextInt());
                }
            }

            public static Integer $default$next(OfInt _this) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling PrimitiveIterator.OfInt.nextInt()");
                }
                return Integer.valueOf(_this.nextInt());
            }

            public static void $default$forEachRemaining(OfInt _this, Consumer consumer) {
                if (consumer instanceof IntConsumer) {
                    _this.forEachRemaining((IntConsumer) consumer);
                    return;
                }
                consumer.getClass();
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
                }
                consumer.getClass();
                _this.forEachRemaining((IntConsumer) new PrimitiveIterator$OfInt$$ExternalSyntheticLambda0(consumer));
            }
        }
    }

    public interface OfLong extends PrimitiveIterator {
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(LongConsumer longConsumer);

        Long next();

        long nextLong();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfLong _this, Object obj) {
                _this.forEachRemaining((LongConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ Object $default$next(OfLong _this) {
                return _this.next();
            }

            public static void $default$forEachRemaining(OfLong _this, LongConsumer longConsumer) {
                longConsumer.getClass();
                while (_this.hasNext()) {
                    longConsumer.accept(_this.nextLong());
                }
            }

            public static Long $default$next(OfLong _this) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling PrimitiveIterator.OfLong.nextLong()");
                }
                return Long.valueOf(_this.nextLong());
            }

            public static void $default$forEachRemaining(OfLong _this, Consumer consumer) {
                if (consumer instanceof LongConsumer) {
                    _this.forEachRemaining((LongConsumer) consumer);
                    return;
                }
                consumer.getClass();
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
                }
                consumer.getClass();
                _this.forEachRemaining((LongConsumer) new PrimitiveIterator$OfLong$$ExternalSyntheticLambda0(consumer));
            }
        }
    }

    public interface OfDouble extends PrimitiveIterator {
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(DoubleConsumer doubleConsumer);

        Double next();

        double nextDouble();

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfDouble _this, Object obj) {
                _this.forEachRemaining((DoubleConsumer) obj);
            }

            public static /* bridge */ /* synthetic */ Object $default$next(OfDouble _this) {
                return _this.next();
            }

            public static void $default$forEachRemaining(OfDouble _this, DoubleConsumer doubleConsumer) {
                doubleConsumer.getClass();
                while (_this.hasNext()) {
                    doubleConsumer.accept(_this.nextDouble());
                }
            }

            public static Double $default$next(OfDouble _this) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling PrimitiveIterator.OfDouble.nextLong()");
                }
                return Double.valueOf(_this.nextDouble());
            }

            public static void $default$forEachRemaining(OfDouble _this, Consumer consumer) {
                if (consumer instanceof DoubleConsumer) {
                    _this.forEachRemaining((DoubleConsumer) consumer);
                    return;
                }
                consumer.getClass();
                if (Tripwire.ENABLED) {
                    Tripwire.trip(_this.getClass(), "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
                }
                consumer.getClass();
                _this.forEachRemaining((DoubleConsumer) new PrimitiveIterator$OfDouble$$ExternalSyntheticLambda0(consumer));
            }
        }
    }
}
