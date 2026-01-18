package com.blacksquircle.ui.language.base;

import com.blacksquircle.ui.language.base.parser.LanguageParser;
import com.blacksquircle.ui.language.base.provider.SuggestionProvider;
import com.blacksquircle.ui.language.base.styler.LanguageStyler;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: Language.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, d2 = {"Lcom/blacksquircle/ui/language/base/Language;", "", "languageName", "", "getLanguageName", "()Ljava/lang/String;", "getParser", "Lcom/blacksquircle/ui/language/base/parser/LanguageParser;", "getProvider", "Lcom/blacksquircle/ui/language/base/provider/SuggestionProvider;", "getStyler", "Lcom/blacksquircle/ui/language/base/styler/LanguageStyler;", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public interface Language {
    @NotNull
    String getLanguageName();

    @NotNull
    LanguageParser getParser();

    @NotNull
    SuggestionProvider getProvider();

    @NotNull
    LanguageStyler getStyler();
}
