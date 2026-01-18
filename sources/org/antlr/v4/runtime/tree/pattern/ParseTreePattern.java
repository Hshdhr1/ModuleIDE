package org.antlr.v4.runtime.tree.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParseTreePattern {
    private final ParseTreePatternMatcher matcher;
    private final String pattern;
    private final int patternRuleIndex;
    private final ParseTree patternTree;

    public ParseTreePattern(ParseTreePatternMatcher matcher, String pattern, int patternRuleIndex, ParseTree patternTree) {
        this.matcher = matcher;
        this.patternRuleIndex = patternRuleIndex;
        this.pattern = pattern;
        this.patternTree = patternTree;
    }

    public ParseTreeMatch match(ParseTree tree) {
        return this.matcher.match(tree, this);
    }

    public boolean matches(ParseTree tree) {
        return this.matcher.match(tree, this).succeeded();
    }

    public List findAll(ParseTree tree, String xpath) {
        Collection<ParseTree> subtrees = XPath.findAll(tree, xpath, this.matcher.getParser());
        ArrayList arrayList = new ArrayList();
        for (ParseTree t : subtrees) {
            ParseTreeMatch match = match(t);
            if (match.succeeded()) {
                arrayList.add(match);
            }
        }
        return arrayList;
    }

    public ParseTreePatternMatcher getMatcher() {
        return this.matcher;
    }

    public String getPattern() {
        return this.pattern;
    }

    public int getPatternRuleIndex() {
        return this.patternRuleIndex;
    }

    public ParseTree getPatternTree() {
        return this.patternTree;
    }
}
