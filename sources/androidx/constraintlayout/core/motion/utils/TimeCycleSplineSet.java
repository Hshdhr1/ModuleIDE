package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
public abstract class TimeCycleSplineSet {
    protected static final int CURVE_OFFSET = 2;
    protected static final int CURVE_PERIOD = 1;
    protected static final int CURVE_VALUE = 0;
    private static final String TAG = "SplineSet";
    protected static float VAL_2PI = 6.2831855f;
    protected int count;
    protected long last_time;
    protected CurveFit mCurveFit;
    protected String mType;
    protected int mWaveShape = 0;
    protected int[] mTimePoints = new int[10];
    protected float[][] mValues = (float[][]) Array.newInstance(float.class, new int[]{10, 3});
    protected float[] mCache = new float[3];
    protected boolean mContinue = false;
    protected float last_cycle = Float.NaN;

    public String toString() {
        String str = this.mType;
        DecimalFormat df = new DecimalFormat("##.##");
        for (int i = 0; i < this.count; i++) {
            str = str + "[" + this.mTimePoints[i] + " , " + df.format(this.mValues[i]) + "] ";
        }
        return str;
    }

    public void setType(String type) {
        this.mType = type;
    }

    protected float calcWave(float period) {
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(VAL_2PI * period);
            case 2:
                return 1.0f - Math.abs(period);
            case 3:
                return (((period * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                return 1.0f - (((period * 2.0f) + 1.0f) % 2.0f);
            case 5:
                return (float) Math.cos(VAL_2PI * period);
            case 6:
                float x = 1.0f - Math.abs(((period * 4.0f) % 4.0f) - 2.0f);
                return 1.0f - (x * x);
            default:
                return (float) Math.sin(VAL_2PI * period);
        }
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    protected void setStartTime(long currentTime) {
        this.last_time = currentTime;
    }

    public void setPoint(int position, float value, float period, int shape, float offset) {
        int[] iArr = this.mTimePoints;
        int i = this.count;
        iArr[i] = position;
        float[][] fArr = this.mValues;
        fArr[i][0] = value;
        fArr[i][1] = period;
        fArr[i][2] = offset;
        this.mWaveShape = Math.max(this.mWaveShape, shape);
        this.count++;
    }

    public static class CustomSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        KeyFrameArray.CustomArray mConstraintAttributeList;
        float[] mTempValues;
        KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();

        public CustomSet(String attribute, KeyFrameArray.CustomArray attrList) {
            this.mAttributeName = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int dimensionality = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] time = new double[size];
            this.mTempValues = new float[dimensionality + 2];
            this.mCache = new float[dimensionality];
            double[][] values = (double[][]) Array.newInstance(double.class, new int[]{size, dimensionality + 2});
            for (int i = 0; i < size; i++) {
                int key = this.mConstraintAttributeList.keyAt(i);
                CustomAttribute ca = this.mConstraintAttributeList.valueAt(i);
                float[] waveProp = this.mWaveProperties.valueAt(i);
                double d = key;
                Double.isNaN(d);
                time[i] = d * 0.01d;
                ca.getValuesToInterpolate(this.mTempValues);
                int k = 0;
                while (true) {
                    if (k < this.mTempValues.length) {
                        values[i][k] = r12[k];
                        k++;
                    }
                }
                values[i][dimensionality] = waveProp[0];
                values[i][dimensionality + 1] = waveProp[1];
            }
            this.mCurveFit = CurveFit.get(curveType, time, values);
        }

        public void setPoint(int position, float value, float period, int shape, float offset) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void setPoint(int position, CustomAttribute value, float period, int shape, float offset) {
            this.mConstraintAttributeList.append(position, value);
            this.mWaveProperties.append(position, new float[]{period, offset});
            this.mWaveShape = Math.max(this.mWaveShape, shape);
        }

        public boolean setProperty(MotionWidget view, float t, long time, KeyCache cache) {
            this.mCurveFit.getPos(t, this.mTempValues);
            float[] fArr = this.mTempValues;
            float period = fArr[fArr.length - 2];
            float offset = fArr[fArr.length - 1];
            long delta_time = time - this.last_time;
            if (Float.isNaN(this.last_cycle)) {
                this.last_cycle = cache.getFloatValue(view, this.mAttributeName, 0);
                if (Float.isNaN(this.last_cycle)) {
                    this.last_cycle = 0.0f;
                }
            }
            double d = this.last_cycle;
            double d2 = delta_time;
            Double.isNaN(d2);
            double d3 = period;
            Double.isNaN(d3);
            Double.isNaN(d);
            this.last_cycle = (float) ((d + ((d2 * 1.0E-9d) * d3)) % 1.0d);
            this.last_time = time;
            float wave = calcWave(this.last_cycle);
            this.mContinue = false;
            for (int i = 0; i < this.mCache.length; i++) {
                this.mContinue |= ((double) this.mTempValues[i]) != 0.0d;
                this.mCache[i] = (this.mTempValues[i] * wave) + offset;
            }
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(view, this.mCache);
            if (period != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setup(int r13) {
        /*
            r12 = this;
            int r0 = r12.count
            if (r0 != 0) goto L1d
            java.io.PrintStream r0 = java.lang.System.err
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Error no points added to "
            r1.append(r2)
            java.lang.String r2 = r12.mType
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            return
        L1d:
            int[] r1 = r12.mTimePoints
            float[][] r2 = r12.mValues
            r3 = 1
            int r0 = r0 - r3
            r4 = 0
            androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.Sort.doubleQuickSort(r1, r2, r4, r0)
            r0 = 0
            r1 = 1
        L29:
            int[] r2 = r12.mTimePoints
            int r5 = r2.length
            if (r1 >= r5) goto L3b
            r5 = r2[r1]
            int r6 = r1 + (-1)
            r2 = r2[r6]
            if (r5 == r2) goto L38
            int r0 = r0 + 1
        L38:
            int r1 = r1 + 1
            goto L29
        L3b:
            if (r0 != 0) goto L3e
            r0 = 1
        L3e:
            double[] r1 = new double[r0]
            r2 = 3
            r5 = 2
            int[] r6 = new int[r5]
            r6[r3] = r2
            r6[r4] = r0
            java.lang.Class<double> r2 = double.class
            java.lang.Object r2 = java.lang.reflect.Array.newInstance(r2, r6)
            double[][] r2 = (double[][]) r2
            r6 = 0
            r7 = 0
        L52:
            int r8 = r12.count
            if (r7 >= r8) goto L96
            if (r7 <= 0) goto L63
            int[] r8 = r12.mTimePoints
            r9 = r8[r7]
            int r10 = r7 + (-1)
            r8 = r8[r10]
            if (r9 != r8) goto L63
            goto L93
        L63:
            int[] r8 = r12.mTimePoints
            r8 = r8[r7]
            double r8 = (double) r8
            r10 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            java.lang.Double.isNaN(r8)
            double r8 = r8 * r10
            r1[r6] = r8
            r8 = r2[r6]
            float[][] r9 = r12.mValues
            r10 = r9[r7]
            r10 = r10[r4]
            double r10 = (double) r10
            r8[r4] = r10
            r8 = r2[r6]
            r10 = r9[r7]
            r10 = r10[r3]
            double r10 = (double) r10
            r8[r3] = r10
            r8 = r2[r6]
            r9 = r9[r7]
            r9 = r9[r5]
            double r9 = (double) r9
            r8[r5] = r9
            int r6 = r6 + 1
        L93:
            int r7 = r7 + 1
            goto L52
        L96:
            androidx.constraintlayout.core.motion.utils.CurveFit r3 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r13, r1, r2)
            r12.mCurveFit = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.setup(int):void");
    }

    protected static class Sort {
        protected Sort() {
        }

        static void doubleQuickSort(int[] key, float[][] value, int low, int hi) {
            int[] stack = new int[key.length + 10];
            int count = 0 + 1;
            stack[0] = hi;
            int count2 = count + 1;
            stack[count] = low;
            while (count2 > 0) {
                int count3 = count2 - 1;
                int low2 = stack[count3];
                count2 = count3 - 1;
                int hi2 = stack[count2];
                if (low2 < hi2) {
                    int p = partition(key, value, low2, hi2);
                    int count4 = count2 + 1;
                    stack[count2] = p - 1;
                    int count5 = count4 + 1;
                    stack[count4] = low2;
                    int count6 = count5 + 1;
                    stack[count5] = hi2;
                    count2 = count6 + 1;
                    stack[count6] = p + 1;
                }
            }
        }

        private static int partition(int[] array, float[][] value, int low, int hi) {
            int pivot = array[hi];
            int i = low;
            for (int j = low; j < hi; j++) {
                if (array[j] <= pivot) {
                    swap(array, value, i, j);
                    i++;
                }
            }
            swap(array, value, i, hi);
            return i;
        }

        private static void swap(int[] array, float[][] value, int a, int b) {
            int tmp = array[a];
            array[a] = array[b];
            array[b] = tmp;
            float[] tmpv = value[a];
            value[a] = value[b];
            value[b] = tmpv;
        }
    }

    public static class CustomVarSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        KeyFrameArray.CustomVar mConstraintAttributeList;
        float[] mTempValues;
        KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();

        public CustomVarSet(String attribute, KeyFrameArray.CustomVar attrList) {
            this.mAttributeName = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int dimensionality = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] time = new double[size];
            this.mTempValues = new float[dimensionality + 2];
            this.mCache = new float[dimensionality];
            double[][] values = (double[][]) Array.newInstance(double.class, new int[]{size, dimensionality + 2});
            for (int i = 0; i < size; i++) {
                int key = this.mConstraintAttributeList.keyAt(i);
                CustomVariable ca = this.mConstraintAttributeList.valueAt(i);
                float[] waveProp = this.mWaveProperties.valueAt(i);
                double d = key;
                Double.isNaN(d);
                time[i] = d * 0.01d;
                ca.getValuesToInterpolate(this.mTempValues);
                int k = 0;
                while (true) {
                    if (k < this.mTempValues.length) {
                        values[i][k] = r12[k];
                        k++;
                    }
                }
                values[i][dimensionality] = waveProp[0];
                values[i][dimensionality + 1] = waveProp[1];
            }
            this.mCurveFit = CurveFit.get(curveType, time, values);
        }

        public void setPoint(int position, float value, float period, int shape, float offset) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void setPoint(int position, CustomVariable value, float period, int shape, float offset) {
            this.mConstraintAttributeList.append(position, value);
            this.mWaveProperties.append(position, new float[]{period, offset});
            this.mWaveShape = Math.max(this.mWaveShape, shape);
        }

        public boolean setProperty(MotionWidget view, float t, long time, KeyCache cache) {
            this.mCurveFit.getPos(t, this.mTempValues);
            float[] fArr = this.mTempValues;
            float period = fArr[fArr.length - 2];
            float offset = fArr[fArr.length - 1];
            long delta_time = time - this.last_time;
            if (Float.isNaN(this.last_cycle)) {
                this.last_cycle = cache.getFloatValue(view, this.mAttributeName, 0);
                if (Float.isNaN(this.last_cycle)) {
                    this.last_cycle = 0.0f;
                }
            }
            double d = this.last_cycle;
            double d2 = delta_time;
            Double.isNaN(d2);
            double d3 = period;
            Double.isNaN(d3);
            Double.isNaN(d);
            this.last_cycle = (float) ((d + ((d2 * 1.0E-9d) * d3)) % 1.0d);
            this.last_time = time;
            float wave = calcWave(this.last_cycle);
            this.mContinue = false;
            for (int i = 0; i < this.mCache.length; i++) {
                this.mContinue |= ((double) this.mTempValues[i]) != 0.0d;
                this.mCache[i] = (this.mTempValues[i] * wave) + offset;
            }
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(view, this.mCache);
            if (period != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }
    }
}
