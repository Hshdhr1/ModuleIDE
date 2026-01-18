package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface ANTLRErrorStrategy {
    boolean inErrorRecoveryMode(Parser parser);

    void recover(Parser parser, RecognitionException recognitionException) throws RecognitionException;

    Token recoverInline(Parser parser) throws RecognitionException;

    void reportError(Parser parser, RecognitionException recognitionException);

    void reportMatch(Parser parser);

    void reset(Parser parser);

    void sync(Parser parser) throws RecognitionException;
}
