package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public abstract class Transition implements Cloneable {
    static final boolean DBG = false;
    private static final String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final String MATCH_NAME_STR = "name";
    private ArrayList mEndValuesList;
    private EpicenterCallback mEpicenterCallback;
    private ArrayMap mNameOverrides;
    TransitionPropagation mPropagation;
    private ArrayList mStartValuesList;
    private static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    private static final PathMotion STRAIGHT_PATH_MOTION = new 1();
    private static ThreadLocal sRunningAnimators = new ThreadLocal();
    private String mName = getClass().getName();
    private long mStartDelay = -1;
    long mDuration = -1;
    private TimeInterpolator mInterpolator = null;
    ArrayList mTargetIds = new ArrayList();
    ArrayList mTargets = new ArrayList();
    private ArrayList mTargetNames = null;
    private ArrayList mTargetTypes = null;
    private ArrayList mTargetIdExcludes = null;
    private ArrayList mTargetExcludes = null;
    private ArrayList mTargetTypeExcludes = null;
    private ArrayList mTargetNameExcludes = null;
    private ArrayList mTargetIdChildExcludes = null;
    private ArrayList mTargetChildExcludes = null;
    private ArrayList mTargetTypeChildExcludes = null;
    private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
    TransitionSet mParent = null;
    private int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    boolean mCanRemoveViews = false;
    ArrayList mCurrentAnimators = new ArrayList();
    private int mNumInstances = 0;
    private boolean mPaused = false;
    private boolean mEnded = false;
    private ArrayList mListeners = null;
    private ArrayList mAnimators = new ArrayList();
    private PathMotion mPathMotion = STRAIGHT_PATH_MOTION;

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(@NonNull Transition transition);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface MatchOrder {
    }

    public interface TransitionListener {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    class 1 extends PathMotion {
        1() {
        }

        public Path getPath(float startX, float startY, float endX, float endY) {
            Path path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(endX, endY);
            return path;
        }
    }

    public Transition() {
    }

    @SuppressLint({"RestrictedApi"})
    public Transition(@NonNull Context context, @NonNull AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.TRANSITION);
        XmlResourceParser parser = (XmlResourceParser) attrs;
        long duration = TypedArrayUtils.getNamedInt(a, parser, "duration", 1, -1);
        if (duration >= 0) {
            setDuration(duration);
        }
        long startDelay = TypedArrayUtils.getNamedInt(a, parser, "startDelay", 2, -1);
        if (startDelay > 0) {
            setStartDelay(startDelay);
        }
        int resId = TypedArrayUtils.getNamedResourceId(a, parser, "interpolator", 0, 0);
        if (resId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, resId));
        }
        String matchOrder = TypedArrayUtils.getNamedString(a, parser, "matchOrder", 3);
        if (matchOrder != null) {
            setMatchOrder(parseMatchOrder(matchOrder));
        }
        a.recycle();
    }

    private static int[] parseMatchOrder(String matchOrderString) {
        StringTokenizer st = new StringTokenizer(matchOrderString, ",");
        int[] matches = new int[st.countTokens()];
        int index = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            if ("id".equalsIgnoreCase(token)) {
                matches[index] = 3;
            } else if ("instance".equalsIgnoreCase(token)) {
                matches[index] = 1;
            } else if ("name".equalsIgnoreCase(token)) {
                matches[index] = 2;
            } else if ("itemId".equalsIgnoreCase(token)) {
                matches[index] = 4;
            } else if (token.isEmpty()) {
                int[] smallerMatches = new int[matches.length - 1];
                System.arraycopy(matches, 0, smallerMatches, 0, index);
                matches = smallerMatches;
                index--;
            } else {
                throw new InflateException("Unknown match type in matchOrder: '" + token + "'");
            }
            index++;
        }
        return matches;
    }

    @NonNull
    public Transition setDuration(long duration) {
        this.mDuration = duration;
        return this;
    }

    public long getDuration() {
        return this.mDuration;
    }

    @NonNull
    public Transition setStartDelay(long startDelay) {
        this.mStartDelay = startDelay;
        return this;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator interpolator) {
        this.mInterpolator = interpolator;
        return this;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return null;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return null;
    }

    public void setMatchOrder(int... matches) {
        if (matches == null || matches.length == 0) {
            this.mMatchOrder = DEFAULT_MATCH_ORDER;
            return;
        }
        for (int i = 0; i < matches.length; i++) {
            int match = matches[i];
            if (!isValidMatch(match)) {
                throw new IllegalArgumentException("matches contains invalid value");
            }
            if (alreadyContains(matches, i)) {
                throw new IllegalArgumentException("matches contains a duplicate value");
            }
        }
        this.mMatchOrder = (int[]) matches.clone();
    }

    private static boolean isValidMatch(int match) {
        return match >= 1 && match <= 4;
    }

    private static boolean alreadyContains(int[] array, int searchIndex) {
        int value = array[searchIndex];
        for (int i = 0; i < searchIndex; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    private void matchInstances(ArrayMap arrayMap, ArrayMap arrayMap2) {
        TransitionValues end;
        for (int i = arrayMap.size() - 1; i >= 0; i--) {
            View view = (View) arrayMap.keyAt(i);
            if (view != null && isValidTarget(view) && (end = (TransitionValues) arrayMap2.remove(view)) != null && isValidTarget(end.view)) {
                TransitionValues start = (TransitionValues) arrayMap.removeAt(i);
                this.mStartValuesList.add(start);
                this.mEndValuesList.add(end);
            }
        }
    }

    private void matchItemIds(ArrayMap arrayMap, ArrayMap arrayMap2, LongSparseArray longSparseArray, LongSparseArray longSparseArray2) {
        View endView;
        int numStartIds = longSparseArray.size();
        for (int i = 0; i < numStartIds; i++) {
            View startView = (View) longSparseArray.valueAt(i);
            if (startView != null && isValidTarget(startView) && (endView = (View) longSparseArray2.get(longSparseArray.keyAt(i))) != null && isValidTarget(endView)) {
                TransitionValues startValues = (TransitionValues) arrayMap.get(startView);
                TransitionValues endValues = (TransitionValues) arrayMap2.get(endView);
                if (startValues != null && endValues != null) {
                    this.mStartValuesList.add(startValues);
                    this.mEndValuesList.add(endValues);
                    arrayMap.remove(startView);
                    arrayMap2.remove(endView);
                }
            }
        }
    }

    private void matchIds(ArrayMap arrayMap, ArrayMap arrayMap2, SparseArray sparseArray, SparseArray sparseArray2) {
        View endView;
        int numStartIds = sparseArray.size();
        for (int i = 0; i < numStartIds; i++) {
            View startView = (View) sparseArray.valueAt(i);
            if (startView != null && isValidTarget(startView) && (endView = (View) sparseArray2.get(sparseArray.keyAt(i))) != null && isValidTarget(endView)) {
                TransitionValues startValues = (TransitionValues) arrayMap.get(startView);
                TransitionValues endValues = (TransitionValues) arrayMap2.get(endView);
                if (startValues != null && endValues != null) {
                    this.mStartValuesList.add(startValues);
                    this.mEndValuesList.add(endValues);
                    arrayMap.remove(startView);
                    arrayMap2.remove(endView);
                }
            }
        }
    }

    private void matchNames(ArrayMap arrayMap, ArrayMap arrayMap2, ArrayMap arrayMap3, ArrayMap arrayMap4) {
        View endView;
        int numStartNames = arrayMap3.size();
        for (int i = 0; i < numStartNames; i++) {
            View startView = (View) arrayMap3.valueAt(i);
            if (startView != null && isValidTarget(startView) && (endView = (View) arrayMap4.get(arrayMap3.keyAt(i))) != null && isValidTarget(endView)) {
                TransitionValues startValues = (TransitionValues) arrayMap.get(startView);
                TransitionValues endValues = (TransitionValues) arrayMap2.get(endView);
                if (startValues != null && endValues != null) {
                    this.mStartValuesList.add(startValues);
                    this.mEndValuesList.add(endValues);
                    arrayMap.remove(startView);
                    arrayMap2.remove(endView);
                }
            }
        }
    }

    private void addUnmatched(ArrayMap arrayMap, ArrayMap arrayMap2) {
        for (int i = 0; i < arrayMap.size(); i++) {
            TransitionValues start = (TransitionValues) arrayMap.valueAt(i);
            if (isValidTarget(start.view)) {
                this.mStartValuesList.add(start);
                this.mEndValuesList.add((Object) null);
            }
        }
        for (int i2 = 0; i2 < arrayMap2.size(); i2++) {
            TransitionValues end = (TransitionValues) arrayMap2.valueAt(i2);
            if (isValidTarget(end.view)) {
                this.mEndValuesList.add(end);
                this.mStartValuesList.add((Object) null);
            }
        }
    }

    private void matchStartAndEnd(TransitionValuesMaps startValues, TransitionValuesMaps endValues) {
        ArrayMap<View, TransitionValues> unmatchedStart = new ArrayMap<>(startValues.mViewValues);
        ArrayMap<View, TransitionValues> unmatchedEnd = new ArrayMap<>(endValues.mViewValues);
        for (int i = 0; i < this.mMatchOrder.length; i++) {
            switch (this.mMatchOrder[i]) {
                case 1:
                    matchInstances(unmatchedStart, unmatchedEnd);
                    break;
                case 2:
                    matchNames(unmatchedStart, unmatchedEnd, startValues.mNameValues, endValues.mNameValues);
                    break;
                case 3:
                    matchIds(unmatchedStart, unmatchedEnd, startValues.mIdValues, endValues.mIdValues);
                    break;
                case 4:
                    matchItemIds(unmatchedStart, unmatchedEnd, startValues.mItemIdValues, endValues.mItemIdValues);
                    break;
            }
        }
        addUnmatched(unmatchedStart, unmatchedEnd);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void createAnimators(ViewGroup sceneRoot, TransitionValuesMaps startValues, TransitionValuesMaps endValues, ArrayList arrayList, ArrayList arrayList2) {
        Animator animator;
        View view;
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        long minStartDelay = Long.MAX_VALUE;
        SparseIntArray startDelays = new SparseIntArray();
        int startValuesListCount = arrayList.size();
        for (int i = 0; i < startValuesListCount; i++) {
            TransitionValues start = (TransitionValues) arrayList.get(i);
            TransitionValues end = (TransitionValues) arrayList2.get(i);
            if (start != null && !start.mTargetedTransitions.contains(this)) {
                start = null;
            }
            if (end != null && !end.mTargetedTransitions.contains(this)) {
                end = null;
            }
            if (start != null || end != null) {
                boolean isChanged = start == null || end == null || isTransitionRequired(start, end);
                if (isChanged && (animator = createAnimator(sceneRoot, start, end)) != null) {
                    TransitionValues infoValues = null;
                    if (end != null) {
                        view = end.view;
                        String[] properties = getTransitionProperties();
                        if (properties != null && properties.length > 0) {
                            infoValues = new TransitionValues(view);
                            TransitionValues newValues = (TransitionValues) endValues.mViewValues.get(view);
                            if (newValues != null) {
                                for (int j = 0; j < properties.length; j++) {
                                    infoValues.values.put(properties[j], newValues.values.get(properties[j]));
                                }
                            }
                            int numExistingAnims = runningAnimators.size();
                            int j2 = 0;
                            while (true) {
                                if (j2 >= numExistingAnims) {
                                    break;
                                }
                                Animator anim = (Animator) runningAnimators.keyAt(j2);
                                AnimationInfo info = (AnimationInfo) runningAnimators.get(anim);
                                if (info.mValues == null || info.mView != view || !info.mName.equals(getName()) || !info.mValues.equals(infoValues)) {
                                    j2++;
                                } else {
                                    animator = null;
                                    break;
                                }
                            }
                        }
                    } else {
                        view = start.view;
                    }
                    if (animator != null) {
                        if (this.mPropagation != null) {
                            long delay = this.mPropagation.getStartDelay(sceneRoot, this, start, end);
                            startDelays.put(this.mAnimators.size(), (int) delay);
                            minStartDelay = Math.min(delay, minStartDelay);
                        }
                        runningAnimators.put(animator, new AnimationInfo(view, getName(), this, ViewUtils.getWindowId(sceneRoot), infoValues));
                        this.mAnimators.add(animator);
                    }
                }
            }
        }
        if (startDelays.size() != 0) {
            for (int i2 = 0; i2 < startDelays.size(); i2++) {
                int index = startDelays.keyAt(i2);
                Animator animator2 = (Animator) this.mAnimators.get(index);
                animator2.setStartDelay((startDelays.valueAt(i2) - minStartDelay) + animator2.getStartDelay());
            }
        }
    }

    boolean isValidTarget(View target) {
        int targetId = target.getId();
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(Integer.valueOf(targetId))) {
            return false;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains(target)) {
            return false;
        }
        if (this.mTargetTypeExcludes != null) {
            int numTypes = this.mTargetTypeExcludes.size();
            for (int i = 0; i < numTypes; i++) {
                Class<?> type = (Class) this.mTargetTypeExcludes.get(i);
                if (type.isInstance(target)) {
                    return false;
                }
            }
        }
        if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(target) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(target))) {
            return false;
        }
        if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && ((this.mTargetTypes == null || this.mTargetTypes.isEmpty()) && (this.mTargetNames == null || this.mTargetNames.isEmpty()))) {
            return true;
        }
        if (this.mTargetIds.contains(Integer.valueOf(targetId)) || this.mTargets.contains(target)) {
            return true;
        }
        if (this.mTargetNames != null && this.mTargetNames.contains(ViewCompat.getTransitionName(target))) {
            return true;
        }
        if (this.mTargetTypes != null) {
            for (int i2 = 0; i2 < this.mTargetTypes.size(); i2++) {
                if (((Class) this.mTargetTypes.get(i2)).isInstance(target)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayMap getRunningAnimators() {
        ArrayMap<Animator, AnimationInfo> runningAnimators = (ArrayMap) sRunningAnimators.get();
        if (runningAnimators == null) {
            ArrayMap<Animator, AnimationInfo> runningAnimators2 = new ArrayMap<>();
            sRunningAnimators.set(runningAnimators2);
            return runningAnimators2;
        }
        return runningAnimators;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void runAnimators() {
        start();
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        Iterator it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator anim = (Animator) it.next();
            if (runningAnimators.containsKey(anim)) {
                start();
                runAnimator(anim, runningAnimators);
            }
        }
        this.mAnimators.clear();
        end();
    }

    private void runAnimator(Animator animator, ArrayMap arrayMap) {
        if (animator != null) {
            animator.addListener(new 2(arrayMap));
            animate(animator);
        }
    }

    class 2 extends AnimatorListenerAdapter {
        final /* synthetic */ ArrayMap val$runningAnimators;

        2(ArrayMap arrayMap) {
            this.val$runningAnimators = arrayMap;
        }

        public void onAnimationStart(Animator animation) {
            Transition.this.mCurrentAnimators.add(animation);
        }

        public void onAnimationEnd(Animator animation) {
            this.val$runningAnimators.remove(animation);
            Transition.this.mCurrentAnimators.remove(animation);
        }
    }

    @NonNull
    public Transition addTarget(@NonNull View target) {
        this.mTargets.add(target);
        return this;
    }

    @NonNull
    public Transition addTarget(@IdRes int targetId) {
        if (targetId != 0) {
            this.mTargetIds.add(Integer.valueOf(targetId));
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull String targetName) {
        if (this.mTargetNames == null) {
            this.mTargetNames = new ArrayList();
        }
        this.mTargetNames.add(targetName);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull Class cls) {
        if (this.mTargetTypes == null) {
            this.mTargetTypes = new ArrayList();
        }
        this.mTargetTypes.add(cls);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View target) {
        this.mTargets.remove(target);
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int targetId) {
        if (targetId != 0) {
            this.mTargetIds.remove(Integer.valueOf(targetId));
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull String targetName) {
        if (this.mTargetNames != null) {
            this.mTargetNames.remove(targetName);
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull Class cls) {
        if (this.mTargetTypes != null) {
            this.mTargetTypes.remove(cls);
        }
        return this;
    }

    private static ArrayList excludeObject(ArrayList arrayList, Object obj, boolean exclude) {
        if (obj != null) {
            if (exclude) {
                return ArrayListManager.add(arrayList, obj);
            }
            return ArrayListManager.remove(arrayList, obj);
        }
        return arrayList;
    }

    @NonNull
    public Transition excludeTarget(@NonNull View target, boolean exclude) {
        this.mTargetExcludes = excludeView(this.mTargetExcludes, target, exclude);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int targetId, boolean exclude) {
        this.mTargetIdExcludes = excludeId(this.mTargetIdExcludes, targetId, exclude);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull String targetName, boolean exclude) {
        this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, targetName, exclude);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull View target, boolean exclude) {
        this.mTargetChildExcludes = excludeView(this.mTargetChildExcludes, target, exclude);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@IdRes int targetId, boolean exclude) {
        this.mTargetIdChildExcludes = excludeId(this.mTargetIdChildExcludes, targetId, exclude);
        return this;
    }

    private ArrayList excludeId(ArrayList arrayList, int targetId, boolean exclude) {
        if (targetId > 0) {
            if (exclude) {
                ArrayList<Integer> list = ArrayListManager.add(arrayList, Integer.valueOf(targetId));
                return list;
            }
            ArrayList<Integer> list2 = ArrayListManager.remove(arrayList, Integer.valueOf(targetId));
            return list2;
        }
        return arrayList;
    }

    private ArrayList excludeView(ArrayList arrayList, View target, boolean exclude) {
        if (target != null) {
            if (exclude) {
                ArrayList<View> list = ArrayListManager.add(arrayList, target);
                return list;
            }
            ArrayList<View> list2 = ArrayListManager.remove(arrayList, target);
            return list2;
        }
        return arrayList;
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class cls, boolean exclude) {
        this.mTargetTypeExcludes = excludeType(this.mTargetTypeExcludes, cls, exclude);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class cls, boolean exclude) {
        this.mTargetTypeChildExcludes = excludeType(this.mTargetTypeChildExcludes, cls, exclude);
        return this;
    }

    private ArrayList excludeType(ArrayList arrayList, Class cls, boolean exclude) {
        if (cls != null) {
            if (exclude) {
                ArrayList<Class<?>> list = ArrayListManager.add(arrayList, cls);
                return list;
            }
            ArrayList<Class<?>> list2 = ArrayListManager.remove(arrayList, cls);
            return list2;
        }
        return arrayList;
    }

    @NonNull
    public List getTargetIds() {
        return this.mTargetIds;
    }

    @NonNull
    public List getTargets() {
        return this.mTargets;
    }

    @Nullable
    public List getTargetNames() {
        return this.mTargetNames;
    }

    @Nullable
    public List getTargetTypes() {
        return this.mTargetTypes;
    }

    void captureValues(ViewGroup sceneRoot, boolean start) {
        clearValues(start);
        if ((this.mTargetIds.size() > 0 || this.mTargets.size() > 0) && ((this.mTargetNames == null || this.mTargetNames.isEmpty()) && (this.mTargetTypes == null || this.mTargetTypes.isEmpty()))) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                int id = ((Integer) this.mTargetIds.get(i)).intValue();
                View view = sceneRoot.findViewById(id);
                if (view != null) {
                    TransitionValues values = new TransitionValues(view);
                    if (start) {
                        captureStartValues(values);
                    } else {
                        captureEndValues(values);
                    }
                    values.mTargetedTransitions.add(this);
                    capturePropagationValues(values);
                    if (start) {
                        addViewValues(this.mStartValues, view, values);
                    } else {
                        addViewValues(this.mEndValues, view, values);
                    }
                }
            }
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                View view2 = (View) this.mTargets.get(i2);
                TransitionValues values2 = new TransitionValues(view2);
                if (start) {
                    captureStartValues(values2);
                } else {
                    captureEndValues(values2);
                }
                values2.mTargetedTransitions.add(this);
                capturePropagationValues(values2);
                if (start) {
                    addViewValues(this.mStartValues, view2, values2);
                } else {
                    addViewValues(this.mEndValues, view2, values2);
                }
            }
        } else {
            captureHierarchy(sceneRoot, start);
        }
        if (!start && this.mNameOverrides != null) {
            int numOverrides = this.mNameOverrides.size();
            ArrayList<View> overriddenViews = new ArrayList<>(numOverrides);
            for (int i3 = 0; i3 < numOverrides; i3++) {
                String fromName = (String) this.mNameOverrides.keyAt(i3);
                overriddenViews.add(this.mStartValues.mNameValues.remove(fromName));
            }
            for (int i4 = 0; i4 < numOverrides; i4++) {
                View view3 = (View) overriddenViews.get(i4);
                if (view3 != null) {
                    String toName = (String) this.mNameOverrides.valueAt(i4);
                    this.mStartValues.mNameValues.put(toName, view3);
                }
            }
        }
    }

    private static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues transitionValues) {
        transitionValuesMaps.mViewValues.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            if (transitionValuesMaps.mIdValues.indexOfKey(id) >= 0) {
                transitionValuesMaps.mIdValues.put(id, (Object) null);
            } else {
                transitionValuesMaps.mIdValues.put(id, view);
            }
        }
        String name = ViewCompat.getTransitionName(view);
        if (name != null) {
            if (transitionValuesMaps.mNameValues.containsKey(name)) {
                transitionValuesMaps.mNameValues.put(name, (Object) null);
            } else {
                transitionValuesMaps.mNameValues.put(name, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listview = view.getParent();
            if (listview.getAdapter().hasStableIds()) {
                int position = listview.getPositionForView(view);
                long itemId = listview.getItemIdAtPosition(position);
                if (transitionValuesMaps.mItemIdValues.indexOfKey(itemId) >= 0) {
                    View alreadyMatched = (View) transitionValuesMaps.mItemIdValues.get(itemId);
                    if (alreadyMatched != null) {
                        ViewCompat.setHasTransientState(alreadyMatched, false);
                        transitionValuesMaps.mItemIdValues.put(itemId, (Object) null);
                        return;
                    }
                    return;
                }
                ViewCompat.setHasTransientState(view, true);
                transitionValuesMaps.mItemIdValues.put(itemId, view);
            }
        }
    }

    void clearValues(boolean start) {
        if (start) {
            this.mStartValues.mViewValues.clear();
            this.mStartValues.mIdValues.clear();
            this.mStartValues.mItemIdValues.clear();
        } else {
            this.mEndValues.mViewValues.clear();
            this.mEndValues.mIdValues.clear();
            this.mEndValues.mItemIdValues.clear();
        }
    }

    private void captureHierarchy(View view, boolean start) {
        if (view != null) {
            int id = view.getId();
            if (this.mTargetIdExcludes == null || !this.mTargetIdExcludes.contains(Integer.valueOf(id))) {
                if (this.mTargetExcludes == null || !this.mTargetExcludes.contains(view)) {
                    if (this.mTargetTypeExcludes != null) {
                        int numTypes = this.mTargetTypeExcludes.size();
                        for (int i = 0; i < numTypes; i++) {
                            if (((Class) this.mTargetTypeExcludes.get(i)).isInstance(view)) {
                                return;
                            }
                        }
                    }
                    if (view.getParent() instanceof ViewGroup) {
                        TransitionValues values = new TransitionValues(view);
                        if (start) {
                            captureStartValues(values);
                        } else {
                            captureEndValues(values);
                        }
                        values.mTargetedTransitions.add(this);
                        capturePropagationValues(values);
                        if (start) {
                            addViewValues(this.mStartValues, view, values);
                        } else {
                            addViewValues(this.mEndValues, view, values);
                        }
                    }
                    if (view instanceof ViewGroup) {
                        if (this.mTargetIdChildExcludes == null || !this.mTargetIdChildExcludes.contains(Integer.valueOf(id))) {
                            if (this.mTargetChildExcludes == null || !this.mTargetChildExcludes.contains(view)) {
                                if (this.mTargetTypeChildExcludes != null) {
                                    int numTypes2 = this.mTargetTypeChildExcludes.size();
                                    for (int i2 = 0; i2 < numTypes2; i2++) {
                                        if (((Class) this.mTargetTypeChildExcludes.get(i2)).isInstance(view)) {
                                            return;
                                        }
                                    }
                                }
                                ViewGroup parent = (ViewGroup) view;
                                for (int i3 = 0; i3 < parent.getChildCount(); i3++) {
                                    captureHierarchy(parent.getChildAt(i3), start);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public TransitionValues getTransitionValues(@NonNull View view, boolean start) {
        if (this.mParent != null) {
            return this.mParent.getTransitionValues(view, start);
        }
        TransitionValuesMaps valuesMaps = start ? this.mStartValues : this.mEndValues;
        return (TransitionValues) valuesMaps.mViewValues.get(view);
    }

    TransitionValues getMatchedTransitionValues(View view, boolean viewInStart) {
        if (this.mParent != null) {
            return this.mParent.getMatchedTransitionValues(view, viewInStart);
        }
        ArrayList<TransitionValues> lookIn = viewInStart ? this.mStartValuesList : this.mEndValuesList;
        if (lookIn == null) {
            return null;
        }
        int count = lookIn.size();
        int index = -1;
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            }
            TransitionValues values = (TransitionValues) lookIn.get(i);
            if (values == null) {
                return null;
            }
            if (values.view != view) {
                i++;
            } else {
                index = i;
                break;
            }
        }
        if (index < 0) {
            return null;
        }
        ArrayList<TransitionValues> matchIn = viewInStart ? this.mEndValuesList : this.mStartValuesList;
        return (TransitionValues) matchIn.get(index);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void pause(View sceneRoot) {
        if (!this.mEnded) {
            int numAnimators = this.mCurrentAnimators.size();
            for (int i = numAnimators - 1; i >= 0; i--) {
                Animator animator = (Animator) this.mCurrentAnimators.get(i);
                AnimatorUtils.pause(animator);
            }
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList<TransitionListener> tmpListeners = (ArrayList) this.mListeners.clone();
                int numListeners = tmpListeners.size();
                for (int i2 = 0; i2 < numListeners; i2++) {
                    ((TransitionListener) tmpListeners.get(i2)).onTransitionPause(this);
                }
            }
            this.mPaused = true;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void resume(View sceneRoot) {
        if (this.mPaused) {
            if (!this.mEnded) {
                int numAnimators = this.mCurrentAnimators.size();
                for (int i = numAnimators - 1; i >= 0; i--) {
                    Animator animator = (Animator) this.mCurrentAnimators.get(i);
                    AnimatorUtils.resume(animator);
                }
                if (this.mListeners != null && this.mListeners.size() > 0) {
                    ArrayList<TransitionListener> tmpListeners = (ArrayList) this.mListeners.clone();
                    int numListeners = tmpListeners.size();
                    for (int i2 = 0; i2 < numListeners; i2++) {
                        ((TransitionListener) tmpListeners.get(i2)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    void playTransition(ViewGroup sceneRoot) {
        AnimationInfo oldInfo;
        this.mStartValuesList = new ArrayList();
        this.mEndValuesList = new ArrayList();
        matchStartAndEnd(this.mStartValues, this.mEndValues);
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        int numOldAnims = runningAnimators.size();
        WindowIdImpl windowId = ViewUtils.getWindowId(sceneRoot);
        for (int i = numOldAnims - 1; i >= 0; i--) {
            Animator anim = (Animator) runningAnimators.keyAt(i);
            if (anim != null && (oldInfo = (AnimationInfo) runningAnimators.get(anim)) != null && oldInfo.mView != null && windowId.equals(oldInfo.mWindowId)) {
                TransitionValues oldValues = oldInfo.mValues;
                View oldView = oldInfo.mView;
                TransitionValues startValues = getTransitionValues(oldView, true);
                TransitionValues endValues = getMatchedTransitionValues(oldView, true);
                if (startValues == null && endValues == null) {
                    endValues = (TransitionValues) this.mEndValues.mViewValues.get(oldView);
                }
                boolean cancel = !(startValues == null && endValues == null) && oldInfo.mTransition.isTransitionRequired(oldValues, endValues);
                if (cancel) {
                    if (anim.isRunning() || anim.isStarted()) {
                        anim.cancel();
                    } else {
                        runningAnimators.remove(anim);
                    }
                }
            }
        }
        createAnimators(sceneRoot, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        runAnimators();
    }

    public boolean isTransitionRequired(@Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return false;
        }
        String[] properties = getTransitionProperties();
        if (properties != null) {
            for (String property : properties) {
                if (isValueChanged(startValues, endValues, property)) {
                    return true;
                }
            }
            return false;
        }
        for (String key : startValues.values.keySet()) {
            if (isValueChanged(startValues, endValues, key)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValueChanged(TransitionValues oldValues, TransitionValues newValues, String key) {
        Object oldValue = oldValues.values.get(key);
        Object newValue = newValues.values.get(key);
        if (oldValue == null && newValue == null) {
            return false;
        }
        return oldValue == null || newValue == null || !oldValue.equals(newValue);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void animate(Animator animator) {
        if (animator == null) {
            end();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay() + animator.getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new 3());
        animator.start();
    }

    class 3 extends AnimatorListenerAdapter {
        3() {
        }

        public void onAnimationEnd(Animator animation) {
            Transition.this.end();
            animation.removeListener(this);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void start() {
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList<TransitionListener> tmpListeners = (ArrayList) this.mListeners.clone();
                int numListeners = tmpListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    ((TransitionListener) tmpListeners.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void end() {
        this.mNumInstances--;
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList<TransitionListener> tmpListeners = (ArrayList) this.mListeners.clone();
                int numListeners = tmpListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    ((TransitionListener) tmpListeners.get(i)).onTransitionEnd(this);
                }
            }
            for (int i2 = 0; i2 < this.mStartValues.mItemIdValues.size(); i2++) {
                View view = (View) this.mStartValues.mItemIdValues.valueAt(i2);
                if (view != null) {
                    ViewCompat.setHasTransientState(view, false);
                }
            }
            for (int i3 = 0; i3 < this.mEndValues.mItemIdValues.size(); i3++) {
                View view2 = (View) this.mEndValues.mItemIdValues.valueAt(i3);
                if (view2 != null) {
                    ViewCompat.setHasTransientState(view2, false);
                }
            }
            this.mEnded = true;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    void forceToEnd(ViewGroup sceneRoot) {
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        int numOldAnims = runningAnimators.size();
        if (sceneRoot != null && numOldAnims != 0) {
            WindowIdImpl windowId = ViewUtils.getWindowId(sceneRoot);
            ArrayMap<Animator, AnimationInfo> oldAnimators = new ArrayMap<>(runningAnimators);
            runningAnimators.clear();
            for (int i = numOldAnims - 1; i >= 0; i--) {
                AnimationInfo info = (AnimationInfo) oldAnimators.valueAt(i);
                if (info.mView != null && windowId != null && windowId.equals(info.mWindowId)) {
                    Animator anim = (Animator) oldAnimators.keyAt(i);
                    anim.end();
                }
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void cancel() {
        int numAnimators = this.mCurrentAnimators.size();
        for (int i = numAnimators - 1; i >= 0; i--) {
            Animator animator = (Animator) this.mCurrentAnimators.get(i);
            animator.cancel();
        }
        if (this.mListeners != null && this.mListeners.size() > 0) {
            ArrayList<TransitionListener> tmpListeners = (ArrayList) this.mListeners.clone();
            int numListeners = tmpListeners.size();
            for (int i2 = 0; i2 < numListeners; i2++) {
                ((TransitionListener) tmpListeners.get(i2)).onTransitionCancel(this);
            }
        }
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(listener);
        return this;
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener listener) {
        if (this.mListeners != null) {
            this.mListeners.remove(listener);
            if (this.mListeners.size() == 0) {
                this.mListeners = null;
            }
        }
        return this;
    }

    public void setPathMotion(@Nullable PathMotion pathMotion) {
        if (pathMotion == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
        } else {
            this.mPathMotion = pathMotion;
        }
    }

    @NonNull
    public PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    public void setEpicenterCallback(@Nullable EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    @Nullable
    public EpicenterCallback getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    @Nullable
    public Rect getEpicenter() {
        if (this.mEpicenterCallback == null) {
            return null;
        }
        return this.mEpicenterCallback.onGetEpicenter(this);
    }

    public void setPropagation(@Nullable TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    @Nullable
    public TransitionPropagation getPropagation() {
        return this.mPropagation;
    }

    void capturePropagationValues(TransitionValues transitionValues) {
        String[] propertyNames;
        if (this.mPropagation != null && !transitionValues.values.isEmpty() && (propertyNames = this.mPropagation.getPropagationProperties()) != null) {
            boolean containsAll = true;
            int i = 0;
            while (true) {
                if (i >= propertyNames.length) {
                    break;
                }
                if (transitionValues.values.containsKey(propertyNames[i])) {
                    i++;
                } else {
                    containsAll = false;
                    break;
                }
            }
            if (!containsAll) {
                this.mPropagation.captureValues(transitionValues);
            }
        }
    }

    void setCanRemoveViews(boolean canRemoveViews) {
        this.mCanRemoveViews = canRemoveViews;
    }

    public String toString() {
        return toString("");
    }

    public Transition clone() {
        try {
            Transition clone = (Transition) super.clone();
            clone.mAnimators = new ArrayList();
            clone.mStartValues = new TransitionValuesMaps();
            clone.mEndValues = new TransitionValuesMaps();
            clone.mStartValuesList = null;
            clone.mEndValuesList = null;
            return clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @NonNull
    public String getName() {
        return this.mName;
    }

    String toString(String indent) {
        String result = indent + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.mDuration != -1) {
            result = result + "dur(" + this.mDuration + ") ";
        }
        if (this.mStartDelay != -1) {
            result = result + "dly(" + this.mStartDelay + ") ";
        }
        if (this.mInterpolator != null) {
            result = result + "interp(" + this.mInterpolator + ") ";
        }
        if (this.mTargetIds.size() > 0 || this.mTargets.size() > 0) {
            String result2 = result + "tgts(";
            if (this.mTargetIds.size() > 0) {
                for (int i = 0; i < this.mTargetIds.size(); i++) {
                    if (i > 0) {
                        result2 = result2 + ", ";
                    }
                    result2 = result2 + this.mTargetIds.get(i);
                }
            }
            if (this.mTargets.size() > 0) {
                for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                    if (i2 > 0) {
                        result2 = result2 + ", ";
                    }
                    result2 = result2 + this.mTargets.get(i2);
                }
            }
            return result2 + ")";
        }
        return result;
    }

    private static class AnimationInfo {
        String mName;
        Transition mTransition;
        TransitionValues mValues;
        View mView;
        WindowIdImpl mWindowId;

        AnimationInfo(View view, String name, Transition transition, WindowIdImpl windowId, TransitionValues values) {
            this.mView = view;
            this.mName = name;
            this.mValues = values;
            this.mWindowId = windowId;
            this.mTransition = transition;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static ArrayList add(ArrayList arrayList, Object obj) {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            if (!arrayList.contains(obj)) {
                arrayList.add(obj);
            }
            return arrayList;
        }

        static ArrayList remove(ArrayList arrayList, Object obj) {
            if (arrayList != null) {
                arrayList.remove(obj);
                if (arrayList.isEmpty()) {
                    return null;
                }
                return arrayList;
            }
            return arrayList;
        }
    }
}
