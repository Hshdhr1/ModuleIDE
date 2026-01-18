package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Consumer {
    void accept(Object obj);

    Consumer andThen(Consumer consumer);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Consumer $default$andThen(Consumer _this, Consumer consumer) {
            consumer.getClass();
            return new Consumer$$ExternalSyntheticLambda0(_this, consumer);
        }

        public static /* synthetic */ void $private$lambda$andThen$0(Consumer _this, Consumer consumer, Object obj) {
            _this.accept(obj);
            consumer.accept(obj);
        }
    }
}
