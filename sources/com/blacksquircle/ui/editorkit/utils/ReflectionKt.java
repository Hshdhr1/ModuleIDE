package com.blacksquircle.ui.editorkit.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Reflection.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005H\u0000¢\u0006\u0002\u0010\u0006\u001a\u0016\u0010\u0007\u001a\u00020\b*\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0016\u0010\f\u001a\u00020\r*\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0000¨\u0006\u000e"}, d2 = {"findField", "Ljava/lang/reflect/Field;", "Ljava/lang/Class;", "name", "", "", "(Ljava/lang/Class;[Ljava/lang/String;)Ljava/lang/reflect/Field;", "setCursorDrawableColor", "", "Landroid/widget/TextView;", "color", "", "tinted", "Landroid/graphics/drawable/Drawable;", "editorkit_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReflection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Reflection.kt\ncom/blacksquircle/ui/editorkit/utils/ReflectionKt\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,93:1\n13309#2,2:94\n1#3:96\n*S KotlinDebug\n*F\n+ 1 Reflection.kt\ncom/blacksquircle/ui/editorkit/utils/ReflectionKt\n*L\n75#1:94,2\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ReflectionKt {
    public static final void setCursorDrawableColor(@NotNull TextView textView, @ColorInt int i) {
        Drawable drawable;
        Drawable tinted;
        Intrinsics.checkNotNullParameter(textView, "<this>");
        if (Build.VERSION.SDK_INT >= 29) {
            Drawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{i, i});
            gradientDrawable.setSize((int) TypedValue.applyDimension(2, 2.0f, textView.getResources().getDisplayMetrics()), (int) textView.getTextSize());
            textView.setTextCursorDrawable(gradientDrawable);
            return;
        }
        try {
            Field findField = findField(TextView.class, "mEditor");
            Object obj = findField != null ? findField.get(textView) : null;
            if (obj == null) {
                obj = textView;
            }
            Class cls = findField != null ? obj.getClass() : TextView.class;
            Field findField2 = findField(TextView.class, "mCursorDrawableRes");
            Object obj2 = findField2 != null ? findField2.get(textView) : null;
            Integer num = obj2 instanceof Integer ? (Integer) obj2 : null;
            if (num == null || (drawable = ContextCompat.getDrawable(textView.getContext(), num.intValue())) == null || (tinted = tinted(drawable, i)) == null) {
                return;
            }
            Field findField3 = Build.VERSION.SDK_INT >= 28 ? findField(cls, "mDrawableForCursor") : null;
            if (findField3 != null) {
                findField3.set(obj, tinted);
                return;
            }
            Field findField4 = findField(cls, "mCursorDrawable", "mDrawableForCursor");
            if (findField4 != null) {
                findField4.set(obj, new Drawable[]{tinted, tinted});
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @NotNull
    public static final Drawable tinted(@NotNull Drawable drawable, @ColorInt int i) {
        Intrinsics.checkNotNullParameter(drawable, "<this>");
        if (drawable instanceof VectorDrawableCompat) {
            ((VectorDrawableCompat) drawable).setTintList(ColorStateList.valueOf(i));
            return drawable;
        }
        if (drawable instanceof VectorDrawable) {
            ((VectorDrawable) drawable).setTintList(ColorStateList.valueOf(i));
            return drawable;
        }
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, i);
        Drawable unwrap = DrawableCompat.unwrap(wrap);
        Intrinsics.checkNotNullExpressionValue(unwrap, "let(...)");
        return unwrap;
    }

    @Nullable
    public static final Field findField(@NotNull Class cls, @NotNull String... strArr) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        Intrinsics.checkNotNullParameter(strArr, "name");
        for (String str : strArr) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }
}
