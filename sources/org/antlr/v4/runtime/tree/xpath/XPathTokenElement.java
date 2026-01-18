package org.antlr.v4.runtime.tree.xpath;

import java.util.ArrayList;
import java.util.Collection;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class XPathTokenElement extends XPathElement {
    protected int tokenType;

    public XPathTokenElement(String tokenName, int tokenType) {
        super(tokenName);
        this.tokenType = tokenType;
    }

    public Collection evaluate(ParseTree t) {
        ArrayList arrayList = new ArrayList();
        for (Tree c : Trees.getChildren(t)) {
            if (c instanceof TerminalNode) {
                TerminalNode tnode = (TerminalNode) c;
                if ((tnode.getSymbol().getType() == this.tokenType && !this.invert) || (tnode.getSymbol().getType() != this.tokenType && this.invert)) {
                    arrayList.add(tnode);
                }
            }
        }
        return arrayList;
    }
}
