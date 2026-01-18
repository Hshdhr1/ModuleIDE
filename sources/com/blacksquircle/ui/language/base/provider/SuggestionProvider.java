package com.blacksquircle.ui.language.base.provider;

import com.blacksquircle.ui.language.base.model.TextStructure;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: SuggestionProvider.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH&¨\u0006\u0010"}, d2 = {"Lcom/blacksquircle/ui/language/base/provider/SuggestionProvider;", "", "clearLines", "", "deleteLine", "lineNumber", "", "getAll", "", "Lcom/blacksquircle/ui/language/base/model/Suggestion;", "processAllLines", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "processLine", "text", "", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public interface SuggestionProvider {
    void clearLines();

    void deleteLine(int lineNumber);

    @NotNull
    Set getAll();

    void processAllLines(@NotNull TextStructure structure);

    void processLine(int lineNumber, @NotNull CharSequence text);
}
