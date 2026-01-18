package com.blacksquircle.ui.editorkit.plugin.dirtytext;

import android.text.Editable;
import android.util.Log;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DirtyTextPlugin.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/dirtytext/DirtyTextPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "isDirty", "", "onChangeListener", "Lcom/blacksquircle/ui/editorkit/plugin/dirtytext/OnChangeListener;", "getOnChangeListener", "()Lcom/blacksquircle/ui/editorkit/plugin/dirtytext/OnChangeListener;", "setOnChangeListener", "(Lcom/blacksquircle/ui/editorkit/plugin/dirtytext/OnChangeListener;)V", "afterTextChanged", "", "text", "Landroid/text/Editable;", "onAttached", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onDetached", "setTextContent", "", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class DirtyTextPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "dirty-text-9124";
    private boolean isDirty;

    @Nullable
    private OnChangeListener onChangeListener;

    public DirtyTextPlugin() {
        super("dirty-text-9124");
    }

    @Nullable
    public final OnChangeListener getOnChangeListener() {
        return this.onChangeListener;
    }

    public final void setOnChangeListener(@Nullable OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        Log.d("dirty-text-9124", "DirtyText plugin loaded successfully!");
    }

    public void onDetached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onDetached(editText);
        this.onChangeListener = null;
    }

    public void afterTextChanged(@Nullable Editable text) {
        OnChangeListener onChangeListener;
        super.afterTextChanged(text);
        if (this.isDirty || (onChangeListener = this.onChangeListener) == null) {
            return;
        }
        onChangeListener.onContentChanged();
    }

    public void setTextContent(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        super.setTextContent(text);
        this.isDirty = false;
    }

    /* compiled from: DirtyTextPlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/dirtytext/DirtyTextPlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
