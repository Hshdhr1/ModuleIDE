package sun.nio.fs;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractWatchKey implements WatchKey {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int MAX_EVENT_LIST_SIZE = 512;
    static final Event OVERFLOW_EVENT = new Event(StandardWatchEventKinds.OVERFLOW, null);
    private final Path dir;
    private final AbstractWatchService watcher;
    private State state = State.READY;
    private List events = new ArrayList();
    private Map lastModifyEvents = new HashMap();

    private enum State {
        READY,
        SIGNALLED
    }

    protected AbstractWatchKey(Path path, AbstractWatchService abstractWatchService) {
        this.watcher = abstractWatchService;
        this.dir = path;
    }

    final AbstractWatchService watcher() {
        return this.watcher;
    }

    public Path watchable() {
        return this.dir;
    }

    final void signal() {
        synchronized (this) {
            if (this.state == State.READY) {
                this.state = State.SIGNALLED;
                this.watcher.enqueueKey(this);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void signalEvent(java.nio.file.WatchEvent.Kind r7, java.lang.Object r8) {
        /*
            r6 = this;
            java.nio.file.WatchEvent$Kind r0 = java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
            r1 = 0
            if (r7 != r0) goto L7
            r0 = 1
            goto L8
        L7:
            r0 = 0
        L8:
            monitor-enter(r6)
            java.util.List r2 = r6.events     // Catch: java.lang.Throwable -> L89
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L89
            if (r2 <= 0) goto L63
            java.util.List r3 = r6.events     // Catch: java.lang.Throwable -> L89
            int r4 = r2 + (-1)
            java.lang.Object r3 = r3.get(r4)     // Catch: java.lang.Throwable -> L89
            java.nio.file.WatchEvent r3 = (java.nio.file.WatchEvent) r3     // Catch: java.lang.Throwable -> L89
            java.nio.file.WatchEvent$Kind r4 = r3.kind()     // Catch: java.lang.Throwable -> L89
            java.nio.file.WatchEvent$Kind r5 = java.nio.file.StandardWatchEventKinds.OVERFLOW     // Catch: java.lang.Throwable -> L89
            if (r4 == r5) goto L5c
            java.nio.file.WatchEvent$Kind r4 = r3.kind()     // Catch: java.lang.Throwable -> L89
            if (r7 != r4) goto L34
            java.lang.Object r4 = r3.context()     // Catch: java.lang.Throwable -> L89
            boolean r4 = sun.nio.fs.AbstractWatchKey$$ExternalSyntheticBackport0.m(r8, r4)     // Catch: java.lang.Throwable -> L89
            if (r4 == 0) goto L34
            goto L5c
        L34:
            java.util.Map r3 = r6.lastModifyEvents     // Catch: java.lang.Throwable -> L89
            boolean r3 = r3.isEmpty()     // Catch: java.lang.Throwable -> L89
            if (r3 != 0) goto L54
            if (r0 == 0) goto L4f
            java.util.Map r3 = r6.lastModifyEvents     // Catch: java.lang.Throwable -> L89
            java.lang.Object r3 = r3.get(r8)     // Catch: java.lang.Throwable -> L89
            java.nio.file.WatchEvent r3 = (java.nio.file.WatchEvent) r3     // Catch: java.lang.Throwable -> L89
            if (r3 == 0) goto L54
            sun.nio.fs.AbstractWatchKey$Event r3 = (sun.nio.fs.AbstractWatchKey.Event) r3     // Catch: java.lang.Throwable -> L89
            r3.increment()     // Catch: java.lang.Throwable -> L89
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L89
            return
        L4f:
            java.util.Map r3 = r6.lastModifyEvents     // Catch: java.lang.Throwable -> L89
            r3.remove(r8)     // Catch: java.lang.Throwable -> L89
        L54:
            r3 = 512(0x200, float:7.17E-43)
            if (r2 < r3) goto L63
            java.nio.file.WatchEvent$Kind r7 = java.nio.file.StandardWatchEventKinds.OVERFLOW     // Catch: java.lang.Throwable -> L89
            r8 = 0
            goto L64
        L5c:
            sun.nio.fs.AbstractWatchKey$Event r3 = (sun.nio.fs.AbstractWatchKey.Event) r3     // Catch: java.lang.Throwable -> L89
            r3.increment()     // Catch: java.lang.Throwable -> L89
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L89
            return
        L63:
            r1 = r0
        L64:
            sun.nio.fs.AbstractWatchKey$Event r0 = new sun.nio.fs.AbstractWatchKey$Event     // Catch: java.lang.Throwable -> L89
            r0.<init>(r7, r8)     // Catch: java.lang.Throwable -> L89
            if (r1 == 0) goto L71
            java.util.Map r7 = r6.lastModifyEvents     // Catch: java.lang.Throwable -> L89
            r7.put(r8, r0)     // Catch: java.lang.Throwable -> L89
            goto L7f
        L71:
            java.nio.file.WatchEvent$Kind r8 = java.nio.file.StandardWatchEventKinds.OVERFLOW     // Catch: java.lang.Throwable -> L89
            if (r7 != r8) goto L7f
            java.util.List r7 = r6.events     // Catch: java.lang.Throwable -> L89
            r7.clear()     // Catch: java.lang.Throwable -> L89
            java.util.Map r7 = r6.lastModifyEvents     // Catch: java.lang.Throwable -> L89
            r7.clear()     // Catch: java.lang.Throwable -> L89
        L7f:
            java.util.List r7 = r6.events     // Catch: java.lang.Throwable -> L89
            r7.add(r0)     // Catch: java.lang.Throwable -> L89
            r6.signal()     // Catch: java.lang.Throwable -> L89
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L89
            return
        L89:
            r7 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L89
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.AbstractWatchKey.signalEvent(java.nio.file.WatchEvent$Kind, java.lang.Object):void");
    }

    public final List pollEvents() {
        List list;
        synchronized (this) {
            list = this.events;
            this.events = new ArrayList();
            this.lastModifyEvents.clear();
        }
        return list;
    }

    public final boolean reset() {
        boolean isValid;
        synchronized (this) {
            if (this.state == State.SIGNALLED && isValid()) {
                if (this.events.isEmpty()) {
                    this.state = State.READY;
                } else {
                    this.watcher.enqueueKey(this);
                }
            }
            isValid = isValid();
        }
        return isValid;
    }

    private static class Event implements WatchEvent {
        private final Object context;
        private int count = 1;
        private final WatchEvent.Kind kind;

        Event(WatchEvent.Kind kind, Object obj) {
            this.kind = kind;
            this.context = obj;
        }

        public WatchEvent.Kind kind() {
            return this.kind;
        }

        public Object context() {
            return this.context;
        }

        public int count() {
            return this.count;
        }

        void increment() {
            this.count++;
        }
    }
}
