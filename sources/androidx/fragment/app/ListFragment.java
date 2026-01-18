package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
public class ListFragment extends Fragment {
    static final int INTERNAL_EMPTY_ID = 16711681;
    static final int INTERNAL_LIST_CONTAINER_ID = 16711683;
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 16711682;
    ListAdapter mAdapter;
    CharSequence mEmptyText;
    View mEmptyView;
    ListView mList;
    View mListContainer;
    boolean mListShown;
    View mProgressContainer;
    TextView mStandardEmptyView;
    private final Handler mHandler = new Handler();
    private final Runnable mRequestFocus = new 1();
    private final AdapterView.OnItemClickListener mOnClickListener = new 2();

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList);
        }
    }

    class 2 implements AdapterView.OnItemClickListener {
        2() {
        }

        public void onItemClick(AdapterView adapterView, View v, int position, long id) {
            ListFragment.this.onListItemClick((ListView) adapterView, v, position, id);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = requireContext();
        FrameLayout root = new FrameLayout(context);
        LinearLayout pframe = new LinearLayout(context);
        pframe.setId(16711682);
        pframe.setOrientation(1);
        pframe.setVisibility(8);
        pframe.setGravity(17);
        ProgressBar progress = new ProgressBar(context, (AttributeSet) null, 16842874);
        pframe.addView(progress, new FrameLayout.LayoutParams(-2, -2));
        root.addView(pframe, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout lframe = new FrameLayout(context);
        lframe.setId(16711683);
        TextView tv = new TextView(context);
        tv.setId(16711681);
        tv.setGravity(17);
        lframe.addView(tv, new FrameLayout.LayoutParams(-1, -1));
        ListView lv = new ListView(context);
        lv.setId(16908298);
        lv.setDrawSelectorOnTop(false);
        lframe.addView(lv, new FrameLayout.LayoutParams(-1, -1));
        root.addView(lframe, new FrameLayout.LayoutParams(-1, -1));
        root.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }

    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mList = null;
        this.mListShown = false;
        this.mListContainer = null;
        this.mProgressContainer = null;
        this.mEmptyView = null;
        this.mStandardEmptyView = null;
        super.onDestroyView();
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    public void setListAdapter(ListAdapter adapter) {
        boolean hadAdapter = this.mAdapter != null;
        this.mAdapter = adapter;
        ListView listView = this.mList;
        if (listView != null) {
            listView.setAdapter(adapter);
            if (!this.mListShown && !hadAdapter) {
                setListShown(true, requireView().getWindowToken() != null);
            }
        }
    }

    public void setSelection(int position) {
        ensureList();
        this.mList.setSelection(position);
    }

    public int getSelectedItemPosition() {
        ensureList();
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        ensureList();
        return this.mList.getSelectedItemId();
    }

    public ListView getListView() {
        ensureList();
        return this.mList;
    }

    public void setEmptyText(CharSequence text) {
        ensureList();
        TextView textView = this.mStandardEmptyView;
        if (textView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        textView.setText(text);
        if (this.mEmptyText == null) {
            this.mList.setEmptyView(this.mStandardEmptyView);
        }
        this.mEmptyText = text;
    }

    public void setListShown(boolean shown) {
        setListShown(shown, true);
    }

    public void setListShownNoAnimation(boolean shown) {
        setListShown(shown, false);
    }

    private void setListShown(boolean shown, boolean animate) {
        ensureList();
        View view = this.mProgressContainer;
        if (view == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (this.mListShown == shown) {
            return;
        }
        this.mListShown = shown;
        if (shown) {
            if (animate) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
                this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
            } else {
                view.clearAnimation();
                this.mListContainer.clearAnimation();
            }
            this.mProgressContainer.setVisibility(8);
            this.mListContainer.setVisibility(0);
            return;
        }
        if (animate) {
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
            this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
        } else {
            view.clearAnimation();
            this.mListContainer.clearAnimation();
        }
        this.mProgressContainer.setVisibility(0);
        this.mListContainer.setVisibility(8);
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    public final ListAdapter requireListAdapter() {
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter == null) {
            throw new IllegalStateException("ListFragment " + this + " does not have a ListAdapter.");
        }
        return listAdapter;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        ListView view = getView();
        if (view == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (view instanceof ListView) {
            this.mList = view;
        } else {
            TextView findViewById = view.findViewById(16711681);
            this.mStandardEmptyView = findViewById;
            if (findViewById == null) {
                this.mEmptyView = view.findViewById(16908292);
            } else {
                findViewById.setVisibility(8);
            }
            this.mProgressContainer = view.findViewById(16711682);
            this.mListContainer = view.findViewById(16711683);
            ListView findViewById2 = view.findViewById(16908298);
            if (!(findViewById2 instanceof ListView)) {
                if (findViewById2 == null) {
                    throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
                }
                throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
            }
            ListView listView = findViewById2;
            this.mList = listView;
            View view2 = this.mEmptyView;
            if (view2 != null) {
                listView.setEmptyView(view2);
            } else {
                CharSequence charSequence = this.mEmptyText;
                if (charSequence != null) {
                    this.mStandardEmptyView.setText(charSequence);
                    this.mList.setEmptyView(this.mStandardEmptyView);
                }
            }
        }
        this.mListShown = true;
        this.mList.setOnItemClickListener(this.mOnClickListener);
        if (this.mAdapter != null) {
            ListAdapter adapter = this.mAdapter;
            this.mAdapter = null;
            setListAdapter(adapter);
        } else if (this.mProgressContainer != null) {
            setListShown(false, false);
        }
        this.mHandler.post(this.mRequestFocus);
    }
}
