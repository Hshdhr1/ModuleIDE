package com.blacksquircle.ui.language.python;

import com.blacksquircle.ui.language.base.Language;
import com.blacksquircle.ui.language.base.parser.LanguageParser;
import com.blacksquircle.ui.language.base.provider.SuggestionProvider;
import com.blacksquircle.ui.language.base.styler.LanguageStyler;
import com.blacksquircle.ui.language.python.parser.PythonParser;
import com.blacksquircle.ui.language.python.provider.PythonProvider;
import com.blacksquircle.ui.language.python.styler.PythonStyler;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: PythonLanguage.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lcom/blacksquircle/ui/language/python/PythonLanguage;", "Lcom/blacksquircle/ui/language/base/Language;", "()V", "languageName", "", "getLanguageName", "()Ljava/lang/String;", "getParser", "Lcom/blacksquircle/ui/language/base/parser/LanguageParser;", "getProvider", "Lcom/blacksquircle/ui/language/base/provider/SuggestionProvider;", "getStyler", "Lcom/blacksquircle/ui/language/base/styler/LanguageStyler;", "Companion", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes59.dex */
public final class PythonLanguage implements Language {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String LANGUAGE_NAME = "python";

    @NotNull
    private final String languageName = "python";

    /* compiled from: PythonLanguage.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/language/python/PythonLanguage$Companion;", "", "()V", "LANGUAGE_NAME", "", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @NotNull
    public String getLanguageName() {
        return this.languageName;
    }

    @NotNull
    public LanguageParser getParser() {
        return PythonParser.Companion.getInstance();
    }

    @NotNull
    public SuggestionProvider getProvider() {
        return PythonProvider.Companion.getInstance();
    }

    @NotNull
    public LanguageStyler getStyler() {
        return PythonStyler.Companion.getInstance();
    }
}
