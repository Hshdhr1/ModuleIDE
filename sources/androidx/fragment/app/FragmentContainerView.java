package androidx.fragment.app;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.R;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
public final class FragmentContainerView extends FrameLayout {
    private View.OnApplyWindowInsetsListener mApplyWindowInsetsListener;
    private ArrayList mDisappearingFragmentChildren;
    private boolean mDrawDisappearingViewsFirst;
    private ArrayList mTransitioningFragmentViews;

    public FragmentContainerView(Context context) {
        super(context);
        this.mDrawDisappearingViewsFirst = true;
    }

    public FragmentContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FragmentContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDrawDisappearingViewsFirst = true;
        if (attrs != null) {
            String name = attrs.getClassAttribute();
            String attribute = "class";
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FragmentContainerView);
            if (name == null) {
                name = a.getString(R.styleable.FragmentContainerView_android_name);
                attribute = "android:name";
            }
            a.recycle();
            if (name != null && !isInEditMode()) {
                throw new UnsupportedOperationException("FragmentContainerView must be within a FragmentActivity to use " + attribute + "=\"" + name + "\"");
            }
        }
    }

    FragmentContainerView(Context context, AttributeSet attrs, FragmentManager fm) {
        String tagMessage;
        super(context, attrs);
        this.mDrawDisappearingViewsFirst = true;
        String name = attrs.getClassAttribute();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FragmentContainerView);
        name = name == null ? a.getString(R.styleable.FragmentContainerView_android_name) : name;
        String tag = a.getString(R.styleable.FragmentContainerView_android_tag);
        a.recycle();
        int id = getId();
        Fragment existingFragment = fm.findFragmentById(id);
        if (name != null && existingFragment == null) {
            if (id <= 0) {
                if (tag != null) {
                    tagMessage = " with tag " + tag;
                } else {
                    tagMessage = "";
                }
                throw new IllegalStateException("FragmentContainerView must have an android:id to add Fragment " + name + tagMessage);
            }
            Fragment containerFragment = fm.getFragmentFactory().instantiate(context.getClassLoader(), name);
            containerFragment.onInflate(context, attrs, (Bundle) null);
            fm.beginTransaction().setReorderingAllowed(true).add((ViewGroup) this, containerFragment, tag).commitNowAllowingStateLoss();
        }
        fm.onContainerAvailable(this);
    }

    public void setLayoutTransition(LayoutTransition transition) {
        if (Build.VERSION.SDK_INT < 18) {
            super.setLayoutTransition(transition);
            return;
        }
        throw new UnsupportedOperationException("FragmentContainerView does not support Layout Transitions or animateLayoutChanges=\"true\".");
    }

    public void setOnApplyWindowInsetsListener(View.OnApplyWindowInsetsListener listener) {
        this.mApplyWindowInsetsListener = listener;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return insets;
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        WindowInsetsCompat dispatchInsets;
        WindowInsetsCompat insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets);
        View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = this.mApplyWindowInsetsListener;
        if (onApplyWindowInsetsListener != null) {
            dispatchInsets = WindowInsetsCompat.toWindowInsetsCompat(onApplyWindowInsetsListener.onApplyWindowInsets(this, insets));
        } else {
            dispatchInsets = ViewCompat.onApplyWindowInsets(this, insetsCompat);
        }
        if (!dispatchInsets.isConsumed()) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                ViewCompat.dispatchApplyWindowInsets(getChildAt(i), dispatchInsets);
            }
        }
        return insets;
    }

    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawDisappearingViewsFirst && this.mDisappearingFragmentChildren != null) {
            for (int i = 0; i < this.mDisappearingFragmentChildren.size(); i++) {
                super.drawChild(canvas, (View) this.mDisappearingFragmentChildren.get(i), getDrawingTime());
            }
        }
        super.dispatchDraw(canvas);
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        ArrayList arrayList;
        if (this.mDrawDisappearingViewsFirst && (arrayList = this.mDisappearingFragmentChildren) != null && arrayList.size() > 0 && this.mDisappearingFragmentChildren.contains(child)) {
            return false;
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    public void startViewTransition(View view) {
        if (view.getParent() == this) {
            if (this.mTransitioningFragmentViews == null) {
                this.mTransitioningFragmentViews = new ArrayList();
            }
            this.mTransitioningFragmentViews.add(view);
        }
        super.startViewTransition(view);
    }

    public void endViewTransition(View view) {
        ArrayList arrayList = this.mTransitioningFragmentViews;
        if (arrayList != null) {
            arrayList.remove(view);
            ArrayList arrayList2 = this.mDisappearingFragmentChildren;
            if (arrayList2 != null && arrayList2.remove(view)) {
                this.mDrawDisappearingViewsFirst = true;
            }
        }
        super.endViewTransition(view);
    }

    void setDrawDisappearingViewsLast(boolean drawDisappearingViewsFirst) {
        this.mDrawDisappearingViewsFirst = drawDisappearingViewsFirst;
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (FragmentManager.getViewFragment(child) == null) {
            throw new IllegalStateException("Views added to a FragmentContainerView must be associated with a Fragment. View " + child + " is not associated with a Fragment.");
        }
        super.addView(child, index, params);
    }

    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (FragmentManager.getViewFragment(child) == null) {
            throw new IllegalStateException("Views added to a FragmentContainerView must be associated with a Fragment. View " + child + " is not associated with a Fragment.");
        }
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    public void removeViewAt(int index) {
        View view = getChildAt(index);
        addDisappearingFragmentView(view);
        super.removeViewAt(index);
    }

    public void removeViewInLayout(View view) {
        addDisappearingFragmentView(view);
        super.removeViewInLayout(view);
    }

    public void removeView(View view) {
        addDisappearingFragmentView(view);
        super.removeView(view);
    }

    public void removeViews(int start, int count) {
        for (int i = start; i < start + count; i++) {
            View view = getChildAt(i);
            addDisappearingFragmentView(view);
        }
        super.removeViews(start, count);
    }

    public void removeViewsInLayout(int start, int count) {
        for (int i = start; i < start + count; i++) {
            View view = getChildAt(i);
            addDisappearingFragmentView(view);
        }
        super.removeViewsInLayout(start, count);
    }

    public void removeAllViewsInLayout() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            addDisappearingFragmentView(view);
        }
        super.removeAllViewsInLayout();
    }

    protected void removeDetachedView(View child, boolean animate) {
        if (animate) {
            addDisappearingFragmentView(child);
        }
        super.removeDetachedView(child, animate);
    }

    private void addDisappearingFragmentView(View v) {
        ArrayList arrayList = this.mTransitioningFragmentViews;
        if (arrayList != null && arrayList.contains(v)) {
            if (this.mDisappearingFragmentChildren == null) {
                this.mDisappearingFragmentChildren = new ArrayList();
            }
            this.mDisappearingFragmentChildren.add(v);
        }
    }
}
