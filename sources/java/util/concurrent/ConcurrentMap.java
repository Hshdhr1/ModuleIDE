package java.util.concurrent;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ConcurrentMap extends Map {
    Object compute(Object obj, BiFunction biFunction);

    Object computeIfAbsent(Object obj, Function function);

    Object computeIfPresent(Object obj, BiFunction biFunction);

    void forEach(BiConsumer biConsumer);

    Object getOrDefault(Object obj, Object obj2);

    Object merge(Object obj, Object obj2, BiFunction biFunction);

    Object putIfAbsent(Object obj, Object obj2);

    boolean remove(Object obj, Object obj2);

    Object replace(Object obj, Object obj2);

    boolean replace(Object obj, Object obj2, Object obj3);

    void replaceAll(BiFunction biFunction);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Object $default$getOrDefault(ConcurrentMap _this, Object obj, Object obj2) {
            Object obj3 = _this.get(obj);
            return obj3 != null ? obj3 : obj2;
        }

        public static void $default$forEach(ConcurrentMap _this, BiConsumer biConsumer) {
            biConsumer.getClass();
            for (Map.Entry entry : _this.entrySet()) {
                try {
                    biConsumer.accept(entry.getKey(), entry.getValue());
                } catch (IllegalStateException unused) {
                }
            }
        }

        public static void $default$replaceAll(ConcurrentMap _this, BiFunction biFunction) {
            biFunction.getClass();
            _this.forEach(new ConcurrentMap$$ExternalSyntheticLambda0(_this, biFunction));
        }

        public static /* synthetic */ void $private$lambda$replaceAll$0(ConcurrentMap _this, BiFunction biFunction, Object obj, Object obj2) {
            while (!_this.replace(obj, obj2, biFunction.apply(obj, obj2)) && (obj2 = _this.get(obj)) != null) {
            }
        }

        public static Object $default$computeIfAbsent(ConcurrentMap _this, Object obj, Function function) {
            Object apply;
            function.getClass();
            Object obj2 = _this.get(obj);
            if (obj2 != null || (apply = function.apply(obj)) == null) {
                return obj2;
            }
            Object putIfAbsent = _this.putIfAbsent(obj, apply);
            return putIfAbsent == null ? apply : putIfAbsent;
        }

        public static Object $default$computeIfPresent(ConcurrentMap _this, Object obj, BiFunction biFunction) {
            Object apply;
            biFunction.getClass();
            while (true) {
                Object obj2 = _this.get(obj);
                if (obj2 == null) {
                    return null;
                }
                apply = biFunction.apply(obj, obj2);
                if (apply == null) {
                    if (_this.remove(obj, obj2)) {
                        break;
                    }
                } else if (_this.replace(obj, obj2, apply)) {
                    break;
                }
            }
            return apply;
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
        
            return r1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.lang.Object $default$compute(java.util.concurrent.ConcurrentMap r2, java.lang.Object r3, java.util.function.BiFunction r4) {
            /*
            L0:
                java.lang.Object r0 = r2.get(r3)
            L4:
                java.lang.Object r1 = r4.apply(r3, r0)
                if (r1 == 0) goto L1a
                if (r0 == 0) goto L13
                boolean r0 = r2.replace(r3, r0, r1)
                if (r0 == 0) goto L0
                goto L19
            L13:
                java.lang.Object r0 = r2.putIfAbsent(r3, r1)
                if (r0 != 0) goto L4
            L19:
                return r1
            L1a:
                if (r0 == 0) goto L22
                boolean r0 = r2.remove(r3, r0)
                if (r0 == 0) goto L0
            L22:
                r3 = 0
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentMap.-CC.$default$compute(java.util.concurrent.ConcurrentMap, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
        }

        public static Object $default$merge(ConcurrentMap _this, Object obj, Object obj2, BiFunction biFunction) {
            biFunction.getClass();
            obj2.getClass();
            while (true) {
                Object obj3 = _this.get(obj);
                while (obj3 == null) {
                    obj3 = _this.putIfAbsent(obj, obj2);
                    if (obj3 == null) {
                        return obj2;
                    }
                }
                Object apply = biFunction.apply(obj3, obj2);
                if (apply != null) {
                    if (_this.replace(obj, obj3, apply)) {
                        return apply;
                    }
                } else if (_this.remove(obj, obj3)) {
                    return null;
                }
            }
        }
    }
}
