package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class InterpreterRuleContext extends ParserRuleContext {
    protected int ruleIndex;

    public InterpreterRuleContext() {
        this.ruleIndex = -1;
    }

    public InterpreterRuleContext(ParserRuleContext parent, int invokingStateNumber, int ruleIndex) {
        super(parent, invokingStateNumber);
        this.ruleIndex = -1;
        this.ruleIndex = ruleIndex;
    }

    public int getRuleIndex() {
        return this.ruleIndex;
    }
}
