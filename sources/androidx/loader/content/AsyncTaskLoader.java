package androidx.loader.content;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateUtils;
import androidx.core.os.OperationCanceledException;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes32.dex */
public abstract class AsyncTaskLoader extends Loader {
    private static final boolean DEBUG = false;
    private static final String TAG = "AsyncTaskLoader";
    private volatile LoadTask mCancellingTask;
    private Executor mExecutor;
    private Handler mHandler;
    private long mLastLoadCompleteTime;
    private volatile LoadTask mTask;
    private long mUpdateThrottle;

    public abstract Object loadInBackground();

    final class LoadTask extends ModernAsyncTask implements Runnable {
        boolean waiting;

        LoadTask() {
        }

        protected Object doInBackground() {
            try {
                return AsyncTaskLoader.this.onLoadInBackground();
            } catch (OperationCanceledException ex) {
                if (!isCancelled()) {
                    throw ex;
                }
                return null;
            }
        }

        protected void onPostExecute(Object obj) {
            AsyncTaskLoader.this.dispatchOnLoadComplete(this, obj);
        }

        protected void onCancelled(Object obj) {
            AsyncTaskLoader.this.dispatchOnCancelled(this, obj);
        }

        public void run() {
            this.waiting = false;
            AsyncTaskLoader.this.executePendingTask();
        }
    }

    public AsyncTaskLoader(Context context) {
        super(context);
        this.mLastLoadCompleteTime = -10000L;
    }

    public void setUpdateThrottle(long delayMS) {
        this.mUpdateThrottle = delayMS;
        if (delayMS != 0) {
            this.mHandler = new Handler();
        }
    }

    protected void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.mTask = new LoadTask();
        executePendingTask();
    }

    protected boolean onCancelLoad() {
        if (this.mTask == null) {
            return false;
        }
        if (!isStarted()) {
            onContentChanged();
        }
        if (this.mCancellingTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            this.mTask = null;
            return false;
        }
        if (this.mTask.waiting) {
            this.mTask.waiting = false;
            this.mHandler.removeCallbacks(this.mTask);
            this.mTask = null;
            return false;
        }
        boolean cancelled = this.mTask.cancel(false);
        if (cancelled) {
            this.mCancellingTask = this.mTask;
            cancelLoadInBackground();
        }
        this.mTask = null;
        return cancelled;
    }

    public void onCanceled(Object obj) {
    }

    void executePendingTask() {
        if (this.mCancellingTask == null && this.mTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (this.mUpdateThrottle > 0) {
                long now = SystemClock.uptimeMillis();
                if (now < this.mLastLoadCompleteTime + this.mUpdateThrottle) {
                    this.mTask.waiting = true;
                    this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
                    return;
                }
            }
            if (this.mExecutor == null) {
                this.mExecutor = getExecutor();
            }
            this.mTask.executeOnExecutor(this.mExecutor);
        }
    }

    void dispatchOnCancelled(LoadTask loadTask, Object obj) {
        onCanceled(obj);
        if (this.mCancellingTask == loadTask) {
            rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            deliverCancellation();
            executePendingTask();
        }
    }

    void dispatchOnLoadComplete(LoadTask loadTask, Object obj) {
        if (this.mTask != loadTask) {
            dispatchOnCancelled(loadTask, obj);
            return;
        }
        if (isAbandoned()) {
            onCanceled(obj);
            return;
        }
        commitContentChanged();
        this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
        this.mTask = null;
        deliverResult(obj);
    }

    protected Object onLoadInBackground() {
        return loadInBackground();
    }

    public void cancelLoadInBackground() {
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.mCancellingTask != null;
    }

    protected Executor getExecutor() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

    @Deprecated
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String str;
        super.dump(prefix, fd, writer, args);
        if (this.mTask != null) {
            writer.print(prefix);
            writer.print("mTask=");
            writer.print(this.mTask);
            writer.print(" waiting=");
            writer.println(this.mTask.waiting);
        }
        if (this.mCancellingTask != null) {
            writer.print(prefix);
            writer.print("mCancellingTask=");
            writer.print(this.mCancellingTask);
            writer.print(" waiting=");
            writer.println(this.mCancellingTask.waiting);
        }
        if (this.mUpdateThrottle != 0) {
            writer.print(prefix);
            writer.print("mUpdateThrottle=");
            writer.print(DateUtils.formatElapsedTime(TimeUnit.MILLISECONDS.toSeconds(this.mUpdateThrottle)));
            writer.print(" mLastLoadCompleteTime=");
            if (this.mLastLoadCompleteTime == -10000) {
                str = "--";
            } else {
                str = "-" + DateUtils.formatElapsedTime(TimeUnit.MILLISECONDS.toSeconds(SystemClock.uptimeMillis() - this.mLastLoadCompleteTime));
            }
            writer.print(str);
            writer.println();
        }
    }
}
