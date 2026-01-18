package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class PollingWatchService extends AbstractWatchService {
    private final Map map = new HashMap();
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new 1());

    static /* bridge */ /* synthetic */ Map -$$Nest$fgetmap(PollingWatchService pollingWatchService) {
        return pollingWatchService.map;
    }

    static /* bridge */ /* synthetic */ ScheduledExecutorService -$$Nest$fgetscheduledExecutor(PollingWatchService pollingWatchService) {
        return pollingWatchService.scheduledExecutor;
    }

    static /* bridge */ /* synthetic */ PollingWatchKey -$$Nest$mdoPrivilegedRegister(PollingWatchService pollingWatchService, Path path, Set set, int i) {
        return pollingWatchService.doPrivilegedRegister(path, set, i);
    }

    PollingWatchService() {
    }

    class 1 implements ThreadFactory {
        1() {
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread((ThreadGroup) null, runnable, "FileSystemWatcher", 0L, false);
            thread.setDaemon(true);
            return thread;
        }
    }

    WatchKey register(Path path, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException {
        HashSet hashSet = new HashSet(kindArr.length);
        for (WatchEvent.Kind kind : kindArr) {
            if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY || kind == StandardWatchEventKinds.ENTRY_DELETE) {
                hashSet.add(kind);
            } else if (kind != StandardWatchEventKinds.OVERFLOW) {
                if (kind == null) {
                    throw new NullPointerException("An element in event set is 'null'");
                }
                throw new UnsupportedOperationException(kind.name());
            }
        }
        if (hashSet.isEmpty()) {
            throw new IllegalArgumentException("No events to register");
        }
        int i = 10;
        if (modifierArr.length > 0) {
            for (WatchEvent.Modifier modifier : modifierArr) {
                modifier.getClass();
                if (ExtendedOptions.SENSITIVITY_HIGH.matches(modifier)) {
                    i = ((Integer) ExtendedOptions.SENSITIVITY_HIGH.parameter()).intValue();
                } else if (ExtendedOptions.SENSITIVITY_MEDIUM.matches(modifier)) {
                    i = ((Integer) ExtendedOptions.SENSITIVITY_MEDIUM.parameter()).intValue();
                } else if (ExtendedOptions.SENSITIVITY_LOW.matches(modifier)) {
                    i = ((Integer) ExtendedOptions.SENSITIVITY_LOW.parameter()).intValue();
                } else {
                    throw new UnsupportedOperationException("Modifier not supported");
                }
            }
        }
        if (!isOpen()) {
            throw new ClosedWatchServiceException();
        }
        try {
            return (WatchKey) AccessController.doPrivileged(new 2(path, hashSet, i));
        } catch (PrivilegedActionException e) {
            IOException cause = e.getCause();
            if (cause != null && (cause instanceof IOException)) {
                throw cause;
            }
            throw new AssertionError(e);
        }
    }

    class 2 implements PrivilegedExceptionAction {
        final /* synthetic */ Set val$eventSet;
        final /* synthetic */ Path val$path;
        final /* synthetic */ int val$value;

        2(Path path, Set set, int i) {
            this.val$path = path;
            this.val$eventSet = set;
            this.val$value = i;
        }

        public PollingWatchKey run() throws IOException {
            return PollingWatchService.-$$Nest$mdoPrivilegedRegister(PollingWatchService.this, this.val$path, this.val$eventSet, this.val$value);
        }
    }

    private PollingWatchKey doPrivilegedRegister(Path path, Set set, int i) throws IOException {
        PollingWatchKey pollingWatchKey;
        BasicFileAttributes readAttributes = Files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
        if (!readAttributes.isDirectory()) {
            throw new NotDirectoryException(path.toString());
        }
        Object fileKey = readAttributes.fileKey();
        if (fileKey == null) {
            throw new AssertionError("File keys must be supported");
        }
        synchronized (closeLock()) {
            if (!isOpen()) {
                throw new ClosedWatchServiceException();
            }
            synchronized (this.map) {
                pollingWatchKey = (PollingWatchKey) this.map.get(fileKey);
                if (pollingWatchKey == null) {
                    pollingWatchKey = new PollingWatchKey(path, this, fileKey);
                    this.map.put(fileKey, pollingWatchKey);
                } else {
                    pollingWatchKey.disable();
                }
            }
            pollingWatchKey.enable(set, i);
        }
        return pollingWatchKey;
    }

    void implClose() throws IOException {
        synchronized (this.map) {
            Iterator it = this.map.entrySet().iterator();
            while (it.hasNext()) {
                PollingWatchKey pollingWatchKey = (PollingWatchKey) ((Map.Entry) it.next()).getValue();
                pollingWatchKey.disable();
                pollingWatchKey.invalidate();
            }
            this.map.clear();
        }
        AccessController.doPrivileged(new 3());
    }

    class 3 implements PrivilegedAction {
        3() {
        }

        public Void run() {
            PollingWatchService.-$$Nest$fgetscheduledExecutor(PollingWatchService.this).shutdown();
            return null;
        }
    }

    private static class CacheEntry {
        private long lastModified;
        private int lastTickCount;

        static /* bridge */ /* synthetic */ long -$$Nest$fgetlastModified(CacheEntry cacheEntry) {
            return cacheEntry.lastModified;
        }

        CacheEntry(long j, int i) {
            this.lastModified = j;
            this.lastTickCount = i;
        }

        int lastTickCount() {
            return this.lastTickCount;
        }

        long lastModified() {
            return this.lastModified;
        }

        void update(long j, int i) {
            this.lastModified = j;
            this.lastTickCount = i;
        }
    }

    private class PollingWatchKey extends AbstractWatchKey {
        private Map entries;
        private Set events;
        private final Object fileKey;
        private ScheduledFuture poller;
        private int tickCount;
        private volatile boolean valid;

        PollingWatchKey(Path path, PollingWatchService pollingWatchService, Object obj) throws IOException {
            super(path, pollingWatchService);
            this.fileKey = obj;
            this.valid = true;
            this.tickCount = 0;
            this.entries = new HashMap();
            try {
                DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path);
                try {
                    for (Path path2 : newDirectoryStream) {
                        this.entries.put(path2.getFileName(), new CacheEntry(Files.getLastModifiedTime(path2, LinkOption.NOFOLLOW_LINKS).toMillis(), this.tickCount));
                    }
                    if (newDirectoryStream != null) {
                        newDirectoryStream.close();
                    }
                } finally {
                }
            } catch (DirectoryIteratorException e) {
                throw e.getCause();
            }
        }

        Object fileKey() {
            return this.fileKey;
        }

        public boolean isValid() {
            return this.valid;
        }

        void invalidate() {
            this.valid = false;
        }

        void enable(Set set, long j) {
            synchronized (this) {
                this.events = set;
                this.poller = PollingWatchService.-$$Nest$fgetscheduledExecutor(PollingWatchService.this).scheduleAtFixedRate(new 1(), j, j, TimeUnit.SECONDS);
            }
        }

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                PollingWatchKey.this.poll();
            }
        }

        void disable() {
            synchronized (this) {
                ScheduledFuture scheduledFuture = this.poller;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
            }
        }

        public void cancel() {
            this.valid = false;
            synchronized (PollingWatchService.-$$Nest$fgetmap(PollingWatchService.this)) {
                PollingWatchService.-$$Nest$fgetmap(PollingWatchService.this).remove(fileKey());
            }
            disable();
        }

        synchronized void poll() {
            if (this.valid) {
                this.tickCount++;
                try {
                    DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(watchable());
                    try {
                        for (Path path : newDirectoryStream) {
                            try {
                                long millis = Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS).toMillis();
                                CacheEntry cacheEntry = (CacheEntry) this.entries.get(path.getFileName());
                                if (cacheEntry == null) {
                                    this.entries.put(path.getFileName(), new CacheEntry(millis, this.tickCount));
                                    if (this.events.contains(StandardWatchEventKinds.ENTRY_CREATE)) {
                                        signalEvent(StandardWatchEventKinds.ENTRY_CREATE, path.getFileName());
                                    } else if (this.events.contains(StandardWatchEventKinds.ENTRY_MODIFY)) {
                                        signalEvent(StandardWatchEventKinds.ENTRY_MODIFY, path.getFileName());
                                    }
                                } else {
                                    if (CacheEntry.-$$Nest$fgetlastModified(cacheEntry) != millis && this.events.contains(StandardWatchEventKinds.ENTRY_MODIFY)) {
                                        signalEvent(StandardWatchEventKinds.ENTRY_MODIFY, path.getFileName());
                                    }
                                    cacheEntry.update(millis, this.tickCount);
                                }
                            } catch (IOException unused) {
                            }
                        }
                    } catch (DirectoryIteratorException unused2) {
                    } catch (Throwable th) {
                        try {
                            newDirectoryStream.close();
                        } catch (IOException unused3) {
                        }
                        throw th;
                    }
                    try {
                        newDirectoryStream.close();
                    } catch (IOException unused4) {
                    }
                    Iterator it = this.entries.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        if (((CacheEntry) entry.getValue()).lastTickCount() != this.tickCount) {
                            Path path2 = (Path) entry.getKey();
                            it.remove();
                            if (this.events.contains(StandardWatchEventKinds.ENTRY_DELETE)) {
                                signalEvent(StandardWatchEventKinds.ENTRY_DELETE, path2);
                            }
                        }
                    }
                } catch (IOException unused5) {
                    cancel();
                    signal();
                }
            }
        }
    }
}
