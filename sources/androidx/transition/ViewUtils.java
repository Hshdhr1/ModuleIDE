package androidx.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Property;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class ViewUtils {
    static final Property CLIP_BOUNDS;
    private static final ViewUtilsBase IMPL;
    private static final String TAG = "ViewUtils";
    static final Property TRANSITION_ALPHA;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            IMPL = new ViewUtilsApi29();
        } else if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new ViewUtilsApi23();
        } else if (Build.VERSION.SDK_INT >= 22) {
            IMPL = new ViewUtilsApi22();
        } else if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new ViewUtilsApi21();
        } else if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new ViewUtilsApi19();
        } else {
            IMPL = new ViewUtilsBase();
        }
        TRANSITION_ALPHA = new 1(Float.class, "translationAlpha");
        CLIP_BOUNDS = new 2(Rect.class, "clipBounds");
    }

    class 1 extends Property {
        1(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Float get(View view) {
            return Float.valueOf(ViewUtils.getTransitionAlpha(view));
        }

        public void set(View view, Float alpha) {
            ViewUtils.setTransitionAlpha(view, alpha.floatValue());
        }
    }

    class 2 extends Property {
        2(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Rect get(View view) {
            return ViewCompat.getClipBounds(view);
        }

        public void set(View view, Rect clipBounds) {
            ViewCompat.setClipBounds(view, clipBounds);
        }
    }

    static ViewOverlayImpl getOverlay(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 18 ? new ViewOverlayApi18(view) : ViewOverlayApi14.createFrom(view);
    }

    static WindowIdImpl getWindowId(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 18 ? new WindowIdApi18(view) : new WindowIdApi14(view.getWindowToken());
    }

    static void setTransitionAlpha(@NonNull View view, float alpha) {
        IMPL.setTransitionAlpha(view, alpha);
    }

    static float getTransitionAlpha(@NonNull View view) {
        return IMPL.getTransitionAlpha(view);
    }

    static void saveNonTransitionAlpha(@NonNull View view) {
        IMPL.saveNonTransitionAlpha(view);
    }

    static void clearNonTransitionAlpha(@NonNull View view) {
        IMPL.clearNonTransitionAlpha(view);
    }

    static void setTransitionVisibility(@NonNull View view, int visibility) {
        IMPL.setTransitionVisibility(view, visibility);
    }

    static void transformMatrixToGlobal(@NonNull View view, @NonNull Matrix matrix) {
        IMPL.transformMatrixToGlobal(view, matrix);
    }

    static void transformMatrixToLocal(@NonNull View view, @NonNull Matrix matrix) {
        IMPL.transformMatrixToLocal(view, matrix);
    }

    static void setAnimationMatrix(@NonNull View v, @Nullable Matrix m) {
        IMPL.setAnimationMatrix(v, m);
    }

    static void setLeftTopRightBottom(@NonNull View v, int left, int top, int right, int bottom) {
        IMPL.setLeftTopRightBottom(v, left, top, right, bottom);
    }

    private ViewUtils() {
    }
}
