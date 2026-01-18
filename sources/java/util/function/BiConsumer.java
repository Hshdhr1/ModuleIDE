package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BiConsumer {
    void accept(Object obj, Object obj2);

    BiConsumer andThen(BiConsumer biConsumer);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static BiConsumer $default$andThen(BiConsumer _this, BiConsumer biConsumer) {
            biConsumer.getClass();
            return new BiConsumer$$ExternalSyntheticLambda0(_this, biConsumer);
        }

        public static /* synthetic */ void $private$lambda$andThen$0(BiConsumer _this, BiConsumer biConsumer, Object obj, Object obj2) {
            _this.accept(obj, obj2);
            biConsumer.accept(obj, obj2);
        }
    }
}
