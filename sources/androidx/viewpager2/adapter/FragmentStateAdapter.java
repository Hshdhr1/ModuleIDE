package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.collection.ArraySet;
import androidx.collection.LongSparseArray;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes52.dex */
public abstract class FragmentStateAdapter extends RecyclerView.Adapter implements StatefulAdapter {
    private static final long GRACE_WINDOW_TIME_MS = 10000;
    private static final String KEY_PREFIX_FRAGMENT = "f#";
    private static final String KEY_PREFIX_STATE = "s#";
    final FragmentManager mFragmentManager;
    private FragmentMaxLifecycleEnforcer mFragmentMaxLifecycleEnforcer;
    final LongSparseArray mFragments;
    private boolean mHasStaleFragments;
    boolean mIsInGracePeriod;
    private final LongSparseArray mItemIdToViewHolder;
    final Lifecycle mLifecycle;
    private final LongSparseArray mSavedStates;

    public abstract Fragment createFragment(int i);

    public FragmentStateAdapter(FragmentActivity fragmentActivity) {
        this(fragmentActivity.getSupportFragmentManager(), fragmentActivity.getLifecycle());
    }

    public FragmentStateAdapter(Fragment fragment) {
        this(fragment.getChildFragmentManager(), fragment.getLifecycle());
    }

