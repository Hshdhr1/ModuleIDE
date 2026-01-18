package com.blacksquircle.ui.editorkit.plugin.pinchzoom;

import android.util.Log;
import android.view.MotionEvent;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PinchZoomPlugin.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/pinchzoom/PinchZoomPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "isDoingPinchZoom", "", "maxTextSize", "", "getMaxTextSize", "()F", "setMaxTextSize", "(F)V", "minTextSize", "getMinTextSize", "setMinTextSize", "pinchFactor", "getDistanceBetweenTouches", "event", "Landroid/view/MotionEvent;", "onAttached", "", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onTouchEvent", "updateTextSize", "size", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class PinchZoomPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final float DEFAULT_MAX_TEXT_SIZE = 20.0f;
    public static final float DEFAULT_MIN_TEXT_SIZE = 10.0f;

    @NotNull
    public static final String PLUGIN_ID = "pinchzoom-0361";
    private boolean isDoingPinchZoom;
    private float maxTextSize;
    private float minTextSize;
    private float pinchFactor;

    public PinchZoomPlugin() {
        super("pinchzoom-0361");
        this.minTextSize = 10.0f;
        this.maxTextSize = 20.0f;
        this.pinchFactor = 1.0f;
    }

    public final float getMinTextSize() {
        return this.minTextSize;
    }

    public final void setMinTextSize(float f) {
        this.minTextSize = f;
    }

    public final float getMaxTextSize() {
        return this.maxTextSize;
    }

    public final void setMaxTextSize(float f) {
        this.maxTextSize = f;
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        Log.d("pinchzoom-0361", "PinchZoom plugin loaded successfully!");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r5) {
        /*
            r4 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            int r0 = r5.getAction()
            r1 = 0
            r2 = 1
            if (r0 == r2) goto L47
            r3 = 2
            if (r0 == r3) goto L14
            r5 = 3
            if (r0 == r5) goto L47
            goto L49
        L14:
            int r0 = r5.getPointerCount()
            if (r0 != r3) goto L49
            float r5 = r4.getDistanceBetweenTouches(r5)
            boolean r0 = r4.isDoingPinchZoom
            if (r0 != 0) goto L3e
            android.content.Context r0 = r4.requireContext()
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            float r0 = r0.scaledDensity
            com.blacksquircle.ui.editorkit.widget.TextProcessor r1 = r4.getEditText()
            float r1 = r1.getTextSize()
            float r1 = r1 / r0
            float r1 = r1 / r5
            r4.pinchFactor = r1
            r4.isDoingPinchZoom = r2
        L3e:
            float r0 = r4.pinchFactor
            float r0 = r0 * r5
            boolean r5 = r4.updateTextSize(r0)
            return r5
        L47:
            r4.isDoingPinchZoom = r1
        L49:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blacksquircle.ui.editorkit.plugin.pinchzoom.PinchZoomPlugin.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private final float getDistanceBetweenTouches(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private final boolean updateTextSize(float size) {
        TextProcessor editText = getEditText();
        float f = this.minTextSize;
        if (size >= f) {
            f = this.maxTextSize;
            if (size <= f) {
                f = ((float) Math.ceil(size * r1)) / 2;
            }
        }
        editText.setTextSize(f);
        return true;
    }

    /* compiled from: PinchZoomPlugin.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/pinchzoom/PinchZoomPlugin$Companion;", "", "()V", "DEFAULT_MAX_TEXT_SIZE", "", "DEFAULT_MIN_TEXT_SIZE", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
