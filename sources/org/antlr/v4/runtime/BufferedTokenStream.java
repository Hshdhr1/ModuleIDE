package org.antlr.v4.runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class BufferedTokenStream implements TokenStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected boolean fetchedEOF;
    protected TokenSource tokenSource;
    protected List tokens = new ArrayList(100);
    protected int p = -1;

    static {
        $assertionsDisabled = !BufferedTokenStream.class.desiredAssertionStatus();
    }

    public BufferedTokenStream(TokenSource tokenSource) {
        if (tokenSource == null) {
            throw new NullPointerException("tokenSource cannot be null");
        }
        this.tokenSource = tokenSource;
    }

    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    public int index() {
        return this.p;
    }

    public int mark() {
        return 0;
    }

    public void release(int marker) {
    }

    @Deprecated
    public void reset() {
        seek(0);
    }

    public void seek(int index) {
        lazyInit();
        this.p = adjustSeekIndex(index);
    }

    public int size() {
        return this.tokens.size();
    }

    public void consume() {
        boolean skipEofCheck = false;
        if (this.p >= 0) {
            if (this.fetchedEOF) {
                if (this.p < this.tokens.size() - 1) {
                    skipEofCheck = true;
                }
            } else if (this.p < this.tokens.size()) {
                skipEofCheck = true;
            }
        } else {
            skipEofCheck = false;
        }
        if (!skipEofCheck && LA(1) == -1) {
            throw new IllegalStateException("cannot consume EOF");
        }
        if (sync(this.p + 1)) {
            this.p = adjustSeekIndex(this.p + 1);
        }
    }

    protected boolean sync(int i) {
        if (!$assertionsDisabled && i < 0) {
            throw new AssertionError();
        }
        int n = (i - this.tokens.size()) + 1;
        if (n <= 0) {
            return true;
        }
        int fetched = fetch(n);
        return fetched >= n;
    }

    protected int fetch(int n) {
        if (this.fetchedEOF) {
            return 0;
        }
        for (int i = 0; i < n; i++) {
            Token t = this.tokenSource.nextToken();
            if (t instanceof WritableToken) {
                ((WritableToken) t).setTokenIndex(this.tokens.size());
            }
            this.tokens.add(t);
            if (t.getType() == -1) {
                this.fetchedEOF = true;
                return i + 1;
            }
        }
        return n;
    }

    public Token get(int i) {
        if (i < 0 || i >= this.tokens.size()) {
            throw new IndexOutOfBoundsException("token index " + i + " out of range 0.." + (this.tokens.size() - 1));
        }
        return (Token) this.tokens.get(i);
    }

    public List get(int start, int stop) {
        if (start < 0 || stop < 0) {
            return null;
        }
        lazyInit();
        ArrayList arrayList = new ArrayList();
        if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
        }
        for (int i = start; i <= stop; i++) {
            Token t = (Token) this.tokens.get(i);
            if (t.getType() != -1) {
                arrayList.add(t);
            } else {
                return arrayList;
            }
        }
        return arrayList;
    }

    public int LA(int i) {
        return LT(i).getType();
    }

    protected Token LB(int k) {
        if (this.p - k < 0) {
            return null;
        }
        return (Token) this.tokens.get(this.p - k);
    }

    public Token LT(int k) {
        lazyInit();
        if (k == 0) {
            return null;
        }
        if (k < 0) {
            return LB(-k);
        }
        int i = (this.p + k) - 1;
        sync(i);
        if (i >= this.tokens.size()) {
            return (Token) this.tokens.get(this.tokens.size() - 1);
        }
        return (Token) this.tokens.get(i);
    }

    protected int adjustSeekIndex(int i) {
        return i;
    }

    protected final void lazyInit() {
        if (this.p == -1) {
            setup();
        }
    }

    protected void setup() {
        sync(0);
        this.p = adjustSeekIndex(0);
    }

    public void setTokenSource(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
        this.tokens.clear();
        this.p = -1;
        this.fetchedEOF = false;
    }

    public List getTokens() {
        return this.tokens;
    }

    public List getTokens(int start, int stop) {
        return getTokens(start, stop, (Set) null);
    }

    public List getTokens(int start, int stop, Set set) {
        lazyInit();
        if (start < 0 || stop >= this.tokens.size() || stop < 0 || start >= this.tokens.size()) {
            throw new IndexOutOfBoundsException("start " + start + " or stop " + stop + " not in 0.." + (this.tokens.size() - 1));
        }
        if (start > stop) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = start; i <= stop; i++) {
            Token t = (Token) this.tokens.get(i);
            if (set == null || set.contains(Integer.valueOf(t.getType()))) {
                arrayList.add(t);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public List getTokens(int start, int stop, int ttype) {
        HashSet<Integer> s = new HashSet<>(ttype);
        s.add(Integer.valueOf(ttype));
        return getTokens(start, stop, (Set) s);
    }

    protected int nextTokenOnChannel(int i, int channel) {
        sync(i);
        if (i >= size()) {
            return size() - 1;
        }
        Object obj = this.tokens.get(i);
        while (true) {
            Token token = (Token) obj;
            if (token.getChannel() != channel && token.getType() != -1) {
                i++;
                sync(i);
                obj = this.tokens.get(i);
            }
            return i;
        }
    }

    protected int previousTokenOnChannel(int i, int channel) {
        sync(i);
        if (i >= size()) {
            return size() - 1;
        }
        int i2 = i;
        while (i2 >= 0) {
            Token token = (Token) this.tokens.get(i2);
            if (token.getType() == -1 || token.getChannel() == channel) {
                return i2;
            }
            i2--;
        }
        return i2;
    }

    public List getHiddenTokensToRight(int tokenIndex, int channel) {
        lazyInit();
        if (tokenIndex < 0 || tokenIndex >= this.tokens.size()) {
            throw new IndexOutOfBoundsException(tokenIndex + " not in 0.." + (this.tokens.size() - 1));
        }
        int nextOnChannel = nextTokenOnChannel(tokenIndex + 1, 0);
        int from = tokenIndex + 1;
        int to = nextOnChannel == -1 ? size() - 1 : nextOnChannel;
        return filterForChannel(from, to, channel);
    }

    public List getHiddenTokensToRight(int tokenIndex) {
        return getHiddenTokensToRight(tokenIndex, -1);
    }

    public List getHiddenTokensToLeft(int tokenIndex, int channel) {
        int prevOnChannel;
        lazyInit();
        if (tokenIndex < 0 || tokenIndex >= this.tokens.size()) {
            throw new IndexOutOfBoundsException(tokenIndex + " not in 0.." + (this.tokens.size() - 1));
        }
        if (tokenIndex == 0 || (prevOnChannel = previousTokenOnChannel(tokenIndex - 1, 0)) == tokenIndex - 1) {
            return null;
        }
        int from = prevOnChannel + 1;
        int to = tokenIndex - 1;
        return filterForChannel(from, to, channel);
    }

    public List getHiddenTokensToLeft(int tokenIndex) {
        return getHiddenTokensToLeft(tokenIndex, -1);
    }

    protected List filterForChannel(int from, int to, int channel) {
        ArrayList arrayList = new ArrayList();
        for (int i = from; i <= to; i++) {
            Token t = (Token) this.tokens.get(i);
            if (channel == -1) {
                if (t.getChannel() != 0) {
                    arrayList.add(t);
                }
            } else if (t.getChannel() == channel) {
                arrayList.add(t);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public String getSourceName() {
        return this.tokenSource.getSourceName();
    }

    public String getText() {
        return getText(Interval.of(0, size() - 1));
    }

    public String getText(Interval interval) {
        int start = interval.a;
        int stop = interval.b;
        if (start < 0 || stop < 0) {
            return "";
        }
        fill();
        if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = start; i <= stop; i++) {
            Token t = (Token) this.tokens.get(i);
            if (t.getType() == -1) {
                break;
            }
            buf.append(t.getText());
        }
        return buf.toString();
    }

    public String getText(RuleContext ctx) {
        return getText(ctx.getSourceInterval());
    }

    public String getText(Token start, Token stop) {
        return (start == null || stop == null) ? "" : getText(Interval.of(start.getTokenIndex(), stop.getTokenIndex()));
    }

    public void fill() {
        int fetched;
        lazyInit();
        do {
            fetched = fetch(1000);
        } while (fetched >= 1000);
    }
}
