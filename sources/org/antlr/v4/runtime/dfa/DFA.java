package org.antlr.v4.runtime.dfa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.DecisionState;
import org.antlr.v4.runtime.atn.StarLoopEntryState;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class DFA {
    public final DecisionState atnStartState;
    public final int decision;
    private final boolean precedenceDfa;
    public volatile DFAState s0;
    public final Map states;

    public DFA(DecisionState atnStartState) {
        this(atnStartState, 0);
    }

    public DFA(DecisionState atnStartState, int decision) {
        this.states = new HashMap();
        this.atnStartState = atnStartState;
        this.decision = decision;
        boolean precedenceDfa = false;
        if ((atnStartState instanceof StarLoopEntryState) && ((StarLoopEntryState) atnStartState).isPrecedenceDecision) {
            precedenceDfa = true;
            DFAState precedenceState = new DFAState(new ATNConfigSet());
            precedenceState.edges = new DFAState[0];
            precedenceState.isAcceptState = false;
            precedenceState.requiresFullContext = false;
            this.s0 = precedenceState;
        }
        this.precedenceDfa = precedenceDfa;
    }

    public final boolean isPrecedenceDfa() {
        return this.precedenceDfa;
    }

    public final DFAState getPrecedenceStartState(int precedence) {
        if (!isPrecedenceDfa()) {
            throw new IllegalStateException("Only precedence DFAs may contain a precedence start state.");
        }
        if (precedence < 0 || precedence >= this.s0.edges.length) {
            return null;
        }
        return this.s0.edges[precedence];
    }

    public final void setPrecedenceStartState(int precedence, DFAState startState) {
        if (!isPrecedenceDfa()) {
            throw new IllegalStateException("Only precedence DFAs may contain a precedence start state.");
        }
        if (precedence >= 0) {
            synchronized (this.s0) {
                if (precedence >= this.s0.edges.length) {
                    this.s0.edges = (DFAState[]) Arrays.copyOf(this.s0.edges, precedence + 1);
                }
                this.s0.edges[precedence] = startState;
            }
        }
    }

    @Deprecated
    public final void setPrecedenceDfa(boolean precedenceDfa) {
        if (precedenceDfa != isPrecedenceDfa()) {
            throw new UnsupportedOperationException("The precedenceDfa field cannot change after a DFA is constructed.");
        }
    }

    class 1 implements Comparator {
        1() {
        }

        public int compare(DFAState o1, DFAState o2) {
            return o1.stateNumber - o2.stateNumber;
        }
    }

    public List getStates() {
        ArrayList arrayList = new ArrayList(this.states.keySet());
        Collections.sort(arrayList, new 1());
        return arrayList;
    }

    public String toString() {
        return toString(VocabularyImpl.EMPTY_VOCABULARY);
    }

    @Deprecated
    public String toString(String[] tokenNames) {
        if (this.s0 == null) {
            return "";
        }
        DFASerializer serializer = new DFASerializer(this, tokenNames);
        return serializer.toString();
    }

    public String toString(Vocabulary vocabulary) {
        if (this.s0 == null) {
            return "";
        }
        DFASerializer serializer = new DFASerializer(this, vocabulary);
        return serializer.toString();
    }

    public String toLexerString() {
        if (this.s0 == null) {
            return "";
        }
        DFASerializer serializer = new LexerDFASerializer(this);
        return serializer.toString();
    }
}
