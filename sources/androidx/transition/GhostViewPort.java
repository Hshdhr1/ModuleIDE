package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

@SuppressLint({"ViewConstructor"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class GhostViewPort extends ViewGroup implements GhostView {

    @Nullable
    private Matrix mMatrix;
    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    int mReferences;
    ViewGroup mStartParent;
    View mStartView;
    final View mView;

    class 1 implements ViewTreeObserver.OnPreDrawListener {
        1() {
        }

        public boolean onPreDraw() {
            ViewCompat.postInvalidateOnAnimation(GhostViewPort.this);
            if (GhostViewPort.this.mStartParent != null && GhostViewPort.this.mStartView != null) {
                GhostViewPort.this.mStartParent.endViewTransition(GhostViewPort.this.mStartView);
                ViewCompat.postInvalidateOnAnimation(GhostViewPort.this.mStartParent);
                GhostViewPort.this.mStartParent = null;
                GhostViewPort.this.mStartView = null;
                return true;
            }
            return true;
        }
    }

    GhostViewPort(View view) {
        super(view.getContext());
        this.mOnPreDrawListener = new 1();
        this.mView = view;
        setWillNotDraw(false);
        setClipChildren(false);
        setLayerType(2, null);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (getGhostView(this.mView) == this) {
            int inverseVisibility = visibility == 0 ? 4 : 0;
            ViewUtils.setTransitionVisibility(this.mView, inverseVisibility);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    void setMatrix(@NonNull Matrix matrix) {
        this.mMatrix = matrix;
    }

    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.mStartParent = viewGroup;
        this.mStartView = view;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setGhostView(this.mView, this);
        this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.setTransitionVisibility(this.mView, 4);
        if (this.mView.getParent() != null) {
            this.mView.getParent().invalidate();
        }
    }

    protected void onDetachedFromWindow() {
        this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.setTransitionVisibility(this.mView, 0);
        setGhostView(this.mView, null);
        if (this.mView.getParent() != null) {
            this.mView.getParent().invalidate();
        }
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        CanvasUtils.enableZ(canvas, true);
        canvas.setMatrix(this.mMatrix);
        ViewUtils.setTransitionVisibility(this.mView, 0);
        this.mView.invalidate();
        ViewUtils.setTransitionVisibility(this.mView, 4);
        drawChild(canvas, this.mView, getDrawingTime());
        CanvasUtils.enableZ(canvas, false);
    }

    static void copySize(View from, View to) {
        ViewUtils.setLeftTopRightBottom(to, to.getLeft(), to.getTop(), to.getLeft() + from.getWidth(), to.getTop() + from.getHeight());
    }

    static GhostViewPort getGhostView(View view) {
        return (GhostViewPort) view.getTag(R.id.ghost_view);
    }

    static void setGhostView(@NonNull View view, @Nullable GhostViewPort ghostView) {
        view.setTag(R.id.ghost_view, ghostView);
    }

    static void calculateMatrix(View view, ViewGroup host, Matrix matrix) {
        ViewGroup parent = view.getParent();
        matrix.reset();
        ViewUtils.transformMatrixToGlobal(parent, matrix);
        matrix.preTranslate(-parent.getScrollX(), -parent.getScrollY());
        ViewUtils.transformMatrixToLocal(host, matrix);
    }

    static GhostViewPort addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
        GhostViewHolder oldHolder;
        if (!(view.getParent() instanceof ViewGroup)) {
            throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
        }
        GhostViewHolder holder = GhostViewHolder.getHolder(viewGroup);
        GhostViewPort ghostView = getGhostView(view);
        int previousRefCount = 0;
        if (ghostView != null && (oldHolder = (GhostViewHolder) ghostView.getParent()) != holder) {
            previousRefCount = ghostView.mReferences;
            oldHolder.removeView(ghostView);
            ghostView = null;
        }
        if (ghostView == null) {
            if (matrix == null) {
                matrix = new Matrix();
                calculateMatrix(view, viewGroup, matrix);
            }
            ghostView = new GhostViewPort(view);
            ghostView.setMatrix(matrix);
            if (holder == null) {
                holder = new GhostViewHolder(viewGroup);
            } else {
                holder.popToOverlayTop();
            }
            copySize(viewGroup, holder);
            copySize(viewGroup, ghostView);
            holder.addGhostView(ghostView);
            ghostView.mReferences = previousRefCount;
        } else if (matrix != null) {
            ghostView.setMatrix(matrix);
        }
        ghostView.mReferences++;
        return ghostView;
    }

    static void removeGhost(View view) {
        View ghostView = getGhostView(view);
        if (ghostView != null) {
            ghostView.mReferences--;
            if (ghostView.mReferences <= 0) {
                GhostViewHolder holder = ghostView.getParent();
                holder.removeView(ghostView);
            }
        }
    }
}
