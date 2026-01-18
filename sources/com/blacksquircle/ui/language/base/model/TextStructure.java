package com.blacksquircle.ui.language.base.model;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextStructure.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0006J\u000e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006J\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0006J\u000e\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0006J\u000e\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006J\u0016\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u0006R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001e"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/TextStructure;", "", "text", "", "(Ljava/lang/CharSequence;)V", "lineCount", "", "getLineCount", "()I", "lines", "", "Lcom/blacksquircle/ui/language/base/model/TextStructure$Line;", "getText", "()Ljava/lang/CharSequence;", "add", "", "line", "index", "clear", "getIndexForEndOfLine", "lineNumber", "getIndexForLine", "getIndexForStartOfLine", "getLine", "getLineForIndex", "remove", "shiftIndexes", "fromLine", "shiftBy", "Line", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public final class TextStructure {

    @NotNull
    private final List lines;

    @NotNull
    private final CharSequence text;

    public TextStructure(@NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "text");
        this.text = charSequence;
        List arrayList = new ArrayList();
        this.lines = arrayList;
        arrayList.add(new Line(0));
    }

    @NotNull
    public final CharSequence getText() {
        return this.text;
    }

    public final int getLineCount() {
        return this.lines.size();
    }

    public final void add(int line, int index) {
        if (line != 0) {
            this.lines.add(line, new Line(index));
        }
    }

    public final void remove(int line) {
        if (line != 0) {
            this.lines.remove(line);
        }
    }

    public final void clear() {
        this.lines.clear();
        this.lines.add(new Line(0));
    }

    public final void shiftIndexes(int fromLine, int shiftBy) {
        if (1 > fromLine || fromLine >= getLineCount()) {
            return;
        }
        while (fromLine < getLineCount()) {
            int indexForLine = getIndexForLine(fromLine) + shiftBy;
            if (fromLine <= 0 || indexForLine > 0) {
                ((Line) this.lines.get(fromLine)).setStart(indexForLine);
            } else {
                remove(fromLine);
                fromLine--;
            }
            fromLine++;
        }
    }

    public final int getIndexForLine(int line) {
        if (line >= getLineCount()) {
            return -1;
        }
        return ((Line) this.lines.get(line)).getStart();
    }

    public final int getIndexForStartOfLine(int lineNumber) {
        return getIndexForLine(lineNumber);
    }

    public final int getIndexForEndOfLine(int lineNumber) {
        if (lineNumber == getLineCount() - 1) {
            return this.text.length();
        }
        return getIndexForLine(lineNumber + 1) - 1;
    }

    public final int getLineForIndex(int index) {
        int lineCount = getLineCount() - 1;
        int i = 0;
        while (i < lineCount) {
            int i2 = (i + lineCount) / 2;
            if (index >= getIndexForLine(i2)) {
                if (index > getIndexForLine(i2)) {
                    i = i2 + 1;
                    if (index < getIndexForLine(i)) {
                    }
                }
                return i2;
            }
            lineCount = i2;
        }
        return getLineCount() - 1;
    }

    @NotNull
    public final Line getLine(int line) {
        if (line > -1 && line < getLineCount()) {
            return (Line) this.lines.get(line);
        }
        return new Line(0);
    }

    /* compiled from: TextStructure.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0010"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/TextStructure$Line;", "", "start", "", "(I)V", "getStart", "()I", "setStart", "component1", "copy", "equals", "", "other", "hashCode", "toString", "", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Line {
        private int start;

        public static /* synthetic */ Line copy$default(Line line, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = line.start;
            }
            return line.copy(i);
        }

        /* renamed from: component1, reason: from getter */
        public final int getStart() {
            return this.start;
        }

        @NotNull
        public final Line copy(int start) {
            return new Line(start);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Line) && this.start == ((Line) other).start;
        }

        public int hashCode() {
            return this.start;
        }

        @NotNull
        public String toString() {
            return "Line(start=" + this.start + ")";
        }

        public Line(int i) {
            this.start = i;
        }

        public final int getStart() {
            return this.start;
        }

        public final void setStart(int i) {
            this.start = i;
        }
    }
}
