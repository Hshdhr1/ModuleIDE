package com.blacksquircle.ui.editorkit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.TypedArrayKt;
import com.blacksquircle.ui.editorkit.R;
import com.blacksquircle.ui.editorkit.widget.internal.ScrollableEditText;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextScroller.kt */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 >2\u00020\u00012\u00020\u0002:\u0002>?B%\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010*\u001a\u00020+2\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010,\u001a\u00020+J\b\u0010-\u001a\u00020+H\u0002J\b\u0010.\u001a\u00020!H\u0002J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020!2\u0006\u00102\u001a\u00020!H\u0002J\b\u00103\u001a\u000200H\u0002J\u0010\u00104\u001a\u00020+2\u0006\u00105\u001a\u000206H\u0014J(\u00107\u001a\u00020+2\u0006\u00101\u001a\u00020\b2\u0006\u00102\u001a\u00020\b2\u0006\u00108\u001a\u00020\b2\u0006\u00109\u001a\u00020\bH\u0016J\u0010\u0010:\u001a\u0002002\u0006\u0010;\u001a\u00020<H\u0017J\b\u0010=\u001a\u00020+H\u0002R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0014\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000f\u001a\u0004\b\u0015\u0010\rR\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001a@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/TextScroller;", "Landroid/view/View;", "Lcom/blacksquircle/ui/editorkit/widget/internal/ScrollableEditText$OnScrollChangedListener;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "draggingBitmap", "Landroid/graphics/Bitmap;", "getDraggingBitmap", "()Landroid/graphics/Bitmap;", "draggingBitmap$delegate", "Lkotlin/Lazy;", "hideCallback", "Ljava/lang/Runnable;", "hideHandler", "Landroid/os/Handler;", "normalBitmap", "getNormalBitmap", "normalBitmap$delegate", "scrollableEditText", "Lcom/blacksquircle/ui/editorkit/widget/internal/ScrollableEditText;", "value", "Lcom/blacksquircle/ui/editorkit/widget/TextScroller$State;", "state", "getState", "()Lcom/blacksquircle/ui/editorkit/widget/TextScroller$State;", "setState", "(Lcom/blacksquircle/ui/editorkit/widget/TextScroller$State;)V", "textScrollMax", "", "textScrollY", "thumbDragging", "Landroid/graphics/drawable/Drawable;", "thumbHeight", "thumbNormal", "thumbPaint", "Landroid/graphics/Paint;", "thumbTop", "attachTo", "", "detach", "getMeasurements", "getThumbTop", "isPointInThumb", "", "x", "y", "isShowScrollerJustified", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onScrollChanged", "oldX", "oldY", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "scrollView", "Companion", "State", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public class TextScroller extends View implements ScrollableEditText.OnScrollChangedListener {
    private static final int ALPHA_MAX = 225;
    private static final int ALPHA_MIN = 0;
    private static final int ALPHA_STEP = 25;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final long EXITING_DELAY = 17;
    private static final long TIME_EXITING = 2000;

    /* renamed from: draggingBitmap$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy draggingBitmap;

    @NotNull
    private final Runnable hideCallback;

    @NotNull
    private final Handler hideHandler;

    /* renamed from: normalBitmap$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy normalBitmap;

    @Nullable
    private ScrollableEditText scrollableEditText;

    @NotNull
    private State state;
    private float textScrollMax;
    private float textScrollY;

    @NotNull
    private final Drawable thumbDragging;
    private final int thumbHeight;

    @NotNull
    private final Drawable thumbNormal;

    @NotNull
    private final Paint thumbPaint;
    private float thumbTop;

    /* compiled from: TextScroller.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.HIDDEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.DRAGGING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[State.EXITING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ void $r8$lambda$c0ZPPW456pCCDPSYSmWtTA2InHE(TextScroller textScroller) {
        hideCallback$lambda$0(textScroller);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TextScroller(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TextScroller(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ TextScroller(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public static final /* synthetic */ Drawable access$getThumbDragging$p(TextScroller textScroller) {
        return textScroller.thumbDragging;
    }

    public static final /* synthetic */ int access$getThumbHeight$p(TextScroller textScroller) {
        return textScroller.thumbHeight;
    }

    public static final /* synthetic */ Drawable access$getThumbNormal$p(TextScroller textScroller) {
        return textScroller.thumbNormal;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TextScroller(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        Drawable drawable;
        Drawable drawable2;
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.state = State.HIDDEN;
        this.normalBitmap = LazyKt.lazy(new TextScroller$normalBitmap$2(this));
        this.draggingBitmap = LazyKt.lazy(new TextScroller$draggingBitmap$2(this));
        this.hideHandler = new Handler(Looper.getMainLooper());
        this.hideCallback = new TextScroller$$ExternalSyntheticLambda0(this);
        Paint paint = new Paint();
        this.thumbPaint = paint;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TextScroller, 0, 0);
        Intrinsics.checkNotNullExpressionValue(obtainStyledAttributes, "obtainStyledAttributes(...)");
        boolean hasValue = obtainStyledAttributes.hasValue(R.styleable.TextScroller_thumbNormal);
        boolean hasValue2 = obtainStyledAttributes.hasValue(R.styleable.TextScroller_thumbDragging);
        boolean hasValue3 = obtainStyledAttributes.hasValue(R.styleable.TextScroller_thumbTint);
        if (hasValue) {
            drawable = TypedArrayKt.getDrawableOrThrow(obtainStyledAttributes, R.styleable.TextScroller_thumbNormal);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.fastscroll_default);
            Intrinsics.checkNotNull(drawable);
            Intrinsics.checkNotNull(drawable);
        }
        this.thumbNormal = drawable;
        if (hasValue2) {
            drawable2 = TypedArrayKt.getDrawableOrThrow(obtainStyledAttributes, R.styleable.TextScroller_thumbDragging);
        } else {
            drawable2 = ContextCompat.getDrawable(context, R.drawable.fastscroll_pressed);
            Intrinsics.checkNotNull(drawable2);
            Intrinsics.checkNotNull(drawable2);
        }
        this.thumbDragging = drawable2;
        if (hasValue3) {
            int colorOrThrow = TypedArrayKt.getColorOrThrow(obtainStyledAttributes, R.styleable.TextScroller_thumbTint);
            drawable.setTint(colorOrThrow);
            drawable2.setTint(colorOrThrow);
        }
        this.thumbHeight = drawable.getIntrinsicHeight();
        paint.setAntiAlias(true);
        paint.setDither(false);
        paint.setAlpha(225);
        obtainStyledAttributes.recycle();
    }

    @NotNull
    public final State getState() {
        return this.state;
    }

    public final void setState(@NotNull State state) {
        Intrinsics.checkNotNullParameter(state, "value");
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            this.hideHandler.removeCallbacks(this.hideCallback);
            this.state = state;
            invalidate();
            return;
        }
        if (i == 2) {
            if (isShowScrollerJustified()) {
                this.hideHandler.removeCallbacks(this.hideCallback);
                this.state = state;
                invalidate();
                return;
            }
            return;
        }
        if (i == 3) {
            this.hideHandler.removeCallbacks(this.hideCallback);
            this.state = state;
            invalidate();
        } else {
            if (i != 4) {
                return;
            }
            this.hideHandler.removeCallbacks(this.hideCallback);
            this.state = state;
            invalidate();
        }
    }

    private final Bitmap getNormalBitmap() {
        return (Bitmap) this.normalBitmap.getValue();
    }

    private final Bitmap getDraggingBitmap() {
        return (Bitmap) this.draggingBitmap.getValue();
    }

    private static final void hideCallback$lambda$0(TextScroller textScroller) {
        Intrinsics.checkNotNullParameter(textScroller, "this$0");
        textScroller.setState(State.EXITING);
    }

    protected void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        int i = WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()];
        if (i == 2) {
            this.thumbPaint.setAlpha(225);
            canvas.drawBitmap(getNormalBitmap(), 0.0f, this.thumbTop, this.thumbPaint);
            return;
        }
        if (i == 3) {
            this.thumbPaint.setAlpha(225);
            canvas.drawBitmap(getDraggingBitmap(), 0.0f, this.thumbTop, this.thumbPaint);
        } else {
            if (i != 4) {
                return;
            }
            if (this.thumbPaint.getAlpha() > 25) {
                Paint paint = this.thumbPaint;
                paint.setAlpha(paint.getAlpha() - 25);
                canvas.drawBitmap(getNormalBitmap(), 0.0f, this.thumbTop, this.thumbPaint);
                getHandler().postDelayed(this.hideCallback, 17L);
                return;
            }
            this.thumbPaint.setAlpha(0);
            setState(State.HIDDEN);
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int i = 0;
        if (this.scrollableEditText != null && this.state != State.HIDDEN) {
            getMeasurements();
            int action = event.getAction();
            if (action != 0) {
                if (action == 1) {
                    setState(State.VISIBLE);
                    setPressed(false);
                    this.hideHandler.postDelayed(this.hideCallback, 2000L);
                } else if (action == 2 && this.state == State.DRAGGING) {
                    setPressed(true);
                    ScrollableEditText scrollableEditText = this.scrollableEditText;
                    if (scrollableEditText != null) {
                        scrollableEditText.abortFling();
                    }
                    int y = (int) event.getY();
                    int i2 = this.thumbHeight;
                    int i3 = y - (i2 / 2);
                    if (i3 >= 0) {
                        i = i2 + i3 > getHeight() - getPaddingBottom() ? (getHeight() - getPaddingBottom()) - this.thumbHeight : i3;
                    }
                    this.thumbTop = i;
                    scrollView();
                    invalidate();
                    return true;
                }
            } else if (isPointInThumb(event.getX(), event.getY())) {
                ScrollableEditText scrollableEditText2 = this.scrollableEditText;
                if (scrollableEditText2 != null) {
                    scrollableEditText2.abortFling();
                }
                setState(State.DRAGGING);
                setPressed(true);
                return true;
            }
        }
        return false;
    }

    public void onScrollChanged(int x, int y, int oldX, int oldY) {
        if (this.state != State.DRAGGING) {
            getMeasurements();
            setState(State.VISIBLE);
            this.hideHandler.postDelayed(this.hideCallback, 2000L);
        }
    }

    public final void attachTo(@NotNull ScrollableEditText scrollableEditText) {
        Intrinsics.checkNotNullParameter(scrollableEditText, "scrollableEditText");
        this.scrollableEditText = scrollableEditText;
        if (scrollableEditText != null) {
            scrollableEditText.addOnScrollChangedListener(this);
        }
    }

    public final void detach() {
        ScrollableEditText scrollableEditText = this.scrollableEditText;
        if (scrollableEditText != null) {
            scrollableEditText.removeOnScrollChangedListener(this);
        }
        this.scrollableEditText = null;
    }

    private final void scrollView() {
        if (this.scrollableEditText == null) {
            return;
        }
        float height = this.thumbTop / ((getHeight() - getPaddingBottom()) - this.thumbHeight);
        ScrollableEditText scrollableEditText = this.scrollableEditText;
        Intrinsics.checkNotNull(scrollableEditText);
        int lineHeight = scrollableEditText.getLineHeight();
        ScrollableEditText scrollableEditText2 = this.scrollableEditText;
        Intrinsics.checkNotNull(scrollableEditText2);
        int height2 = scrollableEditText2.getHeight();
        ScrollableEditText scrollableEditText3 = this.scrollableEditText;
        Intrinsics.checkNotNull(scrollableEditText3);
        int paddingBottom = height2 - scrollableEditText3.getPaddingBottom();
        ScrollableEditText scrollableEditText4 = this.scrollableEditText;
        if (scrollableEditText4 != null) {
            Intrinsics.checkNotNull(scrollableEditText4);
            scrollableEditText4.scrollTo(scrollableEditText4.getScrollX(), (int) ((this.textScrollMax * height) - (height * (paddingBottom - lineHeight))));
        }
    }

    private final void getMeasurements() {
        Layout layout;
        ScrollableEditText scrollableEditText = this.scrollableEditText;
        if (scrollableEditText == null) {
            return;
        }
        this.textScrollMax = (scrollableEditText == null || (layout = scrollableEditText.getLayout()) == null) ? 0.0f : layout.getHeight();
        this.textScrollY = this.scrollableEditText != null ? r0.getScrollY() : 0.0f;
        this.thumbTop = getThumbTop();
    }

    private final float getThumbTop() {
        ScrollableEditText scrollableEditText = this.scrollableEditText;
        if (scrollableEditText == null) {
            return 0.0f;
        }
        Intrinsics.checkNotNull(scrollableEditText);
        int lineHeight = scrollableEditText.getLineHeight();
        ScrollableEditText scrollableEditText2 = this.scrollableEditText;
        Intrinsics.checkNotNull(scrollableEditText2);
        int height = scrollableEditText2.getHeight();
        Intrinsics.checkNotNull(this.scrollableEditText);
        float height2 = ((getHeight() - getPaddingBottom()) - this.thumbHeight) * (this.textScrollY / ((this.textScrollMax - (height - r3.getPaddingBottom())) + lineHeight));
        float f = Float.isNaN(height2) ? 0.0f : height2;
        return f > ((float) ((getHeight() - getPaddingBottom()) - this.thumbHeight)) ? (getHeight() - getPaddingBottom()) - this.thumbHeight : f;
    }

    private final boolean isPointInThumb(float x, float y) {
        if (x < 0.0f || x > getWidth()) {
            return false;
        }
        float f = this.thumbTop;
        return y >= f && y <= f + ((float) this.thumbHeight);
    }

    private final boolean isShowScrollerJustified() {
        float f = this.textScrollMax;
        ScrollableEditText scrollableEditText = this.scrollableEditText;
        Intrinsics.checkNotNull(scrollableEditText);
        int height = scrollableEditText.getHeight();
        ScrollableEditText scrollableEditText2 = this.scrollableEditText;
        Intrinsics.checkNotNull(scrollableEditText2);
        return ((double) (f / ((float) (height - scrollableEditText2.getPaddingBottom())))) >= 1.5d;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: TextScroller.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/TextScroller$State;", "", "(Ljava/lang/String;I)V", "HIDDEN", "VISIBLE", "DRAGGING", "EXITING", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class State {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ State[] $VALUES;
        public static final State HIDDEN = new State("HIDDEN", 0);
        public static final State VISIBLE = new State("VISIBLE", 1);
        public static final State DRAGGING = new State("DRAGGING", 2);
        public static final State EXITING = new State("EXITING", 3);

        private static final /* synthetic */ State[] $values() {
            return new State[]{HIDDEN, VISIBLE, DRAGGING, EXITING};
        }

        @NotNull
        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }

        private State(String str, int i) {
        }

        static {
            State[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }
    }

    /* compiled from: TextScroller.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/TextScroller$Companion;", "", "()V", "ALPHA_MAX", "", "ALPHA_MIN", "ALPHA_STEP", "EXITING_DELAY", "", "TIME_EXITING", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
