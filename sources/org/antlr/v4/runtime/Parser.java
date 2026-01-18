package org.antlr.v4.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializationOptions;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.atn.ParseInfo;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.atn.ProfilingATNSimulator;
import org.antlr.v4.runtime.atn.RuleTransition;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.IntegerStack;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.pattern.ParseTreePatternMatcher;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class Parser extends Recognizer {
    private static final Map bypassAltsAtnCache = new WeakHashMap();
    protected boolean _buildParseTrees;
    protected ParserRuleContext _ctx;
    protected TokenStream _input;
    protected List _parseListeners;
    protected int _syntaxErrors;
    private TraceListener _tracer;
    protected boolean matchedEOF;
    protected ANTLRErrorStrategy _errHandler = new DefaultErrorStrategy();
    protected final IntegerStack _precedenceStack = new IntegerStack();

    public class TraceListener implements ParseTreeListener {
        public TraceListener() {
        }

        public void enterEveryRule(ParserRuleContext ctx) {
            System.out.println("enter   " + Parser.this.getRuleNames()[ctx.getRuleIndex()] + ", LT(1)=" + Parser.this._input.LT(1).getText());
        }

        public void visitTerminal(TerminalNode node) {
            System.out.println("consume " + node.getSymbol() + " rule " + Parser.this.getRuleNames()[Parser.this._ctx.getRuleIndex()]);
        }

        public void visitErrorNode(ErrorNode node) {
        }

        public void exitEveryRule(ParserRuleContext ctx) {
            System.out.println("exit    " + Parser.this.getRuleNames()[ctx.getRuleIndex()] + ", LT(1)=" + Parser.this._input.LT(1).getText());
        }
    }

    public static class TrimToSizeListener implements ParseTreeListener {
        public static final TrimToSizeListener INSTANCE = new TrimToSizeListener();

        public void enterEveryRule(ParserRuleContext ctx) {
        }

        public void visitTerminal(TerminalNode node) {
        }

        public void visitErrorNode(ErrorNode node) {
        }

        public void exitEveryRule(ParserRuleContext ctx) {
            if (ctx.children instanceof ArrayList) {
                ctx.children.trimToSize();
            }
        }
    }

    public Parser(TokenStream input) {
        this._precedenceStack.push(0);
        this._buildParseTrees = true;
        setInputStream(input);
    }

    public void reset() {
        if (getInputStream() != null) {
            getInputStream().seek(0);
        }
        this._errHandler.reset(this);
        this._ctx = null;
        this._syntaxErrors = 0;
        this.matchedEOF = false;
        setTrace(false);
        this._precedenceStack.clear();
        this._precedenceStack.push(0);
        ATNSimulator interpreter = getInterpreter();
        if (interpreter != null) {
            interpreter.reset();
        }
    }

    public Token match(int ttype) throws RecognitionException {
        Token t = getCurrentToken();
        if (t.getType() == ttype) {
            if (ttype == -1) {
                this.matchedEOF = true;
            }
            this._errHandler.reportMatch(this);
            consume();
        } else {
            t = this._errHandler.recoverInline(this);
            if (this._buildParseTrees && t.getTokenIndex() == -1) {
                this._ctx.addErrorNode(createErrorNode(this._ctx, t));
            }
        }
        return t;
    }

    public Token matchWildcard() throws RecognitionException {
        Token t = getCurrentToken();
        if (t.getType() > 0) {
            this._errHandler.reportMatch(this);
            consume();
        } else {
            t = this._errHandler.recoverInline(this);
            if (this._buildParseTrees && t.getTokenIndex() == -1) {
                this._ctx.addErrorNode(createErrorNode(this._ctx, t));
            }
        }
        return t;
    }

    public void setBuildParseTree(boolean buildParseTrees) {
        this._buildParseTrees = buildParseTrees;
    }

    public boolean getBuildParseTree() {
        return this._buildParseTrees;
    }

    public void setTrimParseTree(boolean trimParseTrees) {
        if (trimParseTrees) {
            if (!getTrimParseTree()) {
                addParseListener(TrimToSizeListener.INSTANCE);
                return;
            }
            return;
        }
        removeParseListener(TrimToSizeListener.INSTANCE);
    }

    public boolean getTrimParseTree() {
        return getParseListeners().contains(TrimToSizeListener.INSTANCE);
    }

    public List getParseListeners() {
        List<ParseTreeListener> listeners = this._parseListeners;
        if (listeners == null) {
            return Collections.emptyList();
        }
        return listeners;
    }

    public void addParseListener(ParseTreeListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        if (this._parseListeners == null) {
            this._parseListeners = new ArrayList();
        }
        this._parseListeners.add(listener);
    }

    public void removeParseListener(ParseTreeListener listener) {
        if (this._parseListeners != null && this._parseListeners.remove(listener) && this._parseListeners.isEmpty()) {
            this._parseListeners = null;
        }
    }

    public void removeParseListeners() {
        this._parseListeners = null;
    }

    protected void triggerEnterRuleEvent() {
        for (ParseTreeListener listener : this._parseListeners) {
            listener.enterEveryRule(this._ctx);
            this._ctx.enterRule(listener);
        }
    }

    protected void triggerExitRuleEvent() {
        for (int i = this._parseListeners.size() - 1; i >= 0; i--) {
            ParseTreeListener listener = (ParseTreeListener) this._parseListeners.get(i);
            this._ctx.exitRule(listener);
            listener.exitEveryRule(this._ctx);
        }
    }

    public int getNumberOfSyntaxErrors() {
        return this._syntaxErrors;
    }

    public TokenFactory getTokenFactory() {
        return this._input.getTokenSource().getTokenFactory();
    }

    public void setTokenFactory(TokenFactory tokenFactory) {
        this._input.getTokenSource().setTokenFactory(tokenFactory);
    }

    public ATN getATNWithBypassAlts() {
        ATN result;
        String serializedAtn = getSerializedATN();
        if (serializedAtn == null) {
            throw new UnsupportedOperationException("The current parser does not support an ATN with bypass alternatives.");
        }
        synchronized (bypassAltsAtnCache) {
            result = (ATN) bypassAltsAtnCache.get(serializedAtn);
            if (result == null) {
                ATNDeserializationOptions deserializationOptions = new ATNDeserializationOptions();
                deserializationOptions.setGenerateRuleBypassTransitions(true);
                result = new ATNDeserializer(deserializationOptions).deserialize(serializedAtn.toCharArray());
                bypassAltsAtnCache.put(serializedAtn, result);
            }
        }
        return result;
    }

    public ParseTreePattern compileParseTreePattern(String pattern, int patternRuleIndex) {
        if (getTokenStream() != null) {
            TokenSource tokenSource = getTokenStream().getTokenSource();
            if (tokenSource instanceof Lexer) {
                Lexer lexer = (Lexer) tokenSource;
                return compileParseTreePattern(pattern, patternRuleIndex, lexer);
            }
        }
        throw new UnsupportedOperationException("Parser can't discover a lexer to use");
    }

    public ParseTreePattern compileParseTreePattern(String pattern, int patternRuleIndex, Lexer lexer) {
        ParseTreePatternMatcher m = new ParseTreePatternMatcher(lexer, this);
        return m.compile(pattern, patternRuleIndex);
    }

    public ANTLRErrorStrategy getErrorHandler() {
        return this._errHandler;
    }

    public void setErrorHandler(ANTLRErrorStrategy handler) {
        this._errHandler = handler;
    }

    public TokenStream getInputStream() {
        return getTokenStream();
    }

    public final void setInputStream(IntStream input) {
        setTokenStream((TokenStream) input);
    }

    public TokenStream getTokenStream() {
        return this._input;
    }

    public void setTokenStream(TokenStream input) {
        this._input = null;
        reset();
        this._input = input;
    }

    public Token getCurrentToken() {
        return this._input.LT(1);
    }

    public final void notifyErrorListeners(String msg) {
        notifyErrorListeners(getCurrentToken(), msg, null);
    }

    public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException e) {
        this._syntaxErrors++;
        int line = offendingToken.getLine();
        int charPositionInLine = offendingToken.getCharPositionInLine();
        ANTLRErrorListener listener = getErrorListenerDispatch();
        listener.syntaxError(this, offendingToken, line, charPositionInLine, msg, e);
    }

    public Token consume() {
        Token o = getCurrentToken();
        if (o.getType() != -1) {
            getInputStream().consume();
        }
        boolean hasListener = (this._parseListeners == null || this._parseListeners.isEmpty()) ? false : true;
        if (this._buildParseTrees || hasListener) {
            if (this._errHandler.inErrorRecoveryMode(this)) {
                ErrorNode node = this._ctx.addErrorNode(createErrorNode(this._ctx, o));
                if (this._parseListeners != null) {
                    for (ParseTreeListener listener : this._parseListeners) {
                        listener.visitErrorNode(node);
                    }
                }
            } else {
                TerminalNode node2 = this._ctx.addChild(createTerminalNode(this._ctx, o));
                if (this._parseListeners != null) {
                    for (ParseTreeListener listener2 : this._parseListeners) {
                        listener2.visitTerminal(node2);
                    }
                }
            }
        }
        return o;
    }

    public TerminalNode createTerminalNode(ParserRuleContext parent, Token t) {
        return new TerminalNodeImpl(t);
    }

    public ErrorNode createErrorNode(ParserRuleContext parent, Token t) {
        return new ErrorNodeImpl(t);
    }

    protected void addContextToParseTree() {
        ParserRuleContext parent = (ParserRuleContext) this._ctx.parent;
        if (parent != null) {
            parent.addChild(this._ctx);
        }
    }

    public void enterRule(ParserRuleContext localctx, int state, int ruleIndex) {
        setState(state);
        this._ctx = localctx;
        this._ctx.start = this._input.LT(1);
        if (this._buildParseTrees) {
            addContextToParseTree();
        }
        if (this._parseListeners != null) {
            triggerEnterRuleEvent();
        }
    }

    public void exitRule() {
        if (this.matchedEOF) {
            this._ctx.stop = this._input.LT(1);
        } else {
            this._ctx.stop = this._input.LT(-1);
        }
        if (this._parseListeners != null) {
            triggerExitRuleEvent();
        }
        setState(this._ctx.invokingState);
        this._ctx = (ParserRuleContext) this._ctx.parent;
    }

    public void enterOuterAlt(ParserRuleContext localctx, int altNum) {
        ParserRuleContext parent;
        localctx.setAltNumber(altNum);
        if (this._buildParseTrees && this._ctx != localctx && (parent = (ParserRuleContext) this._ctx.parent) != null) {
            parent.removeLastChild();
            parent.addChild(localctx);
        }
        this._ctx = localctx;
    }

    public final int getPrecedence() {
        if (this._precedenceStack.isEmpty()) {
            return -1;
        }
        return this._precedenceStack.peek();
    }

    @Deprecated
    public void enterRecursionRule(ParserRuleContext localctx, int ruleIndex) {
        enterRecursionRule(localctx, getATN().ruleToStartState[ruleIndex].stateNumber, ruleIndex, 0);
    }

    public void enterRecursionRule(ParserRuleContext localctx, int state, int ruleIndex, int precedence) {
        setState(state);
        this._precedenceStack.push(precedence);
        this._ctx = localctx;
        this._ctx.start = this._input.LT(1);
        if (this._parseListeners != null) {
            triggerEnterRuleEvent();
        }
    }

    public void pushNewRecursionContext(ParserRuleContext localctx, int state, int ruleIndex) {
        ParserRuleContext previous = this._ctx;
        previous.parent = localctx;
        previous.invokingState = state;
        previous.stop = this._input.LT(-1);
        this._ctx = localctx;
        this._ctx.start = previous.start;
        if (this._buildParseTrees) {
            this._ctx.addChild(previous);
        }
        if (this._parseListeners != null) {
            triggerEnterRuleEvent();
        }
    }

    public void unrollRecursionContexts(ParserRuleContext _parentctx) {
        this._precedenceStack.pop();
        this._ctx.stop = this._input.LT(-1);
        ParserRuleContext retctx = this._ctx;
        if (this._parseListeners != null) {
            while (this._ctx != _parentctx) {
                triggerExitRuleEvent();
                this._ctx = (ParserRuleContext) this._ctx.parent;
            }
        } else {
            this._ctx = _parentctx;
        }
        retctx.parent = _parentctx;
        if (this._buildParseTrees && _parentctx != null) {
            _parentctx.addChild(retctx);
        }
    }

    public ParserRuleContext getInvokingContext(int ruleIndex) {
        for (ParserRuleContext p = this._ctx; p != null; p = (ParserRuleContext) p.parent) {
            if (p.getRuleIndex() == ruleIndex) {
                return p;
            }
        }
        return null;
    }

    public ParserRuleContext getContext() {
        return this._ctx;
    }

    public void setContext(ParserRuleContext ctx) {
        this._ctx = ctx;
    }

    public boolean precpred(RuleContext localctx, int precedence) {
        return precedence >= this._precedenceStack.peek();
    }

    public boolean inContext(String context) {
        return false;
    }

    public boolean isExpectedToken(int symbol) {
        ATN atn = ((ParserATNSimulator) getInterpreter()).atn;
        ATNState s = (ATNState) atn.states.get(getState());
        IntervalSet following = atn.nextTokens(s);
        if (following.contains(symbol)) {
            return true;
        }
        if (!following.contains(-2)) {
            return false;
        }
        for (ParserRuleContext ctx = this._ctx; ctx != null && ctx.invokingState >= 0 && following.contains(-2); ctx = (ParserRuleContext) ctx.parent) {
            ATNState invokingState = (ATNState) atn.states.get(ctx.invokingState);
            RuleTransition rt = (RuleTransition) invokingState.transition(0);
            following = atn.nextTokens(rt.followState);
            if (following.contains(symbol)) {
                return true;
            }
        }
        return following.contains(-2) && symbol == -1;
    }

    public boolean isMatchedEOF() {
        return this.matchedEOF;
    }

    public IntervalSet getExpectedTokens() {
        return getATN().getExpectedTokens(getState(), getContext());
    }

    public IntervalSet getExpectedTokensWithinCurrentRule() {
        ATN atn = ((ParserATNSimulator) getInterpreter()).atn;
        ATNState s = (ATNState) atn.states.get(getState());
        return atn.nextTokens(s);
    }

    public int getRuleIndex(String ruleName) {
        Integer ruleIndex = (Integer) getRuleIndexMap().get(ruleName);
        if (ruleIndex != null) {
            return ruleIndex.intValue();
        }
        return -1;
    }

    public ParserRuleContext getRuleContext() {
        return this._ctx;
    }

    public List getRuleInvocationStack() {
        return getRuleInvocationStack(this._ctx);
    }

    public List getRuleInvocationStack(RuleContext p) {
        String[] ruleNames = getRuleNames();
        ArrayList arrayList = new ArrayList();
        while (p != null) {
            int ruleIndex = p.getRuleIndex();
            if (ruleIndex < 0) {
                arrayList.add("n/a");
            } else {
                arrayList.add(ruleNames[ruleIndex]);
            }
            p = p.parent;
        }
        return arrayList;
    }

    public List getDFAStrings() {
        ArrayList arrayList;
        synchronized (((ParserATNSimulator) this._interp).decisionToDFA) {
            arrayList = new ArrayList();
            for (int d = 0; d < ((ParserATNSimulator) this._interp).decisionToDFA.length; d++) {
                DFA dfa = ((ParserATNSimulator) this._interp).decisionToDFA[d];
                arrayList.add(dfa.toString(getVocabulary()));
            }
        }
        return arrayList;
    }

    public void dumpDFA() {
        synchronized (((ParserATNSimulator) this._interp).decisionToDFA) {
            boolean seenOne = false;
            for (int d = 0; d < ((ParserATNSimulator) this._interp).decisionToDFA.length; d++) {
                DFA dfa = ((ParserATNSimulator) this._interp).decisionToDFA[d];
                if (!dfa.states.isEmpty()) {
                    if (seenOne) {
                        System.out.println();
                    }
                    System.out.println("Decision " + dfa.decision + ":");
                    System.out.print(dfa.toString(getVocabulary()));
                    seenOne = true;
                }
            }
        }
    }

    public String getSourceName() {
        return this._input.getSourceName();
    }

    public ParseInfo getParseInfo() {
        ParserATNSimulator interp = (ParserATNSimulator) getInterpreter();
        if (interp instanceof ProfilingATNSimulator) {
            return new ParseInfo((ProfilingATNSimulator) interp);
        }
        return null;
    }

    public void setProfile(boolean profile) {
        ParserATNSimulator interp = (ParserATNSimulator) getInterpreter();
        PredictionMode saveMode = interp.getPredictionMode();
        if (profile) {
            if (!(interp instanceof ProfilingATNSimulator)) {
                setInterpreter(new ProfilingATNSimulator(this));
            }
        } else if (interp instanceof ProfilingATNSimulator) {
            ParserATNSimulator sim = new ParserATNSimulator(this, getATN(), interp.decisionToDFA, interp.getSharedContextCache());
            setInterpreter(sim);
        }
        ((ParserATNSimulator) getInterpreter()).setPredictionMode(saveMode);
    }

    public void setTrace(boolean trace) {
        if (!trace) {
            removeParseListener(this._tracer);
            this._tracer = null;
        } else {
            if (this._tracer != null) {
                removeParseListener(this._tracer);
            } else {
                this._tracer = new TraceListener();
            }
            addParseListener(this._tracer);
        }
    }

    public boolean isTrace() {
        return this._tracer != null;
    }
}
