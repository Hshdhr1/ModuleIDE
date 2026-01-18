package com.blacksquircle.ui.editorkit.plugin.shortcuts;

import com.blacksquircle.ui.editorkit.plugin.base.PluginContainer;
import com.blacksquircle.ui.editorkit.plugin.base.PluginSupplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Actions.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\b\u001a\u00020\t*\u00020\n2\u0019\b\u0002\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t0\f¢\u0006\u0002\b\u000e\",\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"value", "Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/OnShortcutListener;", "onShortcutListener", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginContainer;", "getOnShortcutListener", "(Lcom/blacksquircle/ui/editorkit/plugin/base/PluginContainer;)Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/OnShortcutListener;", "setOnShortcutListener", "(Lcom/blacksquircle/ui/editorkit/plugin/base/PluginContainer;Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/OnShortcutListener;)V", "shortcuts", "", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier;", "block", "Lkotlin/Function1;", "Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/ShortcutsPlugin;", "Lkotlin/ExtensionFunctionType;", "editorkit_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ActionsKt {
    @Nullable
    public static final OnShortcutListener getOnShortcutListener(@NotNull PluginContainer pluginContainer) {
        Intrinsics.checkNotNullParameter(pluginContainer, "<this>");
        ShortcutsPlugin shortcutsPlugin = (ShortcutsPlugin) pluginContainer.findPlugin("shortcuts-1095");
        if (shortcutsPlugin != null) {
            return shortcutsPlugin.getOnShortcutListener();
        }
        return null;
    }

    public static final void setOnShortcutListener(@NotNull PluginContainer pluginContainer, @Nullable OnShortcutListener onShortcutListener) {
        Intrinsics.checkNotNullParameter(pluginContainer, "<this>");
        ShortcutsPlugin shortcutsPlugin = (ShortcutsPlugin) pluginContainer.findPlugin("shortcuts-1095");
        if (shortcutsPlugin == null) {
            return;
        }
        shortcutsPlugin.setOnShortcutListener(onShortcutListener);
    }

    /* compiled from: Actions.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/ShortcutsPlugin;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class 1 extends Lambda implements Function1 {
        public static final 1 INSTANCE = new 1();

        1() {
            super(1);
        }

        public final void invoke(@NotNull ShortcutsPlugin shortcutsPlugin) {
            Intrinsics.checkNotNullParameter(shortcutsPlugin, "$this$null");
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((ShortcutsPlugin) obj);
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void shortcuts$default(PluginSupplier pluginSupplier, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = 1.INSTANCE;
        }
        shortcuts(pluginSupplier, function1);
    }

    public static final void shortcuts(@NotNull PluginSupplier pluginSupplier, @NotNull Function1 function1) {
        Intrinsics.checkNotNullParameter(pluginSupplier, "<this>");
        Intrinsics.checkNotNullParameter(function1, "block");
        ShortcutsPlugin shortcutsPlugin = new ShortcutsPlugin();
        function1.invoke(shortcutsPlugin);
        pluginSupplier.plugin(shortcutsPlugin);
    }
}
