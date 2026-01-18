package org.antlr.v4.runtime.atn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.DoubleKeyMap;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class PredictionContext {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final EmptyPredictionContext EMPTY;
    public static final int EMPTY_RETURN_STATE = Integer.MAX_VALUE;
    private static final int INITIAL_HASH = 1;
    public static int globalNodeCount;
    public final int cachedHashCode;
    public final int id;

    public abstract boolean equals(Object obj);

    public abstract PredictionContext getParent(int i);

    public abstract int getReturnState(int i);

    public abstract int size();

    static {
        $assertionsDisabled = !PredictionContext.class.desiredAssertionStatus();
        EMPTY = new EmptyPredictionContext();
        globalNodeCount = 0;
    }

    protected PredictionContext(int cachedHashCode) {
        int i = globalNodeCount;
        globalNodeCount = i + 1;
        this.id = i;
        this.cachedHashCode = cachedHashCode;
    }

    public static PredictionContext fromRuleContext(ATN atn, RuleContext outerContext) {
        if (outerContext == null) {
            outerContext = RuleContext.EMPTY;
        }
        if (outerContext.parent == null || outerContext == RuleContext.EMPTY) {
            return EMPTY;
        }
        EmptyPredictionContext emptyPredictionContext = EMPTY;
        PredictionContext parent = fromRuleContext(atn, outerContext.parent);
        ATNState state = (ATNState) atn.states.get(outerContext.invokingState);
        RuleTransition transition = (RuleTransition) state.transition(0);
        return SingletonPredictionContext.create(parent, transition.followState.stateNumber);
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean hasEmptyPath() {
        return getReturnState(size() + (-1)) == Integer.MAX_VALUE;
    }

    public final int hashCode() {
        return this.cachedHashCode;
    }

    protected static int calculateEmptyHashCode() {
        int hash = MurmurHash.initialize(1);
        return MurmurHash.finish(hash, 0);
    }

    protected static int calculateHashCode(PredictionContext parent, int returnState) {
        int hash = MurmurHash.initialize(1);
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(hash, parent), returnState), 2);
    }

    protected static int calculateHashCode(PredictionContext[] parents, int[] returnStates) {
        int hash = MurmurHash.initialize(1);
        for (PredictionContext parent : parents) {
            hash = MurmurHash.update(hash, parent);
        }
        for (int returnState : returnStates) {
            hash = MurmurHash.update(hash, returnState);
        }
        return MurmurHash.finish(hash, parents.length * 2);
    }

    public static PredictionContext merge(PredictionContext a, PredictionContext b, boolean rootIsWildcard, DoubleKeyMap doubleKeyMap) {
        if (!$assertionsDisabled && (a == null || b == null)) {
            throw new AssertionError();
        }
        if (a != b && !a.equals(b)) {
            if ((a instanceof SingletonPredictionContext) && (b instanceof SingletonPredictionContext)) {
                return mergeSingletons((SingletonPredictionContext) a, (SingletonPredictionContext) b, rootIsWildcard, doubleKeyMap);
            }
            if (rootIsWildcard) {
                if (!(a instanceof EmptyPredictionContext)) {
                    if (b instanceof EmptyPredictionContext) {
                        return b;
                    }
                } else {
                    return a;
                }
            }
            if (a instanceof SingletonPredictionContext) {
                a = new ArrayPredictionContext((SingletonPredictionContext) a);
            }
            if (b instanceof SingletonPredictionContext) {
                b = new ArrayPredictionContext((SingletonPredictionContext) b);
            }
            return mergeArrays((ArrayPredictionContext) a, (ArrayPredictionContext) b, rootIsWildcard, doubleKeyMap);
        }
        return a;
    }

    public static PredictionContext mergeSingletons(SingletonPredictionContext a, SingletonPredictionContext b, boolean rootIsWildcard, DoubleKeyMap doubleKeyMap) {
        if (doubleKeyMap != null) {
            PredictionContext previous = (PredictionContext) doubleKeyMap.get(a, b);
            if (previous != null) {
                return previous;
            }
            PredictionContext previous2 = (PredictionContext) doubleKeyMap.get(b, a);
            if (previous2 != null) {
                return previous2;
            }
        }
        PredictionContext rootMerge = mergeRoot(a, b, rootIsWildcard);
        if (rootMerge != null) {
            if (doubleKeyMap != null) {
                doubleKeyMap.put(a, b, rootMerge);
            }
            return rootMerge;
        }
        if (a.returnState == b.returnState) {
            PredictionContext parent = merge(a.parent, b.parent, rootIsWildcard, doubleKeyMap);
            if (parent == a.parent) {
                return a;
            }
            if (parent == b.parent) {
                return b;
            }
            PredictionContext a_ = SingletonPredictionContext.create(parent, a.returnState);
            if (doubleKeyMap != null) {
                doubleKeyMap.put(a, b, a_);
                return a_;
            }
            return a_;
        }
        PredictionContext singleParent = null;
        if (a == b || (a.parent != null && a.parent.equals(b.parent))) {
            singleParent = a.parent;
        }
        if (singleParent != null) {
            int[] payloads = {a.returnState, b.returnState};
            if (a.returnState > b.returnState) {
                payloads[0] = b.returnState;
                payloads[1] = a.returnState;
            }
            PredictionContext[] parents = {singleParent, singleParent};
            PredictionContext a_2 = new ArrayPredictionContext(parents, payloads);
            if (doubleKeyMap != null) {
                doubleKeyMap.put(a, b, a_2);
                return a_2;
            }
            return a_2;
        }
        int[] payloads2 = {a.returnState, b.returnState};
        PredictionContext[] parents2 = {a.parent, b.parent};
        if (a.returnState > b.returnState) {
            payloads2[0] = b.returnState;
            payloads2[1] = a.returnState;
            parents2 = new PredictionContext[]{b.parent, a.parent};
        }
        PredictionContext a_3 = new ArrayPredictionContext(parents2, payloads2);
        if (doubleKeyMap != null) {
            doubleKeyMap.put(a, b, a_3);
            return a_3;
        }
        return a_3;
    }

    public static PredictionContext mergeRoot(SingletonPredictionContext a, SingletonPredictionContext b, boolean rootIsWildcard) {
        if (rootIsWildcard) {
            if (a == EMPTY || b == EMPTY) {
                return EMPTY;
            }
            return null;
        }
        if (a == EMPTY && b == EMPTY) {
            return EMPTY;
        }
        if (a == EMPTY) {
            int[] payloads = {b.returnState, Integer.MAX_VALUE};
            PredictionContext[] parents = {b.parent, null};
            return new ArrayPredictionContext(parents, payloads);
        }
        if (b != EMPTY) {
            return null;
        }
        int[] payloads2 = {a.returnState, Integer.MAX_VALUE};
        PredictionContext[] parents2 = {a.parent, null};
        return new ArrayPredictionContext(parents2, payloads2);
    }

    public static PredictionContext mergeArrays(ArrayPredictionContext a, ArrayPredictionContext b, boolean rootIsWildcard, DoubleKeyMap doubleKeyMap) {
        if (doubleKeyMap != null) {
            PredictionContext previous = (PredictionContext) doubleKeyMap.get(a, b);
            if (previous == null) {
                PredictionContext previous2 = (PredictionContext) doubleKeyMap.get(b, a);
                if (previous2 != null) {
                    return previous2;
                }
            } else {
                return previous;
            }
        }
        int i = 0;
        int j = 0;
        int k = 0;
        int[] mergedReturnStates = new int[a.returnStates.length + b.returnStates.length];
        PredictionContext[] mergedParents = new PredictionContext[a.returnStates.length + b.returnStates.length];
        while (i < a.returnStates.length && j < b.returnStates.length) {
            PredictionContext a_parent = a.parents[i];
            PredictionContext b_parent = b.parents[j];
            if (a.returnStates[i] == b.returnStates[j]) {
                int payload = a.returnStates[i];
                boolean both$ = payload == Integer.MAX_VALUE && a_parent == null && b_parent == null;
                boolean ax_ax = (a_parent == null || b_parent == null || !a_parent.equals(b_parent)) ? false : true;
                if (both$ || ax_ax) {
                    mergedParents[k] = a_parent;
                    mergedReturnStates[k] = payload;
                } else {
                    PredictionContext mergedParent = merge(a_parent, b_parent, rootIsWildcard, doubleKeyMap);
                    mergedParents[k] = mergedParent;
                    mergedReturnStates[k] = payload;
                }
                i++;
                j++;
            } else if (a.returnStates[i] < b.returnStates[j]) {
                mergedParents[k] = a_parent;
                mergedReturnStates[k] = a.returnStates[i];
                i++;
            } else {
                mergedParents[k] = b_parent;
                mergedReturnStates[k] = b.returnStates[j];
                j++;
            }
            k++;
        }
        if (i < a.returnStates.length) {
            for (int p = i; p < a.returnStates.length; p++) {
                mergedParents[k] = a.parents[p];
                mergedReturnStates[k] = a.returnStates[p];
                k++;
            }
        } else {
            for (int p2 = j; p2 < b.returnStates.length; p2++) {
                mergedParents[k] = b.parents[p2];
                mergedReturnStates[k] = b.returnStates[p2];
                k++;
            }
        }
        if (k < mergedParents.length) {
            if (k == 1) {
                PredictionContext a_ = SingletonPredictionContext.create(mergedParents[0], mergedReturnStates[0]);
                if (doubleKeyMap != null) {
                    doubleKeyMap.put(a, b, a_);
                }
                return a_;
            }
            mergedParents = (PredictionContext[]) Arrays.copyOf(mergedParents, k);
            mergedReturnStates = Arrays.copyOf(mergedReturnStates, k);
        }
        PredictionContext M = new ArrayPredictionContext(mergedParents, mergedReturnStates);
        if (M.equals(a)) {
            if (doubleKeyMap != null) {
                doubleKeyMap.put(a, b, a);
            }
            return a;
        }
        if (M.equals(b)) {
            if (doubleKeyMap != null) {
                doubleKeyMap.put(a, b, b);
            }
            return b;
        }
        combineCommonParents(mergedParents);
        if (doubleKeyMap != null) {
            doubleKeyMap.put(a, b, M);
        }
        return M;
    }

    protected static void combineCommonParents(PredictionContext[] parents) {
        HashMap hashMap = new HashMap();
        for (PredictionContext parent : parents) {
            if (!hashMap.containsKey(parent)) {
                hashMap.put(parent, parent);
            }
        }
        for (int p = 0; p < parents.length; p++) {
            parents[p] = (PredictionContext) hashMap.get(parents[p]);
        }
    }

    public static String toDOTString(PredictionContext context) {
        if (context == null) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        buf.append("digraph G {\n");
        buf.append("rankdir=LR;\n");
        List<PredictionContext> nodes = getAllContextNodes(context);
        Collections.sort(nodes, new 1());
        for (PredictionContext current : nodes) {
            if (current instanceof SingletonPredictionContext) {
                String s = String.valueOf(current.id);
                buf.append("  s").append(s);
                String returnState = String.valueOf(current.getReturnState(0));
                if (current instanceof EmptyPredictionContext) {
                    returnState = "$";
                }
                buf.append(" [label=\"").append(returnState).append("\"];\n");
            } else {
                ArrayPredictionContext arr = (ArrayPredictionContext) current;
                buf.append("  s").append(arr.id);
                buf.append(" [shape=box, label=\"");
                buf.append("[");
                boolean first = true;
                int[] arr$ = arr.returnStates;
                for (int inv : arr$) {
                    if (!first) {
                        buf.append(", ");
                    }
                    if (inv == Integer.MAX_VALUE) {
                        buf.append("$");
                    } else {
                        buf.append(inv);
                    }
                    first = false;
                }
                buf.append("]");
                buf.append("\"];\n");
            }
        }
        for (PredictionContext current2 : nodes) {
            if (current2 != EMPTY) {
                for (int i = 0; i < current2.size(); i++) {
                    if (current2.getParent(i) != null) {
                        String s2 = String.valueOf(current2.id);
                        buf.append("  s").append(s2);
                        buf.append("->");
                        buf.append("s");
                        buf.append(current2.getParent(i).id);
                        if (current2.size() > 1) {
                            buf.append(" [label=\"parent[" + i + "]\"];\n");
                        } else {
                            buf.append(";\n");
                        }
                    }
                }
            }
        }
        buf.append("}\n");
        return buf.toString();
    }

    static class 1 implements Comparator {
        1() {
        }

        public int compare(PredictionContext o1, PredictionContext o2) {
            return o1.id - o2.id;
        }
    }

    public static PredictionContext getCachedContext(PredictionContext context, PredictionContextCache contextCache, IdentityHashMap identityHashMap) {
        PredictionContext updated;
        if (!context.isEmpty()) {
            PredictionContext existing = (PredictionContext) identityHashMap.get(context);
            if (existing != null) {
                return existing;
            }
            PredictionContext existing2 = contextCache.get(context);
            if (existing2 != null) {
                identityHashMap.put(context, existing2);
                return existing2;
            }
            boolean changed = false;
            PredictionContext[] parents = new PredictionContext[context.size()];
            for (int i = 0; i < parents.length; i++) {
                PredictionContext parent = getCachedContext(context.getParent(i), contextCache, identityHashMap);
                if (changed || parent != context.getParent(i)) {
                    if (!changed) {
                        parents = new PredictionContext[context.size()];
                        for (int j = 0; j < context.size(); j++) {
                            parents[j] = context.getParent(j);
                        }
                        changed = true;
                    }
                    parents[i] = parent;
                }
            }
            if (!changed) {
                contextCache.add(context);
                identityHashMap.put(context, context);
                return context;
            }
            if (parents.length == 0) {
                updated = EMPTY;
            } else if (parents.length == 1) {
                updated = SingletonPredictionContext.create(parents[0], context.getReturnState(0));
            } else {
                ArrayPredictionContext arrayPredictionContext = (ArrayPredictionContext) context;
                updated = new ArrayPredictionContext(parents, arrayPredictionContext.returnStates);
            }
            contextCache.add(updated);
            identityHashMap.put(updated, updated);
            identityHashMap.put(context, updated);
            return updated;
        }
        return context;
    }

    public static List getAllContextNodes(PredictionContext context) {
        ArrayList arrayList = new ArrayList();
        getAllContextNodes_(context, arrayList, new IdentityHashMap());
        return arrayList;
    }

    public static void getAllContextNodes_(PredictionContext context, List list, Map map) {
        if (context != null && !map.containsKey(context)) {
            map.put(context, context);
            list.add(context);
            for (int i = 0; i < context.size(); i++) {
                getAllContextNodes_(context.getParent(i), list, map);
            }
        }
    }

    public String toString(Recognizer recognizer) {
        return toString();
    }

    public String[] toStrings(Recognizer recognizer, int currentState) {
        return toStrings(recognizer, EMPTY, currentState);
    }

    public String[] toStrings(Recognizer recognizer, PredictionContext stop, int currentState) {
        ArrayList arrayList = new ArrayList();
        int perm = 0;
        while (true) {
            int offset = 0;
            boolean last = true;
            PredictionContext p = this;
            int stateNumber = currentState;
            StringBuilder localBuffer = new StringBuilder();
            localBuffer.append("[");
            while (!p.isEmpty() && p != stop) {
                int index = 0;
                if (p.size() > 0) {
                    int bits = 1;
                    while ((1 << bits) < p.size()) {
                        bits++;
                    }
                    int mask = (1 << bits) - 1;
                    index = (perm >> offset) & mask;
                    last &= index >= p.size() + (-1);
                    if (index >= p.size()) {
                        break;
                    }
                    offset += bits;
                }
                if (recognizer != null) {
                    if (localBuffer.length() > 1) {
                        localBuffer.append(' ');
                    }
                    ATN atn = recognizer.getATN();
                    ATNState s = (ATNState) atn.states.get(stateNumber);
                    String ruleName = recognizer.getRuleNames()[s.ruleIndex];
                    localBuffer.append(ruleName);
                } else if (p.getReturnState(index) != Integer.MAX_VALUE && !p.isEmpty()) {
                    if (localBuffer.length() > 1) {
                        localBuffer.append(' ');
                    }
                    localBuffer.append(p.getReturnState(index));
                }
                stateNumber = p.getReturnState(index);
                p = p.getParent(index);
            }
            localBuffer.append("]");
            arrayList.add(localBuffer.toString());
            if (!last) {
                perm++;
            } else {
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
        }
    }
}
