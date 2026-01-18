package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class CommonTokenStream extends BufferedTokenStream {
    protected int channel;

    public CommonTokenStream(TokenSource tokenSource) {
        super(tokenSource);
        this.channel = 0;
    }

    public CommonTokenStream(TokenSource tokenSource, int channel) {
        this(tokenSource);
        this.channel = channel;
    }

    protected int adjustSeekIndex(int i) {
        return nextTokenOnChannel(i, this.channel);
    }

    protected Token LB(int k) {
        if (k == 0 || this.p - k < 0) {
            return null;
        }
        int i = this.p;
        for (int n = 1; n <= k && i > 0; n++) {
            i = previousTokenOnChannel(i - 1, this.channel);
        }
        if (i >= 0) {
            return (Token) this.tokens.get(i);
        }
        return null;
    }

    public Token LT(int k) {
        lazyInit();
        if (k == 0) {
            return null;
        }
        if (k < 0) {
            return LB(-k);
        }
        int i = this.p;
        for (int n = 1; n < k; n++) {
            if (sync(i + 1)) {
                i = nextTokenOnChannel(i + 1, this.channel);
            }
        }
        return (Token) this.tokens.get(i);
    }

    public int getNumberOfOnChannelTokens() {
        int n = 0;
        fill();
        for (int i = 0; i < this.tokens.size(); i++) {
            Token t = (Token) this.tokens.get(i);
            if (t.getChannel() == this.channel) {
                n++;
            }
            if (t.getType() == -1) {
                break;
            }
        }
        return n;
    }
}
