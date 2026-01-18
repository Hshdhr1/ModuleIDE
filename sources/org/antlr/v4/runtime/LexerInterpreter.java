package org.antlr.v4.runtime;

import java.util.ArrayList;
import java.util.Collection;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNType;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LexerInterpreter extends Lexer {
    protected final DFA[] _decisionToDFA;
    protected final PredictionContextCache _sharedContextCache;
    protected final ATN atn;
    protected final String[] channelNames;
    protected final String grammarFileName;
    protected final String[] modeNames;
    protected final String[] ruleNames;

    @Deprecated
    protected final String[] tokenNames;
    private final Vocabulary vocabulary;

    @Deprecated
    public LexerInterpreter(String grammarFileName, Collection collection, Collection collection2, Collection collection3, ATN atn, CharStream input) {
        this(grammarFileName, VocabularyImpl.fromTokenNames((String[]) collection.toArray(new String[collection.size()])), collection2, new ArrayList(), collection3, atn, input);
    }

    @Deprecated
    public LexerInterpreter(String grammarFileName, Vocabulary vocabulary, Collection collection, Collection collection2, ATN atn, CharStream input) {
        this(grammarFileName, vocabulary, collection, new ArrayList(), collection2, atn, input);
    }

    public LexerInterpreter(String grammarFileName, Vocabulary vocabulary, Collection collection, Collection collection2, Collection collection3, ATN atn, CharStream input) {
        super(input);
        this._sharedContextCache = new PredictionContextCache();
        if (atn.grammarType != ATNType.LEXER) {
            throw new IllegalArgumentException("The ATN must be a lexer ATN.");
        }
        this.grammarFileName = grammarFileName;
        this.atn = atn;
        this.tokenNames = new String[atn.maxTokenType];
        for (int i = 0; i < this.tokenNames.length; i++) {
            this.tokenNames[i] = vocabulary.getDisplayName(i);
        }
        this.ruleNames = (String[]) collection.toArray(new String[collection.size()]);
        this.channelNames = (String[]) collection2.toArray(new String[collection2.size()]);
        this.modeNames = (String[]) collection3.toArray(new String[collection3.size()]);
        this.vocabulary = vocabulary;
        this._decisionToDFA = new DFA[atn.getNumberOfDecisions()];
        for (int i2 = 0; i2 < this._decisionToDFA.length; i2++) {
            this._decisionToDFA[i2] = new DFA(atn.getDecisionState(i2), i2);
        }
        this._interp = new LexerATNSimulator(this, atn, this._decisionToDFA, this._sharedContextCache);
    }

    public ATN getATN() {
        return this.atn;
    }

    public String getGrammarFileName() {
        return this.grammarFileName;
    }

    @Deprecated
    public String[] getTokenNames() {
        return this.tokenNames;
    }

    public String[] getRuleNames() {
        return this.ruleNames;
    }

    public String[] getChannelNames() {
        return this.channelNames;
    }

    public String[] getModeNames() {
        return this.modeNames;
    }

    public Vocabulary getVocabulary() {
        return this.vocabulary != null ? this.vocabulary : super.getVocabulary();
    }
}
