package com.blacksquircle.ui.language.python.lexer;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: PythonToken.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\bk\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQj\u0002\bRj\u0002\bSj\u0002\bTj\u0002\bUj\u0002\bVj\u0002\bWj\u0002\bXj\u0002\bYj\u0002\bZj\u0002\b[j\u0002\b\\j\u0002\b]j\u0002\b^j\u0002\b_j\u0002\b`j\u0002\baj\u0002\bbj\u0002\bcj\u0002\bdj\u0002\bej\u0002\bfj\u0002\bgj\u0002\bhj\u0002\bij\u0002\bjj\u0002\bk¨\u0006l"}, d2 = {"Lcom/blacksquircle/ui/language/python/lexer/PythonToken;", "", "(Ljava/lang/String;I)V", "LONG_LITERAL", "INTEGER_LITERAL", "FLOAT_LITERAL", "IMAGINARY_LITERAL", "AND_KEYWORD", "AS", "ASSERT", "BREAK", "CLASS", "CONTINUE", "DEF", "DEL", "ELIF", "ELSE", "EXCEPT", "EXEC", "FINALLY", "FOR", "FROM", "GLOBAL", "IF", "IMPORT", "IN", "IS", "LAMBDA", "NOT_KEYWORD", "OR_KEYWORD", "PASS", "PRINT", "RAISE", "RETURN", "TRY", "WHILE", "YIELD", "CHAR", "DOUBLE", "FLOAT", "INT", "LONG", "SHORT", "SIGNED", "UNSIGNED", "VOID", "METHOD", "TRUE", "FALSE", "NONE", "PLUSEQ", "MINUSEQ", "EXPEQ", "MULTEQ", "ATEQ", "FLOORDIVEQ", "DIVEQ", "MODEQ", "ANDEQ", "OREQ", "XOREQ", "GTGTEQ", "LTLTEQ", "LTLT", "GTGT", "EXP", "FLOORDIV", "LTEQ", "GTEQ", "EQEQ", "NOTEQ", "NOTEQ_OLD", "RARROW", "PLUS", "MINUS", "MULT", "DIV", "MOD", "AND", "OR", "XOR", "TILDE", "LT", "GT", "AT", "COLON", "TICK", "EQ", "COLONEQ", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMICOLON", "COMMA", "DOT", "DECORATOR", "DOUBLE_QUOTED_STRING", "SINGLE_QUOTED_STRING", "LONG_DOUBLE_QUOTED_STRING", "LONG_SINGLE_QUOTED_STRING", "LINE_COMMENT", "IDENTIFIER", "WHITESPACE", "BAD_CHARACTER", "EOF", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes59.dex */
public final class PythonToken {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PythonToken[] $VALUES;
    public static final PythonToken LONG_LITERAL = new PythonToken("LONG_LITERAL", 0);
    public static final PythonToken INTEGER_LITERAL = new PythonToken("INTEGER_LITERAL", 1);
    public static final PythonToken FLOAT_LITERAL = new PythonToken("FLOAT_LITERAL", 2);
    public static final PythonToken IMAGINARY_LITERAL = new PythonToken("IMAGINARY_LITERAL", 3);
    public static final PythonToken AND_KEYWORD = new PythonToken("AND_KEYWORD", 4);
    public static final PythonToken AS = new PythonToken("AS", 5);
    public static final PythonToken ASSERT = new PythonToken("ASSERT", 6);
    public static final PythonToken BREAK = new PythonToken("BREAK", 7);
    public static final PythonToken CLASS = new PythonToken("CLASS", 8);
    public static final PythonToken CONTINUE = new PythonToken("CONTINUE", 9);
    public static final PythonToken DEF = new PythonToken("DEF", 10);
    public static final PythonToken DEL = new PythonToken("DEL", 11);
    public static final PythonToken ELIF = new PythonToken("ELIF", 12);
    public static final PythonToken ELSE = new PythonToken("ELSE", 13);
    public static final PythonToken EXCEPT = new PythonToken("EXCEPT", 14);
    public static final PythonToken EXEC = new PythonToken("EXEC", 15);
    public static final PythonToken FINALLY = new PythonToken("FINALLY", 16);
    public static final PythonToken FOR = new PythonToken("FOR", 17);
    public static final PythonToken FROM = new PythonToken("FROM", 18);
    public static final PythonToken GLOBAL = new PythonToken("GLOBAL", 19);
    public static final PythonToken IF = new PythonToken("IF", 20);
    public static final PythonToken IMPORT = new PythonToken("IMPORT", 21);
    public static final PythonToken IN = new PythonToken("IN", 22);
    public static final PythonToken IS = new PythonToken("IS", 23);
    public static final PythonToken LAMBDA = new PythonToken("LAMBDA", 24);
    public static final PythonToken NOT_KEYWORD = new PythonToken("NOT_KEYWORD", 25);
    public static final PythonToken OR_KEYWORD = new PythonToken("OR_KEYWORD", 26);
    public static final PythonToken PASS = new PythonToken("PASS", 27);
    public static final PythonToken PRINT = new PythonToken("PRINT", 28);
    public static final PythonToken RAISE = new PythonToken("RAISE", 29);
    public static final PythonToken RETURN = new PythonToken("RETURN", 30);
    public static final PythonToken TRY = new PythonToken("TRY", 31);
    public static final PythonToken WHILE = new PythonToken("WHILE", 32);
    public static final PythonToken YIELD = new PythonToken("YIELD", 33);
    public static final PythonToken CHAR = new PythonToken("CHAR", 34);
    public static final PythonToken DOUBLE = new PythonToken("DOUBLE", 35);
    public static final PythonToken FLOAT = new PythonToken("FLOAT", 36);
    public static final PythonToken INT = new PythonToken("INT", 37);
    public static final PythonToken LONG = new PythonToken("LONG", 38);
    public static final PythonToken SHORT = new PythonToken("SHORT", 39);
    public static final PythonToken SIGNED = new PythonToken("SIGNED", 40);
    public static final PythonToken UNSIGNED = new PythonToken("UNSIGNED", 41);
    public static final PythonToken VOID = new PythonToken("VOID", 42);
    public static final PythonToken METHOD = new PythonToken("METHOD", 43);
    public static final PythonToken TRUE = new PythonToken("TRUE", 44);
    public static final PythonToken FALSE = new PythonToken("FALSE", 45);
    public static final PythonToken NONE = new PythonToken("NONE", 46);
    public static final PythonToken PLUSEQ = new PythonToken("PLUSEQ", 47);
    public static final PythonToken MINUSEQ = new PythonToken("MINUSEQ", 48);
    public static final PythonToken EXPEQ = new PythonToken("EXPEQ", 49);
    public static final PythonToken MULTEQ = new PythonToken("MULTEQ", 50);
    public static final PythonToken ATEQ = new PythonToken("ATEQ", 51);
    public static final PythonToken FLOORDIVEQ = new PythonToken("FLOORDIVEQ", 52);
    public static final PythonToken DIVEQ = new PythonToken("DIVEQ", 53);
    public static final PythonToken MODEQ = new PythonToken("MODEQ", 54);
    public static final PythonToken ANDEQ = new PythonToken("ANDEQ", 55);
    public static final PythonToken OREQ = new PythonToken("OREQ", 56);
    public static final PythonToken XOREQ = new PythonToken("XOREQ", 57);
    public static final PythonToken GTGTEQ = new PythonToken("GTGTEQ", 58);
    public static final PythonToken LTLTEQ = new PythonToken("LTLTEQ", 59);
    public static final PythonToken LTLT = new PythonToken("LTLT", 60);
    public static final PythonToken GTGT = new PythonToken("GTGT", 61);
    public static final PythonToken EXP = new PythonToken("EXP", 62);
    public static final PythonToken FLOORDIV = new PythonToken("FLOORDIV", 63);
    public static final PythonToken LTEQ = new PythonToken("LTEQ", 64);
    public static final PythonToken GTEQ = new PythonToken("GTEQ", 65);
    public static final PythonToken EQEQ = new PythonToken("EQEQ", 66);
    public static final PythonToken NOTEQ = new PythonToken("NOTEQ", 67);
    public static final PythonToken NOTEQ_OLD = new PythonToken("NOTEQ_OLD", 68);
    public static final PythonToken RARROW = new PythonToken("RARROW", 69);
    public static final PythonToken PLUS = new PythonToken("PLUS", 70);
    public static final PythonToken MINUS = new PythonToken("MINUS", 71);
    public static final PythonToken MULT = new PythonToken("MULT", 72);
    public static final PythonToken DIV = new PythonToken("DIV", 73);
    public static final PythonToken MOD = new PythonToken("MOD", 74);
    public static final PythonToken AND = new PythonToken("AND", 75);
    public static final PythonToken OR = new PythonToken("OR", 76);
    public static final PythonToken XOR = new PythonToken("XOR", 77);
    public static final PythonToken TILDE = new PythonToken("TILDE", 78);
    public static final PythonToken LT = new PythonToken("LT", 79);
    public static final PythonToken GT = new PythonToken("GT", 80);
    public static final PythonToken AT = new PythonToken("AT", 81);
    public static final PythonToken COLON = new PythonToken("COLON", 82);
    public static final PythonToken TICK = new PythonToken("TICK", 83);
    public static final PythonToken EQ = new PythonToken("EQ", 84);
    public static final PythonToken COLONEQ = new PythonToken("COLONEQ", 85);
    public static final PythonToken LPAREN = new PythonToken("LPAREN", 86);
    public static final PythonToken RPAREN = new PythonToken("RPAREN", 87);
    public static final PythonToken LBRACE = new PythonToken("LBRACE", 88);
    public static final PythonToken RBRACE = new PythonToken("RBRACE", 89);
    public static final PythonToken LBRACK = new PythonToken("LBRACK", 90);
    public static final PythonToken RBRACK = new PythonToken("RBRACK", 91);
    public static final PythonToken SEMICOLON = new PythonToken("SEMICOLON", 92);
    public static final PythonToken COMMA = new PythonToken("COMMA", 93);
    public static final PythonToken DOT = new PythonToken("DOT", 94);
    public static final PythonToken DECORATOR = new PythonToken("DECORATOR", 95);
    public static final PythonToken DOUBLE_QUOTED_STRING = new PythonToken("DOUBLE_QUOTED_STRING", 96);
    public static final PythonToken SINGLE_QUOTED_STRING = new PythonToken("SINGLE_QUOTED_STRING", 97);
    public static final PythonToken LONG_DOUBLE_QUOTED_STRING = new PythonToken("LONG_DOUBLE_QUOTED_STRING", 98);
    public static final PythonToken LONG_SINGLE_QUOTED_STRING = new PythonToken("LONG_SINGLE_QUOTED_STRING", 99);
    public static final PythonToken LINE_COMMENT = new PythonToken("LINE_COMMENT", 100);
    public static final PythonToken IDENTIFIER = new PythonToken("IDENTIFIER", 101);
    public static final PythonToken WHITESPACE = new PythonToken("WHITESPACE", 102);
    public static final PythonToken BAD_CHARACTER = new PythonToken("BAD_CHARACTER", 103);
    public static final PythonToken EOF = new PythonToken("EOF", 104);

    private static final /* synthetic */ PythonToken[] $values() {
        return new PythonToken[]{LONG_LITERAL, INTEGER_LITERAL, FLOAT_LITERAL, IMAGINARY_LITERAL, AND_KEYWORD, AS, ASSERT, BREAK, CLASS, CONTINUE, DEF, DEL, ELIF, ELSE, EXCEPT, EXEC, FINALLY, FOR, FROM, GLOBAL, IF, IMPORT, IN, IS, LAMBDA, NOT_KEYWORD, OR_KEYWORD, PASS, PRINT, RAISE, RETURN, TRY, WHILE, YIELD, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, SIGNED, UNSIGNED, VOID, METHOD, TRUE, FALSE, NONE, PLUSEQ, MINUSEQ, EXPEQ, MULTEQ, ATEQ, FLOORDIVEQ, DIVEQ, MODEQ, ANDEQ, OREQ, XOREQ, GTGTEQ, LTLTEQ, LTLT, GTGT, EXP, FLOORDIV, LTEQ, GTEQ, EQEQ, NOTEQ, NOTEQ_OLD, RARROW, PLUS, MINUS, MULT, DIV, MOD, AND, OR, XOR, TILDE, LT, GT, AT, COLON, TICK, EQ, COLONEQ, LPAREN, RPAREN, LBRACE, RBRACE, LBRACK, RBRACK, SEMICOLON, COMMA, DOT, DECORATOR, DOUBLE_QUOTED_STRING, SINGLE_QUOTED_STRING, LONG_DOUBLE_QUOTED_STRING, LONG_SINGLE_QUOTED_STRING, LINE_COMMENT, IDENTIFIER, WHITESPACE, BAD_CHARACTER, EOF};
    }

    @NotNull
    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static PythonToken valueOf(String str) {
        return (PythonToken) Enum.valueOf(PythonToken.class, str);
    }

    public static PythonToken[] values() {
        return (PythonToken[]) $VALUES.clone();
    }

    private PythonToken(String str, int i) {
    }

    static {
        PythonToken[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
    }
}