    public FragmentStateAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        this.mFragments = new LongSparseArray();
        this.mSavedStates = new LongSparseArray();
        this.mItemIdToViewHolder = new LongSparseArray();
        this.mIsInGracePeriod = false;
        this.mHasStaleFragments = false;
        this.mFragmentManager = fragmentManager;
        this.mLifecycle = lifecycle;
        super.setHasStableIds(true);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Preconditions.checkArgument(this.mFragmentMaxLifecycleEnforcer == null);
        FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer = new FragmentMaxLifecycleEnforcer();
        this.mFragmentMaxLifecycleEnforcer = fragmentMaxLifecycleEnforcer;
        fragmentMaxLifecycleEnforcer.register(recyclerView);
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mFragmentMaxLifecycleEnforcer.unregister(recyclerView);
        this.mFragmentMaxLifecycleEnforcer = null;
    }

    public final FragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FragmentViewHolder.create(parent);
    }

    public final void onBindViewHolder(FragmentViewHolder holder, int position) {
        long itemId = holder.getItemId();
        int viewHolderId = holder.getContainer().getId();
        Long boundItemId = itemForViewHolder(viewHolderId);
        if (boundItemId != null && boundItemId.longValue() != itemId) {
            removeFragment(boundItemId.longValue());
            this.mItemIdToViewHolder.remove(boundItemId.longValue());
        }
        this.mItemIdToViewHolder.put(itemId, Integer.valueOf(viewHolderId));
        ensureFragment(position);
        FrameLayout container = holder.getContainer();
        if (ViewCompat.isAttachedToWindow(container)) {
            if (container.getParent() != null) {
                throw new IllegalStateException("Design assumption violated.");
            }
            container.addOnLayoutChangeListener(new 1(container, holder));
        }
        gcFragments();
    }

    class 1 implements View.OnLayoutChangeListener {
        final /* synthetic */ FrameLayout val$container;
        final /* synthetic */ FragmentViewHolder val$holder;

        1(FrameLayout frameLayout, FragmentViewHolder fragmentViewHolder) {
            this.val$container = frameLayout;
            this.val$holder = fragmentViewHolder;
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (this.val$container.getParent() != null) {
                this.val$container.removeOnLayoutChangeListener(this);
                FragmentStateAdapter.this.placeFragmentInViewHolder(this.val$holder);
            }
        }
    }

    void gcFragments() {
        if (!this.mHasStaleFragments || shouldDelayFragmentTransactions()) {
            return;
        }
        ArraySet arraySet = new ArraySet();
        for (int ix = 0; ix < this.mFragments.size(); ix++) {
            long itemId = this.mFragments.keyAt(ix);
            if (!containsItem(itemId)) {
                arraySet.add(Long.valueOf(itemId));
                this.mItemIdToViewHolder.remove(itemId);
            }
        }
        if (!this.mIsInGracePeriod) {
            this.mHasStaleFragments = false;
            for (int ix2 = 0; ix2 < this.mFragments.size(); ix2++) {
                long itemId2 = this.mFragments.keyAt(ix2);
                if (!isFragmentViewBound(itemId2)) {
                    arraySet.add(Long.valueOf(itemId2));
                }
            }
        }
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            removeFragment(((Long) it.next()).longValue());
        }
    }

    private boolean isFragmentViewBound(long itemId) {
        View view;
        if (this.mItemIdToViewHolder.containsKey(itemId)) {
            return true;
        }
        Fragment fragment = (Fragment) this.mFragments.get(itemId);
        return (fragment == null || (view = fragment.getView()) == null || view.getParent() == null) ? false : true;
    }

    private Long itemForViewHolder(int viewHolderId) {
        Long boundItemId = null;
        for (int ix = 0; ix < this.mItemIdToViewHolder.size(); ix++) {
            if (((Integer) this.mItemIdToViewHolder.valueAt(ix)).intValue() == viewHolderId) {
                if (boundItemId != null) {
                    throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                }
                boundItemId = Long.valueOf(this.mItemIdToViewHolder.keyAt(ix));
            }
        }
        return boundItemId;
    }

    private void ensureFragment(int position) {
        long itemId = getItemId(position);
        if (!this.mFragments.containsKey(itemId)) {
            Fragment newFragment = createFragment(position);
            newFragment.setInitialSavedState((Fragment.SavedState) this.mSavedStates.get(itemId));
            this.mFragments.put(itemId, newFragment);
        }
    }

    public final void onViewAttachedToWindow(FragmentViewHolder holder) {
        placeFragmentInViewHolder(holder);
        gcFragments();
    }

    void placeFragmentInViewHolder(FragmentViewHolder holder) {
        Fragment fragment = (Fragment) this.mFragments.get(holder.getItemId());
        if (fragment == null) {
            throw new IllegalStateException("Design assumption violated.");
        }
        ViewParent container = holder.getContainer();
        View view = fragment.getView();
        if (!fragment.isAdded() && view != null) {
            throw new IllegalStateException("Design assumption violated.");
        }
        if (fragment.isAdded() && view == null) {
            scheduleViewAttach(fragment, container);
            return;
        }
        if (fragment.isAdded() && view.getParent() != null) {
            if (view.getParent() != container) {
                addViewToContainer(view, container);
                return;
            }
            return;
        }
        if (fragment.isAdded()) {
            addViewToContainer(view, container);
            return;
        }
        if (!shouldDelayFragmentTransactions()) {
            scheduleViewAttach(fragment, container);
            this.mFragmentManager.beginTransaction().add(fragment, "f" + holder.getItemId()).setMaxLifecycle(fragment, Lifecycle.State.STARTED).commitNow();
            this.mFragmentMaxLifecycleEnforcer.updateFragmentMaxLifecycle(false);
            return;
        }
        if (this.mFragmentManager.isDestroyed()) {
            return;
        }
        this.mLifecycle.addObserver(new 2(holder));
    }

    class 2 implements LifecycleEventObserver {
        final /* synthetic */ FragmentViewHolder val$holder;

        2(FragmentViewHolder fragmentViewHolder) {
            this.val$holder = fragmentViewHolder;
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (FragmentStateAdapter.this.shouldDelayFragmentTransactions()) {
                return;
            }
            source.getLifecycle().removeObserver(this);
            if (ViewCompat.isAttachedToWindow(this.val$holder.getContainer())) {
                FragmentStateAdapter.this.placeFragmentInViewHolder(this.val$holder);
            }
        }
    }

    class 3 extends FragmentManager.FragmentLifecycleCallbacks {
        final /* synthetic */ FrameLayout val$container;
        final /* synthetic */ Fragment val$fragment;

        3(Fragment fragment, FrameLayout frameLayout) {
            this.val$fragment = fragment;
            this.val$container = frameLayout;
        }

        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            if (f == this.val$fragment) {
                fm.unregisterFragmentLifecycleCallbacks(this);
                FragmentStateAdapter.this.addViewToContainer(v, this.val$container);
            }
        }
    }

    private void scheduleViewAttach(Fragment fragment, FrameLayout container) {
        this.mFragmentManager.registerFragmentLifecycleCallbacks(new 3(fragment, container), false);
    }

    void addViewToContainer(View v, FrameLayout container) {
        if (container.getChildCount() > 1) {
            throw new IllegalStateException("Design assumption violated.");
        }
        if (v.getParent() == container) {
            return;
        }
        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }
        if (v.getParent() != null) {
            v.getParent().removeView(v);
        }
        container.addView(v);
    }

    public final void onViewRecycled(FragmentViewHolder holder) {
        int viewHolderId = holder.getContainer().getId();
        Long boundItemId = itemForViewHolder(viewHolderId);
        if (boundItemId != null) {
            removeFragment(boundItemId.longValue());
            this.mItemIdToViewHolder.remove(boundItemId.longValue());
        }
    }

    public final boolean onFailedToRecycleView(FragmentViewHolder holder) {
        return true;
    }

    private void removeFragment(long itemId) {
        FrameLayout parent;
        Fragment fragment = (Fragment) this.mFragments.get(itemId);
        if (fragment == null) {
            return;
        }
        if (fragment.getView() != null && (parent = fragment.getView().getParent()) != null) {
            parent.removeAllViews();
        }
        if (!containsItem(itemId)) {
            this.mSavedStates.remove(itemId);
        }
        if (!fragment.isAdded()) {
            this.mFragments.remove(itemId);
            return;
        }
        if (shouldDelayFragmentTransactions()) {
            this.mHasStaleFragments = true;
            return;
        }
        if (fragment.isAdded() && containsItem(itemId)) {
            this.mSavedStates.put(itemId, this.mFragmentManager.saveFragmentInstanceState(fragment));
        }
        this.mFragmentManager.beginTransaction().remove(fragment).commitNow();
        this.mFragments.remove(itemId);
    }

    boolean shouldDelayFragmentTransactions() {
        return this.mFragmentManager.isStateSaved();
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean containsItem(long itemId) {
        return itemId >= 0 && itemId < ((long) getItemCount());
    }

    public final void setHasStableIds(boolean hasStableIds) {
        throw new UnsupportedOperationException("Stable Ids are required for the adapter to function properly, and the adapter takes care of setting the flag.");
    }

    public final Parcelable saveState() {
        Bundle savedState = new Bundle(this.mFragments.size() + this.mSavedStates.size());
        for (int ix = 0; ix < this.mFragments.size(); ix++) {
            long itemId = this.mFragments.keyAt(ix);
            Fragment fragment = (Fragment) this.mFragments.get(itemId);
            if (fragment != null && fragment.isAdded()) {
                String key = createKey("f#", itemId);
                this.mFragmentManager.putFragment(savedState, key, fragment);
            }
        }
        for (int ix2 = 0; ix2 < this.mSavedStates.size(); ix2++) {
            long itemId2 = this.mSavedStates.keyAt(ix2);
            if (containsItem(itemId2)) {
                String key2 = createKey("s#", itemId2);
                savedState.putParcelable(key2, (Parcelable) this.mSavedStates.get(itemId2));
            }
        }
        return savedState;
    }

    public final void restoreState(Parcelable savedState) {
        if (!this.mSavedStates.isEmpty() || !this.mFragments.isEmpty()) {
            throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
        }
        Bundle bundle = (Bundle) savedState;
        if (bundle.getClassLoader() == null) {
            bundle.setClassLoader(getClass().getClassLoader());
        }
        for (String key : bundle.keySet()) {
            if (isValidKey(key, "f#")) {
                long itemId = parseIdFromKey(key, "f#");
                Fragment fragment = this.mFragmentManager.getFragment(bundle, key);
                this.mFragments.put(itemId, fragment);
            } else if (isValidKey(key, "s#")) {
                long itemId2 = parseIdFromKey(key, "s#");
                Fragment.SavedState state = bundle.getParcelable(key);
                if (containsItem(itemId2)) {
                    this.mSavedStates.put(itemId2, state);
                }
            } else {
                throw new IllegalArgumentException("Unexpected key in savedState: " + key);
            }
        }
        if (!this.mFragments.isEmpty()) {
            this.mHasStaleFragments = true;
            this.mIsInGracePeriod = true;
            gcFragments();
            scheduleGracePeriodEnd();
        }
    }

    class 4 implements Runnable {
        4() {
        }

        public void run() {
            FragmentStateAdapter.this.mIsInGracePeriod = false;
            FragmentStateAdapter.this.gcFragments();
        }
    }

    private void scheduleGracePeriodEnd() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new 4();
        this.mLifecycle.addObserver(new 5(handler, runnable));
        handler.postDelayed(runnable, 10000L);
    }

    class 5 implements LifecycleEventObserver {
        final /* synthetic */ Handler val$handler;
        final /* synthetic */ Runnable val$runnable;

        5(Handler handler, Runnable runnable) {
            this.val$handler = handler;
            this.val$runnable = runnable;
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                this.val$handler.removeCallbacks(this.val$runnable);
                source.getLifecycle().removeObserver(this);
            }
        }
    }

    private static String createKey(String prefix, long id) {
        return prefix + id;
    }

    private static boolean isValidKey(String key, String prefix) {
        return key.startsWith(prefix) && key.length() > prefix.length();
    }

    private static long parseIdFromKey(String key, String prefix) {
        return Long.parseLong(key.substring(prefix.length()));
    }

    class FragmentMaxLifecycleEnforcer {
        private RecyclerView.AdapterDataObserver mDataObserver;
        private LifecycleEventObserver mLifecycleObserver;
        private ViewPager2.OnPageChangeCallback mPageChangeCallback;
        private long mPrimaryItemId = -1;
        private ViewPager2 mViewPager;

        FragmentMaxLifecycleEnforcer() {
        }

        void register(RecyclerView recyclerView) {
            this.mViewPager = inferViewPager(recyclerView);
            1 r0 = new 1();
            this.mPageChangeCallback = r0;
            this.mViewPager.registerOnPageChangeCallback(r0);
            2 r02 = new 2();
            this.mDataObserver = r02;
            FragmentStateAdapter.this.registerAdapterDataObserver(r02);
            this.mLifecycleObserver = new 3();
            FragmentStateAdapter.this.mLifecycle.addObserver(this.mLifecycleObserver);
        }

        class 1 extends ViewPager2.OnPageChangeCallback {
            1() {
            }

            public void onPageScrollStateChanged(int state) {
                FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
            }

            public void onPageSelected(int position) {
                FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
            }
        }

        class 2 extends DataSetChangeObserver {
            2() {
                super(null);
            }

            public void onChanged() {
                FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(true);
            }
        }

        class 3 implements LifecycleEventObserver {
            3() {
            }

            public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
            }
        }

        void unregister(RecyclerView recyclerView) {
            ViewPager2 viewPager = inferViewPager(recyclerView);
            viewPager.unregisterOnPageChangeCallback(this.mPageChangeCallback);
            FragmentStateAdapter.this.unregisterAdapterDataObserver(this.mDataObserver);
            FragmentStateAdapter.this.mLifecycle.removeObserver(this.mLifecycleObserver);
            this.mViewPager = null;
        }

        void updateFragmentMaxLifecycle(boolean dataSetChanged) {
            int currentItem;
            Fragment currentItemFragment;
            if (FragmentStateAdapter.this.shouldDelayFragmentTransactions() || this.mViewPager.getScrollState() != 0 || FragmentStateAdapter.this.mFragments.isEmpty() || FragmentStateAdapter.this.getItemCount() == 0 || (currentItem = this.mViewPager.getCurrentItem()) >= FragmentStateAdapter.this.getItemCount()) {
                return;
            }
            long currentItemId = FragmentStateAdapter.this.getItemId(currentItem);
            if ((currentItemId == this.mPrimaryItemId && !dataSetChanged) || (currentItemFragment = (Fragment) FragmentStateAdapter.this.mFragments.get(currentItemId)) == null || !currentItemFragment.isAdded()) {
                return;
            }
            this.mPrimaryItemId = currentItemId;
            FragmentTransaction transaction = FragmentStateAdapter.this.mFragmentManager.beginTransaction();
            Fragment toResume = null;
            for (int ix = 0; ix < FragmentStateAdapter.this.mFragments.size(); ix++) {
                long itemId = FragmentStateAdapter.this.mFragments.keyAt(ix);
                Fragment fragment = (Fragment) FragmentStateAdapter.this.mFragments.valueAt(ix);
                if (fragment.isAdded()) {
                    if (itemId != this.mPrimaryItemId) {
                        transaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
                    } else {
                        toResume = fragment;
                    }
                    fragment.setMenuVisibility(itemId == this.mPrimaryItemId);
                }
            }
            if (toResume != null) {
                transaction.setMaxLifecycle(toResume, Lifecycle.State.RESUMED);
            }
            if (!transaction.isEmpty()) {
                transaction.commitNow();
            }
        }

        private ViewPager2 inferViewPager(RecyclerView recyclerView) {
            ViewPager2 parent = recyclerView.getParent();
            if (parent instanceof ViewPager2) {
                return parent;
            }
            throw new IllegalStateException("Expected ViewPager2 instance. Got: " + parent);
        }
    }

    private static abstract class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        public abstract void onChanged();

        private DataSetChangeObserver() {
        }

        /* synthetic */ DataSetChangeObserver(1 x0) {
            this();
        }

        public final void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        public final void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }

        public final void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        public final void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        public final void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    }
}
