package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface LongConsumer {
    void accept(long j);

    LongConsumer andThen(LongConsumer longConsumer);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static LongConsumer $default$andThen(LongConsumer _this, LongConsumer longConsumer) {
            longConsumer.getClass();
            return new LongConsumer$$ExternalSyntheticLambda0(_this, longConsumer);
        }

        public static /* synthetic */ void $private$lambda$andThen$0(LongConsumer _this, LongConsumer longConsumer, long j) {
            _this.accept(j);
            longConsumer.accept(j);
        }
    }
}
