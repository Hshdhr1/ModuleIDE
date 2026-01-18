package sun.nio.ch;

import java.nio.channels.AsynchronousChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ShutdownChannelGroupException;
import java.security.AccessController;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import jdk.internal.misc.InnocuousThread;
import sun.security.action.GetIntegerAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class Invoker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int maxHandlerInvokeCount = ((Integer) AccessController.doPrivileged(new GetIntegerAction("sun.nio.ch.maxCompletionHandlersOnStack", 16))).intValue();
    private static final ThreadLocal myGroupAndInvokeCount = new 1();

    static /* bridge */ /* synthetic */ ThreadLocal -$$Nest$sfgetmyGroupAndInvokeCount() {
        return myGroupAndInvokeCount;
    }

    private Invoker() {
    }

    static class GroupAndInvokeCount {
        private final AsynchronousChannelGroupImpl group;
        private int handlerInvokeCount;

        static /* bridge */ /* synthetic */ AsynchronousChannelGroupImpl -$$Nest$fgetgroup(GroupAndInvokeCount groupAndInvokeCount) {
            return groupAndInvokeCount.group;
        }

        GroupAndInvokeCount(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
            this.group = asynchronousChannelGroupImpl;
        }

        AsynchronousChannelGroupImpl group() {
            return this.group;
        }

        int invokeCount() {
            return this.handlerInvokeCount;
        }

        void setInvokeCount(int i) {
            this.handlerInvokeCount = i;
        }

        void resetInvokeCount() {
            this.handlerInvokeCount = 0;
        }

        void incrementInvokeCount() {
            this.handlerInvokeCount++;
        }
    }

    class 1 extends ThreadLocal {
        /* JADX INFO: Access modifiers changed from: protected */
        public GroupAndInvokeCount initialValue() {
            return null;
        }

        1() {
        }
    }

    static void bindToGroup(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        myGroupAndInvokeCount.set(new GroupAndInvokeCount(asynchronousChannelGroupImpl));
    }

    static GroupAndInvokeCount getGroupAndInvokeCount() {
        return (GroupAndInvokeCount) myGroupAndInvokeCount.get();
    }

    static boolean isBoundToAnyGroup() {
        return myGroupAndInvokeCount.get() != null;
    }

    static boolean mayInvokeDirect(GroupAndInvokeCount groupAndInvokeCount, AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        return groupAndInvokeCount != null && groupAndInvokeCount.group() == asynchronousChannelGroupImpl && groupAndInvokeCount.invokeCount() < maxHandlerInvokeCount;
    }

    static void invokeUnchecked(CompletionHandler completionHandler, Object obj, Object obj2, Throwable th) {
        if (th == null) {
            completionHandler.completed(obj2, obj);
        } else {
            completionHandler.failed(th, obj);
        }
        Thread.interrupted();
        if (System.getSecurityManager() != null) {
            InnocuousThread currentThread = Thread.currentThread();
            if (currentThread instanceof InnocuousThread) {
                ThreadLocal threadLocal = myGroupAndInvokeCount;
                GroupAndInvokeCount groupAndInvokeCount = (GroupAndInvokeCount) threadLocal.get();
                currentThread.eraseThreadLocals();
                if (groupAndInvokeCount != null) {
                    threadLocal.set(groupAndInvokeCount);
                }
            }
        }
    }

    static void invokeDirect(GroupAndInvokeCount groupAndInvokeCount, CompletionHandler completionHandler, Object obj, Object obj2, Throwable th) {
        groupAndInvokeCount.incrementInvokeCount();
        invokeUnchecked(completionHandler, obj, obj2, th);
    }

    static void invoke(AsynchronousChannel asynchronousChannel, CompletionHandler completionHandler, Object obj, Object obj2, Throwable th) {
        boolean z;
        GroupAndInvokeCount groupAndInvokeCount = (GroupAndInvokeCount) myGroupAndInvokeCount.get();
        boolean z2 = false;
        if (groupAndInvokeCount != null) {
            z = groupAndInvokeCount.group() == ((Groupable) asynchronousChannel).group();
            if (z && groupAndInvokeCount.invokeCount() < maxHandlerInvokeCount) {
                z2 = true;
            }
        } else {
            z = false;
        }
        if (z2) {
            invokeDirect(groupAndInvokeCount, completionHandler, obj, obj2, th);
            return;
        }
        try {
            invokeIndirectly(asynchronousChannel, completionHandler, obj, obj2, th);
        } catch (RejectedExecutionException unused) {
            if (z) {
                invokeDirect(groupAndInvokeCount, completionHandler, obj, obj2, th);
                return;
            }
            throw new ShutdownChannelGroupException();
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ Object val$attachment;
        final /* synthetic */ Throwable val$exc;
        final /* synthetic */ CompletionHandler val$handler;
        final /* synthetic */ Object val$result;

        2(CompletionHandler completionHandler, Object obj, Object obj2, Throwable th) {
            this.val$handler = completionHandler;
            this.val$attachment = obj;
            this.val$result = obj2;
            this.val$exc = th;
        }

        public void run() {
            GroupAndInvokeCount groupAndInvokeCount = (GroupAndInvokeCount) Invoker.-$$Nest$sfgetmyGroupAndInvokeCount().get();
            if (groupAndInvokeCount != null) {
                groupAndInvokeCount.setInvokeCount(1);
            }
            Invoker.invokeUnchecked(this.val$handler, this.val$attachment, this.val$result, this.val$exc);
        }
    }

    static void invokeIndirectly(AsynchronousChannel asynchronousChannel, CompletionHandler completionHandler, Object obj, Object obj2, Throwable th) {
        try {
            ((Groupable) asynchronousChannel).group().executeOnPooledThread(new 2(completionHandler, obj, obj2, th));
        } catch (RejectedExecutionException unused) {
            throw new ShutdownChannelGroupException();
        }
    }

    class 3 implements Runnable {
        final /* synthetic */ Object val$attachment;
        final /* synthetic */ Throwable val$exc;
        final /* synthetic */ CompletionHandler val$handler;
        final /* synthetic */ Object val$value;

        3(CompletionHandler completionHandler, Object obj, Object obj2, Throwable th) {
            this.val$handler = completionHandler;
            this.val$attachment = obj;
            this.val$value = obj2;
            this.val$exc = th;
        }

        public void run() {
            Invoker.invokeUnchecked(this.val$handler, this.val$attachment, this.val$value, this.val$exc);
        }
    }

    static void invokeIndirectly(CompletionHandler completionHandler, Object obj, Object obj2, Throwable th, Executor executor) {
        try {
            executor.execute(new 3(completionHandler, obj, obj2, th));
        } catch (RejectedExecutionException unused) {
            throw new ShutdownChannelGroupException();
        }
    }

    static void invokeOnThreadInThreadPool(Groupable groupable, Runnable runnable) {
        GroupAndInvokeCount groupAndInvokeCount = (GroupAndInvokeCount) myGroupAndInvokeCount.get();
        AsynchronousChannelGroupImpl group = groupable.group();
        try {
            if (groupAndInvokeCount != null && GroupAndInvokeCount.-$$Nest$fgetgroup(groupAndInvokeCount) == group) {
                runnable.run();
            } else {
                group.executeOnPooledThread(runnable);
            }
        } catch (RejectedExecutionException unused) {
            throw new ShutdownChannelGroupException();
        }
    }

    static void invokeUnchecked(PendingFuture pendingFuture) {
        CompletionHandler handler = pendingFuture.handler();
        if (handler != null) {
            invokeUnchecked(handler, pendingFuture.attachment(), pendingFuture.value(), pendingFuture.exception());
        }
    }

    static void invoke(PendingFuture pendingFuture) {
        CompletionHandler handler = pendingFuture.handler();
        if (handler != null) {
            invoke(pendingFuture.channel(), handler, pendingFuture.attachment(), pendingFuture.value(), pendingFuture.exception());
        }
    }

    static void invokeIndirectly(PendingFuture pendingFuture) {
        CompletionHandler handler = pendingFuture.handler();
        if (handler != null) {
            invokeIndirectly(pendingFuture.channel(), handler, pendingFuture.attachment(), pendingFuture.value(), pendingFuture.exception());
        }
    }
}
