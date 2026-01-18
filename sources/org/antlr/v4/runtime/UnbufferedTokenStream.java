package org.antlr.v4.runtime;

import java.util.Arrays;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class UnbufferedTokenStream implements TokenStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected int currentTokenIndex;
    protected Token lastToken;
    protected Token lastTokenBufferStart;
    protected int n;
    protected int numMarkers;
    protected int p;
    protected TokenSource tokenSource;
    protected Token[] tokens;

    static {
        $assertionsDisabled = !UnbufferedTokenStream.class.desiredAssertionStatus();
    }

    public UnbufferedTokenStream(TokenSource tokenSource) {
        this(tokenSource, 256);
    }

    public UnbufferedTokenStream(TokenSource tokenSource, int bufferSize) {
        this.p = 0;
        this.numMarkers = 0;
        this.currentTokenIndex = 0;
        this.tokenSource = tokenSource;
        this.tokens = new Token[bufferSize];
        this.n = 0;
        fill(1);
    }

    public Token get(int i) {
        int bufferStartIndex = getBufferStartIndex();
        if (i < bufferStartIndex || i >= this.n + bufferStartIndex) {
            throw new IndexOutOfBoundsException("get(" + i + ") outside buffer: " + bufferStartIndex + ".." + (this.n + bufferStartIndex));
        }
        return this.tokens[i - bufferStartIndex];
    }

    public Token LT(int i) {
        if (i == -1) {
            return this.lastToken;
        }
        sync(i);
        int index = (this.p + i) - 1;
        if (index < 0) {
            throw new IndexOutOfBoundsException("LT(" + i + ") gives negative index");
        }
        if (index >= this.n) {
            if ($assertionsDisabled || (this.n > 0 && this.tokens[this.n - 1].getType() == -1)) {
                return this.tokens[this.n - 1];
            }
            throw new AssertionError();
        }
        return this.tokens[index];
    }

    public int LA(int i) {
        return LT(i).getType();
    }

    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    public String getText() {
        return "";
    }

    public String getText(RuleContext ctx) {
        return getText(ctx.getSourceInterval());
    }

    public String getText(Token start, Token stop) {
        return getText(Interval.of(start.getTokenIndex(), stop.getTokenIndex()));
    }

    public void consume() {
        if (LA(1) == -1) {
            throw new IllegalStateException("cannot consume EOF");
        }
        this.lastToken = this.tokens[this.p];
        if (this.p == this.n - 1 && this.numMarkers == 0) {
            this.n = 0;
            this.p = -1;
            this.lastTokenBufferStart = this.lastToken;
        }
        this.p++;
        this.currentTokenIndex++;
        sync(1);
    }

    protected void sync(int want) {
        int need = (((this.p + want) - 1) - this.n) + 1;
        if (need > 0) {
            fill(need);
        }
    }

    protected int fill(int n) {
        for (int i = 0; i < n; i++) {
            if (this.n <= 0 || this.tokens[this.n - 1].getType() != -1) {
                Token t = this.tokenSource.nextToken();
                add(t);
            } else {
                return i;
            }
        }
        return n;
    }

    protected void add(Token t) {
        if (this.n >= this.tokens.length) {
            this.tokens = (Token[]) Arrays.copyOf(this.tokens, this.tokens.length * 2);
        }
        if (t instanceof WritableToken) {
            ((WritableToken) t).setTokenIndex(getBufferStartIndex() + this.n);
        }
        Token[] tokenArr = this.tokens;
        int i = this.n;
        this.n = i + 1;
        tokenArr[i] = t;
    }

    public int mark() {
        if (this.numMarkers == 0) {
            this.lastTokenBufferStart = this.lastToken;
        }
        int mark = (-this.numMarkers) - 1;
        this.numMarkers++;
        return mark;
    }

    public void release(int marker) {
        int expectedMark = -this.numMarkers;
        if (marker != expectedMark) {
            throw new IllegalStateException("release() called with an invalid marker.");
        }
        this.numMarkers--;
        if (this.numMarkers == 0) {
            if (this.p > 0) {
                System.arraycopy(this.tokens, this.p, this.tokens, 0, this.n - this.p);
                this.n -= this.p;
                this.p = 0;
            }
            this.lastTokenBufferStart = this.lastToken;
        }
    }

    public int index() {
        return this.currentTokenIndex;
    }

    public void seek(int index) {
        if (index != this.currentTokenIndex) {
            if (index > this.currentTokenIndex) {
                sync(index - this.currentTokenIndex);
                index = Math.min(index, (getBufferStartIndex() + this.n) - 1);
            }
            int bufferStartIndex = getBufferStartIndex();
            int i = index - bufferStartIndex;
            if (i < 0) {
                throw new IllegalArgumentException("cannot seek to negative index " + index);
            }
            if (i >= this.n) {
                throw new UnsupportedOperationException("seek to index outside buffer: " + index + " not in " + bufferStartIndex + ".." + (this.n + bufferStartIndex));
            }
            this.p = i;
            this.currentTokenIndex = index;
            if (this.p == 0) {
                this.lastToken = this.lastTokenBufferStart;
            } else {
                this.lastToken = this.tokens[this.p - 1];
            }
        }
    }

    public int size() {
        throw new UnsupportedOperationException("Unbuffered stream cannot know its size");
    }

    public String getSourceName() {
        return this.tokenSource.getSourceName();
    }

    public String getText(Interval interval) {
        int bufferStartIndex = getBufferStartIndex();
        int bufferStopIndex = (this.tokens.length + bufferStartIndex) - 1;
        int start = interval.a;
        int stop = interval.b;
        if (start < bufferStartIndex || stop > bufferStopIndex) {
            throw new UnsupportedOperationException("interval " + interval + " not in token buffer window: " + bufferStartIndex + ".." + bufferStopIndex);
        }
        int a = start - bufferStartIndex;
        int b = stop - bufferStartIndex;
        StringBuilder buf = new StringBuilder();
        for (int i = a; i <= b; i++) {
            Token t = this.tokens[i];
            buf.append(t.getText());
        }
        return buf.toString();
    }

    protected final int getBufferStartIndex() {
        return this.currentTokenIndex - this.p;
    }
}
