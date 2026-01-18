package com.blacksquircle.ui.editorkit.plugin.base;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PluginSupplier.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0006\u001a\u00020\u0007\"\b\b\u0000\u0010\b*\u00020\u00052\u0006\u0010\u0006\u001a\u0002H\b¢\u0006\u0002\u0010\tJ\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier;", "", "()V", "plugins", "", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "plugin", "", "T", "(Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;)V", "supply", "", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class PluginSupplier {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final Set plugins;

    public /* synthetic */ PluginSupplier(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PluginSupplier() {
        this.plugins = new LinkedHashSet();
    }

    public final void plugin(@NotNull EditorPlugin plugin) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        this.plugins.add(plugin);
    }

    @NotNull
    /* renamed from: supply, reason: from getter */
    public final Set getPlugins() {
        return this.plugins;
    }

    /* compiled from: PluginSupplier.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier$Companion;", "", "()V", "create", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier;", "block", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final PluginSupplier create(@NotNull Function1 block) {
            Intrinsics.checkNotNullParameter(block, "block");
            PluginSupplier pluginSupplier = new PluginSupplier(null);
            block.invoke(pluginSupplier);
            return pluginSupplier;
        }
    }
}
