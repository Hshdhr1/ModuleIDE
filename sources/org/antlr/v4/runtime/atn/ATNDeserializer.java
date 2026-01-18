package org.antlr.v4.runtime.atn;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ATNDeserializer {
    public static final UUID SERIALIZED_UUID;
    private final ATNDeserializationOptions deserializationOptions;
    public static final int SERIALIZED_VERSION = 3;
    private static final UUID BASE_SERIALIZED_UUID = UUID.fromString("33761B2D-78BB-4A43-8B0B-4F5BEE8AACF3");
    private static final UUID ADDED_PRECEDENCE_TRANSITIONS = UUID.fromString("1DA0C57D-6C06-438A-9B27-10BCB3CE0F61");
    private static final UUID ADDED_LEXER_ACTIONS = UUID.fromString("AADB8D7E-AEEF-4415-AD2B-8204D6CF042E");
    private static final UUID ADDED_UNICODE_SMP = UUID.fromString("59627784-3BE5-417A-B9EB-8131A7286089");
    private static final List SUPPORTED_UUIDS = new ArrayList();

    interface UnicodeDeserializer {
        int readUnicode(char[] cArr, int i);

        int size();
    }

    enum UnicodeDeserializingMode {
        UNICODE_BMP,
        UNICODE_SMP
    }

    static {
        SUPPORTED_UUIDS.add(BASE_SERIALIZED_UUID);
        SUPPORTED_UUIDS.add(ADDED_PRECEDENCE_TRANSITIONS);
        SUPPORTED_UUIDS.add(ADDED_LEXER_ACTIONS);
        SUPPORTED_UUIDS.add(ADDED_UNICODE_SMP);
        SERIALIZED_UUID = ADDED_UNICODE_SMP;
    }

    static class 1 implements UnicodeDeserializer {
        1() {
        }

        public int readUnicode(char[] data, int p) {
            return ATNDeserializer.toInt(data[p]);
        }

        public int size() {
            return 1;
        }
    }

    static UnicodeDeserializer getUnicodeDeserializer(UnicodeDeserializingMode mode) {
        return mode == UnicodeDeserializingMode.UNICODE_BMP ? new 1() : new 2();
    }

    static class 2 implements UnicodeDeserializer {
        2() {
        }

        public int readUnicode(char[] data, int p) {
            return ATNDeserializer.toInt32(data, p);
        }

        public int size() {
            return 2;
        }
    }

    public ATNDeserializer() {
        this(ATNDeserializationOptions.getDefaultOptions());
    }

    public ATNDeserializer(ATNDeserializationOptions deserializationOptions) {
        this.deserializationOptions = deserializationOptions == null ? ATNDeserializationOptions.getDefaultOptions() : deserializationOptions;
    }

    protected static boolean isFeatureSupported(UUID feature, UUID actualUuid) {
        int featureIndex = SUPPORTED_UUIDS.indexOf(feature);
        return featureIndex >= 0 && SUPPORTED_UUIDS.indexOf(actualUuid) >= featureIndex;
    }

    public ATN deserialize(char[] data) {
        int p;
        int p2;
        int p3;
        ATNState endState;
        char[] data2 = (char[]) data.clone();
        for (int i = 1; i < data2.length; i++) {
            data2[i] = (char) (data2[i] - 2);
        }
        int p4 = 0 + 1;
        int version = toInt(data2[0]);
        if (version != SERIALIZED_VERSION) {
            String reason = String.format(Locale.getDefault(), "Could not deserialize ATN with version %d (expected %d).", new Object[]{Integer.valueOf(version), Integer.valueOf(SERIALIZED_VERSION)});
            throw new UnsupportedOperationException(new InvalidClassException(ATN.class.getName(), reason));
        }
        UUID uuid = toUUID(data2, p4);
        int p5 = p4 + 8;
        if (!SUPPORTED_UUIDS.contains(uuid)) {
            String reason2 = String.format(Locale.getDefault(), "Could not deserialize ATN with UUID %s (expected %s or a legacy UUID).", new Object[]{uuid, SERIALIZED_UUID});
            throw new UnsupportedOperationException(new InvalidClassException(ATN.class.getName(), reason2));
        }
        boolean supportsPrecedencePredicates = isFeatureSupported(ADDED_PRECEDENCE_TRANSITIONS, uuid);
        boolean supportsLexerActions = isFeatureSupported(ADDED_LEXER_ACTIONS, uuid);
        int p6 = p5 + 1;
        ATNType grammarType = ATNType.values()[toInt(data2[p5])];
        int p7 = p6 + 1;
        int maxTokenType = toInt(data2[p6]);
        ATN atn = new ATN(grammarType, maxTokenType);
        ArrayList<Pair<LoopEndState, Integer>> arrayList = new ArrayList();
        ArrayList<Pair<BlockStartState, Integer>> arrayList2 = new ArrayList();
        int p8 = p7 + 1;
        int nstates = toInt(data2[p7]);
        int i2 = 0;
        while (i2 < nstates) {
            int p9 = p8 + 1;
            int stype = toInt(data2[p8]);
            if (stype == 0) {
                atn.addState(null);
            } else {
                int p10 = p9 + 1;
                int ruleIndex = toInt(data2[p9]);
                if (ruleIndex == 65535) {
                    ruleIndex = -1;
                }
                ATNState s = stateFactory(stype, ruleIndex);
                if (stype == 12) {
                    p9 = p10 + 1;
                    int loopBackStateNumber = toInt(data2[p10]);
                    arrayList.add(new Pair((LoopEndState) s, Integer.valueOf(loopBackStateNumber)));
                } else if (s instanceof BlockStartState) {
                    p9 = p10 + 1;
                    int endStateNumber = toInt(data2[p10]);
                    arrayList2.add(new Pair((BlockStartState) s, Integer.valueOf(endStateNumber)));
                } else {
                    p9 = p10;
                }
                atn.addState(s);
            }
            i2++;
            p8 = p9;
        }
        for (Pair<LoopEndState, Integer> pair : arrayList) {
            ((LoopEndState) pair.a).loopBackState = (ATNState) atn.states.get(((Integer) pair.b).intValue());
        }
        for (Pair<BlockStartState, Integer> pair2 : arrayList2) {
            ((BlockStartState) pair2.a).endState = (BlockEndState) atn.states.get(((Integer) pair2.b).intValue());
        }
        int p11 = p8 + 1;
        int numNonGreedyStates = toInt(data2[p8]);
        int i3 = 0;
        while (true) {
            p = p11;
            if (i3 >= numNonGreedyStates) {
                break;
            }
            p11 = p + 1;
            int stateNumber = toInt(data2[p]);
            ((DecisionState) atn.states.get(stateNumber)).nonGreedy = true;
            i3++;
        }
        if (supportsPrecedencePredicates) {
            int p12 = p + 1;
            int numPrecedenceStates = toInt(data2[p]);
            int i4 = 0;
            while (true) {
                p = p12;
                if (i4 >= numPrecedenceStates) {
                    break;
                }
                p12 = p + 1;
                int stateNumber2 = toInt(data2[p]);
                ((RuleStartState) atn.states.get(stateNumber2)).isLeftRecursiveRule = true;
                i4++;
            }
        }
        int p13 = p;
        int p14 = p13 + 1;
        int nrules = toInt(data2[p13]);
        if (atn.grammarType == ATNType.LEXER) {
            atn.ruleToTokenType = new int[nrules];
        }
        atn.ruleToStartState = new RuleStartState[nrules];
        int i5 = 0;
        while (i5 < nrules) {
            int p15 = p14 + 1;
            int s2 = toInt(data2[p14]);
            RuleStartState startState = (RuleStartState) atn.states.get(s2);
            atn.ruleToStartState[i5] = startState;
            if (atn.grammarType == ATNType.LEXER) {
                int p16 = p15 + 1;
                int tokenType = toInt(data2[p15]);
                if (tokenType == 65535) {
                    tokenType = -1;
                }
                atn.ruleToTokenType[i5] = tokenType;
                if (isFeatureSupported(ADDED_LEXER_ACTIONS, uuid)) {
                    p15 = p16;
                } else {
                    p15 = p16 + 1;
                    toInt(data2[p16]);
                }
            }
            i5++;
            p14 = p15;
        }
        atn.ruleToStopState = new RuleStopState[nrules];
        for (ATNState state : atn.states) {
            if (state instanceof RuleStopState) {
                RuleStopState stopState = (RuleStopState) state;
                atn.ruleToStopState[state.ruleIndex] = stopState;
                atn.ruleToStartState[state.ruleIndex].stopState = stopState;
            }
        }
        int p17 = p14 + 1;
        int nmodes = toInt(data2[p14]);
        int i6 = 0;
        while (true) {
            p2 = p17;
            if (i6 >= nmodes) {
                break;
            }
            p17 = p2 + 1;
            int s3 = toInt(data2[p2]);
            atn.modeToStartState.add((TokensStartState) atn.states.get(s3));
            i6++;
        }
        List arrayList3 = new ArrayList();
        int p18 = deserializeSets(data2, p2, arrayList3, getUnicodeDeserializer(UnicodeDeserializingMode.UNICODE_BMP));
        if (isFeatureSupported(ADDED_UNICODE_SMP, uuid)) {
            p18 = deserializeSets(data2, p18, arrayList3, getUnicodeDeserializer(UnicodeDeserializingMode.UNICODE_SMP));
        }
        int p19 = p18 + 1;
        int nedges = toInt(data2[p18]);
        int i7 = 0;
        while (i7 < nedges) {
            int src = toInt(data2[p19]);
            int trg = toInt(data2[p19 + 1]);
            int ttype = toInt(data2[p19 + 2]);
            int arg1 = toInt(data2[p19 + 3]);
            int arg2 = toInt(data2[p19 + 4]);
            int arg3 = toInt(data2[p19 + 5]);
            Transition trans = edgeFactory(atn, ttype, src, trg, arg1, arg2, arg3, arrayList3);
            ATNState srcState = (ATNState) atn.states.get(src);
            srcState.addTransition(trans);
            i7++;
            p19 += 6;
        }
        for (ATNState state2 : atn.states) {
            for (int i8 = 0; i8 < state2.getNumberOfTransitions(); i8++) {
                Transition t = state2.transition(i8);
                if (t instanceof RuleTransition) {
                    RuleTransition ruleTransition = (RuleTransition) t;
                    int outermostPrecedenceReturn = -1;
                    if (atn.ruleToStartState[ruleTransition.target.ruleIndex].isLeftRecursiveRule && ruleTransition.precedence == 0) {
                        outermostPrecedenceReturn = ruleTransition.target.ruleIndex;
                    }
                    EpsilonTransition returnTransition = new EpsilonTransition(ruleTransition.followState, outermostPrecedenceReturn);
                    atn.ruleToStopState[ruleTransition.target.ruleIndex].addTransition(returnTransition);
                }
            }
        }
        for (ATNState state3 : atn.states) {
            if (state3 instanceof BlockStartState) {
                if (((BlockStartState) state3).endState == null) {
                    throw new IllegalStateException();
                }
                if (((BlockStartState) state3).endState.startState != null) {
                    throw new IllegalStateException();
                }
                ((BlockStartState) state3).endState.startState = (BlockStartState) state3;
            }
            if (state3 instanceof PlusLoopbackState) {
                PlusLoopbackState loopbackState = (PlusLoopbackState) state3;
                for (int i9 = 0; i9 < loopbackState.getNumberOfTransitions(); i9++) {
                    ATNState target = loopbackState.transition(i9).target;
                    if (target instanceof PlusBlockStartState) {
                        ((PlusBlockStartState) target).loopBackState = loopbackState;
                    }
                }
            } else if (state3 instanceof StarLoopbackState) {
                StarLoopbackState loopbackState2 = (StarLoopbackState) state3;
                for (int i10 = 0; i10 < loopbackState2.getNumberOfTransitions(); i10++) {
                    ATNState target2 = loopbackState2.transition(i10).target;
                    if (target2 instanceof StarLoopEntryState) {
                        ((StarLoopEntryState) target2).loopBackState = loopbackState2;
                    }
                }
            }
        }
        int p20 = p19 + 1;
        int ndecisions = toInt(data2[p19]);
        int i11 = 1;
        while (true) {
            p3 = p20;
            if (i11 > ndecisions) {
                break;
            }
            p20 = p3 + 1;
            int s4 = toInt(data2[p3]);
            DecisionState decState = (DecisionState) atn.states.get(s4);
            atn.decisionToState.add(decState);
            decState.decision = i11 - 1;
            i11++;
        }
        if (atn.grammarType == ATNType.LEXER) {
            if (supportsLexerActions) {
                int p21 = p3 + 1;
                atn.lexerActions = new LexerAction[toInt(data2[p3])];
                int i12 = 0;
                while (i12 < atn.lexerActions.length) {
                    int p22 = p21 + 1;
                    LexerActionType actionType = LexerActionType.values()[toInt(data2[p21])];
                    int p23 = p22 + 1;
                    int data1 = toInt(data2[p22]);
                    if (data1 == 65535) {
                        data1 = -1;
                    }
                    int p24 = p23 + 1;
                    int data22 = toInt(data2[p23]);
                    if (data22 == 65535) {
                        data22 = -1;
                    }
                    LexerAction lexerAction = lexerActionFactory(actionType, data1, data22);
                    atn.lexerActions[i12] = lexerAction;
                    i12++;
                    p21 = p24;
                }
            } else {
                ArrayList arrayList4 = new ArrayList();
                for (ATNState state4 : atn.states) {
                    for (int i13 = 0; i13 < state4.getNumberOfTransitions(); i13++) {
                        Transition transition = state4.transition(i13);
                        if (transition instanceof ActionTransition) {
                            int ruleIndex2 = ((ActionTransition) transition).ruleIndex;
                            int actionIndex = ((ActionTransition) transition).actionIndex;
                            LexerCustomAction lexerAction2 = new LexerCustomAction(ruleIndex2, actionIndex);
                            state4.setTransition(i13, new ActionTransition(transition.target, ruleIndex2, arrayList4.size(), false));
                            arrayList4.add(lexerAction2);
                        }
                    }
                }
                atn.lexerActions = (LexerAction[]) arrayList4.toArray(new LexerAction[arrayList4.size()]);
            }
        }
        markPrecedenceDecisions(atn);
        if (this.deserializationOptions.isVerifyATN()) {
            verifyATN(atn);
        }
        if (this.deserializationOptions.isGenerateRuleBypassTransitions() && atn.grammarType == ATNType.PARSER) {
            atn.ruleToTokenType = new int[atn.ruleToStartState.length];
            for (int i14 = 0; i14 < atn.ruleToStartState.length; i14++) {
                atn.ruleToTokenType[i14] = atn.maxTokenType + i14 + 1;
            }
            for (int i15 = 0; i15 < atn.ruleToStartState.length; i15++) {
                BasicBlockStartState bypassStart = new BasicBlockStartState();
                bypassStart.ruleIndex = i15;
                atn.addState(bypassStart);
                BlockEndState bypassStop = new BlockEndState();
                bypassStop.ruleIndex = i15;
                atn.addState(bypassStop);
                bypassStart.endState = bypassStop;
                atn.defineDecisionState(bypassStart);
                bypassStop.startState = bypassStart;
                Transition excludeTransition = null;
                if (atn.ruleToStartState[i15].isLeftRecursiveRule) {
                    endState = null;
                    Iterator i$ = atn.states.iterator();
                    while (true) {
                        if (!i$.hasNext()) {
                            break;
                        }
                        ATNState state5 = (ATNState) i$.next();
                        if (state5.ruleIndex == i15 && (state5 instanceof StarLoopEntryState)) {
                            ATNState maybeLoopEndState = state5.transition(state5.getNumberOfTransitions() - 1).target;
                            if ((maybeLoopEndState instanceof LoopEndState) && maybeLoopEndState.epsilonOnlyTransitions && (maybeLoopEndState.transition(0).target instanceof RuleStopState)) {
                                endState = state5;
                                break;
                            }
                        }
                    }
                    if (endState == null) {
                        throw new UnsupportedOperationException("Couldn't identify final state of the precedence rule prefix section.");
                    }
                    excludeTransition = ((StarLoopEntryState) endState).loopBackState.transition(0);
                } else {
                    endState = atn.ruleToStopState[i15];
                }
                Iterator it = atn.states.iterator();
                while (it.hasNext()) {
                    for (Transition transition2 : ((ATNState) it.next()).transitions) {
                        if (transition2 != excludeTransition && transition2.target == endState) {
                            transition2.target = bypassStop;
                        }
                    }
                }
                while (atn.ruleToStartState[i15].getNumberOfTransitions() > 0) {
                    bypassStart.addTransition(atn.ruleToStartState[i15].removeTransition(atn.ruleToStartState[i15].getNumberOfTransitions() - 1));
                }
                atn.ruleToStartState[i15].addTransition(new EpsilonTransition(bypassStart));
                bypassStop.addTransition(new EpsilonTransition(endState));
                ATNState matchState = new BasicState();
                atn.addState(matchState);
                matchState.addTransition(new AtomTransition(bypassStop, atn.ruleToTokenType[i15]));
                bypassStart.addTransition(new EpsilonTransition(matchState));
            }
            if (this.deserializationOptions.isVerifyATN()) {
                verifyATN(atn);
            }
        }
        return atn;
    }

    private int deserializeSets(char[] data, int p, List list, UnicodeDeserializer unicodeDeserializer) {
        int nsets = toInt(data[p]);
        int p2 = p + 1;
        for (int i = 0; i < nsets; i++) {
            int nintervals = toInt(data[p2]);
            int p3 = p2 + 1;
            IntervalSet set = new IntervalSet(new int[0]);
            list.add(set);
            int p4 = p3 + 1;
            boolean containsEof = toInt(data[p3]) != 0;
            if (containsEof) {
                set.add(-1);
            }
            p2 = p4;
            for (int j = 0; j < nintervals; j++) {
                int a = unicodeDeserializer.readUnicode(data, p2);
                int p5 = p2 + unicodeDeserializer.size();
                int b = unicodeDeserializer.readUnicode(data, p5);
                p2 = p5 + unicodeDeserializer.size();
                set.add(a, b);
            }
        }
        return p2;
    }

    protected void markPrecedenceDecisions(ATN atn) {
        for (ATNState state : atn.states) {
            if ((state instanceof StarLoopEntryState) && atn.ruleToStartState[state.ruleIndex].isLeftRecursiveRule) {
                ATNState maybeLoopEndState = state.transition(state.getNumberOfTransitions() - 1).target;
                if ((maybeLoopEndState instanceof LoopEndState) && maybeLoopEndState.epsilonOnlyTransitions && (maybeLoopEndState.transition(0).target instanceof RuleStopState)) {
                    ((StarLoopEntryState) state).isPrecedenceDecision = true;
                }
            }
        }
    }

    protected void verifyATN(ATN atn) {
        for (ATNState state : atn.states) {
            if (state != null) {
                checkCondition(state.onlyHasEpsilonTransitions() || state.getNumberOfTransitions() <= 1);
                if (state instanceof PlusBlockStartState) {
                    checkCondition(((PlusBlockStartState) state).loopBackState != null);
                }
                if (state instanceof StarLoopEntryState) {
                    StarLoopEntryState starLoopEntryState = (StarLoopEntryState) state;
                    checkCondition(starLoopEntryState.loopBackState != null);
                    checkCondition(starLoopEntryState.getNumberOfTransitions() == 2);
                    if (starLoopEntryState.transition(0).target instanceof StarBlockStartState) {
                        checkCondition(starLoopEntryState.transition(1).target instanceof LoopEndState);
                        checkCondition(!starLoopEntryState.nonGreedy);
                    } else if (starLoopEntryState.transition(0).target instanceof LoopEndState) {
                        checkCondition(starLoopEntryState.transition(1).target instanceof StarBlockStartState);
                        checkCondition(starLoopEntryState.nonGreedy);
                    } else {
                        throw new IllegalStateException();
                    }
                }
                if (state instanceof StarLoopbackState) {
                    checkCondition(state.getNumberOfTransitions() == 1);
                    checkCondition(state.transition(0).target instanceof StarLoopEntryState);
                }
                if (state instanceof LoopEndState) {
                    checkCondition(((LoopEndState) state).loopBackState != null);
                }
                if (state instanceof RuleStartState) {
                    checkCondition(((RuleStartState) state).stopState != null);
                }
                if (state instanceof BlockStartState) {
                    checkCondition(((BlockStartState) state).endState != null);
                }
                if (state instanceof BlockEndState) {
                    checkCondition(((BlockEndState) state).startState != null);
                }
                if (state instanceof DecisionState) {
                    DecisionState decisionState = (DecisionState) state;
                    checkCondition(decisionState.getNumberOfTransitions() <= 1 || decisionState.decision >= 0);
                } else {
                    checkCondition(state.getNumberOfTransitions() <= 1 || (state instanceof RuleStopState));
                }
            }
        }
    }

    protected void checkCondition(boolean condition) {
        checkCondition(condition, null);
    }

    protected void checkCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    protected static int toInt(char c) {
        return c;
    }

    protected static int toInt32(char[] data, int offset) {
        return data[offset] | (data[offset + 1] << 16);
    }

    protected static long toLong(char[] data, int offset) {
        long lowOrder = toInt32(data, offset) & 4294967295L;
        return (toInt32(data, offset + 2) << 32) | lowOrder;
    }

    protected static UUID toUUID(char[] data, int offset) {
        long leastSigBits = toLong(data, offset);
        long mostSigBits = toLong(data, offset + 4);
        return new UUID(mostSigBits, leastSigBits);
    }

    protected Transition edgeFactory(ATN atn, int type, int src, int trg, int arg1, int arg2, int arg3, List list) {
        ATNState target = (ATNState) atn.states.get(trg);
        switch (type) {
            case 1:
                return new EpsilonTransition(target);
            case 2:
                if (arg3 != 0) {
                    return new RangeTransition(target, -1, arg2);
                }
                return new RangeTransition(target, arg1, arg2);
            case 3:
                return new RuleTransition((RuleStartState) atn.states.get(arg1), arg2, arg3, target);
            case 4:
                PredicateTransition pt = new PredicateTransition(target, arg1, arg2, arg3 != 0);
                return pt;
            case 5:
                if (arg3 != 0) {
                    return new AtomTransition(target, -1);
                }
                return new AtomTransition(target, arg1);
            case 6:
                ActionTransition a = new ActionTransition(target, arg1, arg2, arg3 != 0);
                return a;
            case 7:
                return new SetTransition(target, (IntervalSet) list.get(arg1));
            case 8:
                return new NotSetTransition(target, (IntervalSet) list.get(arg1));
            case 9:
                return new WildcardTransition(target);
            case 10:
                return new PrecedencePredicateTransition(target, arg1);
            default:
                throw new IllegalArgumentException("The specified transition type is not valid.");
        }
    }

    protected ATNState stateFactory(int type, int ruleIndex) {
        ATNState s;
        switch (type) {
            case 0:
                return null;
            case 1:
                s = new BasicState();
                break;
            case 2:
                s = new RuleStartState();
                break;
            case 3:
                s = new BasicBlockStartState();
                break;
            case 4:
                s = new PlusBlockStartState();
                break;
            case 5:
                s = new StarBlockStartState();
                break;
            case 6:
                s = new TokensStartState();
                break;
            case 7:
                s = new RuleStopState();
                break;
            case 8:
                s = new BlockEndState();
                break;
            case 9:
                s = new StarLoopbackState();
                break;
            case 10:
                s = new StarLoopEntryState();
                break;
            case 11:
                s = new PlusLoopbackState();
                break;
            case 12:
                s = new LoopEndState();
                break;
            default:
                String message = String.format(Locale.getDefault(), "The specified state type %d is not valid.", new Object[]{Integer.valueOf(type)});
                throw new IllegalArgumentException(message);
        }
        s.ruleIndex = ruleIndex;
        return s;
    }

    protected LexerAction lexerActionFactory(LexerActionType type, int data1, int data2) {
        switch (type) {
            case CHANNEL:
                return new LexerChannelAction(data1);
            case CUSTOM:
                return new LexerCustomAction(data1, data2);
            case MODE:
                return new LexerModeAction(data1);
            case MORE:
                return LexerMoreAction.INSTANCE;
            case POP_MODE:
                return LexerPopModeAction.INSTANCE;
            case PUSH_MODE:
                return new LexerPushModeAction(data1);
            case SKIP:
                return LexerSkipAction.INSTANCE;
            case TYPE:
                return new LexerTypeAction(data1);
            default:
                String message = String.format(Locale.getDefault(), "The specified lexer action type %d is not valid.", new Object[]{type});
                throw new IllegalArgumentException(message);
        }
    }
}
