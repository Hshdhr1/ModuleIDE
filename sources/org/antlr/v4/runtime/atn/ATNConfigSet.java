package org.antlr.v4.runtime.atn;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.misc.AbstractEqualityComparator;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.antlr.v4.runtime.misc.DoubleKeyMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ATNConfigSet implements Set {
    private int cachedHashCode;
    public AbstractConfigHashSet configLookup;
    public final ArrayList configs;
    protected BitSet conflictingAlts;
    public boolean dipsIntoOuterContext;
    public final boolean fullCtx;
    public boolean hasSemanticContext;
    protected boolean readonly;
    public int uniqueAlt;

    public static class ConfigHashSet extends AbstractConfigHashSet {
        public ConfigHashSet() {
            super(ConfigEqualityComparator.INSTANCE);
        }
    }

    public static final class ConfigEqualityComparator extends AbstractEqualityComparator {
        public static final ConfigEqualityComparator INSTANCE = new ConfigEqualityComparator();

        private ConfigEqualityComparator() {
        }

        public int hashCode(ATNConfig o) {
            int hashCode = o.state.stateNumber + 217;
            return (((hashCode * 31) + o.alt) * 31) + o.semanticContext.hashCode();
        }

        public boolean equals(ATNConfig a, ATNConfig b) {
            if (a == b) {
                return true;
            }
            if (a == null || b == null) {
                return false;
            }
            return a.state.stateNumber == b.state.stateNumber && a.alt == b.alt && a.semanticContext.equals(b.semanticContext);
        }
    }

    public ATNConfigSet(boolean fullCtx) {
        this.readonly = false;
        this.configs = new ArrayList(7);
        this.cachedHashCode = -1;
        this.configLookup = new ConfigHashSet();
        this.fullCtx = fullCtx;
    }

    public ATNConfigSet() {
        this(true);
    }

    public ATNConfigSet(ATNConfigSet old) {
        this(old.fullCtx);
        addAll(old);
        this.uniqueAlt = old.uniqueAlt;
        this.conflictingAlts = old.conflictingAlts;
        this.hasSemanticContext = old.hasSemanticContext;
        this.dipsIntoOuterContext = old.dipsIntoOuterContext;
    }

    public boolean add(ATNConfig config) {
        return add(config, null);
    }

    public boolean add(ATNConfig config, DoubleKeyMap doubleKeyMap) {
        if (this.readonly) {
            throw new IllegalStateException("This set is readonly");
        }
        if (config.semanticContext != SemanticContext.NONE) {
            this.hasSemanticContext = true;
        }
        if (config.getOuterContextDepth() > 0) {
            this.dipsIntoOuterContext = true;
        }
        ATNConfig existing = (ATNConfig) this.configLookup.getOrAdd(config);
        if (existing == config) {
            this.cachedHashCode = -1;
            this.configs.add(config);
        } else {
            boolean rootIsWildcard = !this.fullCtx;
            PredictionContext merged = PredictionContext.merge(existing.context, config.context, rootIsWildcard, doubleKeyMap);
            existing.reachesIntoOuterContext = Math.max(existing.reachesIntoOuterContext, config.reachesIntoOuterContext);
            if (config.isPrecedenceFilterSuppressed()) {
                existing.setPrecedenceFilterSuppressed(true);
            }
            existing.context = merged;
        }
        return true;
    }

    public List elements() {
        return this.configs;
    }

    public Set getStates() {
        HashSet hashSet = new HashSet();
        Iterator i$ = this.configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            hashSet.add(c.state);
        }
        return hashSet;
    }

    public BitSet getAlts() {
        BitSet alts = new BitSet();
        Iterator i$ = this.configs.iterator();
        while (i$.hasNext()) {
            ATNConfig config = (ATNConfig) i$.next();
            alts.set(config.alt);
        }
        return alts;
    }

    public List getPredicates() {
        ArrayList arrayList = new ArrayList();
        Iterator i$ = this.configs.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            if (c.semanticContext != SemanticContext.NONE) {
                arrayList.add(c.semanticContext);
            }
        }
        return arrayList;
    }

    public ATNConfig get(int i) {
        return (ATNConfig) this.configs.get(i);
    }

    public void optimizeConfigs(ATNSimulator interpreter) {
        if (this.readonly) {
            throw new IllegalStateException("This set is readonly");
        }
        if (!this.configLookup.isEmpty()) {
            Iterator i$ = this.configs.iterator();
            while (i$.hasNext()) {
                ATNConfig config = (ATNConfig) i$.next();
                config.context = interpreter.getCachedContext(config.context);
            }
        }
    }

    public boolean addAll(Collection collection) {
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            ATNConfig c = (ATNConfig) i$.next();
            add(c);
        }
        return false;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ATNConfigSet)) {
            return false;
        }
        ATNConfigSet other = (ATNConfigSet) o;
        return this.configs != null && this.configs.equals(other.configs) && this.fullCtx == other.fullCtx && this.uniqueAlt == other.uniqueAlt && this.conflictingAlts == other.conflictingAlts && this.hasSemanticContext == other.hasSemanticContext && this.dipsIntoOuterContext == other.dipsIntoOuterContext;
    }

    public int hashCode() {
        if (!isReadonly()) {
            return this.configs.hashCode();
        }
        if (this.cachedHashCode == -1) {
            this.cachedHashCode = this.configs.hashCode();
        }
        return this.cachedHashCode;
    }

    public int size() {
        return this.configs.size();
    }

    public boolean isEmpty() {
        return this.configs.isEmpty();
    }

    public boolean contains(Object o) {
        if (this.configLookup == null) {
            throw new UnsupportedOperationException("This method is not implemented for readonly sets.");
        }
        return this.configLookup.contains(o);
    }

    public boolean containsFast(ATNConfig obj) {
        if (this.configLookup == null) {
            throw new UnsupportedOperationException("This method is not implemented for readonly sets.");
        }
        return this.configLookup.containsFast(obj);
    }

    public Iterator iterator() {
        return this.configs.iterator();
    }

    public void clear() {
        if (this.readonly) {
            throw new IllegalStateException("This set is readonly");
        }
        this.configs.clear();
        this.cachedHashCode = -1;
        this.configLookup.clear();
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
        this.configLookup = null;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(elements().toString());
        if (this.hasSemanticContext) {
            buf.append(",hasSemanticContext=").append(this.hasSemanticContext);
        }
        if (this.uniqueAlt != 0) {
            buf.append(",uniqueAlt=").append(this.uniqueAlt);
        }
        if (this.conflictingAlts != null) {
            buf.append(",conflictingAlts=").append(this.conflictingAlts);
        }
        if (this.dipsIntoOuterContext) {
            buf.append(",dipsIntoOuterContext");
        }
        return buf.toString();
    }

    public ATNConfig[] toArray() {
        return (ATNConfig[]) this.configLookup.toArray();
    }

    public Object[] toArray(Object[] objArr) {
        return this.configLookup.toArray(objArr);
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public static abstract class AbstractConfigHashSet extends Array2DHashSet {
        public AbstractConfigHashSet(AbstractEqualityComparator abstractEqualityComparator) {
            this(abstractEqualityComparator, 16, 2);
        }

        public AbstractConfigHashSet(AbstractEqualityComparator abstractEqualityComparator, int initialCapacity, int initialBucketCapacity) {
            super(abstractEqualityComparator, initialCapacity, initialBucketCapacity);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final ATNConfig asElementType(Object o) {
            if (o instanceof ATNConfig) {
                return (ATNConfig) o;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final ATNConfig[][] createBuckets(int capacity) {
            return new ATNConfig[capacity][];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final ATNConfig[] createBucket(int capacity) {
            return new ATNConfig[capacity];
        }
    }
}
