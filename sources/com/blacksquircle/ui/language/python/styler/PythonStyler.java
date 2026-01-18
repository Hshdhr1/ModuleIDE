package com.blacksquircle.ui.language.python.styler;

import com.blacksquircle.ui.language.base.model.SyntaxHighlightResult;
import com.blacksquircle.ui.language.base.model.TextStructure;
import com.blacksquircle.ui.language.base.model.TokenType;
import com.blacksquircle.ui.language.base.styler.LanguageStyler;
import com.blacksquircle.ui.language.python.lexer.PythonLexer;
import com.blacksquircle.ui.language.python.lexer.PythonToken;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PythonStyler.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\t"}, d2 = {"Lcom/blacksquircle/ui/language/python/styler/PythonStyler;", "Lcom/blacksquircle/ui/language/base/styler/LanguageStyler;", "()V", "execute", "", "Lcom/blacksquircle/ui/language/base/model/SyntaxHighlightResult;", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "Companion", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes59.dex */
public final class PythonStyler implements LanguageStyler {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private static PythonStyler pythonStyler;

    /* compiled from: PythonStyler.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PythonToken.values().length];
            try {
                iArr[PythonToken.LONG_LITERAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PythonToken.INTEGER_LITERAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PythonToken.FLOAT_LITERAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PythonToken.IMAGINARY_LITERAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PythonToken.PLUSEQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[PythonToken.MINUSEQ.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[PythonToken.EXPEQ.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[PythonToken.MULTEQ.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[PythonToken.ATEQ.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[PythonToken.FLOORDIVEQ.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[PythonToken.DIVEQ.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[PythonToken.MODEQ.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[PythonToken.ANDEQ.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[PythonToken.OREQ.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[PythonToken.XOREQ.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[PythonToken.GTGTEQ.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[PythonToken.LTLTEQ.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[PythonToken.LTLT.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[PythonToken.GTGT.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[PythonToken.EXP.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[PythonToken.FLOORDIV.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[PythonToken.LTEQ.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[PythonToken.GTEQ.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[PythonToken.EQEQ.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[PythonToken.NOTEQ.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[PythonToken.NOTEQ_OLD.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[PythonToken.RARROW.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[PythonToken.PLUS.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[PythonToken.MINUS.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[PythonToken.MULT.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[PythonToken.DIV.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[PythonToken.MOD.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[PythonToken.AND.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[PythonToken.OR.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[PythonToken.XOR.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[PythonToken.TILDE.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr[PythonToken.LT.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr[PythonToken.GT.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr[PythonToken.AT.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr[PythonToken.COLON.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr[PythonToken.TICK.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr[PythonToken.EQ.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr[PythonToken.COLONEQ.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr[PythonToken.LPAREN.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr[PythonToken.RPAREN.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr[PythonToken.LBRACE.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr[PythonToken.RBRACE.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr[PythonToken.LBRACK.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                iArr[PythonToken.RBRACK.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr[PythonToken.SEMICOLON.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr[PythonToken.COMMA.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                iArr[PythonToken.DOT.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                iArr[PythonToken.AND_KEYWORD.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                iArr[PythonToken.AS.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                iArr[PythonToken.ASSERT.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                iArr[PythonToken.BREAK.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                iArr[PythonToken.CLASS.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                iArr[PythonToken.CONTINUE.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                iArr[PythonToken.DEF.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                iArr[PythonToken.DEL.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                iArr[PythonToken.ELIF.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                iArr[PythonToken.ELSE.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                iArr[PythonToken.EXCEPT.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                iArr[PythonToken.EXEC.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                iArr[PythonToken.FINALLY.ordinal()] = 65;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                iArr[PythonToken.FOR.ordinal()] = 66;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                iArr[PythonToken.FROM.ordinal()] = 67;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                iArr[PythonToken.GLOBAL.ordinal()] = 68;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                iArr[PythonToken.IF.ordinal()] = 69;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                iArr[PythonToken.IMPORT.ordinal()] = 70;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                iArr[PythonToken.IN.ordinal()] = 71;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                iArr[PythonToken.IS.ordinal()] = 72;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                iArr[PythonToken.LAMBDA.ordinal()] = 73;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                iArr[PythonToken.NOT_KEYWORD.ordinal()] = 74;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                iArr[PythonToken.OR_KEYWORD.ordinal()] = 75;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                iArr[PythonToken.PASS.ordinal()] = 76;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                iArr[PythonToken.PRINT.ordinal()] = 77;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                iArr[PythonToken.RAISE.ordinal()] = 78;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                iArr[PythonToken.RETURN.ordinal()] = 79;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                iArr[PythonToken.TRY.ordinal()] = 80;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                iArr[PythonToken.WHILE.ordinal()] = 81;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                iArr[PythonToken.YIELD.ordinal()] = 82;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                iArr[PythonToken.CHAR.ordinal()] = 83;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                iArr[PythonToken.DOUBLE.ordinal()] = 84;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                iArr[PythonToken.FLOAT.ordinal()] = 85;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                iArr[PythonToken.INT.ordinal()] = 86;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                iArr[PythonToken.LONG.ordinal()] = 87;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                iArr[PythonToken.SHORT.ordinal()] = 88;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                iArr[PythonToken.SIGNED.ordinal()] = 89;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                iArr[PythonToken.UNSIGNED.ordinal()] = 90;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                iArr[PythonToken.VOID.ordinal()] = 91;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                iArr[PythonToken.METHOD.ordinal()] = 92;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                iArr[PythonToken.TRUE.ordinal()] = 93;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                iArr[PythonToken.FALSE.ordinal()] = 94;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                iArr[PythonToken.NONE.ordinal()] = 95;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                iArr[PythonToken.DECORATOR.ordinal()] = 96;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                iArr[PythonToken.DOUBLE_QUOTED_STRING.ordinal()] = 97;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                iArr[PythonToken.SINGLE_QUOTED_STRING.ordinal()] = 98;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                iArr[PythonToken.LONG_DOUBLE_QUOTED_STRING.ordinal()] = 99;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                iArr[PythonToken.LONG_SINGLE_QUOTED_STRING.ordinal()] = 100;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                iArr[PythonToken.LINE_COMMENT.ordinal()] = 101;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                iArr[PythonToken.IDENTIFIER.ordinal()] = 102;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                iArr[PythonToken.WHITESPACE.ordinal()] = 103;
            } catch (NoSuchFieldError unused103) {
            }
            try {
                iArr[PythonToken.BAD_CHARACTER.ordinal()] = 104;
            } catch (NoSuchFieldError unused104) {
            }
            try {
                iArr[PythonToken.EOF.ordinal()] = 105;
            } catch (NoSuchFieldError unused105) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ PythonStyler(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PythonStyler() {
    }

    public static final /* synthetic */ PythonStyler access$getPythonStyler$cp() {
        return pythonStyler;
    }

