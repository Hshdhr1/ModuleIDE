package com.blacksquircle.ui.language.python.parser;

import com.blacksquircle.ui.language.base.model.ParseResult;
import com.blacksquircle.ui.language.base.model.TextStructure;
import com.blacksquircle.ui.language.base.parser.LanguageParser;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PythonParser.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lcom/blacksquircle/ui/language/python/parser/PythonParser;", "Lcom/blacksquircle/ui/language/base/parser/LanguageParser;", "()V", "execute", "Lcom/blacksquircle/ui/language/base/model/ParseResult;", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "Companion", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes59.dex */
public final class PythonParser implements LanguageParser {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private static PythonParser pythonParser;

    public /* synthetic */ PythonParser(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PythonParser() {
    }

    public static final /* synthetic */ PythonParser access$getPythonParser$cp() {
        return pythonParser;
    }

    public static final /* synthetic */ void access$setPythonParser$cp(PythonParser pythonParser2) {
        pythonParser = pythonParser2;
    }

    /* compiled from: PythonParser.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/blacksquircle/ui/language/python/parser/PythonParser$Companion;", "", "()V", "pythonParser", "Lcom/blacksquircle/ui/language/python/parser/PythonParser;", "getInstance", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final PythonParser getInstance() {
            PythonParser access$getPythonParser$cp = PythonParser.access$getPythonParser$cp();
            if (access$getPythonParser$cp != null) {
                return access$getPythonParser$cp;
            }
            PythonParser pythonParser = new PythonParser(null);
            Companion companion = PythonParser.INSTANCE;
            PythonParser.access$setPythonParser$cp(pythonParser);
            return pythonParser;
        }
    }

    @NotNull
    public ParseResult execute(@NotNull TextStructure structure) {
        Intrinsics.checkNotNullParameter(structure, "structure");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
