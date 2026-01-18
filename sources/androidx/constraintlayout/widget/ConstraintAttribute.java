package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class ConstraintAttribute {
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
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
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
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
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
                int HSVToColor = Color.HSVToColor(value);
                this.mColorValue = HSVToColor;
                this.mColorValue = (HSVToColor & 16777215) | (clamp((int) (value[3] * 255.0f)) << 24);
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

    public boolean diff(ConstraintAttribute constraintAttribute) {
        if (constraintAttribute == null || this.mType != constraintAttribute.mType) {
            return false;
        }
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
            case 1:
            case 6:
                if (this.mIntegerValue == constraintAttribute.mIntegerValue) {
                }
                break;
            case 2:
                if (this.mBooleanValue == constraintAttribute.mBooleanValue) {
                }
                break;
            case 3:
                if (this.mIntegerValue == constraintAttribute.mIntegerValue) {
                }
                break;
            case 4:
            case 5:
                if (this.mColorValue == constraintAttribute.mColorValue) {
                }
                break;
            case 7:
                if (this.mFloatValue == constraintAttribute.mFloatValue) {
                }
                break;
            case 8:
                if (this.mFloatValue == constraintAttribute.mFloatValue) {
                }
                break;
        }
        return false;
    }

    public ConstraintAttribute(String name, AttributeType attributeType) {
        this.mMethod = false;
        this.mName = name;
        this.mType = attributeType;
    }

    public ConstraintAttribute(String name, AttributeType attributeType, Object value, boolean method) {
        this.mMethod = false;
        this.mName = name;
        this.mType = attributeType;
        this.mMethod = method;
        setValue(value);
    }

    public ConstraintAttribute(ConstraintAttribute source, Object value) {
        this.mMethod = false;
        this.mName = source.mName;
        this.mType = source.mType;
        setValue(value);
    }

    public void setValue(Object value) {
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
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

    public static HashMap extractAttributes(HashMap base, View view) {
        HashMap<String, ConstraintAttribute> ret = new HashMap<>();
        Class<? extends View> viewClass = view.getClass();
        for (String name : base.keySet()) {
            ConstraintAttribute constraintAttribute = (ConstraintAttribute) base.get(name);
            try {
                if (name.equals("BackgroundColor")) {
                    ColorDrawable viewColor = view.getBackground();
                    Object val = Integer.valueOf(viewColor.getColor());
                    ret.put(name, new ConstraintAttribute(constraintAttribute, val));
                } else {
                    String valueOf = String.valueOf(name);
                    Method method = viewClass.getMethod(valueOf.length() != 0 ? "getMap".concat(valueOf) : new String("getMap"), new Class[0]);
                    Object val2 = method.invoke(view, new Object[0]);
                    ret.put(name, new ConstraintAttribute(constraintAttribute, val2));
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
        return ret;
    }

    public static void setAttributes(View view, HashMap map) {
        Class<? extends View> viewClass = view.getClass();
        for (String name : map.keySet()) {
            ConstraintAttribute constraintAttribute = (ConstraintAttribute) map.get(name);
            String methodName = name;
            if (!constraintAttribute.mMethod) {
                String valueOf = String.valueOf(methodName);
                methodName = valueOf.length() != 0 ? "set".concat(valueOf) : new String("set");
            }
            try {
                switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[constraintAttribute.mType.ordinal()]) {
                    case 1:
                        Method method = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                        method.invoke(view, new Object[]{Integer.valueOf(constraintAttribute.mIntegerValue)});
                        break;
                    case 2:
                        Method method2 = viewClass.getMethod(methodName, new Class[]{Boolean.TYPE});
                        method2.invoke(view, new Object[]{Boolean.valueOf(constraintAttribute.mBooleanValue)});
                        break;
                    case 3:
                        Method method3 = viewClass.getMethod(methodName, new Class[]{CharSequence.class});
                        method3.invoke(view, new Object[]{constraintAttribute.mStringValue});
                        break;
                    case 4:
                        Method method4 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                        method4.invoke(view, new Object[]{Integer.valueOf(constraintAttribute.mColorValue)});
                        break;
                    case 5:
                        Method method5 = viewClass.getMethod(methodName, new Class[]{Drawable.class});
                        ColorDrawable drawable = new ColorDrawable();
                        drawable.setColor(constraintAttribute.mColorValue);
                        method5.invoke(view, new Object[]{drawable});
                        break;
                    case 6:
                        Method method6 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                        method6.invoke(view, new Object[]{Integer.valueOf(constraintAttribute.mIntegerValue)});
                        break;
                    case 7:
                        Method method7 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                        method7.invoke(view, new Object[]{Float.valueOf(constraintAttribute.mFloatValue)});
                        break;
                    case 8:
                        Method method8 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                        method8.invoke(view, new Object[]{Float.valueOf(constraintAttribute.mFloatValue)});
                        break;
                }
            } catch (InvocationTargetException e) {
                String name2 = viewClass.getName();
                StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 34 + String.valueOf(name2).length());
                sb.append(" Custom Attribute \"");
                sb.append(name);
                sb.append("\" not found on ");
                sb.append(name2);
                Log.e("TransitionLayout", sb.toString());
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                String name3 = viewClass.getName();
                StringBuilder sb2 = new StringBuilder(String.valueOf(name).length() + 34 + String.valueOf(name3).length());
                sb2.append(" Custom Attribute \"");
                sb2.append(name);
                sb2.append("\" not found on ");
                sb2.append(name3);
                Log.e("TransitionLayout", sb2.toString());
                e2.printStackTrace();
            } catch (NoSuchMethodException e3) {
                Log.e("TransitionLayout", e3.getMessage());
                String name4 = viewClass.getName();
                StringBuilder sb3 = new StringBuilder(String.valueOf(name).length() + 34 + String.valueOf(name4).length());
                sb3.append(" Custom Attribute \"");
                sb3.append(name);
                sb3.append("\" not found on ");
                sb3.append(name4);
                Log.e("TransitionLayout", sb3.toString());
                String name5 = viewClass.getName();
                StringBuilder sb4 = new StringBuilder(String.valueOf(name5).length() + 20 + String.valueOf(methodName).length());
                sb4.append(name5);
                sb4.append(" must have a method ");
                sb4.append(methodName);
                Log.e("TransitionLayout", sb4.toString());
            }
        }
    }

    public void applyCustom(View view) {
        Class<? extends View> viewClass = view.getClass();
        String name = this.mName;
        String methodName = name;
        if (!this.mMethod) {
            String valueOf = String.valueOf(methodName);
            methodName = valueOf.length() != 0 ? "set".concat(valueOf) : new String("set");
        }
        try {
            switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
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
                case 5:
                    Method method5 = viewClass.getMethod(methodName, new Class[]{Drawable.class});
                    ColorDrawable drawable = new ColorDrawable();
                    drawable.setColor(this.mColorValue);
                    method5.invoke(view, new Object[]{drawable});
                    break;
                case 7:
                    Method method6 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method6.invoke(view, new Object[]{Float.valueOf(this.mFloatValue)});
                    break;
                case 8:
                    Method method7 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method7.invoke(view, new Object[]{Float.valueOf(this.mFloatValue)});
                    break;
            }
        } catch (InvocationTargetException e) {
            String name2 = viewClass.getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 34 + String.valueOf(name2).length());
            sb.append(" Custom Attribute \"");
            sb.append(name);
            sb.append("\" not found on ");
            sb.append(name2);
            Log.e("TransitionLayout", sb.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            String name3 = viewClass.getName();
            StringBuilder sb2 = new StringBuilder(String.valueOf(name).length() + 34 + String.valueOf(name3).length());
            sb2.append(" Custom Attribute \"");
            sb2.append(name);
            sb2.append("\" not found on ");
            sb2.append(name3);
            Log.e("TransitionLayout", sb2.toString());
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            Log.e("TransitionLayout", e3.getMessage());
            String name4 = viewClass.getName();
            StringBuilder sb3 = new StringBuilder(String.valueOf(name).length() + 34 + String.valueOf(name4).length());
            sb3.append(" Custom Attribute \"");
            sb3.append(name);
            sb3.append("\" not found on ");
            sb3.append(name4);
            Log.e("TransitionLayout", sb3.toString());
            String name5 = viewClass.getName();
            StringBuilder sb4 = new StringBuilder(String.valueOf(name5).length() + 20 + String.valueOf(methodName).length());
            sb4.append(name5);
            sb4.append(" must have a method ");
            sb4.append(methodName);
            Log.e("TransitionLayout", sb4.toString());
        }
    }

    private static int clamp(int c) {
        int c2 = (c & ((c >> 31) ^ (-1))) - 255;
        return (c2 & (c2 >> 31)) + 255;
    }

    public void setInterpolatedValue(View view, float[] value) {
        Class<? extends View> viewClass = view.getClass();
        String valueOf = String.valueOf(this.mName);
        String methodName = valueOf.length() != 0 ? "set".concat(valueOf) : new String("set");
        try {
            switch (this.mType) {
                case BOOLEAN_TYPE:
                    Method method = viewClass.getMethod(methodName, new Class[]{Boolean.TYPE});
                    Object[] objArr = new Object[1];
                    objArr[0] = Boolean.valueOf(value[0] > 0.5f);
                    method.invoke(view, objArr);
                    return;
                case STRING_TYPE:
                    String valueOf2 = String.valueOf(this.mName);
                    throw new RuntimeException(valueOf2.length() != 0 ? "unable to interpolate strings ".concat(valueOf2) : new String("unable to interpolate strings "));
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
                    Method method3 = viewClass.getMethod(methodName, new Class[]{Drawable.class});
                    int r2 = clamp((int) (((float) Math.pow(value[0], 0.45454545454545453d)) * 255.0f));
                    int g2 = clamp((int) (((float) Math.pow(value[1], 0.45454545454545453d)) * 255.0f));
                    int b2 = clamp((int) (((float) Math.pow(value[2], 0.45454545454545453d)) * 255.0f));
                    int a2 = clamp((int) (value[3] * 255.0f));
                    int color2 = (a2 << 24) | (r2 << 16) | (g2 << 8) | b2;
                    ColorDrawable drawable = new ColorDrawable();
                    drawable.setColor(color2);
                    method3.invoke(view, new Object[]{drawable});
                    return;
                case INT_TYPE:
                    Method method4 = viewClass.getMethod(methodName, new Class[]{Integer.TYPE});
                    method4.invoke(view, new Object[]{Integer.valueOf((int) value[0])});
                    return;
                case FLOAT_TYPE:
                    Method method5 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method5.invoke(view, new Object[]{Float.valueOf(value[0])});
                    return;
                case DIMENSION_TYPE:
                    Method method6 = viewClass.getMethod(methodName, new Class[]{Float.TYPE});
                    method6.invoke(view, new Object[]{Float.valueOf(value[0])});
                    return;
                default:
                    return;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            String name = Debug.getName(view);
            StringBuilder sb = new StringBuilder(String.valueOf(methodName).length() + 21 + String.valueOf(name).length());
            sb.append("no method ");
            sb.append(methodName);
            sb.append(" on View \"");
            sb.append(name);
            sb.append("\"");
            Log.e("TransitionLayout", sb.toString());
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            String name2 = Debug.getName(view);
            StringBuilder sb2 = new StringBuilder(String.valueOf(methodName).length() + 32 + String.valueOf(name2).length());
            sb2.append("cannot access method ");
            sb2.append(methodName);
            sb2.append(" on View \"");
            sb2.append(name2);
            sb2.append("\"");
            Log.e("TransitionLayout", sb2.toString());
            e3.printStackTrace();
        }
    }

    public static void parse(Context context, XmlPullParser parser, HashMap custom) {
        AttributeSet attributeSet = Xml.asAttributeSet(parser);
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.CustomAttribute);
        String name = null;
        boolean method = false;
        Object value = null;
        AttributeType type = null;
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomAttribute_attributeName) {
                name = a.getString(attr);
                if (name != null && name.length() > 0) {
                    char upperCase = Character.toUpperCase(name.charAt(0));
                    String substring = name.substring(1);
                    StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 1);
                    sb.append(upperCase);
                    sb.append(substring);
                    name = sb.toString();
                }
            } else if (attr == R.styleable.CustomAttribute_methodName) {
                method = true;
                name = a.getString(attr);
            } else if (attr == R.styleable.CustomAttribute_customBoolean) {
                value = Boolean.valueOf(a.getBoolean(attr, false));
                type = AttributeType.BOOLEAN_TYPE;
            } else if (attr == R.styleable.CustomAttribute_customColorValue) {
                type = AttributeType.COLOR_TYPE;
                value = Integer.valueOf(a.getColor(attr, 0));
            } else if (attr == R.styleable.CustomAttribute_customColorDrawableValue) {
                type = AttributeType.COLOR_DRAWABLE_TYPE;
                value = Integer.valueOf(a.getColor(attr, 0));
            } else if (attr == R.styleable.CustomAttribute_customPixelDimension) {
                type = AttributeType.DIMENSION_TYPE;
                value = Float.valueOf(TypedValue.applyDimension(1, a.getDimension(attr, 0.0f), context.getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.CustomAttribute_customDimension) {
                type = AttributeType.DIMENSION_TYPE;
                value = Float.valueOf(a.getDimension(attr, 0.0f));
            } else if (attr == R.styleable.CustomAttribute_customFloatValue) {
                type = AttributeType.FLOAT_TYPE;
                value = Float.valueOf(a.getFloat(attr, Float.NaN));
            } else if (attr == R.styleable.CustomAttribute_customIntegerValue) {
                type = AttributeType.INT_TYPE;
                value = Integer.valueOf(a.getInteger(attr, -1));
            } else if (attr == R.styleable.CustomAttribute_customStringValue) {
                type = AttributeType.STRING_TYPE;
                value = a.getString(attr);
            } else if (attr == R.styleable.CustomAttribute_customReference) {
                type = AttributeType.REFERENCE_TYPE;
                int tmp = a.getResourceId(attr, -1);
                if (tmp == -1) {
                    tmp = a.getInt(attr, -1);
                }
                value = Integer.valueOf(tmp);
            }
        }
        if (name != null && value != null) {
            custom.put(name, new ConstraintAttribute(name, type, value, method));
        }
        a.recycle();
    }
}
