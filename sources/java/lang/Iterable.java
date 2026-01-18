package java.lang;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Iterable {
    void forEach(Consumer consumer);

    Iterator iterator();

    Spliterator spliterator();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static void $default$forEach(Iterable _this, Consumer consumer) {
            consumer.getClass();
            Iterator it = _this.iterator();
            while (it.hasNext()) {
                consumer.accept(it.next());
            }
        }

        public static Spliterator $default$spliterator(Iterable _this) {
            return Spliterators.spliteratorUnknownSize(_this.iterator(), 0);
        }
    }
}
