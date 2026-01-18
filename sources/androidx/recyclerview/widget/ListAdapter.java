package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public abstract class ListAdapter extends RecyclerView.Adapter {
    final AsyncListDiffer mDiffer;
    private final AsyncListDiffer.ListListener mListener = new 1();

    class 1 implements AsyncListDiffer.ListListener {
        1() {
        }

        public void onCurrentListChanged(@NonNull List list, @NonNull List list2) {
            ListAdapter.this.onCurrentListChanged(list, list2);
        }
    }

    protected ListAdapter(@NonNull DiffUtil.ItemCallback itemCallback) {
        this.mDiffer = new AsyncListDiffer(new AdapterListUpdateCallback(this), new AsyncDifferConfig.Builder(itemCallback).build());
        this.mDiffer.addListListener(this.mListener);
    }

    protected ListAdapter(@NonNull AsyncDifferConfig asyncDifferConfig) {
        this.mDiffer = new AsyncListDiffer(new AdapterListUpdateCallback(this), asyncDifferConfig);
        this.mDiffer.addListListener(this.mListener);
    }

    public void submitList(@Nullable List list) {
        this.mDiffer.submitList(list);
    }

    public void submitList(@Nullable List list, @Nullable Runnable commitCallback) {
        this.mDiffer.submitList(list, commitCallback);
    }

    protected Object getItem(int position) {
        return this.mDiffer.getCurrentList().get(position);
    }

    public int getItemCount() {
        return this.mDiffer.getCurrentList().size();
    }

    @NonNull
    public List getCurrentList() {
        return this.mDiffer.getCurrentList();
    }

    public void onCurrentListChanged(@NonNull List list, @NonNull List list2) {
    }
}
