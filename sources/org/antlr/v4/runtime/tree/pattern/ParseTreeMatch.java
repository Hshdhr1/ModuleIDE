package org.antlr.v4.runtime.tree.pattern;

import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.tree.ParseTree;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParseTreeMatch {
    private final MultiMap labels;
    private final ParseTree mismatchedNode;
    private final ParseTreePattern pattern;
    private final ParseTree tree;

    public ParseTreeMatch(ParseTree tree, ParseTreePattern pattern, MultiMap multiMap, ParseTree mismatchedNode) {
        if (tree == null) {
            throw new IllegalArgumentException("tree cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern cannot be null");
        }
        if (multiMap == null) {
            throw new IllegalArgumentException("labels cannot be null");
        }
        this.tree = tree;
        this.pattern = pattern;
        this.labels = multiMap;
        this.mismatchedNode = mismatchedNode;
    }

    public ParseTree get(String label) {
        List<ParseTree> parseTrees = (List) this.labels.get(label);
        if (parseTrees == null || parseTrees.size() == 0) {
            return null;
        }
        return (ParseTree) parseTrees.get(parseTrees.size() - 1);
    }

    public List getAll(String label) {
        List<ParseTree> nodes = (List) this.labels.get(label);
        if (nodes == null) {
            return Collections.emptyList();
        }
        return nodes;
    }

    public MultiMap getLabels() {
        return this.labels;
    }

    public ParseTree getMismatchedNode() {
        return this.mismatchedNode;
    }

    public boolean succeeded() {
        return this.mismatchedNode == null;
    }

    public ParseTreePattern getPattern() {
        return this.pattern;
    }

    public ParseTree getTree() {
        return this.tree;
    }

    public String toString() {
        Object[] objArr = new Object[2];
        objArr[0] = succeeded() ? "succeeded" : "failed";
        objArr[1] = Integer.valueOf(getLabels().size());
        return String.format("Match %s; found %d labels", objArr);
    }
}
