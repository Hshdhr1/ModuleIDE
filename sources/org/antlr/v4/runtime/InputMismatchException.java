package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class InputMismatchException extends RecognitionException {
    public InputMismatchException(Parser recognizer) {
        super(recognizer, recognizer.getInputStream(), recognizer._ctx);
        setOffendingToken(recognizer.getCurrentToken());
    }

    public InputMismatchException(Parser recognizer, int state, ParserRuleContext ctx) {
        super(recognizer, recognizer.getInputStream(), ctx);
        setOffendingState(state);
        setOffendingToken(recognizer.getCurrentToken());
    }
}
