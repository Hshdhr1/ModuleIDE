package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.os.CancellationSignal;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"RestrictedApi"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public class FragmentTransitionSupport extends FragmentTransitionImpl {
    public boolean canHandle(Object transition) {
        return transition instanceof Transition;
    }

    public Object cloneTransition(Object transition) {
        if (transition == null) {
            return null;
        }
        Transition copy = ((Transition) transition).clone();
        return copy;
    }

    public Object wrapTransitionInSet(Object transition) {
        if (transition == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition) transition);
        return transitionSet;
    }

    public void setSharedElementTargets(Object transitionObj, View nonExistentView, ArrayList arrayList) {
        TransitionSet transition = (TransitionSet) transitionObj;
        List<View> views = transition.getTargets();
        views.clear();
        int count = arrayList.size();
        for (int i = 0; i < count; i++) {
            View view = (View) arrayList.get(i);
            bfsAddViewChildren(views, view);
        }
        views.add(nonExistentView);
        arrayList.add(nonExistentView);
        addTargets(transition, arrayList);
    }

    public void setEpicenter(Object transitionObj, View view) {
        if (view != null) {
            Transition transition = (Transition) transitionObj;
            Rect epicenter = new Rect();
            getBoundsOnScreen(view, epicenter);
            transition.setEpicenterCallback(new 1(epicenter));
        }
    }

    class 1 extends Transition.EpicenterCallback {
        final /* synthetic */ Rect val$epicenter;

        1(Rect rect) {
            this.val$epicenter = rect;
        }

        public Rect onGetEpicenter(@NonNull Transition transition) {
            return this.val$epicenter;
        }
    }

    public void addTargets(Object transitionObj, ArrayList arrayList) {
        Transition transition = (Transition) transitionObj;
        if (transition != null) {
            if (transition instanceof TransitionSet) {
                TransitionSet set = (TransitionSet) transition;
                int numTransitions = set.getTransitionCount();
                for (int i = 0; i < numTransitions; i++) {
                    Transition child = set.getTransitionAt(i);
                    addTargets(child, arrayList);
                }
                return;
            }
            if (!hasSimpleTarget(transition)) {
                List<View> targets = transition.getTargets();
                if (isNullOrEmpty(targets)) {
                    int numViews = arrayList.size();
                    for (int i2 = 0; i2 < numViews; i2++) {
                        transition.addTarget((View) arrayList.get(i2));
                    }
                }
            }
        }
    }

    private static boolean hasSimpleTarget(Transition transition) {
        return (isNullOrEmpty(transition.getTargetIds()) && isNullOrEmpty(transition.getTargetNames()) && isNullOrEmpty(transition.getTargetTypes())) ? false : true;
    }

    public Object mergeTransitionsTogether(Object transition1, Object transition2, Object transition3) {
        TransitionSet transitionSet = new TransitionSet();
        if (transition1 != null) {
            transitionSet.addTransition((Transition) transition1);
        }
        if (transition2 != null) {
            transitionSet.addTransition((Transition) transition2);
        }
        if (transition3 != null) {
            transitionSet.addTransition((Transition) transition3);
        }
        return transitionSet;
    }

    class 2 implements Transition.TransitionListener {
        final /* synthetic */ ArrayList val$exitingViews;
        final /* synthetic */ View val$fragmentView;

        2(View view, ArrayList arrayList) {
            this.val$fragmentView = view;
            this.val$exitingViews = arrayList;
        }

        public void onTransitionStart(@NonNull Transition transition) {
            transition.removeListener(this);
            transition.addListener(this);
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
            this.val$fragmentView.setVisibility(8);
            int numViews = this.val$exitingViews.size();
            for (int i = 0; i < numViews; i++) {
                ((View) this.val$exitingViews.get(i)).setVisibility(0);
            }
        }

        public void onTransitionCancel(@NonNull Transition transition) {
        }

        public void onTransitionPause(@NonNull Transition transition) {
        }

        public void onTransitionResume(@NonNull Transition transition) {
        }
    }

    public void scheduleHideFragmentView(Object exitTransitionObj, View fragmentView, ArrayList arrayList) {
        Transition exitTransition = (Transition) exitTransitionObj;
        exitTransition.addListener(new 2(fragmentView, arrayList));
    }

    public Object mergeTransitionsInSequence(Object exitTransitionObj, Object enterTransitionObj, Object sharedElementTransitionObj) {
        Transition staggered = null;
        Transition exitTransition = (Transition) exitTransitionObj;
        Transition enterTransition = (Transition) enterTransitionObj;
        Transition sharedElementTransition = (Transition) sharedElementTransitionObj;
        if (exitTransition != null && enterTransition != null) {
            staggered = new TransitionSet().addTransition(exitTransition).addTransition(enterTransition).setOrdering(1);
        } else if (exitTransition != null) {
            staggered = exitTransition;
        } else if (enterTransition != null) {
            staggered = enterTransition;
        }
        if (sharedElementTransition == null) {
            return staggered;
        }
        TransitionSet together = new TransitionSet();
        if (staggered != null) {
            together.addTransition(staggered);
        }
        together.addTransition(sharedElementTransition);
        return together;
    }

    public void beginDelayedTransition(ViewGroup sceneRoot, Object transition) {
        TransitionManager.beginDelayedTransition(sceneRoot, (Transition) transition);
    }

    class 3 extends TransitionListenerAdapter {
        final /* synthetic */ Object val$enterTransition;
        final /* synthetic */ ArrayList val$enteringViews;
        final /* synthetic */ Object val$exitTransition;
        final /* synthetic */ ArrayList val$exitingViews;
        final /* synthetic */ Object val$sharedElementTransition;
        final /* synthetic */ ArrayList val$sharedElementsIn;

        3(Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
            this.val$enterTransition = obj;
            this.val$enteringViews = arrayList;
            this.val$exitTransition = obj2;
            this.val$exitingViews = arrayList2;
            this.val$sharedElementTransition = obj3;
            this.val$sharedElementsIn = arrayList3;
        }

        public void onTransitionStart(@NonNull Transition transition) {
            if (this.val$enterTransition != null) {
                FragmentTransitionSupport.this.replaceTargets(this.val$enterTransition, this.val$enteringViews, null);
            }
            if (this.val$exitTransition != null) {
                FragmentTransitionSupport.this.replaceTargets(this.val$exitTransition, this.val$exitingViews, null);
            }
            if (this.val$sharedElementTransition != null) {
                FragmentTransitionSupport.this.replaceTargets(this.val$sharedElementTransition, this.val$sharedElementsIn, null);
            }
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
        }
    }

    public void scheduleRemoveTargets(Object overallTransitionObj, Object enterTransition, ArrayList arrayList, Object exitTransition, ArrayList arrayList2, Object sharedElementTransition, ArrayList arrayList3) {
        Transition overallTransition = (Transition) overallTransitionObj;
        overallTransition.addListener(new 3(enterTransition, arrayList, exitTransition, arrayList2, sharedElementTransition, arrayList3));
    }

    class 4 implements CancellationSignal.OnCancelListener {
        final /* synthetic */ Transition val$realTransition;

        4(Transition transition) {
            this.val$realTransition = transition;
        }

        public void onCancel() {
            this.val$realTransition.cancel();
        }
    }

    public void setListenerForTransitionEnd(@NonNull Fragment outFragment, @NonNull Object transition, @NonNull CancellationSignal signal, @NonNull Runnable transitionCompleteRunnable) {
        Transition realTransition = (Transition) transition;
        signal.setOnCancelListener(new 4(realTransition));
        realTransition.addListener(new 5(transitionCompleteRunnable));
    }

    class 5 implements Transition.TransitionListener {
        final /* synthetic */ Runnable val$transitionCompleteRunnable;

        5(Runnable runnable) {
            this.val$transitionCompleteRunnable = runnable;
        }

        public void onTransitionStart(@NonNull Transition transition) {
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            this.val$transitionCompleteRunnable.run();
        }

        public void onTransitionCancel(@NonNull Transition transition) {
        }

        public void onTransitionPause(@NonNull Transition transition) {
        }

        public void onTransitionResume(@NonNull Transition transition) {
        }
    }

    public void swapSharedElementTargets(Object sharedElementTransitionObj, ArrayList arrayList, ArrayList arrayList2) {
        TransitionSet sharedElementTransition = (TransitionSet) sharedElementTransitionObj;
        if (sharedElementTransition != null) {
            sharedElementTransition.getTargets().clear();
            sharedElementTransition.getTargets().addAll(arrayList2);
            replaceTargets(sharedElementTransition, arrayList, arrayList2);
        }
    }

    public void replaceTargets(Object transitionObj, ArrayList arrayList, ArrayList arrayList2) {
        Transition transition = (Transition) transitionObj;
        if (transition instanceof TransitionSet) {
            TransitionSet set = (TransitionSet) transition;
            int numTransitions = set.getTransitionCount();
            for (int i = 0; i < numTransitions; i++) {
                Transition child = set.getTransitionAt(i);
                replaceTargets(child, arrayList, arrayList2);
            }
            return;
        }
        if (!hasSimpleTarget(transition)) {
            List<View> targets = transition.getTargets();
            if (targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
                int targetCount = arrayList2 == null ? 0 : arrayList2.size();
                for (int i2 = 0; i2 < targetCount; i2++) {
                    transition.addTarget((View) arrayList2.get(i2));
                }
                for (int i3 = arrayList.size() - 1; i3 >= 0; i3--) {
                    transition.removeTarget((View) arrayList.get(i3));
                }
            }
        }
    }

    public void addTarget(Object transitionObj, View view) {
        if (transitionObj != null) {
            Transition transition = (Transition) transitionObj;
            transition.addTarget(view);
        }
    }

    public void removeTarget(Object transitionObj, View view) {
        if (transitionObj != null) {
            Transition transition = (Transition) transitionObj;
            transition.removeTarget(view);
        }
    }

    public void setEpicenter(Object transitionObj, Rect epicenter) {
        if (transitionObj != null) {
            Transition transition = (Transition) transitionObj;
            transition.setEpicenterCallback(new 6(epicenter));
        }
    }

    class 6 extends Transition.EpicenterCallback {
        final /* synthetic */ Rect val$epicenter;

        6(Rect rect) {
            this.val$epicenter = rect;
        }

        public Rect onGetEpicenter(@NonNull Transition transition) {
            if (this.val$epicenter == null || this.val$epicenter.isEmpty()) {
                return null;
            }
            return this.val$epicenter;
        }
    }
}
