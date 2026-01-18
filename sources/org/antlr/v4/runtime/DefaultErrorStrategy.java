package org.antlr.v4.runtime;

import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.RuleTransition;
import org.antlr.v4.runtime.misc.IntSet;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class DefaultErrorStrategy implements ANTLRErrorStrategy {
    protected boolean errorRecoveryMode = false;
    protected int lastErrorIndex = -1;
    protected IntervalSet lastErrorStates;
    protected ParserRuleContext nextTokensContext;
    protected int nextTokensState;

    public void reset(Parser recognizer) {
        endErrorCondition(recognizer);
    }

    protected void beginErrorCondition(Parser recognizer) {
        this.errorRecoveryMode = true;
    }

    public boolean inErrorRecoveryMode(Parser recognizer) {
        return this.errorRecoveryMode;
    }

    protected void endErrorCondition(Parser recognizer) {
        this.errorRecoveryMode = false;
        this.lastErrorStates = null;
        this.lastErrorIndex = -1;
    }

    public void reportMatch(Parser recognizer) {
        endErrorCondition(recognizer);
    }

    public void reportError(Parser recognizer, RecognitionException e) {
        if (!inErrorRecoveryMode(recognizer)) {
            beginErrorCondition(recognizer);
            if (e instanceof NoViableAltException) {
                reportNoViableAlternative(recognizer, (NoViableAltException) e);
                return;
            }
            if (e instanceof InputMismatchException) {
                reportInputMismatch(recognizer, (InputMismatchException) e);
            } else if (e instanceof FailedPredicateException) {
                reportFailedPredicate(recognizer, (FailedPredicateException) e);
            } else {
                System.err.println("unknown recognition error type: " + e.getClass().getName());
                recognizer.notifyErrorListeners(e.getOffendingToken(), e.getMessage(), e);
            }
        }
    }

    public void recover(Parser recognizer, RecognitionException e) {
        if (this.lastErrorIndex == recognizer.getInputStream().index() && this.lastErrorStates != null && this.lastErrorStates.contains(recognizer.getState())) {
            recognizer.consume();
        }
        this.lastErrorIndex = recognizer.getInputStream().index();
        if (this.lastErrorStates == null) {
            this.lastErrorStates = new IntervalSet(new int[0]);
        }
        this.lastErrorStates.add(recognizer.getState());
        IntervalSet followSet = getErrorRecoverySet(recognizer);
        consumeUntil(recognizer, followSet);
    }

    public void sync(Parser recognizer) throws RecognitionException {
        ATNState s = (ATNState) ((ParserATNSimulator) recognizer.getInterpreter()).atn.states.get(recognizer.getState());
        if (!inErrorRecoveryMode(recognizer)) {
            TokenStream tokens = recognizer.getInputStream();
            int la = tokens.LA(1);
            IntervalSet nextTokens = recognizer.getATN().nextTokens(s);
            if (nextTokens.contains(la)) {
                this.nextTokensContext = null;
                this.nextTokensState = -1;
                return;
            }
            if (nextTokens.contains(-2)) {
                if (this.nextTokensContext == null) {
                    this.nextTokensContext = recognizer.getContext();
                    this.nextTokensState = recognizer.getState();
                    return;
                }
                return;
            }
            switch (s.getStateType()) {
                case 3:
                case 4:
                case 5:
                case 10:
                    if (singleTokenDeletion(recognizer) == null) {
                        throw new InputMismatchException(recognizer);
                    }
                    return;
                case 6:
                case 7:
                case 8:
                default:
                    return;
                case 9:
                case 11:
                    reportUnwantedToken(recognizer);
                    IntervalSet expecting = recognizer.getExpectedTokens();
                    IntervalSet whatFollowsLoopIterationOrRule = expecting.or((IntSet) getErrorRecoverySet(recognizer));
                    consumeUntil(recognizer, whatFollowsLoopIterationOrRule);
                    return;
            }
        }
    }

    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        String input;
        TokenStream tokens = recognizer.getInputStream();
        if (tokens != null) {
            input = e.getStartToken().getType() == -1 ? "<EOF>" : tokens.getText(e.getStartToken(), e.getOffendingToken());
        } else {
            input = "<unknown input>";
        }
        String msg = "no viable alternative at input " + escapeWSAndQuote(input);
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        String msg = "mismatched input " + getTokenErrorDisplay(e.getOffendingToken()) + " expecting " + e.getExpectedTokens().toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

    protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
        String ruleName = recognizer.getRuleNames()[recognizer._ctx.getRuleIndex()];
        String msg = "rule " + ruleName + " " + e.getMessage();
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

    protected void reportUnwantedToken(Parser recognizer) {
        if (!inErrorRecoveryMode(recognizer)) {
            beginErrorCondition(recognizer);
            Token t = recognizer.getCurrentToken();
            String tokenName = getTokenErrorDisplay(t);
            IntervalSet expecting = getExpectedTokens(recognizer);
            String msg = "extraneous input " + tokenName + " expecting " + expecting.toString(recognizer.getVocabulary());
            recognizer.notifyErrorListeners(t, msg, null);
        }
    }

    protected void reportMissingToken(Parser recognizer) {
        if (!inErrorRecoveryMode(recognizer)) {
            beginErrorCondition(recognizer);
            Token t = recognizer.getCurrentToken();
            IntervalSet expecting = getExpectedTokens(recognizer);
            String msg = "missing " + expecting.toString(recognizer.getVocabulary()) + " at " + getTokenErrorDisplay(t);
            recognizer.notifyErrorListeners(t, msg, null);
        }
    }

    public Token recoverInline(Parser recognizer) throws RecognitionException {
        Token matchedSymbol = singleTokenDeletion(recognizer);
        if (matchedSymbol != null) {
            recognizer.consume();
            return matchedSymbol;
        }
        if (singleTokenInsertion(recognizer)) {
            return getMissingSymbol(recognizer);
        }
        if (this.nextTokensContext == null) {
            InputMismatchException e = new InputMismatchException(recognizer);
            throw e;
        }
        InputMismatchException e2 = new InputMismatchException(recognizer, this.nextTokensState, this.nextTokensContext);
        throw e2;
    }

    protected boolean singleTokenInsertion(Parser recognizer) {
        int currentSymbolType = recognizer.getInputStream().LA(1);
        ATNState currentState = (ATNState) ((ParserATNSimulator) recognizer.getInterpreter()).atn.states.get(recognizer.getState());
        ATNState next = currentState.transition(0).target;
        ATN atn = ((ParserATNSimulator) recognizer.getInterpreter()).atn;
        IntervalSet expectingAtLL2 = atn.nextTokens(next, recognizer._ctx);
        if (!expectingAtLL2.contains(currentSymbolType)) {
            return false;
        }
        reportMissingToken(recognizer);
        return true;
    }

    protected Token singleTokenDeletion(Parser recognizer) {
        int nextTokenType = recognizer.getInputStream().LA(2);
        IntervalSet expecting = getExpectedTokens(recognizer);
        if (!expecting.contains(nextTokenType)) {
            return null;
        }
        reportUnwantedToken(recognizer);
        recognizer.consume();
        Token matchedSymbol = recognizer.getCurrentToken();
        reportMatch(recognizer);
        return matchedSymbol;
    }

    protected Token getMissingSymbol(Parser recognizer) {
        Token currentSymbol = recognizer.getCurrentToken();
        IntervalSet expecting = getExpectedTokens(recognizer);
        int expectedTokenType = 0;
        if (!expecting.isNil()) {
            expectedTokenType = expecting.getMinElement();
        }
        String tokenText = expectedTokenType == -1 ? "<missing EOF>" : "<missing " + recognizer.getVocabulary().getDisplayName(expectedTokenType) + ">";
        Token current = currentSymbol;
        Token lookback = recognizer.getInputStream().LT(-1);
        if (current.getType() == -1 && lookback != null) {
            current = lookback;
        }
        return recognizer.getTokenFactory().create(new Pair(current.getTokenSource(), current.getTokenSource().getInputStream()), expectedTokenType, tokenText, 0, -1, -1, current.getLine(), current.getCharPositionInLine());
    }

    protected IntervalSet getExpectedTokens(Parser recognizer) {
        return recognizer.getExpectedTokens();
    }

    protected String getTokenErrorDisplay(Token t) {
        if (t == null) {
            return "<no token>";
        }
        String s = getSymbolText(t);
        if (s == null) {
            if (getSymbolType(t) == -1) {
                s = "<EOF>";
            } else {
                s = "<" + getSymbolType(t) + ">";
            }
        }
        return escapeWSAndQuote(s);
    }

    protected String getSymbolText(Token symbol) {
        return symbol.getText();
    }

    protected int getSymbolType(Token symbol) {
        return symbol.getType();
    }

    protected String escapeWSAndQuote(String s) {
        return "'" + s.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") + "'";
    }

    protected IntervalSet getErrorRecoverySet(Parser recognizer) {
        ATN atn = ((ParserATNSimulator) recognizer.getInterpreter()).atn;
        IntervalSet recoverSet = new IntervalSet(new int[0]);
        for (RuleContext ctx = recognizer._ctx; ctx != null && ctx.invokingState >= 0; ctx = ctx.parent) {
            ATNState invokingState = (ATNState) atn.states.get(ctx.invokingState);
            RuleTransition rt = (RuleTransition) invokingState.transition(0);
            IntervalSet follow = atn.nextTokens(rt.followState);
            recoverSet.addAll((IntSet) follow);
        }
        recoverSet.remove(-2);
        return recoverSet;
    }

    protected void consumeUntil(Parser recognizer, IntervalSet set) {
        int ttype = recognizer.getInputStream().LA(1);
        while (ttype != -1 && !set.contains(ttype)) {
            recognizer.consume();
            ttype = recognizer.getInputStream().LA(1);
        }
    }
}
