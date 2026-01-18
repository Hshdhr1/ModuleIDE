package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractPoller implements Runnable {
    private final LinkedList requestList = new LinkedList();
    private boolean shutdown = false;

    private enum RequestType {
        REGISTER,
        CANCEL,
        CLOSE
    }

    abstract void implCancelKey(WatchKey watchKey);

    abstract void implCloseAll();

    abstract Object implRegister(Path path, Set set, WatchEvent.Modifier... modifierArr);

    abstract void wakeup() throws IOException;

    protected AbstractPoller() {
    }

    class 1 implements PrivilegedAction {
        final /* synthetic */ Runnable val$thisRunnable;

        1(Runnable runnable) {
            this.val$thisRunnable = runnable;
        }

        public Object run() {
            Thread thread = new Thread((ThreadGroup) null, this.val$thisRunnable, "FileSystemWatchService", 0L, false);
            thread.setDaemon(true);
            thread.start();
            return null;
        }
    }

    public void start() {
        AccessController.doPrivileged(new 1(this));
    }

    final WatchKey register(Path path, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException {
        path.getClass();
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
        return (WatchKey) invoke(RequestType.REGISTER, path, hashSet, modifierArr);
    }

    final void cancel(WatchKey watchKey) {
        try {
            invoke(RequestType.CANCEL, watchKey);
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    final void close() throws IOException {
        invoke(RequestType.CLOSE, new Object[0]);
    }

    private static class Request {
        private final Object[] params;
        private final RequestType type;
        private boolean completed = false;
        private Object result = null;

        Request(RequestType requestType, Object... objArr) {
            this.type = requestType;
            this.params = objArr;
        }

        RequestType type() {
            return this.type;
        }

        Object[] parameters() {
            return this.params;
        }

        void release(Object obj) {
            synchronized (this) {
                this.completed = true;
                this.result = obj;
                notifyAll();
            }
        }

        Object awaitResult() {
            Object obj;
            synchronized (this) {
                boolean z = false;
                while (!this.completed) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
                if (z) {
                    Thread.currentThread().interrupt();
                }
                obj = this.result;
            }
            return obj;
        }
    }

    private Object invoke(RequestType requestType, Object... objArr) throws IOException {
        Request request = new Request(requestType, objArr);
        synchronized (this.requestList) {
            if (this.shutdown) {
                throw new ClosedWatchServiceException();
            }
            this.requestList.add(request);
            wakeup();
        }
        Object awaitResult = request.awaitResult();
        if (awaitResult instanceof RuntimeException) {
            throw ((RuntimeException) awaitResult);
        }
        if (awaitResult instanceof IOException) {
            throw ((IOException) awaitResult);
        }
        return awaitResult;
    }

    boolean processRequests() {
        synchronized (this.requestList) {
            while (true) {
                Request request = (Request) this.requestList.poll();
                if (request != null) {
                    if (this.shutdown) {
                        request.release(new ClosedWatchServiceException());
                    } else {
                        int i = 2.$SwitchMap$sun$nio$fs$AbstractPoller$RequestType[request.type().ordinal()];
                        if (i == 1) {
                            Object[] parameters = request.parameters();
                            request.release(implRegister((Path) parameters[0], (Set) parameters[1], (WatchEvent.Modifier[]) parameters[2]));
                        } else if (i == 2) {
                            implCancelKey((WatchKey) request.parameters()[0]);
                            request.release(null);
                        } else if (i == 3) {
                            implCloseAll();
                            request.release(null);
                            this.shutdown = true;
                        } else {
                            request.release(new IOException("request not recognized"));
                        }
                    }
                }
            }
        }
        return this.shutdown;
    }

    static /* synthetic */ class 2 {
        static final /* synthetic */ int[] $SwitchMap$sun$nio$fs$AbstractPoller$RequestType;

        static {
            int[] iArr = new int[RequestType.values().length];
            $SwitchMap$sun$nio$fs$AbstractPoller$RequestType = iArr;
            try {
                iArr[RequestType.REGISTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$sun$nio$fs$AbstractPoller$RequestType[RequestType.CANCEL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$sun$nio$fs$AbstractPoller$RequestType[RequestType.CLOSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
