package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
class DefaultSpecialEffectsController extends SpecialEffectsController {
    DefaultSpecialEffectsController(ViewGroup container) {
        super(container);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void executeOperations(java.util.List r13, boolean r14) {
        /*
            r12 = this;
            r0 = 0
            r1 = 0
            java.util.Iterator r2 = r13.iterator()
        L6:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L3a
            java.lang.Object r3 = r2.next()
            androidx.fragment.app.SpecialEffectsController$Operation r3 = (androidx.fragment.app.SpecialEffectsController.Operation) r3
            androidx.fragment.app.Fragment r4 = r3.getFragment()
            android.view.View r4 = r4.mView
            androidx.fragment.app.SpecialEffectsController$Operation$State r4 = androidx.fragment.app.SpecialEffectsController.Operation.State.from(r4)
            int[] r5 = androidx.fragment.app.DefaultSpecialEffectsController.10.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State
            androidx.fragment.app.SpecialEffectsController$Operation$State r6 = r3.getFinalState()
            int r6 = r6.ordinal()
            r5 = r5[r6]
            switch(r5) {
                case 1: goto L32;
                case 2: goto L32;
                case 3: goto L32;
                case 4: goto L2c;
                default: goto L2b;
            }
        L2b:
            goto L39
        L2c:
            androidx.fragment.app.SpecialEffectsController$Operation$State r5 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
            if (r4 == r5) goto L39
            r1 = r3
            goto L39
        L32:
            androidx.fragment.app.SpecialEffectsController$Operation$State r5 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
            if (r4 != r5) goto L39
            if (r0 != 0) goto L39
            r0 = r3
        L39:
            goto L6
        L3a:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r9 = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>(r13)
            r10 = r3
            java.util.Iterator r3 = r13.iterator()
        L4f:
            boolean r4 = r3.hasNext()
            r11 = 1
            if (r4 == 0) goto L8f
            java.lang.Object r4 = r3.next()
            androidx.fragment.app.SpecialEffectsController$Operation r4 = (androidx.fragment.app.SpecialEffectsController.Operation) r4
            androidx.core.os.CancellationSignal r5 = new androidx.core.os.CancellationSignal
            r5.<init>()
            r4.markStartedSpecialEffect(r5)
            androidx.fragment.app.DefaultSpecialEffectsController$AnimationInfo r6 = new androidx.fragment.app.DefaultSpecialEffectsController$AnimationInfo
            r6.<init>(r4, r5, r14)
            r2.add(r6)
            androidx.core.os.CancellationSignal r6 = new androidx.core.os.CancellationSignal
            r6.<init>()
            r4.markStartedSpecialEffect(r6)
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r7 = new androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo
            r8 = 0
            if (r14 == 0) goto L7c
            if (r4 != r0) goto L7f
            goto L7e
        L7c:
            if (r4 != r1) goto L7f
        L7e:
            goto L80
        L7f:
            r11 = 0
        L80:
            r7.<init>(r4, r6, r14, r11)
            r9.add(r7)
            androidx.fragment.app.DefaultSpecialEffectsController$1 r7 = new androidx.fragment.app.DefaultSpecialEffectsController$1
            r7.<init>(r10, r4)
            r4.addCompletionListener(r7)
            goto L4f
        L8f:
            r3 = r12
            r4 = r9
            r5 = r10
            r6 = r14
            r7 = r0
            r8 = r1
            java.util.Map r3 = r3.startTransitions(r4, r5, r6, r7, r8)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r11)
            boolean r4 = r3.containsValue(r4)
            r12.startAnimations(r2, r10, r4, r3)
            java.util.Iterator r5 = r10.iterator()
        La8:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto Lb8
            java.lang.Object r6 = r5.next()
            androidx.fragment.app.SpecialEffectsController$Operation r6 = (androidx.fragment.app.SpecialEffectsController.Operation) r6
            r12.applyContainerChanges(r6)
            goto La8
        Lb8:
            r10.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.executeOperations(java.util.List, boolean):void");
    }

    static /* synthetic */ class 10 {
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    class 1 implements Runnable {
        final /* synthetic */ List val$awaitingContainerChanges;
        final /* synthetic */ SpecialEffectsController.Operation val$operation;

        1(List list, SpecialEffectsController.Operation operation) {
            this.val$awaitingContainerChanges = list;
            this.val$operation = operation;
        }

        public void run() {
            if (this.val$awaitingContainerChanges.contains(this.val$operation)) {
                this.val$awaitingContainerChanges.remove(this.val$operation);
                DefaultSpecialEffectsController.this.applyContainerChanges(this.val$operation);
            }
        }
    }

    private void startAnimations(List list, List list2, boolean startedAnyTransition, Map map) {
        ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> animationsToRun = new ArrayList<>();
        boolean startedAnyAnimator = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AnimationInfo animationInfo = (AnimationInfo) it.next();
            if (animationInfo.isVisibilityUnchanged()) {
                animationInfo.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator anim = animationInfo.getAnimation(context);
                if (anim == null) {
                    animationInfo.completeSpecialEffect();
                } else {
                    Animator animator = anim.animator;
                    if (animator == null) {
                        animationsToRun.add(animationInfo);
                    } else {
                        SpecialEffectsController.Operation operation = animationInfo.getOperation();
                        Fragment fragment = operation.getFragment();
                        boolean startedTransition = Boolean.TRUE.equals(map.get(operation));
                        if (startedTransition) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo.completeSpecialEffect();
                        } else {
                            boolean isHideOperation = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (isHideOperation) {
                                list2.remove(operation);
                            }
                            View viewToAnimate = fragment.mView;
                            container.startViewTransition(viewToAnimate);
                            animator.addListener(new 2(container, viewToAnimate, isHideOperation, operation, animationInfo));
                            animator.setTarget(viewToAnimate);
                            animator.start();
                            CancellationSignal signal = animationInfo.getSignal();
                            signal.setOnCancelListener(new 3(animator));
                            startedAnyAnimator = true;
                            it = it;
                        }
                    }
                }
            }
        }
        Iterator it2 = animationsToRun.iterator();
        while (it2.hasNext()) {
            AnimationInfo animationInfo2 = (AnimationInfo) it2.next();
            SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
            Fragment fragment2 = operation2.getFragment();
            if (startedAnyTransition) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo2.completeSpecialEffect();
            } else if (startedAnyAnimator) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo2.completeSpecialEffect();
            } else {
                View viewToAnimate2 = fragment2.mView;
                Animation anim2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo2.getAnimation(context))).animation);
                SpecialEffectsController.Operation.State finalState = operation2.getFinalState();
                if (finalState != SpecialEffectsController.Operation.State.REMOVED) {
                    viewToAnimate2.startAnimation(anim2);
                    animationInfo2.completeSpecialEffect();
                } else {
                    container.startViewTransition(viewToAnimate2);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(anim2, container, viewToAnimate2);
                    endViewTransitionAnimation.setAnimationListener(new 4(container, viewToAnimate2, animationInfo2));
                    viewToAnimate2.startAnimation(endViewTransitionAnimation);
                }
                CancellationSignal signal2 = animationInfo2.getSignal();
                signal2.setOnCancelListener(new 5(viewToAnimate2, container, animationInfo2));
            }
        }
    }

    class 2 extends AnimatorListenerAdapter {
        final /* synthetic */ AnimationInfo val$animationInfo;
        final /* synthetic */ ViewGroup val$container;
        final /* synthetic */ boolean val$isHideOperation;
        final /* synthetic */ SpecialEffectsController.Operation val$operation;
        final /* synthetic */ View val$viewToAnimate;

        2(ViewGroup viewGroup, View view, boolean z, SpecialEffectsController.Operation operation, AnimationInfo animationInfo) {
            this.val$container = viewGroup;
            this.val$viewToAnimate = view;
            this.val$isHideOperation = z;
            this.val$operation = operation;
            this.val$animationInfo = animationInfo;
        }

        public void onAnimationEnd(Animator anim) {
            this.val$container.endViewTransition(this.val$viewToAnimate);
            if (this.val$isHideOperation) {
                this.val$operation.getFinalState().applyState(this.val$viewToAnimate);
            }
            this.val$animationInfo.completeSpecialEffect();
        }
    }

    class 3 implements CancellationSignal.OnCancelListener {
        final /* synthetic */ Animator val$animator;

        3(Animator animator) {
            this.val$animator = animator;
        }

        public void onCancel() {
            this.val$animator.end();
        }
    }

    class 4 implements Animation.AnimationListener {
        final /* synthetic */ AnimationInfo val$animationInfo;
        final /* synthetic */ ViewGroup val$container;
        final /* synthetic */ View val$viewToAnimate;

        4(ViewGroup viewGroup, View view, AnimationInfo animationInfo) {
            this.val$container = viewGroup;
            this.val$viewToAnimate = view;
            this.val$animationInfo = animationInfo;
        }

        public void onAnimationStart(Animation animation) {
        }

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                4.this.val$container.endViewTransition(4.this.val$viewToAnimate);
                4.this.val$animationInfo.completeSpecialEffect();
            }
        }

        public void onAnimationEnd(Animation animation) {
            this.val$container.post(new 1());
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class 5 implements CancellationSignal.OnCancelListener {
        final /* synthetic */ AnimationInfo val$animationInfo;
        final /* synthetic */ ViewGroup val$container;
        final /* synthetic */ View val$viewToAnimate;

        5(View view, ViewGroup viewGroup, AnimationInfo animationInfo) {
            this.val$viewToAnimate = view;
            this.val$container = viewGroup;
            this.val$animationInfo = animationInfo;
        }

        public void onCancel() {
            this.val$viewToAnimate.clearAnimation();
            this.val$container.endViewTransition(this.val$viewToAnimate);
            this.val$animationInfo.completeSpecialEffect();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:183:0x0521 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0564  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Map startTransitions(java.util.List r39, java.util.List r40, boolean r41, androidx.fragment.app.SpecialEffectsController.Operation r42, androidx.fragment.app.SpecialEffectsController.Operation r43) {
        /*
            Method dump skipped, instructions count: 1467
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.startTransitions(java.util.List, java.util.List, boolean, androidx.fragment.app.SpecialEffectsController$Operation, androidx.fragment.app.SpecialEffectsController$Operation):java.util.Map");
    }

    class 6 implements Runnable {
        final /* synthetic */ SpecialEffectsController.Operation val$firstOut;
        final /* synthetic */ boolean val$isPop;
        final /* synthetic */ SpecialEffectsController.Operation val$lastIn;
        final /* synthetic */ ArrayMap val$lastInViews;

        6(SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2, boolean z, ArrayMap arrayMap) {
            this.val$lastIn = operation;
            this.val$firstOut = operation2;
            this.val$isPop = z;
            this.val$lastInViews = arrayMap;
        }

        public void run() {
            FragmentTransition.callSharedElementStartEnd(this.val$lastIn.getFragment(), this.val$firstOut.getFragment(), this.val$isPop, this.val$lastInViews, false);
        }
    }

    class 7 implements Runnable {
        final /* synthetic */ FragmentTransitionImpl val$impl;
        final /* synthetic */ Rect val$lastInEpicenterRect;
        final /* synthetic */ View val$lastInEpicenterView;

        7(FragmentTransitionImpl fragmentTransitionImpl, View view, Rect rect) {
            this.val$impl = fragmentTransitionImpl;
            this.val$lastInEpicenterView = view;
            this.val$lastInEpicenterRect = rect;
        }

        public void run() {
            this.val$impl.getBoundsOnScreen(this.val$lastInEpicenterView, this.val$lastInEpicenterRect);
        }
    }

    class 8 implements Runnable {
        final /* synthetic */ ArrayList val$transitioningViews;

        8(ArrayList arrayList) {
            this.val$transitioningViews = arrayList;
        }

        public void run() {
            FragmentTransition.setViewVisibility(this.val$transitioningViews, 4);
        }
    }

    class 9 implements Runnable {
        final /* synthetic */ TransitionInfo val$transitionInfo;

        9(TransitionInfo transitionInfo) {
            this.val$transitionInfo = transitionInfo;
        }

        public void run() {
            this.val$transitionInfo.completeSpecialEffect();
        }
    }

    void retainMatchingViews(ArrayMap arrayMap, Collection collection) {
        Iterator<Map.Entry<String, View>> iterator = arrayMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, View> entry = (Map.Entry) iterator.next();
            if (!collection.contains(ViewCompat.getTransitionName((View) entry.getValue()))) {
                iterator.remove();
            }
        }
    }

    void captureTransitioningViews(ArrayList arrayList, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                if (!arrayList.contains(view)) {
                    arrayList.add(viewGroup);
                    return;
                }
                return;
            }
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    captureTransitioningViews(arrayList, child);
                }
            }
            return;
        }
        if (!arrayList.contains(view)) {
            arrayList.add(view);
        }
    }

    void findNamedViews(Map map, View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    findNamedViews(map, child);
                }
            }
        }
    }

    void applyContainerChanges(SpecialEffectsController.Operation operation) {
        View view = operation.getFragment().mView;
        operation.getFinalState().applyState(view);
    }

    private static class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation mOperation;
        private final CancellationSignal mSignal;

        SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal signal) {
            this.mOperation = operation;
            this.mSignal = signal;
        }

        SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        CancellationSignal getSignal() {
            return this.mSignal;
        }

        boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return currentState == finalState || !(currentState == SpecialEffectsController.Operation.State.VISIBLE || finalState == SpecialEffectsController.Operation.State.VISIBLE);
        }

        void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }
    }

    private static class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop) {
            super(operation, signal);
            this.mLoadedAnim = false;
            this.mIsPop = isPop;
        }

        FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator loadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = loadAnimation;
            this.mLoadedAnim = true;
            return loadAnimation;
        }
    }

    private static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;
        private final Object mSharedElementTransition;
        private final Object mTransition;

        TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop, boolean providesSharedElementTransition) {
            Object exitTransition;
            Object enterTransition;
            boolean allowEnterTransitionOverlap;
            super(operation, signal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                if (isPop) {
                    enterTransition = operation.getFragment().getReenterTransition();
                } else {
                    enterTransition = operation.getFragment().getEnterTransition();
                }
                this.mTransition = enterTransition;
                if (isPop) {
                    allowEnterTransitionOverlap = operation.getFragment().getAllowReturnTransitionOverlap();
                } else {
                    allowEnterTransitionOverlap = operation.getFragment().getAllowEnterTransitionOverlap();
                }
                this.mOverlapAllowed = allowEnterTransitionOverlap;
            } else {
                if (isPop) {
                    exitTransition = operation.getFragment().getReturnTransition();
                } else {
                    exitTransition = operation.getFragment().getExitTransition();
                }
                this.mTransition = exitTransition;
                this.mOverlapAllowed = true;
            }
            if (providesSharedElementTransition) {
                if (isPop) {
                    this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
                    return;
                } else {
                    this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
                    return;
                }
            }
            this.mSharedElementTransition = null;
        }

        Object getTransition() {
            return this.mTransition;
        }

        boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl transitionImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl sharedElementTransitionImpl = getHandlingImpl(this.mSharedElementTransition);
            if (transitionImpl == null || sharedElementTransitionImpl == null || transitionImpl == sharedElementTransitionImpl) {
                return transitionImpl != null ? transitionImpl : sharedElementTransitionImpl;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        private FragmentTransitionImpl getHandlingImpl(Object transition) {
            if (transition == null) {
                return null;
            }
            if (FragmentTransition.PLATFORM_IMPL != null && FragmentTransition.PLATFORM_IMPL.canHandle(transition)) {
                return FragmentTransition.PLATFORM_IMPL;
            }
            if (FragmentTransition.SUPPORT_IMPL != null && FragmentTransition.SUPPORT_IMPL.canHandle(transition)) {
                return FragmentTransition.SUPPORT_IMPL;
            }
            throw new IllegalArgumentException("Transition " + transition + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
