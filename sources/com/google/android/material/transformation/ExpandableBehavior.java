package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.expandable.ExpandableWidget;
import java.util.List;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public abstract class ExpandableBehavior extends CoordinatorLayout.Behavior {
    private static final int STATE_COLLAPSED = 2;
    private static final int STATE_EXPANDED = 1;
    private static final int STATE_UNINITIALIZED = 0;
    private int currentState;

    public abstract boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2);

    protected abstract boolean onExpandedStateChange(View view, View view2, boolean z, boolean z2);

    static /* synthetic */ int access$000(ExpandableBehavior x0) {
        return x0.currentState;
    }

    public ExpandableBehavior() {
        this.currentState = 0;
    }

    public ExpandableBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentState = 0;
    }

    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        ExpandableWidget dep;
        if (!ViewCompat.isLaidOut(child) && (dep = findExpandableWidget(parent, child)) != null && didStateChange(dep.isExpanded())) {
            this.currentState = dep.isExpanded() ? 1 : 2;
            int expectedState = this.currentState;
            child.getViewTreeObserver().addOnPreDrawListener(new 1(child, expectedState, dep));
            return false;
        }
        return false;
    }

    class 1 implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ View val$child;
        final /* synthetic */ ExpandableWidget val$dep;
        final /* synthetic */ int val$expectedState;

        1(View view, int i, ExpandableWidget expandableWidget) {
            this.val$child = view;
            this.val$expectedState = i;
            this.val$dep = expandableWidget;
        }

        public boolean onPreDraw() {
            this.val$child.getViewTreeObserver().removeOnPreDrawListener(this);
            if (ExpandableBehavior.access$000(ExpandableBehavior.this) == this.val$expectedState) {
                ExpandableBehavior expandableBehavior = ExpandableBehavior.this;
                ExpandableWidget expandableWidget = this.val$dep;
                expandableBehavior.onExpandedStateChange((View) expandableWidget, this.val$child, expandableWidget.isExpanded(), false);
            }
            return false;
        }
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        ExpandableWidget dep = (ExpandableWidget) dependency;
        boolean expanded = dep.isExpanded();
        if (didStateChange(expanded)) {
            this.currentState = dep.isExpanded() ? 1 : 2;
            return onExpandedStateChange((View) dep, child, dep.isExpanded(), true);
        }
        return false;
    }

    protected ExpandableWidget findExpandableWidget(CoordinatorLayout parent, View child) {
        List<View> dependencies = parent.getDependencies(child);
        int size = dependencies.size();
        for (int i = 0; i < size; i++) {
            ExpandableWidget expandableWidget = (View) dependencies.get(i);
            if (layoutDependsOn(parent, child, expandableWidget)) {
                return expandableWidget;
            }
        }
        return null;
    }

    private boolean didStateChange(boolean expanded) {
        if (!expanded) {
            return this.currentState == 1;
        }
        int i = this.currentState;
        return i == 0 || i == 2;
    }

    public static ExpandableBehavior from(View view, Class cls) {
        CoordinatorLayout.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior<?> behavior = layoutParams.getBehavior();
        if (!(behavior instanceof ExpandableBehavior)) {
            throw new IllegalArgumentException("The view is not associated with ExpandableBehavior");
        }
        return (ExpandableBehavior) cls.cast(behavior);
    }
}
