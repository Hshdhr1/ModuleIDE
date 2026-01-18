package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
class FragmentTransitionCompat21 extends FragmentTransitionImpl {
    FragmentTransitionCompat21() {
    }

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

        public Rect onGetEpicenter(Transition transition) {
            return this.val$epicenter;
        }
    }

    public void addTargets(Object transitionObj, ArrayList arrayList) {
        TransitionSet transitionSet = (Transition) transitionObj;
        if (transitionSet == null) {
            return;
        }
        if (transitionSet instanceof TransitionSet) {
            TransitionSet set = transitionSet;
            int numTransitions = set.getTransitionCount();
            for (int i = 0; i < numTransitions; i++) {
                Transition child = set.getTransitionAt(i);
                addTargets(child, arrayList);
            }
            return;
        }
        if (!hasSimpleTarget(transitionSet)) {
            List<View> targets = transitionSet.getTargets();
            if (isNullOrEmpty(targets)) {
                int numViews = arrayList.size();
                for (int i2 = 0; i2 < numViews; i2++) {
                    transitionSet.addTarget((View) arrayList.get(i2));
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

        public void onTransitionStart(Transition transition) {
            transition.removeListener(this);
            transition.addListener(this);
        }

        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
            this.val$fragmentView.setVisibility(8);
            int numViews = this.val$exitingViews.size();
            for (int i = 0; i < numViews; i++) {
                ((View) this.val$exitingViews.get(i)).setVisibility(0);
            }
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }

    public void scheduleHideFragmentView(Object exitTransitionObj, View fragmentView, ArrayList arrayList) {
        Transition exitTransition = (Transition) exitTransitionObj;
        exitTransition.addListener(new 2(fragmentView, arrayList));
    }

    public Object mergeTransitionsInSequence(Object exitTransitionObj, Object enterTransitionObj, Object sharedElementTransitionObj) {
        TransitionSet transitionSet = null;
        TransitionSet transitionSet2 = (Transition) exitTransitionObj;
        TransitionSet transitionSet3 = (Transition) enterTransitionObj;
        Transition sharedElementTransition = (Transition) sharedElementTransitionObj;
        if (transitionSet2 != null && transitionSet3 != null) {
            transitionSet = new TransitionSet().addTransition(transitionSet2).addTransition(transitionSet3).setOrdering(1);
        } else if (transitionSet2 != null) {
            transitionSet = transitionSet2;
        } else if (transitionSet3 != null) {
            transitionSet = transitionSet3;
        }
        if (sharedElementTransition != null) {
            TransitionSet together = new TransitionSet();
            if (transitionSet != null) {
                together.addTransition(transitionSet);
            }
            together.addTransition(sharedElementTransition);
            return together;
        }
        return transitionSet;
    }

    public void beginDelayedTransition(ViewGroup sceneRoot, Object transition) {
        TransitionManager.beginDelayedTransition(sceneRoot, (Transition) transition);
    }

    class 3 implements Transition.TransitionListener {
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

        public void onTransitionStart(Transition transition) {
            Object obj = this.val$enterTransition;
            if (obj != null) {
                FragmentTransitionCompat21.this.replaceTargets(obj, this.val$enteringViews, null);
            }
            Object obj2 = this.val$exitTransition;
            if (obj2 != null) {
                FragmentTransitionCompat21.this.replaceTargets(obj2, this.val$exitingViews, null);
            }
            Object obj3 = this.val$sharedElementTransition;
            if (obj3 != null) {
                FragmentTransitionCompat21.this.replaceTargets(obj3, this.val$sharedElementsIn, null);
            }
        }

        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }

    public void scheduleRemoveTargets(Object overallTransitionObj, Object enterTransition, ArrayList arrayList, Object exitTransition, ArrayList arrayList2, Object sharedElementTransition, ArrayList arrayList3) {
        Transition overallTransition = (Transition) overallTransitionObj;
        overallTransition.addListener(new 3(enterTransition, arrayList, exitTransition, arrayList2, sharedElementTransition, arrayList3));
    }

    class 4 implements Transition.TransitionListener {
        final /* synthetic */ Runnable val$transitionCompleteRunnable;

        4(Runnable runnable) {
            this.val$transitionCompleteRunnable = runnable;
        }

        public void onTransitionStart(Transition transition) {
        }

        public void onTransitionEnd(Transition transition) {
            this.val$transitionCompleteRunnable.run();
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }

    public void setListenerForTransitionEnd(Fragment outFragment, Object transition, CancellationSignal signal, Runnable transitionCompleteRunnable) {
        ((Transition) transition).addListener(new 4(transitionCompleteRunnable));
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
        List<View> targets;
        TransitionSet transitionSet = (Transition) transitionObj;
        if (transitionSet instanceof TransitionSet) {
            TransitionSet set = transitionSet;
            int numTransitions = set.getTransitionCount();
            for (int i = 0; i < numTransitions; i++) {
                Transition child = set.getTransitionAt(i);
                replaceTargets(child, arrayList, arrayList2);
            }
            return;
        }
        if (!hasSimpleTarget(transitionSet) && (targets = transitionSet.getTargets()) != null && targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
            int targetCount = arrayList2 == null ? 0 : arrayList2.size();
            for (int i2 = 0; i2 < targetCount; i2++) {
                transitionSet.addTarget((View) arrayList2.get(i2));
            }
            int i3 = arrayList.size();
            for (int i4 = i3 - 1; i4 >= 0; i4--) {
                transitionSet.removeTarget((View) arrayList.get(i4));
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
            transition.setEpicenterCallback(new 5(epicenter));
        }
    }

    class 5 extends Transition.EpicenterCallback {
        final /* synthetic */ Rect val$epicenter;

        5(Rect rect) {
            this.val$epicenter = rect;
        }

        public Rect onGetEpicenter(Transition transition) {
            Rect rect = this.val$epicenter;
            if (rect == null || rect.isEmpty()) {
                return null;
            }
            return this.val$epicenter;
        }
    }
}
