package org.antlr.v4.runtime.atn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.MurmurHash;
import org.antlr.v4.runtime.misc.Utils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class SemanticContext {
    public static final SemanticContext NONE = new Predicate();

    public static abstract class Operator extends SemanticContext {
        public abstract Collection getOperands();
    }

    public abstract boolean eval(Recognizer recognizer, RuleContext ruleContext);

    static /* synthetic */ List access$000(Collection x0) {
        return filterPrecedencePredicates(x0);
    }

    public SemanticContext evalPrecedence(Recognizer recognizer, RuleContext parserCallStack) {
        return this;
    }

    public static class Predicate extends SemanticContext {
        public final boolean isCtxDependent;
        public final int predIndex;
        public final int ruleIndex;

        protected Predicate() {
            this.ruleIndex = -1;
            this.predIndex = -1;
            this.isCtxDependent = false;
        }

        public Predicate(int ruleIndex, int predIndex, boolean isCtxDependent) {
            this.ruleIndex = ruleIndex;
            this.predIndex = predIndex;
            this.isCtxDependent = isCtxDependent;
        }

        public boolean eval(Recognizer recognizer, RuleContext parserCallStack) {
            RuleContext localctx = this.isCtxDependent ? parserCallStack : null;
            return recognizer.sempred(localctx, this.ruleIndex, this.predIndex);
        }

        public int hashCode() {
            int hashCode = MurmurHash.initialize();
            return MurmurHash.finish(MurmurHash.update(MurmurHash.update(MurmurHash.update(hashCode, this.ruleIndex), this.predIndex), this.isCtxDependent ? 1 : 0), 3);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Predicate)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            Predicate p = (Predicate) obj;
            return this.ruleIndex == p.ruleIndex && this.predIndex == p.predIndex && this.isCtxDependent == p.isCtxDependent;
        }

        public String toString() {
            return "{" + this.ruleIndex + ":" + this.predIndex + "}?";
        }
    }

    public static class PrecedencePredicate extends SemanticContext implements Comparable {
        public final int precedence;

        protected PrecedencePredicate() {
            this.precedence = 0;
        }

        public PrecedencePredicate(int precedence) {
            this.precedence = precedence;
        }

        public boolean eval(Recognizer recognizer, RuleContext parserCallStack) {
            return recognizer.precpred(parserCallStack, this.precedence);
        }

        public SemanticContext evalPrecedence(Recognizer recognizer, RuleContext parserCallStack) {
            if (recognizer.precpred(parserCallStack, this.precedence)) {
                return SemanticContext.NONE;
            }
            return null;
        }

        public int compareTo(PrecedencePredicate o) {
            return this.precedence - o.precedence;
        }

        public int hashCode() {
            int hashCode = this.precedence + 31;
            return hashCode;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PrecedencePredicate)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            PrecedencePredicate other = (PrecedencePredicate) obj;
            return this.precedence == other.precedence;
        }

        public String toString() {
            return "{" + this.precedence + ">=prec}?";
        }
    }

    public static class AND extends Operator {
        public final SemanticContext[] opnds;

        public AND(SemanticContext a, SemanticContext b) {
            HashSet hashSet = new HashSet();
            if (a instanceof AND) {
                hashSet.addAll(Arrays.asList(((AND) a).opnds));
            } else {
                hashSet.add(a);
            }
            if (b instanceof AND) {
                hashSet.addAll(Arrays.asList(((AND) b).opnds));
            } else {
                hashSet.add(b);
            }
            List<PrecedencePredicate> precedencePredicates = SemanticContext.access$000(hashSet);
            if (!precedencePredicates.isEmpty()) {
                PrecedencePredicate reduced = (PrecedencePredicate) Collections.min(precedencePredicates);
                hashSet.add(reduced);
            }
            this.opnds = (SemanticContext[]) hashSet.toArray(new SemanticContext[hashSet.size()]);
        }

        public Collection getOperands() {
            return Arrays.asList(this.opnds);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AND)) {
                return false;
            }
            AND other = (AND) obj;
            return Arrays.equals(this.opnds, other.opnds);
        }

        public int hashCode() {
            return MurmurHash.hashCode(this.opnds, AND.class.hashCode());
        }

        public boolean eval(Recognizer recognizer, RuleContext parserCallStack) {
            SemanticContext[] arr$ = this.opnds;
            for (SemanticContext opnd : arr$) {
                if (!opnd.eval(recognizer, parserCallStack)) {
                    return false;
                }
            }
            return true;
        }

        public SemanticContext evalPrecedence(Recognizer recognizer, RuleContext parserCallStack) {
            boolean differs = false;
            ArrayList arrayList = new ArrayList();
            SemanticContext[] arr$ = this.opnds;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$++) {
                SemanticContext context = arr$[i$];
                SemanticContext evaluated = context.evalPrecedence(recognizer, parserCallStack);
                differs |= evaluated != context;
                if (evaluated == null) {
                    return null;
                }
                if (evaluated != NONE) {
                    arrayList.add(evaluated);
                }
            }
            if (differs) {
                if (arrayList.isEmpty()) {
                    return NONE;
                }
                SemanticContext result = (SemanticContext) arrayList.get(0);
                for (int i = 1; i < arrayList.size(); i++) {
                    result = SemanticContext.and(result, (SemanticContext) arrayList.get(i));
                }
                return result;
            }
            return this;
        }

        public String toString() {
            return Utils.join(Arrays.asList(this.opnds).iterator(), "&&");
        }
    }

    public static class OR extends Operator {
        public final SemanticContext[] opnds;

        public OR(SemanticContext a, SemanticContext b) {
            HashSet hashSet = new HashSet();
            if (a instanceof OR) {
                hashSet.addAll(Arrays.asList(((OR) a).opnds));
            } else {
                hashSet.add(a);
            }
            if (b instanceof OR) {
                hashSet.addAll(Arrays.asList(((OR) b).opnds));
            } else {
                hashSet.add(b);
            }
            List<PrecedencePredicate> precedencePredicates = SemanticContext.access$000(hashSet);
            if (!precedencePredicates.isEmpty()) {
                PrecedencePredicate reduced = (PrecedencePredicate) Collections.max(precedencePredicates);
                hashSet.add(reduced);
            }
            this.opnds = (SemanticContext[]) hashSet.toArray(new SemanticContext[hashSet.size()]);
        }

        public Collection getOperands() {
            return Arrays.asList(this.opnds);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OR)) {
                return false;
            }
            OR other = (OR) obj;
            return Arrays.equals(this.opnds, other.opnds);
        }

        public int hashCode() {
            return MurmurHash.hashCode(this.opnds, OR.class.hashCode());
        }

        public boolean eval(Recognizer recognizer, RuleContext parserCallStack) {
            SemanticContext[] arr$ = this.opnds;
            for (SemanticContext opnd : arr$) {
                if (opnd.eval(recognizer, parserCallStack)) {
                    return true;
                }
            }
            return false;
        }

        public SemanticContext evalPrecedence(Recognizer recognizer, RuleContext parserCallStack) {
            boolean differs = false;
            ArrayList arrayList = new ArrayList();
            SemanticContext[] arr$ = this.opnds;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$++) {
                SemanticContext context = arr$[i$];
                SemanticContext evaluated = context.evalPrecedence(recognizer, parserCallStack);
                differs |= evaluated != context;
                if (evaluated == NONE) {
                    return NONE;
                }
                if (evaluated != null) {
                    arrayList.add(evaluated);
                }
            }
            if (differs) {
                if (arrayList.isEmpty()) {
                    return null;
                }
                SemanticContext result = (SemanticContext) arrayList.get(0);
                for (int i = 1; i < arrayList.size(); i++) {
                    result = SemanticContext.or(result, (SemanticContext) arrayList.get(i));
                }
                return result;
            }
            return this;
        }

        public String toString() {
            return Utils.join(Arrays.asList(this.opnds).iterator(), "||");
        }
    }

    public static SemanticContext and(SemanticContext a, SemanticContext b) {
        if (a == null || a == NONE) {
            return b;
        }
        if (b == null || b == NONE) {
            return a;
        }
        AND result = new AND(a, b);
        if (result.opnds.length == 1) {
            return result.opnds[0];
        }
        return result;
    }

    public static SemanticContext or(SemanticContext a, SemanticContext b) {
        if (a != null) {
            if (b == null) {
                return a;
            }
            if (a == NONE || b == NONE) {
                return NONE;
            }
            OR result = new OR(a, b);
            return result.opnds.length == 1 ? result.opnds[0] : result;
        }
        return b;
    }

    private static List filterPrecedencePredicates(Collection collection) {
        ArrayList<PrecedencePredicate> result = null;
        Iterator<? extends SemanticContext> iterator = collection.iterator();
        while (iterator.hasNext()) {
            SemanticContext context = (SemanticContext) iterator.next();
            if (context instanceof PrecedencePredicate) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add((PrecedencePredicate) context);
                iterator.remove();
            }
        }
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }
}
