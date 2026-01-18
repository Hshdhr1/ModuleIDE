package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes30.dex */
final class EmojiInputFilter implements InputFilter {
    private EmojiCompat.InitCallback mInitCallback;
    private final TextView mTextView;

    EmojiInputFilter(TextView textView) {
        this.mTextView = textView;
    }

    public CharSequence filter(CharSequence source, int sourceStart, int sourceEnd, Spanned dest, int destStart, int destEnd) {
        CharSequence text;
        if (this.mTextView.isInEditMode()) {
            return source;
        }
        switch (EmojiCompat.get().getLoadState()) {
            case 0:
            case 3:
                EmojiCompat.get().registerInitCallback(getInitCallback());
                break;
            case 1:
                boolean process = true;
                if (destEnd == 0 && destStart == 0 && dest.length() == 0) {
                    CharSequence oldText = this.mTextView.getText();
                    if (source == oldText) {
                        process = false;
                    }
                }
                if (process && source != null) {
                    if (sourceStart == 0 && sourceEnd == source.length()) {
                        text = source;
                    } else {
                        text = source.subSequence(sourceStart, sourceEnd);
                    }
                    break;
                }
                break;
        }
        return source;
    }

    private EmojiCompat.InitCallback getInitCallback() {
        if (this.mInitCallback == null) {
            this.mInitCallback = new InitCallbackImpl(this.mTextView, this);
        }
        return this.mInitCallback;
    }

    private static class InitCallbackImpl extends EmojiCompat.InitCallback {
        private final Reference mEmojiInputFilterReference;
        private final Reference mViewRef;

        InitCallbackImpl(TextView textView, EmojiInputFilter emojiInputFilter) {
            this.mViewRef = new WeakReference(textView);
            this.mEmojiInputFilterReference = new WeakReference(emojiInputFilter);
        }

        public void onInitialized() {
            super.onInitialized();
            TextView textView = (TextView) this.mViewRef.get();
            InputFilter myInputFilter = (InputFilter) this.mEmojiInputFilterReference.get();
            if (isInputFilterCurrentlyRegisteredOnTextView(textView, myInputFilter) && textView.isAttachedToWindow()) {
                Spannable process = EmojiCompat.get().process(textView.getText());
                int selectionStart = Selection.getSelectionStart(process);
                int selectionEnd = Selection.getSelectionEnd(process);
                textView.setText(process);
                if (process instanceof Spannable) {
                    EmojiInputFilter.updateSelection(process, selectionStart, selectionEnd);
                }
            }
        }

        private boolean isInputFilterCurrentlyRegisteredOnTextView(TextView textView, InputFilter myInputFilter) {
            InputFilter[] currentFilters;
            if (myInputFilter == null || textView == null || (currentFilters = textView.getFilters()) == null) {
                return false;
            }
            for (InputFilter inputFilter : currentFilters) {
                if (inputFilter == myInputFilter) {
                    return true;
                }
            }
            return false;
        }
    }

    static void updateSelection(Spannable spannable, int start, int end) {
        if (start >= 0 && end >= 0) {
            Selection.setSelection(spannable, start, end);
        } else if (start >= 0) {
            Selection.setSelection(spannable, start);
        } else if (end >= 0) {
            Selection.setSelection(spannable, end);
        }
    }
}
