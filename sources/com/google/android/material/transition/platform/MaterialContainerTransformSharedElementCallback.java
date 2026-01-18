package com.google.android.material.transition.platform;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class MaterialContainerTransformSharedElementCallback extends SharedElementCallback {
    private static WeakReference capturedSharedElement;
    private Rect returnEndBounds;
    private boolean entering = true;
    private boolean transparentWindowBackgroundEnabled = true;
    private boolean sharedElementReenterTransitionEnabled = false;
    private ShapeProvider shapeProvider = new ShapeableViewShapeProvider();

    public interface ShapeProvider {
        ShapeAppearanceModel provideShape(View view);
    }

    static /* synthetic */ void access$000(Window x0) {
        removeWindowBackground(x0);
    }

    static /* synthetic */ void access$100(Window x0) {
        restoreWindowBackground(x0);
    }

    static /* synthetic */ WeakReference access$200() {
        return capturedSharedElement;
    }

    static /* synthetic */ WeakReference access$202(WeakReference x0) {
        capturedSharedElement = x0;
        return x0;
    }

    public static class ShapeableViewShapeProvider implements ShapeProvider {
        public ShapeAppearanceModel provideShape(View sharedElement) {
            if (sharedElement instanceof Shapeable) {
                return ((Shapeable) sharedElement).getShapeAppearanceModel();
            }
            return null;
        }
    }

    public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
        capturedSharedElement = new WeakReference(sharedElement);
        return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
    }

    public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        WeakReference weakReference;
        View sharedElement;
        ShapeAppearanceModel shapeAppearanceModel;
        View snapshotView = super.onCreateSnapshotView(context, snapshot);
        if (snapshotView != null && (weakReference = capturedSharedElement) != null && this.shapeProvider != null && (sharedElement = (View) weakReference.get()) != null && (shapeAppearanceModel = this.shapeProvider.provideShape(sharedElement)) != null) {
            snapshotView.setTag(R.id.mtrl_motion_snapshot_view, shapeAppearanceModel);
        }
        return snapshotView;
    }

    public void onMapSharedElements(List list, Map map) {
        View sharedElement;
        Activity activity;
        if (!list.isEmpty() && !map.isEmpty() && (sharedElement = (View) map.get(list.get(0))) != null && (activity = ContextUtils.getActivity(sharedElement.getContext())) != null) {
            Window window = activity.getWindow();
            if (this.entering) {
                setUpEnterTransform(window);
            } else {
                setUpReturnTransform(activity, window);
            }
        }
    }

    public void onSharedElementStart(List list, List list2, List list3) {
        if (!list2.isEmpty() && !list3.isEmpty()) {
            ((View) list2.get(0)).setTag(R.id.mtrl_motion_snapshot_view, list3.get(0));
        }
        if (!this.entering && !list2.isEmpty() && this.returnEndBounds != null) {
            View sharedElement = (View) list2.get(0);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(this.returnEndBounds.width(), 1073741824);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(this.returnEndBounds.height(), 1073741824);
            sharedElement.measure(widthSpec, heightSpec);
            sharedElement.layout(this.returnEndBounds.left, this.returnEndBounds.top, this.returnEndBounds.right, this.returnEndBounds.bottom);
        }
    }

    public void onSharedElementEnd(List list, List list2, List list3) {
        if (!list2.isEmpty() && (((View) list2.get(0)).getTag(R.id.mtrl_motion_snapshot_view) instanceof View)) {
            ((View) list2.get(0)).setTag(R.id.mtrl_motion_snapshot_view, (Object) null);
        }
        if (!this.entering && !list2.isEmpty()) {
            this.returnEndBounds = TransitionUtils.getRelativeBoundsRect((View) list2.get(0));
        }
        this.entering = false;
    }

    public ShapeProvider getShapeProvider() {
        return this.shapeProvider;
    }

    public void setShapeProvider(ShapeProvider shapeProvider) {
        this.shapeProvider = shapeProvider;
    }

    public boolean isTransparentWindowBackgroundEnabled() {
        return this.transparentWindowBackgroundEnabled;
    }

    public void setTransparentWindowBackgroundEnabled(boolean transparentWindowBackgroundEnabled) {
        this.transparentWindowBackgroundEnabled = transparentWindowBackgroundEnabled;
    }

    public boolean isSharedElementReenterTransitionEnabled() {
        return this.sharedElementReenterTransitionEnabled;
    }

    public void setSharedElementReenterTransitionEnabled(boolean sharedElementReenterTransitionEnabled) {
        this.sharedElementReenterTransitionEnabled = sharedElementReenterTransitionEnabled;
    }

    private void setUpEnterTransform(Window window) {
        Transition transition = window.getSharedElementEnterTransition();
        if (transition instanceof MaterialContainerTransform) {
            MaterialContainerTransform transform = (MaterialContainerTransform) transition;
            if (!this.sharedElementReenterTransitionEnabled) {
                window.setSharedElementReenterTransition((Transition) null);
            }
            if (this.transparentWindowBackgroundEnabled) {
                updateBackgroundFadeDuration(window, transform);
                transform.addListener(new 1(window));
            }
        }
    }

    class 1 extends TransitionListenerAdapter {
        final /* synthetic */ Window val$window;

        1(Window window) {
            this.val$window = window;
        }

        public void onTransitionStart(Transition transition) {
            MaterialContainerTransformSharedElementCallback.access$000(this.val$window);
        }

        public void onTransitionEnd(Transition transition) {
            MaterialContainerTransformSharedElementCallback.access$100(this.val$window);
        }
    }

    private void setUpReturnTransform(Activity activity, Window window) {
        Transition transition = window.getSharedElementReturnTransition();
        if (transition instanceof MaterialContainerTransform) {
            MaterialContainerTransform transform = (MaterialContainerTransform) transition;
            transform.setHoldAtEndEnabled(true);
            transform.addListener(new 2(activity));
            if (this.transparentWindowBackgroundEnabled) {
                updateBackgroundFadeDuration(window, transform);
                transform.addListener(new 3(window));
            }
        }
    }

    class 2 extends TransitionListenerAdapter {
        final /* synthetic */ Activity val$activity;

        2(Activity activity) {
            this.val$activity = activity;
        }

        public void onTransitionEnd(Transition transition) {
            View sharedElement;
            if (MaterialContainerTransformSharedElementCallback.access$200() != null && (sharedElement = (View) MaterialContainerTransformSharedElementCallback.access$200().get()) != null) {
                sharedElement.setAlpha(1.0f);
                MaterialContainerTransformSharedElementCallback.access$202(null);
            }
            this.val$activity.finish();
            this.val$activity.overridePendingTransition(0, 0);
        }
    }

    class 3 extends TransitionListenerAdapter {
        final /* synthetic */ Window val$window;

        3(Window window) {
            this.val$window = window;
        }

        public void onTransitionStart(Transition transition) {
            MaterialContainerTransformSharedElementCallback.access$000(this.val$window);
        }
    }

    private static void removeWindowBackground(Window window) {
        Drawable windowBackground = getWindowBackground(window);
        if (windowBackground == null) {
            return;
        }
        windowBackground.mutate().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(0, BlendModeCompat.CLEAR));
    }

    private static void restoreWindowBackground(Window window) {
        Drawable windowBackground = getWindowBackground(window);
        if (windowBackground == null) {
            return;
        }
        windowBackground.mutate().clearColorFilter();
    }

    private static Drawable getWindowBackground(Window window) {
        return window.getDecorView().getBackground();
    }

    private static void updateBackgroundFadeDuration(Window window, MaterialContainerTransform transform) {
        if (transform.getDuration() >= 0) {
            window.setTransitionBackgroundFadeDuration(transform.getDuration());
        }
    }
}
