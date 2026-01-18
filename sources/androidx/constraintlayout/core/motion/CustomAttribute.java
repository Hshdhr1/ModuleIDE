package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.Utils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
public class CustomAttribute {
    private static final String TAG = "TransitionLayout";
    boolean mBooleanValue;
    private int mColorValue;
    private float mFloatValue;
    private int mIntegerValue;
    private boolean mMethod;
    String mName;
    private String mStringValue;
    private AttributeType mType;

    public enum AttributeType {
        INT_TYPE,
        FLOAT_TYPE,
        COLOR_TYPE,
        COLOR_DRAWABLE_TYPE,
        STRING_TYPE,
        BOOLEAN_TYPE,
        DIMENSION_TYPE,
        REFERENCE_TYPE
    }

    public AttributeType getType() {
        return this.mType;
    }

    public boolean isContinuous() {
        switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()]) {
            case 1:
            case 2:
            case 3:
                return false;
            default:
                return true;
        }
    }

    public void setFloatValue(float value) {
        this.mFloatValue = value;
    }

    public void setColorValue(int value) {
        this.mColorValue = value;
    }

    public void setIntValue(int value) {
        this.mIntegerValue = value;
    }

    public void setStringValue(String value) {
        this.mStringValue = value;
    }

    public int numberOfInterpolatedValues() {
        switch (this.mType) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                return 4;
            default:
                return 1;
        }
    }

    public float getValueToInterpolate() {
        switch (this.mType) {
            case BOOLEAN_TYPE:
                return this.mBooleanValue ? 1.0f : 0.0f;
            case STRING_TYPE:
                throw new RuntimeException("Cannot interpolate String");
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case INT_TYPE:
                return this.mIntegerValue;
            case FLOAT_TYPE:
                return this.mFloatValue;
            case DIMENSION_TYPE:
                return this.mFloatValue;
            default:
                return Float.NaN;
        }
    }

    public void getValuesToInterpolate(float[] ret) {
        switch (this.mType) {
            case BOOLEAN_TYPE:
                ret[0] = this.mBooleanValue ? 1.0f : 0.0f;
                return;
            case STRING_TYPE:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                int i = this.mColorValue;
                int a = (i >> 24) & 255;
                int r = (i >> 16) & 255;
                int g = (i >> 8) & 255;
                int b = i & 255;
                float f_r = (float) Math.pow(r / 255.0f, 2.2d);
                float f_g = (float) Math.pow(g / 255.0f, 2.2d);
                float f_b = (float) Math.pow(b / 255.0f, 2.2d);
                ret[0] = f_r;
                ret[1] = f_g;
                ret[2] = f_b;
                ret[3] = a / 255.0f;
                return;
            case INT_TYPE:
                ret[0] = this.mIntegerValue;
                return;
            case FLOAT_TYPE:
                ret[0] = this.mFloatValue;
                return;
            case DIMENSION_TYPE:
                ret[0] = this.mFloatValue;
                return;
            default:
                return;
        }
    }

    public void setValue(float[] value) {
        switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()]) {
            case 1:
            case 6:
                this.mIntegerValue = (int) value[0];
                return;
            case 2:
                this.mBooleanValue = ((double) value[0]) > 0.5d;
                return;
            case 3:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case 4:
            case 5:
                int hsvToRgb = hsvToRgb(value[0], value[1], value[2]);
                this.mColorValue = hsvToRgb;
                this.mColorValue = (hsvToRgb & 16777215) | (clamp((int) (value[3] * 255.0f)) << 24);
                return;
            case 7:
                this.mFloatValue = value[0];
                return;
            case 8:
                this.mFloatValue = value[0];
                return;
            default:
                return;
        }
    }

    public static int hsvToRgb(float hue, float saturation, float value) {
        int h = (int) (hue * 6.0f);
        float f = (6.0f * hue) - h;
        int p = (int) ((value * 255.0f * (1.0f - saturation)) + 0.5f);
        int q = (int) ((value * 255.0f * (1.0f - (f * saturation))) + 0.5f);
        int t = (int) ((value * 255.0f * (1.0f - ((1.0f - f) * saturation))) + 0.5f);
        int v = (int) ((255.0f * value) + 0.5f);
        switch (h) {
            case 0:
                return (-16777216) | ((v << 16) + (t << 8) + p);
            case 1:
                return (-16777216) | ((q << 16) + (v << 8) + p);
            case 2:
                return (-16777216) | ((p << 16) + (v << 8) + t);
            case 3:
                return (-16777216) | ((p << 16) + (q << 8) + v);
            case 4:
                return (-16777216) | ((t << 16) + (p << 8) + v);
            case 5:
                return (-16777216) | ((v << 16) + (p << 8) + q);
            default:
                return 0;
        }
    }

    public boolean diff(CustomAttribute CustomAttribute) {
        if (CustomAttribute == null || this.mType != CustomAttribute.mType) {
            return false;
        }
        switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()]) {
            case 1:
            case 6:
                if (this.mIntegerValue == CustomAttribute.mIntegerValue) {
                }
                break;
            case 2:
                if (this.mBooleanValue == CustomAttribute.mBooleanValue) {
                }
                break;
            case 3:
                if (this.mIntegerValue == CustomAttribute.mIntegerValue) {
                }
                break;
            case 4:
            case 5:
                if (this.mColorValue == CustomAttribute.mColorValue) {
                }
                break;
            case 7:
                if (this.mFloatValue == CustomAttribute.mFloatValue) {
                }
                break;
            case 8:
                if (this.mFloatValue == CustomAttribute.mFloatValue) {
                }
                break;
        }
        return false;
    }

    public CustomAttribute(String name, AttributeType attributeType) {
        this.mMethod = false;
        this.mName = name;
        this.mType = attributeType;
    }

    public CustomAttribute(String name, AttributeType attributeType, Object value, boolean method) {
        this.mMethod = false;
        this.mName = name;
        this.mType = attributeType;
        this.mMethod = method;
        setValue(value);
    }

    public CustomAttribute(CustomAttribute source, Object value) {
        this.mMethod = false;
        this.mName = source.mName;
        this.mType = source.mType;
        setValue(value);
    }

    public void setValue(Object value) {
        switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()]) {
            case 1:
            case 6:
                this.mIntegerValue = ((Integer) value).intValue();
                break;
            case 2:
                this.mBooleanValue = ((Boolean) value).booleanValue();
                break;
            case 3:
                this.mStringValue = (String) value;
                break;
            case 4:
            case 5:
                this.mColorValue = ((Integer) value).intValue();
                break;
            case 7:
                this.mFloatValue = ((Float) value).floatValue();
                break;
            case 8:
                this.mFloatValue = ((Float) value).floatValue();
                break;
        }
    }

    public static HashMap extractAttributes(HashMap hashMap, Object view) {
        HashMap<String, CustomAttribute> ret = new HashMap<>();
        Class<? extends Object> viewClass = view.getClass();
        for (String name : hashMap.keySet()) {
            CustomAttribute CustomAttribute = (CustomAttribute) hashMap.get(name);
            try {
                Method method = viewClass.getMethod("getMap" + name, new Class[0]);
                Object val = method.invoke(view, new Object[0]);
                ret.put(name, new CustomAttribute(CustomAttribute, val));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return ret;
    }

    public static void setAttributes(Object view, HashMap hashMap) {
        Class<? extends Object> viewClass = view.getClass();
        for (String name : hashMap.keySet()) {
            CustomAttribute CustomAttribute = (CustomAttribute) hashMap.get(name);
            String methodName = name;
            if (!CustomAttribute.mMethod) {
                methodName = "set" + methodName;
            }
            try {
                switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[CustomAttribute.mType.ordinal()]) {
                    case 1:
                        Method method = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                        method.invoke(view, new Object[]{Integer.valueOf(CustomAttribute.mIntegerValue)});
                        break;
                    case 2:
                        Method method2 = viewClass.getMethod(methodName, new Class[]{Boolean.TYPE});
                        method2.invoke(view, new Object[]{Boolean.valueOf(CustomAttribute.mBooleanValue)});
                        break;
                    case 3:
                        Method method3 = viewClass.getMethod(methodName, new Class[]{CharSequence.class});
                        method3.invoke(view, new Object[]{CustomAttribute.mStringValue});
                        break;
                    case 4:
                        Method method4 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                        method4.invoke(view, new Object[]{Integer.valueOf(CustomAttribute.mColorValue)});
                        break;
                    case 6:
                        Method method5 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                        method5.invoke(view, new Object[]{Integer.valueOf(CustomAttribute.mIntegerValue)});
                        break;
                    case 7:
                        Method method6 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                        method6.invoke(view, new Object[]{Float.valueOf(CustomAttribute.mFloatValue)});
                        break;
                    case 8:
                        Method method7 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                        method7.invoke(view, new Object[]{Float.valueOf(CustomAttribute.mFloatValue)});
                        break;
                }
            } catch (IllegalAccessException e) {
                Utils.loge("TransitionLayout", " Custom Attribute \"" + name + "\" not found on " + viewClass.getName());
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                Utils.loge("TransitionLayout", e2.getMessage());
                Utils.loge("TransitionLayout", " Custom Attribute \"" + name + "\" not found on " + viewClass.getName());
                StringBuilder sb = new StringBuilder();
                sb.append(viewClass.getName());
                sb.append(" must have a method ");
                sb.append(methodName);
                Utils.loge("TransitionLayout", sb.toString());
            } catch (InvocationTargetException e3) {
                Utils.loge("TransitionLayout", " Custom Attribute \"" + name + "\" not found on " + viewClass.getName());
                e3.printStackTrace();
            }
        }
    }

    public void applyCustom(Object view) {
        Class<? extends Object> viewClass = view.getClass();
        String name = this.mName;
        String methodName = name;
        if (!this.mMethod) {
            methodName = "set" + methodName;
        }
        try {
            switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()]) {
                case 1:
                case 6:
                    Method method = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                    method.invoke(view, new Object[]{Integer.valueOf(this.mIntegerValue)});
                    break;
                case 2:
                    Method method2 = viewClass.getMethod(methodName, new Class[]{Boolean.TYPE});
                    method2.invoke(view, new Object[]{Boolean.valueOf(this.mBooleanValue)});
                    break;
                case 3:
                    Method method3 = viewClass.getMethod(methodName, new Class[]{CharSequence.class});
                    method3.invoke(view, new Object[]{this.mStringValue});
                    break;
                case 4:
                    Method method4 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                    method4.invoke(view, new Object[]{Integer.valueOf(this.mColorValue)});
                    break;
                case 7:
                    Method method5 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method5.invoke(view, new Object[]{Float.valueOf(this.mFloatValue)});
                    break;
                case 8:
                    Method method6 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method6.invoke(view, new Object[]{Float.valueOf(this.mFloatValue)});
                    break;
            }
        } catch (NoSuchMethodException e) {
            Utils.loge("TransitionLayout", e.getMessage());
            Utils.loge("TransitionLayout", " Custom Attribute \"" + name + "\" not found on " + viewClass.getName());
            StringBuilder sb = new StringBuilder();
            sb.append(viewClass.getName());
            sb.append(" must have a method ");
            sb.append(methodName);
            Utils.loge("TransitionLayout", sb.toString());
        } catch (IllegalAccessException e2) {
            Utils.loge("TransitionLayout", " Custom Attribute \"" + name + "\" not found on " + viewClass.getName());
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            Utils.loge("TransitionLayout", " Custom Attribute \"" + name + "\" not found on " + viewClass.getName());
            e3.printStackTrace();
        }
    }

    private static int clamp(int c) {
        int c2 = (c & ((c >> 31) ^ (-1))) - 255;
        return (c2 & (c2 >> 31)) + 255;
    }

    public void setInterpolatedValue(Object view, float[] value) {
        Class<? extends Object> viewClass = view.getClass();
        String methodName = "set" + this.mName;
        try {
            switch (this.mType) {
                case BOOLEAN_TYPE:
                    Method method = viewClass.getMethod(methodName, new Class[]{Boolean.TYPE});
                    boolean z = true;
                    Object[] objArr = new Object[1];
                    if (value[0] <= 0.5f) {
                        z = false;
                    }
                    objArr[0] = Boolean.valueOf(z);
                    method.invoke(view, objArr);
                    return;
                case STRING_TYPE:
                    throw new RuntimeException("unable to interpolate strings " + this.mName);
                case COLOR_TYPE:
                    Method method2 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                    int r = clamp((int) (((float) Math.pow(value[0], 0.45454545454545453d)) * 255.0f));
                    int g = clamp((int) (((float) Math.pow(value[1], 0.45454545454545453d)) * 255.0f));
                    int b = clamp((int) (((float) Math.pow(value[2], 0.45454545454545453d)) * 255.0f));
                    int a = clamp((int) (value[3] * 255.0f));
                    int color = (a << 24) | (r << 16) | (g << 8) | b;
                    method2.invoke(view, new Object[]{Integer.valueOf(color)});
                    return;
                case COLOR_DRAWABLE_TYPE:
                default:
                    return;
                case INT_TYPE:
                    Method method3 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                    method3.invoke(view, new Object[]{Integer.valueOf((int) value[0])});
                    return;
                case FLOAT_TYPE:
                    Method method4 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method4.invoke(view, new Object[]{Float.valueOf(value[0])});
                    return;
                case DIMENSION_TYPE:
                    Method method5 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method5.invoke(view, new Object[]{Float.valueOf(value[0])});
                    return;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            Utils.loge("TransitionLayout", "no method " + methodName + " on View \"" + view.getClass().getName() + "\"");
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            Utils.loge("TransitionLayout", "cannot access method " + methodName + " on View \"" + view.getClass().getName() + "\"");
            e3.printStackTrace();
        }
    }
}
