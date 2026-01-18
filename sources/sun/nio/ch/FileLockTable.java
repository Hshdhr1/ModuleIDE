package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.channels.Channel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class FileLockTable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ConcurrentHashMap lockMap = new ConcurrentHashMap();
    private static ReferenceQueue queue = new ReferenceQueue();
    private final Channel channel;
    private final FileKey fileKey;
    private final Set locks = new HashSet();

    private static class FileLockReference extends WeakReference {
        private FileKey fileKey;

        FileLockReference(FileLock fileLock, ReferenceQueue referenceQueue, FileKey fileKey) {
            super(fileLock, referenceQueue);
            this.fileKey = fileKey;
        }

        FileKey fileKey() {
            return this.fileKey;
        }
    }

    FileLockTable(Channel channel, FileDescriptor fileDescriptor) throws IOException {
        this.channel = channel;
        this.fileKey = FileKey.create(fileDescriptor);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001f, code lost:
    
        r1.add(new sun.nio.ch.FileLockTable.FileLockReference(r9, sun.nio.ch.FileLockTable.queue, r8.fileKey));
        r8.locks.add(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0050, code lost:
    
        checkList(r3, r9.position(), r9.size());
        r3.add(new sun.nio.ch.FileLockTable.FileLockReference(r9, sun.nio.ch.FileLockTable.queue, r8.fileKey));
        r8.locks.add(r9);
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void add(java.nio.channels.FileLock r9) throws java.nio.channels.OverlappingFileLockException {
        /*
            r8 = this;
            java.util.concurrent.ConcurrentHashMap r0 = sun.nio.ch.FileLockTable.lockMap
            sun.nio.ch.FileKey r1 = r8.fileKey
            java.lang.Object r0 = r0.get(r1)
            java.util.List r0 = (java.util.List) r0
        La:
            if (r0 != 0) goto L39
            java.util.ArrayList r1 = new java.util.ArrayList
            r0 = 2
            r1.<init>(r0)
            monitor-enter(r1)
            java.util.concurrent.ConcurrentHashMap r0 = sun.nio.ch.FileLockTable.lockMap     // Catch: java.lang.Throwable -> L35
            sun.nio.ch.FileKey r2 = r8.fileKey     // Catch: java.lang.Throwable -> L35
            java.lang.Object r0 = r0.putIfAbsent(r2, r1)     // Catch: java.lang.Throwable -> L35
            java.util.List r0 = (java.util.List) r0     // Catch: java.lang.Throwable -> L35
            if (r0 != 0) goto L33
            sun.nio.ch.FileLockTable$FileLockReference r0 = new sun.nio.ch.FileLockTable$FileLockReference     // Catch: java.lang.Throwable -> L35
            java.lang.ref.ReferenceQueue r2 = sun.nio.ch.FileLockTable.queue     // Catch: java.lang.Throwable -> L35
            sun.nio.ch.FileKey r3 = r8.fileKey     // Catch: java.lang.Throwable -> L35
            r0.<init>(r9, r2, r3)     // Catch: java.lang.Throwable -> L35
            r1.add(r0)     // Catch: java.lang.Throwable -> L35
            java.util.Set r0 = r8.locks     // Catch: java.lang.Throwable -> L35
            r0.add(r9)     // Catch: java.lang.Throwable -> L35
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
            r2 = r8
            goto L65
        L33:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
            goto L39
        L35:
            r0 = move-exception
            r9 = r0
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
            throw r9
        L39:
            r3 = r0
            monitor-enter(r3)
            java.util.concurrent.ConcurrentHashMap r0 = sun.nio.ch.FileLockTable.lockMap     // Catch: java.lang.Throwable -> L6c
            sun.nio.ch.FileKey r1 = r8.fileKey     // Catch: java.lang.Throwable -> L6c
            java.lang.Object r0 = r0.get(r1)     // Catch: java.lang.Throwable -> L6c
            java.util.List r0 = (java.util.List) r0     // Catch: java.lang.Throwable -> L6c
            if (r3 != r0) goto L69
            long r4 = r9.position()     // Catch: java.lang.Throwable -> L6c
            long r6 = r9.size()     // Catch: java.lang.Throwable -> L6c
            r2 = r8
            r2.checkList(r3, r4, r6)     // Catch: java.lang.Throwable -> L71
            sun.nio.ch.FileLockTable$FileLockReference r0 = new sun.nio.ch.FileLockTable$FileLockReference     // Catch: java.lang.Throwable -> L71
            java.lang.ref.ReferenceQueue r1 = sun.nio.ch.FileLockTable.queue     // Catch: java.lang.Throwable -> L71
            sun.nio.ch.FileKey r4 = r2.fileKey     // Catch: java.lang.Throwable -> L71
            r0.<init>(r9, r1, r4)     // Catch: java.lang.Throwable -> L71
            r3.add(r0)     // Catch: java.lang.Throwable -> L71
            java.util.Set r0 = r2.locks     // Catch: java.lang.Throwable -> L71
            r0.add(r9)     // Catch: java.lang.Throwable -> L71
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L71
        L65:
            r8.removeStaleEntries()
            return
        L69:
            r2 = r8
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L71
            goto La
        L6c:
            r0 = move-exception
            r2 = r8
        L6e:
            r9 = r0
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L71
            throw r9
        L71:
            r0 = move-exception
            goto L6e
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.FileLockTable.add(java.nio.channels.FileLock):void");
    }

    private void removeKeyIfEmpty(FileKey fileKey, List list) {
        if (list.isEmpty()) {
            lockMap.remove(fileKey);
        }
    }

    void remove(FileLock fileLock) {
        List list = (List) lockMap.get(this.fileKey);
        if (list == null) {
            return;
        }
        synchronized (list) {
            int i = 0;
            while (true) {
                if (i >= list.size()) {
                    break;
                }
                FileLockReference fileLockReference = (FileLockReference) list.get(i);
                if (((FileLock) fileLockReference.get()) == fileLock) {
                    fileLockReference.clear();
                    list.remove(i);
                    this.locks.remove(fileLock);
                    break;
                }
                i++;
            }
        }
    }

    List removeAll() {
        ArrayList arrayList = new ArrayList();
        List list = (List) lockMap.get(this.fileKey);
        if (list == null) {
            return arrayList;
        }
        synchronized (list) {
            int i = 0;
            while (i < list.size()) {
                FileLockReference fileLockReference = (FileLockReference) list.get(i);
                FileLock fileLock = (FileLock) fileLockReference.get();
                if (fileLock == null || fileLock.acquiredBy() != this.channel) {
                    i++;
                } else {
                    fileLockReference.clear();
                    list.remove(i);
                    arrayList.add(fileLock);
                }
            }
            removeKeyIfEmpty(this.fileKey, list);
            this.locks.clear();
        }
        return arrayList;
    }

    void replace(FileLock fileLock, FileLock fileLock2) {
        List list = (List) lockMap.get(this.fileKey);
        synchronized (list) {
            int i = 0;
            while (true) {
                if (i >= list.size()) {
                    break;
                }
                FileLockReference fileLockReference = (FileLockReference) list.get(i);
                if (((FileLock) fileLockReference.get()) == fileLock) {
                    fileLockReference.clear();
                    list.set(i, new FileLockReference(fileLock2, queue, this.fileKey));
                    this.locks.remove(fileLock);
                    this.locks.add(fileLock2);
                    break;
                }
                i++;
            }
        }
    }

    private void checkList(List list, long j, long j2) throws OverlappingFileLockException {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            FileLock fileLock = (FileLock) ((FileLockReference) it.next()).get();
            if (fileLock != null && fileLock.overlaps(j, j2)) {
                throw new OverlappingFileLockException();
            }
        }
    }

    private void removeStaleEntries() {
        while (true) {
            FileLockReference poll = queue.poll();
            if (poll == null) {
                return;
            }
            FileKey fileKey = poll.fileKey();
            List list = (List) lockMap.get(fileKey);
            if (list != null) {
                synchronized (list) {
                    list.remove(poll);
                    removeKeyIfEmpty(fileKey, list);
                }
            }
        }
    }
}
