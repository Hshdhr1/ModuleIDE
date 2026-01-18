package java.time;

import java.io.Serializable;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class DesugarClock {
    static final long NANOS_PER_MILLI = 1000000;

    private DesugarClock() {
    }

    public static Clock tickMillis(ZoneId zoneId) {
        return new DesugarTickClock(Clock.system(zoneId), 1000000L);
    }

    static final class DesugarTickClock extends Clock implements Serializable {
        private static final long serialVersionUID = 6504659149906368850L;
        private final Clock baseClock;
        private final long tickNanos;

        DesugarTickClock(Clock clock, long j) {
            this.baseClock = clock;
            this.tickNanos = j;
        }

        public ZoneId getZone() {
            return this.baseClock.getZone();
        }

        public Clock withZone(ZoneId zoneId) {
            return zoneId.equals(this.baseClock.getZone()) ? this : new DesugarTickClock(this.baseClock.withZone(zoneId), this.tickNanos);
        }

        public long millis() {
            long millis = this.baseClock.millis();
            return millis - DesugarClock$DesugarTickClock$$ExternalSyntheticBackport0.m(millis, this.tickNanos / 1000000);
        }

        public Instant instant() {
            if (this.tickNanos % 1000000 == 0) {
                long millis = this.baseClock.millis();
                return Instant.ofEpochMilli(millis - DesugarClock$DesugarTickClock$$ExternalSyntheticBackport0.m(millis, this.tickNanos / 1000000));
            }
            return this.baseClock.instant().minusNanos(DesugarClock$DesugarTickClock$$ExternalSyntheticBackport0.m(r0.getNano(), this.tickNanos));
        }

        public boolean equals(Object obj) {
            if (obj instanceof DesugarTickClock) {
                DesugarTickClock desugarTickClock = (DesugarTickClock) obj;
                if (this.baseClock.equals(desugarTickClock.baseClock) && this.tickNanos == desugarTickClock.tickNanos) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            int hashCode = this.baseClock.hashCode();
            long j = this.tickNanos;
            return hashCode ^ ((int) (j ^ (j >>> 32)));
        }

        public String toString() {
            return "DesugarTickClock[" + this.baseClock + "," + Duration.ofNanos(this.tickNanos) + "]";
        }
    }
}
