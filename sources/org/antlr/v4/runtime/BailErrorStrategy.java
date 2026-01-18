package org.antlr.v4.runtime;

import org.antlr.v4.runtime.misc.ParseCancellationException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class BailErrorStrategy extends DefaultErrorStrategy {
    public void recover(Parser recognizer, RecognitionException e) {
        for (ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent()) {
            context.exception = e;
        }
        throw new ParseCancellationException((Throwable) e);
    }

    public Token recoverInline(Parser recognizer) throws RecognitionException {
        InputMismatchException e = new InputMismatchException(recognizer);
        for (ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent()) {
            context.exception = e;
        }
        throw new ParseCancellationException((Throwable) e);
    }

    public void sync(Parser recognizer) {
    }
}
