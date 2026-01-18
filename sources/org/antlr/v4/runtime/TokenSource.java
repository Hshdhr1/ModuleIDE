package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface TokenSource {
    int getCharPositionInLine();

    CharStream getInputStream();

    int getLine();

    String getSourceName();

    TokenFactory getTokenFactory();

    Token nextToken();

    void setTokenFactory(TokenFactory tokenFactory);
}
