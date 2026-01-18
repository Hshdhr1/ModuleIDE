package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_SIZE = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    private int mLastTotalSpace;
    protected final RecyclerView.LayoutManager mLayoutManager;
    final Rect mTmpRect;

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedMeasurement(View view);

    public abstract int getDecoratedMeasurementInOther(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public abstract int getTransformedEndWithDecoration(View view);

    public abstract int getTransformedStartWithDecoration(View view);

    public abstract void offsetChild(View view, int i);

    public abstract void offsetChildren(int i);

    /* synthetic */ OrientationHelper(RecyclerView.LayoutManager x0, 1 x1) {
        this(x0);
    }

    private OrientationHelper(RecyclerView.LayoutManager layoutManager) {
        this.mLastTotalSpace = Integer.MIN_VALUE;
        this.mTmpRect = new Rect();
        this.mLayoutManager = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.mLayoutManager;
    }

    public void onLayoutComplete() {
        this.mLastTotalSpace = getTotalSpace();
    }

    public int getTotalSpaceChange() {
        if (Integer.MIN_VALUE == this.mLastTotalSpace) {
            return 0;
        }
        return getTotalSpace() - this.mLastTotalSpace;
    }

    public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager layoutManager, int orientation) {
        switch (orientation) {
            case 0:
                return createHorizontalHelper(layoutManager);
            case 1:
                return createVerticalHelper(layoutManager);
            default:
                throw new IllegalArgumentException("invalid orientation");
        }
    }

    class 1 extends OrientationHelper {
        1(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager, null);
        }

        public int getEndAfterPadding() {
            return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
        }

        public int getEnd() {
            return this.mLayoutManager.getWidth();
        }

        public void offsetChildren(int amount) {
            this.mLayoutManager.offsetChildrenHorizontal(amount);
        }

        public int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingLeft();
        }

        public int getDecoratedMeasurement(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin;
        }

        public int getDecoratedMeasurementInOther(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin;
        }

        public int getDecoratedEnd(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedRight(view) + params.rightMargin;
        }

        public int getDecoratedStart(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedLeft(view) - params.leftMargin;
        }

        public int getTransformedEndWithDecoration(View view) {
            this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
            return this.mTmpRect.right;
        }

        public int getTransformedStartWithDecoration(View view) {
            this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
            return this.mTmpRect.left;
        }

        public int getTotalSpace() {
            return (this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
        }

        public void offsetChild(View view, int offset) {
            view.offsetLeftAndRight(offset);
        }

        public int getEndPadding() {
            return this.mLayoutManager.getPaddingRight();
        }

        public int getMode() {
            return this.mLayoutManager.getWidthMode();
        }

        public int getModeInOther() {
            return this.mLayoutManager.getHeightMode();
        }
    }

    public static OrientationHelper createHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        return new 1(layoutManager);
    }

    class 2 extends OrientationHelper {
        2(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager, null);
        }

        public int getEndAfterPadding() {
            return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
        }

        public int getEnd() {
            return this.mLayoutManager.getHeight();
        }

        public void offsetChildren(int amount) {
            this.mLayoutManager.offsetChildrenVertical(amount);
        }

        public int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingTop();
        }

        public int getDecoratedMeasurement(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin;
        }

        public int getDecoratedMeasurementInOther(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin;
        }

        public int getDecoratedEnd(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedBottom(view) + params.bottomMargin;
        }

        public int getDecoratedStart(View view) {
            RecyclerView.LayoutParams params = view.getLayoutParams();
            return this.mLayoutManager.getDecoratedTop(view) - params.topMargin;
        }

        public int getTransformedEndWithDecoration(View view) {
            this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
            return this.mTmpRect.bottom;
        }

        public int getTransformedStartWithDecoration(View view) {
            this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
            return this.mTmpRect.top;
        }

        public int getTotalSpace() {
            return (this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
        }

        public void offsetChild(View view, int offset) {
            view.offsetTopAndBottom(offset);
        }

        public int getEndPadding() {
            return this.mLayoutManager.getPaddingBottom();
        }

        public int getMode() {
            return this.mLayoutManager.getHeightMode();
        }

        public int getModeInOther() {
            return this.mLayoutManager.getWidthMode();
        }
    }

    public static OrientationHelper createVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        return new 2(layoutManager);
    }
}
