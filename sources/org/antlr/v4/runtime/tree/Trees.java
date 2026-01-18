package org.antlr.v4.runtime.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Predicate;
import org.antlr.v4.runtime.misc.Utils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class Trees {
    public static String toStringTree(Tree t) {
        return toStringTree(t, (List) null);
    }

    public static String toStringTree(Tree t, Parser recog) {
        String[] ruleNames = recog != null ? recog.getRuleNames() : null;
        List<String> ruleNamesList = ruleNames != null ? Arrays.asList(ruleNames) : null;
        return toStringTree(t, ruleNamesList);
    }

    public static String toStringTree(Tree t, List list) {
        String s = Utils.escapeWhitespace(getNodeText(t, list), false);
        if (t.getChildCount() == 0) {
            return s;
        }
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        String s2 = Utils.escapeWhitespace(getNodeText(t, list), false);
        buf.append(s2);
        buf.append(' ');
        for (int i = 0; i < t.getChildCount(); i++) {
            if (i > 0) {
                buf.append(' ');
            }
            buf.append(toStringTree(t.getChild(i), list));
        }
        buf.append(")");
        return buf.toString();
    }

    public static String getNodeText(Tree t, Parser recog) {
        String[] ruleNames = recog != null ? recog.getRuleNames() : null;
        List<String> ruleNamesList = ruleNames != null ? Arrays.asList(ruleNames) : null;
        return getNodeText(t, ruleNamesList);
    }

    public static String getNodeText(Tree t, List list) {
        Token symbol;
        if (list != null) {
            if (t instanceof RuleContext) {
                int ruleIndex = ((RuleContext) t).getRuleContext().getRuleIndex();
                String ruleName = (String) list.get(ruleIndex);
                int altNumber = ((RuleContext) t).getAltNumber();
                if (altNumber != 0) {
                    return ruleName + ":" + altNumber;
                }
                return ruleName;
            }
            if (t instanceof ErrorNode) {
                String ruleName2 = t.toString();
                return ruleName2;
            }
            if ((t instanceof TerminalNode) && (symbol = ((TerminalNode) t).getSymbol()) != null) {
                String s = symbol.getText();
                return s;
            }
        }
        Object payload = t.getPayload();
        if (payload instanceof Token) {
            String ruleName3 = ((Token) payload).getText();
            return ruleName3;
        }
        String ruleName4 = t.getPayload().toString();
        return ruleName4;
    }

    public static List getChildren(Tree t) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < t.getChildCount(); i++) {
            arrayList.add(t.getChild(i));
        }
        return arrayList;
    }

    public static List getAncestors(Tree t) {
        if (t.getParent() == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Tree t2 = t.getParent(); t2 != null; t2 = t2.getParent()) {
            arrayList.add(0, t2);
        }
        return arrayList;
    }

    public static boolean isAncestorOf(Tree t, Tree u) {
        if (t == null || u == null || t.getParent() == null) {
            return false;
        }
        for (Tree p = u.getParent(); p != null; p = p.getParent()) {
            if (t == p) {
                return true;
            }
        }
        return false;
    }

    public static Collection findAllTokenNodes(ParseTree t, int ttype) {
        return findAllNodes(t, ttype, true);
    }

    public static Collection findAllRuleNodes(ParseTree t, int ruleIndex) {
        return findAllNodes(t, ruleIndex, false);
    }

    public static List findAllNodes(ParseTree t, int index, boolean findTokens) {
        ArrayList arrayList = new ArrayList();
        _findAllNodes(t, index, findTokens, arrayList);
        return arrayList;
    }

    public static void _findAllNodes(ParseTree t, int index, boolean findTokens, List list) {
        if (findTokens && (t instanceof TerminalNode)) {
            TerminalNode tnode = (TerminalNode) t;
            if (tnode.getSymbol().getType() == index) {
                list.add(t);
            }
        } else if (!findTokens && (t instanceof ParserRuleContext)) {
            ParserRuleContext ctx = (ParserRuleContext) t;
            if (ctx.getRuleIndex() == index) {
                list.add(t);
            }
        }
        for (int i = 0; i < t.getChildCount(); i++) {
            _findAllNodes(t.getChild(i), index, findTokens, list);
        }
    }

    public static List getDescendants(ParseTree t) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(t);
        int n = t.getChildCount();
        for (int i = 0; i < n; i++) {
            arrayList.addAll(getDescendants(t.getChild(i)));
        }
        return arrayList;
    }

    public static List descendants(ParseTree t) {
        return getDescendants(t);
    }

    public static ParserRuleContext getRootOfSubtreeEnclosingRegion(ParseTree t, int startTokenIndex, int stopTokenIndex) {
        int n = t.getChildCount();
        for (int i = 0; i < n; i++) {
            ParseTree child = t.getChild(i);
            ParserRuleContext r = getRootOfSubtreeEnclosingRegion(child, startTokenIndex, stopTokenIndex);
            if (r != null) {
                return r;
            }
        }
        if (t instanceof ParserRuleContext) {
            ParserRuleContext r2 = (ParserRuleContext) t;
            if (startTokenIndex >= r2.getStart().getTokenIndex() && (r2.getStop() == null || stopTokenIndex <= r2.getStop().getTokenIndex())) {
                return r2;
            }
        }
        return null;
    }

    public static void stripChildrenOutOfRange(ParserRuleContext t, ParserRuleContext root, int startIndex, int stopIndex) {
        if (t != null) {
            for (int i = 0; i < t.getChildCount(); i++) {
                ParseTree child = t.getChild(i);
                Interval range = child.getSourceInterval();
                if ((child instanceof ParserRuleContext) && ((range.b < startIndex || range.a > stopIndex) && isAncestorOf(child, root))) {
                    CommonToken abbrev = new CommonToken(0, "...");
                    t.children.set(i, new TerminalNodeImpl(abbrev));
                }
            }
        }
    }

    public static Tree findNodeSuchThat(Tree t, Predicate predicate) {
        if (!predicate.test(t)) {
            if (t == null) {
                return null;
            }
            int n = t.getChildCount();
            for (int i = 0; i < n; i++) {
                Tree u = findNodeSuchThat(t.getChild(i), predicate);
                if (u != null) {
                    return u;
                }
            }
            return null;
        }
        return t;
    }

    private Trees() {
    }
}
