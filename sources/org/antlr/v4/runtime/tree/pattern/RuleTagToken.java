package org.antlr.v4.runtime.tree.pattern;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class RuleTagToken implements Token {
    private final int bypassTokenType;
    private final String label;
    private final String ruleName;

    public RuleTagToken(String ruleName, int bypassTokenType) {
        this(ruleName, bypassTokenType, null);
    }

    public RuleTagToken(String ruleName, int bypassTokenType, String label) {
        if (ruleName == null || ruleName.isEmpty()) {
            throw new IllegalArgumentException("ruleName cannot be null or empty.");
        }
        this.ruleName = ruleName;
        this.bypassTokenType = bypassTokenType;
        this.label = label;
    }

    public final String getRuleName() {
        return this.ruleName;
    }

    public final String getLabel() {
        return this.label;
    }

    public int getChannel() {
        return 0;
    }

    public String getText() {
        return this.label != null ? "<" + this.label + ":" + this.ruleName + ">" : "<" + this.ruleName + ">";
    }

    public int getType() {
        return this.bypassTokenType;
    }

    public int getLine() {
        return 0;
    }

    public int getCharPositionInLine() {
        return -1;
    }

    public int getTokenIndex() {
        return -1;
    }

    public int getStartIndex() {
        return -1;
    }

    public int getStopIndex() {
        return -1;
    }

    public TokenSource getTokenSource() {
        return null;
    }

    public CharStream getInputStream() {
        return null;
    }

    public String toString() {
        return this.ruleName + ":" + this.bypassTokenType;
    }
}
