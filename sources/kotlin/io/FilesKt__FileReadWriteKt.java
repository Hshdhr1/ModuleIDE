package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileReadWrite.kt */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a!\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0087\b\u001a\u0017\u0010\t\u001a\u00020\n*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a!\u0010\u000b\u001a\u00020\f*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0002\u001a\u0012\u0010\u0011\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0010\u001a\u0012\u0010\u0014\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0010\u001a\u0014\u0010\u0015\u001a\u00020\u0016*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0017\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0019\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u001a\u001a\u00020\u0012*\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0014\u0010\u001c\u001a\n \u001e*\u0004\u0018\u00010\u001d0\u001d*\u00020\u0004H\u0000\u001a\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u001dH\u0000\u001aB\u0010#\u001a\u00020\u0012*\u00020\u000226\u0010$\u001a2\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0013\u0012\u00110\b¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u00120%\u001aJ\u0010#\u001a\u00020\u0012*\u00020\u00022\u0006\u0010*\u001a\u00020\b26\u0010$\u001a2\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0013\u0012\u00110\b¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u00120%\u001a7\u0010+\u001a\u00020\u0012*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042!\u0010$\u001a\u001d\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(-\u0012\u0004\u0012\u00020\u00120,\u001a\r\u0010.\u001a\u00020/*\u00020\u0002H\u0087\b\u001a\r\u00100\u001a\u000201*\u00020\u0002H\u0087\b\u001a\u001a\u00102\u001a\b\u0012\u0004\u0012\u00020\u001603*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001aL\u00104\u001a\u0002H5\"\u0004\b\u0000\u00105*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0018\u00106\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001607\u0012\u0004\u0012\u0002H50,H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u00108\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00069"}, d2 = {"reader", "Ljava/io/InputStreamReader;", "Ljava/io/File;", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "writer", "Ljava/io/OutputStreamWriter;", "bufferedWriter", "Ljava/io/BufferedWriter;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "", "writeBytes", "", "array", "appendBytes", "readText", "", "writeText", "text", "appendText", "writeTextImpl", "Ljava/io/OutputStream;", "newReplaceEncoder", "Ljava/nio/charset/CharsetEncoder;", "kotlin.jvm.PlatformType", "byteBufferForEncoding", "Ljava/nio/ByteBuffer;", "chunkSize", "encoder", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "readLines", "", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/io/FilesKt")
@SourceDebugExtension({"SMAP\nFileReadWrite.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileReadWrite.kt\nkotlin/io/FilesKt__FileReadWriteKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,290:1\n1#2:291\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
    public static /* synthetic */ Unit $r8$lambda$4EcuRhbBdJT1OYuEKwTrZ40dPsY(ArrayList arrayList, String str) {
        return readLines$lambda$9$FilesKt__FileReadWriteKt(arrayList, str);
    }

    static /* synthetic */ InputStreamReader reader$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new InputStreamReader(new FileInputStream(file), charset);
    }

    @InlineOnly
    private static final InputStreamReader reader(File file, Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new InputStreamReader(new FileInputStream(file), charset);
    }

    static /* synthetic */ BufferedReader bufferedReader$default(File file, Charset charset, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        BufferedReader bufferedReader = (Reader) new InputStreamReader(new FileInputStream(file), charset);
        return bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, i);
    }

    @InlineOnly
    private static final BufferedReader bufferedReader(File file, Charset charset, int i) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        BufferedReader bufferedReader = (Reader) new InputStreamReader(new FileInputStream(file), charset);
        return bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, i);
    }

    static /* synthetic */ OutputStreamWriter writer$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new OutputStreamWriter(new FileOutputStream(file), charset);
    }

    @InlineOnly
    private static final OutputStreamWriter writer(File file, Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new OutputStreamWriter(new FileOutputStream(file), charset);
    }

    static /* synthetic */ BufferedWriter bufferedWriter$default(File file, Charset charset, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        BufferedWriter bufferedWriter = (Writer) new OutputStreamWriter(new FileOutputStream(file), charset);
        return bufferedWriter instanceof BufferedWriter ? bufferedWriter : new BufferedWriter(bufferedWriter, i);
    }

    @InlineOnly
    private static final BufferedWriter bufferedWriter(File file, Charset charset, int i) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        BufferedWriter bufferedWriter = (Writer) new OutputStreamWriter(new FileOutputStream(file), charset);
        return bufferedWriter instanceof BufferedWriter ? bufferedWriter : new BufferedWriter(bufferedWriter, i);
    }

    static /* synthetic */ PrintWriter printWriter$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        BufferedWriter bufferedWriter = (Writer) new OutputStreamWriter(new FileOutputStream(file), charset);
        return new PrintWriter((Writer) (bufferedWriter instanceof BufferedWriter ? bufferedWriter : new BufferedWriter(bufferedWriter, 8192)));
    }

    @InlineOnly
    private static final PrintWriter printWriter(File file, Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        BufferedWriter bufferedWriter = (Writer) new OutputStreamWriter(new FileOutputStream(file), charset);
        return new PrintWriter((Writer) (bufferedWriter instanceof BufferedWriter ? bufferedWriter : new BufferedWriter(bufferedWriter, 8192)));
    }

    @NotNull
    public static final byte[] readBytes(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        InputStream inputStream = (Closeable) new FileInputStream(file);
        try {
            InputStream inputStream2 = (FileInputStream) inputStream;
            long length = file.length();
            if (length > 2147483647L) {
                throw new OutOfMemoryError("File " + file + " is too big (" + length + " bytes) to fit in memory.");
            }
            int i = (int) length;
            byte[] bArr = new byte[i];
            int i2 = i;
            int i3 = 0;
            while (i2 > 0) {
                int read = inputStream2.read(bArr, i3, i2);
                if (read < 0) {
                    break;
                }
                i2 -= read;
                i3 += read;
            }
            if (i2 > 0) {
                bArr = Arrays.copyOf(bArr, i3);
                Intrinsics.checkNotNullExpressionValue(bArr, "copyOf(...)");
            } else {
                int read2 = inputStream2.read();
                if (read2 != -1) {
                    OutputStream exposingBufferByteArrayOutputStream = new ExposingBufferByteArrayOutputStream(8193);
                    exposingBufferByteArrayOutputStream.write(read2);
                    ByteStreamsKt.copyTo$default(inputStream2, exposingBufferByteArrayOutputStream, 0, 2, null);
                    int size = exposingBufferByteArrayOutputStream.size() + i;
                    if (size < 0) {
                        throw new OutOfMemoryError("File " + file + " is too big to fit in memory.");
                    }
                    byte[] buffer = exposingBufferByteArrayOutputStream.getBuffer();
                    byte[] copyOf = Arrays.copyOf(bArr, size);
                    Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                    bArr = ArraysKt.copyInto(buffer, copyOf, i, 0, exposingBufferByteArrayOutputStream.size());
                }
            }
            CloseableKt.closeFinally(inputStream, null);
            return bArr;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(inputStream, th);
                throw th2;
            }
        }
    }

    public static final void writeBytes(@NotNull File file, @NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(array, "array");
        FileOutputStream fileOutputStream = (Closeable) new FileOutputStream(file);
        try {
            fileOutputStream.write(array);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
        } finally {
        }
    }

    public static final void appendBytes(@NotNull File file, @NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(array, "array");
        FileOutputStream fileOutputStream = (Closeable) new FileOutputStream(file, true);
        try {
            fileOutputStream.write(array);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
        } finally {
        }
    }

    @NotNull
    public static final String readText(@NotNull File file, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Reader reader = (Closeable) new InputStreamReader(new FileInputStream(file), charset);
        try {
            String readText = TextStreamsKt.readText((InputStreamReader) reader);
            CloseableKt.closeFinally(reader, null);
            return readText;
        } finally {
        }
    }

    public static /* synthetic */ String readText$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readText(file, charset);
    }

    public static /* synthetic */ void writeText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, str, charset);
    }

    public static final void writeText(@NotNull File file, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        OutputStream outputStream = (Closeable) new FileOutputStream(file);
        try {
            FilesKt.writeTextImpl((FileOutputStream) outputStream, text, charset);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(outputStream, null);
        } finally {
        }
    }

    public static /* synthetic */ void appendText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, str, charset);
    }

    public static final void appendText(@NotNull File file, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        OutputStream outputStream = (Closeable) new FileOutputStream(file, true);
        try {
            FilesKt.writeTextImpl((FileOutputStream) outputStream, text, charset);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(outputStream, null);
        } finally {
        }
    }

    public static final void writeTextImpl(@NotNull OutputStream outputStream, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        if (text.length() < 16384) {
            byte[] bytes = text.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            outputStream.write(bytes);
            return;
        }
        CharsetEncoder newReplaceEncoder = FilesKt.newReplaceEncoder(charset);
        CharBuffer allocate = CharBuffer.allocate(8192);
        Intrinsics.checkNotNull(newReplaceEncoder);
        ByteBuffer byteBufferForEncoding = FilesKt.byteBufferForEncoding(8192, newReplaceEncoder);
        int i = 0;
        int i2 = 0;
        while (i < text.length()) {
            int min = Math.min(8192 - i2, text.length() - i);
            int i3 = i + min;
            char[] array = allocate.array();
            Intrinsics.checkNotNullExpressionValue(array, "array(...)");
            text.getChars(i, i3, array, i2);
            allocate.limit(min + i2);
            i2 = 1;
            if (!newReplaceEncoder.encode(allocate, byteBufferForEncoding, i3 == text.length()).isUnderflow()) {
                throw new IllegalStateException("Check failed.");
            }
            outputStream.write(byteBufferForEncoding.array(), 0, byteBufferForEncoding.position());
            if (allocate.position() != allocate.limit()) {
                allocate.put(0, allocate.get());
            } else {
                i2 = 0;
            }
            allocate.clear();
            byteBufferForEncoding.clear();
            i = i3;
        }
    }

    public static final CharsetEncoder newReplaceEncoder(@NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(charset, "<this>");
        return charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    @NotNull
    public static final ByteBuffer byteBufferForEncoding(int i, @NotNull CharsetEncoder encoder) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        ByteBuffer allocate = ByteBuffer.allocate(i * ((int) Math.ceil(encoder.maxBytesPerChar())));
        Intrinsics.checkNotNullExpressionValue(allocate, "allocate(...)");
        return allocate;
    }

    public static final void forEachBlock(@NotNull File file, @NotNull Function2 action) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        FilesKt.forEachBlock(file, 4096, action);
    }

    public static final void forEachBlock(@NotNull File file, int i, @NotNull Function2 action) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        byte[] bArr = new byte[RangesKt.coerceAtLeast(i, 512)];
        FileInputStream fileInputStream = (Closeable) new FileInputStream(file);
        try {
            FileInputStream fileInputStream2 = fileInputStream;
            while (true) {
                int read = fileInputStream2.read(bArr);
                if (read > 0) {
                    action.invoke(bArr, Integer.valueOf(read));
                } else {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileInputStream, null);
                    return;
                }
            }
        } finally {
        }
    }

    public static /* synthetic */ void forEachLine$default(File file, Charset charset, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    public static final void forEachLine(@NotNull File file, @NotNull Charset charset, @NotNull Function1 action) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(action, "action");
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset)), action);
    }

    @InlineOnly
    private static final FileInputStream inputStream(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new FileInputStream(file);
    }

    @InlineOnly
    private static final FileOutputStream outputStream(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new FileOutputStream(file);
    }

    public static /* synthetic */ List readLines$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readLines(file, charset);
    }

    @NotNull
    public static final List readLines(@NotNull File file, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        List arrayList = new ArrayList();
        FilesKt.forEachLine(file, charset, new FilesKt__FileReadWriteKt$$ExternalSyntheticLambda0(arrayList));
        return arrayList;
    }

    private static final Unit readLines$lambda$9$FilesKt__FileReadWriteKt(ArrayList arrayList, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        arrayList.add(it);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object useLines$default(File file, Charset charset, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(block, "block");
        BufferedReader bufferedReader = (Reader) new InputStreamReader(new FileInputStream(file), charset);
        BufferedReader bufferedReader2 = (Closeable) (bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, 8192));
        try {
            Object invoke = block.invoke(TextStreamsKt.lineSequence(bufferedReader2));
            CloseableKt.closeFinally(bufferedReader2, null);
            return invoke;
        } finally {
        }
    }

    public static final Object useLines(@NotNull File file, @NotNull Charset charset, @NotNull Function1 block) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(block, "block");
        BufferedReader bufferedReader = (Reader) new InputStreamReader(new FileInputStream(file), charset);
        BufferedReader bufferedReader2 = (Closeable) (bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, 8192));
        try {
            Object invoke = block.invoke(TextStreamsKt.lineSequence(bufferedReader2));
            CloseableKt.closeFinally(bufferedReader2, null);
            return invoke;
        } finally {
        }
    }
}
