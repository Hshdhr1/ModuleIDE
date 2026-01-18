package org.antlr.v4.runtime;

import java.io.Serializable;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class CommonToken implements WritableToken, Serializable {
    protected static final Pair EMPTY_SOURCE = new Pair(null, null);
    protected int channel;
    protected int charPositionInLine;
    protected int index;
    protected int line;
    protected Pair source;
    protected int start;
    protected int stop;
    protected String text;
    protected int type;

    public CommonToken(int type) {
        this.charPositionInLine = -1;
        this.channel = 0;
        this.index = -1;
        this.type = type;
        this.source = EMPTY_SOURCE;
    }

    public CommonToken(Pair pair, int type, int channel, int start, int stop) {
        this.charPositionInLine = -1;
        this.channel = 0;
        this.index = -1;
        this.source = pair;
        this.type = type;
        this.channel = channel;
        this.start = start;
        this.stop = stop;
        if (pair.a != null) {
            this.line = ((TokenSource) pair.a).getLine();
            this.charPositionInLine = ((TokenSource) pair.a).getCharPositionInLine();
        }
    }

    public CommonToken(int type, String text) {
        this.charPositionInLine = -1;
        this.channel = 0;
        this.index = -1;
        this.type = type;
        this.channel = 0;
        this.text = text;
        this.source = EMPTY_SOURCE;
    }

    public CommonToken(Token oldToken) {
        this.charPositionInLine = -1;
        this.channel = 0;
        this.index = -1;
        this.type = oldToken.getType();
        this.line = oldToken.getLine();
        this.index = oldToken.getTokenIndex();
        this.charPositionInLine = oldToken.getCharPositionInLine();
        this.channel = oldToken.getChannel();
        this.start = oldToken.getStartIndex();
        this.stop = oldToken.getStopIndex();
        if (oldToken instanceof CommonToken) {
            this.text = ((CommonToken) oldToken).text;
            this.source = ((CommonToken) oldToken).source;
        } else {
            this.text = oldToken.getText();
            this.source = new Pair(oldToken.getTokenSource(), oldToken.getInputStream());
        }
    }

    public int getType() {
        return this.type;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getText() {
        if (this.text != null) {
            return this.text;
        }
        CharStream input = getInputStream();
        if (input == null) {
            return null;
        }
        int n = input.size();
        if (this.start < n && this.stop < n) {
            return input.getText(Interval.of(this.start, this.stop));
        }
        return "<EOF>";
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLine() {
        return this.line;
    }

    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    public void setCharPositionInLine(int charPositionInLine) {
        this.charPositionInLine = charPositionInLine;
    }

    public int getChannel() {
        return this.channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStartIndex() {
        return this.start;
    }

    public void setStartIndex(int start) {
        this.start = start;
    }

    public int getStopIndex() {
        return this.stop;
    }

    public void setStopIndex(int stop) {
        this.stop = stop;
    }

    public int getTokenIndex() {
        return this.index;
    }

    public void setTokenIndex(int index) {
        this.index = index;
    }

    public TokenSource getTokenSource() {
        return (TokenSource) this.source.a;
    }

    public CharStream getInputStream() {
        return (CharStream) this.source.b;
    }

    public String toString() {
        return toString(null);
    }

    public String toString(Recognizer r) {
        String txt;
        String channelStr = "";
        if (this.channel > 0) {
            channelStr = ",channel=" + this.channel;
        }
        String txt2 = getText();
        if (txt2 != null) {
            txt = txt2.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
        } else {
            txt = "<no text>";
        }
        String typeString = String.valueOf(this.type);
        if (r != null) {
            typeString = r.getVocabulary().getDisplayName(this.type);
        }
        return "[@" + getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + typeString + ">" + channelStr + "," + this.line + ":" + getCharPositionInLine() + "]";
    }
}
