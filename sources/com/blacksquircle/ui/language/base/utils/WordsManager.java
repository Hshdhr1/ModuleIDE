package com.blacksquircle.ui.language.base.utils;

import com.blacksquircle.ui.language.base.model.Suggestion;
import com.blacksquircle.ui.language.base.model.TextStructure;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/* compiled from: WordsManager.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0005J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0017R6\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/blacksquircle/ui/language/base/utils/WordsManager;", "", "()V", "lineMap", "Ljava/util/HashMap;", "", "Ljava/util/LinkedList;", "Lcom/blacksquircle/ui/language/base/model/Suggestion;", "Lkotlin/collections/HashMap;", "wordsPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "clearLines", "", "deleteLine", "lineNumber", "getWords", "", "processAllLines", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "processLine", "text", "", "Companion", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nWordsManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WordsManager.kt\ncom/blacksquircle/ui/language/base/utils/WordsManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,78:1\n1#2:79\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public final class WordsManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String WORDS_REGEX = "\\w((\\w|-)*(\\w))?";
    private final Pattern wordsPattern = Pattern.compile("\\w((\\w|-)*(\\w))?");

    @NotNull
    private final HashMap lineMap = new HashMap();

    /* compiled from: WordsManager.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/language/base/utils/WordsManager$Companion;", "", "()V", "WORDS_REGEX", "", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @NotNull
    public final Set getWords() {
        Set hashSet = new HashSet();
        Iterator it = this.lineMap.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((LinkedList) it.next()).iterator();
            while (it2.hasNext()) {
                hashSet.add((Suggestion) it2.next());
            }
        }
        return hashSet;
    }

    public final void processAllLines(@NotNull TextStructure structure) {
        Intrinsics.checkNotNullParameter(structure, "structure");
        int lineCount = structure.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            processLine(i, structure.getText().subSequence(structure.getIndexForStartOfLine(i), structure.getIndexForEndOfLine(i)));
        }
    }

    public final void processLine(int lineNumber, @NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        LinkedList linkedList = (LinkedList) this.lineMap.get(Integer.valueOf(lineNumber));
        if (linkedList != null) {
            linkedList.clear();
        }
        Matcher matcher = this.wordsPattern.matcher(text);
        while (matcher.find()) {
            Suggestion suggestion = new Suggestion(Suggestion.Type.WORD, text.subSequence(matcher.start(), matcher.end()).toString(), "");
            if (this.lineMap.containsKey(Integer.valueOf(lineNumber))) {
                LinkedList linkedList2 = (LinkedList) this.lineMap.get(Integer.valueOf(lineNumber));
                if (linkedList2 != null) {
                    linkedList2.add(suggestion);
                }
            } else {
                Map map = this.lineMap;
                Integer valueOf = Integer.valueOf(lineNumber);
                LinkedList linkedList3 = new LinkedList();
                linkedList3.add(suggestion);
                map.put(valueOf, linkedList3);
            }
        }
    }

    public final void deleteLine(int lineNumber) {
        this.lineMap.remove(Integer.valueOf(lineNumber));
    }

    public final void clearLines() {
        this.lineMap.clear();
    }
}
