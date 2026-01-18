package org.antlr.v4.runtime.tree;

import java.util.ArrayDeque;
import org.antlr.v4.runtime.misc.IntegerStack;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class IterativeParseTreeWalker extends ParseTreeWalker {
    public void walk(ParseTreeListener listener, ParseTree t) {
        ArrayDeque arrayDeque = new ArrayDeque();
        IntegerStack indexStack = new IntegerStack();
        ParseTree currentNode = t;
        int currentIndex = 0;
        while (currentNode != null) {
            if (currentNode instanceof ErrorNode) {
                listener.visitErrorNode((ErrorNode) currentNode);
            } else if (currentNode instanceof TerminalNode) {
                listener.visitTerminal((TerminalNode) currentNode);
            } else {
                RuleNode r = (RuleNode) currentNode;
                enterRule(listener, r);
            }
            if (currentNode.getChildCount() > 0) {
                arrayDeque.push(currentNode);
                indexStack.push(currentIndex);
                currentIndex = 0;
                currentNode = currentNode.getChild(0);
            } else {
                while (true) {
                    if (currentNode instanceof RuleNode) {
                        exitRule(listener, (RuleNode) currentNode);
                    }
                    if (arrayDeque.isEmpty()) {
                        currentNode = null;
                        currentIndex = 0;
                        break;
                    }
                    currentIndex++;
                    currentNode = ((ParseTree) arrayDeque.peek()).getChild(currentIndex);
                    if (currentNode == null) {
                        currentNode = (ParseTree) arrayDeque.pop();
                        currentIndex = indexStack.pop();
                        if (currentNode == null) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
