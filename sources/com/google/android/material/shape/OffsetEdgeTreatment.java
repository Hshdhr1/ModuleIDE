package com.google.android.material.shape;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class OffsetEdgeTreatment extends EdgeTreatment {
    private final float offset;
    private final EdgeTreatment other;

    public OffsetEdgeTreatment(EdgeTreatment other, float offset) {
        this.other = other;
        this.offset = offset;
    }

    public void getEdgePath(float length, float center, float interpolation, ShapePath shapePath) {
        this.other.getEdgePath(length, center - this.offset, interpolation, shapePath);
    }

    boolean forceIntersection() {
        return this.other.forceIntersection();
    }
}
