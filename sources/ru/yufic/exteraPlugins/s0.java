package ru.yufic.exteraPlugins;

import android.text.Editable;
import android.text.Layout;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class s0 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    s0(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        int i;
        Editable text = EditActivity.x(this.a).getText();
        int selectionStart = EditActivity.x(this.a).getSelectionStart();
        int selectionEnd = EditActivity.x(this.a).getSelectionEnd();
        Layout layout = EditActivity.x(this.a).getLayout();
        if (layout == null) {
            return;
        }
        int lineForOffset = layout.getLineForOffset(selectionStart);
        int lineForOffset2 = layout.getLineForOffset(selectionEnd);
        EditActivity.x(this.a).beginBatchEdit();
        int i2 = (lineForOffset2 - lineForOffset) + 1;
        int[] iArr = new int[i2];
        while (true) {
            int i3 = 0;
            if (lineForOffset2 < lineForOffset) {
                break;
            }
            int i4 = lineForOffset2 - lineForOffset;
            int lineStart = layout.getLineStart(lineForOffset2);
            int min = Math.min(lineStart + 4, text.length());
            while (true) {
                i = lineStart + i3;
                if (i >= min || text.charAt(i) != ' ') {
                    break;
                } else {
                    i3++;
                }
            }
            if (i3 > 0) {
                text.delete(lineStart, i);
                iArr[i4] = i3;
            }
            lineForOffset2--;
        }
        EditActivity.x(this.a).endBatchEdit();
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            i5 += iArr[i6];
        }
        if (selectionStart != selectionEnd) {
            EditActivity.x(this.a).setSelection(selectionStart, selectionEnd - i5);
        } else {
            EditActivity.x(this.a).setSelection(Math.max(layout.getLineStart(lineForOffset), selectionStart - iArr[0]));
        }
    }
}
