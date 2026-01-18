package androidx.fragment.app;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
public abstract class FragmentTransitionImpl {
    public abstract void addTarget(Object obj, View view);

    public abstract void addTargets(Object obj, ArrayList arrayList);

    public abstract void beginDelayedTransition(ViewGroup viewGroup, Object obj);

    public abstract boolean canHandle(Object obj);

    public abstract Object cloneTransition(Object obj);

    public abstract Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3);

    public abstract Object mergeTransitionsTogether(Object obj, Object obj2, Object obj3);

    public abstract void removeTarget(Object obj, View view);

    public abstract void replaceTargets(Object obj, ArrayList arrayList, ArrayList arrayList2);

    public abstract void scheduleHideFragmentView(Object obj, View view, ArrayList arrayList);

    public abstract void scheduleRemoveTargets(Object obj, Object obj2, ArrayList arrayList, Object obj3, ArrayList arrayList2, Object obj4, ArrayList arrayList3);

    public abstract void setEpicenter(Object obj, Rect rect);

    public abstract void setEpicenter(Object obj, View view);

    public abstract void setSharedElementTargets(Object obj, View view, ArrayList arrayList);

    public abstract void swapSharedElementTargets(Object obj, ArrayList arrayList, ArrayList arrayList2);

    public abstract Object wrapTransitionInSet(Object obj);

    protected void getBoundsOnScreen(View view, Rect epicenter) {
        if (!ViewCompat.isAttachedToWindow(view)) {
            return;
        }
        RectF rect = new RectF();
        rect.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        view.getMatrix().mapRect(rect);
        rect.offset(view.getLeft(), view.getTop());
        ViewParent parent = view.getParent();
        while (parent instanceof View) {
            View parentView = (View) parent;
            rect.offset(-parentView.getScrollX(), -parentView.getScrollY());
            parentView.getMatrix().mapRect(rect);
            rect.offset(parentView.getLeft(), parentView.getTop());
            parent = parentView.getParent();
        }
        int[] loc = new int[2];
        view.getRootView().getLocationOnScreen(loc);
        rect.offset(loc[0], loc[1]);
        epicenter.set(Math.round(rect.left), Math.round(rect.top), Math.round(rect.right), Math.round(rect.bottom));
    }

    ArrayList prepareSetNameOverridesReordered(ArrayList arrayList) {
        ArrayList<String> names = new ArrayList<>();
        int numSharedElements = arrayList.size();
        for (int i = 0; i < numSharedElements; i++) {
            View view = (View) arrayList.get(i);
            names.add(ViewCompat.getTransitionName(view));
            ViewCompat.setTransitionName(view, (String) null);
        }
        return names;
    }

    void setNameOverridesReordered(View sceneRoot, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Map map) {
        int numSharedElements = arrayList2.size();
        ArrayList<String> outNames = new ArrayList<>();
        for (int i = 0; i < numSharedElements; i++) {
            View view = (View) arrayList.get(i);
            String name = ViewCompat.getTransitionName(view);
            outNames.add(name);
            if (name != null) {
                ViewCompat.setTransitionName(view, (String) null);
                String inName = (String) map.get(name);
                int j = 0;
                while (true) {
                    if (j >= numSharedElements) {
                        break;
                    }
                    if (!inName.equals(arrayList3.get(j))) {
                        j++;
                    } else {
                        ViewCompat.setTransitionName((View) arrayList2.get(j), name);
                        break;
                    }
                }
            }
        }
        OneShotPreDrawListener.add(sceneRoot, new 1(numSharedElements, arrayList2, arrayList3, arrayList, outNames));
    }

    class 1 implements Runnable {
        final /* synthetic */ ArrayList val$inNames;
        final /* synthetic */ int val$numSharedElements;
        final /* synthetic */ ArrayList val$outNames;
        final /* synthetic */ ArrayList val$sharedElementsIn;
        final /* synthetic */ ArrayList val$sharedElementsOut;

        1(int i, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
            this.val$numSharedElements = i;
            this.val$sharedElementsIn = arrayList;
            this.val$inNames = arrayList2;
            this.val$sharedElementsOut = arrayList3;
            this.val$outNames = arrayList4;
        }

        public void run() {
            for (int i = 0; i < this.val$numSharedElements; i++) {
                ViewCompat.setTransitionName((View) this.val$sharedElementsIn.get(i), (String) this.val$inNames.get(i));
                ViewCompat.setTransitionName((View) this.val$sharedElementsOut.get(i), (String) this.val$outNames.get(i));
            }
        }
    }

    void captureTransitioningViews(ArrayList arrayList, View view) {
        if (view.getVisibility() == 0) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                    arrayList.add(viewGroup);
                    return;
                }
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = viewGroup.getChildAt(i);
                    captureTransitioningViews(arrayList, child);
                }
                return;
            }
            arrayList.add(view);
        }
    }

    void findNamedViews(Map map, View view) {
        if (view.getVisibility() == 0) {
            String transitionName = ViewCompat.getTransitionName(view);
            if (transitionName != null) {
                map.put(transitionName, view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = viewGroup.getChildAt(i);
                    findNamedViews(map, child);
                }
            }
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ Map val$nameOverrides;
        final /* synthetic */ ArrayList val$sharedElementsIn;

        2(ArrayList arrayList, Map map) {
            this.val$sharedElementsIn = arrayList;
            this.val$nameOverrides = map;
        }

        public void run() {
            int numSharedElements = this.val$sharedElementsIn.size();
            for (int i = 0; i < numSharedElements; i++) {
                View view = (View) this.val$sharedElementsIn.get(i);
                String name = ViewCompat.getTransitionName(view);
                if (name != null) {
                    String inName = FragmentTransitionImpl.findKeyForValue(this.val$nameOverrides, name);
                    ViewCompat.setTransitionName(view, inName);
                }
            }
        }
    }

    void setNameOverridesOrdered(View sceneRoot, ArrayList arrayList, Map map) {
        OneShotPreDrawListener.add(sceneRoot, new 2(arrayList, map));
    }

    public void setListenerForTransitionEnd(Fragment outFragment, Object transition, CancellationSignal signal, Runnable transitionCompleteRunnable) {
        transitionCompleteRunnable.run();
    }

    class 3 implements Runnable {
        final /* synthetic */ Map val$nameOverrides;
        final /* synthetic */ ArrayList val$sharedElementsIn;

        3(ArrayList arrayList, Map map) {
            this.val$sharedElementsIn = arrayList;
            this.val$nameOverrides = map;
        }

        public void run() {
            int numSharedElements = this.val$sharedElementsIn.size();
            for (int i = 0; i < numSharedElements; i++) {
                View view = (View) this.val$sharedElementsIn.get(i);
                String name = ViewCompat.getTransitionName(view);
                String inName = (String) this.val$nameOverrides.get(name);
                ViewCompat.setTransitionName(view, inName);
            }
        }
    }

    void scheduleNameReset(ViewGroup sceneRoot, ArrayList arrayList, Map map) {
        OneShotPreDrawListener.add(sceneRoot, new 3(arrayList, map));
    }

    protected static void bfsAddViewChildren(List list, View startView) {
        int startIndex = list.size();
        if (containedBeforeIndex(list, startView, startIndex)) {
            return;
        }
        if (ViewCompat.getTransitionName(startView) != null) {
            list.add(startView);
        }
        for (int index = startIndex; index < list.size(); index++) {
            ViewGroup viewGroup = (View) list.get(index);
            if (viewGroup instanceof ViewGroup) {
                ViewGroup viewGroup2 = viewGroup;
                int childCount = viewGroup2.getChildCount();
                for (int childIndex = 0; childIndex < childCount; childIndex++) {
                    View child = viewGroup2.getChildAt(childIndex);
                    if (!containedBeforeIndex(list, child, startIndex) && ViewCompat.getTransitionName(child) != null) {
                        list.add(child);
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(List list, View view, int maxIndex) {
        for (int i = 0; i < maxIndex; i++) {
            if (list.get(i) == view) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    static String findKeyForValue(Map map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }
        return null;
    }
}
