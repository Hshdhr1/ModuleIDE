package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class KeyTimeCycle extends Key {
    public static final int KEY_TYPE = 3;
    static final String NAME = "KeyTimeCycle";
    public static final int SHAPE_BOUNCE = 6;
    public static final int SHAPE_COS_WAVE = 5;
    public static final int SHAPE_REVERSE_SAW_WAVE = 4;
    public static final int SHAPE_SAW_WAVE = 3;
    public static final int SHAPE_SIN_WAVE = 0;
    public static final int SHAPE_SQUARE_WAVE = 1;
    public static final int SHAPE_TRIANGLE_WAVE = 2;
    private static final String TAG = "KeyTimeCycle";
    public static final String WAVE_OFFSET = "waveOffset";
    public static final String WAVE_PERIOD = "wavePeriod";
    public static final String WAVE_SHAPE = "waveShape";
    private String mTransitionEasing;
    private int mCurveFit = -1;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mProgress = Float.NaN;
    private int mWaveShape = 0;
    private String mCustomWaveShape = null;
    private float mWavePeriod = Float.NaN;
    private float mWaveOffset = 0.0f;

    public KeyTimeCycle() {
        this.mType = 3;
        this.mCustomConstraints = new HashMap();
    }

    static /* synthetic */ float access$000(KeyTimeCycle x0) {
        return x0.mAlpha;
    }

    static /* synthetic */ float access$002(KeyTimeCycle x0, float x1) {
        x0.mAlpha = x1;
        return x1;
    }

    static /* synthetic */ float access$100(KeyTimeCycle x0) {
        return x0.mElevation;
    }

    static /* synthetic */ float access$1000(KeyTimeCycle x0) {
        return x0.mRotationY;
    }

    static /* synthetic */ float access$1002(KeyTimeCycle x0, float x1) {
        x0.mRotationY = x1;
        return x1;
    }

    static /* synthetic */ float access$102(KeyTimeCycle x0, float x1) {
        x0.mElevation = x1;
        return x1;
    }

    static /* synthetic */ String access$1102(KeyTimeCycle x0, String x1) {
        x0.mTransitionEasing = x1;
        return x1;
    }

    static /* synthetic */ float access$1200(KeyTimeCycle x0) {
        return x0.mScaleY;
    }

    static /* synthetic */ float access$1202(KeyTimeCycle x0, float x1) {
        x0.mScaleY = x1;
        return x1;
    }

    static /* synthetic */ float access$1300(KeyTimeCycle x0) {
        return x0.mTransitionPathRotate;
    }

    static /* synthetic */ float access$1302(KeyTimeCycle x0, float x1) {
        x0.mTransitionPathRotate = x1;
        return x1;
    }

    static /* synthetic */ float access$1400(KeyTimeCycle x0) {
        return x0.mTranslationX;
    }

    static /* synthetic */ float access$1402(KeyTimeCycle x0, float x1) {
        x0.mTranslationX = x1;
        return x1;
    }

    static /* synthetic */ float access$1500(KeyTimeCycle x0) {
        return x0.mTranslationY;
    }

    static /* synthetic */ float access$1502(KeyTimeCycle x0, float x1) {
        x0.mTranslationY = x1;
        return x1;
    }

    static /* synthetic */ float access$1600(KeyTimeCycle x0) {
        return x0.mTranslationZ;
    }

    static /* synthetic */ float access$1602(KeyTimeCycle x0, float x1) {
        x0.mTranslationZ = x1;
        return x1;
    }

    static /* synthetic */ float access$1700(KeyTimeCycle x0) {
        return x0.mProgress;
    }

    static /* synthetic */ float access$1702(KeyTimeCycle x0, float x1) {
        x0.mProgress = x1;
        return x1;
    }

    static /* synthetic */ float access$200(KeyTimeCycle x0) {
        return x0.mRotation;
    }

    static /* synthetic */ float access$202(KeyTimeCycle x0, float x1) {
        x0.mRotation = x1;
        return x1;
    }

    static /* synthetic */ int access$300(KeyTimeCycle x0) {
        return x0.mCurveFit;
    }

    static /* synthetic */ int access$302(KeyTimeCycle x0, int x1) {
        x0.mCurveFit = x1;
        return x1;
    }

    static /* synthetic */ String access$402(KeyTimeCycle x0, String x1) {
        x0.mCustomWaveShape = x1;
        return x1;
    }

    static /* synthetic */ int access$500(KeyTimeCycle x0) {
        return x0.mWaveShape;
    }

    static /* synthetic */ int access$502(KeyTimeCycle x0, int x1) {
        x0.mWaveShape = x1;
        return x1;
    }

    static /* synthetic */ float access$600(KeyTimeCycle x0) {
        return x0.mWavePeriod;
    }

    static /* synthetic */ float access$602(KeyTimeCycle x0, float x1) {
        x0.mWavePeriod = x1;
        return x1;
    }

    static /* synthetic */ float access$700(KeyTimeCycle x0) {
        return x0.mWaveOffset;
    }

    static /* synthetic */ float access$702(KeyTimeCycle x0, float x1) {
        x0.mWaveOffset = x1;
        return x1;
    }

    static /* synthetic */ float access$800(KeyTimeCycle x0) {
        return x0.mScaleX;
    }

    static /* synthetic */ float access$802(KeyTimeCycle x0, float x1) {
        x0.mScaleX = x1;
        return x1;
    }

    static /* synthetic */ float access$900(KeyTimeCycle x0) {
        return x0.mRotationX;
    }

    static /* synthetic */ float access$902(KeyTimeCycle x0, float x1) {
        x0.mRotationX = x1;
        return x1;
    }

    public void load(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyTimeCycle);
        Loader.read(this, a);
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
        if (!Float.isNaN(this.mTranslationX)) {
            attributes.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            attributes.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            attributes.add("translationZ");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            attributes.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mScaleX)) {
            attributes.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            attributes.add("scaleY");
        }
        if (!Float.isNaN(this.mProgress)) {
            attributes.add("progress");
        }
        if (this.mCustomConstraints.size() > 0) {
            for (String s : this.mCustomConstraints.keySet()) {
                String valueOf = String.valueOf("CUSTOM,");
                String valueOf2 = String.valueOf(s);
                attributes.add(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            }
        }
    }

    public void setInterpolation(HashMap interpolation) {
        if (this.mCurveFit == -1) {
            return;
        }
        if (!Float.isNaN(this.mAlpha)) {
            interpolation.put("alpha", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mElevation)) {
            interpolation.put("elevation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotation)) {
            interpolation.put("rotation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationX)) {
            interpolation.put("rotationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationY)) {
            interpolation.put("rotationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationX)) {
            interpolation.put("translationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationY)) {
            interpolation.put("translationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            interpolation.put("translationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            interpolation.put("transitionPathRotate", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleX)) {
            interpolation.put("scaleX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleX)) {
            interpolation.put("scaleY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mProgress)) {
            interpolation.put("progress", Integer.valueOf(this.mCurveFit));
        }
        if (this.mCustomConstraints.size() > 0) {
            for (String s : this.mCustomConstraints.keySet()) {
                String valueOf = String.valueOf("CUSTOM,");
                String valueOf2 = String.valueOf(s);
                interpolation.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), Integer.valueOf(this.mCurveFit));
            }
        }
    }

    public void addValues(HashMap splines) {
        throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:136:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addTimeValues(java.util.HashMap r12) {
        /*
            Method dump skipped, instructions count: 614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyTimeCycle.addTimeValues(java.util.HashMap):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setValue(java.lang.String r3, java.lang.Object r4) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyTimeCycle.setValue(java.lang.String, java.lang.Object):void");
    }

    private static class Loader {
        private static final int ANDROID_ALPHA = 1;
        private static final int ANDROID_ELEVATION = 2;
        private static final int ANDROID_ROTATION = 4;
        private static final int ANDROID_ROTATION_X = 5;
        private static final int ANDROID_ROTATION_Y = 6;
        private static final int ANDROID_SCALE_X = 7;
        private static final int ANDROID_SCALE_Y = 14;
        private static final int ANDROID_TRANSLATION_X = 15;
        private static final int ANDROID_TRANSLATION_Y = 16;
        private static final int ANDROID_TRANSLATION_Z = 17;
        private static final int CURVE_FIT = 13;
        private static final int FRAME_POSITION = 12;
        private static final int PROGRESS = 18;
        private static final int TARGET_ID = 10;
        private static final int TRANSITION_EASING = 9;
        private static final int TRANSITION_PATH_ROTATE = 8;
        private static final int WAVE_OFFSET = 21;
        private static final int WAVE_PERIOD = 20;
        private static final int WAVE_SHAPE = 19;
        private static SparseIntArray mAttrMap;

        private Loader() {
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyTimeCycle_android_alpha, 1);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_elevation, 2);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_rotation, 4);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationX, 5);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationY, 6);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleX, 7);
            mAttrMap.append(R.styleable.KeyTimeCycle_transitionPathRotate, 8);
            mAttrMap.append(R.styleable.KeyTimeCycle_transitionEasing, 9);
            mAttrMap.append(R.styleable.KeyTimeCycle_motionTarget, 10);
            mAttrMap.append(R.styleable.KeyTimeCycle_framePosition, 12);
            mAttrMap.append(R.styleable.KeyTimeCycle_curveFit, 13);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleY, 14);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_translationX, 15);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_translationY, 16);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_translationZ, 17);
            mAttrMap.append(R.styleable.KeyTimeCycle_motionProgress, 18);
            mAttrMap.append(R.styleable.KeyTimeCycle_wavePeriod, 20);
            mAttrMap.append(R.styleable.KeyTimeCycle_waveOffset, 21);
            mAttrMap.append(R.styleable.KeyTimeCycle_waveShape, 19);
        }

        public static void read(KeyTimeCycle c, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mAttrMap.get(attr)) {
                    case 1:
                        KeyTimeCycle.access$002(c, a.getFloat(attr, KeyTimeCycle.access$000(c)));
                        break;
                    case 2:
                        KeyTimeCycle.access$102(c, a.getDimension(attr, KeyTimeCycle.access$100(c)));
                        break;
                    case 3:
                    case 11:
                    default:
                        String hexString = Integer.toHexString(attr);
                        int i2 = mAttrMap.get(attr);
                        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 33);
                        sb.append("unused attribute 0x");
                        sb.append(hexString);
                        sb.append("   ");
                        sb.append(i2);
                        Log.e("KeyTimeCycle", sb.toString());
                        break;
                    case 4:
                        KeyTimeCycle.access$202(c, a.getFloat(attr, KeyTimeCycle.access$200(c)));
                        break;
                    case 5:
                        KeyTimeCycle.access$902(c, a.getFloat(attr, KeyTimeCycle.access$900(c)));
                        break;
                    case 6:
                        KeyTimeCycle.access$1002(c, a.getFloat(attr, KeyTimeCycle.access$1000(c)));
                        break;
                    case 7:
                        KeyTimeCycle.access$802(c, a.getFloat(attr, KeyTimeCycle.access$800(c)));
                        break;
                    case 8:
                        KeyTimeCycle.access$1302(c, a.getFloat(attr, KeyTimeCycle.access$1300(c)));
                        break;
                    case 9:
                        KeyTimeCycle.access$1102(c, a.getString(attr));
                        break;
                    case 10:
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
                    case 12:
                        c.mFramePosition = a.getInt(attr, c.mFramePosition);
                        break;
                    case 13:
                        KeyTimeCycle.access$302(c, a.getInteger(attr, KeyTimeCycle.access$300(c)));
                        break;
                    case 14:
                        KeyTimeCycle.access$1202(c, a.getFloat(attr, KeyTimeCycle.access$1200(c)));
                        break;
                    case 15:
                        KeyTimeCycle.access$1402(c, a.getDimension(attr, KeyTimeCycle.access$1400(c)));
                        break;
                    case 16:
                        KeyTimeCycle.access$1502(c, a.getDimension(attr, KeyTimeCycle.access$1500(c)));
                        break;
                    case 17:
                        if (Build.VERSION.SDK_INT >= 21) {
                            KeyTimeCycle.access$1602(c, a.getDimension(attr, KeyTimeCycle.access$1600(c)));
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        KeyTimeCycle.access$1702(c, a.getFloat(attr, KeyTimeCycle.access$1700(c)));
                        break;
                    case 19:
                        if (a.peekValue(attr).type == 3) {
                            KeyTimeCycle.access$402(c, a.getString(attr));
                            KeyTimeCycle.access$502(c, 7);
                            break;
                        } else {
                            KeyTimeCycle.access$502(c, a.getInt(attr, KeyTimeCycle.access$500(c)));
                            break;
                        }
                    case 20:
                        KeyTimeCycle.access$602(c, a.getFloat(attr, KeyTimeCycle.access$600(c)));
                        break;
                    case 21:
                        TypedValue type = a.peekValue(attr);
                        if (type.type == 5) {
                            KeyTimeCycle.access$702(c, a.getDimension(attr, KeyTimeCycle.access$700(c)));
                            break;
                        } else {
                            KeyTimeCycle.access$702(c, a.getFloat(attr, KeyTimeCycle.access$700(c)));
                            break;
                        }
                }
            }
        }
    }

    public Key copy(Key src) {
        super.copy(src);
        KeyTimeCycle k = (KeyTimeCycle) src;
        this.mTransitionEasing = k.mTransitionEasing;
        this.mCurveFit = k.mCurveFit;
        this.mWaveShape = k.mWaveShape;
        this.mWavePeriod = k.mWavePeriod;
        this.mWaveOffset = k.mWaveOffset;
        this.mProgress = k.mProgress;
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
        return new KeyTimeCycle().copy(this);
    }
}
