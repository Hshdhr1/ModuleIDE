package org.antlr.v4.runtime.atn;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.IntSet;
import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ATN {
    public static final int INVALID_ALT_NUMBER = 0;
    public final ATNType grammarType;
    public LexerAction[] lexerActions;
    public final int maxTokenType;
    public RuleStartState[] ruleToStartState;
    public RuleStopState[] ruleToStopState;
    public int[] ruleToTokenType;
    public final List states = new ArrayList();
    public final List decisionToState = new ArrayList();
    public final Map modeNameToStartState = new LinkedHashMap();
    public final List modeToStartState = new ArrayList();

    public ATN(ATNType grammarType, int maxTokenType) {
        this.grammarType = grammarType;
        this.maxTokenType = maxTokenType;
    }

    public IntervalSet nextTokens(ATNState s, RuleContext ctx) {
        LL1Analyzer anal = new LL1Analyzer(this);
        IntervalSet next = anal.LOOK(s, ctx);
        return next;
    }

    public IntervalSet nextTokens(ATNState s) {
        if (s.nextTokenWithinRule != null) {
            return s.nextTokenWithinRule;
        }
        s.nextTokenWithinRule = nextTokens(s, null);
        s.nextTokenWithinRule.setReadonly(true);
        return s.nextTokenWithinRule;
    }

    public void addState(ATNState state) {
        if (state != null) {
            state.atn = this;
            state.stateNumber = this.states.size();
        }
        this.states.add(state);
    }

    public void removeState(ATNState state) {
        this.states.set(state.stateNumber, (Object) null);
    }

    public int defineDecisionState(DecisionState s) {
        this.decisionToState.add(s);
        s.decision = this.decisionToState.size() - 1;
        return s.decision;
    }

    public DecisionState getDecisionState(int decision) {
        if (this.decisionToState.isEmpty()) {
            return null;
        }
        return (DecisionState) this.decisionToState.get(decision);
    }

    public int getNumberOfDecisions() {
        return this.decisionToState.size();
    }

    public IntervalSet getExpectedTokens(int stateNumber, RuleContext context) {
        if (stateNumber < 0 || stateNumber >= this.states.size()) {
            throw new IllegalArgumentException("Invalid state number.");
        }
        ATNState s = (ATNState) this.states.get(stateNumber);
        IntervalSet following = nextTokens(s);
        if (!following.contains(-2)) {
            return following;
        }
        IntervalSet expected = new IntervalSet(new int[0]);
        expected.addAll((IntSet) following);
        expected.remove(-2);
        for (RuleContext ctx = context; ctx != null && ctx.invokingState >= 0 && following.contains(-2); ctx = ctx.parent) {
            ATNState invokingState = (ATNState) this.states.get(ctx.invokingState);
            RuleTransition rt = (RuleTransition) invokingState.transition(0);
            following = nextTokens(rt.followState);
            expected.addAll((IntSet) following);
            expected.remove(-2);
        }
        if (following.contains(-2)) {
            expected.add(-1);
        }
        return expected;
    }
}
