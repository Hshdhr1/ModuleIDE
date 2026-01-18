package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class KeyCycle extends Key {
    public static final int KEY_TYPE = 4;
    static final String NAME = "KeyCycle";
    public static final int SHAPE_BOUNCE = 6;
    public static final int SHAPE_COS_WAVE = 5;
    public static final int SHAPE_REVERSE_SAW_WAVE = 4;
    public static final int SHAPE_SAW_WAVE = 3;
    public static final int SHAPE_SIN_WAVE = 0;
    public static final int SHAPE_SQUARE_WAVE = 1;
    public static final int SHAPE_TRIANGLE_WAVE = 2;
    private static final String TAG = "KeyCycle";
    public static final String WAVE_OFFSET = "waveOffset";
    public static final String WAVE_PERIOD = "wavePeriod";
    public static final String WAVE_PHASE = "wavePhase";
    public static final String WAVE_SHAPE = "waveShape";
    private String mTransitionEasing = null;
    private int mCurveFit = 0;
    private int mWaveShape = -1;
    private String mCustomWaveShape = null;
    private float mWavePeriod = Float.NaN;
    private float mWaveOffset = 0.0f;
    private float mWavePhase = 0.0f;
    private float mProgress = Float.NaN;
    private int mWaveVariesBy = -1;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;

    public KeyCycle() {
        this.mType = 4;
        this.mCustomConstraints = new HashMap();
    }

    static /* synthetic */ float access$1000(KeyCycle x0) {
        return x0.mRotation;
    }

    static /* synthetic */ float access$1002(KeyCycle x0, float x1) {
        x0.mRotation = x1;
        return x1;
    }

    static /* synthetic */ String access$102(KeyCycle x0, String x1) {
        x0.mTransitionEasing = x1;
        return x1;
    }

    static /* synthetic */ float access$1100(KeyCycle x0) {
        return x0.mRotationX;
    }

    static /* synthetic */ float access$1102(KeyCycle x0, float x1) {
        x0.mRotationX = x1;
        return x1;
    }

    static /* synthetic */ float access$1200(KeyCycle x0) {
        return x0.mRotationY;
    }

    static /* synthetic */ float access$1202(KeyCycle x0, float x1) {
        x0.mRotationY = x1;
        return x1;
    }

    static /* synthetic */ float access$1300(KeyCycle x0) {
        return x0.mTransitionPathRotate;
    }

    static /* synthetic */ float access$1302(KeyCycle x0, float x1) {
        x0.mTransitionPathRotate = x1;
        return x1;
    }

    static /* synthetic */ float access$1400(KeyCycle x0) {
        return x0.mScaleX;
    }

    static /* synthetic */ float access$1402(KeyCycle x0, float x1) {
        x0.mScaleX = x1;
        return x1;
    }

    static /* synthetic */ float access$1500(KeyCycle x0) {
        return x0.mScaleY;
    }

    static /* synthetic */ float access$1502(KeyCycle x0, float x1) {
        x0.mScaleY = x1;
        return x1;
    }

    static /* synthetic */ float access$1600(KeyCycle x0) {
        return x0.mTranslationX;
    }

    static /* synthetic */ float access$1602(KeyCycle x0, float x1) {
        x0.mTranslationX = x1;
        return x1;
    }

    static /* synthetic */ float access$1700(KeyCycle x0) {
        return x0.mTranslationY;
    }

    static /* synthetic */ float access$1702(KeyCycle x0, float x1) {
        x0.mTranslationY = x1;
        return x1;
    }

    static /* synthetic */ float access$1800(KeyCycle x0) {
        return x0.mTranslationZ;
    }

    static /* synthetic */ float access$1802(KeyCycle x0, float x1) {
        x0.mTranslationZ = x1;
        return x1;
    }

    static /* synthetic */ float access$1900(KeyCycle x0) {
        return x0.mProgress;
    }

    static /* synthetic */ float access$1902(KeyCycle x0, float x1) {
        x0.mProgress = x1;
        return x1;
    }

    static /* synthetic */ int access$200(KeyCycle x0) {
        return x0.mCurveFit;
    }

    static /* synthetic */ float access$2000(KeyCycle x0) {
        return x0.mWavePhase;
    }

    static /* synthetic */ float access$2002(KeyCycle x0, float x1) {
        x0.mWavePhase = x1;
        return x1;
    }

    static /* synthetic */ int access$202(KeyCycle x0, int x1) {
        x0.mCurveFit = x1;
        return x1;
    }

    static /* synthetic */ String access$302(KeyCycle x0, String x1) {
        x0.mCustomWaveShape = x1;
        return x1;
    }

    static /* synthetic */ int access$400(KeyCycle x0) {
        return x0.mWaveShape;
    }

    static /* synthetic */ int access$402(KeyCycle x0, int x1) {
        x0.mWaveShape = x1;
        return x1;
    }

    static /* synthetic */ float access$500(KeyCycle x0) {
        return x0.mWavePeriod;
    }

    static /* synthetic */ float access$502(KeyCycle x0, float x1) {
        x0.mWavePeriod = x1;
        return x1;
    }

    static /* synthetic */ float access$600(KeyCycle x0) {
        return x0.mWaveOffset;
    }

    static /* synthetic */ float access$602(KeyCycle x0, float x1) {
        x0.mWaveOffset = x1;
        return x1;
    }

    static /* synthetic */ int access$700(KeyCycle x0) {
        return x0.mWaveVariesBy;
    }

    static /* synthetic */ int access$702(KeyCycle x0, int x1) {
        x0.mWaveVariesBy = x1;
        return x1;
    }

    static /* synthetic */ float access$800(KeyCycle x0) {
        return x0.mAlpha;
    }

    static /* synthetic */ float access$802(KeyCycle x0, float x1) {
        x0.mAlpha = x1;
        return x1;
    }

    static /* synthetic */ float access$900(KeyCycle x0) {
        return x0.mElevation;
    }

    static /* synthetic */ float access$902(KeyCycle x0, float x1) {
        x0.mElevation = x1;
        return x1;
    }

    public void load(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyCycle);
        Loader.access$000(this, a);
    }

    public void getAttributeNames(HashSet attributes) {
        if (!Float.isNaN(this.mAlpha)) {
            attributes.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            attributes.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            attributes.add("rotation");
        }
        if (!Float.isNaN(this.mRotationX)) {
            attributes.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            attributes.add("rotationY");
        }
        if (!Float.isNaN(this.mScaleX)) {
            attributes.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            attributes.add("scaleY");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            attributes.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            attributes.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            attributes.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            attributes.add("translationZ");
        }
        if (this.mCustomConstraints.size() > 0) {
            for (String s : this.mCustomConstraints.keySet()) {
                String valueOf = String.valueOf("CUSTOM,");
                String valueOf2 = String.valueOf(s);
                attributes.add(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            }
        }
    }

    public void addCycleValues(HashMap oscSet) {
        ViewOscillator osc;
        ViewOscillator osc2;
        for (String key : oscSet.keySet()) {
            if (key.startsWith("CUSTOM")) {
                String customKey = key.substring("CUSTOM".length() + 1);
                ConstraintAttribute cValue = (ConstraintAttribute) this.mCustomConstraints.get(customKey);
                if (cValue != null && cValue.getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE && (osc = (ViewOscillator) oscSet.get(key)) != null) {
                    osc.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, cValue.getValueToInterpolate(), cValue);
                }
            } else {
                float value = getValue(key);
                if (!Float.isNaN(value) && (osc2 = (ViewOscillator) oscSet.get(key)) != null) {
                    osc2.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, value);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:84:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public float getValue(java.lang.String r4) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyCycle.getValue(java.lang.String):float");
    }

    public void addValues(HashMap splines) {
        int size = splines.size();
        StringBuilder sb = new StringBuilder(22);
        sb.append("add ");
        sb.append(size);
        sb.append(" values");
        Debug.logStack("KeyCycle", sb.toString(), 2);
        for (String s : splines.keySet()) {
            SplineSet splineSet = (SplineSet) splines.get(s);
            if (splineSet != null) {
                switch (s) {
                    case "alpha":
                        splineSet.setPoint(this.mFramePosition, this.mAlpha);
                        break;
                    case "elevation":
                        splineSet.setPoint(this.mFramePosition, this.mElevation);
                        break;
                    case "rotation":
                        splineSet.setPoint(this.mFramePosition, this.mRotation);
                        break;
                    case "rotationX":
                        splineSet.setPoint(this.mFramePosition, this.mRotationX);
                        break;
                    case "rotationY":
                        splineSet.setPoint(this.mFramePosition, this.mRotationY);
                        break;
                    case "transitionPathRotate":
                        splineSet.setPoint(this.mFramePosition, this.mTransitionPathRotate);
                        break;
                    case "scaleX":
                        splineSet.setPoint(this.mFramePosition, this.mScaleX);
                        break;
                    case "scaleY":
                        splineSet.setPoint(this.mFramePosition, this.mScaleY);
                        break;
                    case "translationX":
                        splineSet.setPoint(this.mFramePosition, this.mTranslationX);
                        break;
                    case "translationY":
                        splineSet.setPoint(this.mFramePosition, this.mTranslationY);
                        break;
                    case "translationZ":
                        splineSet.setPoint(this.mFramePosition, this.mTranslationZ);
                        break;
                    case "waveOffset":
                        splineSet.setPoint(this.mFramePosition, this.mWaveOffset);
                        break;
                    case "wavePhase":
                        splineSet.setPoint(this.mFramePosition, this.mWavePhase);
                        break;
                    case "progress":
                        splineSet.setPoint(this.mFramePosition, this.mProgress);
                        break;
                    default:
                        if (s.startsWith("CUSTOM")) {
                            break;
                        } else {
                            String valueOf = String.valueOf(s);
                            Log.v("WARNING KeyCycle", valueOf.length() != 0 ? "  UNKNOWN  ".concat(valueOf) : new String("  UNKNOWN  "));
                            break;
                        }
                }
            }
        }
    }

    private static class Loader {
        private static final int ANDROID_ALPHA = 9;
        private static final int ANDROID_ELEVATION = 10;
        private static final int ANDROID_ROTATION = 11;
        private static final int ANDROID_ROTATION_X = 12;
        private static final int ANDROID_ROTATION_Y = 13;
        private static final int ANDROID_SCALE_X = 15;
        private static final int ANDROID_SCALE_Y = 16;
        private static final int ANDROID_TRANSLATION_X = 17;
        private static final int ANDROID_TRANSLATION_Y = 18;
        private static final int ANDROID_TRANSLATION_Z = 19;
        private static final int CURVE_FIT = 4;
        private static final int FRAME_POSITION = 2;
        private static final int PROGRESS = 20;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TRANSITION_PATH_ROTATE = 14;
        private static final int WAVE_OFFSET = 7;
        private static final int WAVE_PERIOD = 6;
        private static final int WAVE_PHASE = 21;
        private static final int WAVE_SHAPE = 5;
        private static final int WAVE_VARIES_BY = 8;
        private static SparseIntArray mAttrMap;

        private Loader() {
        }

        static /* synthetic */ void access$000(KeyCycle x0, TypedArray x1) {
            read(x0, x1);
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyCycle_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyCycle_framePosition, 2);
            mAttrMap.append(R.styleable.KeyCycle_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyCycle_curveFit, 4);
            mAttrMap.append(R.styleable.KeyCycle_waveShape, 5);
            mAttrMap.append(R.styleable.KeyCycle_wavePeriod, 6);
            mAttrMap.append(R.styleable.KeyCycle_waveOffset, 7);
            mAttrMap.append(R.styleable.KeyCycle_waveVariesBy, 8);
            mAttrMap.append(R.styleable.KeyCycle_android_alpha, 9);
            mAttrMap.append(R.styleable.KeyCycle_android_elevation, 10);
            mAttrMap.append(R.styleable.KeyCycle_android_rotation, 11);
            mAttrMap.append(R.styleable.KeyCycle_android_rotationX, 12);
            mAttrMap.append(R.styleable.KeyCycle_android_rotationY, 13);
            mAttrMap.append(R.styleable.KeyCycle_transitionPathRotate, 14);
            mAttrMap.append(R.styleable.KeyCycle_android_scaleX, 15);
            mAttrMap.append(R.styleable.KeyCycle_android_scaleY, 16);
            mAttrMap.append(R.styleable.KeyCycle_android_translationX, 17);
            mAttrMap.append(R.styleable.KeyCycle_android_translationY, 18);
            mAttrMap.append(R.styleable.KeyCycle_android_translationZ, 19);
            mAttrMap.append(R.styleable.KeyCycle_motionProgress, 20);
            mAttrMap.append(R.styleable.KeyCycle_wavePhase, 21);
        }

        private static void read(KeyCycle c, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mAttrMap.get(attr)) {
                    case 1:
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
                    case 2:
                        c.mFramePosition = a.getInt(attr, c.mFramePosition);
                        break;
                    case 3:
                        KeyCycle.access$102(c, a.getString(attr));
                        break;
                    case 4:
                        KeyCycle.access$202(c, a.getInteger(attr, KeyCycle.access$200(c)));
                        break;
                    case 5:
                        if (a.peekValue(attr).type == 3) {
                            KeyCycle.access$302(c, a.getString(attr));
                            KeyCycle.access$402(c, 7);
                            break;
                        } else {
                            KeyCycle.access$402(c, a.getInt(attr, KeyCycle.access$400(c)));
                            break;
                        }
                    case 6:
                        KeyCycle.access$502(c, a.getFloat(attr, KeyCycle.access$500(c)));
                        break;
                    case 7:
                        TypedValue type = a.peekValue(attr);
                        if (type.type == 5) {
                            KeyCycle.access$602(c, a.getDimension(attr, KeyCycle.access$600(c)));
                            break;
                        } else {
                            KeyCycle.access$602(c, a.getFloat(attr, KeyCycle.access$600(c)));
                            break;
                        }
                    case 8:
                        KeyCycle.access$702(c, a.getInt(attr, KeyCycle.access$700(c)));
                        break;
                    case 9:
                        KeyCycle.access$802(c, a.getFloat(attr, KeyCycle.access$800(c)));
                        break;
                    case 10:
                        KeyCycle.access$902(c, a.getDimension(attr, KeyCycle.access$900(c)));
                        break;
                    case 11:
                        KeyCycle.access$1002(c, a.getFloat(attr, KeyCycle.access$1000(c)));
                        break;
                    case 12:
                        KeyCycle.access$1102(c, a.getFloat(attr, KeyCycle.access$1100(c)));
                        break;
                    case 13:
                        KeyCycle.access$1202(c, a.getFloat(attr, KeyCycle.access$1200(c)));
                        break;
                    case 14:
                        KeyCycle.access$1302(c, a.getFloat(attr, KeyCycle.access$1300(c)));
                        break;
                    case 15:
                        KeyCycle.access$1402(c, a.getFloat(attr, KeyCycle.access$1400(c)));
                        break;
                    case 16:
                        KeyCycle.access$1502(c, a.getFloat(attr, KeyCycle.access$1500(c)));
                        break;
                    case 17:
                        KeyCycle.access$1602(c, a.getDimension(attr, KeyCycle.access$1600(c)));
                        break;
                    case 18:
                        KeyCycle.access$1702(c, a.getDimension(attr, KeyCycle.access$1700(c)));
                        break;
                    case 19:
                        if (Build.VERSION.SDK_INT >= 21) {
                            KeyCycle.access$1802(c, a.getDimension(attr, KeyCycle.access$1800(c)));
                            break;
                        } else {
                            break;
                        }
                    case 20:
                        KeyCycle.access$1902(c, a.getFloat(attr, KeyCycle.access$1900(c)));
                        break;
                    case 21:
                        KeyCycle.access$2002(c, a.getFloat(attr, KeyCycle.access$2000(c)) / 360.0f);
                        break;
                    default:
                        String hexString = Integer.toHexString(attr);
                        int i2 = mAttrMap.get(attr);
                        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 33);
                        sb.append("unused attribute 0x");
                        sb.append(hexString);
                        sb.append("   ");
                        sb.append(i2);
                        Log.e("KeyCycle", sb.toString());
                        break;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:100:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setValue(java.lang.String r3, java.lang.Object r4) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyCycle.setValue(java.lang.String, java.lang.Object):void");
    }

    public Key copy(Key src) {
        super.copy(src);
        KeyCycle k = (KeyCycle) src;
        this.mTransitionEasing = k.mTransitionEasing;
        this.mCurveFit = k.mCurveFit;
        this.mWaveShape = k.mWaveShape;
        this.mCustomWaveShape = k.mCustomWaveShape;
        this.mWavePeriod = k.mWavePeriod;
        this.mWaveOffset = k.mWaveOffset;
        this.mWavePhase = k.mWavePhase;
        this.mProgress = k.mProgress;
        this.mWaveVariesBy = k.mWaveVariesBy;
        this.mAlpha = k.mAlpha;
        this.mElevation = k.mElevation;
        this.mRotation = k.mRotation;
        this.mTransitionPathRotate = k.mTransitionPathRotate;
        this.mRotationX = k.mRotationX;
        this.mRotationY = k.mRotationY;
        this.mScaleX = k.mScaleX;
        this.mScaleY = k.mScaleY;
        this.mTranslationX = k.mTranslationX;
        this.mTranslationY = k.mTranslationY;
        this.mTranslationZ = k.mTranslationZ;
        return this;
    }

    public Key clone() {
        return new KeyCycle().copy(this);
    }
}
