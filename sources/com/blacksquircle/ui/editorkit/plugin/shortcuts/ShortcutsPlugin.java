package com.blacksquircle.ui.editorkit.plugin.shortcuts;

import android.util.Log;
import android.view.KeyEvent;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ShortcutsPlugin.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/ShortcutsPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "onShortcutListener", "Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/OnShortcutListener;", "getOnShortcutListener", "()Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/OnShortcutListener;", "setOnShortcutListener", "(Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/OnShortcutListener;)V", "shortcutKeyFilter", "", "", "getShortcutKeyFilter", "()Ljava/util/List;", "setShortcutKeyFilter", "(Ljava/util/List;)V", "onAttached", "", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onDetached", "onKeyDown", "", "keyCode", "event", "Landroid/view/KeyEvent;", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ShortcutsPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "shortcuts-1095";

    @Nullable
    private OnShortcutListener onShortcutListener;

    @NotNull
    private List shortcutKeyFilter;

    public ShortcutsPlugin() {
        super("shortcuts-1095");
        this.shortcutKeyFilter = CollectionsKt.emptyList();
    }

    @Nullable
    public final OnShortcutListener getOnShortcutListener() {
        return this.onShortcutListener;
    }

    public final void setOnShortcutListener(@Nullable OnShortcutListener onShortcutListener) {
        this.onShortcutListener = onShortcutListener;
    }

    @NotNull
    public final List getShortcutKeyFilter() {
        return this.shortcutKeyFilter;
    }

    public final void setShortcutKeyFilter(@NotNull List list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.shortcutKeyFilter = list;
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        Log.d("shortcuts-1095", "Shortcuts plugin loaded successfully!");
    }

    public void onDetached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onDetached(editText);
        this.onShortcutListener = null;
    }

    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        OnShortcutListener onShortcutListener = this.onShortcutListener;
        if (onShortcutListener != null) {
            if (event == null || keyCode == 113 || keyCode == 114 || keyCode == 57 || keyCode == 58) {
                return false;
            }
            Shortcut shortcut = new Shortcut(event.isCtrlPressed(), event.isShiftPressed(), event.isAltPressed(), keyCode);
            if ((shortcut.getCtrl() || shortcut.getAlt() || this.shortcutKeyFilter.contains(Integer.valueOf(keyCode))) && onShortcutListener.onShortcut(shortcut)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /* compiled from: ShortcutsPlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/ShortcutsPlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
