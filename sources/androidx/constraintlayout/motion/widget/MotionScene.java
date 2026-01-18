package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class MotionScene {
    static final int ANTICIPATE = 6;
    static final int BOUNCE = 4;
    private static final String CONSTRAINTSET_TAG = "ConstraintSet";
    private static final boolean DEBUG = false;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final String INCLUDE_TAG = "include";
    private static final String INCLUDE_TAG_UC = "Include";
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final String KEYFRAMESET_TAG = "KeyFrameSet";
    public static final int LAYOUT_CALL_MEASURE = 2;
    public static final int LAYOUT_HONOR_REQUEST = 1;
    public static final int LAYOUT_IGNORE_REQUEST = 0;
    static final int LINEAR = 3;
    private static final int MIN_DURATION = 8;
    private static final String MOTIONSCENE_TAG = "MotionScene";
    private static final String ONCLICK_TAG = "OnClick";
    private static final String ONSWIPE_TAG = "OnSwipe";
    static final int OVERSHOOT = 5;
    private static final int SPLINE_STRING = -1;
    private static final String STATESET_TAG = "StateSet";
    private static final String TAG = "MotionScene";
    static final int TRANSITION_BACKWARD = 0;
    static final int TRANSITION_FORWARD = 1;
    private static final String TRANSITION_TAG = "Transition";
    public static final int UNSET = -1;
    private static final String VIEW_TRANSITION = "ViewTransition";
    private MotionEvent mLastTouchDown;
    float mLastTouchX;
    float mLastTouchY;
    private final MotionLayout mMotionLayout;
    private boolean mRtl;
    private MotionLayout.MotionTracker mVelocityTracker;
    final ViewTransitionController mViewTransitionController;
    StateSet mStateSet = null;
    Transition mCurrentTransition = null;
    private boolean mDisableAutoTransition = false;
    private ArrayList mTransitionList = new ArrayList();
    private Transition mDefaultTransition = null;
    private ArrayList mAbstractTransitionList = new ArrayList();
    private SparseArray mConstraintSetMap = new SparseArray();
    private HashMap mConstraintSetIdMap = new HashMap();
    private SparseIntArray mDeriveMap = new SparseIntArray();
    private boolean DEBUG_DESKTOP = false;
    private int mDefaultDuration = 400;
    private int mLayoutDuringTransition = 0;
    private boolean mIgnoreTouch = false;
    private boolean mMotionOutsideRegion = false;

    static /* synthetic */ int access$1000(MotionScene x0) {
        return x0.mLayoutDuringTransition;
    }

    static /* synthetic */ SparseArray access$1100(MotionScene x0) {
        return x0.mConstraintSetMap;
    }

    static /* synthetic */ int access$1200(MotionScene x0, Context x1, int x2) {
        return x0.parseInclude(x1, x2);
    }

    static /* synthetic */ MotionLayout access$700(MotionScene x0) {
        return x0.mMotionLayout;
    }

    static /* synthetic */ int access$900(MotionScene x0) {
        return x0.mDefaultDuration;
    }

    void setTransition(int beginId, int endId) {
        int start = beginId;
        int end = endId;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null) {
            int tmp = stateSet.stateGetConstraintID(beginId, -1, -1);
            if (tmp != -1) {
                start = tmp;
            }
            int tmp2 = this.mStateSet.stateGetConstraintID(endId, -1, -1);
            if (tmp2 != -1) {
                end = tmp2;
            }
        }
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$000(transition) == endId && Transition.access$100(this.mCurrentTransition) == beginId) {
            return;
        }
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition2 = (Transition) it.next();
            if ((Transition.access$000(transition2) == end && Transition.access$100(transition2) == start) || (Transition.access$000(transition2) == endId && Transition.access$100(transition2) == beginId)) {
                this.mCurrentTransition = transition2;
                if (transition2 != null && Transition.access$200(transition2) != null) {
                    Transition.access$200(this.mCurrentTransition).setRTL(this.mRtl);
                    return;
                }
                return;
            }
        }
        Transition matchTransition = this.mDefaultTransition;
        Iterator it2 = this.mAbstractTransitionList.iterator();
        while (it2.hasNext()) {
            Transition transition3 = (Transition) it2.next();
            if (Transition.access$000(transition3) == endId) {
                matchTransition = transition3;
            }
        }
        Transition t = new Transition(this, matchTransition);
        Transition.access$102(t, start);
        Transition.access$002(t, end);
        if (start != -1) {
            this.mTransitionList.add(t);
        }
        this.mCurrentTransition = t;
    }

    public void addTransition(Transition transition) {
        int index = getIndex(transition);
        if (index == -1) {
            this.mTransitionList.add(transition);
        } else {
            this.mTransitionList.set(index, transition);
        }
    }

    public void removeTransition(Transition transition) {
        int index = getIndex(transition);
        if (index != -1) {
            this.mTransitionList.remove(index);
        }
    }

    private int getIndex(Transition transition) {
        int id = Transition.access$300(transition);
        if (id == -1) {
            throw new IllegalArgumentException("The transition must have an id");
        }
        for (int index = 0; index < this.mTransitionList.size(); index++) {
            if (Transition.access$300((Transition) this.mTransitionList.get(index)) == id) {
                return index;
            }
        }
        return -1;
    }

    public boolean validateLayout(MotionLayout layout) {
        return layout == this.mMotionLayout && layout.mScene == this;
    }

    public void setTransition(Transition transition) {
        this.mCurrentTransition = transition;
        if (transition != null && Transition.access$200(transition) != null) {
            Transition.access$200(this.mCurrentTransition).setRTL(this.mRtl);
        }
    }

    private int getRealID(int stateId) {
        int tmp;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (tmp = stateSet.stateGetConstraintID(stateId, -1, -1)) != -1) {
            return tmp;
        }
        return stateId;
    }

    public List getTransitionsWithState(int stateId) {
        int stateId2 = getRealID(stateId);
        ArrayList<Transition> ret = new ArrayList<>();
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = (Transition) it.next();
            if (Transition.access$100(transition) == stateId2 || Transition.access$000(transition) == stateId2) {
                ret.add(transition);
            }
        }
        return ret;
    }

    public void addOnClickListeners(MotionLayout motionLayout, int currentState) {
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = (Transition) it.next();
            if (Transition.access$400(transition).size() > 0) {
                Iterator it2 = Transition.access$400(transition).iterator();
                while (it2.hasNext()) {
                    Transition.TransitionOnClick onClick = (Transition.TransitionOnClick) it2.next();
                    onClick.removeOnClickListeners(motionLayout);
                }
            }
        }
        Iterator it3 = this.mAbstractTransitionList.iterator();
        while (it3.hasNext()) {
            Transition transition2 = (Transition) it3.next();
            if (Transition.access$400(transition2).size() > 0) {
                Iterator it4 = Transition.access$400(transition2).iterator();
                while (it4.hasNext()) {
                    Transition.TransitionOnClick onClick2 = (Transition.TransitionOnClick) it4.next();
                    onClick2.removeOnClickListeners(motionLayout);
                }
            }
        }
        Iterator it5 = this.mTransitionList.iterator();
        while (it5.hasNext()) {
            Transition transition3 = (Transition) it5.next();
            if (Transition.access$400(transition3).size() > 0) {
                Iterator it6 = Transition.access$400(transition3).iterator();
                while (it6.hasNext()) {
                    Transition.TransitionOnClick onClick3 = (Transition.TransitionOnClick) it6.next();
                    onClick3.addOnClickListeners(motionLayout, currentState, transition3);
                }
            }
        }
        Iterator it7 = this.mAbstractTransitionList.iterator();
        while (it7.hasNext()) {
            Transition transition4 = (Transition) it7.next();
            if (Transition.access$400(transition4).size() > 0) {
                Iterator it8 = Transition.access$400(transition4).iterator();
                while (it8.hasNext()) {
                    Transition.TransitionOnClick onClick4 = (Transition.TransitionOnClick) it8.next();
                    onClick4.addOnClickListeners(motionLayout, currentState, transition4);
                }
            }
        }
    }

    public Transition bestTransitionFor(int currentState, float dx, float dy, MotionEvent lastTouchDown) {
        RectF cache;
        Iterator it;
        float val;
        float val2;
        float f = dx;
        float f2 = dy;
        if (currentState != -1) {
            List<Transition> candidates = getTransitionsWithState(currentState);
            float max = 0.0f;
            Transition best = null;
            RectF cache2 = new RectF();
            Iterator it2 = candidates.iterator();
            while (it2.hasNext()) {
                Transition transition = (Transition) it2.next();
                if (!Transition.access$500(transition)) {
                    if (Transition.access$200(transition) != null) {
                        Transition.access$200(transition).setRTL(this.mRtl);
                        RectF region = Transition.access$200(transition).getTouchRegion(this.mMotionLayout, cache2);
                        if (region == null || lastTouchDown == null || region.contains(lastTouchDown.getX(), lastTouchDown.getY())) {
                            RectF region2 = Transition.access$200(transition).getLimitBoundsTo(this.mMotionLayout, cache2);
                            if (region2 == null || lastTouchDown == null || region2.contains(lastTouchDown.getX(), lastTouchDown.getY())) {
                                float val3 = Transition.access$200(transition).dot(f, f2);
                                if (!Transition.access$200(transition).mIsRotateMode || lastTouchDown == null) {
                                    cache = cache2;
                                    it = it2;
                                    val = val3;
                                } else {
                                    float startX = lastTouchDown.getX() - Transition.access$200(transition).mRotateCenterX;
                                    float startY = lastTouchDown.getY() - Transition.access$200(transition).mRotateCenterY;
                                    float endX = f + startX;
                                    float endY = f2 + startY;
                                    cache = cache2;
                                    it = it2;
                                    double endAngle = Math.atan2(endY, endX);
                                    double startAngle = Math.atan2(startX, startY);
                                    val = 10.0f * ((float) (endAngle - startAngle));
                                }
                                if (Transition.access$000(transition) == currentState) {
                                    val2 = val * (-1.0f);
                                } else {
                                    val2 = val * 1.1f;
                                }
                                if (val2 > max) {
                                    float max2 = val2;
                                    max = max2;
                                    best = transition;
                                }
                            }
                        }
                    } else {
                        cache = cache2;
                        it = it2;
                    }
                    f = dx;
                    f2 = dy;
                    cache2 = cache;
                    it2 = it;
                }
            }
            return best;
        }
        return this.mCurrentTransition;
    }

    public ArrayList getDefinedTransitions() {
        return this.mTransitionList;
    }

    public Transition getTransitionById(int id) {
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = (Transition) it.next();
            if (Transition.access$300(transition) == id) {
                return transition;
            }
        }
        return null;
    }

    public int[] getConstraintSetIds() {
        int[] ids = new int[this.mConstraintSetMap.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = this.mConstraintSetMap.keyAt(i);
        }
        return ids;
    }

    boolean autoTransition(MotionLayout motionLayout, int currentState) {
        Transition transition;
        if (isProcessingTouch() || this.mDisableAutoTransition) {
            return false;
        }
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition2 = (Transition) it.next();
            if (Transition.access$600(transition2) != 0 && ((transition = this.mCurrentTransition) != transition2 || !transition.isTransitionFlag(2))) {
                if (currentState == Transition.access$100(transition2) && (Transition.access$600(transition2) == 4 || Transition.access$600(transition2) == 2)) {
                    motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                    motionLayout.setTransition(transition2);
                    if (Transition.access$600(transition2) == 4) {
                        motionLayout.transitionToEnd();
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    } else {
                        motionLayout.setProgress(1.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
                if (currentState == Transition.access$000(transition2) && (Transition.access$600(transition2) == 3 || Transition.access$600(transition2) == 1)) {
                    motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                    motionLayout.setTransition(transition2);
                    if (Transition.access$600(transition2) == 3) {
                        motionLayout.transitionToStart();
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    } else {
                        motionLayout.setProgress(0.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isProcessingTouch() {
        return this.mVelocityTracker != null;
    }

    public void setRtl(boolean rtl) {
        this.mRtl = rtl;
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            Transition.access$200(this.mCurrentTransition).setRTL(this.mRtl);
        }
    }

    public void viewTransition(int id, View... view) {
        this.mViewTransitionController.viewTransition(id, view);
    }

    public void enableViewTransition(int id, boolean enable) {
        this.mViewTransitionController.enableViewTransition(id, enable);
    }

    public boolean isViewTransitionEnabled(int id) {
        return this.mViewTransitionController.isViewTransitionEnabled(id);
    }

    public boolean applyViewTransition(int viewTransitionId, MotionController motionController) {
        return this.mViewTransitionController.applyViewTransition(viewTransitionId, motionController);
    }

    public static class Transition {
        public static final int AUTO_ANIMATE_TO_END = 4;
        public static final int AUTO_ANIMATE_TO_START = 3;
        public static final int AUTO_JUMP_TO_END = 2;
        public static final int AUTO_JUMP_TO_START = 1;
        public static final int AUTO_NONE = 0;
        static final int TRANSITION_FLAG_FIRST_DRAW = 1;
        static final int TRANSITION_FLAG_INTERCEPT_TOUCH = 4;
        static final int TRANSITION_FLAG_INTRA_AUTO = 2;
        private int mAutoTransition;
        private int mConstraintSetEnd;
        private int mConstraintSetStart;
        private int mDefaultInterpolator;
        private int mDefaultInterpolatorID;
        private String mDefaultInterpolatorString;
        private boolean mDisable;
        private int mDuration;
        private int mId;
        private boolean mIsAbstract;
        private ArrayList mKeyFramesList;
        private int mLayoutDuringTransition;
        private final MotionScene mMotionScene;
        private ArrayList mOnClicks;
        private int mPathMotionArc;
        private float mStagger;
        private TouchResponse mTouchResponse;
        private int mTransitionFlags;

        static /* synthetic */ int access$000(Transition x0) {
            return x0.mConstraintSetEnd;
        }

        static /* synthetic */ int access$002(Transition x0, int x1) {
            x0.mConstraintSetEnd = x1;
            return x1;
        }

        static /* synthetic */ int access$100(Transition x0) {
            return x0.mConstraintSetStart;
        }

        static /* synthetic */ int access$102(Transition x0, int x1) {
            x0.mConstraintSetStart = x1;
            return x1;
        }

        static /* synthetic */ boolean access$1300(Transition x0) {
            return x0.mIsAbstract;
        }

        static /* synthetic */ ArrayList access$1400(Transition x0) {
            return x0.mKeyFramesList;
        }

        static /* synthetic */ int access$1500(Transition x0) {
            return x0.mDefaultInterpolator;
        }

        static /* synthetic */ String access$1600(Transition x0) {
            return x0.mDefaultInterpolatorString;
        }

        static /* synthetic */ int access$1700(Transition x0) {
            return x0.mDefaultInterpolatorID;
        }

        static /* synthetic */ int access$1800(Transition x0) {
            return x0.mDuration;
        }

        static /* synthetic */ int access$1900(Transition x0) {
            return x0.mPathMotionArc;
        }

        static /* synthetic */ TouchResponse access$200(Transition x0) {
            return x0.mTouchResponse;
        }

        static /* synthetic */ float access$2000(Transition x0) {
            return x0.mStagger;
        }

        static /* synthetic */ TouchResponse access$202(Transition x0, TouchResponse x1) {
            x0.mTouchResponse = x1;
            return x1;
        }

        static /* synthetic */ int access$300(Transition x0) {
            return x0.mId;
        }

        static /* synthetic */ ArrayList access$400(Transition x0) {
            return x0.mOnClicks;
        }

        static /* synthetic */ boolean access$500(Transition x0) {
            return x0.mDisable;
        }

        static /* synthetic */ int access$600(Transition x0) {
            return x0.mAutoTransition;
        }

        static /* synthetic */ MotionScene access$800(Transition x0) {
            return x0.mMotionScene;
        }

        public void setOnSwipe(OnSwipe onSwipe) {
            this.mTouchResponse = onSwipe == null ? null : new TouchResponse(MotionScene.access$700(this.mMotionScene), onSwipe);
        }

        public void addOnClick(int id, int action) {
            Iterator it = this.mOnClicks.iterator();
            while (it.hasNext()) {
                TransitionOnClick onClick = (TransitionOnClick) it.next();
                if (onClick.mTargetId == id) {
                    onClick.mMode = action;
                    return;
                }
            }
            TransitionOnClick click = new TransitionOnClick(this, id, action);
            this.mOnClicks.add(click);
        }

        public void removeOnClick(int id) {
            TransitionOnClick toRemove = null;
            Iterator it = this.mOnClicks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TransitionOnClick onClick = (TransitionOnClick) it.next();
                if (onClick.mTargetId == id) {
                    toRemove = onClick;
                    break;
                }
            }
            if (toRemove != null) {
                this.mOnClicks.remove(toRemove);
            }
        }

        public int getLayoutDuringTransition() {
            return this.mLayoutDuringTransition;
        }

        public void setLayoutDuringTransition(int mode) {
            this.mLayoutDuringTransition = mode;
        }

        public void addOnClick(Context context, XmlPullParser parser) {
            this.mOnClicks.add(new TransitionOnClick(context, this, parser));
        }

        public void setAutoTransition(int type) {
            this.mAutoTransition = type;
        }

        public int getAutoTransition() {
            return this.mAutoTransition;
        }

        public int getId() {
            return this.mId;
        }

        public int getEndConstraintSetId() {
            return this.mConstraintSetEnd;
        }

        public int getStartConstraintSetId() {
            return this.mConstraintSetStart;
        }

        public void setDuration(int duration) {
            this.mDuration = Math.max(duration, 8);
        }

        public int getDuration() {
            return this.mDuration;
        }

        public float getStagger() {
            return this.mStagger;
        }

        public List getKeyFrameList() {
            return this.mKeyFramesList;
        }

        public void addKeyFrame(KeyFrames keyFrames) {
            this.mKeyFramesList.add(keyFrames);
        }

        public List getOnClickList() {
            return this.mOnClicks;
        }

        public TouchResponse getTouchResponse() {
            return this.mTouchResponse;
        }

        public void setStagger(float stagger) {
            this.mStagger = stagger;
        }

        public void setPathMotionArc(int arcMode) {
            this.mPathMotionArc = arcMode;
        }

        public int getPathMotionArc() {
            return this.mPathMotionArc;
        }

        public boolean isEnabled() {
            return !this.mDisable;
        }

        public void setEnable(boolean enable) {
            setEnabled(enable);
        }

        public void setEnabled(boolean enable) {
            this.mDisable = !enable;
        }

        public String debugString(Context context) {
            String ret;
            if (this.mConstraintSetStart == -1) {
                ret = "null";
            } else {
                ret = context.getResources().getResourceEntryName(this.mConstraintSetStart);
            }
            if (this.mConstraintSetEnd == -1) {
                return String.valueOf(ret).concat(" -> null");
            }
            String valueOf = String.valueOf(ret);
            String resourceEntryName = context.getResources().getResourceEntryName(this.mConstraintSetEnd);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(resourceEntryName).length());
            sb.append(valueOf);
            sb.append(" -> ");
            sb.append(resourceEntryName);
            String ret2 = sb.toString();
            return ret2;
        }

        public boolean isTransitionFlag(int flag) {
            return (this.mTransitionFlags & flag) != 0;
        }

        public void setTransitionFlag(int flag) {
            this.mTransitionFlags = flag;
        }

        public void setOnTouchUp(int touchUpMode) {
            TouchResponse touchResponse = getTouchResponse();
            if (touchResponse != null) {
                touchResponse.setTouchUpMode(touchUpMode);
            }
        }

        public static class TransitionOnClick implements View.OnClickListener {
            public static final int ANIM_TOGGLE = 17;
            public static final int ANIM_TO_END = 1;
            public static final int ANIM_TO_START = 16;
            public static final int JUMP_TO_END = 256;
            public static final int JUMP_TO_START = 4096;
            int mMode;
            int mTargetId;
            private final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser parser) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                TypedArray a = context.obtainStyledAttributes(Xml.asAttributeSet(parser), R.styleable.OnClick);
                int N = a.getIndexCount();
                for (int i = 0; i < N; i++) {
                    int attr = a.getIndex(i);
                    if (attr == R.styleable.OnClick_targetId) {
                        this.mTargetId = a.getResourceId(attr, this.mTargetId);
                    } else if (attr == R.styleable.OnClick_clickAction) {
                        this.mMode = a.getInt(attr, this.mMode);
                    }
                }
                a.recycle();
            }

            public TransitionOnClick(Transition transition, int id, int action) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                this.mTargetId = id;
                this.mMode = action;
            }

            public void addOnClickListeners(MotionLayout motionLayout, int currentState, Transition transition) {
                int i = this.mTargetId;
                View v = i == -1 ? motionLayout : motionLayout.findViewById(i);
                if (v == null) {
                    int i2 = this.mTargetId;
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("OnClick could not find id ");
                    sb.append(i2);
                    Log.e("MotionScene", sb.toString());
                    return;
                }
                int start = Transition.access$100(transition);
                int end = Transition.access$000(transition);
                if (start == -1) {
                    v.setOnClickListener(this);
                    return;
                }
                int i3 = this.mMode;
                boolean z = false;
                boolean listen = (i3 & 1) != 0 && currentState == start;
                boolean listen2 = listen | ((i3 & 256) != 0 && currentState == start) | ((i3 & 1) != 0 && currentState == start) | ((i3 & 16) != 0 && currentState == end);
                if ((i3 & 4096) != 0 && currentState == end) {
                    z = true;
                }
                if (listen2 | z) {
                    v.setOnClickListener(this);
                }
            }

            public void removeOnClickListeners(MotionLayout motionLayout) {
                int i = this.mTargetId;
                if (i == -1) {
                    return;
                }
                View v = motionLayout.findViewById(i);
                if (v == null) {
                    int i2 = this.mTargetId;
                    StringBuilder sb = new StringBuilder(35);
                    sb.append(" (*)  could not find id ");
                    sb.append(i2);
                    Log.e("MotionScene", sb.toString());
                    return;
                }
                v.setOnClickListener((View.OnClickListener) null);
            }

            boolean isTransitionViable(Transition current, MotionLayout tl) {
                Transition transition = this.mTransition;
                if (transition == current) {
                    return true;
                }
                int dest = Transition.access$000(transition);
                int from = Transition.access$100(this.mTransition);
                return from == -1 ? tl.mCurrentState != dest : tl.mCurrentState == from || tl.mCurrentState == dest;
            }

            public void onClick(View view) {
                MotionLayout tl = MotionScene.access$700(Transition.access$800(this.mTransition));
                if (!tl.isInteractionEnabled()) {
                    return;
                }
                if (Transition.access$100(this.mTransition) == -1) {
                    int currentState = tl.getCurrentState();
                    if (currentState == -1) {
                        tl.transitionToState(Transition.access$000(this.mTransition));
                        return;
                    }
                    Transition t = new Transition(Transition.access$800(this.mTransition), this.mTransition);
                    Transition.access$102(t, currentState);
                    Transition.access$002(t, Transition.access$000(this.mTransition));
                    tl.setTransition(t);
                    tl.transitionToEnd();
                    return;
                }
                Transition current = Transition.access$800(this.mTransition).mCurrentTransition;
                int i = this.mMode;
                boolean bidirectional = false;
                boolean forward = ((i & 1) == 0 && (i & 256) == 0) ? false : true;
                boolean backward = ((i & 16) == 0 && (i & 4096) == 0) ? false : true;
                if (forward && backward) {
                    bidirectional = true;
                }
                if (bidirectional) {
                    Transition transition = Transition.access$800(this.mTransition).mCurrentTransition;
                    Transition transition2 = this.mTransition;
                    if (transition != transition2) {
                        tl.setTransition(transition2);
                    }
                    if (tl.getCurrentState() == tl.getEndState() || tl.getProgress() > 0.5f) {
                        forward = false;
                    } else {
                        backward = false;
                    }
                }
                if (isTransitionViable(current, tl)) {
                    if (forward && (1 & this.mMode) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.transitionToEnd();
                        return;
                    }
                    if (backward && (this.mMode & 16) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.transitionToStart();
                    } else if (forward && (this.mMode & 256) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.setProgress(1.0f);
                    } else if (backward && (this.mMode & 4096) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.setProgress(0.0f);
                    }
                }
            }
        }

        Transition(MotionScene motionScene, Transition global) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mMotionScene = motionScene;
            this.mDuration = MotionScene.access$900(motionScene);
            if (global != null) {
                this.mPathMotionArc = global.mPathMotionArc;
                this.mDefaultInterpolator = global.mDefaultInterpolator;
                this.mDefaultInterpolatorString = global.mDefaultInterpolatorString;
                this.mDefaultInterpolatorID = global.mDefaultInterpolatorID;
                this.mDuration = global.mDuration;
                this.mKeyFramesList = global.mKeyFramesList;
                this.mStagger = global.mStagger;
                this.mLayoutDuringTransition = global.mLayoutDuringTransition;
            }
        }

        public Transition(int id, MotionScene motionScene, int constraintSetStartId, int constraintSetEndId) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mId = id;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = constraintSetStartId;
            this.mConstraintSetEnd = constraintSetEndId;
            this.mDuration = MotionScene.access$900(motionScene);
            this.mLayoutDuringTransition = MotionScene.access$1000(motionScene);
        }

        Transition(MotionScene motionScene, Context context, XmlPullParser parser) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mDuration = MotionScene.access$900(motionScene);
            this.mLayoutDuringTransition = MotionScene.access$1000(motionScene);
            this.mMotionScene = motionScene;
            fillFromAttributeList(motionScene, context, Xml.asAttributeSet(parser));
        }

        public void setInterpolatorInfo(int interpolator, String interpolatorString, int interpolatorID) {
            this.mDefaultInterpolator = interpolator;
            this.mDefaultInterpolatorString = interpolatorString;
            this.mDefaultInterpolatorID = interpolatorID;
        }

        private void fillFromAttributeList(MotionScene motionScene, Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Transition);
            fill(motionScene, context, a);
            a.recycle();
        }

        private void fill(MotionScene motionScene, Context context, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.Transition_constraintSetEnd) {
                    this.mConstraintSetEnd = a.getResourceId(attr, -1);
                    String type = context.getResources().getResourceTypeName(this.mConstraintSetEnd);
                    if ("layout".equals(type)) {
                        ConstraintSet cSet = new ConstraintSet();
                        cSet.load(context, this.mConstraintSetEnd);
                        MotionScene.access$1100(motionScene).append(this.mConstraintSetEnd, cSet);
                    } else if ("xml".equals(type)) {
                        int id = MotionScene.access$1200(motionScene, context, this.mConstraintSetEnd);
                        this.mConstraintSetEnd = id;
                    }
                } else if (attr == R.styleable.Transition_constraintSetStart) {
                    this.mConstraintSetStart = a.getResourceId(attr, this.mConstraintSetStart);
                    String type2 = context.getResources().getResourceTypeName(this.mConstraintSetStart);
                    if ("layout".equals(type2)) {
                        ConstraintSet cSet2 = new ConstraintSet();
                        cSet2.load(context, this.mConstraintSetStart);
                        MotionScene.access$1100(motionScene).append(this.mConstraintSetStart, cSet2);
                    } else if ("xml".equals(type2)) {
                        int id2 = MotionScene.access$1200(motionScene, context, this.mConstraintSetStart);
                        this.mConstraintSetStart = id2;
                    }
                } else if (attr == R.styleable.Transition_motionInterpolator) {
                    TypedValue type3 = a.peekValue(attr);
                    if (type3.type == 1) {
                        int resourceId = a.getResourceId(attr, -1);
                        this.mDefaultInterpolatorID = resourceId;
                        if (resourceId != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (type3.type == 3) {
                        String string = a.getString(attr);
                        this.mDefaultInterpolatorString = string;
                        if (string != null) {
                            if (string.indexOf("/") > 0) {
                                this.mDefaultInterpolatorID = a.getResourceId(attr, -1);
                                this.mDefaultInterpolator = -2;
                            } else {
                                this.mDefaultInterpolator = -1;
                            }
                        }
                    } else {
                        this.mDefaultInterpolator = a.getInteger(attr, this.mDefaultInterpolator);
                    }
                } else if (attr == R.styleable.Transition_duration) {
                    int i2 = a.getInt(attr, this.mDuration);
                    this.mDuration = i2;
                    if (i2 < 8) {
                        this.mDuration = 8;
                    }
                } else if (attr == R.styleable.Transition_staggered) {
                    this.mStagger = a.getFloat(attr, this.mStagger);
                } else if (attr == R.styleable.Transition_autoTransition) {
                    this.mAutoTransition = a.getInteger(attr, this.mAutoTransition);
                } else if (attr == R.styleable.Transition_android_id) {
                    this.mId = a.getResourceId(attr, this.mId);
                } else if (attr == R.styleable.Transition_transitionDisable) {
                    this.mDisable = a.getBoolean(attr, this.mDisable);
                } else if (attr == R.styleable.Transition_pathMotionArc) {
                    this.mPathMotionArc = a.getInteger(attr, -1);
                } else if (attr == R.styleable.Transition_layoutDuringTransition) {
                    this.mLayoutDuringTransition = a.getInteger(attr, 0);
                } else if (attr == R.styleable.Transition_transitionFlags) {
                    this.mTransitionFlags = a.getInteger(attr, 0);
                }
            }
            int i3 = this.mConstraintSetStart;
            if (i3 == -1) {
                this.mIsAbstract = true;
            }
        }
    }

    public MotionScene(MotionLayout layout) {
        this.mMotionLayout = layout;
        this.mViewTransitionController = new ViewTransitionController(layout);
    }

    MotionScene(Context context, MotionLayout layout, int resourceID) {
        this.mMotionLayout = layout;
        this.mViewTransitionController = new ViewTransitionController(layout);
        load(context, resourceID);
        this.mConstraintSetMap.put(R.id.motion_base, new ConstraintSet());
        this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(R.id.motion_base));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r12, int r13) {
        /*
            Method dump skipped, instructions count: 478
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.load(android.content.Context, int):void");
    }

    private void parseMotionSceneTags(Context context, XmlPullParser parser) {
        AttributeSet attrs = Xml.asAttributeSet(parser);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MotionScene);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.MotionScene_defaultDuration) {
                int i2 = a.getInt(attr, this.mDefaultDuration);
                this.mDefaultDuration = i2;
                if (i2 < 8) {
                    this.mDefaultDuration = 8;
                }
            } else if (attr == R.styleable.MotionScene_layoutDuringTransition) {
                this.mLayoutDuringTransition = a.getInteger(attr, 0);
            }
        }
        a.recycle();
    }

    private int getId(Context context, String idString) {
        int id = -1;
        if (idString.contains("/")) {
            String tmp = idString.substring(idString.indexOf(47) + 1);
            id = context.getResources().getIdentifier(tmp, "id", context.getPackageName());
            if (this.DEBUG_DESKTOP) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder(27);
                sb.append("id getMap res = ");
                sb.append(id);
                printStream.println(sb.toString());
            }
        }
        if (id == -1) {
            if (idString != null && idString.length() > 1) {
                int id2 = Integer.parseInt(idString.substring(1));
                return id2;
            }
            Log.e("MotionScene", "error in parsing id");
            return id;
        }
        return id;
    }

    private void parseInclude(Context context, XmlPullParser mainParser) {
        TypedArray a = context.obtainStyledAttributes(Xml.asAttributeSet(mainParser), R.styleable.include);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.include_constraintSet) {
                int resourceId = a.getResourceId(attr, -1);
                parseInclude(context, resourceId);
            }
        }
        a.recycle();
    }

    private int parseInclude(Context context, int resourceId) {
        Resources res = context.getResources();
        XmlResourceParser xml = res.getXml(resourceId);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                String tagName = xml.getName();
                if (2 == eventType && "ConstraintSet".equals(tagName)) {
                    return parseConstraintSet(context, xml);
                }
            }
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int parseConstraintSet(android.content.Context r18, org.xmlpull.v1.XmlPullParser r19) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.parseConstraintSet(android.content.Context, org.xmlpull.v1.XmlPullParser):int");
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    public ConstraintSet getConstraintSet(Context context, String id) {
        if (this.DEBUG_DESKTOP) {
            PrintStream printStream = System.out;
            String valueOf = String.valueOf(id);
            printStream.println(valueOf.length() != 0 ? "id ".concat(valueOf) : new String("id "));
            PrintStream printStream2 = System.out;
            int size = this.mConstraintSetMap.size();
            StringBuilder sb = new StringBuilder(16);
            sb.append("size ");
            sb.append(size);
            printStream2.println(sb.toString());
        }
        for (int i = 0; i < this.mConstraintSetMap.size(); i++) {
            int key = this.mConstraintSetMap.keyAt(i);
            String IdAsString = context.getResources().getResourceName(key);
            if (this.DEBUG_DESKTOP) {
                PrintStream printStream3 = System.out;
                StringBuilder sb2 = new StringBuilder(String.valueOf(IdAsString).length() + 41 + String.valueOf(id).length());
                sb2.append("Id for <");
                sb2.append(i);
                sb2.append("> is <");
                sb2.append(IdAsString);
                sb2.append("> looking for <");
                sb2.append(id);
                sb2.append(">");
                printStream3.println(sb2.toString());
            }
            if (id.equals(IdAsString)) {
                return (ConstraintSet) this.mConstraintSetMap.get(key);
            }
        }
        return null;
    }

    ConstraintSet getConstraintSet(int id) {
        return getConstraintSet(id, -1, -1);
    }

    ConstraintSet getConstraintSet(int id, int width, int height) {
        int cid;
        if (this.DEBUG_DESKTOP) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder(14);
            sb.append("id ");
            sb.append(id);
            printStream.println(sb.toString());
            PrintStream printStream2 = System.out;
            int size = this.mConstraintSetMap.size();
            StringBuilder sb2 = new StringBuilder(16);
            sb2.append("size ");
            sb2.append(size);
            printStream2.println(sb2.toString());
        }
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (cid = stateSet.stateGetConstraintID(id, width, height)) != -1) {
            id = cid;
        }
        if (this.mConstraintSetMap.get(id) == null) {
            String name = Debug.getName(this.mMotionLayout.getContext(), id);
            StringBuilder sb3 = new StringBuilder(String.valueOf(name).length() + 55);
            sb3.append("Warning could not find ConstraintSet id/");
            sb3.append(name);
            sb3.append(" In MotionScene");
            Log.e("MotionScene", sb3.toString());
            SparseArray sparseArray = this.mConstraintSetMap;
            return (ConstraintSet) sparseArray.get(sparseArray.keyAt(0));
        }
        return (ConstraintSet) this.mConstraintSetMap.get(id);
    }

    public void setConstraintSet(int id, ConstraintSet set) {
        this.mConstraintSetMap.put(id, set);
    }

    public void getKeyFrames(MotionController motionController) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            Transition transition2 = this.mDefaultTransition;
            if (transition2 != null) {
                Iterator it = Transition.access$1400(transition2).iterator();
                while (it.hasNext()) {
                    KeyFrames keyFrames = (KeyFrames) it.next();
                    keyFrames.addFrames(motionController);
                }
                return;
            }
            return;
        }
        Iterator it2 = Transition.access$1400(transition).iterator();
        while (it2.hasNext()) {
            KeyFrames keyFrames2 = (KeyFrames) it2.next();
            keyFrames2.addFrames(motionController);
        }
    }

    Key getKeyFrame(Context context, int type, int target, int position) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return null;
        }
        Iterator it = Transition.access$1400(transition).iterator();
        while (it.hasNext()) {
            KeyFrames keyFrames = (KeyFrames) it.next();
            for (Integer integer : keyFrames.getKeys()) {
                if (target == integer.intValue()) {
                    ArrayList<Key> keys = keyFrames.getKeyFramesForView(integer.intValue());
                    Iterator it2 = keys.iterator();
                    while (it2.hasNext()) {
                        Key key = (Key) it2.next();
                        if (key.mFramePosition == position && key.mType == type) {
                            return key;
                        }
                    }
                }
            }
        }
        return null;
    }

    int getTransitionDirection(int stateId) {
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = (Transition) it.next();
            if (Transition.access$100(transition) == stateId) {
                return 0;
            }
        }
        return 1;
    }

    boolean hasKeyFramePosition(View view, int position) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return false;
        }
        Iterator it = Transition.access$1400(transition).iterator();
        while (it.hasNext()) {
            KeyFrames keyFrames = (KeyFrames) it.next();
            ArrayList<Key> framePoints = keyFrames.getKeyFramesForView(view.getId());
            Iterator it2 = framePoints.iterator();
            while (it2.hasNext()) {
                Key framePoint = (Key) it2.next();
                if (framePoint.mFramePosition == position) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setKeyframe(View view, int position, String name, Object value) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return;
        }
        Iterator it = Transition.access$1400(transition).iterator();
        while (it.hasNext()) {
            KeyFrames keyFrames = (KeyFrames) it.next();
            ArrayList<Key> framePoints = keyFrames.getKeyFramesForView(view.getId());
            Iterator it2 = framePoints.iterator();
            while (it2.hasNext()) {
                Key framePoint = (Key) it2.next();
                if (framePoint.mFramePosition == position) {
                    float v = 0.0f;
                    if (value != null) {
                        v = ((Float) value).floatValue();
                    }
                    if (v == 0.0f) {
                    }
                    name.equalsIgnoreCase("app:PerpendicularPath_percent");
                }
            }
        }
    }

    public float getPathPercent(View view, int position) {
        return 0.0f;
    }

    boolean supportTouch() {
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = (Transition) it.next();
            if (Transition.access$200(transition) != null) {
                return true;
            }
        }
        Transition transition2 = this.mCurrentTransition;
        return (transition2 == null || Transition.access$200(transition2) == null) ? false : true;
    }

    void processTouchEvent(MotionEvent event, int currentState, MotionLayout motionLayout) {
        MotionLayout.MotionTracker motionTracker;
        MotionEvent motionEvent;
        RectF cache = new RectF();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = this.mMotionLayout.obtainVelocityTracker();
        }
        this.mVelocityTracker.addMovement(event);
        if (currentState != -1) {
            boolean z = false;
            switch (event.getAction()) {
                case 0:
                    this.mLastTouchX = event.getRawX();
                    this.mLastTouchY = event.getRawY();
                    this.mLastTouchDown = event;
                    this.mIgnoreTouch = false;
                    if (Transition.access$200(this.mCurrentTransition) != null) {
                        RectF region = Transition.access$200(this.mCurrentTransition).getLimitBoundsTo(this.mMotionLayout, cache);
                        if (region != null && !region.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                            this.mLastTouchDown = null;
                            this.mIgnoreTouch = true;
                            return;
                        }
                        RectF region2 = Transition.access$200(this.mCurrentTransition).getTouchRegion(this.mMotionLayout, cache);
                        if (region2 != null && !region2.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                            this.mMotionOutsideRegion = true;
                        } else {
                            this.mMotionOutsideRegion = false;
                        }
                        Transition.access$200(this.mCurrentTransition).setDown(this.mLastTouchX, this.mLastTouchY);
                        return;
                    }
                    return;
                case 2:
                    if (!this.mIgnoreTouch) {
                        float dy = event.getRawY() - this.mLastTouchY;
                        float dx = event.getRawX() - this.mLastTouchX;
                        if ((dx == 0.0d && dy == 0.0d) || (motionEvent = this.mLastTouchDown) == null) {
                            return;
                        }
                        Transition transition = bestTransitionFor(currentState, dx, dy, motionEvent);
                        if (transition != null) {
                            motionLayout.setTransition(transition);
                            RectF region3 = Transition.access$200(this.mCurrentTransition).getTouchRegion(this.mMotionLayout, cache);
                            if (region3 != null && !region3.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                                z = true;
                            }
                            this.mMotionOutsideRegion = z;
                            Transition.access$200(this.mCurrentTransition).setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
                            break;
                        }
                    }
                    break;
            }
        }
        if (this.mIgnoreTouch) {
            return;
        }
        Transition transition2 = this.mCurrentTransition;
        if (transition2 != null && Transition.access$200(transition2) != null && !this.mMotionOutsideRegion) {
            Transition.access$200(this.mCurrentTransition).processTouchEvent(event, this.mVelocityTracker, currentState, this);
        }
        this.mLastTouchX = event.getRawX();
        this.mLastTouchY = event.getRawY();
        if (event.getAction() == 1 && (motionTracker = this.mVelocityTracker) != null) {
            motionTracker.recycle();
            this.mVelocityTracker = null;
            if (motionLayout.mCurrentState != -1) {
                autoTransition(motionLayout, motionLayout.mCurrentState);
            }
        }
    }

    void processScrollMove(float dx, float dy) {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            Transition.access$200(this.mCurrentTransition).scrollMove(dx, dy);
        }
    }

    void processScrollUp(float dx, float dy) {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            Transition.access$200(this.mCurrentTransition).scrollUp(dx, dy);
        }
    }

    float getProgressDirection(float dx, float dy) {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getProgressDirection(dx, dy);
        }
        return 0.0f;
    }

    int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return Transition.access$100(transition);
    }

    int getEndId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return Transition.access$000(transition);
    }

    public Interpolator getInterpolator() {
        switch (Transition.access$1500(this.mCurrentTransition)) {
            case -2:
                return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), Transition.access$1700(this.mCurrentTransition));
            case -1:
                Easing easing = Easing.getInterpolator(Transition.access$1600(this.mCurrentTransition));
                return new 1(this, easing);
            case 0:
                return new AccelerateDecelerateInterpolator();
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new DecelerateInterpolator();
            case 3:
                return null;
            case 4:
                return new BounceInterpolator();
            case 5:
                return new OvershootInterpolator();
            case 6:
                return new AnticipateInterpolator();
            default:
                return null;
        }
    }

    class 1 implements Interpolator {
        final /* synthetic */ Easing val$easing;

        1(final MotionScene this$0, final Easing val$easing) {
            this.val$easing = val$easing;
        }

        public float getInterpolation(float v) {
            return (float) this.val$easing.get(v);
        }
    }

    public int getDuration() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return Transition.access$1800(transition);
        }
        return this.mDefaultDuration;
    }

    public void setDuration(int duration) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            transition.setDuration(duration);
        } else {
            this.mDefaultDuration = duration;
        }
    }

    public int gatPathMotionArc() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return Transition.access$1900(transition);
        }
        return -1;
    }

    public float getStaggered() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return Transition.access$2000(transition);
        }
        return 0.0f;
    }

    float getMaxAcceleration() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getMaxAcceleration();
        }
        return 0.0f;
    }

    float getMaxVelocity() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getMaxVelocity();
        }
        return 0.0f;
    }

    float getSpringStiffiness() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getSpringStiffness();
        }
        return 0.0f;
    }

    float getSpringMass() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getSpringMass();
        }
        return 0.0f;
    }

    float getSpringDamping() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getSpringDamping();
        }
        return 0.0f;
    }

    float getSpringStopThreshold() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getSpringStopThreshold();
        }
        return 0.0f;
    }

    int getSpringBoundary() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getSpringBoundary();
        }
        return 0;
    }

    int getAutoCompleteMode() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getAutoCompleteMode();
        }
        return 0;
    }

    void setupTouch() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            Transition.access$200(this.mCurrentTransition).setupTouch();
        }
    }

    boolean getMoveWhenScrollAtTop() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && Transition.access$200(transition) != null) {
            return Transition.access$200(this.mCurrentTransition).getMoveWhenScrollAtTop();
        }
        return false;
    }

    void readFallback(MotionLayout motionLayout) {
        for (int i = 0; i < this.mConstraintSetMap.size(); i++) {
            int key = this.mConstraintSetMap.keyAt(i);
            if (hasCycleDependency(key)) {
                Log.e("MotionScene", "Cannot be derived from yourself");
                return;
            }
            readConstraintChain(key, motionLayout);
        }
    }

    private boolean hasCycleDependency(int key) {
        int derived = this.mDeriveMap.get(key);
        int len = this.mDeriveMap.size();
        while (derived > 0) {
            if (derived == key) {
                return true;
            }
            int len2 = len - 1;
            if (len < 0) {
                return true;
            }
            derived = this.mDeriveMap.get(derived);
            len = len2;
        }
        return false;
    }

    private void readConstraintChain(int key, MotionLayout motionLayout) {
        ConstraintSet cs = (ConstraintSet) this.mConstraintSetMap.get(key);
        cs.derivedState = cs.mIdString;
        int derivedFromId = this.mDeriveMap.get(key);
        if (derivedFromId > 0) {
            readConstraintChain(derivedFromId, motionLayout);
            ConstraintSet derivedFrom = (ConstraintSet) this.mConstraintSetMap.get(derivedFromId);
            if (derivedFrom == null) {
                String valueOf = String.valueOf(Debug.getName(this.mMotionLayout.getContext(), derivedFromId));
                Log.e("MotionScene", valueOf.length() != 0 ? "ERROR! invalid deriveConstraintsFrom: @id/".concat(valueOf) : new String("ERROR! invalid deriveConstraintsFrom: @id/"));
                return;
            }
            String valueOf2 = String.valueOf(cs.derivedState);
            String str = derivedFrom.derivedState;
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 1 + String.valueOf(str).length());
            sb.append(valueOf2);
            sb.append("/");
            sb.append(str);
            cs.derivedState = sb.toString();
            cs.readFallback(derivedFrom);
        } else {
            cs.derivedState = String.valueOf(cs.derivedState).concat("  layout");
            cs.readFallback(motionLayout);
        }
        cs.applyDeltaFrom(cs);
    }

    public static String stripID(String id) {
        if (id == null) {
            return "";
        }
        int index = id.indexOf(47);
        if (index < 0) {
            return id;
        }
        return id.substring(index + 1);
    }

    public int lookUpConstraintId(String id) {
        Integer boxed = (Integer) this.mConstraintSetIdMap.get(id);
        if (boxed == null) {
            return 0;
        }
        return boxed.intValue();
    }

    public String lookUpConstraintName(int id) {
        for (Map.Entry<String, Integer> entry : this.mConstraintSetIdMap.entrySet()) {
            Integer boxed = (Integer) entry.getValue();
            if (boxed != null && boxed.intValue() == id) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public void disableAutoTransition(boolean disable) {
        this.mDisableAutoTransition = disable;
    }

    static String getLine(Context context, int resourceId, XmlPullParser pullParser) {
        String name = Debug.getName(context, resourceId);
        int lineNumber = pullParser.getLineNumber();
        String name2 = pullParser.getName();
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 22 + String.valueOf(name2).length());
        sb.append(".(");
        sb.append(name);
        sb.append(".xml:");
        sb.append(lineNumber);
        sb.append(") \"");
        sb.append(name2);
        sb.append("\"");
        return sb.toString();
    }
}
