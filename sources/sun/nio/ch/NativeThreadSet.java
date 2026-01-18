package sun.nio.ch;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class NativeThreadSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long[] elts;
    private int used = 0;
    private boolean waitingToEmpty;

    NativeThreadSet(int i) {
        this.elts = new long[i];
    }

    int add() {
        long current = NativeThread.current();
        if (current == 0) {
            current = -1;
        }
        synchronized (this) {
            int i = this.used;
            long[] jArr = this.elts;
            int i2 = 0;
            if (i >= jArr.length) {
                int length = jArr.length;
                long[] jArr2 = new long[length * 2];
                System.arraycopy(jArr, 0, jArr2, 0, length);
                this.elts = jArr2;
                i2 = length;
            }
            while (true) {
                long[] jArr3 = this.elts;
                if (i2 >= jArr3.length) {
                    return -1;
                }
                if (jArr3[i2] == 0) {
                    jArr3[i2] = current;
                    this.used++;
                    return i2;
                }
                i2++;
            }
        }
    }

    void remove(int i) {
        synchronized (this) {
            this.elts[i] = 0;
            int i2 = this.used - 1;
            this.used = i2;
            if (i2 == 0 && this.waitingToEmpty) {
                notifyAll();
            }
        }
    }

    synchronized void signalAndWait() {
        boolean z = false;
        while (true) {
            int i = this.used;
            if (i <= 0) {
                break;
            }
            int length = this.elts.length;
            for (int i2 = 0; i2 < length; i2++) {
                long j = this.elts[i2];
                if (j != 0) {
                    if (j != -1) {
                        NativeThread.signal(j);
                    }
                    i--;
                    if (i == 0) {
                        break;
                    }
                }
            }
            this.waitingToEmpty = true;
            try {
                wait(50L);
                this.waitingToEmpty = false;
            } catch (InterruptedException unused) {
                this.waitingToEmpty = false;
                z = true;
            } catch (Throwable th) {
                this.waitingToEmpty = false;
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }
}
