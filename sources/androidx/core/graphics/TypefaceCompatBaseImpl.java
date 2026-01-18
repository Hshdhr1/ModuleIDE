package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
class TypefaceCompatBaseImpl {
    private static final int INVALID_KEY = 0;
    private static final String TAG = "TypefaceCompatBaseImpl";
    private ConcurrentHashMap mFontFamilies = new ConcurrentHashMap();

    private interface StyleExtractor {
        int getWeight(Object obj);

        boolean isItalic(Object obj);
    }

    TypefaceCompatBaseImpl() {
    }

    private static Object findBestFont(Object[] objArr, int style, StyleExtractor styleExtractor) {
        int targetWeight = (style & 1) == 0 ? 400 : 700;
        boolean isTargetItalic = (style & 2) != 0;
        Object obj = null;
        int bestScore = Integer.MAX_VALUE;
        for (Object obj2 : objArr) {
            int score = (Math.abs(styleExtractor.getWeight(obj2) - targetWeight) * 2) + (styleExtractor.isItalic(obj2) == isTargetItalic ? 0 : 1);
            if (obj == null || bestScore > score) {
                obj = obj2;
                bestScore = score;
            }
        }
        return obj;
    }

    private static long getUniqueKey(Typeface typeface) {
        if (typeface == null) {
            return 0L;
        }
        try {
            Field field = Typeface.class.getDeclaredField("native_instance");
            field.setAccessible(true);
            Number num = (Number) field.get(typeface);
            return num.longValue();
        } catch (IllegalAccessException e) {
            Log.e("TypefaceCompatBaseImpl", "Could not retrieve font from family.", e);
            return 0L;
        } catch (NoSuchFieldException e2) {
            Log.e("TypefaceCompatBaseImpl", "Could not retrieve font from family.", e2);
            return 0L;
        }
    }

    class 1 implements StyleExtractor {
        1() {
        }

        public int getWeight(FontsContractCompat.FontInfo info) {
            return info.getWeight();
        }

        public boolean isItalic(FontsContractCompat.FontInfo info) {
            return info.isItalic();
        }
    }

    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fonts, int style) {
        return (FontsContractCompat.FontInfo) findBestFont(fonts, style, new 1());
    }

    protected Typeface createFromInputStream(Context context, InputStream is) {
        File tmpFile = TypefaceCompatUtil.getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (TypefaceCompatUtil.copyToFile(tmpFile, is)) {
                return Typeface.createFromFile(tmpFile.getPath());
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        } finally {
            tmpFile.delete();
        }
    }

    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        if (fonts.length < 1) {
            return null;
        }
        FontsContractCompat.FontInfo font = findBestInfo(fonts, style);
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(font.getUri());
            return createFromInputStream(context, is);
        } catch (IOException e) {
            return null;
        } finally {
            TypefaceCompatUtil.closeQuietly(is);
        }
    }

    class 2 implements StyleExtractor {
        2() {
        }

        public int getWeight(FontResourcesParserCompat.FontFileResourceEntry entry) {
            return entry.getWeight();
        }

        public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry entry) {
            return entry.isItalic();
        }
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, int style) {
        return (FontResourcesParserCompat.FontFileResourceEntry) findBestFont(entry.getEntries(), style, new 2());
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        FontResourcesParserCompat.FontFileResourceEntry best = findBestEntry(entry, style);
        if (best == null) {
            return null;
        }
        Typeface typeface = TypefaceCompat.createFromResourcesFontFile(context, resources, best.getResourceId(), best.getFileName(), style);
        addFontFamily(typeface, entry);
        return typeface;
    }

    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        File tmpFile = TypefaceCompatUtil.getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (TypefaceCompatUtil.copyToFile(tmpFile, resources, id)) {
                return Typeface.createFromFile(tmpFile.getPath());
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        } finally {
            tmpFile.delete();
        }
    }

    FontResourcesParserCompat.FontFamilyFilesResourceEntry getFontFamily(Typeface typeface) {
        long key = getUniqueKey(typeface);
        if (key == 0) {
            return null;
        }
        return (FontResourcesParserCompat.FontFamilyFilesResourceEntry) this.mFontFamilies.get(Long.valueOf(key));
    }

    private void addFontFamily(Typeface typeface, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry) {
        long key = getUniqueKey(typeface);
        if (key != 0) {
            this.mFontFamilies.put(Long.valueOf(key), entry);
        }
    }
}
