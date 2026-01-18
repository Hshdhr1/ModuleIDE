package com.blacksquircle.ui.editorkit.plugin.autoindent;

import com.blacksquircle.ui.editorkit.plugin.base.PluginSupplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Actions.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0019\b\u0002\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\b\u0006¨\u0006\u0007"}, d2 = {"autoIndentation", "", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier;", "block", "Lkotlin/Function1;", "Lcom/blacksquircle/ui/editorkit/plugin/autoindent/AutoIndentPlugin;", "Lkotlin/ExtensionFunctionType;", "editorkit_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ActionsKt {

    /* compiled from: Actions.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/blacksquircle/ui/editorkit/plugin/autoindent/AutoIndentPlugin;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class 1 extends Lambda implements Function1 {
        public static final 1 INSTANCE = new 1();

        1() {
            super(1);
        }

        public final void invoke(@NotNull AutoIndentPlugin autoIndentPlugin) {
            Intrinsics.checkNotNullParameter(autoIndentPlugin, "$this$null");
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((AutoIndentPlugin) obj);
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void autoIndentation$default(PluginSupplier pluginSupplier, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = 1.INSTANCE;
        }
        autoIndentation(pluginSupplier, function1);
    }

    public static final void autoIndentation(@NotNull PluginSupplier pluginSupplier, @NotNull Function1 function1) {
        Intrinsics.checkNotNullParameter(pluginSupplier, "<this>");
        Intrinsics.checkNotNullParameter(function1, "block");
        AutoIndentPlugin autoIndentPlugin = new AutoIndentPlugin();
        function1.invoke(autoIndentPlugin);
        pluginSupplier.plugin(autoIndentPlugin);
    }
}
