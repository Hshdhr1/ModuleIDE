package androidx.recyclerview.widget;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StableIdStorage;
import androidx.recyclerview.widget.ViewTypeStorage;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
class NestedAdapterWrapper {
    public final RecyclerView.Adapter adapter;
    private RecyclerView.AdapterDataObserver mAdapterObserver = new 1();
    int mCachedItemCount;
    final Callback mCallback;

    @NonNull
    private final StableIdStorage.StableIdLookup mStableIdLookup;

    @NonNull
    private final ViewTypeStorage.ViewTypeLookup mViewTypeLookup;

    interface Callback {
        void onChanged(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

        void onItemRangeChanged(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeChanged(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2, @Nullable Object obj);

        void onItemRangeInserted(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeMoved(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeRemoved(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onStateRestorationPolicyChanged(NestedAdapterWrapper nestedAdapterWrapper);
    }

    class 1 extends RecyclerView.AdapterDataObserver {
        1() {
        }

        public void onChanged() {
            NestedAdapterWrapper.this.mCachedItemCount = NestedAdapterWrapper.this.adapter.getItemCount();
            NestedAdapterWrapper.this.mCallback.onChanged(NestedAdapterWrapper.this);
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            NestedAdapterWrapper.this.mCallback.onItemRangeChanged(NestedAdapterWrapper.this, positionStart, itemCount, null);
        }

        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            NestedAdapterWrapper.this.mCallback.onItemRangeChanged(NestedAdapterWrapper.this, positionStart, itemCount, payload);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            NestedAdapterWrapper.this.mCachedItemCount += itemCount;
            NestedAdapterWrapper.this.mCallback.onItemRangeInserted(NestedAdapterWrapper.this, positionStart, itemCount);
            if (NestedAdapterWrapper.this.mCachedItemCount > 0 && NestedAdapterWrapper.this.adapter.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                NestedAdapterWrapper.this.mCallback.onStateRestorationPolicyChanged(NestedAdapterWrapper.this);
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            NestedAdapterWrapper.this.mCachedItemCount -= itemCount;
            NestedAdapterWrapper.this.mCallback.onItemRangeRemoved(NestedAdapterWrapper.this, positionStart, itemCount);
            if (NestedAdapterWrapper.this.mCachedItemCount < 1 && NestedAdapterWrapper.this.adapter.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                NestedAdapterWrapper.this.mCallback.onStateRestorationPolicyChanged(NestedAdapterWrapper.this);
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            Preconditions.checkArgument(itemCount == 1, "moving more than 1 item is not supported in RecyclerView");
            NestedAdapterWrapper.this.mCallback.onItemRangeMoved(NestedAdapterWrapper.this, fromPosition, toPosition);
        }

        public void onStateRestorationPolicyChanged() {
            NestedAdapterWrapper.this.mCallback.onStateRestorationPolicyChanged(NestedAdapterWrapper.this);
        }
    }

    NestedAdapterWrapper(RecyclerView.Adapter adapter, Callback callback, ViewTypeStorage viewTypeStorage, StableIdStorage.StableIdLookup stableIdLookup) {
        this.adapter = adapter;
        this.mCallback = callback;
        this.mViewTypeLookup = viewTypeStorage.createViewTypeWrapper(this);
        this.mStableIdLookup = stableIdLookup;
        this.mCachedItemCount = this.adapter.getItemCount();
        this.adapter.registerAdapterDataObserver(this.mAdapterObserver);
    }

    void dispose() {
        this.adapter.unregisterAdapterDataObserver(this.mAdapterObserver);
        this.mViewTypeLookup.dispose();
    }

    int getCachedItemCount() {
        return this.mCachedItemCount;
    }

    int getItemViewType(int localPosition) {
        return this.mViewTypeLookup.localToGlobal(this.adapter.getItemViewType(localPosition));
    }

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int globalViewType) {
        int localType = this.mViewTypeLookup.globalToLocal(globalViewType);
        return this.adapter.onCreateViewHolder(parent, localType);
    }

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int localPosition) {
        this.adapter.bindViewHolder(viewHolder, localPosition);
    }

    public long getItemId(int localPosition) {
        long localItemId = this.adapter.getItemId(localPosition);
        return this.mStableIdLookup.localToGlobal(localItemId);
    }
}
