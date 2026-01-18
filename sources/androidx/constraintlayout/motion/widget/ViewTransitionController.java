package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.SharedValues;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class ViewTransitionController {
    ArrayList animations;
    private final MotionLayout mMotionLayout;
    private HashSet mRelatedViews;
    private ArrayList viewTransitions = new ArrayList();
    private String TAG = "ViewTransitionController";
    ArrayList removeList = new ArrayList();

    static /* synthetic */ MotionLayout access$000(ViewTransitionController x0) {
        return x0.mMotionLayout;
    }

    public ViewTransitionController(MotionLayout layout) {
        this.mMotionLayout = layout;
    }

    public void add(ViewTransition viewTransition) {
        this.viewTransitions.add(viewTransition);
        this.mRelatedViews = null;
        if (viewTransition.getStateTransition() == 4) {
            listenForSharedVariable(viewTransition, true);
        } else if (viewTransition.getStateTransition() == 5) {
            listenForSharedVariable(viewTransition, false);
        }
    }

    void remove(int id) {
        ViewTransition del = null;
        Iterator it = this.viewTransitions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ViewTransition viewTransition = (ViewTransition) it.next();
            if (viewTransition.getId() == id) {
                del = viewTransition;
                break;
            }
        }
        if (del != null) {
            this.mRelatedViews = null;
            this.viewTransitions.remove(del);
        }
    }

    private void viewTransition(ViewTransition vt, View... view) {
        int currentId = this.mMotionLayout.getCurrentState();
        if (vt.mViewTransitionMode != 2) {
            if (currentId == -1) {
                String str = this.TAG;
                String valueOf = String.valueOf(this.mMotionLayout.toString());
                Log.w(str, valueOf.length() != 0 ? "No support for ViewTransition within transition yet. Currently: ".concat(valueOf) : new String("No support for ViewTransition within transition yet. Currently: "));
                return;
            } else {
                ConstraintSet current = this.mMotionLayout.getConstraintSet(currentId);
                if (current == null) {
                    return;
                }
                vt.applyTransition(this, this.mMotionLayout, currentId, current, view);
                return;
            }
        }
        vt.applyTransition(this, this.mMotionLayout, currentId, null, view);
    }

    void enableViewTransition(int id, boolean enable) {
        Iterator it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = (ViewTransition) it.next();
            if (viewTransition.getId() == id) {
                viewTransition.setEnabled(enable);
                return;
            }
        }
    }

    boolean isViewTransitionEnabled(int id) {
        Iterator it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = (ViewTransition) it.next();
            if (viewTransition.getId() == id) {
                return viewTransition.isEnabled();
            }
        }
        return false;
    }

    void viewTransition(int id, View... views) {
        ViewTransition vt = null;
        ArrayList<View> list = new ArrayList<>();
        Iterator it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = (ViewTransition) it.next();
            if (viewTransition.getId() == id) {
                vt = viewTransition;
                for (View view : views) {
                    if (viewTransition.checkTags(view)) {
                        list.add(view);
                    }
                }
                if (!list.isEmpty()) {
                    viewTransition(vt, (View[]) list.toArray(new View[0]));
                    list.clear();
                }
            }
        }
        if (vt == null) {
            Log.e(this.TAG, " Could not find ViewTransition");
        }
    }

    void touchEvent(MotionEvent event) {
        int currentId = this.mMotionLayout.getCurrentState();
        if (currentId == -1) {
        }
        if (this.mRelatedViews == null) {
            this.mRelatedViews = new HashSet();
            Iterator it = this.viewTransitions.iterator();
            while (it.hasNext()) {
                ViewTransition viewTransition = (ViewTransition) it.next();
                int count = this.mMotionLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = this.mMotionLayout.getChildAt(i);
                    if (viewTransition.matchesView(view)) {
                        view.getId();
                        this.mRelatedViews.add(view);
                    }
                }
            }
        }
        float x = event.getX();
        float y = event.getY();
        Rect rec = new Rect();
        int action = event.getAction();
        ArrayList arrayList = this.animations;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator it2 = this.animations.iterator();
            while (it2.hasNext()) {
                ViewTransition.Animate animation = (ViewTransition.Animate) it2.next();
                animation.reactTo(action, x, y);
            }
        }
        switch (action) {
            case 0:
            case 1:
                ConstraintSet current = this.mMotionLayout.getConstraintSet(currentId);
                Iterator it3 = this.viewTransitions.iterator();
                while (it3.hasNext()) {
                    ViewTransition viewTransition2 = (ViewTransition) it3.next();
                    if (viewTransition2.supports(action)) {
                        Iterator it4 = this.mRelatedViews.iterator();
                        while (it4.hasNext()) {
                            View view2 = (View) it4.next();
                            if (viewTransition2.matchesView(view2)) {
                                view2.getHitRect(rec);
                                if (rec.contains((int) x, (int) y)) {
                                    viewTransition2.applyTransition(this, this.mMotionLayout, currentId, current, view2);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    void addAnimation(ViewTransition.Animate animation) {
        if (this.animations == null) {
            this.animations = new ArrayList();
        }
        this.animations.add(animation);
    }

    void removeAnimation(ViewTransition.Animate animation) {
        this.removeList.add(animation);
    }

    void animate() {
        ArrayList arrayList = this.animations;
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ViewTransition.Animate animation = (ViewTransition.Animate) it.next();
            animation.mutate();
        }
        this.animations.removeAll(this.removeList);
        this.removeList.clear();
        if (this.animations.isEmpty()) {
            this.animations = null;
        }
    }

    void invalidate() {
        this.mMotionLayout.invalidate();
    }

    boolean applyViewTransition(int viewTransitionId, MotionController motionController) {
        Iterator it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = (ViewTransition) it.next();
            if (viewTransition.getId() == viewTransitionId) {
                viewTransition.mKeyFrames.addAllFrames(motionController);
                return true;
            }
        }
        return false;
    }

    private void listenForSharedVariable(ViewTransition viewTransition, boolean isSet) {
        int listen_for_id = viewTransition.getSharedValueID();
        int listen_for_value = viewTransition.getSharedValue();
        ConstraintLayout.getSharedValues().addListener(viewTransition.getSharedValueID(), new 1(viewTransition, listen_for_id, isSet, listen_for_value));
    }

    class 1 implements SharedValues.SharedValuesListener {
        final /* synthetic */ boolean val$isSet;
        final /* synthetic */ int val$listen_for_id;
        final /* synthetic */ int val$listen_for_value;
        final /* synthetic */ ViewTransition val$viewTransition;

        1(final ViewTransition val$viewTransition, final int val$listen_for_id, final boolean val$isSet, final int val$listen_for_value) {
            this.val$viewTransition = val$viewTransition;
            this.val$listen_for_id = val$listen_for_id;
            this.val$isSet = val$isSet;
            this.val$listen_for_value = val$listen_for_value;
        }

        public void onNewValue(int id, int value, int oldValue) {
            int current_value = this.val$viewTransition.getSharedValueCurrent();
            this.val$viewTransition.setSharedValueCurrent(value);
            if (this.val$listen_for_id == id && current_value != value) {
                if (this.val$isSet) {
                    if (this.val$listen_for_value == value) {
                        int count = ViewTransitionController.access$000(ViewTransitionController.this).getChildCount();
                        for (int i = 0; i < count; i++) {
                            View view = ViewTransitionController.access$000(ViewTransitionController.this).getChildAt(i);
                            if (this.val$viewTransition.matchesView(view)) {
                                int currentId = ViewTransitionController.access$000(ViewTransitionController.this).getCurrentState();
                                ConstraintSet current = ViewTransitionController.access$000(ViewTransitionController.this).getConstraintSet(currentId);
                                ViewTransition viewTransition = this.val$viewTransition;
                                ViewTransitionController viewTransitionController = ViewTransitionController.this;
                                viewTransition.applyTransition(viewTransitionController, ViewTransitionController.access$000(viewTransitionController), currentId, current, view);
                            }
                        }
                        return;
                    }
                    return;
                }
                if (this.val$listen_for_value != value) {
                    int count2 = ViewTransitionController.access$000(ViewTransitionController.this).getChildCount();
                    for (int i2 = 0; i2 < count2; i2++) {
                        View view2 = ViewTransitionController.access$000(ViewTransitionController.this).getChildAt(i2);
                        if (this.val$viewTransition.matchesView(view2)) {
                            int currentId2 = ViewTransitionController.access$000(ViewTransitionController.this).getCurrentState();
                            ConstraintSet current2 = ViewTransitionController.access$000(ViewTransitionController.this).getConstraintSet(currentId2);
                            ViewTransition viewTransition2 = this.val$viewTransition;
                            ViewTransitionController viewTransitionController2 = ViewTransitionController.this;
                            viewTransition2.applyTransition(viewTransitionController2, ViewTransitionController.access$000(viewTransitionController2), currentId2, current2, view2);
                        }
                    }
                }
            }
        }
    }
}
