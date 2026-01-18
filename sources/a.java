// default package

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public abstract class a {
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r7, android.net.Uri r8) {
        /*
            boolean r0 = android.provider.DocumentsContract.isDocumentUri(r7, r8)
            r1 = 0
            if (r0 == 0) goto Lcc
            boolean r0 = i(r8)
            r2 = 0
            java.lang.String r3 = ":"
            r4 = 1
            if (r0 == 0) goto L3f
            java.lang.String r7 = android.provider.DocumentsContract.getDocumentId(r8)
            java.lang.String[] r7 = r7.split(r3)
            r8 = r7[r2]
            java.lang.String r0 = "primary"
            boolean r8 = r0.equalsIgnoreCase(r8)
            if (r8 == 0) goto Lee
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            r8.append(r0)
            java.lang.String r0 = "/"
            r8.append(r0)
            r7 = r7[r4]
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            goto Lef
        L3f:
            boolean r0 = g(r8)
            java.lang.String r5 = "_id=?"
            if (r0 == 0) goto L8f
            java.lang.String r8 = android.provider.DocumentsContract.getDocumentId(r8)
            java.lang.String[] r0 = r8.split(r3)
            r2 = r0[r2]
            java.lang.String r3 = "raw"
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L5c
            r7 = r0[r4]
            return r7
        L5c:
            int r3 = android.os.Build.VERSION.SDK_INT
            r6 = 29
            if (r3 < r6) goto L78
            java.lang.String r3 = "msf"
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 == 0) goto L78
            r8 = r0[r4]
            java.lang.String[] r8 = new java.lang.String[]{r8}
            android.net.Uri r0 = android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI
            java.lang.String r7 = e(r7, r0, r5, r8)
            goto Lef
        L78:
            java.lang.String r0 = "content://downloads/public_downloads"
            android.net.Uri r0 = android.net.Uri.parse(r0)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            long r2 = r8.longValue()
            android.net.Uri r8 = android.content.ContentUris.withAppendedId(r0, r2)
            java.lang.String r7 = e(r7, r8, r1, r1)
            goto Lef
        L8f:
            boolean r0 = j(r8)
            if (r0 == 0) goto Lee
            java.lang.String r8 = android.provider.DocumentsContract.getDocumentId(r8)
            java.lang.String[] r8 = r8.split(r3)
            r0 = r8[r2]
            java.lang.String r2 = "image"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto Laa
            android.net.Uri r0 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            goto Lc1
        Laa:
            java.lang.String r2 = "video"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto Lb5
            android.net.Uri r0 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            goto Lc1
        Lb5:
            java.lang.String r2 = "audio"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto Lc0
            android.net.Uri r0 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            goto Lc1
        Lc0:
            r0 = r1
        Lc1:
            r8 = r8[r4]
            java.lang.String[] r8 = new java.lang.String[]{r8}
            java.lang.String r7 = e(r7, r0, r5, r8)
            goto Lef
        Lcc:
            java.lang.String r0 = r8.getScheme()
            java.lang.String r2 = "content"
            boolean r0 = r2.equalsIgnoreCase(r0)
            if (r0 == 0) goto Ldd
            java.lang.String r7 = e(r7, r8, r1, r1)
            goto Lef
        Ldd:
            java.lang.String r7 = "file"
            java.lang.String r0 = r8.getScheme()
            boolean r7 = r7.equalsIgnoreCase(r0)
            if (r7 == 0) goto Lee
            java.lang.String r7 = r8.getPath()
            goto Lef
        Lee:
            r7 = r1
        Lef:
            if (r7 == 0) goto Lf8
            java.lang.String r8 = "UTF-8"
            java.lang.String r7 = java.net.URLDecoder.decode(r7, r8)     // Catch: java.lang.Exception -> Lf8
            return r7
        Lf8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a(android.content.Context, android.net.Uri):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x002d -> B:18:0x0058). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void b(java.lang.String r3, java.lang.String r4) {
        /*
            boolean r0 = h(r3)
            if (r0 != 0) goto L7
            goto L58
        L7:
            c(r4)
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L44
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L44
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3e
            r2 = 0
            r3.<init>(r4, r2)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3e
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L38
        L1a:
            int r0 = r1.read(r4)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L38
            if (r0 > 0) goto L31
            r1.close()     // Catch: java.io.IOException -> L24
            goto L28
        L24:
            r4 = move-exception
            r4.printStackTrace()
        L28:
            r3.close()     // Catch: java.io.IOException -> L2c
            goto L58
        L2c:
            r3 = move-exception
            r3.printStackTrace()
            goto L58
        L31:
            r3.write(r4, r2, r0)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L38
            goto L1a
        L35:
            r4 = move-exception
        L36:
            r0 = r1
            goto L5a
        L38:
            r4 = move-exception
        L39:
            r0 = r1
            goto L46
        L3b:
            r4 = move-exception
            r3 = r0
            goto L36
        L3e:
            r4 = move-exception
            r3 = r0
            goto L39
        L41:
            r4 = move-exception
            r3 = r0
            goto L5a
        L44:
            r4 = move-exception
            r3 = r0
        L46:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L59
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r4 = move-exception
            r4.printStackTrace()
        L53:
            if (r3 == 0) goto L58
            r3.close()     // Catch: java.io.IOException -> L2c
        L58:
            return
        L59:
            r4 = move-exception
        L5a:
            if (r0 == 0) goto L64
            r0.close()     // Catch: java.io.IOException -> L60
            goto L64
        L60:
            r0 = move-exception
            r0.printStackTrace()
        L64:
            if (r3 == 0) goto L6e
            r3.close()     // Catch: java.io.IOException -> L6a
            goto L6e
        L6a:
            r3 = move-exception
            r3.printStackTrace()
        L6e:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: a.b(java.lang.String, java.lang.String):void");
    }

    private static void c(String str) {
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (lastIndexOf > 0) {
            k(str.substring(0, lastIndexOf));
        }
        File file = new File(str);
        try {
            if (file.exists()) {
                return;
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void d(String str) {
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        d(file2.getAbsolutePath());
                    }
                    if (file2.isFile()) {
                        file2.delete();
                    }
                }
            }
            file.delete();
        }
    }

    private static String e(Context context, Uri uri, String str, String[] strArr) {
        Throwable th;
        Throwable th2;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, (String) null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        query.close();
                        return string;
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    try {
                        query.close();
                        throw th2;
                    } catch (Throwable th4) {
                        th = th4;
                        if (th2 == null) {
                            throw th;
                        }
                        if (th2 != th) {
                            try {
                                th2.addSuppressed(th);
                            } catch (Exception unused) {
                            }
                        }
                        throw th2;
                    }
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th5) {
            th = th5;
            th2 = null;
        }
    }

    public static String f(Context context) {
        return context.getExternalFilesDir((String) null).getAbsolutePath();
    }

    private static boolean g(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean h(String str) {
        return new File(str).exists();
    }

    private static boolean i(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean j(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void k(String str) {
        if (h(str)) {
            return;
        }
        new File(str).mkdirs();
    }

    public static String l(String str) {
        Reader fileReader;
        c(str);
        StringBuilder sb = new StringBuilder();
        Reader reader = null;
        try {
            try {
                try {
                    fileReader = new FileReader(new File(str));
                } catch (IOException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            char[] cArr = new char[1024];
            while (true) {
                int read = fileReader.read(cArr);
                if (read <= 0) {
                    break;
                }
                sb.append(new String(cArr, 0, read));
            }
            fileReader.close();
        } catch (IOException e3) {
            e = e3;
            reader = fileReader;
            e.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            return sb.toString();
        } catch (Throwable th2) {
            th = th2;
            reader = fileReader;
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
        return sb.toString();
    }

    public static void m(String str, String str2) {
        Writer fileWriter;
        c(str);
        Writer writer = null;
        try {
            try {
                try {
                    fileWriter = new FileWriter(new File(str), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileWriter.write(str2);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e3) {
            e = e3;
            writer = fileWriter;
            e.printStackTrace();
            if (writer != null) {
                writer.close();
            }
        } catch (Throwable th2) {
            th = th2;
            writer = fileWriter;
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }
}
