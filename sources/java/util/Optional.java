package java.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Optional {
    private static final Optional EMPTY = new Optional();
    private final Object value;

    private Optional() {
        this.value = null;
    }

    public static Optional empty() {
        return EMPTY;
    }

    private Optional(Object obj) {
        obj.getClass();
        this.value = obj;
    }

    public static Optional of(Object obj) {
        return new Optional(obj);
    }

    public static Optional ofNullable(Object obj) {
        return obj == null ? empty() : of(obj);
    }

    public Object get() {
        Object obj = this.value;
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException("No value present");
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public void ifPresent(Consumer consumer) {
        Object obj = this.value;
        if (obj != null) {
            consumer.accept(obj);
        }
    }

    public void ifPresentOrElse(Consumer consumer, Runnable runnable) {
        Object obj = this.value;
        if (obj != null) {
            consumer.accept(obj);
        } else {
            runnable.run();
        }
    }

    public Optional filter(Predicate predicate) {
        predicate.getClass();
        return (isPresent() && !predicate.test(this.value)) ? empty() : this;
    }

    public Optional map(Function function) {
        function.getClass();
        if (!isPresent()) {
            return empty();
        }
        return ofNullable(function.apply(this.value));
    }

    public Optional flatMap(Function function) {
        function.getClass();
        if (!isPresent()) {
            return empty();
        }
        Optional optional = (Optional) function.apply(this.value);
        optional.getClass();
        return optional;
    }

    public Optional or(Supplier supplier) {
        supplier.getClass();
        if (isPresent()) {
            return this;
        }
        Optional optional = (Optional) supplier.get();
        optional.getClass();
        return optional;
    }

    public Stream stream() {
        if (!isPresent()) {
            return Stream.-CC.empty();
        }
        return Stream.-CC.of(this.value);
    }

    public Object orElse(Object obj) {
        Object obj2 = this.value;
        return obj2 != null ? obj2 : obj;
    }

    public Object orElseGet(Supplier supplier) {
        Object obj = this.value;
        return obj != null ? obj : supplier.get();
    }

    public Object orElseThrow() {
        Object obj = this.value;
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException("No value present");
    }

    public Object orElseThrow(Supplier supplier) throws Throwable {
        Object obj = this.value;
        if (obj != null) {
            return obj;
        }
        throw ((Throwable) supplier.get());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Optional) {
            return Optional$$ExternalSyntheticBackport0.m(this.value, ((Optional) obj).value);
        }
        return false;
    }

    public int hashCode() {
        return Optional$$ExternalSyntheticBackport1.m(this.value);
    }

    public String toString() {
        Object obj = this.value;
        return obj != null ? String.format("Optional[%s]", obj) : "Optional.empty";
    }
}
