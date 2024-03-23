package api.сache;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Cache {
    public static final ConcurrentHashMap<UUID, AtomicLong> cache = new ConcurrentHashMap<>();

    public static void putIfAbsent(UUID valletId, AtomicLong amount) {
        cache.putIfAbsent(valletId, amount);
    }

    public static long get(UUID valletId) {
        return cache.get(valletId).get();
    }

    public static long addAndGet(UUID valletId, long amount, boolean operationType) {
        if (operationType) {
            return cache.get(valletId).addAndGet(amount);
        } else {
            return cache.get(valletId).getAndAdd(-amount);
        }
    }

    public static void remove(UUID valletId) {
        cache.remove(valletId);
    }
}
