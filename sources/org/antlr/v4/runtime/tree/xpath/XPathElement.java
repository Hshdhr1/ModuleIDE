package org.antlr.v4.runtime.tree.xpath;

import java.util.Collection;
import org.antlr.v4.runtime.tree.ParseTree;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class XPathElement {
    protected boolean invert;
    protected String nodeName;

    public abstract Collection evaluate(ParseTree parseTree);

    public XPathElement(String nodeName) {
        this.nodeName = nodeName;
    }

    public String toString() {
        String inv = this.invert ? "!" : "";
        return getClass().getSimpleName() + "[" + inv + this.nodeName + "]";
    }
}
