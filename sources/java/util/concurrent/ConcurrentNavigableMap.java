package java.util.concurrent;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ConcurrentNavigableMap extends ConcurrentMap, NavigableMap {
    NavigableSet descendingKeySet();

    ConcurrentNavigableMap descendingMap();

    ConcurrentNavigableMap headMap(Object obj);

    ConcurrentNavigableMap headMap(Object obj, boolean z);

    NavigableSet keySet();

    NavigableSet navigableKeySet();

    ConcurrentNavigableMap subMap(Object obj, Object obj2);

    ConcurrentNavigableMap subMap(Object obj, boolean z, Object obj2, boolean z2);

    ConcurrentNavigableMap tailMap(Object obj);

    ConcurrentNavigableMap tailMap(Object obj, boolean z);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ NavigableMap $default$descendingMap(ConcurrentNavigableMap _this) {
            return _this.descendingMap();
        }

        public static /* bridge */ /* synthetic */ NavigableMap $default$headMap(ConcurrentNavigableMap _this, Object obj, boolean z) {
            return _this.headMap(obj, z);
        }

        public static /* bridge */ /* synthetic */ SortedMap $default$headMap(ConcurrentNavigableMap _this, Object obj) {
            return _this.headMap(obj);
        }

        public static /* bridge */ /* synthetic */ Set $default$keySet(ConcurrentNavigableMap _this) {
            return _this.keySet();
        }

        public static /* bridge */ /* synthetic */ NavigableMap $default$subMap(ConcurrentNavigableMap _this, Object obj, boolean z, Object obj2, boolean z2) {
            return _this.subMap(obj, z, obj2, z2);
        }

        public static /* bridge */ /* synthetic */ SortedMap $default$subMap(ConcurrentNavigableMap _this, Object obj, Object obj2) {
            return _this.subMap(obj, obj2);
        }

        public static /* bridge */ /* synthetic */ NavigableMap $default$tailMap(ConcurrentNavigableMap _this, Object obj, boolean z) {
            return _this.tailMap(obj, z);
        }

        public static /* bridge */ /* synthetic */ SortedMap $default$tailMap(ConcurrentNavigableMap _this, Object obj) {
            return _this.tailMap(obj);
        }
    }
}
