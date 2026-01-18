package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    static final FragmentTransitionImpl PLATFORM_IMPL;
    static final FragmentTransitionImpl SUPPORT_IMPL;

    interface Callback {
        void onComplete(Fragment fragment, CancellationSignal cancellationSignal);

        void onStart(Fragment fragment, CancellationSignal cancellationSignal);
    }

    static {
        FragmentTransitionCompat21 fragmentTransitionCompat21;
        if (Build.VERSION.SDK_INT >= 21) {
            fragmentTransitionCompat21 = new FragmentTransitionCompat21();
        } else {
            fragmentTransitionCompat21 = null;
        }
        PLATFORM_IMPL = fragmentTransitionCompat21;
        SUPPORT_IMPL = resolveSupportImpl();
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            Class<FragmentTransitionImpl> impl = Class.forName("androidx.transition.FragmentTransitionSupport");
            return (FragmentTransitionImpl) impl.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static void startTransitions(Context context, FragmentContainer fragmentContainer, ArrayList arrayList, ArrayList arrayList2, int startIndex, int endIndex, boolean isReordered, Callback callback) {
        ViewGroup container;
        SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
        for (int i = startIndex; i < endIndex; i++) {
            BackStackRecord record = (BackStackRecord) arrayList.get(i);
            boolean isPop = ((Boolean) arrayList2.get(i)).booleanValue();
            if (isPop) {
                calculatePopFragments(record, transitioningFragments, isReordered);
            } else {
                calculateFragments(record, transitioningFragments, isReordered);
            }
        }
        int i2 = transitioningFragments.size();
        if (i2 != 0) {
            View nonExistentView = new View(context);
            int numContainers = transitioningFragments.size();
            for (int i3 = 0; i3 < numContainers; i3++) {
                int containerId = transitioningFragments.keyAt(i3);
                ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, arrayList, arrayList2, startIndex, endIndex);
                FragmentContainerTransition containerTransition = (FragmentContainerTransition) transitioningFragments.valueAt(i3);
                if (fragmentContainer.onHasView() && (container = fragmentContainer.onFindViewById(containerId)) != null) {
                    if (isReordered) {
                        configureTransitionsReordered(container, containerTransition, nonExistentView, nameOverrides, callback);
                    } else {
                        configureTransitionsOrdered(container, containerTransition, nonExistentView, nameOverrides, callback);
                    }
                }
            }
        }
    }

    private static ArrayMap calculateNameOverrides(int containerId, ArrayList arrayList, ArrayList arrayList2, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = (BackStackRecord) arrayList.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = ((Boolean) arrayList2.get(recordNum)).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = (String) sources.get(i);
                        String targetName = (String) targets.get(i);
                        String previousTarget = (String) nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    private static void configureTransitionsReordered(ViewGroup container, FragmentContainerTransition fragments, View nonExistentView, ArrayMap arrayMap, Callback callback) {
        Object exitTransition;
        ArrayList<View> sharedElementsIn;
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        FragmentTransitionImpl impl = chooseImpl(outFragment, inFragment);
        if (impl == null) {
            return;
        }
        boolean inIsPop = fragments.lastInIsPop;
        boolean outIsPop = fragments.firstOutIsPop;
        ArrayList<View> sharedElementsIn2 = new ArrayList<>();
        ArrayList<View> sharedElementsOut = new ArrayList<>();
        Object enterTransition = getEnterTransition(impl, inFragment, inIsPop);
        Object exitTransition2 = getExitTransition(impl, outFragment, outIsPop);
        Object sharedElementTransition = configureSharedElementsReordered(impl, container, nonExistentView, arrayMap, fragments, sharedElementsOut, sharedElementsIn2, enterTransition, exitTransition2);
        if (enterTransition == null && sharedElementTransition == null) {
            exitTransition = exitTransition2;
            if (exitTransition == null) {
                return;
            }
        } else {
            exitTransition = exitTransition2;
        }
        ArrayList<View> exitingViews = configureEnteringExitingViews(impl, exitTransition, outFragment, sharedElementsOut, nonExistentView);
        ArrayList<View> enteringViews = configureEnteringExitingViews(impl, enterTransition, inFragment, sharedElementsIn2, nonExistentView);
        setViewVisibility(enteringViews, 4);
        Object transition = mergeTransitions(impl, enterTransition, exitTransition, sharedElementTransition, inFragment, inIsPop);
        if (outFragment == null || exitingViews == null) {
            sharedElementsIn = sharedElementsIn2;
        } else if (exitingViews.size() > 0 || sharedElementsOut.size() > 0) {
            CancellationSignal signal = new CancellationSignal();
            sharedElementsIn = sharedElementsIn2;
            callback.onStart(outFragment, signal);
            impl.setListenerForTransitionEnd(outFragment, transition, signal, new 1(callback, outFragment, signal));
        } else {
            sharedElementsIn = sharedElementsIn2;
        }
        if (transition != null) {
            replaceHide(impl, exitTransition, outFragment, exitingViews);
            ArrayList<String> inNames = impl.prepareSetNameOverridesReordered(sharedElementsIn);
            ArrayList<View> sharedElementsIn3 = sharedElementsIn;
            impl.scheduleRemoveTargets(transition, enterTransition, enteringViews, exitTransition, exitingViews, sharedElementTransition, sharedElementsIn3);
            impl.beginDelayedTransition(container, transition);
            impl.setNameOverridesReordered(container, sharedElementsOut, sharedElementsIn3, inNames, arrayMap);
            setViewVisibility(enteringViews, 0);
            impl.swapSharedElementTargets(sharedElementTransition, sharedElementsOut, sharedElementsIn3);
        }
    }

    class 1 implements Runnable {
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ Fragment val$outFragment;
        final /* synthetic */ CancellationSignal val$signal;

        1(Callback callback, Fragment fragment, CancellationSignal cancellationSignal) {
            this.val$callback = callback;
            this.val$outFragment = fragment;
            this.val$signal = cancellationSignal;
        }

        public void run() {
            this.val$callback.onComplete(this.val$outFragment, this.val$signal);
        }
    }

    private static void replaceHide(FragmentTransitionImpl impl, Object exitTransition, Fragment exitingFragment, ArrayList arrayList) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            impl.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), arrayList);
            ViewGroup container = exitingFragment.mContainer;
            OneShotPreDrawListener.add(container, new 2(arrayList));
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ ArrayList val$exitingViews;

        2(ArrayList arrayList) {
            this.val$exitingViews = arrayList;
        }

        public void run() {
            FragmentTransition.setViewVisibility(this.val$exitingViews, 4);
        }
    }

    private static void configureTransitionsOrdered(ViewGroup container, FragmentContainerTransition fragments, View nonExistentView, ArrayMap arrayMap, Callback callback) {
        Object exitTransition;
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        FragmentTransitionImpl impl = chooseImpl(outFragment, inFragment);
        if (impl == null) {
            return;
        }
        boolean inIsPop = fragments.lastInIsPop;
        boolean outIsPop = fragments.firstOutIsPop;
        Object enterTransition = getEnterTransition(impl, inFragment, inIsPop);
        Object exitTransition2 = getExitTransition(impl, outFragment, outIsPop);
        ArrayList<View> sharedElementsOut = new ArrayList<>();
        ArrayList<View> sharedElementsIn = new ArrayList<>();
        Object sharedElementTransition = configureSharedElementsOrdered(impl, container, nonExistentView, arrayMap, fragments, sharedElementsOut, sharedElementsIn, enterTransition, exitTransition2);
        if (enterTransition == null && sharedElementTransition == null) {
            exitTransition = exitTransition2;
            if (exitTransition == null) {
                return;
            }
        } else {
            exitTransition = exitTransition2;
        }
        ArrayList<View> exitingViews = configureEnteringExitingViews(impl, exitTransition, outFragment, sharedElementsOut, nonExistentView);
        Object exitTransition3 = (exitingViews == null || exitingViews.isEmpty()) ? null : exitTransition;
        impl.addTarget(enterTransition, nonExistentView);
        Object transition = mergeTransitions(impl, enterTransition, exitTransition3, sharedElementTransition, inFragment, fragments.lastInIsPop);
        if (outFragment != null && exitingViews != null && (exitingViews.size() > 0 || sharedElementsOut.size() > 0)) {
            CancellationSignal signal = new CancellationSignal();
            callback.onStart(outFragment, signal);
            impl.setListenerForTransitionEnd(outFragment, transition, signal, new 3(callback, outFragment, signal));
        }
        if (transition != null) {
            ArrayList<View> enteringViews = new ArrayList<>();
            impl.scheduleRemoveTargets(transition, enterTransition, enteringViews, exitTransition3, exitingViews, sharedElementTransition, sharedElementsIn);
            scheduleTargetChange(impl, container, inFragment, nonExistentView, sharedElementsIn, enterTransition, enteringViews, exitTransition3, exitingViews);
            impl.setNameOverridesOrdered(container, sharedElementsIn, arrayMap);
            impl.beginDelayedTransition(container, transition);
            impl.scheduleNameReset(container, sharedElementsIn, arrayMap);
        }
    }

    class 3 implements Runnable {
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ Fragment val$outFragment;
        final /* synthetic */ CancellationSignal val$signal;

        3(Callback callback, Fragment fragment, CancellationSignal cancellationSignal) {
            this.val$callback = callback;
            this.val$outFragment = fragment;
            this.val$signal = cancellationSignal;
        }

        public void run() {
            this.val$callback.onComplete(this.val$outFragment, this.val$signal);
        }
    }

    class 4 implements Runnable {
        final /* synthetic */ Object val$enterTransition;
        final /* synthetic */ ArrayList val$enteringViews;
        final /* synthetic */ Object val$exitTransition;
        final /* synthetic */ ArrayList val$exitingViews;
        final /* synthetic */ FragmentTransitionImpl val$impl;
        final /* synthetic */ Fragment val$inFragment;
        final /* synthetic */ View val$nonExistentView;
        final /* synthetic */ ArrayList val$sharedElementsIn;

        4(Object obj, FragmentTransitionImpl fragmentTransitionImpl, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object obj2) {
            this.val$enterTransition = obj;
            this.val$impl = fragmentTransitionImpl;
            this.val$nonExistentView = view;
            this.val$inFragment = fragment;
            this.val$sharedElementsIn = arrayList;
            this.val$enteringViews = arrayList2;
            this.val$exitingViews = arrayList3;
            this.val$exitTransition = obj2;
        }

        public void run() {
            Object obj = this.val$enterTransition;
            if (obj != null) {
                this.val$impl.removeTarget(obj, this.val$nonExistentView);
                ArrayList<View> views = FragmentTransition.configureEnteringExitingViews(this.val$impl, this.val$enterTransition, this.val$inFragment, this.val$sharedElementsIn, this.val$nonExistentView);
                this.val$enteringViews.addAll(views);
            }
            ArrayList<View> views2 = this.val$exitingViews;
            if (views2 != null) {
                if (this.val$exitTransition != null) {
                    ArrayList<View> tempExiting = new ArrayList<>();
                    tempExiting.add(this.val$nonExistentView);
                    this.val$impl.replaceTargets(this.val$exitTransition, this.val$exitingViews, tempExiting);
                }
                this.val$exitingViews.clear();
                this.val$exitingViews.add(this.val$nonExistentView);
            }
        }
    }

    private static void scheduleTargetChange(FragmentTransitionImpl impl, ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList arrayList, Object enterTransition, ArrayList arrayList2, Object exitTransition, ArrayList arrayList3) {
        OneShotPreDrawListener.add(sceneRoot, new 4(enterTransition, impl, nonExistentView, inFragment, arrayList, arrayList2, arrayList3, exitTransition));
    }

    private static FragmentTransitionImpl chooseImpl(Fragment outFragment, Fragment inFragment) {
        ArrayList<Object> transitions = new ArrayList<>();
        if (outFragment != null) {
            Object exitTransition = outFragment.getExitTransition();
            if (exitTransition != null) {
                transitions.add(exitTransition);
            }
            Object returnTransition = outFragment.getReturnTransition();
            if (returnTransition != null) {
                transitions.add(returnTransition);
            }
            Object sharedReturnTransition = outFragment.getSharedElementReturnTransition();
            if (sharedReturnTransition != null) {
                transitions.add(sharedReturnTransition);
            }
        }
        if (inFragment != null) {
            Object enterTransition = inFragment.getEnterTransition();
            if (enterTransition != null) {
                transitions.add(enterTransition);
            }
            Object reenterTransition = inFragment.getReenterTransition();
            if (reenterTransition != null) {
                transitions.add(reenterTransition);
            }
            Object sharedEnterTransition = inFragment.getSharedElementEnterTransition();
            if (sharedEnterTransition != null) {
                transitions.add(sharedEnterTransition);
            }
        }
        if (transitions.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
        if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, transitions)) {
            return fragmentTransitionImpl;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = SUPPORT_IMPL;
        if (fragmentTransitionImpl2 != null && canHandleAll(fragmentTransitionImpl2, transitions)) {
            return fragmentTransitionImpl2;
        }
        if (fragmentTransitionImpl == null && fragmentTransitionImpl2 == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean canHandleAll(FragmentTransitionImpl impl, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!impl.canHandle(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl impl, Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object sharedElementEnterTransition;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            sharedElementEnterTransition = outFragment.getSharedElementReturnTransition();
        } else {
            sharedElementEnterTransition = inFragment.getSharedElementEnterTransition();
        }
        Object transition = impl.cloneTransition(sharedElementEnterTransition);
        return impl.wrapTransitionInSet(transition);
    }

    private static Object getEnterTransition(FragmentTransitionImpl impl, Fragment inFragment, boolean isPop) {
        Object enterTransition;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            enterTransition = inFragment.getReenterTransition();
        } else {
            enterTransition = inFragment.getEnterTransition();
        }
        return impl.cloneTransition(enterTransition);
    }

    private static Object getExitTransition(FragmentTransitionImpl impl, Fragment outFragment, boolean isPop) {
        Object exitTransition;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            exitTransition = outFragment.getReturnTransition();
        } else {
            exitTransition = outFragment.getExitTransition();
        }
        return impl.cloneTransition(exitTransition);
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap arrayMap, FragmentContainerTransition fragments, ArrayList arrayList, ArrayList arrayList2, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Object sharedElementTransition3;
        ArrayMap<String, View> inSharedElements;
        Rect epicenter;
        View epicenterView;
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        if (inFragment != null) {
            inFragment.requireView().setVisibility(0);
        }
        if (inFragment != null && outFragment != null) {
            boolean inIsPop = fragments.lastInIsPop;
            if (arrayMap.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(impl, inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(impl, arrayMap, sharedElementTransition, fragments);
            ArrayMap<String, View> inSharedElements2 = captureInSharedElements(impl, arrayMap, sharedElementTransition, fragments);
            if (arrayMap.isEmpty()) {
                if (outSharedElements != null) {
                    outSharedElements.clear();
                }
                if (inSharedElements2 != null) {
                    inSharedElements2.clear();
                }
                sharedElementTransition2 = null;
            } else {
                addSharedElementsWithMatchingNames(arrayList, outSharedElements, arrayMap.keySet());
                addSharedElementsWithMatchingNames(arrayList2, inSharedElements2, arrayMap.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (enterTransition == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                arrayList2.add(nonExistentView);
                impl.setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                boolean outIsPop = fragments.firstOutIsPop;
                BackStackRecord outTransaction = fragments.firstOutTransaction;
                sharedElementTransition3 = sharedElementTransition2;
                inSharedElements = inSharedElements2;
                setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, outIsPop, outTransaction);
                Rect epicenter2 = new Rect();
                View epicenterView2 = getInEpicenterView(inSharedElements, fragments, enterTransition, inIsPop);
                if (epicenterView2 != null) {
                    impl.setEpicenter(enterTransition, epicenter2);
                }
                epicenter = epicenter2;
                epicenterView = epicenterView2;
            } else {
                sharedElementTransition3 = sharedElementTransition2;
                inSharedElements = inSharedElements2;
                epicenter = null;
                epicenterView = null;
            }
            OneShotPreDrawListener.add(sceneRoot, new 5(inFragment, outFragment, inIsPop, inSharedElements, epicenterView, impl, epicenter));
            return sharedElementTransition3;
        }
        return null;
    }

    class 5 implements Runnable {
        final /* synthetic */ Rect val$epicenter;
        final /* synthetic */ View val$epicenterView;
        final /* synthetic */ FragmentTransitionImpl val$impl;
        final /* synthetic */ Fragment val$inFragment;
        final /* synthetic */ boolean val$inIsPop;
        final /* synthetic */ ArrayMap val$inSharedElements;
        final /* synthetic */ Fragment val$outFragment;

        5(Fragment fragment, Fragment fragment2, boolean z, ArrayMap arrayMap, View view, FragmentTransitionImpl fragmentTransitionImpl, Rect rect) {
            this.val$inFragment = fragment;
            this.val$outFragment = fragment2;
            this.val$inIsPop = z;
            this.val$inSharedElements = arrayMap;
            this.val$epicenterView = view;
            this.val$impl = fragmentTransitionImpl;
            this.val$epicenter = rect;
        }

        public void run() {
            FragmentTransition.callSharedElementStartEnd(this.val$inFragment, this.val$outFragment, this.val$inIsPop, this.val$inSharedElements, false);
            View view = this.val$epicenterView;
            if (view != null) {
                this.val$impl.getBoundsOnScreen(view, this.val$epicenter);
            }
        }
    }

    private static void addSharedElementsWithMatchingNames(ArrayList arrayList, ArrayMap arrayMap, Collection collection) {
        for (int i = arrayMap.size() - 1; i >= 0; i--) {
            View view = (View) arrayMap.valueAt(i);
            if (collection.contains(ViewCompat.getTransitionName(view))) {
                arrayList.add(view);
            }
        }
    }

    private static Object configureSharedElementsOrdered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap arrayMap, FragmentContainerTransition fragments, ArrayList arrayList, ArrayList arrayList2, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Rect inEpicenter;
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        if (inFragment != null && outFragment != null) {
            boolean inIsPop = fragments.lastInIsPop;
            if (arrayMap.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(impl, inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(impl, arrayMap, sharedElementTransition, fragments);
            if (arrayMap.isEmpty()) {
                sharedElementTransition2 = null;
            } else {
                arrayList.addAll(outSharedElements.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (enterTransition == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                Rect inEpicenter2 = new Rect();
                impl.setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                boolean outIsPop = fragments.firstOutIsPop;
                BackStackRecord outTransaction = fragments.firstOutTransaction;
                setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, outIsPop, outTransaction);
                if (enterTransition != null) {
                    impl.setEpicenter(enterTransition, inEpicenter2);
                }
                inEpicenter = inEpicenter2;
            } else {
                inEpicenter = null;
            }
            Object finalSharedElementTransition = sharedElementTransition2;
            Object sharedElementTransition3 = sharedElementTransition2;
            OneShotPreDrawListener.add(sceneRoot, new 6(impl, arrayMap, finalSharedElementTransition, fragments, arrayList2, nonExistentView, inFragment, outFragment, inIsPop, arrayList, enterTransition, inEpicenter));
            return sharedElementTransition3;
        }
        return null;
    }

    class 6 implements Runnable {
        final /* synthetic */ Object val$enterTransition;
        final /* synthetic */ Object val$finalSharedElementTransition;
        final /* synthetic */ FragmentContainerTransition val$fragments;
        final /* synthetic */ FragmentTransitionImpl val$impl;
        final /* synthetic */ Rect val$inEpicenter;
        final /* synthetic */ Fragment val$inFragment;
        final /* synthetic */ boolean val$inIsPop;
        final /* synthetic */ ArrayMap val$nameOverrides;
        final /* synthetic */ View val$nonExistentView;
        final /* synthetic */ Fragment val$outFragment;
        final /* synthetic */ ArrayList val$sharedElementsIn;
        final /* synthetic */ ArrayList val$sharedElementsOut;

        6(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean z, ArrayList arrayList2, Object obj2, Rect rect) {
            this.val$impl = fragmentTransitionImpl;
            this.val$nameOverrides = arrayMap;
            this.val$finalSharedElementTransition = obj;
            this.val$fragments = fragmentContainerTransition;
            this.val$sharedElementsIn = arrayList;
            this.val$nonExistentView = view;
            this.val$inFragment = fragment;
            this.val$outFragment = fragment2;
            this.val$inIsPop = z;
            this.val$sharedElementsOut = arrayList2;
            this.val$enterTransition = obj2;
            this.val$inEpicenter = rect;
        }

        public void run() {
            ArrayMap<String, View> inSharedElements = FragmentTransition.captureInSharedElements(this.val$impl, this.val$nameOverrides, this.val$finalSharedElementTransition, this.val$fragments);
            if (inSharedElements != null) {
                this.val$sharedElementsIn.addAll(inSharedElements.values());
                this.val$sharedElementsIn.add(this.val$nonExistentView);
            }
            FragmentTransition.callSharedElementStartEnd(this.val$inFragment, this.val$outFragment, this.val$inIsPop, inSharedElements, false);
            Object obj = this.val$finalSharedElementTransition;
            if (obj != null) {
                this.val$impl.swapSharedElementTargets(obj, this.val$sharedElementsOut, this.val$sharedElementsIn);
                View inEpicenterView = FragmentTransition.getInEpicenterView(inSharedElements, this.val$fragments, this.val$enterTransition, this.val$inIsPop);
                if (inEpicenterView != null) {
                    this.val$impl.getBoundsOnScreen(inEpicenterView, this.val$inEpicenter);
                }
            }
        }
    }

    private static ArrayMap captureOutSharedElements(FragmentTransitionImpl impl, ArrayMap arrayMap, Object sharedElementTransition, FragmentContainerTransition fragments) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        if (arrayMap.isEmpty() || sharedElementTransition == null) {
            arrayMap.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        impl.findNamedViews(outSharedElements, outFragment.requireView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        if (names != null) {
            outSharedElements.retainAll(names);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = (View) outSharedElements.get(name);
                if (view == null) {
                    arrayMap.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    String targetValue = (String) arrayMap.remove(name);
                    arrayMap.put(ViewCompat.getTransitionName(view), targetValue);
                }
            }
        } else {
            arrayMap.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    static ArrayMap captureInSharedElements(FragmentTransitionImpl impl, ArrayMap arrayMap, Object sharedElementTransition, FragmentContainerTransition fragments) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (arrayMap.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        impl.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
            inSharedElements.retainAll(arrayMap.values());
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = (View) inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(arrayMap, name);
                    if (key2 != null) {
                        arrayMap.remove(key2);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view)) && (key = findKeyForValue(arrayMap, name)) != null) {
                    arrayMap.put(key, ViewCompat.getTransitionName(view));
                }
            }
        } else {
            retainValues(arrayMap, inSharedElements);
        }
        return inSharedElements;
    }

    static String findKeyForValue(ArrayMap arrayMap, String value) {
        int numElements = arrayMap.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(arrayMap.valueAt(i))) {
                return (String) arrayMap.keyAt(i);
            }
        }
        return null;
    }

    static View getInEpicenterView(ArrayMap arrayMap, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition != null && arrayMap != null && inTransaction.mSharedElementSourceNames != null && !inTransaction.mSharedElementSourceNames.isEmpty()) {
            String targetName = inIsPop ? (String) inTransaction.mSharedElementSourceNames.get(0) : (String) inTransaction.mSharedElementTargetNames.get(0);
            return (View) arrayMap.get(targetName);
        }
        return null;
    }

    private static void setOutEpicenter(FragmentTransitionImpl impl, Object sharedElementTransition, Object exitTransition, ArrayMap arrayMap, boolean outIsPop, BackStackRecord outTransaction) {
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            String sourceName = outIsPop ? (String) outTransaction.mSharedElementTargetNames.get(0) : (String) outTransaction.mSharedElementSourceNames.get(0);
            View outEpicenterView = (View) arrayMap.get(sourceName);
            impl.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                impl.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    static void retainValues(ArrayMap arrayMap, ArrayMap arrayMap2) {
        for (int i = arrayMap.size() - 1; i >= 0; i--) {
            String targetName = (String) arrayMap.valueAt(i);
            if (!arrayMap2.containsKey(targetName)) {
                arrayMap.removeAt(i);
            }
        }
    }

    static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap arrayMap, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < count; i++) {
                names.add(arrayMap.keyAt(i));
                views.add(arrayMap.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, (List) null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, (List) null);
            }
        }
    }

    static ArrayList configureEnteringExitingViews(FragmentTransitionImpl impl, Object transition, Fragment fragment, ArrayList arrayList, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                impl.captureTransitioningViews(viewList, root);
            }
            if (arrayList != null) {
                viewList.removeAll(arrayList);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                impl.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    static void setViewVisibility(ArrayList arrayList, int visibility) {
        if (arrayList == null) {
            return;
        }
        for (int i = arrayList.size() - 1; i >= 0; i--) {
            View view = (View) arrayList.get(i);
            view.setVisibility(visibility);
        }
    }

    private static Object mergeTransitions(FragmentTransitionImpl impl, Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean overlap = true;
        if (enterTransition != null && exitTransition != null && inFragment != null) {
            overlap = isPop ? inFragment.getAllowReturnTransitionOverlap() : inFragment.getAllowEnterTransitionOverlap();
        }
        if (overlap) {
            Object transition = impl.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
            return transition;
        }
        Object transition2 = impl.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
        return transition2;
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray sparseArray, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            FragmentTransaction.Op op = (FragmentTransaction.Op) transaction.mOps.get(opNum);
            addToFirstInLastOut(transaction, op, sparseArray, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray sparseArray, boolean isReordered) {
        if (!transaction.mManager.getContainer().onHasView()) {
            return;
        }
        int numOps = transaction.mOps.size();
        for (int opNum = numOps - 1; opNum >= 0; opNum--) {
            FragmentTransaction.Op op = (FragmentTransaction.Op) transaction.mOps.get(opNum);
            addToFirstInLastOut(transaction, op, sparseArray, true, isReordered);
        }
    }

    static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }

    private static void addToFirstInLastOut(BackStackRecord transaction, FragmentTransaction.Op op, SparseArray sparseArray, boolean isPop, boolean isReorderedTransaction) {
        int containerId;
        Fragment fragment = op.mFragment;
        if (fragment == null || (containerId = fragment.mContainerId) == 0) {
            return;
        }
        int command = isPop ? INVERSE_OPS[op.mCmd] : op.mCmd;
        boolean setLastIn = false;
        boolean wasRemoved = false;
        boolean setFirstOut = false;
        boolean wasAdded = false;
        switch (command) {
            case 1:
            case 7:
                if (isReorderedTransaction) {
                    setLastIn = fragment.mIsNewlyAdded;
                } else {
                    setLastIn = (fragment.mAdded || fragment.mHidden) ? false : true;
                }
                wasAdded = true;
                break;
            case 3:
            case 6:
                if (isReorderedTransaction) {
                    setFirstOut = !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mPostponedAlpha >= 0.0f;
                } else {
                    setFirstOut = fragment.mAdded && !fragment.mHidden;
                }
                wasRemoved = true;
                break;
            case 4:
                if (isReorderedTransaction) {
                    setFirstOut = fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden;
                } else {
                    setFirstOut = fragment.mAdded && !fragment.mHidden;
                }
                wasRemoved = true;
                break;
            case 5:
                if (isReorderedTransaction) {
                    setLastIn = fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded;
                } else {
                    setLastIn = fragment.mHidden;
                }
                wasAdded = true;
                break;
        }
        FragmentContainerTransition containerTransition = (FragmentContainerTransition) sparseArray.get(containerId);
        if (setLastIn) {
            containerTransition = ensureContainer(containerTransition, sparseArray, containerId);
            containerTransition.lastIn = fragment;
            containerTransition.lastInIsPop = isPop;
            containerTransition.lastInTransaction = transaction;
        }
        if (!isReorderedTransaction && wasAdded) {
            if (containerTransition != null && containerTransition.firstOut == fragment) {
                containerTransition.firstOut = null;
            }
            if (!transaction.mReorderingAllowed) {
                FragmentManager manager = transaction.mManager;
                FragmentStateManager fragmentStateManager = manager.createOrGetFragmentStateManager(fragment);
                manager.getFragmentStore().makeActive(fragmentStateManager);
                manager.moveToState(fragment);
            }
        }
        if (setFirstOut && (containerTransition == null || containerTransition.firstOut == null)) {
            containerTransition = ensureContainer(containerTransition, sparseArray, containerId);
            containerTransition.firstOut = fragment;
            containerTransition.firstOutIsPop = isPop;
            containerTransition.firstOutTransaction = transaction;
        }
        if (!isReorderedTransaction && wasRemoved && containerTransition != null && containerTransition.lastIn == fragment) {
            containerTransition.lastIn = null;
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray sparseArray, int containerId) {
        if (containerTransition == null) {
            FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
            sparseArray.put(containerId, containerTransition2);
            return containerTransition2;
        }
        return containerTransition;
    }

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    private FragmentTransition() {
    }
}
