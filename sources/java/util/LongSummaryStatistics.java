package java.util;

import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class LongSummaryStatistics implements LongConsumer, IntConsumer {
    private long count;
    private long max;
    private long min;
    private long sum;

    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer.-CC.$default$andThen(this, intConsumer);
    }

    public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return LongConsumer.-CC.$default$andThen(this, longConsumer);
    }

    public LongSummaryStatistics() {
        this.min = Long.MAX_VALUE;
        this.max = Long.MIN_VALUE;
    }

    public LongSummaryStatistics(long j, long j2, long j3, long j4) throws IllegalArgumentException {
        this.min = Long.MAX_VALUE;
        this.max = Long.MIN_VALUE;
        if (j < 0) {
            throw new IllegalArgumentException("Negative count value");
        }
        if (j > 0) {
            if (j2 > j3) {
                throw new IllegalArgumentException("Minimum greater than maximum");
            }
            this.count = j;
            this.sum = j4;
            this.min = j2;
            this.max = j3;
        }
    }

    public void accept(int i) {
        accept(i);
    }

    public void accept(long j) {
        this.count++;
        this.sum += j;
        this.min = Math.min(this.min, j);
        this.max = Math.max(this.max, j);
    }

    public void combine(LongSummaryStatistics longSummaryStatistics) {
        this.count += longSummaryStatistics.count;
        this.sum += longSummaryStatistics.sum;
        this.min = Math.min(this.min, longSummaryStatistics.min);
        this.max = Math.max(this.max, longSummaryStatistics.max);
    }

    public final long getCount() {
        return this.count;
    }

    public final long getSum() {
        return this.sum;
    }

    public final long getMin() {
        return this.min;
    }

    public final long getMax() {
        return this.max;
    }

    public final double getAverage() {
        if (getCount() <= 0) {
            return 0.0d;
        }
        double sum = getSum();
        double count = getCount();
        Double.isNaN(sum);
        Double.isNaN(count);
        return sum / count;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", getClass().getSimpleName(), Long.valueOf(getCount()), Long.valueOf(getSum()), Long.valueOf(getMin()), Double.valueOf(getAverage()), Long.valueOf(getMax()));
    }
}
