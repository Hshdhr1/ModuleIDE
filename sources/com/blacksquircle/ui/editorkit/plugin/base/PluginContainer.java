package com.blacksquircle.ui.editorkit.plugin.base;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PluginContainer.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001f\u0010\n\u001a\u00020\u000b\"\b\b\u0000\u0010\u0003*\u00020\u00042\u0006\u0010\f\u001a\u0002H\u0003H&¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0012"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/base/PluginContainer;", "", "findPlugin", "T", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "pluginId", "", "(Ljava/lang/String;)Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "hasPlugin", "", "installPlugin", "", "plugin", "(Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;)V", "plugins", "supplier", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier;", "uninstallPlugin", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public interface PluginContainer {
    @Nullable
    EditorPlugin findPlugin(@NotNull String pluginId);

    boolean hasPlugin(@NotNull String pluginId);

    void installPlugin(@NotNull EditorPlugin plugin);

    void plugins(@NotNull PluginSupplier supplier);

    void uninstallPlugin(@NotNull String pluginId);
}
