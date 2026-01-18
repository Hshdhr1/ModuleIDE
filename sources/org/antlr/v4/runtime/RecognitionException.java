package org.antlr.v4.runtime;

import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class RecognitionException extends RuntimeException {
    private final RuleContext ctx;
    private final IntStream input;
    private int offendingState;
    private Token offendingToken;
    private final Recognizer recognizer;

    public RecognitionException(Recognizer recognizer, IntStream input, ParserRuleContext ctx) {
        this.offendingState = -1;
        this.recognizer = recognizer;
        this.input = input;
        this.ctx = ctx;
        if (recognizer != null) {
            this.offendingState = recognizer.getState();
        }
    }

    public RecognitionException(String message, Recognizer recognizer, IntStream input, ParserRuleContext ctx) {
        super(message);
        this.offendingState = -1;
        this.recognizer = recognizer;
        this.input = input;
        this.ctx = ctx;
        if (recognizer != null) {
            this.offendingState = recognizer.getState();
        }
    }

    public int getOffendingState() {
        return this.offendingState;
    }

    protected final void setOffendingState(int offendingState) {
        this.offendingState = offendingState;
    }

    public IntervalSet getExpectedTokens() {
        if (this.recognizer != null) {
            return this.recognizer.getATN().getExpectedTokens(this.offendingState, this.ctx);
        }
        return null;
    }

    public RuleContext getCtx() {
        return this.ctx;
    }

    public IntStream getInputStream() {
        return this.input;
    }

    public Token getOffendingToken() {
        return this.offendingToken;
    }

    protected final void setOffendingToken(Token offendingToken) {
        this.offendingToken = offendingToken;
    }

    public Recognizer getRecognizer() {
        return this.recognizer;
    }
}
