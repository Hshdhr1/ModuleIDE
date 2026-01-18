package org.antlr.v4.runtime;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class CommonTokenFactory implements TokenFactory {
    public static final TokenFactory DEFAULT = new CommonTokenFactory();
    protected final boolean copyText;

    public CommonTokenFactory(boolean copyText) {
        this.copyText = copyText;
    }

    public CommonTokenFactory() {
        this(false);
    }

    public CommonToken create(Pair pair, int type, String text, int channel, int start, int stop, int line, int charPositionInLine) {
        CommonToken t = new CommonToken(pair, type, channel, start, stop);
        t.setLine(line);
        t.setCharPositionInLine(charPositionInLine);
        if (text != null) {
            t.setText(text);
        } else if (this.copyText && pair.b != null) {
            t.setText(((CharStream) pair.b).getText(Interval.of(start, stop)));
        }
        return t;
    }

    public CommonToken create(int type, String text) {
        return new CommonToken(type, text);
    }
}
