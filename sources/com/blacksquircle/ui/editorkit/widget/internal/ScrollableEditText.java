package com.blacksquircle.ui.editorkit.widget.internal;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.MultiAutoCompleteTextView;
import android.widget.OverScroller;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ScrollableEditText.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001:\u0001,B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u000fJ\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0002J\u0006\u0010\u001a\u001a\u00020\nJ(\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007H\u0014J(\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007H\u0014J\u0010\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020\u0015H\u0002J\u000e\u0010)\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u000fJ\u0010\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/internal/ScrollableEditText;", "Landroid/widget/MultiAutoCompleteTextView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "horizontallyScrollable", "", "maximumVelocity", "", "scrollListeners", "", "Lcom/blacksquircle/ui/editorkit/widget/internal/ScrollableEditText$OnScrollChangedListener;", "textScroller", "Landroid/widget/OverScroller;", "velocityTracker", "Landroid/view/VelocityTracker;", "abortFling", "", "addOnScrollChangedListener", "listener", "computeScroll", "initVelocityTrackerIfNotExists", "isHorizontallyScrollableCompat", "onScrollChanged", "horiz", "vert", "oldHoriz", "oldVert", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "recycleVelocityTracker", "removeOnScrollChangedListener", "setHorizontallyScrolling", "whether", "OnScrollChangedListener", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public abstract class ScrollableEditText extends MultiAutoCompleteTextView {
    private boolean horizontallyScrollable;
    private final float maximumVelocity;

    @NotNull
    private final List scrollListeners;

    @NotNull
    private final OverScroller textScroller;

    @Nullable
    private VelocityTracker velocityTracker;

    /* compiled from: ScrollableEditText.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H&¨\u0006\t"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/internal/ScrollableEditText$OnScrollChangedListener;", "", "onScrollChanged", "", "x", "", "y", "oldX", "oldY", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnScrollChangedListener {
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ScrollableEditText(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ScrollableEditText(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ ScrollableEditText(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 16842859 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ScrollableEditText(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.textScroller = new OverScroller(context);
        this.scrollListeners = new ArrayList();
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Iterator it = this.scrollListeners.iterator();
        while (it.hasNext()) {
            ((OnScrollChangedListener) it.next()).onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
        }
    }

    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        Iterator it = this.scrollListeners.iterator();
        while (it.hasNext()) {
            ((OnScrollChangedListener) it.next()).onScrollChanged(horiz, vert, oldHoriz, oldVert);
        }
    }

    public boolean onTouchEvent(@NotNull MotionEvent event) {
        VelocityTracker velocityTracker;
        VelocityTracker velocityTracker2;
        Intrinsics.checkNotNullParameter(event, "event");
        initVelocityTrackerIfNotExists();
        int action = event.getAction();
        if (action == 0) {
            abortFling();
        } else if (action == 1) {
            VelocityTracker velocityTracker3 = this.velocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.computeCurrentVelocity(1000, this.maximumVelocity);
            }
            int xVelocity = (!getHorizontallyScrollable() || (velocityTracker = this.velocityTracker) == null) ? 0 : (int) velocityTracker.getXVelocity();
            VelocityTracker velocityTracker4 = this.velocityTracker;
            int yVelocity = velocityTracker4 != null ? (int) velocityTracker4.getYVelocity() : 0;
            if (Math.abs(yVelocity) < 0 || Math.abs(xVelocity) < 0) {
                recycleVelocityTracker();
            } else if (xVelocity != 0 || yVelocity != 0) {
                OverScroller overScroller = this.textScroller;
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int i = -xVelocity;
                int i2 = -yVelocity;
                Layout layout = getLayout();
                int width = ((layout != null ? layout.getWidth() : getWidth()) - getWidth()) + getPaddingStart() + getPaddingEnd();
                Layout layout2 = getLayout();
                overScroller.fling(scrollX, scrollY, i, i2, 0, width, 0, ((layout2 != null ? layout2.getHeight() : getWidth()) - getHeight()) + getPaddingTop() + getPaddingBottom());
            }
        } else if (action == 2 && (velocityTracker2 = this.velocityTracker) != null) {
            velocityTracker2.addMovement(event);
        }
        return super.onTouchEvent(event);
    }

    public void computeScroll() {
        if (this.textScroller.computeScrollOffset()) {
            int currX = this.textScroller.getCurrX();
            int currY = this.textScroller.getCurrY();
            if (currY < 0) {
                if (getScrollY() - Math.abs(currY) > 0) {
                    scrollTo(currX, currY);
                }
            } else {
                scrollTo(currX, currY);
            }
            postInvalidate();
        }
    }

    public void setHorizontallyScrolling(boolean whether) {
        super.setHorizontallyScrolling(whether);
        this.horizontallyScrollable = whether;
    }

    /* renamed from: isHorizontallyScrollableCompat, reason: from getter */
    public final boolean getHorizontallyScrollable() {
        return this.horizontallyScrollable;
    }

    public final void addOnScrollChangedListener(@NotNull OnScrollChangedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.scrollListeners.add(listener);
    }

    public final void removeOnScrollChangedListener(@NotNull OnScrollChangedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.scrollListeners.remove(listener);
    }

    public final void abortFling() {
        if (!this.textScroller.isFinished()) {
            this.textScroller.abortAnimation();
        }
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
    }

    private final void initVelocityTrackerIfNotExists() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }

    private final void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.velocityTracker = null;
    }
}
