package org.antlr.v4.runtime.tree.xpath;

import java.util.ArrayList;
import java.util.Collection;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class XPathWildcardElement extends XPathElement {
    public XPathWildcardElement() {
        super("*");
    }

    public Collection evaluate(ParseTree t) {
        if (this.invert) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (Tree c : Trees.getChildren(t)) {
            arrayList.add((ParseTree) c);
        }
        return arrayList;
    }
}
