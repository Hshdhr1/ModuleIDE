package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface Token {
    public static final int DEFAULT_CHANNEL = 0;
    public static final int EOF = -1;
    public static final int EPSILON = -2;
    public static final int HIDDEN_CHANNEL = 1;
    public static final int INVALID_TYPE = 0;
    public static final int MIN_USER_CHANNEL_VALUE = 2;
    public static final int MIN_USER_TOKEN_TYPE = 1;

    int getChannel();

    int getCharPositionInLine();

    CharStream getInputStream();

    int getLine();

    int getStartIndex();

    int getStopIndex();

    String getText();

    int getTokenIndex();

    TokenSource getTokenSource();

    int getType();
}
