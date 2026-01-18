package java.nio.file;

import java.nio.file.WatchEvent;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class StandardWatchEventKinds {
    public static final WatchEvent.Kind OVERFLOW = new StdWatchEventKind("OVERFLOW", Object.class);
    public static final WatchEvent.Kind ENTRY_CREATE = new StdWatchEventKind("ENTRY_CREATE", Path.class);
    public static final WatchEvent.Kind ENTRY_DELETE = new StdWatchEventKind("ENTRY_DELETE", Path.class);
    public static final WatchEvent.Kind ENTRY_MODIFY = new StdWatchEventKind("ENTRY_MODIFY", Path.class);

    private StandardWatchEventKinds() {
    }

    private static class StdWatchEventKind implements WatchEvent.Kind {
        private final String name;
        private final Class type;

        StdWatchEventKind(String str, Class cls) {
            this.name = str;
            this.type = cls;
        }

        public String name() {
            return this.name;
        }

        public Class type() {
            return this.type;
        }

        public String toString() {
            return this.name;
        }
    }
}
