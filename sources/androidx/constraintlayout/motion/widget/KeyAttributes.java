package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class KeyAttributes extends Key {
    private static final boolean DEBUG = false;
    public static final int KEY_TYPE = 1;
    static final String NAME = "KeyAttribute";
    private static final String TAG = "KeyAttributes";
    private String mTransitionEasing;
    private int mCurveFit = -1;
    private boolean mVisibility = false;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mProgress = Float.NaN;

    public KeyAttributes() {
        this.mType = 1;
        this.mCustomConstraints = new HashMap();
    }

    static /* synthetic */ float access$000(KeyAttributes x0) {
        return x0.mAlpha;
    }

    static /* synthetic */ float access$002(KeyAttributes x0, float x1) {
        x0.mAlpha = x1;
        return x1;
    }

    static /* synthetic */ float access$100(KeyAttributes x0) {
        return x0.mElevation;
    }

    static /* synthetic */ float access$1000(KeyAttributes x0) {
        return x0.mScaleY;
    }

    static /* synthetic */ float access$1002(KeyAttributes x0, float x1) {
        x0.mScaleY = x1;
        return x1;
    }

    static /* synthetic */ float access$102(KeyAttributes x0, float x1) {
        x0.mElevation = x1;
        return x1;
    }

    static /* synthetic */ float access$1100(KeyAttributes x0) {
        return x0.mTransitionPathRotate;
    }

    static /* synthetic */ float access$1102(KeyAttributes x0, float x1) {
        x0.mTransitionPathRotate = x1;
        return x1;
    }

    static /* synthetic */ float access$1200(KeyAttributes x0) {
        return x0.mTranslationX;
    }

    static /* synthetic */ float access$1202(KeyAttributes x0, float x1) {
        x0.mTranslationX = x1;
        return x1;
    }

    static /* synthetic */ float access$1300(KeyAttributes x0) {
        return x0.mTranslationY;
    }

    static /* synthetic */ float access$1302(KeyAttributes x0, float x1) {
        x0.mTranslationY = x1;
        return x1;
    }

    static /* synthetic */ float access$1400(KeyAttributes x0) {
        return x0.mTranslationZ;
    }

    static /* synthetic */ float access$1402(KeyAttributes x0, float x1) {
        x0.mTranslationZ = x1;
        return x1;
    }

    static /* synthetic */ float access$1500(KeyAttributes x0) {
        return x0.mProgress;
    }

    static /* synthetic */ float access$1502(KeyAttributes x0, float x1) {
        x0.mProgress = x1;
        return x1;
    }

    static /* synthetic */ float access$200(KeyAttributes x0) {
        return x0.mRotation;
    }

    static /* synthetic */ float access$202(KeyAttributes x0, float x1) {
        x0.mRotation = x1;
        return x1;
    }

    static /* synthetic */ int access$300(KeyAttributes x0) {
        return x0.mCurveFit;
    }

    static /* synthetic */ int access$302(KeyAttributes x0, int x1) {
        x0.mCurveFit = x1;
        return x1;
    }

    static /* synthetic */ float access$400(KeyAttributes x0) {
        return x0.mScaleX;
    }

    static /* synthetic */ float access$402(KeyAttributes x0, float x1) {
        x0.mScaleX = x1;
        return x1;
    }

    static /* synthetic */ float access$500(KeyAttributes x0) {
        return x0.mRotationX;
    }

    static /* synthetic */ float access$502(KeyAttributes x0, float x1) {
        x0.mRotationX = x1;
        return x1;
    }

    static /* synthetic */ float access$600(KeyAttributes x0) {
        return x0.mRotationY;
    }

    static /* synthetic */ float access$602(KeyAttributes x0, float x1) {
        x0.mRotationY = x1;
        return x1;
    }

    static /* synthetic */ float access$700(KeyAttributes x0) {
        return x0.mPivotX;
    }

    static /* synthetic */ float access$702(KeyAttributes x0, float x1) {
        x0.mPivotX = x1;
        return x1;
    }

    static /* synthetic */ float access$800(KeyAttributes x0) {
        return x0.mPivotY;
    }

    static /* synthetic */ float access$802(KeyAttributes x0, float x1) {
        x0.mPivotY = x1;
        return x1;
    }

    static /* synthetic */ String access$902(KeyAttributes x0, String x1) {
        x0.mTransitionEasing = x1;
        return x1;
    }

    public void load(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyAttribute);
        Loader.read(this, a);
    }

    int getCurveFit() {
        return this.mCurveFit;
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
        if (!Float.isNaN(this.mPivotX)) {
            attributes.add("transformPivotX");
        }
        if (!Float.isNaN(this.mPivotY)) {
            attributes.add("transformPivotY");
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
        if (!Float.isNaN(this.mPivotX)) {
            interpolation.put("transformPivotX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotY)) {
            interpolation.put("transformPivotY", Integer.valueOf(this.mCurveFit));
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
        if (!Float.isNaN(this.mScaleY)) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:154:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addValues(java.util.HashMap r8) {
        /*
            Method dump skipped, instructions count: 554
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyAttributes.addValues(java.util.HashMap):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setValue(java.lang.String r2, java.lang.Object r3) {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyAttributes.setValue(java.lang.String, java.lang.Object):void");
    }

    private static class Loader {
        private static final int ANDROID_ALPHA = 1;
        private static final int ANDROID_ELEVATION = 2;
        private static final int ANDROID_PIVOT_X = 19;
        private static final int ANDROID_PIVOT_Y = 20;
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
        private static SparseIntArray mAttrMap;

        private Loader() {
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyAttribute_android_alpha, 1);
            mAttrMap.append(R.styleable.KeyAttribute_android_elevation, 2);
            mAttrMap.append(R.styleable.KeyAttribute_android_rotation, 4);
            mAttrMap.append(R.styleable.KeyAttribute_android_rotationX, 5);
            mAttrMap.append(R.styleable.KeyAttribute_android_rotationY, 6);
            mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotX, 19);
            mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotY, 20);
            mAttrMap.append(R.styleable.KeyAttribute_android_scaleX, 7);
            mAttrMap.append(R.styleable.KeyAttribute_transitionPathRotate, 8);
            mAttrMap.append(R.styleable.KeyAttribute_transitionEasing, 9);
            mAttrMap.append(R.styleable.KeyAttribute_motionTarget, 10);
            mAttrMap.append(R.styleable.KeyAttribute_framePosition, 12);
            mAttrMap.append(R.styleable.KeyAttribute_curveFit, 13);
            mAttrMap.append(R.styleable.KeyAttribute_android_scaleY, 14);
            mAttrMap.append(R.styleable.KeyAttribute_android_translationX, 15);
            mAttrMap.append(R.styleable.KeyAttribute_android_translationY, 16);
            mAttrMap.append(R.styleable.KeyAttribute_android_translationZ, 17);
            mAttrMap.append(R.styleable.KeyAttribute_motionProgress, 18);
        }

        public static void read(KeyAttributes c, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mAttrMap.get(attr)) {
                    case 1:
                        KeyAttributes.access$002(c, a.getFloat(attr, KeyAttributes.access$000(c)));
                        break;
                    case 2:
                        KeyAttributes.access$102(c, a.getDimension(attr, KeyAttributes.access$100(c)));
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
                        Log.e("KeyAttribute", sb.toString());
                        break;
                    case 4:
                        KeyAttributes.access$202(c, a.getFloat(attr, KeyAttributes.access$200(c)));
                        break;
                    case 5:
                        KeyAttributes.access$502(c, a.getFloat(attr, KeyAttributes.access$500(c)));
                        break;
                    case 6:
                        KeyAttributes.access$602(c, a.getFloat(attr, KeyAttributes.access$600(c)));
                        break;
                    case 7:
                        KeyAttributes.access$402(c, a.getFloat(attr, KeyAttributes.access$400(c)));
                        break;
                    case 8:
                        KeyAttributes.access$1102(c, a.getFloat(attr, KeyAttributes.access$1100(c)));
                        break;
                    case 9:
                        KeyAttributes.access$902(c, a.getString(attr));
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
                        KeyAttributes.access$302(c, a.getInteger(attr, KeyAttributes.access$300(c)));
                        break;
                    case 14:
                        KeyAttributes.access$1002(c, a.getFloat(attr, KeyAttributes.access$1000(c)));
                        break;
                    case 15:
                        KeyAttributes.access$1202(c, a.getDimension(attr, KeyAttributes.access$1200(c)));
                        break;
                    case 16:
                        KeyAttributes.access$1302(c, a.getDimension(attr, KeyAttributes.access$1300(c)));
                        break;
                    case 17:
                        if (Build.VERSION.SDK_INT >= 21) {
                            KeyAttributes.access$1402(c, a.getDimension(attr, KeyAttributes.access$1400(c)));
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        KeyAttributes.access$1502(c, a.getFloat(attr, KeyAttributes.access$1500(c)));
                        break;
                    case 19:
                        KeyAttributes.access$702(c, a.getDimension(attr, KeyAttributes.access$700(c)));
                        break;
                    case 20:
                        KeyAttributes.access$802(c, a.getDimension(attr, KeyAttributes.access$800(c)));
                        break;
                }
            }
        }
    }

    public Key copy(Key src) {
        super.copy(src);
        KeyAttributes k = (KeyAttributes) src;
        this.mCurveFit = k.mCurveFit;
        this.mVisibility = k.mVisibility;
        this.mAlpha = k.mAlpha;
        this.mElevation = k.mElevation;
        this.mRotation = k.mRotation;
        this.mRotationX = k.mRotationX;
        this.mRotationY = k.mRotationY;
        this.mPivotX = k.mPivotX;
        this.mPivotY = k.mPivotY;
        this.mTransitionPathRotate = k.mTransitionPathRotate;
        this.mScaleX = k.mScaleX;
        this.mScaleY = k.mScaleY;
        this.mTranslationX = k.mTranslationX;
        this.mTranslationY = k.mTranslationY;
        this.mTranslationZ = k.mTranslationZ;
        this.mProgress = k.mProgress;
        return this;
    }

    public Key clone() {
        return new KeyAttributes().copy(this);
    }
}
