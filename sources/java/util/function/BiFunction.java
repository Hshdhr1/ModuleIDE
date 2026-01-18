package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BiFunction {
    BiFunction andThen(Function function);

    Object apply(Object obj, Object obj2);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static BiFunction $default$andThen(BiFunction _this, Function function) {
            function.getClass();
            return new BiFunction$$ExternalSyntheticLambda0(_this, function);
        }

        public static /* synthetic */ Object $private$lambda$andThen$0(BiFunction _this, Function function, Object obj, Object obj2) {
            return function.apply(_this.apply(obj, obj2));
        }
    }
}
