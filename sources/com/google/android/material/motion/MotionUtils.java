package com.google.android.material.motion;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.TypedValue;
import androidx.core.graphics.PathParser;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class MotionUtils {
    private static final String EASING_TYPE_CUBIC_BEZIER = "cubic-bezier";
    private static final String EASING_TYPE_FORMAT_END = ")";
    private static final String EASING_TYPE_FORMAT_START = "(";
    private static final String EASING_TYPE_PATH = "path";

    private MotionUtils() {
    }

    public static int resolveThemeDuration(Context context, int attrResId, int defaultDuration) {
        return MaterialAttributes.resolveInteger(context, attrResId, defaultDuration);
    }

    public static TimeInterpolator resolveThemeInterpolator(Context context, int attrResId, TimeInterpolator defaultInterpolator) {
        TypedValue easingValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrResId, easingValue, true)) {
            if (easingValue.type != 3) {
                throw new IllegalArgumentException("Motion easing theme attribute must be a string");
            }
            String easingString = String.valueOf(easingValue.string);
            if (isEasingType(easingString, "cubic-bezier")) {
                String controlPointsString = getEasingContent(easingString, "cubic-bezier");
                String[] controlPoints = controlPointsString.split(",");
                if (controlPoints.length != 4) {
                    throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + controlPoints.length);
                }
                float controlX1 = getControlPoint(controlPoints, 0);
                float controlY1 = getControlPoint(controlPoints, 1);
                float controlX2 = getControlPoint(controlPoints, 2);
                float controlY2 = getControlPoint(controlPoints, 3);
                return PathInterpolatorCompat.create(controlX1, controlY1, controlX2, controlY2);
            }
            if (isEasingType(easingString, "path")) {
                String path = getEasingContent(easingString, "path");
                return PathInterpolatorCompat.create(PathParser.createPathFromPathData(path));
            }
            throw new IllegalArgumentException("Invalid motion easing type: " + easingString);
        }
        return defaultInterpolator;
    }

    private static boolean isEasingType(String easingString, String easingType) {
        return easingString.startsWith(new StringBuilder().append(easingType).append("(").toString()) && easingString.endsWith(")");
    }

    private static String getEasingContent(String easingString, String easingType) {
        return easingString.substring(easingType.length() + "(".length(), easingString.length() - ")".length());
    }

    private static float getControlPoint(String[] controlPoints, int index) {
        float controlPoint = Float.parseFloat(controlPoints[index]);
        if (controlPoint < 0.0f || controlPoint > 1.0f) {
            throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + controlPoint);
        }
        return controlPoint;
    }
}
