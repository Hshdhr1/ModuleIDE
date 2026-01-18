package ru.yufic.exteraPlugins;

import android.text.Editable;
import android.text.Layout;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class t0 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    t0(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
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
        for (int i = lineForOffset2; i >= lineForOffset; i--) {
            text.insert(layout.getLineStart(i), "    ");
        }
        EditActivity.x(this.a).endBatchEdit();
        int i2 = (lineForOffset2 - lineForOffset) + 1;
        if (selectionStart != selectionEnd) {
            EditActivity.x(this.a).setSelection(selectionStart, selectionEnd + (i2 * 4));
        } else {
            EditActivity.x(this.a).setSelection(selectionStart + 4);
        }
    }
}
