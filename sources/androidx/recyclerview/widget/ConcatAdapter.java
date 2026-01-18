package androidx.recyclerview.widget;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public final class ConcatAdapter extends RecyclerView.Adapter {
    static final String TAG = "ConcatAdapter";
    private final ConcatAdapterController mController;

    @SafeVarargs
    public ConcatAdapter(@NonNull RecyclerView.Adapter... adapterArr) {
        this(Config.DEFAULT, adapterArr);
    }

    @SafeVarargs
    public ConcatAdapter(@NonNull Config config, @NonNull RecyclerView.Adapter... adapterArr) {
        this(config, Arrays.asList(adapterArr));
    }

    public ConcatAdapter(@NonNull List list) {
        this(Config.DEFAULT, list);
    }

    public ConcatAdapter(@NonNull Config config, @NonNull List list) {
        this.mController = new ConcatAdapterController(this, config);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter = (RecyclerView.Adapter) it.next();
            addAdapter(adapter);
        }
        super.setHasStableIds(this.mController.hasStableIds());
    }

    public boolean addAdapter(@NonNull RecyclerView.Adapter adapter) {
        return this.mController.addAdapter(adapter);
    }

    public boolean addAdapter(int index, @NonNull RecyclerView.Adapter adapter) {
        return this.mController.addAdapter(index, adapter);
    }

    public boolean removeAdapter(@NonNull RecyclerView.Adapter adapter) {
        return this.mController.removeAdapter(adapter);
    }

    public int getItemViewType(int position) {
        return this.mController.getItemViewType(position);
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return this.mController.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mController.onBindViewHolder(holder, position);
    }

    public void setHasStableIds(boolean hasStableIds) {
        throw new UnsupportedOperationException("Calling setHasStableIds is not allowed on the ConcatAdapter. Use the Config object passed in the constructor to control this behavior");
    }

    public void setStateRestorationPolicy(@NonNull RecyclerView.Adapter.StateRestorationPolicy strategy) {
        throw new UnsupportedOperationException("Calling setStateRestorationPolicy is not allowed on the ConcatAdapter. This value is inferred from added adapters");
    }

    public long getItemId(int position) {
        return this.mController.getItemId(position);
    }

    void internalSetStateRestorationPolicy(@NonNull RecyclerView.Adapter.StateRestorationPolicy strategy) {
        super.setStateRestorationPolicy(strategy);
    }

    public int getItemCount() {
        return this.mController.getTotalCount();
    }

    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return this.mController.onFailedToRecycleView(holder);
    }

    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        this.mController.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        this.mController.onViewDetachedFromWindow(holder);
    }

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        this.mController.onViewRecycled(holder);
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mController.onAttachedToRecyclerView(recyclerView);
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mController.onDetachedFromRecyclerView(recyclerView);
    }

    @NonNull
    public List getAdapters() {
        return Collections.unmodifiableList(this.mController.getCopyOfAdapters());
    }

    public int findRelativeAdapterPositionIn(@NonNull RecyclerView.Adapter adapter, @NonNull RecyclerView.ViewHolder viewHolder, int localPosition) {
        return this.mController.getLocalAdapterPosition(adapter, viewHolder, localPosition);
    }

    public static final class Config {

        @NonNull
        public static final Config DEFAULT = new Config(true, StableIdMode.NO_STABLE_IDS);
        public final boolean isolateViewTypes;

        @NonNull
        public final StableIdMode stableIdMode;

        public enum StableIdMode {
            NO_STABLE_IDS,
            ISOLATED_STABLE_IDS,
            SHARED_STABLE_IDS
        }

        Config(boolean isolateViewTypes, @NonNull StableIdMode stableIdMode) {
            this.isolateViewTypes = isolateViewTypes;
            this.stableIdMode = stableIdMode;
        }

        public static final class Builder {
            private boolean mIsolateViewTypes = Config.DEFAULT.isolateViewTypes;
            private StableIdMode mStableIdMode = Config.DEFAULT.stableIdMode;

            @NonNull
            public Builder setIsolateViewTypes(boolean isolateViewTypes) {
                this.mIsolateViewTypes = isolateViewTypes;
                return this;
            }

            @NonNull
            public Builder setStableIdMode(@NonNull StableIdMode stableIdMode) {
                this.mStableIdMode = stableIdMode;
                return this;
            }

            @NonNull
            public Config build() {
                return new Config(this.mIsolateViewTypes, this.mStableIdMode);
            }
        }
    }
}
