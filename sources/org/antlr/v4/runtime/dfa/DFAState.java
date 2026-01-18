package org.antlr.v4.runtime.dfa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.antlr.v4.runtime.atn.ATNConfig;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.LexerActionExecutor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class DFAState {
    public ATNConfigSet configs;
    public DFAState[] edges;
    public boolean isAcceptState;
    public LexerActionExecutor lexerActionExecutor;
    public PredPrediction[] predicates;
    public int prediction;
    public boolean requiresFullContext;
    public int stateNumber;

    public static class PredPrediction {
        public int alt;
        public SemanticContext pred;

        public PredPrediction(SemanticContext pred, int alt) {
            this.alt = alt;
            this.pred = pred;
        }

        public String toString() {
            return "(" + this.pred + ", " + this.alt + ")";
        }
    }

    public DFAState() {
        this.stateNumber = -1;
        this.configs = new ATNConfigSet();
        this.isAcceptState = false;
    }

    public DFAState(int stateNumber) {
        this.stateNumber = -1;
        this.configs = new ATNConfigSet();
        this.isAcceptState = false;
        this.stateNumber = stateNumber;
    }

    public DFAState(ATNConfigSet configs) {
        this.stateNumber = -1;
        this.configs = new ATNConfigSet();
        this.isAcceptState = false;
        this.configs = configs;
    }

    public Set getAltSet() {
        HashSet hashSet = new HashSet();
        if (this.configs != null) {
            Iterator i$ = this.configs.iterator();
            while (i$.hasNext()) {
                ATNConfig c = (ATNConfig) i$.next();
                hashSet.add(Integer.valueOf(c.alt));
            }
        }
        if (hashSet.isEmpty()) {
            return null;
        }
        return hashSet;
    }

    public int hashCode() {
        int hash = MurmurHash.initialize(7);
        return MurmurHash.finish(MurmurHash.update(hash, this.configs.hashCode()), 1);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DFAState)) {
            return false;
        }
        DFAState other = (DFAState) o;
        return this.configs.equals(other.configs);
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.stateNumber).append(":").append(this.configs);
        if (this.isAcceptState) {
            buf.append("=>");
            if (this.predicates != null) {
                buf.append(Arrays.toString(this.predicates));
            } else {
                buf.append(this.prediction);
            }
        }
        return buf.toString();
    }
}
