package org.antlr.v4.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.atn.ParseInfo;
import org.antlr.v4.runtime.misc.Utils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class Recognizer {
    public static final int EOF = -1;
    protected ATNSimulator _interp;
    private List _listeners = new 1();
    private int _stateNumber = -1;
    private static final Map tokenTypeMapCache = new WeakHashMap();
    private static final Map ruleIndexMapCache = new WeakHashMap();

    public abstract ATN getATN();

    public abstract String getGrammarFileName();

    public abstract IntStream getInputStream();

    public abstract String[] getRuleNames();

    public abstract TokenFactory getTokenFactory();

    @Deprecated
    public abstract String[] getTokenNames();

    public abstract void setInputStream(IntStream intStream);

    public abstract void setTokenFactory(TokenFactory tokenFactory);

    class 1 extends CopyOnWriteArrayList {
        1() {
            add(ConsoleErrorListener.INSTANCE);
        }
    }

    public Vocabulary getVocabulary() {
        return VocabularyImpl.fromTokenNames(getTokenNames());
    }

    public Map getTokenTypeMap() {
        Map<String, Integer> result;
        Vocabulary vocabulary = getVocabulary();
        synchronized (tokenTypeMapCache) {
            result = (Map) tokenTypeMapCache.get(vocabulary);
            if (result == null) {
                HashMap hashMap = new HashMap();
                for (int i = 0; i <= getATN().maxTokenType; i++) {
                    String literalName = vocabulary.getLiteralName(i);
                    if (literalName != null) {
                        hashMap.put(literalName, Integer.valueOf(i));
                    }
                    String symbolicName = vocabulary.getSymbolicName(i);
                    if (symbolicName != null) {
                        hashMap.put(symbolicName, Integer.valueOf(i));
                    }
                }
                hashMap.put("EOF", -1);
                result = Collections.unmodifiableMap(hashMap);
                tokenTypeMapCache.put(vocabulary, result);
            }
        }
        return result;
    }

    public Map getRuleIndexMap() {
        Map<String, Integer> result;
        String[] ruleNames = getRuleNames();
        if (ruleNames == null) {
            throw new UnsupportedOperationException("The current recognizer does not provide a list of rule names.");
        }
        synchronized (ruleIndexMapCache) {
            result = (Map) ruleIndexMapCache.get(ruleNames);
            if (result == null) {
                result = Collections.unmodifiableMap(Utils.toMap(ruleNames));
                ruleIndexMapCache.put(ruleNames, result);
            }
        }
        return result;
    }

    public int getTokenType(String tokenName) {
        Integer ttype = (Integer) getTokenTypeMap().get(tokenName);
        if (ttype != null) {
            return ttype.intValue();
        }
        return 0;
    }

    public String getSerializedATN() {
        throw new UnsupportedOperationException("there is no serialized ATN");
    }

    public ATNSimulator getInterpreter() {
        return this._interp;
    }

    public ParseInfo getParseInfo() {
        return null;
    }

    public void setInterpreter(ATNSimulator aTNSimulator) {
        this._interp = aTNSimulator;
    }

    public String getErrorHeader(RecognitionException e) {
        int line = e.getOffendingToken().getLine();
        int charPositionInLine = e.getOffendingToken().getCharPositionInLine();
        return "line " + line + ":" + charPositionInLine;
    }

    @Deprecated
    public String getTokenErrorDisplay(Token t) {
        if (t == null) {
            return "<no token>";
        }
        String s = t.getText();
        if (s == null) {
            if (t.getType() == -1) {
                s = "<EOF>";
            } else {
                s = "<" + t.getType() + ">";
            }
        }
        return "'" + s.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") + "'";
    }

    public void addErrorListener(ANTLRErrorListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener cannot be null.");
        }
        this._listeners.add(listener);
    }

    public void removeErrorListener(ANTLRErrorListener listener) {
        this._listeners.remove(listener);
    }

    public void removeErrorListeners() {
        this._listeners.clear();
    }

    public List getErrorListeners() {
        return this._listeners;
    }

    public ANTLRErrorListener getErrorListenerDispatch() {
        return new ProxyErrorListener(getErrorListeners());
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int actionIndex) {
        return true;
    }

    public boolean precpred(RuleContext localctx, int precedence) {
        return true;
    }

    public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
    }

    public final int getState() {
        return this._stateNumber;
    }

    public final void setState(int atnState) {
        this._stateNumber = atnState;
    }
}
