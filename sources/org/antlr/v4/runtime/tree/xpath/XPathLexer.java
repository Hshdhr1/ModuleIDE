package org.antlr.v4.runtime.tree.xpath;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class XPathLexer extends Lexer {
    public static final int ANYWHERE = 3;
    public static final int BANG = 6;
    public static final int ID = 7;
    public static final int ROOT = 4;
    public static final int RULE_REF = 2;
    public static final int STRING = 8;
    public static final int TOKEN_REF = 1;
    public static final int WILDCARD = 5;
    protected int charPositionInLine;
    protected int line;
    public static String[] modeNames = {"DEFAULT_MODE"};
    public static final String[] ruleNames = {"ANYWHERE", "ROOT", "WILDCARD", "BANG", "ID", "NameChar", "NameStartChar", "STRING"};
    private static final String[] _LITERAL_NAMES = {null, null, null, "'//'", "'/'", "'*'", "'!'"};
    private static final String[] _SYMBOLIC_NAMES = {null, "TOKEN_REF", "RULE_REF", "ANYWHERE", "ROOT", "WILDCARD", "BANG", "ID", "STRING"};
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    @Deprecated
    public static final String[] tokenNames = new String[_SYMBOLIC_NAMES.length];

    static {
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }
            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    public String getGrammarFileName() {
        return "XPathLexer.g4";
    }

    public String[] getRuleNames() {
        return ruleNames;
    }

    public String[] getModeNames() {
        return modeNames;
    }

    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    public ATN getATN() {
        return null;
    }

    public XPathLexer(CharStream input) {
        super(input);
        this.line = 1;
        this.charPositionInLine = 0;
    }

    public Token nextToken() {
        this._tokenStartCharIndex = this._input.index();
        CommonToken t = null;
        while (t == null) {
            switch (this._input.LA(1)) {
                case -1:
                    return new CommonToken(-1, "<EOF>");
                case 33:
                    consume();
                    t = new CommonToken(6, "!");
                    break;
                case 39:
                    String s = matchString();
                    t = new CommonToken(8, s);
                    break;
                case 42:
                    consume();
                    t = new CommonToken(5, "*");
                    break;
                case 47:
                    consume();
                    if (this._input.LA(1) == 47) {
                        consume();
                        t = new CommonToken(3, "//");
                        break;
                    } else {
                        t = new CommonToken(4, "/");
                        break;
                    }
                default:
                    if (isNameStartChar(this._input.LA(1))) {
                        String id = matchID();
                        if (!Character.isUpperCase(id.charAt(0))) {
                            t = new CommonToken(2, id);
                            break;
                        } else {
                            t = new CommonToken(1, id);
                            break;
                        }
                    } else {
                        throw new LexerNoViableAltException(this, this._input, this._tokenStartCharIndex, null);
                    }
            }
        }
        t.setStartIndex(this._tokenStartCharIndex);
        t.setCharPositionInLine(this._tokenStartCharIndex);
        t.setLine(this.line);
        return t;
    }

    public void consume() {
        int curChar = this._input.LA(1);
        if (curChar == 10) {
            this.line++;
            this.charPositionInLine = 0;
        } else {
            this.charPositionInLine++;
        }
        this._input.consume();
    }

    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    public String matchID() {
        int start = this._input.index();
        consume();
        while (isNameChar(this._input.LA(1))) {
            consume();
        }
        return this._input.getText(Interval.of(start, this._input.index() - 1));
    }

    public String matchString() {
        int start = this._input.index();
        consume();
        while (this._input.LA(1) != 39) {
            consume();
        }
        consume();
        return this._input.getText(Interval.of(start, this._input.index() - 1));
    }

    public boolean isNameChar(int c) {
        return Character.isUnicodeIdentifierPart(c);
    }

    public boolean isNameStartChar(int c) {
        return Character.isUnicodeIdentifierStart(c);
    }
}
