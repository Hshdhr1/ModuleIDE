package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Function {
    Function andThen(Function function);

    Object apply(Object obj);

    Function compose(Function function);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* synthetic */ Object lambda$identity$2(Object obj) {
            return obj;
        }

        public static Function $default$compose(Function _this, Function function) {
            function.getClass();
            return new Function$$ExternalSyntheticLambda2(_this, function);
        }

        public static /* synthetic */ Object $private$lambda$compose$0(Function _this, Function function, Object obj) {
            return _this.apply(function.apply(obj));
        }

        public static Function $default$andThen(Function _this, Function function) {
            function.getClass();
            return new Function$$ExternalSyntheticLambda1(_this, function);
        }

        public static /* synthetic */ Object $private$lambda$andThen$1(Function _this, Function function, Object obj) {
            return function.apply(_this.apply(obj));
        }

        public static Function identity() {
            return new Function$$ExternalSyntheticLambda0();
        }
    }
}
