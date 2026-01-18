package java.util;

import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DoubleSummaryStatistics implements DoubleConsumer {
    private long count;
    private double max;
    private double min;
    private double simpleSum;
    private double sum;
    private double sumCompensation;

    public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
    }

    public DoubleSummaryStatistics() {
        this.min = Double.POSITIVE_INFINITY;
        this.max = Double.NEGATIVE_INFINITY;
    }

    public DoubleSummaryStatistics(long j, double d, double d2, double d3) throws IllegalArgumentException {
        this.min = Double.POSITIVE_INFINITY;
        this.max = Double.NEGATIVE_INFINITY;
        if (j < 0) {
            throw new IllegalArgumentException("Negative count value");
        }
        if (j > 0) {
            if (d > d2) {
                throw new IllegalArgumentException("Minimum greater than maximum");
            }
            long count = DoubleStream.-CC.of(d, d2, d3).filter(new DoubleSummaryStatistics$$ExternalSyntheticLambda0()).count();
            if (count > 0 && count < 3) {
                throw new IllegalArgumentException("Some, not all, of the minimum, maximum, or sum is NaN");
            }
            this.count = j;
            this.sum = d3;
            this.simpleSum = d3;
            this.sumCompensation = 0.0d;
            this.min = d;
            this.max = d2;
        }
    }

    public void accept(double d) {
        this.count++;
        this.simpleSum += d;
        sumWithCompensation(d);
        this.min = Math.min(this.min, d);
        this.max = Math.max(this.max, d);
    }

    public void combine(DoubleSummaryStatistics doubleSummaryStatistics) {
        this.count += doubleSummaryStatistics.count;
        this.simpleSum += doubleSummaryStatistics.simpleSum;
        sumWithCompensation(doubleSummaryStatistics.sum);
        sumWithCompensation(doubleSummaryStatistics.sumCompensation);
        this.min = Math.min(this.min, doubleSummaryStatistics.min);
        this.max = Math.max(this.max, doubleSummaryStatistics.max);
    }

    private void sumWithCompensation(double d) {
        double d2 = d - this.sumCompensation;
        double d3 = this.sum;
        double d4 = d3 + d2;
        this.sumCompensation = (d4 - d3) - d2;
        this.sum = d4;
    }

    public final long getCount() {
        return this.count;
    }

    public final double getSum() {
        double d = this.sum + this.sumCompensation;
        return (Double.isNaN(d) && Double.isInfinite(this.simpleSum)) ? this.simpleSum : d;
    }

    public final double getMin() {
        return this.min;
    }

    public final double getMax() {
        return this.max;
    }

    public final double getAverage() {
        if (getCount() <= 0) {
            return 0.0d;
        }
        double sum = getSum();
        double count = getCount();
        Double.isNaN(count);
        return sum / count;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%f, min=%f, average=%f, max=%f}", getClass().getSimpleName(), Long.valueOf(getCount()), Double.valueOf(getSum()), Double.valueOf(getMin()), Double.valueOf(getAverage()), Double.valueOf(getMax()));
    }
}
