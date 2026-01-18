package com.google.android.material.color;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import com.google.android.material.R;
import com.google.android.material.color.DynamicColorsOptions;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class DynamicColors {
    private static final DeviceSupportCondition DEFAULT_DEVICE_SUPPORT_CONDITION;
    private static final Map DYNAMIC_COLOR_SUPPORTED_BRANDS;
    private static final Map DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS;
    private static final int[] DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE = {R.attr.dynamicColorThemeOverlay};
    private static final DeviceSupportCondition SAMSUNG_DEVICE_SUPPORT_CONDITION;
    private static final int USE_DEFAULT_THEME_OVERLAY = 0;

    private interface DeviceSupportCondition {
        boolean isSupported();
    }

    public interface OnAppliedCallback {
        void onApplied(Activity activity);
    }

    public interface Precondition {
        boolean shouldApplyDynamicColors(Activity activity, int i);
    }

    static /* synthetic */ void access$000(Activity x0, int x1, Precondition x2, OnAppliedCallback x3) {
        applyToActivityIfAvailable(x0, x1, x2, x3);
    }

    static {
        1 r0 = new 1();
        DEFAULT_DEVICE_SUPPORT_CONDITION = r0;
        2 r1 = new 2();
        SAMSUNG_DEVICE_SUPPORT_CONDITION = r1;
        HashMap hashMap = new HashMap();
        hashMap.put("google", r0);
        hashMap.put("hmd global", r0);
        hashMap.put("infinix", r0);
        hashMap.put("infinix mobility limited", r0);
        hashMap.put("itel", r0);
        hashMap.put("kyocera", r0);
        hashMap.put("lenovo", r0);
        hashMap.put("lge", r0);
        hashMap.put("motorola", r0);
        hashMap.put("nothing", r0);
        hashMap.put("oneplus", r0);
        hashMap.put("oppo", r0);
        hashMap.put("realme", r0);
        hashMap.put("robolectric", r0);
        hashMap.put("samsung", r1);
        hashMap.put("sharp", r0);
        hashMap.put("sony", r0);
        hashMap.put("tcl", r0);
        hashMap.put("tecno", r0);
        hashMap.put("tecno mobile limited", r0);
        hashMap.put("vivo", r0);
        hashMap.put("xiaomi", r0);
        DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("asus", r0);
        hashMap2.put("jio", r0);
        DYNAMIC_COLOR_SUPPORTED_BRANDS = Collections.unmodifiableMap(hashMap2);
    }

    class 1 implements DeviceSupportCondition {
        1() {
        }

        public boolean isSupported() {
            return true;
        }
    }

    class 2 implements DeviceSupportCondition {
        private Long version;

        2() {
        }

        public boolean isSupported() {
            if (this.version == null) {
                try {
                    Method method = Build.class.getDeclaredMethod("getLong", new Class[]{String.class});
                    method.setAccessible(true);
                    this.version = Long.valueOf(((Long) method.invoke((Object) null, new Object[]{"ro.build.version.oneui"})).longValue());
                } catch (Exception e) {
                    this.version = -1L;
                }
            }
            return this.version.longValue() >= 40100;
        }
    }

    private DynamicColors() {
    }

    public static void applyToActivitiesIfAvailable(Application application) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(Application application, int theme) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setThemeOverlay(theme).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(Application application, Precondition precondition) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setPrecondition(precondition).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(Application application, int theme, Precondition precondition) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setThemeOverlay(theme).setPrecondition(precondition).build());
    }

    public static void applyToActivitiesIfAvailable(Application application, DynamicColorsOptions dynamicColorsOptions) {
        application.registerActivityLifecycleCallbacks(new DynamicColorsActivityLifecycleCallbacks(dynamicColorsOptions));
    }

    @Deprecated
    public static void applyIfAvailable(Activity activity) {
        applyToActivityIfAvailable(activity);
    }

    @Deprecated
    public static void applyIfAvailable(Activity activity, int theme) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().setThemeOverlay(theme).build());
    }

    @Deprecated
    public static void applyIfAvailable(Activity activity, Precondition precondition) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().setPrecondition(precondition).build());
    }

    public static void applyToActivityIfAvailable(Activity activity) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().build());
    }

    public static void applyToActivityIfAvailable(Activity activity, DynamicColorsOptions dynamicColorsOptions) {
        applyToActivityIfAvailable(activity, dynamicColorsOptions.getThemeOverlay(), dynamicColorsOptions.getPrecondition(), dynamicColorsOptions.getOnAppliedCallback());
    }

    private static void applyToActivityIfAvailable(Activity activity, int theme, Precondition precondition, OnAppliedCallback onAppliedCallback) {
        if (!isDynamicColorAvailable()) {
            return;
        }
        if (theme == 0) {
            theme = getDefaultThemeOverlay(activity);
        }
        if (theme != 0 && precondition.shouldApplyDynamicColors(activity, theme)) {
            ThemeUtils.applyThemeOverlay(activity, theme);
            onAppliedCallback.onApplied(activity);
        }
    }

    public static Context wrapContextIfAvailable(Context originalContext) {
        return wrapContextIfAvailable(originalContext, 0);
    }

    public static Context wrapContextIfAvailable(Context originalContext, int theme) {
        if (!isDynamicColorAvailable()) {
            return originalContext;
        }
        if (theme == 0) {
            theme = getDefaultThemeOverlay(originalContext);
        }
        return theme == 0 ? originalContext : new ContextThemeWrapper(originalContext, theme);
    }

    public static boolean isDynamicColorAvailable() {
        if (Build.VERSION.SDK_INT < 31) {
            return false;
        }
        DeviceSupportCondition deviceSupportCondition = (DeviceSupportCondition) DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS.get(Build.MANUFACTURER.toLowerCase());
        if (deviceSupportCondition == null) {
            deviceSupportCondition = (DeviceSupportCondition) DYNAMIC_COLOR_SUPPORTED_BRANDS.get(Build.BRAND.toLowerCase());
        }
        return deviceSupportCondition != null && deviceSupportCondition.isSupported();
    }

    private static int getDefaultThemeOverlay(Context context) {
        TypedArray dynamicColorAttributes = context.obtainStyledAttributes(DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE);
        int theme = dynamicColorAttributes.getResourceId(0, 0);
        dynamicColorAttributes.recycle();
        return theme;
    }

    private static class DynamicColorsActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private final DynamicColorsOptions dynamicColorsOptions;

        DynamicColorsActivityLifecycleCallbacks(DynamicColorsOptions options) {
            this.dynamicColorsOptions = options;
        }

        public void onActivityPreCreated(Activity activity, Bundle savedInstanceState) {
            DynamicColors.access$000(activity, this.dynamicColorsOptions.getThemeOverlay(), this.dynamicColorsOptions.getPrecondition(), this.dynamicColorsOptions.getOnAppliedCallback());
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }
}
