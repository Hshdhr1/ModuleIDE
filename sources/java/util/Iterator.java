package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Iterator {
    void forEachRemaining(Consumer consumer);

    boolean hasNext();

    Object next();

    void remove();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static void $default$remove(Iterator _this) {
            throw new UnsupportedOperationException("remove");
        }

        public static void $default$forEachRemaining(Iterator _this, Consumer consumer) {
            consumer.getClass();
            while (_this.hasNext()) {
                consumer.accept(_this.next());
            }
        }
    }
}
