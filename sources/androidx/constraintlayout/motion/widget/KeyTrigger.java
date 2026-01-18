package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class KeyTrigger extends Key {
    public static final String CROSS = "CROSS";
    public static final int KEY_TYPE = 5;
    static final String NAME = "KeyTrigger";
    public static final String NEGATIVE_CROSS = "negativeCross";
    public static final String POSITIVE_CROSS = "positiveCross";
    public static final String POST_LAYOUT = "postLayout";
    private static final String TAG = "KeyTrigger";
    public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
    public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
    public static final String TRIGGER_ID = "triggerID";
    public static final String TRIGGER_RECEIVER = "triggerReceiver";
    public static final String TRIGGER_SLACK = "triggerSlack";
    public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
    public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
    public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
    private float mFireLastPos;
    private int mCurveFit = -1;
    private String mCross = null;
    private int mTriggerReceiver = UNSET;
    private String mNegativeCross = null;
    private String mPositiveCross = null;
    private int mTriggerID = UNSET;
    private int mTriggerCollisionId = UNSET;
    private View mTriggerCollisionView = null;
    float mTriggerSlack = 0.1f;
    private boolean mFireCrossReset = true;
    private boolean mFireNegativeReset = true;
    private boolean mFirePositiveReset = true;
    private float mFireThreshold = Float.NaN;
    private boolean mPostLayout = false;
    int mViewTransitionOnNegativeCross = UNSET;
    int mViewTransitionOnPositiveCross = UNSET;
    int mViewTransitionOnCross = UNSET;
    RectF mCollisionRect = new RectF();
    RectF mTargetRect = new RectF();
    HashMap mMethodHashMap = new HashMap();

    public KeyTrigger() {
        this.mType = 5;
        this.mCustomConstraints = new HashMap();
    }

    static /* synthetic */ float access$002(KeyTrigger x0, float x1) {
        x0.mFireThreshold = x1;
        return x1;
    }

    static /* synthetic */ String access$102(KeyTrigger x0, String x1) {
        x0.mNegativeCross = x1;
        return x1;
    }

    static /* synthetic */ String access$202(KeyTrigger x0, String x1) {
        x0.mPositiveCross = x1;
        return x1;
    }

    static /* synthetic */ String access$302(KeyTrigger x0, String x1) {
        x0.mCross = x1;
        return x1;
    }

    static /* synthetic */ int access$400(KeyTrigger x0) {
        return x0.mTriggerID;
    }

    static /* synthetic */ int access$402(KeyTrigger x0, int x1) {
        x0.mTriggerID = x1;
        return x1;
    }

    static /* synthetic */ int access$500(KeyTrigger x0) {
        return x0.mTriggerCollisionId;
    }

    static /* synthetic */ int access$502(KeyTrigger x0, int x1) {
        x0.mTriggerCollisionId = x1;
        return x1;
    }

    static /* synthetic */ boolean access$600(KeyTrigger x0) {
        return x0.mPostLayout;
    }

    static /* synthetic */ boolean access$602(KeyTrigger x0, boolean x1) {
        x0.mPostLayout = x1;
        return x1;
    }

    static /* synthetic */ int access$700(KeyTrigger x0) {
        return x0.mTriggerReceiver;
    }

    static /* synthetic */ int access$702(KeyTrigger x0, int x1) {
        x0.mTriggerReceiver = x1;
        return x1;
    }

    public void load(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyTrigger);
        Loader.read(this, a, context);
    }

    int getCurveFit() {
        return this.mCurveFit;
    }

    public void getAttributeNames(HashSet attributes) {
    }

    public void addValues(HashMap splines) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setValue(java.lang.String r2, java.lang.Object r3) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyTrigger.setValue(java.lang.String, java.lang.Object):void");
    }

    private void setUpRect(RectF rect, View child, boolean postLayout) {
        rect.top = child.getTop();
        rect.bottom = child.getBottom();
        rect.left = child.getLeft();
        rect.right = child.getRight();
        if (postLayout) {
            child.getMatrix().mapRect(rect);
        }
    }

    public void conditionallyFire(float pos, View child) {
        boolean fireCross = false;
        boolean fireNegative = false;
        boolean firePositive = false;
        if (this.mTriggerCollisionId != UNSET) {
            if (this.mTriggerCollisionView == null) {
                this.mTriggerCollisionView = child.getParent().findViewById(this.mTriggerCollisionId);
            }
            setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
            setUpRect(this.mTargetRect, child, this.mPostLayout);
            boolean in = this.mCollisionRect.intersect(this.mTargetRect);
            if (in) {
                if (this.mFireCrossReset) {
                    fireCross = true;
                    this.mFireCrossReset = false;
                }
                if (this.mFirePositiveReset) {
                    firePositive = true;
                    this.mFirePositiveReset = false;
                }
                this.mFireNegativeReset = true;
            } else {
                if (!this.mFireCrossReset) {
                    fireCross = true;
                    this.mFireCrossReset = true;
                }
                if (this.mFireNegativeReset) {
                    fireNegative = true;
                    this.mFireNegativeReset = false;
                }
                this.mFirePositiveReset = true;
            }
        } else {
            if (this.mFireCrossReset) {
                float f = this.mFireThreshold;
                float lastOffset = this.mFireLastPos - f;
                if ((pos - f) * lastOffset < 0.0f) {
                    fireCross = true;
                    this.mFireCrossReset = false;
                }
            } else if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireCrossReset = true;
            }
            if (this.mFireNegativeReset) {
                float f2 = this.mFireThreshold;
                float offset = pos - f2;
                float lastOffset2 = this.mFireLastPos - f2;
                if (offset * lastOffset2 < 0.0f && offset < 0.0f) {
                    fireNegative = true;
                    this.mFireNegativeReset = false;
                }
            } else if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireNegativeReset = true;
            }
            if (this.mFirePositiveReset) {
                float f3 = this.mFireThreshold;
                float offset2 = pos - f3;
                float lastOffset3 = this.mFireLastPos - f3;
                if (offset2 * lastOffset3 < 0.0f && offset2 > 0.0f) {
                    firePositive = true;
                    this.mFirePositiveReset = false;
                }
            } else if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFirePositiveReset = true;
            }
        }
        this.mFireLastPos = pos;
        if (fireNegative || fireCross || firePositive) {
            child.getParent().fireTrigger(this.mTriggerID, firePositive, pos);
        }
        View call = this.mTriggerReceiver == UNSET ? child : child.getParent().findViewById(this.mTriggerReceiver);
        if (fireNegative) {
            String str = this.mNegativeCross;
            if (str != null) {
                fire(str, call);
            }
            if (this.mViewTransitionOnNegativeCross != UNSET) {
                child.getParent().viewTransition(this.mViewTransitionOnNegativeCross, call);
            }
        }
        if (firePositive) {
            String str2 = this.mPositiveCross;
            if (str2 != null) {
                fire(str2, call);
            }
            if (this.mViewTransitionOnPositiveCross != UNSET) {
                child.getParent().viewTransition(this.mViewTransitionOnPositiveCross, call);
            }
        }
        if (fireCross) {
            String str3 = this.mCross;
            if (str3 != null) {
                fire(str3, call);
            }
            if (this.mViewTransitionOnCross != UNSET) {
                child.getParent().viewTransition(this.mViewTransitionOnCross, call);
            }
        }
    }

    private void fire(String str, View call) {
        if (str == null) {
            return;
        }
        if (str.startsWith(".")) {
            fireCustom(str, call);
            return;
        }
        Method method = null;
        if (this.mMethodHashMap.containsKey(str) && (method = (Method) this.mMethodHashMap.get(str)) == null) {
            return;
        }
        if (method == null) {
            try {
                method = call.getClass().getMethod(str, new Class[0]);
                this.mMethodHashMap.put(str, method);
            } catch (NoSuchMethodException e) {
                this.mMethodHashMap.put(str, (Object) null);
                String simpleName = call.getClass().getSimpleName();
                String name = Debug.getName(call);
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34 + String.valueOf(simpleName).length() + String.valueOf(name).length());
                sb.append("Could not find method \"");
                sb.append(str);
                sb.append("\"on class ");
                sb.append(simpleName);
                sb.append(" ");
                sb.append(name);
                Log.e("KeyTrigger", sb.toString());
                return;
            }
        }
        try {
            method.invoke(call, new Object[0]);
        } catch (Exception e2) {
            String str2 = this.mCross;
            String simpleName2 = call.getClass().getSimpleName();
            String name2 = Debug.getName(call);
            StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 30 + String.valueOf(simpleName2).length() + String.valueOf(name2).length());
            sb2.append("Exception in call \"");
            sb2.append(str2);
            sb2.append("\"on class ");
            sb2.append(simpleName2);
            sb2.append(" ");
            sb2.append(name2);
            Log.e("KeyTrigger", sb2.toString());
        }
    }

    private void fireCustom(String str, View view) {
        boolean callAll = str.length() == 1;
        if (!callAll) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String name : this.mCustomConstraints.keySet()) {
            String lowerCase = name.toLowerCase(Locale.ROOT);
            if (callAll || lowerCase.matches(str)) {
                ConstraintAttribute custom = (ConstraintAttribute) this.mCustomConstraints.get(name);
                if (custom != null) {
                    custom.applyCustom(view);
                }
            }
        }
    }

    private static class Loader {
        private static final int COLLISION = 9;
        private static final int CROSS = 4;
        private static final int FRAME_POS = 8;
        private static final int NEGATIVE_CROSS = 1;
        private static final int POSITIVE_CROSS = 2;
        private static final int POST_LAYOUT = 10;
        private static final int TARGET_ID = 7;
        private static final int TRIGGER_ID = 6;
        private static final int TRIGGER_RECEIVER = 11;
        private static final int TRIGGER_SLACK = 5;
        private static final int VT_CROSS = 12;
        private static final int VT_NEGATIVE_CROSS = 13;
        private static final int VT_POSITIVE_CROSS = 14;
        private static SparseIntArray mAttrMap;

        private Loader() {
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
            mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
            mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
            mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
            mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
            mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
            mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnCross, 12);
            mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnNegativeCross, 13);
            mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnPositiveCross, 14);
        }

        public static void read(KeyTrigger c, TypedArray a, Context context) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mAttrMap.get(attr)) {
                    case 1:
                        KeyTrigger.access$102(c, a.getString(attr));
                        break;
                    case 2:
                        KeyTrigger.access$202(c, a.getString(attr));
                        break;
                    case 3:
                    default:
                        String hexString = Integer.toHexString(attr);
                        int i2 = mAttrMap.get(attr);
                        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 33);
                        sb.append("unused attribute 0x");
                        sb.append(hexString);
                        sb.append("   ");
                        sb.append(i2);
                        Log.e("KeyTrigger", sb.toString());
                        break;
                    case 4:
                        KeyTrigger.access$302(c, a.getString(attr));
                        break;
                    case 5:
                        c.mTriggerSlack = a.getFloat(attr, c.mTriggerSlack);
                        break;
                    case 6:
                        KeyTrigger.access$402(c, a.getResourceId(attr, KeyTrigger.access$400(c)));
                        break;
                    case 7:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            c.mTargetId = a.getResourceId(attr, c.mTargetId);
                            if (c.mTargetId == -1) {
                                c.mTargetString = a.getString(attr);
                                break;
                            } else {
                                break;
                            }
                        } else if (a.peekValue(attr).type == 3) {
                            c.mTargetString = a.getString(attr);
                            break;
                        } else {
                            c.mTargetId = a.getResourceId(attr, c.mTargetId);
                            break;
                        }
                    case 8:
                        c.mFramePosition = a.getInteger(attr, c.mFramePosition);
                        KeyTrigger.access$002(c, (c.mFramePosition + 0.5f) / 100.0f);
                        break;
                    case 9:
                        KeyTrigger.access$502(c, a.getResourceId(attr, KeyTrigger.access$500(c)));
                        break;
                    case 10:
                        KeyTrigger.access$602(c, a.getBoolean(attr, KeyTrigger.access$600(c)));
                        break;
                    case 11:
                        KeyTrigger.access$702(c, a.getResourceId(attr, KeyTrigger.access$700(c)));
                        break;
                    case 12:
                        c.mViewTransitionOnCross = a.getResourceId(attr, c.mViewTransitionOnCross);
                        break;
                    case 13:
                        c.mViewTransitionOnNegativeCross = a.getResourceId(attr, c.mViewTransitionOnNegativeCross);
                        break;
                    case 14:
                        c.mViewTransitionOnPositiveCross = a.getResourceId(attr, c.mViewTransitionOnPositiveCross);
                        break;
                }
            }
        }
    }

    public Key copy(Key src) {
        super.copy(src);
        KeyTrigger k = (KeyTrigger) src;
        this.mCurveFit = k.mCurveFit;
        this.mCross = k.mCross;
        this.mTriggerReceiver = k.mTriggerReceiver;
        this.mNegativeCross = k.mNegativeCross;
        this.mPositiveCross = k.mPositiveCross;
        this.mTriggerID = k.mTriggerID;
        this.mTriggerCollisionId = k.mTriggerCollisionId;
        this.mTriggerCollisionView = k.mTriggerCollisionView;
        this.mTriggerSlack = k.mTriggerSlack;
        this.mFireCrossReset = k.mFireCrossReset;
        this.mFireNegativeReset = k.mFireNegativeReset;
        this.mFirePositiveReset = k.mFirePositiveReset;
        this.mFireThreshold = k.mFireThreshold;
        this.mFireLastPos = k.mFireLastPos;
        this.mPostLayout = k.mPostLayout;
        this.mCollisionRect = k.mCollisionRect;
        this.mTargetRect = k.mTargetRect;
        this.mMethodHashMap = k.mMethodHashMap;
        return this;
    }

    public Key clone() {
        return new KeyTrigger().copy(this);
    }
}
