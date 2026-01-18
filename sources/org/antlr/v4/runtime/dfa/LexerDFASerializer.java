package org.antlr.v4.runtime.dfa;

import org.antlr.v4.runtime.VocabularyImpl;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LexerDFASerializer extends DFASerializer {
    public LexerDFASerializer(DFA dfa) {
        super(dfa, VocabularyImpl.EMPTY_VOCABULARY);
    }

    protected String getEdgeLabel(int i) {
        return new StringBuilder("'").appendCodePoint(i).append("'").toString();
    }
}
