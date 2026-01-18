package com.google.android.material.color;

import android.app.Activity;
import com.google.android.material.color.DynamicColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class DynamicColorsOptions {
    private static final DynamicColors.Precondition ALWAYS_ALLOW = new 1();
    private static final DynamicColors.OnAppliedCallback NO_OP_CALLBACK = new 2();
    private final DynamicColors.OnAppliedCallback onAppliedCallback;
    private final DynamicColors.Precondition precondition;
    private final int themeOverlay;

    /* synthetic */ DynamicColorsOptions(Builder x0, 1 x1) {
        this(x0);
    }

    static /* synthetic */ DynamicColors.Precondition access$300() {
        return ALWAYS_ALLOW;
    }

    static /* synthetic */ DynamicColors.OnAppliedCallback access$400() {
        return NO_OP_CALLBACK;
    }

    class 1 implements DynamicColors.Precondition {
        1() {
        }

        public boolean shouldApplyDynamicColors(Activity activity, int theme) {
            return true;
        }
    }

    class 2 implements DynamicColors.OnAppliedCallback {
        2() {
        }

        public void onApplied(Activity activity) {
        }
    }

    private DynamicColorsOptions(Builder builder) {
        this.themeOverlay = Builder.access$000(builder);
        this.precondition = Builder.access$100(builder);
        this.onAppliedCallback = Builder.access$200(builder);
    }

    public int getThemeOverlay() {
        return this.themeOverlay;
    }

    public DynamicColors.Precondition getPrecondition() {
        return this.precondition;
    }

    public DynamicColors.OnAppliedCallback getOnAppliedCallback() {
        return this.onAppliedCallback;
    }

    public static class Builder {
        private int themeOverlay;
        private DynamicColors.Precondition precondition = DynamicColorsOptions.access$300();
        private DynamicColors.OnAppliedCallback onAppliedCallback = DynamicColorsOptions.access$400();

        static /* synthetic */ int access$000(Builder x0) {
            return x0.themeOverlay;
        }

        static /* synthetic */ DynamicColors.Precondition access$100(Builder x0) {
            return x0.precondition;
        }

        static /* synthetic */ DynamicColors.OnAppliedCallback access$200(Builder x0) {
            return x0.onAppliedCallback;
        }

        public Builder setThemeOverlay(int themeOverlay) {
            this.themeOverlay = themeOverlay;
            return this;
        }

        public Builder setPrecondition(DynamicColors.Precondition precondition) {
            this.precondition = precondition;
            return this;
        }

        public Builder setOnAppliedCallback(DynamicColors.OnAppliedCallback onAppliedCallback) {
            this.onAppliedCallback = onAppliedCallback;
            return this;
        }

        public DynamicColorsOptions build() {
            return new DynamicColorsOptions(this, null);
        }
    }
}
