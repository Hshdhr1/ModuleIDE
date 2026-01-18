package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.TransitionUtils;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public class ChangeImageTransform extends Transition {
    private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
    private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
    private static final String[] sTransitionProperties = {"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};
    private static final TypeEvaluator NULL_MATRIX_EVALUATOR = new 1();
    private static final Property ANIMATED_TRANSFORM_PROPERTY = new 2(Matrix.class, "animatedTransform");

    class 1 implements TypeEvaluator {
        1() {
        }

        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            return null;
        }
    }

    class 2 extends Property {
        2(Class cls, String arg1) {
            super(cls, arg1);
        }

        public void set(ImageView view, Matrix matrix) {
            ImageViewUtils.animateTransform(view, matrix);
        }

        public Matrix get(ImageView object) {
            return null;
        }
    }

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues transitionValues) {
        ImageView imageView = transitionValues.view;
        if ((imageView instanceof ImageView) && imageView.getVisibility() == 0) {
            ImageView imageView2 = imageView;
            Drawable drawable = imageView2.getDrawable();
            if (drawable != null) {
                Map<String, Object> values = transitionValues.values;
                int left = imageView.getLeft();
                int top = imageView.getTop();
                int right = imageView.getRight();
                int bottom = imageView.getBottom();
                Rect bounds = new Rect(left, top, right, bottom);
                values.put("android:changeImageTransform:bounds", bounds);
                values.put("android:changeImageTransform:matrix", copyImageMatrix(imageView2));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @NonNull
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect startBounds = (Rect) startValues.values.get("android:changeImageTransform:bounds");
        Rect endBounds = (Rect) endValues.values.get("android:changeImageTransform:bounds");
        if (startBounds == null || endBounds == null) {
            return null;
        }
        Matrix startMatrix = (Matrix) startValues.values.get("android:changeImageTransform:matrix");
        Matrix endMatrix = (Matrix) endValues.values.get("android:changeImageTransform:matrix");
        boolean matricesEqual = (startMatrix == null && endMatrix == null) || (startMatrix != null && startMatrix.equals(endMatrix));
        if (startBounds.equals(endBounds) && matricesEqual) {
            return null;
        }
        ImageView imageView = (ImageView) endValues.view;
        Drawable drawable = imageView.getDrawable();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        if (drawableWidth <= 0 || drawableHeight <= 0) {
            return createNullAnimator(imageView);
        }
        if (startMatrix == null) {
            startMatrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (endMatrix == null) {
            endMatrix = MatrixUtils.IDENTITY_MATRIX;
        }
        ANIMATED_TRANSFORM_PROPERTY.set(imageView, startMatrix);
        return createMatrixAnimator(imageView, startMatrix, endMatrix);
    }

    @NonNull
    private ObjectAnimator createNullAnimator(@NonNull ImageView imageView) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, new Matrix[]{MatrixUtils.IDENTITY_MATRIX, MatrixUtils.IDENTITY_MATRIX});
    }

    private ObjectAnimator createMatrixAnimator(ImageView imageView, Matrix startMatrix, Matrix endMatrix) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), new Matrix[]{startMatrix, endMatrix});
    }

    @NonNull
    private static Matrix copyImageMatrix(@NonNull ImageView view) {
        Drawable image = view.getDrawable();
        if (image.getIntrinsicWidth() > 0 && image.getIntrinsicHeight() > 0) {
            switch (3.$SwitchMap$android$widget$ImageView$ScaleType[view.getScaleType().ordinal()]) {
                case 1:
                    return fitXYMatrix(view);
                case 2:
                    return centerCropMatrix(view);
                default:
                    return new Matrix(view.getImageMatrix());
            }
        }
        return new Matrix(view.getImageMatrix());
    }

    static /* synthetic */ class 3 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ImageView.ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static Matrix fitXYMatrix(ImageView view) {
        Drawable image = view.getDrawable();
        Matrix matrix = new Matrix();
        matrix.postScale(view.getWidth() / image.getIntrinsicWidth(), view.getHeight() / image.getIntrinsicHeight());
        return matrix;
    }

    private static Matrix centerCropMatrix(ImageView view) {
        Drawable image = view.getDrawable();
        int imageWidth = image.getIntrinsicWidth();
        int imageViewWidth = view.getWidth();
        float scaleX = imageViewWidth / imageWidth;
        int imageHeight = image.getIntrinsicHeight();
        int imageViewHeight = view.getHeight();
        float scaleY = imageViewHeight / imageHeight;
        float maxScale = Math.max(scaleX, scaleY);
        float width = imageWidth * maxScale;
        float height = imageHeight * maxScale;
        int tx = Math.round((imageViewWidth - width) / 2.0f);
        int ty = Math.round((imageViewHeight - height) / 2.0f);
        Matrix matrix = new Matrix();
        matrix.postScale(maxScale, maxScale);
        matrix.postTranslate(tx, ty);
        return matrix;
    }
}
