package org.antlr.v4.runtime.tree.xpath;

import java.util.ArrayList;
import java.util.Collection;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class XPathWildcardAnywhereElement extends XPathElement {
    public XPathWildcardAnywhereElement() {
        super("*");
    }

    public Collection evaluate(ParseTree t) {
        return this.invert ? new ArrayList() : Trees.getDescendants(t);
    }
}
