package org.antlr.v4.runtime.atn;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.dfa.DFA;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParseInfo {
    protected final ProfilingATNSimulator atnSimulator;

    public ParseInfo(ProfilingATNSimulator atnSimulator) {
        this.atnSimulator = atnSimulator;
    }

    public DecisionInfo[] getDecisionInfo() {
        return this.atnSimulator.getDecisionInfo();
    }

    public List getLLDecisions() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < decisions.length; i++) {
            long fallBack = decisions[i].LL_Fallback;
            if (fallBack > 0) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    public long getTotalTimeInPrediction() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        long t = 0;
        for (DecisionInfo decisionInfo : decisions) {
            t += decisionInfo.timeInPrediction;
        }
        return t;
    }

    public long getTotalSLLLookaheadOps() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        long k = 0;
        for (DecisionInfo decisionInfo : decisions) {
            k += decisionInfo.SLL_TotalLook;
        }
        return k;
    }

    public long getTotalLLLookaheadOps() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        long k = 0;
        for (DecisionInfo decisionInfo : decisions) {
            k += decisionInfo.LL_TotalLook;
        }
        return k;
    }

    public long getTotalSLLATNLookaheadOps() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        long k = 0;
        for (DecisionInfo decisionInfo : decisions) {
            k += decisionInfo.SLL_ATNTransitions;
        }
        return k;
    }

    public long getTotalLLATNLookaheadOps() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        long k = 0;
        for (DecisionInfo decisionInfo : decisions) {
            k += decisionInfo.LL_ATNTransitions;
        }
        return k;
    }

    public long getTotalATNLookaheadOps() {
        DecisionInfo[] decisions = this.atnSimulator.getDecisionInfo();
        long k = 0;
        for (int i = 0; i < decisions.length; i++) {
            k = k + decisions[i].SLL_ATNTransitions + decisions[i].LL_ATNTransitions;
        }
        return k;
    }

    public int getDFASize() {
        int n = 0;
        DFA[] decisionToDFA = this.atnSimulator.decisionToDFA;
        for (int i = 0; i < decisionToDFA.length; i++) {
            n += getDFASize(i);
        }
        return n;
    }

    public int getDFASize(int decision) {
        DFA decisionToDFA = this.atnSimulator.decisionToDFA[decision];
        return decisionToDFA.states.size();
    }
}
