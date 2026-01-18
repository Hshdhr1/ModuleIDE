package org.antlr.v4.runtime;

import java.util.BitSet;
import java.util.Iterator;
import org.antlr.v4.runtime.atn.ATNConfig;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class DiagnosticErrorListener extends BaseErrorListener {
    protected final boolean exactOnly;

    public DiagnosticErrorListener() {
        this(true);
    }

    public DiagnosticErrorListener(boolean exactOnly) {
        this.exactOnly = exactOnly;
    }

    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        if (!this.exactOnly || exact) {
            String decision = getDecisionDescription(recognizer, dfa);
            BitSet conflictingAlts = getConflictingAlts(ambigAlts, configs);
            String text = recognizer.getTokenStream().getText(Interval.of(startIndex, stopIndex));
            String message = String.format("reportAmbiguity d=%s: ambigAlts=%s, input='%s'", new Object[]{decision, conflictingAlts, text});
            recognizer.notifyErrorListeners(message);
        }
    }

    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        String decision = getDecisionDescription(recognizer, dfa);
        String text = recognizer.getTokenStream().getText(Interval.of(startIndex, stopIndex));
        String message = String.format("reportAttemptingFullContext d=%s, input='%s'", new Object[]{decision, text});
        recognizer.notifyErrorListeners(message);
    }

    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        String decision = getDecisionDescription(recognizer, dfa);
        String text = recognizer.getTokenStream().getText(Interval.of(startIndex, stopIndex));
        String message = String.format("reportContextSensitivity d=%s, input='%s'", new Object[]{decision, text});
        recognizer.notifyErrorListeners(message);
    }

    protected String getDecisionDescription(Parser recognizer, DFA dfa) {
        int decision = dfa.decision;
        int ruleIndex = dfa.atnStartState.ruleIndex;
        String[] ruleNames = recognizer.getRuleNames();
        if (ruleIndex < 0 || ruleIndex >= ruleNames.length) {
            return String.valueOf(decision);
        }
        String ruleName = ruleNames[ruleIndex];
        if (ruleName == null || ruleName.isEmpty()) {
            return String.valueOf(decision);
        }
        return String.format("%d (%s)", new Object[]{Integer.valueOf(decision), ruleName});
    }

    protected BitSet getConflictingAlts(BitSet reportedAlts, ATNConfigSet configs) {
        if (reportedAlts == null) {
            BitSet result = new BitSet();
            Iterator i$ = configs.iterator();
            while (i$.hasNext()) {
                ATNConfig config = (ATNConfig) i$.next();
                result.set(config.alt);
            }
            return result;
        }
        return reportedAlts;
    }
}
