package androidx.recyclerview.widget;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public class AsyncListDiffer {
    private static final Executor sMainThreadExecutor = new MainThreadExecutor();
    final AsyncDifferConfig mConfig;

    @Nullable
    private List mList;
    private final List mListeners;
    Executor mMainThreadExecutor;
    int mMaxScheduledGeneration;

    @NonNull
    private List mReadOnlyList;
    private final ListUpdateCallback mUpdateCallback;

    public interface ListListener {
        void onCurrentListChanged(@NonNull List list, @NonNull List list2);
    }

    private static class MainThreadExecutor implements Executor {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        MainThreadExecutor() {
        }

        public void execute(@NonNull Runnable command) {
            this.mHandler.post(command);
        }
    }

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback itemCallback) {
        this(new AdapterListUpdateCallback(adapter), new AsyncDifferConfig.Builder(itemCallback).build());
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig asyncDifferConfig) {
        this.mListeners = new CopyOnWriteArrayList();
        this.mReadOnlyList = Collections.emptyList();
        this.mUpdateCallback = listUpdateCallback;
        this.mConfig = asyncDifferConfig;
        if (asyncDifferConfig.getMainThreadExecutor() != null) {
            this.mMainThreadExecutor = asyncDifferConfig.getMainThreadExecutor();
        } else {
            this.mMainThreadExecutor = sMainThreadExecutor;
        }
    }

    @NonNull
    public List getCurrentList() {
        return this.mReadOnlyList;
    }

    public void submitList(@Nullable List list) {
        submitList(list, null);
    }

    public void submitList(@Nullable List list, @Nullable Runnable commitCallback) {
        int runGeneration = this.mMaxScheduledGeneration + 1;
        this.mMaxScheduledGeneration = runGeneration;
        if (list == this.mList) {
            if (commitCallback != null) {
                commitCallback.run();
                return;
            }
            return;
        }
        List list2 = this.mReadOnlyList;
        if (list == null) {
            int countRemoved = this.mList.size();
            this.mList = null;
            this.mReadOnlyList = Collections.emptyList();
            this.mUpdateCallback.onRemoved(0, countRemoved);
            onCurrentListChanged(list2, commitCallback);
            return;
        }
        if (this.mList == null) {
            this.mList = list;
            this.mReadOnlyList = Collections.unmodifiableList(list);
            this.mUpdateCallback.onInserted(0, list.size());
            onCurrentListChanged(list2, commitCallback);
            return;
        }
        this.mConfig.getBackgroundThreadExecutor().execute(new 1(this.mList, list, runGeneration, commitCallback));
    }

    class 1 implements Runnable {
        final /* synthetic */ Runnable val$commitCallback;
        final /* synthetic */ List val$newList;
        final /* synthetic */ List val$oldList;
        final /* synthetic */ int val$runGeneration;

        1(List list, List list2, int i, Runnable runnable) {
            this.val$oldList = list;
            this.val$newList = list2;
            this.val$runGeneration = i;
            this.val$commitCallback = runnable;
        }

        class 1 extends DiffUtil.Callback {
            1() {
            }

            public int getOldListSize() {
                return 1.this.val$oldList.size();
            }

            public int getNewListSize() {
                return 1.this.val$newList.size();
            }

            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Object obj = 1.this.val$oldList.get(oldItemPosition);
                Object obj2 = 1.this.val$newList.get(newItemPosition);
                if (obj == null || obj2 == null) {
                    return obj == null && obj2 == null;
                }
                return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(obj, obj2);
            }

            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Object obj = 1.this.val$oldList.get(oldItemPosition);
                Object obj2 = 1.this.val$newList.get(newItemPosition);
                if (obj != null && obj2 != null) {
                    return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(obj, obj2);
                }
                if (obj == null && obj2 == null) {
                    return true;
                }
                throw new AssertionError();
            }

            @Nullable
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                Object obj = 1.this.val$oldList.get(oldItemPosition);
                Object obj2 = 1.this.val$newList.get(newItemPosition);
                if (obj != null && obj2 != null) {
                    return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(obj, obj2);
                }
                throw new AssertionError();
            }
        }

        public void run() {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new 1());
            AsyncListDiffer.this.mMainThreadExecutor.execute(new 2(result));
        }

        class 2 implements Runnable {
            final /* synthetic */ DiffUtil.DiffResult val$result;

            2(DiffUtil.DiffResult diffResult) {
                this.val$result = diffResult;
            }

            public void run() {
                if (AsyncListDiffer.this.mMaxScheduledGeneration == 1.this.val$runGeneration) {
                    AsyncListDiffer.this.latchList(1.this.val$newList, this.val$result, 1.this.val$commitCallback);
                }
            }
        }
    }

    void latchList(@NonNull List list, @NonNull DiffUtil.DiffResult diffResult, @Nullable Runnable commitCallback) {
        List list2 = this.mReadOnlyList;
        this.mList = list;
        this.mReadOnlyList = Collections.unmodifiableList(list);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
        onCurrentListChanged(list2, commitCallback);
    }

    private void onCurrentListChanged(@NonNull List list, @Nullable Runnable commitCallback) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((ListListener) it.next()).onCurrentListChanged(list, this.mReadOnlyList);
        }
        if (commitCallback != null) {
            commitCallback.run();
        }
    }

    public void addListListener(@NonNull ListListener listListener) {
        this.mListeners.add(listListener);
    }

    public void removeListListener(@NonNull ListListener listListener) {
        this.mListeners.remove(listListener);
    }
}
