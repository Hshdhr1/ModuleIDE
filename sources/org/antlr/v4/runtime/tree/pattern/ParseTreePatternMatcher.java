package org.antlr.v4.runtime.tree.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ListTokenSource;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParseTreePatternMatcher {
    private final Lexer lexer;
    private final Parser parser;
    protected String start = "<";
    protected String stop = ">";
    protected String escape = "\\";

    public static class StartRuleDoesNotConsumeFullPattern extends RuntimeException {
    }

    public static class CannotInvokeStartRule extends RuntimeException {
        public CannotInvokeStartRule(Throwable e) {
            super(e);
        }
    }

    public ParseTreePatternMatcher(Lexer lexer, Parser parser) {
        this.lexer = lexer;
        this.parser = parser;
    }

    public void setDelimiters(String start, String stop, String escapeLeft) {
        if (start == null || start.isEmpty()) {
            throw new IllegalArgumentException("start cannot be null or empty");
        }
        if (stop == null || stop.isEmpty()) {
            throw new IllegalArgumentException("stop cannot be null or empty");
        }
        this.start = start;
        this.stop = stop;
        this.escape = escapeLeft;
    }

    public boolean matches(ParseTree tree, String pattern, int patternRuleIndex) {
        ParseTreePattern p = compile(pattern, patternRuleIndex);
        return matches(tree, p);
    }

    public boolean matches(ParseTree tree, ParseTreePattern pattern) {
        MultiMap<String, ParseTree> labels = new MultiMap();
        ParseTree mismatchedNode = matchImpl(tree, pattern.getPatternTree(), labels);
        return mismatchedNode == null;
    }

    public ParseTreeMatch match(ParseTree tree, String pattern, int patternRuleIndex) {
        ParseTreePattern p = compile(pattern, patternRuleIndex);
        return match(tree, p);
    }

    public ParseTreeMatch match(ParseTree tree, ParseTreePattern pattern) {
        MultiMap<String, ParseTree> labels = new MultiMap();
        ParseTree mismatchedNode = matchImpl(tree, pattern.getPatternTree(), labels);
        return new ParseTreeMatch(tree, pattern, labels, mismatchedNode);
    }

    public ParseTreePattern compile(String pattern, int patternRuleIndex) {
        List<? extends Token> tokenList = tokenize(pattern);
        ListTokenSource tokenSrc = new ListTokenSource(tokenList);
        CommonTokenStream tokens = new CommonTokenStream(tokenSrc);
        ParserInterpreter parserInterp = new ParserInterpreter(this.parser.getGrammarFileName(), this.parser.getVocabulary(), (Collection) Arrays.asList(this.parser.getRuleNames()), this.parser.getATNWithBypassAlts(), (TokenStream) tokens);
        try {
            parserInterp.setErrorHandler(new BailErrorStrategy());
            ParseTree tree = parserInterp.parse(patternRuleIndex);
            if (tokens.LA(1) != -1) {
                throw new StartRuleDoesNotConsumeFullPattern();
            }
            return new ParseTreePattern(this, pattern, patternRuleIndex, tree);
        } catch (Exception e) {
            throw new CannotInvokeStartRule(e);
        } catch (RecognitionException re) {
            throw re;
        } catch (ParseCancellationException e2) {
            throw e2.getCause();
        }
    }

    public Lexer getLexer() {
        return this.lexer;
    }

    public Parser getParser() {
        return this.parser;
    }

    protected ParseTree matchImpl(ParseTree tree, ParseTree patternTree, MultiMap multiMap) {
        if (tree == null) {
            throw new IllegalArgumentException("tree cannot be null");
        }
        if (patternTree == null) {
            throw new IllegalArgumentException("patternTree cannot be null");
        }
        if ((tree instanceof TerminalNode) && (patternTree instanceof TerminalNode)) {
            TerminalNode t1 = (TerminalNode) tree;
            TerminalNode t2 = (TerminalNode) patternTree;
            if (t1.getSymbol().getType() == t2.getSymbol().getType()) {
                if (t2.getSymbol() instanceof TokenTagToken) {
                    TokenTagToken tokenTagToken = (TokenTagToken) t2.getSymbol();
                    multiMap.map(tokenTagToken.getTokenName(), tree);
                    if (tokenTagToken.getLabel() == null) {
                        return null;
                    }
                    multiMap.map(tokenTagToken.getLabel(), tree);
                    return null;
                }
                if (t1.getText().equals(t2.getText()) || 0 != 0) {
                    return null;
                }
                return t1;
            }
            if (0 != 0) {
                return null;
            }
            return t1;
        }
        if (!(tree instanceof ParserRuleContext) || !(patternTree instanceof ParserRuleContext)) {
            return tree;
        }
        ParserRuleContext r1 = (ParserRuleContext) tree;
        ParserRuleContext r2 = (ParserRuleContext) patternTree;
        RuleTagToken ruleTagToken = getRuleTagToken(r2);
        if (ruleTagToken != null) {
            if (r1.getRuleContext().getRuleIndex() == r2.getRuleContext().getRuleIndex()) {
                multiMap.map(ruleTagToken.getRuleName(), tree);
                if (ruleTagToken.getLabel() == null) {
                    return null;
                }
                multiMap.map(ruleTagToken.getLabel(), tree);
                return null;
            }
            if (0 != 0) {
                return null;
            }
            return r1;
        }
        if (r1.getChildCount() != r2.getChildCount()) {
            if (0 != 0) {
                return null;
            }
            return r1;
        }
        int n = r1.getChildCount();
        for (int i = 0; i < n; i++) {
            ParseTree childMatch = matchImpl(r1.getChild(i), patternTree.getChild(i), multiMap);
            if (childMatch != null) {
                return childMatch;
            }
        }
        return null;
    }

    protected RuleTagToken getRuleTagToken(ParseTree t) {
        if (t instanceof RuleNode) {
            RuleNode r = (RuleNode) t;
            if (r.getChildCount() == 1 && (r.getChild(0) instanceof TerminalNode)) {
                TerminalNode c = (TerminalNode) r.getChild(0);
                if (c.getSymbol() instanceof RuleTagToken) {
                    return (RuleTagToken) c.getSymbol();
                }
            }
        }
        return null;
    }

    public List tokenize(String pattern) {
        List<Chunk> chunks = split(pattern);
        ArrayList arrayList = new ArrayList();
        for (Chunk chunk : chunks) {
            if (chunk instanceof TagChunk) {
                TagChunk tagChunk = (TagChunk) chunk;
                if (Character.isUpperCase(tagChunk.getTag().charAt(0))) {
                    Integer ttype = Integer.valueOf(this.parser.getTokenType(tagChunk.getTag()));
                    if (ttype.intValue() == 0) {
                        throw new IllegalArgumentException("Unknown token " + tagChunk.getTag() + " in pattern: " + pattern);
                    }
                    TokenTagToken t = new TokenTagToken(tagChunk.getTag(), ttype.intValue(), tagChunk.getLabel());
                    arrayList.add(t);
                } else if (Character.isLowerCase(tagChunk.getTag().charAt(0))) {
                    int ruleIndex = this.parser.getRuleIndex(tagChunk.getTag());
                    if (ruleIndex == -1) {
                        throw new IllegalArgumentException("Unknown rule " + tagChunk.getTag() + " in pattern: " + pattern);
                    }
                    int ruleImaginaryTokenType = this.parser.getATNWithBypassAlts().ruleToTokenType[ruleIndex];
                    arrayList.add(new RuleTagToken(tagChunk.getTag(), ruleImaginaryTokenType, tagChunk.getLabel()));
                } else {
                    throw new IllegalArgumentException("invalid tag: " + tagChunk.getTag() + " in pattern: " + pattern);
                }
            } else {
                TextChunk textChunk = (TextChunk) chunk;
                ANTLRInputStream in = new ANTLRInputStream(textChunk.getText());
                this.lexer.setInputStream(in);
                Token t2 = this.lexer.nextToken();
                while (t2.getType() != -1) {
                    arrayList.add(t2);
                    t2 = this.lexer.nextToken();
                }
            }
        }
        return arrayList;
    }

    public List split(String pattern) {
        int afterLastTag;
        int p = 0;
        int n = pattern.length();
        ArrayList arrayList = new ArrayList();
        new StringBuilder();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        while (p < n) {
            if (p == pattern.indexOf(this.escape + this.start, p)) {
                p += this.escape.length() + this.start.length();
            } else if (p == pattern.indexOf(this.escape + this.stop, p)) {
                p += this.escape.length() + this.stop.length();
            } else if (p == pattern.indexOf(this.start, p)) {
                arrayList2.add(Integer.valueOf(p));
                p += this.start.length();
            } else if (p == pattern.indexOf(this.stop, p)) {
                arrayList3.add(Integer.valueOf(p));
                p += this.stop.length();
            } else {
                p++;
            }
        }
        if (arrayList2.size() > arrayList3.size()) {
            throw new IllegalArgumentException("unterminated tag in pattern: " + pattern);
        }
        if (arrayList2.size() < arrayList3.size()) {
            throw new IllegalArgumentException("missing start tag in pattern: " + pattern);
        }
        int ntags = arrayList2.size();
        for (int i = 0; i < ntags; i++) {
            if (((Integer) arrayList2.get(i)).intValue() >= ((Integer) arrayList3.get(i)).intValue()) {
                throw new IllegalArgumentException("tag delimiters out of order in pattern: " + pattern);
            }
        }
        if (ntags == 0) {
            String text = pattern.substring(0, n);
            arrayList.add(new TextChunk(text));
        }
        if (ntags > 0 && ((Integer) arrayList2.get(0)).intValue() > 0) {
            String text2 = pattern.substring(0, ((Integer) arrayList2.get(0)).intValue());
            arrayList.add(new TextChunk(text2));
        }
        for (int i2 = 0; i2 < ntags; i2++) {
            String tag = pattern.substring(this.start.length() + ((Integer) arrayList2.get(i2)).intValue(), ((Integer) arrayList3.get(i2)).intValue());
            String ruleOrToken = tag;
            String label = null;
            int colon = tag.indexOf(58);
            if (colon >= 0) {
                label = tag.substring(0, colon);
                ruleOrToken = tag.substring(colon + 1, tag.length());
            }
            arrayList.add(new TagChunk(label, ruleOrToken));
            if (i2 + 1 < ntags) {
                String text3 = pattern.substring(this.stop.length() + ((Integer) arrayList3.get(i2)).intValue(), ((Integer) arrayList2.get(i2 + 1)).intValue());
                arrayList.add(new TextChunk(text3));
            }
        }
        if (ntags > 0 && (afterLastTag = ((Integer) arrayList3.get(ntags - 1)).intValue() + this.stop.length()) < n) {
            String text4 = pattern.substring(afterLastTag, n);
            arrayList.add(new TextChunk(text4));
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            Chunk c = (Chunk) arrayList.get(i3);
            if (c instanceof TextChunk) {
                TextChunk tc = (TextChunk) c;
                String unescaped = tc.getText().replace(this.escape, "");
                if (unescaped.length() < tc.getText().length()) {
                    arrayList.set(i3, new TextChunk(unescaped));
                }
            }
        }
        return arrayList;
    }
}
