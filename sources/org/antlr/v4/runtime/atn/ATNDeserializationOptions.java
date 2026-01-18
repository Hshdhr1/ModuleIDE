package org.antlr.v4.runtime.atn;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ATNDeserializationOptions {
    private static final ATNDeserializationOptions defaultOptions = new ATNDeserializationOptions();
    private boolean generateRuleBypassTransitions;
    private boolean readOnly;
    private boolean verifyATN;

    static {
        defaultOptions.makeReadOnly();
    }

    public ATNDeserializationOptions() {
        this.verifyATN = true;
        this.generateRuleBypassTransitions = false;
    }

    public ATNDeserializationOptions(ATNDeserializationOptions options) {
        this.verifyATN = options.verifyATN;
        this.generateRuleBypassTransitions = options.generateRuleBypassTransitions;
    }

    public static ATNDeserializationOptions getDefaultOptions() {
        return defaultOptions;
    }

    public final boolean isReadOnly() {
        return this.readOnly;
    }

    public final void makeReadOnly() {
        this.readOnly = true;
    }

    public final boolean isVerifyATN() {
        return this.verifyATN;
    }

    public final void setVerifyATN(boolean verifyATN) {
        throwIfReadOnly();
        this.verifyATN = verifyATN;
    }

    public final boolean isGenerateRuleBypassTransitions() {
        return this.generateRuleBypassTransitions;
    }

    public final void setGenerateRuleBypassTransitions(boolean generateRuleBypassTransitions) {
        throwIfReadOnly();
        this.generateRuleBypassTransitions = generateRuleBypassTransitions;
    }

    protected void throwIfReadOnly() {
        if (isReadOnly()) {
            throw new IllegalStateException("The object is read only.");
        }
    }
}
