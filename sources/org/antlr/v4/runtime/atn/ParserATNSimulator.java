package org.antlr.v4.runtime.atn;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.antlr.v4.runtime.misc.DoubleKeyMap;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParserATNSimulator extends ATNSimulator {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final boolean TURN_OFF_LR_LOOP_ENTRY_BRANCH_OPT;
    public static final boolean debug = false;
    public static final boolean debug_list_atn_decisions = false;
    public static final boolean dfa_debug = false;
    public static final boolean retry_debug = false;
    protected DFA _dfa;
    protected TokenStream _input;
    protected ParserRuleContext _outerContext;
    protected int _startIndex;
    public final DFA[] decisionToDFA;
    protected DoubleKeyMap mergeCache;
    private PredictionMode mode;
    protected final Parser parser;

    static {
        $assertionsDisabled = !ParserATNSimulator.class.desiredAssertionStatus();
        TURN_OFF_LR_LOOP_ENTRY_BRANCH_OPT = Boolean.parseBoolean(getSafeEnv("TURN_OFF_LR_LOOP_ENTRY_BRANCH_OPT"));
    }

    public ParserATNSimulator(ATN atn, DFA[] decisionToDFA, PredictionContextCache sharedContextCache) {
        this(null, atn, decisionToDFA, sharedContextCache);
    }

    public ParserATNSimulator(Parser parser, ATN atn, DFA[] decisionToDFA, PredictionContextCache sharedContextCache) {
        super(atn, sharedContextCache);
        this.mode = PredictionMode.LL;
        this.parser = parser;
        this.decisionToDFA = decisionToDFA;
    }

    public void reset() {
    }

    public void clearDFA() {
        for (int d = 0; d < this.decisionToDFA.length; d++) {
            this.decisionToDFA[d] = new DFA(this.atn.getDecisionState(d), d);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int adaptivePredict(TokenStream tokenStream, int i, ParserRuleContext parserRuleContext) {
        DFAState dFAState;
        this._input = tokenStream;
        this._startIndex = tokenStream.index();
        this._outerContext = parserRuleContext;
        DFA dfa = this.decisionToDFA[i];
        this._dfa = dfa;
        int mark = tokenStream.mark();
        int i2 = this._startIndex;
        try {
            if (dfa.isPrecedenceDfa()) {
                dFAState = dfa.getPrecedenceStartState(this.parser.getPrecedence());
            } else {
                dFAState = dfa.s0;
            }
            if (dFAState == null) {
                if (parserRuleContext == null) {
                    parserRuleContext = ParserRuleContext.EMPTY;
                }
                ATNConfigSet computeStartState = computeStartState(dfa.atnStartState, ParserRuleContext.EMPTY, false);
                if (dfa.isPrecedenceDfa()) {
                    dfa.s0.configs = computeStartState;
                    dFAState = addDFAState(dfa, new DFAState(applyPrecedenceFilter(computeStartState)));
                    dfa.setPrecedenceStartState(this.parser.getPrecedence(), dFAState);
                } else {
                    dFAState = addDFAState(dfa, new DFAState(computeStartState));
                    dfa.s0 = dFAState;
                }
            }
            return execATN(dfa, dFAState, tokenStream, i2, parserRuleContext);
        } finally {
            this.mergeCache = null;
            this._dfa = null;
            tokenStream.seek(i2);
            tokenStream.release(mark);
        }
    }

    protected int execATN(DFA dfa, DFAState s0, TokenStream input, int startIndex, ParserRuleContext outerContext) {
        DFAState previousD = s0;
        int t = input.LA(1);
        while (true) {
            DFAState D = getExistingTargetState(previousD, t);
            if (D == null) {
                D = computeTargetState(dfa, previousD, t);
            }
            if (D == ERROR) {
                NoViableAltException e = noViableAlt(input, outerContext, previousD.configs, startIndex);
                input.seek(startIndex);
                int alt = getSynValidOrSemInvalidAltThatFinishedDecisionEntryRule(previousD.configs, outerContext);
                if (alt == 0) {
                    throw e;
                }
                return alt;
            }
            if (D.requiresFullContext && this.mode != PredictionMode.SLL) {
                BitSet conflictingAlts = D.configs.conflictingAlts;
                if (D.predicates != null) {
                    int conflictIndex = input.index();
                    if (conflictIndex != startIndex) {
                        input.seek(startIndex);
                    }
                    conflictingAlts = evalSemanticContext(D.predicates, outerContext, true);
                    if (conflictingAlts.cardinality() == 1) {
                        return conflictingAlts.nextSetBit(0);
                    }
                    if (conflictIndex != startIndex) {
                        input.seek(conflictIndex);
                    }
                }
                ATNConfigSet s0_closure = computeStartState(dfa.atnStartState, outerContext, true);
                reportAttemptingFullContext(dfa, conflictingAlts, D.configs, startIndex, input.index());
                return execATNWithFullContext(dfa, D, s0_closure, input, startIndex, outerContext);
            }
            if (D.isAcceptState) {
                if (D.predicates == null) {
                    return D.prediction;
                }
                int stopIndex = input.index();
                input.seek(startIndex);
                BitSet alts = evalSemanticContext(D.predicates, outerContext, true);
                switch (alts.cardinality()) {
                    case 0:
                        throw noViableAlt(input, outerContext, D.configs, startIndex);
                    case 1:
                        return alts.nextSetBit(0);
                    default:
                        reportAmbiguity(dfa, D, startIndex, stopIndex, false, alts, D.configs);
                        return alts.nextSetBit(0);
                }
            }
            previousD = D;
            if (t != -1) {
                input.consume();
                t = input.LA(1);
            }
        }
    }

    protected DFAState getExistingTargetState(DFAState previousD, int t) {
        DFAState[] edges = previousD.edges;
        if (edges == null || t + 1 < 0 || t + 1 >= edges.length) {
            return null;
        }
        return edges[t + 1];
    }

    protected DFAState computeTargetState(DFA dfa, DFAState previousD, int t) {
        ATNConfigSet reach = computeReachSet(previousD.configs, t, false);
        if (reach == null) {
            addDFAEdge(dfa, previousD, t, ERROR);
            return ERROR;
        }
        DFAState D = new DFAState(reach);
        int predictedAlt = getUniqueAlt(reach);
        if (predictedAlt != 0) {
            D.isAcceptState = true;
            D.configs.uniqueAlt = predictedAlt;
            D.prediction = predictedAlt;
        } else if (PredictionMode.hasSLLConflictTerminatingPrediction(this.mode, reach)) {
            D.configs.conflictingAlts = getConflictingAlts(reach);
            D.requiresFullContext = true;
            D.isAcceptState = true;
            D.prediction = D.configs.conflictingAlts.nextSetBit(0);
        }
        if (D.isAcceptState && D.configs.hasSemanticContext) {
            predicateDFAState(D, this.atn.getDecisionState(dfa.decision));
            if (D.predicates != null) {
                D.prediction = 0;
            }
        }
        return addDFAEdge(dfa, previousD, t, D);
    }

    protected void predicateDFAState(DFAState dfaState, DecisionState decisionState) {
        int nalts = decisionState.getNumberOfTransitions();
        BitSet altsToCollectPredsFrom = getConflictingAltsOrUniqueAlt(dfaState.configs);
        SemanticContext[] altToPred = getPredsForAmbigAlts(altsToCollectPredsFrom, dfaState.configs, nalts);
        if (altToPred != null) {
            dfaState.predicates = getPredicatePredictions(altsToCollectPredsFrom, altToPred);
            dfaState.prediction = 0;
        } else {
            dfaState.prediction = altsToCollectPredsFrom.nextSetBit(0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
    
        if (r8.uniqueAlt == 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
    
        reportContextSensitivity(r24, r7, r8, r28, r27.index());
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0093, code lost:
    
        reportAmbiguity(r24, r25, r28, r27.index(), r14, r8.getAlts(), r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
    
        return r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0076 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0010 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int execATNWithFullContext(org.antlr.v4.runtime.dfa.DFA r24, org.antlr.v4.runtime.dfa.DFAState r25, org.antlr.v4.runtime.atn.ATNConfigSet r26, org.antlr.v4.runtime.TokenStream r27, int r28, org.antlr.v4.runtime.ParserRuleContext r29) {
        /*
            r23 = this;
            r20 = 1
            r14 = 0
            r8 = 0
            r21 = r26
            r27.seek(r28)
            r5 = 1
            r0 = r27
            int r22 = r0.LA(r5)
        L10:
            r0 = r23
            r1 = r21
            r2 = r22
            r3 = r20
            org.antlr.v4.runtime.atn.ATNConfigSet r8 = r0.computeReachSet(r1, r2, r3)
            if (r8 != 0) goto L3f
            r0 = r23
            r1 = r27
            r2 = r29
            r3 = r21
            r4 = r28
            org.antlr.v4.runtime.NoViableAltException r19 = r0.noViableAlt(r1, r2, r3, r4)
            r27.seek(r28)
            r0 = r23
            r1 = r21
            r2 = r29
            int r17 = r0.getSynValidOrSemInvalidAltThatFinishedDecisionEntryRule(r1, r2)
            if (r17 == 0) goto L3e
            r7 = r17
        L3d:
            return r7
        L3e:
            throw r19
        L3f:
            java.util.Collection r18 = org.antlr.v4.runtime.atn.PredictionMode.getConflictingAltSubsets(r8)
            int r5 = getUniqueAlt(r8)
            r8.uniqueAlt = r5
            int r5 = r8.uniqueAlt
            if (r5 == 0) goto L61
            int r7 = r8.uniqueAlt
        L4f:
            int r5 = r8.uniqueAlt
            if (r5 == 0) goto L93
            int r10 = r27.index()
            r5 = r23
            r6 = r24
            r9 = r28
            r5.reportContextSensitivity(r6, r7, r8, r9, r10)
            goto L3d
        L61:
            r0 = r23
            org.antlr.v4.runtime.atn.PredictionMode r5 = r0.mode
            org.antlr.v4.runtime.atn.PredictionMode r6 = org.antlr.v4.runtime.atn.PredictionMode.LL_EXACT_AMBIG_DETECTION
            if (r5 == r6) goto L81
            int r7 = org.antlr.v4.runtime.atn.PredictionMode.resolvesToJustOneViableAlt(r18)
            if (r7 != 0) goto L4f
        L6f:
            r21 = r8
            r5 = -1
            r0 = r22
            if (r0 == r5) goto L10
            r27.consume()
            r5 = 1
            r0 = r27
            int r22 = r0.LA(r5)
            goto L10
        L81:
            boolean r5 = org.antlr.v4.runtime.atn.PredictionMode.allSubsetsConflict(r18)
            if (r5 == 0) goto L6f
            boolean r5 = org.antlr.v4.runtime.atn.PredictionMode.allSubsetsEqual(r18)
            if (r5 == 0) goto L6f
            r14 = 1
            int r7 = org.antlr.v4.runtime.atn.PredictionMode.getSingleViableAlt(r18)
            goto L4f
        L93:
            int r13 = r27.index()
            java.util.BitSet r15 = r8.getAlts()
            r9 = r23
            r10 = r24
            r11 = r25
            r12 = r28
            r16 = r8
            r9.reportAmbiguity(r10, r11, r12, r13, r14, r15, r16)
            goto L3d
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.v4.runtime.atn.ParserATNSimulator.execATNWithFullContext(org.antlr.v4.runtime.dfa.DFA, org.antlr.v4.runtime.dfa.DFAState, org.antlr.v4.runtime.atn.ATNConfigSet, org.antlr.v4.runtime.TokenStream, int, org.antlr.v4.runtime.ParserRuleContext):int");
    }

    protected ATNConfigSet computeReachSet(ATNConfigSet closure, int t, boolean fullCtx) {
        if (this.mergeCache == null) {
            this.mergeCache = new DoubleKeyMap();
        }
        ATNConfigSet intermediate = new ATNConfigSet(fullCtx);
        List<ATNConfig> skippedStopStates = null;
        Iterator i$ = closure.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (c.state instanceof RuleStopState) {
                if (!$assertionsDisabled && !c.context.isEmpty()) {
                    throw new AssertionError();
                }
                if (fullCtx || t == -1) {
                    if (skippedStopStates == null) {
                        skippedStopStates = new ArrayList<>();
                    }
                    skippedStopStates.add(c);
                }
            } else {
                int n = c.state.getNumberOfTransitions();
                for (int ti = 0; ti < n; ti++) {
                    Transition trans = c.state.transition(ti);
                    ATNState target = getReachableTarget(trans, t);
                    if (target != null) {
                        intermediate.add(new ATNConfig(c, target), this.mergeCache);
                    }
                }
            }
        }
        ATNConfigSet reach = null;
        if (skippedStopStates == null && t != -1) {
            if (intermediate.size() == 1) {
                reach = intermediate;
            } else if (getUniqueAlt(intermediate) != 0) {
                reach = intermediate;
            }
        }
        if (reach == null) {
            reach = new ATNConfigSet(fullCtx);
            HashSet hashSet = new HashSet();
            boolean treatEofAsEpsilon = t == -1;
            Iterator i$2 = intermediate.iterator();
            while (i$2.hasNext()) {
                ATNConfig c2 = (ATNConfig) i$2.next();
                closure(c2, reach, hashSet, false, fullCtx, treatEofAsEpsilon);
            }
        }
        if (t == -1) {
            reach = removeAllConfigsNotInRuleStopState(reach, reach == intermediate);
        }
        if (skippedStopStates != null && (!fullCtx || !PredictionMode.hasConfigInRuleStopState(reach))) {
            if (!$assertionsDisabled && skippedStopStates.isEmpty()) {
                throw new AssertionError();
            }
            for (ATNConfig c3 : skippedStopStates) {
                reach.add(c3, this.mergeCache);
            }
        }
        if (reach.isEmpty()) {
            return null;
        }
        return reach;
    }

    protected ATNConfigSet removeAllConfigsNotInRuleStopState(ATNConfigSet configs, boolean lookToEndOfRule) {
        if (!PredictionMode.allConfigsInRuleStopStates(configs)) {
            ATNConfigSet result = new ATNConfigSet(configs.fullCtx);
            Iterator i$ = configs.iterator();
            while (i$.hasNext()) {
                ATNConfig config = (ATNConfig) i$.next();
                if (config.state instanceof RuleStopState) {
                    result.add(config, this.mergeCache);
                } else if (lookToEndOfRule && config.state.onlyHasEpsilonTransitions()) {
                    IntervalSet nextTokens = this.atn.nextTokens(config.state);
                    if (nextTokens.contains(-2)) {
                        ATNState endOfRuleState = this.atn.ruleToStopState[config.state.ruleIndex];
                        result.add(new ATNConfig(config, endOfRuleState), this.mergeCache);
                    }
                }
            }
            return result;
        }
        return configs;
    }

    protected ATNConfigSet computeStartState(ATNState p, RuleContext ctx, boolean fullCtx) {
        PredictionContext initialContext = PredictionContext.fromRuleContext(this.atn, ctx);
        ATNConfigSet configs = new ATNConfigSet(fullCtx);
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            ATNState target = p.transition(i).target;
            ATNConfig c = new ATNConfig(target, i + 1, initialContext);
            closure(c, configs, new HashSet(), true, fullCtx, false);
        }
        return configs;
    }

    protected ATNConfigSet applyPrecedenceFilter(ATNConfigSet configs) {
        PredictionContext context;
        SemanticContext updatedContext;
        HashMap hashMap = new HashMap();
        ATNConfigSet configSet = new ATNConfigSet(configs.fullCtx);
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig config = (ATNConfig) i$.next();
            if (config.alt == 1 && (updatedContext = config.semanticContext.evalPrecedence(this.parser, this._outerContext)) != null) {
                hashMap.put(Integer.valueOf(config.state.stateNumber), config.context);
                if (updatedContext != config.semanticContext) {
                    configSet.add(new ATNConfig(config, updatedContext), this.mergeCache);
                } else {
                    configSet.add(config, this.mergeCache);
                }
            }
        }
        Iterator i$2 = configs.iterator();
        while (i$2.hasNext()) {
            ATNConfig config2 = (ATNConfig) i$2.next();
            if (config2.alt != 1 && (config2.isPrecedenceFilterSuppressed() || (context = (PredictionContext) hashMap.get(Integer.valueOf(config2.state.stateNumber))) == null || !context.equals(config2.context))) {
                configSet.add(config2, this.mergeCache);
            }
        }
        return configSet;
    }

    protected ATNState getReachableTarget(Transition trans, int ttype) {
        if (trans.matches(ttype, 0, this.atn.maxTokenType)) {
            return trans.target;
        }
        return null;
    }

    protected SemanticContext[] getPredsForAmbigAlts(BitSet ambigAlts, ATNConfigSet configs, int nalts) {
        SemanticContext[] altToPred = new SemanticContext[nalts + 1];
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (ambigAlts.get(c.alt)) {
                altToPred[c.alt] = SemanticContext.or(altToPred[c.alt], c.semanticContext);
            }
        }
        int nPredAlts = 0;
        for (int i = 1; i <= nalts; i++) {
            if (altToPred[i] == null) {
                altToPred[i] = SemanticContext.NONE;
            } else if (altToPred[i] != SemanticContext.NONE) {
                nPredAlts++;
            }
        }
        if (nPredAlts == 0) {
            return null;
        }
        return altToPred;
    }

    protected DFAState.PredPrediction[] getPredicatePredictions(BitSet ambigAlts, SemanticContext[] altToPred) {
        ArrayList arrayList = new ArrayList();
        boolean containsPredicate = false;
        for (int i = 1; i < altToPred.length; i++) {
            SemanticContext pred = altToPred[i];
            if (!$assertionsDisabled && pred == null) {
                throw new AssertionError();
            }
            if (ambigAlts != null && ambigAlts.get(i)) {
                arrayList.add(new DFAState.PredPrediction(pred, i));
            }
            if (pred != SemanticContext.NONE) {
                containsPredicate = true;
            }
        }
        if (containsPredicate) {
            return (DFAState.PredPrediction[]) arrayList.toArray(new DFAState.PredPrediction[arrayList.size()]);
        }
        return null;
    }

    protected int getSynValidOrSemInvalidAltThatFinishedDecisionEntryRule(ATNConfigSet configs, ParserRuleContext outerContext) {
        Pair<ATNConfigSet, ATNConfigSet> sets = splitAccordingToSemanticValidity(configs, outerContext);
        ATNConfigSet semValidConfigs = (ATNConfigSet) sets.a;
        ATNConfigSet semInvalidConfigs = (ATNConfigSet) sets.b;
        int alt = getAltThatFinishedDecisionEntryRule(semValidConfigs);
        if (alt != 0) {
            return alt;
        }
        if (semInvalidConfigs.size() <= 0 || (alt = getAltThatFinishedDecisionEntryRule(semInvalidConfigs)) == 0) {
            return 0;
        }
        return alt;
    }

    protected int getAltThatFinishedDecisionEntryRule(ATNConfigSet configs) {
        IntervalSet alts = new IntervalSet(new int[0]);
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (c.getOuterContextDepth() > 0 || ((c.state instanceof RuleStopState) && c.context.hasEmptyPath())) {
                alts.add(c.alt);
            }
        }
        if (alts.size() == 0) {
            return 0;
        }
        return alts.getMinElement();
    }

    protected Pair splitAccordingToSemanticValidity(ATNConfigSet configs, ParserRuleContext outerContext) {
        ATNConfigSet succeeded = new ATNConfigSet(configs.fullCtx);
        ATNConfigSet failed = new ATNConfigSet(configs.fullCtx);
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (c.semanticContext != SemanticContext.NONE) {
                boolean predicateEvaluationResult = evalSemanticContext(c.semanticContext, outerContext, c.alt, configs.fullCtx);
                if (predicateEvaluationResult) {
                    succeeded.add(c);
                } else {
                    failed.add(c);
                }
            } else {
                succeeded.add(c);
            }
        }
        return new Pair(succeeded, failed);
    }

    protected BitSet evalSemanticContext(DFAState.PredPrediction[] predPredictions, ParserRuleContext outerContext, boolean complete) {
        BitSet predictions = new BitSet();
        for (DFAState.PredPrediction pair : predPredictions) {
            if (pair.pred == SemanticContext.NONE) {
                predictions.set(pair.alt);
                if (!complete) {
                    break;
                }
            } else {
                boolean predicateEvaluationResult = evalSemanticContext(pair.pred, outerContext, pair.alt, false);
                if (predicateEvaluationResult) {
                    predictions.set(pair.alt);
                    if (!complete) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return predictions;
    }

    protected boolean evalSemanticContext(SemanticContext pred, ParserRuleContext parserCallStack, int alt, boolean fullCtx) {
        return pred.eval(this.parser, parserCallStack);
    }

    protected void closure(ATNConfig config, ATNConfigSet configs, Set set, boolean collectPredicates, boolean fullCtx, boolean treatEofAsEpsilon) {
        closureCheckingStopState(config, configs, set, collectPredicates, fullCtx, 0, treatEofAsEpsilon);
        if (!$assertionsDisabled && fullCtx && configs.dipsIntoOuterContext) {
            throw new AssertionError();
        }
    }

    protected void closureCheckingStopState(ATNConfig config, ATNConfigSet configs, Set set, boolean collectPredicates, boolean fullCtx, int depth, boolean treatEofAsEpsilon) {
        if (config.state instanceof RuleStopState) {
            if (!config.context.isEmpty()) {
                for (int i = 0; i < config.context.size(); i++) {
                    if (config.context.getReturnState(i) == Integer.MAX_VALUE) {
                        if (fullCtx) {
                            configs.add(new ATNConfig(config, config.state, PredictionContext.EMPTY), this.mergeCache);
                        } else {
                            closure_(config, configs, set, collectPredicates, fullCtx, depth, treatEofAsEpsilon);
                        }
                    } else {
                        ATNState returnState = (ATNState) this.atn.states.get(config.context.getReturnState(i));
                        PredictionContext newContext = config.context.getParent(i);
                        ATNConfig c = new ATNConfig(returnState, config.alt, newContext, config.semanticContext);
                        c.reachesIntoOuterContext = config.reachesIntoOuterContext;
                        if (!$assertionsDisabled && depth <= Integer.MIN_VALUE) {
                            throw new AssertionError();
                        }
                        closureCheckingStopState(c, configs, set, collectPredicates, fullCtx, depth - 1, treatEofAsEpsilon);
                    }
                }
                return;
            }
            if (fullCtx) {
                configs.add(config, this.mergeCache);
                return;
            }
        }
        closure_(config, configs, set, collectPredicates, fullCtx, depth, treatEofAsEpsilon);
    }

    protected void closure_(ATNConfig config, ATNConfigSet configs, Set set, boolean collectPredicates, boolean fullCtx, int depth, boolean treatEofAsEpsilon) {
        ATNState p = config.state;
        if (!p.onlyHasEpsilonTransitions()) {
            configs.add(config, this.mergeCache);
        }
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            if (i != 0 || !canDropLoopEntryEdgeInLeftRecursiveRule(config)) {
                Transition t = p.transition(i);
                boolean continueCollecting = !(t instanceof ActionTransition) && collectPredicates;
                ATNConfig c = getEpsilonTarget(config, t, continueCollecting, depth == 0, fullCtx, treatEofAsEpsilon);
                if (c != null) {
                    int newDepth = depth;
                    if (config.state instanceof RuleStopState) {
                        if (!$assertionsDisabled && fullCtx) {
                            throw new AssertionError();
                        }
                        if (this._dfa != null && this._dfa.isPrecedenceDfa()) {
                            int outermostPrecedenceReturn = ((EpsilonTransition) t).outermostPrecedenceReturn();
                            if (outermostPrecedenceReturn == this._dfa.atnStartState.ruleIndex) {
                                c.setPrecedenceFilterSuppressed(true);
                            }
                        }
                        c.reachesIntoOuterContext++;
                        if (set.add(c)) {
                            configs.dipsIntoOuterContext = true;
                            if (!$assertionsDisabled && newDepth <= Integer.MIN_VALUE) {
                                throw new AssertionError();
                            }
                            newDepth--;
                            closureCheckingStopState(c, configs, set, continueCollecting, fullCtx, newDepth, treatEofAsEpsilon);
                        } else {
                            continue;
                        }
                    } else if (t.isEpsilon() || set.add(c)) {
                        if ((t instanceof RuleTransition) && newDepth >= 0) {
                            newDepth++;
                        }
                        closureCheckingStopState(c, configs, set, continueCollecting, fullCtx, newDepth, treatEofAsEpsilon);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    protected boolean canDropLoopEntryEdgeInLeftRecursiveRule(ATNConfig config) {
        if (TURN_OFF_LR_LOOP_ENTRY_BRANCH_OPT) {
            return false;
        }
        ATNState p = config.state;
        if (p.getStateType() != 10 || !((StarLoopEntryState) p).isPrecedenceDecision || config.context.isEmpty() || config.context.hasEmptyPath()) {
            return false;
        }
        int numCtxs = config.context.size();
        for (int i = 0; i < numCtxs; i++) {
            if (((ATNState) this.atn.states.get(config.context.getReturnState(i))).ruleIndex != p.ruleIndex) {
                return false;
            }
        }
        BlockStartState decisionStartState = (BlockStartState) p.transition(0).target;
        int blockEndStateNum = decisionStartState.endState.stateNumber;
        BlockEndState blockEndState = (BlockEndState) this.atn.states.get(blockEndStateNum);
        for (int i2 = 0; i2 < numCtxs; i2++) {
            int returnStateNumber = config.context.getReturnState(i2);
            ATNState returnState = (ATNState) this.atn.states.get(returnStateNumber);
            if (returnState.getNumberOfTransitions() != 1 || !returnState.transition(0).isEpsilon()) {
                return false;
            }
            ATNState returnStateTarget = returnState.transition(0).target;
            if ((returnState.getStateType() != 8 || returnStateTarget != p) && returnState != blockEndState && returnStateTarget != blockEndState && (returnStateTarget.getStateType() != 8 || returnStateTarget.getNumberOfTransitions() != 1 || !returnStateTarget.transition(0).isEpsilon() || returnStateTarget.transition(0).target != p)) {
                return false;
            }
        }
        return true;
    }

    public String getRuleName(int index) {
        return (this.parser == null || index < 0) ? "<rule " + index + ">" : this.parser.getRuleNames()[index];
    }

    protected ATNConfig getEpsilonTarget(ATNConfig config, Transition t, boolean collectPredicates, boolean inContext, boolean fullCtx, boolean treatEofAsEpsilon) {
        switch (t.getSerializationType()) {
            case 1:
                return new ATNConfig(config, t.target);
            case 2:
            case 5:
            case 7:
                if (treatEofAsEpsilon && t.matches(-1, 0, 1)) {
                    return new ATNConfig(config, t.target);
                }
                return null;
            case 3:
                return ruleTransition(config, (RuleTransition) t);
            case 4:
                return predTransition(config, (PredicateTransition) t, collectPredicates, inContext, fullCtx);
            case 6:
                return actionTransition(config, (ActionTransition) t);
            case 8:
            case 9:
            default:
                return null;
            case 10:
                return precedenceTransition(config, (PrecedencePredicateTransition) t, collectPredicates, inContext, fullCtx);
        }
    }

    protected ATNConfig actionTransition(ATNConfig config, ActionTransition t) {
        return new ATNConfig(config, t.target);
    }

    public ATNConfig precedenceTransition(ATNConfig config, PrecedencePredicateTransition pt, boolean collectPredicates, boolean inContext, boolean fullCtx) {
        if (collectPredicates && inContext) {
            if (fullCtx) {
                int currentPosition = this._input.index();
                this._input.seek(this._startIndex);
                boolean predSucceeds = evalSemanticContext(pt.getPredicate(), this._outerContext, config.alt, fullCtx);
                this._input.seek(currentPosition);
                if (!predSucceeds) {
                    return null;
                }
                ATNConfig c = new ATNConfig(config, pt.target);
                return c;
            }
            SemanticContext newSemCtx = SemanticContext.and(config.semanticContext, pt.getPredicate());
            ATNConfig c2 = new ATNConfig(config, pt.target, newSemCtx);
            return c2;
        }
        ATNConfig c3 = new ATNConfig(config, pt.target);
        return c3;
    }

    protected ATNConfig predTransition(ATNConfig config, PredicateTransition pt, boolean collectPredicates, boolean inContext, boolean fullCtx) {
        if (collectPredicates && (!pt.isCtxDependent || (pt.isCtxDependent && inContext))) {
            if (fullCtx) {
                int currentPosition = this._input.index();
                this._input.seek(this._startIndex);
                boolean predSucceeds = evalSemanticContext(pt.getPredicate(), this._outerContext, config.alt, fullCtx);
                this._input.seek(currentPosition);
                if (!predSucceeds) {
                    return null;
                }
                ATNConfig c = new ATNConfig(config, pt.target);
                return c;
            }
            SemanticContext newSemCtx = SemanticContext.and(config.semanticContext, pt.getPredicate());
            ATNConfig c2 = new ATNConfig(config, pt.target, newSemCtx);
            return c2;
        }
        ATNConfig c3 = new ATNConfig(config, pt.target);
        return c3;
    }

    protected ATNConfig ruleTransition(ATNConfig config, RuleTransition t) {
        ATNState returnState = t.followState;
        PredictionContext newContext = SingletonPredictionContext.create(config.context, returnState.stateNumber);
        return new ATNConfig(config, t.target, newContext);
    }

    protected BitSet getConflictingAlts(ATNConfigSet configs) {
        Collection<BitSet> altsets = PredictionMode.getConflictingAltSubsets(configs);
        return PredictionMode.getAlts(altsets);
    }

    protected BitSet getConflictingAltsOrUniqueAlt(ATNConfigSet configs) {
        if (configs.uniqueAlt != 0) {
            BitSet conflictingAlts = new BitSet();
            conflictingAlts.set(configs.uniqueAlt);
            return conflictingAlts;
        }
        return configs.conflictingAlts;
    }

    public String getTokenName(int t) {
        if (t == -1) {
            return "EOF";
        }
        Vocabulary vocabulary = this.parser != null ? this.parser.getVocabulary() : VocabularyImpl.EMPTY_VOCABULARY;
        String displayName = vocabulary.getDisplayName(t);
        return !displayName.equals(Integer.toString(t)) ? displayName + "<" + t + ">" : displayName;
    }

    public String getLookaheadName(TokenStream input) {
        return getTokenName(input.LA(1));
    }

    public void dumpDeadEndConfigs(NoViableAltException nvae) {
        System.err.println("dead end configs: ");
        Iterator i$ = nvae.getDeadEndConfigs().iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            String trans = "no edges";
            if (c.state.getNumberOfTransitions() > 0) {
                Transition t = c.state.transition(0);
                if (t instanceof AtomTransition) {
                    AtomTransition at = (AtomTransition) t;
                    trans = "Atom " + getTokenName(at.label);
                } else if (t instanceof SetTransition) {
                    SetTransition st = (SetTransition) t;
                    boolean not = st instanceof NotSetTransition;
                    trans = (not ? "~" : "") + "Set " + st.set.toString();
                }
            }
            System.err.println(c.toString(this.parser, true) + ":" + trans);
        }
    }

    protected NoViableAltException noViableAlt(TokenStream input, ParserRuleContext outerContext, ATNConfigSet configs, int startIndex) {
        return new NoViableAltException(this.parser, input, input.get(startIndex), input.LT(1), configs, outerContext);
    }

    protected static int getUniqueAlt(ATNConfigSet configs) {
        int alt = 0;
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (alt == 0) {
                alt = c.alt;
            } else if (c.alt != alt) {
                return 0;
            }
        }
        return alt;
    }

    protected DFAState addDFAEdge(DFA dfa, DFAState from, int t, DFAState to) {
        if (to == null) {
            return null;
        }
        DFAState to2 = addDFAState(dfa, to);
        if (from == null || t < -1 || t > this.atn.maxTokenType) {
            return to2;
        }
        synchronized (from) {
            if (from.edges == null) {
                from.edges = new DFAState[this.atn.maxTokenType + 1 + 1];
            }
            from.edges[t + 1] = to2;
        }
        return to2;
    }

    protected DFAState addDFAState(DFA dfa, DFAState D) {
        if (D != ERROR) {
            synchronized (dfa.states) {
                DFAState existing = (DFAState) dfa.states.get(D);
                if (existing != null) {
                    return existing;
                }
                D.stateNumber = dfa.states.size();
                if (!D.configs.isReadonly()) {
                    D.configs.optimizeConfigs(this);
                    D.configs.setReadonly(true);
                }
                dfa.states.put(D, D);
                return D;
            }
        }
        return D;
    }

    protected void reportAttemptingFullContext(DFA dfa, BitSet conflictingAlts, ATNConfigSet configs, int startIndex, int stopIndex) {
        if (this.parser != null) {
            this.parser.getErrorListenerDispatch().reportAttemptingFullContext(this.parser, dfa, startIndex, stopIndex, conflictingAlts, configs);
        }
    }

    protected void reportContextSensitivity(DFA dfa, int prediction, ATNConfigSet configs, int startIndex, int stopIndex) {
        if (this.parser != null) {
            this.parser.getErrorListenerDispatch().reportContextSensitivity(this.parser, dfa, startIndex, stopIndex, prediction, configs);
        }
    }

    protected void reportAmbiguity(DFA dfa, DFAState D, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        if (this.parser != null) {
            this.parser.getErrorListenerDispatch().reportAmbiguity(this.parser, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
        }
    }

    public final void setPredictionMode(PredictionMode mode) {
        this.mode = mode;
    }

    public final PredictionMode getPredictionMode() {
        return this.mode;
    }

    public Parser getParser() {
        return this.parser;
    }

    public static String getSafeEnv(String envName) {
        try {
            return System.getenv(envName);
        } catch (SecurityException e) {
            return null;
        }
    }
}
