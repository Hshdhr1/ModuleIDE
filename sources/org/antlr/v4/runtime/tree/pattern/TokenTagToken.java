package org.antlr.v4.runtime.tree.pattern;

import org.antlr.v4.runtime.CommonToken;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class TokenTagToken extends CommonToken {
    private final String label;
    private final String tokenName;

    public TokenTagToken(String tokenName, int type) {
        this(tokenName, type, null);
    }

    public TokenTagToken(String tokenName, int type, String label) {
        super(type);
        this.tokenName = tokenName;
        this.label = label;
    }

    public final String getTokenName() {
        return this.tokenName;
    }

    public final String getLabel() {
        return this.label;
    }

    public String getText() {
        return this.label != null ? "<" + this.label + ":" + this.tokenName + ">" : "<" + this.tokenName + ">";
    }

    public String toString() {
        return this.tokenName + ":" + this.type;
    }
}
