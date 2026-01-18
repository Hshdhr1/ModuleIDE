package org.antlr.v4.runtime.atn;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.antlr.v4.runtime.misc.AbstractEqualityComparator;
import org.antlr.v4.runtime.misc.FlexibleHashMap;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public enum PredictionMode {
    SLL,
    LL,
    LL_EXACT_AMBIG_DETECTION;

    static class AltAndContextMap extends FlexibleHashMap {
        public AltAndContextMap() {
            super(AltAndContextConfigEqualityComparator.INSTANCE);
        }
    }

    private static final class AltAndContextConfigEqualityComparator extends AbstractEqualityComparator {
        public static final AltAndContextConfigEqualityComparator INSTANCE = new AltAndContextConfigEqualityComparator();

        private AltAndContextConfigEqualityComparator() {
        }

        public int hashCode(ATNConfig o) {
            int hashCode = MurmurHash.initialize(7);
            return MurmurHash.finish(MurmurHash.update(MurmurHash.update(hashCode, o.state.stateNumber), o.context), 2);
        }

        public boolean equals(ATNConfig a, ATNConfig b) {
            if (a == b) {
                return true;
            }
            if (a == null || b == null) {
                return false;
            }
            return a.state.stateNumber == b.state.stateNumber && a.context.equals(b.context);
        }
    }

    public static boolean hasSLLConflictTerminatingPrediction(PredictionMode mode, ATNConfigSet configs) {
        if (allConfigsInRuleStopStates(configs)) {
            return true;
        }
        if (mode == SLL && configs.hasSemanticContext) {
            ATNConfigSet dup = new ATNConfigSet();
            Iterator i$ = configs.iterator();
            while (i$.hasNext()) {
                ATNConfig c = (ATNConfig) i$.next();
                dup.add(new ATNConfig(c, SemanticContext.NONE));
            }
            configs = dup;
        }
        Collection<BitSet> altsets = getConflictingAltSubsets(configs);
        return hasConflictingAltSet(altsets) && !hasStateAssociatedWithOneAlt(configs);
    }

    public static boolean hasConfigInRuleStopState(ATNConfigSet configs) {
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (c.state instanceof RuleStopState) {
                return true;
            }
        }
        return false;
    }

    public static boolean allConfigsInRuleStopStates(ATNConfigSet configs) {
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig config = (ATNConfig) i$.next();
            if (!(config.state instanceof RuleStopState)) {
                return false;
            }
        }
        return true;
    }

    public static int resolvesToJustOneViableAlt(Collection collection) {
        return getSingleViableAlt(collection);
    }

    public static boolean allSubsetsConflict(Collection collection) {
        return !hasNonConflictingAltSet(collection);
    }

    public static boolean hasNonConflictingAltSet(Collection collection) {
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            BitSet alts = (BitSet) i$.next();
            if (alts.cardinality() == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasConflictingAltSet(Collection collection) {
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            BitSet alts = (BitSet) i$.next();
            if (alts.cardinality() > 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean allSubsetsEqual(Collection collection) {
        Iterator<BitSet> it = collection.iterator();
        BitSet first = (BitSet) it.next();
        while (it.hasNext()) {
            BitSet next = (BitSet) it.next();
            if (!next.equals(first)) {
                return false;
            }
        }
        return true;
    }

    public static int getUniqueAlt(Collection collection) {
        BitSet all = getAlts(collection);
        if (all.cardinality() == 1) {
            return all.nextSetBit(0);
        }
        return 0;
    }

    public static BitSet getAlts(Collection collection) {
        BitSet all = new BitSet();
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            BitSet alts = (BitSet) i$.next();
            all.or(alts);
        }
        return all;
    }

    public static BitSet getAlts(ATNConfigSet configs) {
        BitSet alts = new BitSet();
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig config = (ATNConfig) i$.next();
            alts.set(config.alt);
        }
        return alts;
    }

    public static Collection getConflictingAltSubsets(ATNConfigSet configs) {
        AltAndContextMap configToAlts = new AltAndContextMap();
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            BitSet alts = (BitSet) configToAlts.get(c);
            if (alts == null) {
                alts = new BitSet();
                configToAlts.put(c, alts);
            }
            alts.set(c.alt);
        }
        return configToAlts.values();
    }

    public static Map getStateToAltMap(ATNConfigSet configs) {
        HashMap hashMap = new HashMap();
        Iterator i$ = configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            BitSet alts = (BitSet) hashMap.get(c.state);
            if (alts == null) {
                alts = new BitSet();
                hashMap.put(c.state, alts);
            }
            alts.set(c.alt);
        }
        return hashMap;
    }

    public static boolean hasStateAssociatedWithOneAlt(ATNConfigSet configs) {
        Map<ATNState, BitSet> x = getStateToAltMap(configs);
        for (BitSet alts : x.values()) {
            if (alts.cardinality() == 1) {
                return true;
            }
        }
        return false;
    }

    public static int getSingleViableAlt(Collection collection) {
        BitSet viableAlts = new BitSet();
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            BitSet alts = (BitSet) i$.next();
            int minAlt = alts.nextSetBit(0);
            viableAlts.set(minAlt);
            if (viableAlts.cardinality() > 1) {
                return 0;
            }
        }
        return viableAlts.nextSetBit(0);
    }
}
