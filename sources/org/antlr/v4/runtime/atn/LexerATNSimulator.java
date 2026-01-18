package org.antlr.v4.runtime.atn;

import java.util.Iterator;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LexerATNSimulator extends ATNSimulator {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int MAX_DFA_EDGE = 127;
    public static final int MIN_DFA_EDGE = 0;
    public static final boolean debug = false;
    public static final boolean dfa_debug = false;
    protected int charPositionInLine;
    public final DFA[] decisionToDFA;
    protected int line;
    protected int mode;
    protected final SimState prevAccept;
    protected final Lexer recog;
    protected int startIndex;

    static {
        $assertionsDisabled = !LexerATNSimulator.class.desiredAssertionStatus();
    }

    protected static class SimState {
        protected DFAState dfaState;
        protected int index = -1;
        protected int line = 0;
        protected int charPos = -1;

        protected SimState() {
        }

        protected void reset() {
            this.index = -1;
            this.line = 0;
            this.charPos = -1;
            this.dfaState = null;
        }
    }

    public LexerATNSimulator(ATN atn, DFA[] decisionToDFA, PredictionContextCache sharedContextCache) {
        this(null, atn, decisionToDFA, sharedContextCache);
    }

    public LexerATNSimulator(Lexer recog, ATN atn, DFA[] decisionToDFA, PredictionContextCache sharedContextCache) {
        super(atn, sharedContextCache);
        this.startIndex = -1;
        this.line = 1;
        this.charPositionInLine = 0;
        this.mode = 0;
        this.prevAccept = new SimState();
        this.decisionToDFA = decisionToDFA;
        this.recog = recog;
    }

    public void copyState(LexerATNSimulator simulator) {
        this.charPositionInLine = simulator.charPositionInLine;
        this.line = simulator.line;
        this.mode = simulator.mode;
        this.startIndex = simulator.startIndex;
    }

    public int match(CharStream input, int mode) {
        int execATN;
        this.mode = mode;
        int mark = input.mark();
        try {
            this.startIndex = input.index();
            this.prevAccept.reset();
            DFA dfa = this.decisionToDFA[mode];
            if (dfa.s0 == null) {
                execATN = matchATN(input);
            } else {
                execATN = execATN(input, dfa.s0);
            }
            return execATN;
        } finally {
            input.release(mark);
        }
    }

    public void reset() {
        this.prevAccept.reset();
        this.startIndex = -1;
        this.line = 1;
        this.charPositionInLine = 0;
        this.mode = 0;
    }

    public void clearDFA() {
        for (int d = 0; d < this.decisionToDFA.length; d++) {
            this.decisionToDFA[d] = new DFA(this.atn.getDecisionState(d), d);
        }
    }

    protected int matchATN(CharStream input) {
        ATNState startState = (ATNState) this.atn.modeToStartState.get(this.mode);
        int i = this.mode;
        ATNConfigSet s0_closure = computeStartState(input, startState);
        boolean suppressEdge = s0_closure.hasSemanticContext;
        s0_closure.hasSemanticContext = false;
        DFAState next = addDFAState(s0_closure);
        if (!suppressEdge) {
            this.decisionToDFA[this.mode].s0 = next;
        }
        int predict = execATN(input, next);
        return predict;
    }

    protected int execATN(CharStream input, DFAState ds0) {
        if (ds0.isAcceptState) {
            captureSimState(this.prevAccept, input, ds0);
        }
        int t = input.LA(1);
        DFAState s = ds0;
        while (true) {
            DFAState target = getExistingTargetState(s, t);
            if (target == null) {
                target = computeTargetState(input, s, t);
            }
            if (target == ERROR) {
                break;
            }
            if (t != -1) {
                consume(input);
            }
            if (target.isAcceptState) {
                captureSimState(this.prevAccept, input, target);
                if (t == -1) {
                    break;
                }
            }
            t = input.LA(1);
            s = target;
        }
        return failOrAccept(this.prevAccept, input, s.configs, t);
    }

    protected DFAState getExistingTargetState(DFAState s, int t) {
        if (s.edges == null || t < 0 || t > 127) {
            return null;
        }
        return s.edges[t + 0];
    }

    protected DFAState computeTargetState(CharStream input, DFAState s, int t) {
        ATNConfigSet reach = new OrderedATNConfigSet();
        getReachableConfigSet(input, s.configs, reach, t);
        if (!reach.isEmpty()) {
            return addDFAEdge(s, t, reach);
        }
        if (!reach.hasSemanticContext) {
            addDFAEdge(s, t, ERROR);
        }
        return ERROR;
    }

    protected int failOrAccept(SimState prevAccept, CharStream input, ATNConfigSet reach, int t) {
        if (prevAccept.dfaState != null) {
            LexerActionExecutor lexerActionExecutor = prevAccept.dfaState.lexerActionExecutor;
            accept(input, lexerActionExecutor, this.startIndex, prevAccept.index, prevAccept.line, prevAccept.charPos);
            return prevAccept.dfaState.prediction;
        }
        if (t == -1 && input.index() == this.startIndex) {
            return -1;
        }
        throw new LexerNoViableAltException(this.recog, input, this.startIndex, reach);
    }

    protected void getReachableConfigSet(CharStream input, ATNConfigSet closure, ATNConfigSet reach, int t) {
        int skipAlt = 0;
        Iterator i$ = closure.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            boolean currentAltReachedAcceptState = c.alt == skipAlt;
            if (!currentAltReachedAcceptState || !((LexerATNConfig) c).hasPassedThroughNonGreedyDecision()) {
                int n = c.state.getNumberOfTransitions();
                int ti = 0;
                while (true) {
                    if (ti < n) {
                        Transition trans = c.state.transition(ti);
                        ATNState target = getReachableTarget(trans, t);
                        if (target != null) {
                            LexerActionExecutor lexerActionExecutor = ((LexerATNConfig) c).getLexerActionExecutor();
                            if (lexerActionExecutor != null) {
                                lexerActionExecutor = lexerActionExecutor.fixOffsetBeforeMatch(input.index() - this.startIndex);
                            }
                            boolean treatEofAsEpsilon = t == -1;
                            if (closure(input, new LexerATNConfig((LexerATNConfig) c, target, lexerActionExecutor), reach, currentAltReachedAcceptState, true, treatEofAsEpsilon)) {
                                skipAlt = c.alt;
                                break;
                            }
                        }
                        ti++;
                    }
                }
            }
        }
    }

    protected void accept(CharStream input, LexerActionExecutor lexerActionExecutor, int startIndex, int index, int line, int charPos) {
        input.seek(index);
        this.line = line;
        this.charPositionInLine = charPos;
        if (lexerActionExecutor != null && this.recog != null) {
            lexerActionExecutor.execute(this.recog, input, startIndex);
        }
    }

    protected ATNState getReachableTarget(Transition trans, int t) {
        if (trans.matches(t, 0, 1114111)) {
            return trans.target;
        }
        return null;
    }

    protected ATNConfigSet computeStartState(CharStream input, ATNState p) {
        PredictionContext initialContext = PredictionContext.EMPTY;
        ATNConfigSet configs = new OrderedATNConfigSet();
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            ATNState target = p.transition(i).target;
            LexerATNConfig c = new LexerATNConfig(target, i + 1, initialContext);
            closure(input, c, configs, false, false, false);
        }
        return configs;
    }

    protected boolean closure(CharStream input, LexerATNConfig config, ATNConfigSet configs, boolean currentAltReachedAcceptState, boolean speculative, boolean treatEofAsEpsilon) {
        if (config.state instanceof RuleStopState) {
            if (config.context == null || config.context.hasEmptyPath()) {
                if (config.context == null || config.context.isEmpty()) {
                    configs.add((ATNConfig) config);
                    return true;
                }
                configs.add((ATNConfig) new LexerATNConfig(config, config.state, PredictionContext.EMPTY));
                currentAltReachedAcceptState = true;
            }
            if (config.context != null && !config.context.isEmpty()) {
                for (int i = 0; i < config.context.size(); i++) {
                    if (config.context.getReturnState(i) != Integer.MAX_VALUE) {
                        PredictionContext newContext = config.context.getParent(i);
                        ATNState returnState = (ATNState) this.atn.states.get(config.context.getReturnState(i));
                        currentAltReachedAcceptState = closure(input, new LexerATNConfig(config, returnState, newContext), configs, currentAltReachedAcceptState, speculative, treatEofAsEpsilon);
                    }
                }
            }
            return currentAltReachedAcceptState;
        }
        if (!config.state.onlyHasEpsilonTransitions() && (!currentAltReachedAcceptState || !config.hasPassedThroughNonGreedyDecision())) {
            configs.add((ATNConfig) config);
        }
        ATNState p = config.state;
        for (int i2 = 0; i2 < p.getNumberOfTransitions(); i2++) {
            Transition t = p.transition(i2);
            LexerATNConfig c = getEpsilonTarget(input, config, t, configs, speculative, treatEofAsEpsilon);
            if (c != null) {
                currentAltReachedAcceptState = closure(input, c, configs, currentAltReachedAcceptState, speculative, treatEofAsEpsilon);
            }
        }
        return currentAltReachedAcceptState;
    }

    protected LexerATNConfig getEpsilonTarget(CharStream input, LexerATNConfig config, Transition t, ATNConfigSet configs, boolean speculative, boolean treatEofAsEpsilon) {
        switch (t.getSerializationType()) {
            case 1:
                LexerATNConfig c = new LexerATNConfig(config, t.target);
                return c;
            case 2:
            case 5:
            case 7:
                if (!treatEofAsEpsilon || !t.matches(-1, 0, 1114111)) {
                    return null;
                }
                LexerATNConfig c2 = new LexerATNConfig(config, t.target);
                return c2;
            case 3:
                RuleTransition ruleTransition = (RuleTransition) t;
                PredictionContext newContext = SingletonPredictionContext.create(config.context, ruleTransition.followState.stateNumber);
                LexerATNConfig c3 = new LexerATNConfig(config, t.target, newContext);
                return c3;
            case 4:
                PredicateTransition pt = (PredicateTransition) t;
                configs.hasSemanticContext = true;
                if (!evaluatePredicate(input, pt.ruleIndex, pt.predIndex, speculative)) {
                    return null;
                }
                LexerATNConfig c4 = new LexerATNConfig(config, t.target);
                return c4;
            case 6:
                if (config.context == null || config.context.hasEmptyPath()) {
                    LexerActionExecutor lexerActionExecutor = LexerActionExecutor.append(config.getLexerActionExecutor(), this.atn.lexerActions[((ActionTransition) t).actionIndex]);
                    LexerATNConfig c5 = new LexerATNConfig(config, t.target, lexerActionExecutor);
                    return c5;
                }
                LexerATNConfig c6 = new LexerATNConfig(config, t.target);
                return c6;
            case 8:
            case 9:
            default:
                return null;
            case 10:
                throw new UnsupportedOperationException("Precedence predicates are not supported in lexers.");
        }
    }

    protected boolean evaluatePredicate(CharStream input, int ruleIndex, int predIndex, boolean speculative) {
        if (this.recog == null) {
            return true;
        }
        if (!speculative) {
            return this.recog.sempred(null, ruleIndex, predIndex);
        }
        int savedCharPositionInLine = this.charPositionInLine;
        int savedLine = this.line;
        int index = input.index();
        int marker = input.mark();
        try {
            consume(input);
            return this.recog.sempred(null, ruleIndex, predIndex);
        } finally {
            this.charPositionInLine = savedCharPositionInLine;
            this.line = savedLine;
            input.seek(index);
            input.release(marker);
        }
    }

    protected void captureSimState(SimState settings, CharStream input, DFAState dfaState) {
        settings.index = input.index();
        settings.line = this.line;
        settings.charPos = this.charPositionInLine;
        settings.dfaState = dfaState;
    }

    protected DFAState addDFAEdge(DFAState from, int t, ATNConfigSet q) {
        boolean suppressEdge = q.hasSemanticContext;
        q.hasSemanticContext = false;
        DFAState to = addDFAState(q);
        if (!suppressEdge) {
            addDFAEdge(from, t, to);
        }
        return to;
    }

    protected void addDFAEdge(DFAState p, int t, DFAState q) {
        if (t >= 0 && t <= 127) {
            synchronized (p) {
                if (p.edges == null) {
                    p.edges = new DFAState[128];
                }
                p.edges[t + 0] = q;
            }
        }
    }

    protected DFAState addDFAState(ATNConfigSet configs) {
        if (!$assertionsDisabled && configs.hasSemanticContext) {
            throw new AssertionError();
        }
        DFAState proposed = new DFAState(configs);
        ATNConfig firstConfigWithRuleStopState = null;
        Iterator i$ = configs.iterator();
        while (true) {
            if (!i$.hasNext()) {
                break;
            }
            ATNConfig c = (ATNConfig) i$.next();
            if (c.state instanceof RuleStopState) {
                firstConfigWithRuleStopState = c;
                break;
            }
        }
        if (firstConfigWithRuleStopState != null) {
            proposed.isAcceptState = true;
            proposed.lexerActionExecutor = ((LexerATNConfig) firstConfigWithRuleStopState).getLexerActionExecutor();
            proposed.prediction = this.atn.ruleToTokenType[firstConfigWithRuleStopState.state.ruleIndex];
        }
        DFA dfa = this.decisionToDFA[this.mode];
        synchronized (dfa.states) {
            DFAState existing = (DFAState) dfa.states.get(proposed);
            if (existing == null) {
                proposed.stateNumber = dfa.states.size();
                configs.setReadonly(true);
                proposed.configs = configs;
                dfa.states.put(proposed, proposed);
                return proposed;
            }
            return existing;
        }
    }

    public final DFA getDFA(int mode) {
        return this.decisionToDFA[mode];
    }

    public String getText(CharStream input) {
        return input.getText(Interval.of(this.startIndex, input.index() - 1));
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    public void setCharPositionInLine(int charPositionInLine) {
        this.charPositionInLine = charPositionInLine;
    }

    public void consume(CharStream input) {
        int curChar = input.LA(1);
        if (curChar == 10) {
            this.line++;
            this.charPositionInLine = 0;
        } else {
            this.charPositionInLine++;
        }
        input.consume();
    }

    public String getTokenName(int t) {
        return t == -1 ? "EOF" : "'" + ((char) t) + "'";
    }
}
