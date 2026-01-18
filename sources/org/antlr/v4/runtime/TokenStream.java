package org.antlr.v4.runtime;

import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface TokenStream extends IntStream {
    Token LT(int i);

    Token get(int i);

    String getText();

    String getText(RuleContext ruleContext);

    String getText(Token token, Token token2);

    String getText(Interval interval);

    TokenSource getTokenSource();
}
