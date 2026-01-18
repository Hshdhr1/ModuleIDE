package org.antlr.v4.runtime.tree.xpath;

import java.util.ArrayList;
import java.util.Collection;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class XPathRuleElement extends XPathElement {
    protected int ruleIndex;

    public XPathRuleElement(String ruleName, int ruleIndex) {
        super(ruleName);
        this.ruleIndex = ruleIndex;
    }

    public Collection evaluate(ParseTree t) {
        ArrayList arrayList = new ArrayList();
        for (Tree c : Trees.getChildren(t)) {
            if (c instanceof ParserRuleContext) {
                ParserRuleContext ctx = (ParserRuleContext) c;
                if ((ctx.getRuleIndex() == this.ruleIndex && !this.invert) || (ctx.getRuleIndex() != this.ruleIndex && this.invert)) {
                    arrayList.add(ctx);
                }
            }
        }
        return arrayList;
    }
}
