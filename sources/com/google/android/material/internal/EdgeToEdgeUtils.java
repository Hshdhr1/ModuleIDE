package com.google.android.material.internal;

import android.content.Context;
import android.os.Build;
import android.view.Window;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.color.MaterialColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class EdgeToEdgeUtils {
    private static final int EDGE_TO_EDGE_BAR_ALPHA = 128;

    private EdgeToEdgeUtils() {
    }

    public static void applyEdgeToEdge(Window window, boolean edgeToEdgeEnabled) {
        applyEdgeToEdge(window, edgeToEdgeEnabled, null, null);
    }

    public static void applyEdgeToEdge(Window window, boolean edgeToEdgeEnabled, Integer statusBarOverlapBackgroundColor, Integer navigationBarOverlapBackgroundColor) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        boolean useDefaultBackgroundColorForStatusBar = statusBarOverlapBackgroundColor == null || statusBarOverlapBackgroundColor.intValue() == 0;
        boolean useDefaultBackgroundColorForNavigationBar = navigationBarOverlapBackgroundColor == null || navigationBarOverlapBackgroundColor.intValue() == 0;
        if (useDefaultBackgroundColorForStatusBar || useDefaultBackgroundColorForNavigationBar) {
            int defaultBackgroundColor = MaterialColors.getColor(window.getContext(), 16842801, -16777216);
            if (useDefaultBackgroundColorForStatusBar) {
                statusBarOverlapBackgroundColor = Integer.valueOf(defaultBackgroundColor);
            }
            if (useDefaultBackgroundColorForNavigationBar) {
                navigationBarOverlapBackgroundColor = Integer.valueOf(defaultBackgroundColor);
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, !edgeToEdgeEnabled);
        int statusBarColor = getStatusBarColor(window.getContext(), edgeToEdgeEnabled);
        int navigationBarColor = getNavigationBarColor(window.getContext(), edgeToEdgeEnabled);
        window.setStatusBarColor(statusBarColor);
        window.setNavigationBarColor(navigationBarColor);
        boolean isLightStatusBar = isUsingLightSystemBar(statusBarColor, MaterialColors.isColorLight(statusBarOverlapBackgroundColor.intValue()));
        boolean isLightNavigationBar = isUsingLightSystemBar(navigationBarColor, MaterialColors.isColorLight(navigationBarOverlapBackgroundColor.intValue()));
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(window, window.getDecorView());
        if (insetsController != null) {
            insetsController.setAppearanceLightStatusBars(isLightStatusBar);
            insetsController.setAppearanceLightNavigationBars(isLightNavigationBar);
        }
    }

    private static int getStatusBarColor(Context context, boolean isEdgeToEdgeEnabled) {
        if (isEdgeToEdgeEnabled && Build.VERSION.SDK_INT < 23) {
            int opaqueStatusBarColor = MaterialColors.getColor(context, 16843857, -16777216);
            return ColorUtils.setAlphaComponent(opaqueStatusBarColor, 128);
        }
        if (isEdgeToEdgeEnabled) {
            return 0;
        }
        return MaterialColors.getColor(context, 16843857, -16777216);
    }

    private static int getNavigationBarColor(Context context, boolean isEdgeToEdgeEnabled) {
        if (isEdgeToEdgeEnabled && Build.VERSION.SDK_INT < 27) {
            int opaqueNavBarColor = MaterialColors.getColor(context, 16843858, -16777216);
            return ColorUtils.setAlphaComponent(opaqueNavBarColor, 128);
        }
        if (isEdgeToEdgeEnabled) {
            return 0;
        }
        return MaterialColors.getColor(context, 16843858, -16777216);
    }

    private static boolean isUsingLightSystemBar(int systemBarColor, boolean isLightBackground) {
        return MaterialColors.isColorLight(systemBarColor) || (systemBarColor == 0 && isLightBackground);
    }
}
