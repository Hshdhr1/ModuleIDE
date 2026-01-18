package org.antlr.v4.runtime;

import java.util.Locale;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LexerNoViableAltException extends RecognitionException {
    private final ATNConfigSet deadEndConfigs;
    private final int startIndex;

    public LexerNoViableAltException(Lexer lexer, CharStream input, int startIndex, ATNConfigSet deadEndConfigs) {
        super(lexer, input, null);
        this.startIndex = startIndex;
        this.deadEndConfigs = deadEndConfigs;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public ATNConfigSet getDeadEndConfigs() {
        return this.deadEndConfigs;
    }

    public CharStream getInputStream() {
        return (CharStream) super.getInputStream();
    }

    public String toString() {
        String symbol = "";
        if (this.startIndex >= 0 && this.startIndex < getInputStream().size()) {
            symbol = Utils.escapeWhitespace(getInputStream().getText(Interval.of(this.startIndex, this.startIndex)), false);
        }
        return String.format(Locale.getDefault(), "%s('%s')", new Object[]{LexerNoViableAltException.class.getSimpleName(), symbol});
    }
}
