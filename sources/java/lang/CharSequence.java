package java.lang;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface CharSequence {
    char charAt(int i);

    IntStream chars();

    IntStream codePoints();

    int length();

    CharSequence subSequence(int i, int i2);

    String toString();

    class 1CharIterator implements PrimitiveIterator.OfInt {
        int cur = 0;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfInt.-CC.$default$forEachRemaining((PrimitiveIterator.OfInt) this, consumer);
        }

        public /* synthetic */ Integer next() {
            return PrimitiveIterator.OfInt.-CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        public /* bridge */ /* synthetic */ Object next() {
            return PrimitiveIterator.OfInt.-CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        1CharIterator() {
        }

        public boolean hasNext() {
            return this.cur < CharSequence.this.length();
        }

        public int nextInt() {
            if (hasNext()) {
                CharSequence charSequence = CharSequence.this;
                int i = this.cur;
                this.cur = i + 1;
                return charSequence.charAt(i);
            }
            throw new NoSuchElementException();
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            while (this.cur < CharSequence.this.length()) {
                intConsumer.accept(CharSequence.this.charAt(this.cur));
                this.cur++;
            }
        }
    }

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static IntStream $default$chars(CharSequence _this) {
            return StreamSupport.intStream(new CharSequence$$ExternalSyntheticLambda1(_this), 16464, false);
        }

        public static /* synthetic */ Spliterator.OfInt $private$lambda$chars$0(CharSequence _this) {
            return Spliterators.spliterator((PrimitiveIterator.OfInt) _this.new 1CharIterator(), _this.length(), 16);
        }

        public static IntStream $default$codePoints(CharSequence _this) {
            return StreamSupport.intStream(new CharSequence$$ExternalSyntheticLambda0(_this), 16, false);
        }

        public static /* synthetic */ Spliterator.OfInt $private$lambda$codePoints$1(CharSequence _this) {
            return Spliterators.spliteratorUnknownSize((PrimitiveIterator.OfInt) _this.new 1CodePointIterator(), 16);
        }

        public static int compare(CharSequence charSequence, CharSequence charSequence2) {
            charSequence.getClass();
            charSequence2.getClass();
            if (charSequence == charSequence2) {
                return 0;
            }
            if (charSequence.getClass() == charSequence2.getClass() && (charSequence instanceof Comparable)) {
                return ((Comparable) charSequence).compareTo(charSequence2);
            }
            int min = Math.min(charSequence.length(), charSequence2.length());
            for (int i = 0; i < min; i++) {
                char charAt = charSequence.charAt(i);
                char charAt2 = charSequence2.charAt(i);
                if (charAt != charAt2) {
                    return charAt - charAt2;
                }
            }
            return charSequence.length() - charSequence2.length();
        }
    }

    class 1CodePointIterator implements PrimitiveIterator.OfInt {
        int cur = 0;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfInt.-CC.$default$forEachRemaining((PrimitiveIterator.OfInt) this, consumer);
        }

        public /* synthetic */ Integer next() {
            return PrimitiveIterator.OfInt.-CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        public /* bridge */ /* synthetic */ Object next() {
            return PrimitiveIterator.OfInt.-CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        1CodePointIterator() {
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            int length = CharSequence.this.length();
            int i = this.cur;
            while (i < length) {
                try {
                    int i2 = i + 1;
                    try {
                        char charAt = CharSequence.this.charAt(i);
                        if (!Character.isHighSurrogate(charAt) || i2 >= length) {
                            intConsumer.accept(charAt);
                        } else {
                            char charAt2 = CharSequence.this.charAt(i2);
                            if (Character.isLowSurrogate(charAt2)) {
                                i += 2;
                                intConsumer.accept(Character.toCodePoint(charAt, charAt2));
                            } else {
                                intConsumer.accept(charAt);
                            }
                        }
                        i = i2;
                    } catch (Throwable th) {
                        th = th;
                        i = i2;
                        this.cur = i;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            this.cur = i;
        }

        public boolean hasNext() {
            return this.cur < CharSequence.this.length();
        }

        public int nextInt() {
            int i;
            int length = CharSequence.this.length();
            int i2 = this.cur;
            if (i2 >= length) {
                throw new NoSuchElementException();
            }
            CharSequence charSequence = CharSequence.this;
            this.cur = i2 + 1;
            char charAt = charSequence.charAt(i2);
            if (Character.isHighSurrogate(charAt) && (i = this.cur) < length) {
                char charAt2 = CharSequence.this.charAt(i);
                if (Character.isLowSurrogate(charAt2)) {
                    this.cur++;
                    return Character.toCodePoint(charAt, charAt2);
                }
            }
            return charAt;
        }
    }
}
