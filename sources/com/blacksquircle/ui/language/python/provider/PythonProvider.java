package com.blacksquircle.ui.language.python.provider;

import com.blacksquircle.ui.language.base.model.TextStructure;
import com.blacksquircle.ui.language.base.provider.SuggestionProvider;
import com.blacksquircle.ui.language.base.utils.WordsManager;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PythonProvider.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/blacksquircle/ui/language/python/provider/PythonProvider;", "Lcom/blacksquircle/ui/language/base/provider/SuggestionProvider;", "()V", "wordsManager", "Lcom/blacksquircle/ui/language/base/utils/WordsManager;", "clearLines", "", "deleteLine", "lineNumber", "", "getAll", "", "Lcom/blacksquircle/ui/language/base/model/Suggestion;", "processAllLines", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "processLine", "text", "", "Companion", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes59.dex */
public final class PythonProvider implements SuggestionProvider {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private static PythonProvider pythonProvider;

    @NotNull
    private final WordsManager wordsManager;

    public /* synthetic */ PythonProvider(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PythonProvider() {
        this.wordsManager = new WordsManager();
    }

    public static final /* synthetic */ PythonProvider access$getPythonProvider$cp() {
        return pythonProvider;
    }

    public static final /* synthetic */ void access$setPythonProvider$cp(PythonProvider pythonProvider2) {
        pythonProvider = pythonProvider2;
    }

    /* compiled from: PythonProvider.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/blacksquircle/ui/language/python/provider/PythonProvider$Companion;", "", "()V", "pythonProvider", "Lcom/blacksquircle/ui/language/python/provider/PythonProvider;", "getInstance", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final PythonProvider getInstance() {
            PythonProvider access$getPythonProvider$cp = PythonProvider.access$getPythonProvider$cp();
            if (access$getPythonProvider$cp != null) {
                return access$getPythonProvider$cp;
            }
            PythonProvider pythonProvider = new PythonProvider(null);
            Companion companion = PythonProvider.INSTANCE;
            PythonProvider.access$setPythonProvider$cp(pythonProvider);
            return pythonProvider;
        }
    }

    @NotNull
    public Set getAll() {
        return this.wordsManager.getWords();
    }

    public void processAllLines(@NotNull TextStructure structure) {
        Intrinsics.checkNotNullParameter(structure, "structure");
        this.wordsManager.processAllLines(structure);
    }

    public void processLine(int lineNumber, @NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.wordsManager.processLine(lineNumber, text);
    }

    public void deleteLine(int lineNumber) {
        this.wordsManager.deleteLine(lineNumber);
    }

    public void clearLines() {
        this.wordsManager.clearLines();
    }
}
