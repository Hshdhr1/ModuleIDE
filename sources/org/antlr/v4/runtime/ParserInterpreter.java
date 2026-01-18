package org.antlr.v4.runtime;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.atn.ActionTransition;
import org.antlr.v4.runtime.atn.AtomTransition;
import org.antlr.v4.runtime.atn.DecisionState;
import org.antlr.v4.runtime.atn.LoopEndState;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PrecedencePredicateTransition;
import org.antlr.v4.runtime.atn.PredicateTransition;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.atn.RuleStartState;
import org.antlr.v4.runtime.atn.RuleTransition;
import org.antlr.v4.runtime.atn.StarLoopEntryState;
import org.antlr.v4.runtime.atn.Transition;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParserInterpreter extends Parser {
    protected final Deque _parentContextStack;
    protected final ATN atn;
    protected final DFA[] decisionToDFA;
    protected final String grammarFileName;
    protected int overrideDecision;
    protected int overrideDecisionAlt;
    protected int overrideDecisionInputIndex;
    protected boolean overrideDecisionReached;
    protected InterpreterRuleContext overrideDecisionRoot;
    protected InterpreterRuleContext rootContext;
    protected final String[] ruleNames;
    protected final PredictionContextCache sharedContextCache;

    @Deprecated
    protected final String[] tokenNames;
    private final Vocabulary vocabulary;

    @Deprecated
    public ParserInterpreter(String grammarFileName, Collection collection, Collection collection2, ATN atn, TokenStream input) {
        this(grammarFileName, VocabularyImpl.fromTokenNames((String[]) collection.toArray(new String[collection.size()])), collection2, atn, input);
    }

    public ParserInterpreter(String grammarFileName, Vocabulary vocabulary, Collection collection, ATN atn, TokenStream input) {
        super(input);
        this.sharedContextCache = new PredictionContextCache();
        this._parentContextStack = new ArrayDeque();
        this.overrideDecision = -1;
        this.overrideDecisionInputIndex = -1;
        this.overrideDecisionAlt = -1;
        this.overrideDecisionReached = false;
        this.overrideDecisionRoot = null;
        this.grammarFileName = grammarFileName;
        this.atn = atn;
        this.tokenNames = new String[atn.maxTokenType];
        for (int i = 0; i < this.tokenNames.length; i++) {
            this.tokenNames[i] = vocabulary.getDisplayName(i);
        }
        this.ruleNames = (String[]) collection.toArray(new String[collection.size()]);
        this.vocabulary = vocabulary;
        int numberOfDecisions = atn.getNumberOfDecisions();
        this.decisionToDFA = new DFA[numberOfDecisions];
        for (int i2 = 0; i2 < numberOfDecisions; i2++) {
            DecisionState decisionState = atn.getDecisionState(i2);
            this.decisionToDFA[i2] = new DFA(decisionState, i2);
        }
        setInterpreter(new ParserATNSimulator(this, atn, this.decisionToDFA, this.sharedContextCache));
    }

    public void reset() {
        super.reset();
        this.overrideDecisionReached = false;
        this.overrideDecisionRoot = null;
    }

    public ATN getATN() {
        return this.atn;
    }

    @Deprecated
    public String[] getTokenNames() {
        return this.tokenNames;
    }

    public Vocabulary getVocabulary() {
        return this.vocabulary;
    }

    public String[] getRuleNames() {
        return this.ruleNames;
    }

    public String getGrammarFileName() {
        return this.grammarFileName;
    }

    public ParserRuleContext parse(int startRuleIndex) {
        RuleStartState startRuleStartState = this.atn.ruleToStartState[startRuleIndex];
        this.rootContext = createInterpreterRuleContext(null, -1, startRuleIndex);
        if (startRuleStartState.isLeftRecursiveRule) {
            enterRecursionRule(this.rootContext, startRuleStartState.stateNumber, startRuleIndex, 0);
        } else {
            enterRule(this.rootContext, startRuleStartState.stateNumber, startRuleIndex);
        }
        while (true) {
            ATNState p = getATNState();
            switch (p.getStateType()) {
                case 7:
                    if (this._ctx.isEmpty()) {
                        if (startRuleStartState.isLeftRecursiveRule) {
                            ParserRuleContext result = this._ctx;
                            Pair<ParserRuleContext, Integer> parentContext = (Pair) this._parentContextStack.pop();
                            unrollRecursionContexts((ParserRuleContext) parentContext.a);
                            return result;
                        }
                        exitRule();
                        ParserRuleContext result2 = this.rootContext;
                        return result2;
                    }
                    visitRuleStopState(p);
                    break;
                default:
                    try {
                        visitState(p);
                        break;
                    } catch (RecognitionException e) {
                        setState(this.atn.ruleToStopState[p.ruleIndex].stateNumber);
                        getContext().exception = e;
                        getErrorHandler().reportError(this, e);
                        recover(e);
                        break;
                    }
            }
        }
    }

    public void enterRecursionRule(ParserRuleContext localctx, int state, int ruleIndex, int precedence) {
        Pair<ParserRuleContext, Integer> pair = new Pair(this._ctx, Integer.valueOf(localctx.invokingState));
        this._parentContextStack.push(pair);
        super.enterRecursionRule(localctx, state, ruleIndex, precedence);
    }

    protected ATNState getATNState() {
        return (ATNState) this.atn.states.get(getState());
    }

    protected void visitState(ATNState p) {
        int predictedAlt = 1;
        if (p instanceof DecisionState) {
            predictedAlt = visitDecisionState((DecisionState) p);
        }
        Transition transition = p.transition(predictedAlt - 1);
        switch (transition.getSerializationType()) {
            case 1:
                if (p.getStateType() == 10 && ((StarLoopEntryState) p).isPrecedenceDecision && !(transition.target instanceof LoopEndState)) {
                    InterpreterRuleContext localctx = createInterpreterRuleContext((ParserRuleContext) ((Pair) this._parentContextStack.peek()).a, ((Integer) ((Pair) this._parentContextStack.peek()).b).intValue(), this._ctx.getRuleIndex());
                    pushNewRecursionContext(localctx, this.atn.ruleToStartState[p.ruleIndex].stateNumber, this._ctx.getRuleIndex());
                    break;
                }
                break;
            case 2:
            case 7:
            case 8:
                if (!transition.matches(this._input.LA(1), 1, 65535)) {
                    recoverInline();
                }
                matchWildcard();
                break;
            case 3:
                RuleStartState ruleStartState = (RuleStartState) transition.target;
                int ruleIndex = ruleStartState.ruleIndex;
                InterpreterRuleContext newctx = createInterpreterRuleContext(this._ctx, p.stateNumber, ruleIndex);
                if (ruleStartState.isLeftRecursiveRule) {
                    enterRecursionRule(newctx, ruleStartState.stateNumber, ruleIndex, ((RuleTransition) transition).precedence);
                    break;
                } else {
                    enterRule(newctx, transition.target.stateNumber, ruleIndex);
                    break;
                }
            case 4:
                PredicateTransition predicateTransition = (PredicateTransition) transition;
                if (!sempred(this._ctx, predicateTransition.ruleIndex, predicateTransition.predIndex)) {
                    throw new FailedPredicateException(this);
                }
                break;
            case 5:
                match(((AtomTransition) transition).label);
                break;
            case 6:
                ActionTransition actionTransition = (ActionTransition) transition;
                action(this._ctx, actionTransition.ruleIndex, actionTransition.actionIndex);
                break;
            case 9:
                matchWildcard();
                break;
            case 10:
                if (!precpred(this._ctx, ((PrecedencePredicateTransition) transition).precedence)) {
                    throw new FailedPredicateException(this, String.format("precpred(_ctx, %d)", new Object[]{Integer.valueOf(((PrecedencePredicateTransition) transition).precedence)}));
                }
                break;
            default:
                throw new UnsupportedOperationException("Unrecognized ATN transition type.");
        }
        setState(transition.target.stateNumber);
    }

    protected int visitDecisionState(DecisionState p) {
        if (p.getNumberOfTransitions() <= 1) {
            return 1;
        }
        getErrorHandler().sync(this);
        int decision = p.decision;
        if (decision == this.overrideDecision && this._input.index() == this.overrideDecisionInputIndex && !this.overrideDecisionReached) {
            int predictedAlt = this.overrideDecisionAlt;
            this.overrideDecisionReached = true;
            return predictedAlt;
        }
        int predictedAlt2 = ((ParserATNSimulator) getInterpreter()).adaptivePredict(this._input, decision, this._ctx);
        return predictedAlt2;
    }

    protected InterpreterRuleContext createInterpreterRuleContext(ParserRuleContext parent, int invokingStateNumber, int ruleIndex) {
        return new InterpreterRuleContext(parent, invokingStateNumber, ruleIndex);
    }

    protected void visitRuleStopState(ATNState p) {
        RuleStartState ruleStartState = this.atn.ruleToStartState[p.ruleIndex];
        if (ruleStartState.isLeftRecursiveRule) {
            Pair<ParserRuleContext, Integer> parentContext = (Pair) this._parentContextStack.pop();
            unrollRecursionContexts((ParserRuleContext) parentContext.a);
            setState(((Integer) parentContext.b).intValue());
        } else {
            exitRule();
        }
        RuleTransition ruleTransition = (RuleTransition) ((ATNState) this.atn.states.get(getState())).transition(0);
        setState(ruleTransition.followState.stateNumber);
    }

    public void addDecisionOverride(int decision, int tokenIndex, int forcedAlt) {
        this.overrideDecision = decision;
        this.overrideDecisionInputIndex = tokenIndex;
        this.overrideDecisionAlt = forcedAlt;
    }

    public InterpreterRuleContext getOverrideDecisionRoot() {
        return this.overrideDecisionRoot;
    }

    protected void recover(RecognitionException e) {
        int i = this._input.index();
        getErrorHandler().recover(this, e);
        if (this._input.index() == i) {
            if (e instanceof InputMismatchException) {
                InputMismatchException ime = (InputMismatchException) e;
                Token tok = e.getOffendingToken();
                int expectedTokenType = 0;
                if (!ime.getExpectedTokens().isNil()) {
                    expectedTokenType = ime.getExpectedTokens().getMinElement();
                }
                Token errToken = getTokenFactory().create(new Pair(tok.getTokenSource(), tok.getTokenSource().getInputStream()), expectedTokenType, tok.getText(), 0, -1, -1, tok.getLine(), tok.getCharPositionInLine());
                this._ctx.addErrorNode(createErrorNode(this._ctx, errToken));
                return;
            }
            Token tok2 = e.getOffendingToken();
            Token errToken2 = getTokenFactory().create(new Pair(tok2.getTokenSource(), tok2.getTokenSource().getInputStream()), 0, tok2.getText(), 0, -1, -1, tok2.getLine(), tok2.getCharPositionInLine());
            this._ctx.addErrorNode(createErrorNode(this._ctx, errToken2));
        }
    }

    protected Token recoverInline() {
        return this._errHandler.recoverInline(this);
    }

    public InterpreterRuleContext getRootContext() {
        return this.rootContext;
    }
}
