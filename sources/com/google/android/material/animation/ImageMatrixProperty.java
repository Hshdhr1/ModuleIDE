package com.google.android.material.animation;

import android.graphics.Matrix;
import android.util.Property;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class ImageMatrixProperty extends Property {
    private final Matrix matrix;

    public ImageMatrixProperty() {
        super(Matrix.class, "imageMatrixProperty");
        this.matrix = new Matrix();
    }

    public void set(ImageView object, Matrix value) {
        object.setImageMatrix(value);
    }

    public Matrix get(ImageView object) {
        this.matrix.set(object.getImageMatrix());
        return this.matrix;
    }
}
