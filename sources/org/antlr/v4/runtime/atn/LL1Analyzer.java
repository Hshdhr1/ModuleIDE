package org.antlr.v4.runtime.atn;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.IntSet;
import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LL1Analyzer {
    public static final int HIT_PRED = 0;
    public final ATN atn;

    public LL1Analyzer(ATN atn) {
        this.atn = atn;
    }

    public IntervalSet[] getDecisionLookahead(ATNState s) {
        if (s == null) {
            return null;
        }
        IntervalSet[] look = new IntervalSet[s.getNumberOfTransitions()];
        for (int alt = 0; alt < s.getNumberOfTransitions(); alt++) {
            look[alt] = new IntervalSet(new int[0]);
            _LOOK(s.transition(alt).target, null, PredictionContext.EMPTY, look[alt], new HashSet(), new BitSet(), false, false);
            if (look[alt].size() == 0 || look[alt].contains(0)) {
                look[alt] = null;
            }
        }
        return look;
    }

    public IntervalSet LOOK(ATNState s, RuleContext ctx) {
        return LOOK(s, null, ctx);
    }

    public IntervalSet LOOK(ATNState s, ATNState stopState, RuleContext ctx) {
        IntervalSet r = new IntervalSet(new int[0]);
        PredictionContext lookContext = ctx != null ? PredictionContext.fromRuleContext(s.atn, ctx) : null;
        _LOOK(s, stopState, lookContext, r, new HashSet(), new BitSet(), true, true);
        return r;
    }

    protected void _LOOK(ATNState s, ATNState stopState, PredictionContext ctx, IntervalSet look, Set set, BitSet calledRuleStack, boolean seeThruPreds, boolean addEOF) {
        ATNConfig c = new ATNConfig(s, 0, ctx);
        if (set.add(c)) {
            if (s == stopState) {
                if (ctx == null) {
                    look.add(-2);
                    return;
                } else if (ctx.isEmpty() && addEOF) {
                    look.add(-1);
                    return;
                }
            }
            if (s instanceof RuleStopState) {
                if (ctx == null) {
                    look.add(-2);
                    return;
                }
                if (ctx.isEmpty() && addEOF) {
                    look.add(-1);
                    return;
                }
                if (ctx != PredictionContext.EMPTY) {
                    boolean removed = calledRuleStack.get(s.ruleIndex);
                    try {
                        calledRuleStack.clear(s.ruleIndex);
                        for (int i = 0; i < ctx.size(); i++) {
                            ATNState returnState = (ATNState) this.atn.states.get(ctx.getReturnState(i));
                            _LOOK(returnState, stopState, ctx.getParent(i), look, set, calledRuleStack, seeThruPreds, addEOF);
                        }
                        if (removed) {
                            return;
                        } else {
                            return;
                        }
                    } finally {
                        if (removed) {
                            calledRuleStack.set(s.ruleIndex);
                        }
                    }
                }
            }
            int n = s.getNumberOfTransitions();
            for (int i2 = 0; i2 < n; i2++) {
                Transition t = s.transition(i2);
                if (t.getClass() == RuleTransition.class) {
                    if (calledRuleStack.get(((RuleTransition) t).target.ruleIndex)) {
                        continue;
                    } else {
                        PredictionContext newContext = SingletonPredictionContext.create(ctx, ((RuleTransition) t).followState.stateNumber);
                        try {
                            calledRuleStack.set(((RuleTransition) t).target.ruleIndex);
                            _LOOK(t.target, stopState, newContext, look, set, calledRuleStack, seeThruPreds, addEOF);
                        } finally {
                            calledRuleStack.clear(((RuleTransition) t).target.ruleIndex);
                        }
                    }
                } else if (t instanceof AbstractPredicateTransition) {
                    if (seeThruPreds) {
                        _LOOK(t.target, stopState, ctx, look, set, calledRuleStack, seeThruPreds, addEOF);
                    } else {
                        look.add(0);
                    }
                } else if (t.isEpsilon()) {
                    _LOOK(t.target, stopState, ctx, look, set, calledRuleStack, seeThruPreds, addEOF);
                } else if (t.getClass() == WildcardTransition.class) {
                    look.addAll((IntSet) IntervalSet.of(1, this.atn.maxTokenType));
                } else {
                    IntervalSet set2 = t.label();
                    if (set2 != null) {
                        if (t instanceof NotSetTransition) {
                            set2 = set2.complement((IntSet) IntervalSet.of(1, this.atn.maxTokenType));
                        }
                        look.addAll((IntSet) set2);
                    }
                }
            }
        }
    }
}