    public static final /* synthetic */ void access$setPythonStyler$cp(PythonStyler pythonStyler2) {
        pythonStyler = pythonStyler2;
    }

    /* compiled from: PythonStyler.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/blacksquircle/ui/language/python/styler/PythonStyler$Companion;", "", "()V", "pythonStyler", "Lcom/blacksquircle/ui/language/python/styler/PythonStyler;", "getInstance", "language-python"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final PythonStyler getInstance() {
            PythonStyler access$getPythonStyler$cp = PythonStyler.access$getPythonStyler$cp();
            if (access$getPythonStyler$cp != null) {
                return access$getPythonStyler$cp;
            }
            PythonStyler pythonStyler = new PythonStyler(null);
            Companion companion = PythonStyler.INSTANCE;
            PythonStyler.access$setPythonStyler$cp(pythonStyler);
            return pythonStyler;
        }
    }

    @NotNull
    public List execute(@NotNull TextStructure structure) {
        int i;
        Intrinsics.checkNotNullParameter(structure, "structure");
        String obj = structure.getText().toString();
        List arrayList = new ArrayList();
        PythonLexer pythonLexer = new PythonLexer(new StringReader(obj));
        while (true) {
            try {
                i = WhenMappings.$EnumSwitchMapping$0[pythonLexer.advance().ordinal()];
            } catch (Throwable th) {
                th.printStackTrace();
            }
            if (i == 105) {
                return arrayList;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    arrayList.add(new SyntaxHighlightResult(TokenType.NUMBER, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    arrayList.add(new SyntaxHighlightResult(TokenType.OPERATOR, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                    break;
                default:
                    switch (i) {
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                        case 62:
                        case 63:
                        case 64:
                        case 65:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                        case 76:
                        case 77:
                        case 78:
                        case 79:
                        case 80:
                        case 81:
                        case 82:
                            arrayList.add(new SyntaxHighlightResult(TokenType.KEYWORD, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                        case 83:
                        case 84:
                        case 85:
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                            arrayList.add(new SyntaxHighlightResult(TokenType.TYPE, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                        case 92:
                            arrayList.add(new SyntaxHighlightResult(TokenType.METHOD, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                        case 93:
                        case 94:
                        case 95:
                            arrayList.add(new SyntaxHighlightResult(TokenType.LANG_CONST, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                        case 96:
                            arrayList.add(new SyntaxHighlightResult(TokenType.PREPROCESSOR, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                        case 97:
                        case 98:
                        case 99:
                        case 100:
                            arrayList.add(new SyntaxHighlightResult(TokenType.STRING, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                        case 101:
                            arrayList.add(new SyntaxHighlightResult(TokenType.COMMENT, pythonLexer.getTokenStart(), pythonLexer.getTokenEnd()));
                            break;
                    }
            }
        }
    }
}
