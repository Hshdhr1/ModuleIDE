package androidx.core.content.pm;

import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public abstract class ShortcutInfoCompatSaver {
    public abstract Object addShortcuts(List list);

    public abstract Object removeAllShortcuts();

    public abstract Object removeShortcuts(List list);

    public List getShortcuts() throws Exception {
        return new ArrayList();
    }

    public static class NoopImpl extends ShortcutInfoCompatSaver {
        public Void addShortcuts(List list) {
            return null;
        }

        public Void removeShortcuts(List list) {
            return null;
        }

        public Void removeAllShortcuts() {
            return null;
        }
    }
}
