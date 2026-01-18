package com.blacksquircle.ui.editorkit.plugin.textscroller;

import android.util.Log;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import com.blacksquircle.ui.editorkit.widget.TextScroller;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextScrollerPlugin.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/textscroller/TextScrollerPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "scroller", "Lcom/blacksquircle/ui/editorkit/widget/TextScroller;", "getScroller", "()Lcom/blacksquircle/ui/editorkit/widget/TextScroller;", "setScroller", "(Lcom/blacksquircle/ui/editorkit/widget/TextScroller;)V", "onAttached", "", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onDetached", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class TextScrollerPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "text-scroller-1821";

    @Nullable
    private TextScroller scroller;

    public TextScrollerPlugin() {
        super("text-scroller-1821");
    }

    @Nullable
    public final TextScroller getScroller() {
        return this.scroller;
    }

    public final void setScroller(@Nullable TextScroller textScroller) {
        this.scroller = textScroller;
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        TextScroller textScroller = this.scroller;
        if (textScroller != null) {
            textScroller.attachTo(editText);
        }
        Log.d("text-scroller-1821", "TextScroller plugin loaded successfully!");
    }

    public void onDetached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onDetached(editText);
        TextScroller textScroller = this.scroller;
        if (textScroller != null) {
            textScroller.detach();
        }
        this.scroller = null;
    }

    /* compiled from: TextScrollerPlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/textscroller/TextScrollerPlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
