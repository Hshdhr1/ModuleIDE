package androidx.transition;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Property;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class PathProperty extends Property {
    private float mCurrentFraction;
    private final float mPathLength;
    private final PathMeasure mPathMeasure;
    private final PointF mPointF;
    private final float[] mPosition;
    private final Property mProperty;

    PathProperty(Property property, Path path) {
        super(Float.class, property.getName());
        this.mPosition = new float[2];
        this.mPointF = new PointF();
        this.mProperty = property;
        this.mPathMeasure = new PathMeasure(path, false);
        this.mPathLength = this.mPathMeasure.getLength();
    }

    public Float get(Object obj) {
        return Float.valueOf(this.mCurrentFraction);
    }

    public void set(Object obj, Float fraction) {
        this.mCurrentFraction = fraction.floatValue();
        this.mPathMeasure.getPosTan(this.mPathLength * fraction.floatValue(), this.mPosition, (float[]) null);
        this.mPointF.x = this.mPosition[0];
        this.mPointF.y = this.mPosition[1];
        this.mProperty.set(obj, this.mPointF);
    }
}
