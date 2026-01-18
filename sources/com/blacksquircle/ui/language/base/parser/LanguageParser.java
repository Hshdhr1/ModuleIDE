package com.blacksquircle.ui.language.base.parser;

import com.blacksquircle.ui.language.base.model.ParseResult;
import com.blacksquircle.ui.language.base.model.TextStructure;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: LanguageParser.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/blacksquircle/ui/language/base/parser/LanguageParser;", "", "execute", "Lcom/blacksquircle/ui/language/base/model/ParseResult;", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public interface LanguageParser {
    @NotNull
    ParseResult execute(@NotNull TextStructure structure);
}
