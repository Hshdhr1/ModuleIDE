package com.google.android.material.slider;

import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class BasicLabelFormatter implements LabelFormatter {
    private static final int BILLION = 1000000000;
    private static final int MILLION = 1000000;
    private static final int THOUSAND = 1000;
    private static final long TRILLION = 1000000000000L;

    public String getFormattedValue(float value) {
        return value >= 1.0E12f ? String.format(Locale.US, "%.1fT", new Object[]{Float.valueOf(value / 1.0E12f)}) : value >= 1.0E9f ? String.format(Locale.US, "%.1fB", new Object[]{Float.valueOf(value / 1.0E9f)}) : value >= 1000000.0f ? String.format(Locale.US, "%.1fM", new Object[]{Float.valueOf(value / 1000000.0f)}) : value >= 1000.0f ? String.format(Locale.US, "%.1fK", new Object[]{Float.valueOf(value / 1000.0f)}) : String.format(Locale.US, "%.0f", new Object[]{Float.valueOf(value)});
    }
}
