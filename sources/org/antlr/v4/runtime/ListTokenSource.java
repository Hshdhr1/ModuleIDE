package org.antlr.v4.runtime;

import java.util.List;
import org.antlr.v4.runtime.misc.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ListTokenSource implements TokenSource {
    private TokenFactory _factory;
    protected Token eofToken;
    protected int i;
    private final String sourceName;
    protected final List tokens;

    public ListTokenSource(List list) {
        this(list, null);
    }

    public ListTokenSource(List list, String sourceName) {
        this._factory = CommonTokenFactory.DEFAULT;
        if (list == null) {
            throw new NullPointerException("tokens cannot be null");
        }
        this.tokens = list;
        this.sourceName = sourceName;
    }

    public int getCharPositionInLine() {
        int lastNewLine;
        if (this.i < this.tokens.size()) {
            return ((Token) this.tokens.get(this.i)).getCharPositionInLine();
        }
        if (this.eofToken != null) {
            return this.eofToken.getCharPositionInLine();
        }
        if (this.tokens.size() > 0) {
            Token lastToken = (Token) this.tokens.get(this.tokens.size() - 1);
            String tokenText = lastToken.getText();
            if (tokenText != null && (lastNewLine = tokenText.lastIndexOf(10)) >= 0) {
                return (tokenText.length() - lastNewLine) - 1;
            }
            return ((lastToken.getCharPositionInLine() + lastToken.getStopIndex()) - lastToken.getStartIndex()) + 1;
        }
        return 0;
    }

    public Token nextToken() {
        int previousStop;
        if (this.i >= this.tokens.size()) {
            if (this.eofToken == null) {
                int start = -1;
                if (this.tokens.size() > 0 && (previousStop = ((Token) this.tokens.get(this.tokens.size() - 1)).getStopIndex()) != -1) {
                    start = previousStop + 1;
                }
                int stop = Math.max(-1, start - 1);
                this.eofToken = this._factory.create(new Pair(this, getInputStream()), -1, "EOF", 0, start, stop, getLine(), getCharPositionInLine());
            }
            return this.eofToken;
        }
        Token t = (Token) this.tokens.get(this.i);
        if (this.i == this.tokens.size() - 1 && t.getType() == -1) {
            this.eofToken = t;
        }
        this.i++;
        return t;
    }

    public int getLine() {
        if (this.i < this.tokens.size()) {
            return ((Token) this.tokens.get(this.i)).getLine();
        }
        if (this.eofToken != null) {
            return this.eofToken.getLine();
        }
        if (this.tokens.size() > 0) {
            Token lastToken = (Token) this.tokens.get(this.tokens.size() - 1);
            int line = lastToken.getLine();
            String tokenText = lastToken.getText();
            if (tokenText != null) {
                for (int i = 0; i < tokenText.length(); i++) {
                    if (tokenText.charAt(i) == '\n') {
                        line++;
                    }
                }
                return line;
            }
            return line;
        }
        return 1;
    }

    public CharStream getInputStream() {
        if (this.i < this.tokens.size()) {
            return ((Token) this.tokens.get(this.i)).getInputStream();
        }
        if (this.eofToken != null) {
            return this.eofToken.getInputStream();
        }
        if (this.tokens.size() > 0) {
            return ((Token) this.tokens.get(this.tokens.size() - 1)).getInputStream();
        }
        return null;
    }

    public String getSourceName() {
        if (this.sourceName != null) {
            return this.sourceName;
        }
        CharStream inputStream = getInputStream();
        if (inputStream != null) {
            return inputStream.getSourceName();
        }
        return "List";
    }

    public void setTokenFactory(TokenFactory tokenFactory) {
        this._factory = tokenFactory;
    }

    public TokenFactory getTokenFactory() {
        return this._factory;
    }
}
