package org.antlr.v4.runtime.atn;

import java.io.InvalidClassException;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.misc.IntegerList;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.Utils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ATNSerializer {
    static final /* synthetic */ boolean $assertionsDisabled;
    public ATN atn;
    private List tokenNames;

    private interface CodePointSerializer {
        void serializeCodePoint(IntegerList integerList, int i);
    }

    static {
        $assertionsDisabled = !ATNSerializer.class.desiredAssertionStatus();
    }

    static /* synthetic */ void access$000(ATNSerializer x0, IntegerList x1, int x2) {
        x0.serializeInt(x1, x2);
    }

    public ATNSerializer(ATN atn) {
        if (!$assertionsDisabled && atn.grammarType == null) {
            throw new AssertionError();
        }
        this.atn = atn;
    }

    public ATNSerializer(ATN atn, List list) {
        if (!$assertionsDisabled && atn.grammarType == null) {
            throw new AssertionError();
        }
        this.atn = atn;
        this.tokenNames = list;
    }

    public IntegerList serialize() {
        IntegerList data = new IntegerList();
        data.add(ATNDeserializer.SERIALIZED_VERSION);
        serializeUUID(data, ATNDeserializer.SERIALIZED_UUID);
        data.add(this.atn.grammarType.ordinal());
        data.add(this.atn.maxTokenType);
        int nedges = 0;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        IntegerList nonGreedyStates = new IntegerList();
        IntegerList precedenceStates = new IntegerList();
        data.add(this.atn.states.size());
        for (ATNState s : this.atn.states) {
            if (s == null) {
                data.add(0);
            } else {
                int stateType = s.getStateType();
                if ((s instanceof DecisionState) && ((DecisionState) s).nonGreedy) {
                    nonGreedyStates.add(s.stateNumber);
                }
                if ((s instanceof RuleStartState) && ((RuleStartState) s).isLeftRecursiveRule) {
                    precedenceStates.add(s.stateNumber);
                }
                data.add(stateType);
                if (s.ruleIndex == -1) {
                    data.add(65535);
                } else {
                    data.add(s.ruleIndex);
                }
                if (s.getStateType() == 12) {
                    data.add(((LoopEndState) s).loopBackState.stateNumber);
                } else if (s instanceof BlockStartState) {
                    data.add(((BlockStartState) s).endState.stateNumber);
                }
                if (s.getStateType() != 7) {
                    nedges += s.getNumberOfTransitions();
                }
                for (int i = 0; i < s.getNumberOfTransitions(); i++) {
                    Transition t = s.transition(i);
                    int edgeType = ((Integer) Transition.serializationTypes.get(t.getClass())).intValue();
                    if (edgeType == 7 || edgeType == 8) {
                        SetTransition st = (SetTransition) t;
                        linkedHashMap.put(st.set, true);
                    }
                }
            }
        }
        data.add(nonGreedyStates.size());
        for (int i2 = 0; i2 < nonGreedyStates.size(); i2++) {
            data.add(nonGreedyStates.get(i2));
        }
        data.add(precedenceStates.size());
        for (int i3 = 0; i3 < precedenceStates.size(); i3++) {
            data.add(precedenceStates.get(i3));
        }
        int nrules = this.atn.ruleToStartState.length;
        data.add(nrules);
        for (int r = 0; r < nrules; r++) {
            ATNState ruleStartState = this.atn.ruleToStartState[r];
            data.add(ruleStartState.stateNumber);
            if (this.atn.grammarType == ATNType.LEXER) {
                if (this.atn.ruleToTokenType[r] == -1) {
                    data.add(65535);
                } else {
                    data.add(this.atn.ruleToTokenType[r]);
                }
            }
        }
        int nmodes = this.atn.modeToStartState.size();
        data.add(nmodes);
        if (nmodes > 0) {
            for (ATNState modeStartState : this.atn.modeToStartState) {
                data.add(modeStartState.stateNumber);
            }
        }
        ArrayList<IntervalSet> arrayList = new ArrayList();
        ArrayList<IntervalSet> arrayList2 = new ArrayList();
        for (IntervalSet set : linkedHashMap.keySet()) {
            if (set.getMaxElement() <= 65535) {
                arrayList.add(set);
            } else {
                arrayList2.add(set);
            }
        }
        serializeSets(data, arrayList, new 1());
        serializeSets(data, arrayList2, new 2());
        HashMap hashMap = new HashMap();
        int setIndex = 0;
        for (IntervalSet bmpSet : arrayList) {
            hashMap.put(bmpSet, Integer.valueOf(setIndex));
            setIndex++;
        }
        for (IntervalSet smpSet : arrayList2) {
            hashMap.put(smpSet, Integer.valueOf(setIndex));
            setIndex++;
        }
        data.add(nedges);
        for (ATNState s2 : this.atn.states) {
            if (s2 != null && s2.getStateType() != 7) {
                for (int i4 = 0; i4 < s2.getNumberOfTransitions(); i4++) {
                    Transition t2 = s2.transition(i4);
                    if (this.atn.states.get(t2.target.stateNumber) == null) {
                        throw new IllegalStateException("Cannot serialize a transition to a removed state.");
                    }
                    int src = s2.stateNumber;
                    int trg = t2.target.stateNumber;
                    int edgeType2 = ((Integer) Transition.serializationTypes.get(t2.getClass())).intValue();
                    int arg1 = 0;
                    int arg2 = 0;
                    int arg3 = 0;
                    switch (edgeType2) {
                        case 2:
                            arg1 = ((RangeTransition) t2).from;
                            arg2 = ((RangeTransition) t2).to;
                            if (arg1 == -1) {
                                arg1 = 0;
                                arg3 = 1;
                                break;
                            } else {
                                break;
                            }
                        case 3:
                            trg = ((RuleTransition) t2).followState.stateNumber;
                            arg1 = ((RuleTransition) t2).target.stateNumber;
                            arg2 = ((RuleTransition) t2).ruleIndex;
                            arg3 = ((RuleTransition) t2).precedence;
                            break;
                        case 4:
                            PredicateTransition pt = (PredicateTransition) t2;
                            arg1 = pt.ruleIndex;
                            arg2 = pt.predIndex;
                            if (pt.isCtxDependent) {
                                arg3 = 1;
                                break;
                            } else {
                                arg3 = 0;
                                break;
                            }
                        case 5:
                            arg1 = ((AtomTransition) t2).label;
                            if (arg1 == -1) {
                                arg1 = 0;
                                arg3 = 1;
                                break;
                            } else {
                                break;
                            }
                        case 6:
                            ActionTransition at = (ActionTransition) t2;
                            arg1 = at.ruleIndex;
                            arg2 = at.actionIndex;
                            if (arg2 == -1) {
                                arg2 = 65535;
                            }
                            if (at.isCtxDependent) {
                                arg3 = 1;
                                break;
                            } else {
                                arg3 = 0;
                                break;
                            }
                        case 7:
                            arg1 = ((Integer) hashMap.get(((SetTransition) t2).set)).intValue();
                            break;
                        case 8:
                            arg1 = ((Integer) hashMap.get(((SetTransition) t2).set)).intValue();
                            break;
                        case 10:
                            PrecedencePredicateTransition ppt = (PrecedencePredicateTransition) t2;
                            arg1 = ppt.precedence;
                            break;
                    }
                    data.add(src);
                    data.add(trg);
                    data.add(edgeType2);
                    data.add(arg1);
                    data.add(arg2);
                    data.add(arg3);
                }
            }
        }
        int ndecisions = this.atn.decisionToState.size();
        data.add(ndecisions);
        for (DecisionState decStartState : this.atn.decisionToState) {
            data.add(decStartState.stateNumber);
        }
        if (this.atn.grammarType == ATNType.LEXER) {
            data.add(this.atn.lexerActions.length);
            LexerAction[] arr$ = this.atn.lexerActions;
            for (LexerAction action : arr$) {
                data.add(action.getActionType().ordinal());
                switch (action.getActionType()) {
                    case CHANNEL:
                        int channel = ((LexerChannelAction) action).getChannel();
                        if (channel == -1) {
                            channel = 65535;
                        }
                        data.add(channel);
                        data.add(0);
                        break;
                    case CUSTOM:
                        int ruleIndex = ((LexerCustomAction) action).getRuleIndex();
                        int actionIndex = ((LexerCustomAction) action).getActionIndex();
                        if (ruleIndex == -1) {
                            ruleIndex = 65535;
                        }
                        data.add(ruleIndex);
                        if (actionIndex == -1) {
                            actionIndex = 65535;
                        }
                        data.add(actionIndex);
                        break;
                    case MODE:
                        int mode = ((LexerModeAction) action).getMode();
                        if (mode == -1) {
                            mode = 65535;
                        }
                        data.add(mode);
                        data.add(0);
                        break;
                    case MORE:
                        data.add(0);
                        data.add(0);
                        break;
                    case POP_MODE:
                        data.add(0);
                        data.add(0);
                        break;
                    case PUSH_MODE:
                        int mode2 = ((LexerPushModeAction) action).getMode();
                        if (mode2 == -1) {
                            mode2 = 65535;
                        }
                        data.add(mode2);
                        data.add(0);
                        break;
                    case SKIP:
                        data.add(0);
                        data.add(0);
                        break;
                    case TYPE:
                        int type = ((LexerTypeAction) action).getType();
                        if (type == -1) {
                            type = 65535;
                        }
                        data.add(type);
                        data.add(0);
                        break;
                    default:
                        String message = String.format(Locale.getDefault(), "The specified lexer action type %s is not valid.", new Object[]{action.getActionType()});
                        throw new IllegalArgumentException(message);
                }
            }
        }
        for (int i5 = 1; i5 < data.size(); i5++) {
            if (data.get(i5) < 0 || data.get(i5) > 65535) {
                throw new UnsupportedOperationException("Serialized ATN data element " + data.get(i5) + " element " + i5 + " out of range 0..65535");
            }
            int value = (data.get(i5) + 2) & 65535;
            data.set(i5, value);
        }
        return data;
    }

    class 1 implements CodePointSerializer {
        1() {
        }

        public void serializeCodePoint(IntegerList data, int cp) {
            data.add(cp);
        }
    }

    class 2 implements CodePointSerializer {
        2() {
        }

        public void serializeCodePoint(IntegerList data, int cp) {
            ATNSerializer.access$000(ATNSerializer.this, data, cp);
        }
    }

    private static void serializeSets(IntegerList data, Collection collection, CodePointSerializer codePointSerializer) {
        int nSets = collection.size();
        data.add(nSets);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            IntervalSet set = (IntervalSet) it.next();
            boolean containsEof = set.contains(-1);
            if (containsEof && ((Interval) set.getIntervals().get(0)).b == -1) {
                data.add(set.getIntervals().size() - 1);
            } else {
                data.add(set.getIntervals().size());
            }
            data.add(containsEof ? 1 : 0);
            for (Interval I : set.getIntervals()) {
                if (I.a == -1) {
                    if (I.b != -1) {
                        codePointSerializer.serializeCodePoint(data, 0);
                    }
                } else {
                    codePointSerializer.serializeCodePoint(data, I.a);
                }
                codePointSerializer.serializeCodePoint(data, I.b);
            }
        }
    }

    public String decode(char[] data) {
        int p;
        int p2;
        int p3;
        int p4;
        int p5;
        int p6;
        char[] data2 = (char[]) data.clone();
        for (int i = 1; i < data2.length; i++) {
            data2[i] = (char) (data2[i] - 2);
        }
        StringBuilder buf = new StringBuilder();
        int p7 = 0 + 1;
        int version = ATNDeserializer.toInt(data2[0]);
        if (version != ATNDeserializer.SERIALIZED_VERSION) {
            String reason = String.format("Could not deserialize ATN with version %d (expected %d).", new Object[]{Integer.valueOf(version), Integer.valueOf(ATNDeserializer.SERIALIZED_VERSION)});
            throw new UnsupportedOperationException(new InvalidClassException(ATN.class.getName(), reason));
        }
        UUID uuid = ATNDeserializer.toUUID(data2, p7);
        int p8 = p7 + 8;
        if (!uuid.equals(ATNDeserializer.SERIALIZED_UUID)) {
            String reason2 = String.format(Locale.getDefault(), "Could not deserialize ATN with UUID %s (expected %s).", new Object[]{uuid, ATNDeserializer.SERIALIZED_UUID});
            throw new UnsupportedOperationException(new InvalidClassException(ATN.class.getName(), reason2));
        }
        int p9 = p8 + 1;
        int p10 = p9 + 1;
        int maxType = ATNDeserializer.toInt(data2[p9]);
        buf.append("max type ").append(maxType).append("\n");
        int p11 = p10 + 1;
        int nstates = ATNDeserializer.toInt(data2[p10]);
        int i2 = 0;
        while (true) {
            p = p11;
            if (i2 >= nstates) {
                break;
            }
            p11 = p + 1;
            int stype = ATNDeserializer.toInt(data2[p]);
            if (stype != 0) {
                int p12 = p11 + 1;
                int ruleIndex = ATNDeserializer.toInt(data2[p11]);
                if (ruleIndex == 65535) {
                    ruleIndex = -1;
                }
                String arg = "";
                if (stype == 12) {
                    p11 = p12 + 1;
                    int loopBackStateNumber = ATNDeserializer.toInt(data2[p12]);
                    arg = " " + loopBackStateNumber;
                } else if (stype == 4 || stype == 5 || stype == 3) {
                    p11 = p12 + 1;
                    int endStateNumber = ATNDeserializer.toInt(data2[p12]);
                    arg = " " + endStateNumber;
                } else {
                    p11 = p12;
                }
                buf.append(i2).append(":").append((String) ATNState.serializationNames.get(stype)).append(" ").append(ruleIndex).append(arg).append("\n");
            }
            i2++;
        }
        int p13 = p + 1;
        int numNonGreedyStates = ATNDeserializer.toInt(data2[p]);
        int i3 = 0;
        while (true) {
            p2 = p13;
            if (i3 >= numNonGreedyStates) {
                break;
            }
            p13 = p2 + 1;
            ATNDeserializer.toInt(data2[p2]);
            i3++;
        }
        int p14 = p2 + 1;
        int numPrecedenceStates = ATNDeserializer.toInt(data2[p2]);
        int i4 = 0;
        while (true) {
            p3 = p14;
            if (i4 >= numPrecedenceStates) {
                break;
            }
            p14 = p3 + 1;
            ATNDeserializer.toInt(data2[p3]);
            i4++;
        }
        int p15 = p3 + 1;
        int nrules = ATNDeserializer.toInt(data2[p3]);
        int i5 = 0;
        while (true) {
            p4 = p15;
            if (i5 >= nrules) {
                break;
            }
            p15 = p4 + 1;
            int s = ATNDeserializer.toInt(data2[p4]);
            if (this.atn.grammarType == ATNType.LEXER) {
                int arg1 = ATNDeserializer.toInt(data2[p15]);
                buf.append("rule ").append(i5).append(":").append(s).append(" ").append(arg1).append('\n');
                p15++;
            } else {
                buf.append("rule ").append(i5).append(":").append(s).append('\n');
            }
            i5++;
        }
        int p16 = p4 + 1;
        int nmodes = ATNDeserializer.toInt(data2[p4]);
        int i6 = 0;
        while (true) {
            p5 = p16;
            if (i6 >= nmodes) {
                break;
            }
            p16 = p5 + 1;
            int s2 = ATNDeserializer.toInt(data2[p5]);
            buf.append("mode ").append(i6).append(":").append(s2).append('\n');
            i6++;
        }
        int numBMPSets = ATNDeserializer.toInt(data2[p5]);
        int p17 = appendSets(buf, data2, p5 + 1, numBMPSets, 0, ATNDeserializer.getUnicodeDeserializer(ATNDeserializer.UnicodeDeserializingMode.UNICODE_BMP));
        int numSMPSets = ATNDeserializer.toInt(data2[p17]);
        int p18 = appendSets(buf, data2, p17 + 1, numSMPSets, numBMPSets, ATNDeserializer.getUnicodeDeserializer(ATNDeserializer.UnicodeDeserializingMode.UNICODE_SMP));
        int p19 = p18 + 1;
        int nedges = ATNDeserializer.toInt(data2[p18]);
        int i7 = 0;
        while (i7 < nedges) {
            int src = ATNDeserializer.toInt(data2[p19]);
            int trg = ATNDeserializer.toInt(data2[p19 + 1]);
            int ttype = ATNDeserializer.toInt(data2[p19 + 2]);
            int arg12 = ATNDeserializer.toInt(data2[p19 + 3]);
            int arg2 = ATNDeserializer.toInt(data2[p19 + 4]);
            int arg3 = ATNDeserializer.toInt(data2[p19 + 5]);
            buf.append(src).append("->").append(trg).append(" ").append((String) Transition.serializationNames.get(ttype)).append(" ").append(arg12).append(",").append(arg2).append(",").append(arg3).append("\n");
            i7++;
            p19 += 6;
        }
        int p20 = p19 + 1;
        int ndecisions = ATNDeserializer.toInt(data2[p19]);
        int i8 = 0;
        while (true) {
            p6 = p20;
            if (i8 >= ndecisions) {
                break;
            }
            p20 = p6 + 1;
            int s3 = ATNDeserializer.toInt(data2[p6]);
            buf.append(i8).append(":").append(s3).append("\n");
            i8++;
        }
        if (this.atn.grammarType == ATNType.LEXER) {
            int p21 = p6 + 1;
            int lexerActionCount = ATNDeserializer.toInt(data2[p6]);
            int i9 = 0;
            while (true) {
                p6 = p21;
                if (i9 >= lexerActionCount) {
                    break;
                }
                int p22 = p6 + 1;
                LexerActionType lexerActionType = LexerActionType.values()[ATNDeserializer.toInt(data2[p6])];
                int p23 = p22 + 1;
                ATNDeserializer.toInt(data2[p22]);
                p21 = p23 + 1;
                ATNDeserializer.toInt(data2[p23]);
                i9++;
            }
        }
        return buf.toString();
    }

    private int appendSets(StringBuilder buf, char[] data, int p, int nsets, int setIndexOffset, ATNDeserializer.UnicodeDeserializer unicodeDeserializer) {
        int i = 0;
        int p2 = p;
        while (i < nsets) {
            int p3 = p2 + 1;
            int nintervals = ATNDeserializer.toInt(data[p2]);
            buf.append(i + setIndexOffset).append(":");
            int p4 = p3 + 1;
            boolean containsEof = data[p3] != 0;
            if (containsEof) {
                buf.append(getTokenName(-1));
            }
            int p5 = p4;
            for (int j = 0; j < nintervals; j++) {
                if (containsEof || j > 0) {
                    buf.append(", ");
                }
                int a = unicodeDeserializer.readUnicode(data, p5);
                int p6 = p5 + unicodeDeserializer.size();
                int b = unicodeDeserializer.readUnicode(data, p6);
                p5 = p6 + unicodeDeserializer.size();
                buf.append(getTokenName(a)).append("..").append(getTokenName(b));
            }
            buf.append("\n");
            i++;
            p2 = p5;
        }
        return p2;
    }

    public String getTokenName(int t) {
        if (t == -1) {
            return "EOF";
        }
        if (this.atn.grammarType == ATNType.LEXER && t >= 0 && t <= 65535) {
            switch (t) {
                case 8:
                    return "'\\b'";
                case 9:
                    return "'\\t'";
                case 10:
                    return "'\\n'";
                case 12:
                    return "'\\f'";
                case 13:
                    return "'\\r'";
                case 39:
                    return "'\\''";
                case 92:
                    return "'\\\\'";
                default:
                    if (Character.UnicodeBlock.of((char) t) == Character.UnicodeBlock.BASIC_LATIN && !Character.isISOControl((char) t)) {
                        return '\'' + Character.toString((char) t) + '\'';
                    }
                    String hex = Integer.toHexString(65536 | t).toUpperCase().substring(1, 5);
                    return "'\\u" + hex + "'";
            }
        }
        if (this.tokenNames != null && t >= 0 && t < this.tokenNames.size()) {
            return (String) this.tokenNames.get(t);
        }
        return String.valueOf(t);
    }

    public static String getSerializedAsString(ATN atn) {
        return new String(getSerializedAsChars(atn));
    }

    public static IntegerList getSerialized(ATN atn) {
        return new ATNSerializer(atn).serialize();
    }

    public static char[] getSerializedAsChars(ATN atn) {
        return Utils.toCharArray(getSerialized(atn));
    }

    public static String getDecoded(ATN atn, List list) {
        IntegerList serialized = getSerialized(atn);
        char[] data = Utils.toCharArray(serialized);
        return new ATNSerializer(atn, list).decode(data);
    }

    private void serializeUUID(IntegerList data, UUID uuid) {
        serializeLong(data, uuid.getLeastSignificantBits());
        serializeLong(data, uuid.getMostSignificantBits());
    }

    private void serializeLong(IntegerList data, long value) {
        serializeInt(data, (int) value);
        serializeInt(data, (int) (value >> 32));
    }

    private void serializeInt(IntegerList data, int value) {
        data.add((char) value);
        data.add((char) (value >> 16));
    }
}
