package com.google.android.material.color;

import com.google.android.material.R;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class HarmonizedColorsOptions {
    private final int colorAttributeToHarmonizeWith;
    private final HarmonizedColorAttributes colorAttributes;
    private final int[] colorResourceIds;

    /* synthetic */ HarmonizedColorsOptions(Builder x0, 1 x1) {
        this(x0);
    }

    public static HarmonizedColorsOptions createMaterialDefaults() {
        return new Builder().setColorAttributes(HarmonizedColorAttributes.createMaterialDefaults()).build();
    }

    private HarmonizedColorsOptions(Builder builder) {
        this.colorResourceIds = Builder.access$000(builder);
        this.colorAttributes = Builder.access$100(builder);
        this.colorAttributeToHarmonizeWith = Builder.access$200(builder);
    }

    public int[] getColorResourceIds() {
        return this.colorResourceIds;
    }

    public HarmonizedColorAttributes getColorAttributes() {
        return this.colorAttributes;
    }

    public int getColorAttributeToHarmonizeWith() {
        return this.colorAttributeToHarmonizeWith;
    }

    public static class Builder {
        private HarmonizedColorAttributes colorAttributes;
        private int[] colorResourceIds = new int[0];
        private int colorAttributeToHarmonizeWith = R.attr.colorPrimary;

        static /* synthetic */ int[] access$000(Builder x0) {
            return x0.colorResourceIds;
        }

        static /* synthetic */ HarmonizedColorAttributes access$100(Builder x0) {
            return x0.colorAttributes;
        }

        static /* synthetic */ int access$200(Builder x0) {
            return x0.colorAttributeToHarmonizeWith;
        }

        public Builder setColorResourceIds(int[] colorResourceIds) {
            this.colorResourceIds = colorResourceIds;
            return this;
        }

        public Builder setColorAttributes(HarmonizedColorAttributes colorAttributes) {
            this.colorAttributes = colorAttributes;
            return this;
        }

        public Builder setColorAttributeToHarmonizeWith(int colorAttributeToHarmonizeWith) {
            this.colorAttributeToHarmonizeWith = colorAttributeToHarmonizeWith;
            return this;
        }

        public HarmonizedColorsOptions build() {
            return new HarmonizedColorsOptions(this, null);
        }
    }

    int getThemeOverlayResourceId(int defaultThemeOverlay) {
        HarmonizedColorAttributes harmonizedColorAttributes = this.colorAttributes;
        if (harmonizedColorAttributes != null && harmonizedColorAttributes.getThemeOverlay() != 0) {
            return this.colorAttributes.getThemeOverlay();
        }
        return defaultThemeOverlay;
    }
}
