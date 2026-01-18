package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class RuleContextWithAltNum extends ParserRuleContext {
    public int altNum;

    public RuleContextWithAltNum() {
        this.altNum = 0;
    }

    public RuleContextWithAltNum(ParserRuleContext parent, int invokingStateNumber) {
        super(parent, invokingStateNumber);
    }

    public int getAltNumber() {
        return this.altNum;
    }

    public void setAltNumber(int altNum) {
        this.altNum = altNum;
    }
}
