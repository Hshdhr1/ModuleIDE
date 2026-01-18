package com.github.difflib.algorithm.myers;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class PathNode {
    public final boolean bootstrap;
    public final int i;
    public final int j;
    public final PathNode prev;
    public final boolean snake;

    public PathNode(int i, int i2, boolean z, boolean z2, PathNode pathNode) {
        this.i = i;
        this.j = i2;
        this.bootstrap = z2;
        if (z) {
            this.prev = pathNode;
        } else {
            this.prev = pathNode == null ? null : pathNode.previousSnake();
        }
        this.snake = z;
    }

    public boolean isSnake() {
        return this.snake;
    }

    public boolean isBootstrap() {
        return this.bootstrap;
    }

    public final PathNode previousSnake() {
        PathNode pathNode;
        if (isBootstrap()) {
            return null;
        }
        return (isSnake() || (pathNode = this.prev) == null) ? this : pathNode.previousSnake();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (PathNode pathNode = this; pathNode != null; pathNode = pathNode.prev) {
            sb.append("(");
            sb.append(pathNode.i);
            sb.append(",");
            sb.append(pathNode.j);
            sb.append(")");
        }
        sb.append("]");
        return sb.toString();
    }
}
