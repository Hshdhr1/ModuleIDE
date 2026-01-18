package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Spliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface SortedSet extends Set {
    Comparator comparator();

    Object first();

    SortedSet headSet(Object obj);

    Object last();

    Spliterator spliterator();

    SortedSet subSet(Object obj, Object obj2);

    SortedSet tailSet(Object obj);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Spliterator $default$spliterator(SortedSet _this) {
            return _this.new 1(_this, 21);
        }
    }

    class 1 extends Spliterators.IteratorSpliterator {
        1(Collection collection, int i) {
            super(collection, i);
        }

        public Comparator getComparator() {
            return SortedSet.this.comparator();
        }
    }
}
