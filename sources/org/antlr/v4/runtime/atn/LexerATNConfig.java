package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.misc.MurmurHash;
import org.antlr.v4.runtime.misc.ObjectEqualityComparator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LexerATNConfig extends ATNConfig {
    private final LexerActionExecutor lexerActionExecutor;
    private final boolean passedThroughNonGreedyDecision;

    public LexerATNConfig(ATNState state, int alt, PredictionContext context) {
        super(state, alt, context, SemanticContext.NONE);
        this.passedThroughNonGreedyDecision = false;
        this.lexerActionExecutor = null;
    }

    public LexerATNConfig(ATNState state, int alt, PredictionContext context, LexerActionExecutor lexerActionExecutor) {
        super(state, alt, context, SemanticContext.NONE);
        this.lexerActionExecutor = lexerActionExecutor;
        this.passedThroughNonGreedyDecision = false;
    }

    public LexerATNConfig(LexerATNConfig c, ATNState state) {
        super(c, state, c.context, c.semanticContext);
        this.lexerActionExecutor = c.lexerActionExecutor;
        this.passedThroughNonGreedyDecision = checkNonGreedyDecision(c, state);
    }

    public LexerATNConfig(LexerATNConfig c, ATNState state, LexerActionExecutor lexerActionExecutor) {
        super(c, state, c.context, c.semanticContext);
        this.lexerActionExecutor = lexerActionExecutor;
        this.passedThroughNonGreedyDecision = checkNonGreedyDecision(c, state);
    }

    public LexerATNConfig(LexerATNConfig c, ATNState state, PredictionContext context) {
        super(c, state, context, c.semanticContext);
        this.lexerActionExecutor = c.lexerActionExecutor;
        this.passedThroughNonGreedyDecision = checkNonGreedyDecision(c, state);
    }

    public final LexerActionExecutor getLexerActionExecutor() {
        return this.lexerActionExecutor;
    }

    public final boolean hasPassedThroughNonGreedyDecision() {
        return this.passedThroughNonGreedyDecision;
    }

    public int hashCode() {
        int hashCode = MurmurHash.initialize(7);
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(MurmurHash.update(MurmurHash.update(MurmurHash.update(MurmurHash.update(hashCode, this.state.stateNumber), this.alt), this.context), this.semanticContext), this.passedThroughNonGreedyDecision ? 1 : 0), this.lexerActionExecutor), 6);
    }

    public boolean equals(ATNConfig other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LexerATNConfig)) {
            return false;
        }
        LexerATNConfig lexerOther = (LexerATNConfig) other;
        if (this.passedThroughNonGreedyDecision == lexerOther.passedThroughNonGreedyDecision && ObjectEqualityComparator.INSTANCE.equals(this.lexerActionExecutor, lexerOther.lexerActionExecutor)) {
            return super.equals(other);
        }
        return false;
    }

    private static boolean checkNonGreedyDecision(LexerATNConfig source, ATNState target) {
        return source.passedThroughNonGreedyDecision || ((target instanceof DecisionState) && ((DecisionState) target).nonGreedy);
    }
}
