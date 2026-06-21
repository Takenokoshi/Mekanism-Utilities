package com.takenokoshi.mekut.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import org.jetbrains.annotations.NotNull;

import mekanism.api.functions.TriConsumer;
import net.minecraft.data.models.blockstates.PropertyDispatch.TriFunction;

public class CacheTable<KEY1, KEY2, VALUE> {
    private final Map<KEY1, Map<KEY2, VALUE>> tableA = new HashMap<>();
    private final Map<KEY2, Map<KEY1, VALUE>> tableB = new HashMap<>();

    public VALUE getOrDefault(KEY1 key1, KEY2 key2, VALUE def) {
        Map<KEY2, VALUE> map = tableA.get(key1);
        return map == null ? def : map.getOrDefault(key2, def);
    }

    public VALUE get(KEY1 key1, KEY2 key2) {
        return getOrDefault(key1, key2, null);
    }

    public void put(KEY1 key1, KEY2 key2, @NotNull VALUE value) {
        tableA.computeIfAbsent(key1, k -> new HashMap<>())
                .put(key2, value);
        tableB.computeIfAbsent(key2, k -> new HashMap<>())
                .put(key1, value);
    }

    public void putKey2Map(KEY1 key1, Map<KEY2, VALUE> values) {
        tableA.computeIfAbsent(key1, k -> new HashMap<>())
                .putAll(values);
        values.forEach((key2, value) -> tableB.computeIfAbsent(key2, k -> new HashMap<>())
                .put(key1, value));
    }

    public void putKey1Map(KEY2 key2, Map<KEY1, VALUE> values) {
        values.forEach((key1, value) -> tableA.computeIfAbsent(key1, k -> new HashMap<>())
                .put(key2, value));
        tableB.computeIfAbsent(key2, k -> new HashMap<>())
                .putAll(values);
    }

    public boolean containsKey1(KEY1 key1) {
        return tableA.containsKey(key1);
    }

    public Map<KEY2, VALUE> getMapOfKey1(KEY1 key1) {
        Map<KEY2, VALUE> v = tableA.getOrDefault(key1, null);
        // Modify v affects only tableA,cannot affect tableB
        return v == null ? new HashMap<>() : new HashMap<>(v);
    }

    public boolean containsKey2(KEY2 key2) {
        return tableB.containsKey(key2);
    }

    public Map<KEY1, VALUE> getMapOfKey2(KEY2 key2) {
        Map<KEY1, VALUE> v = tableB.getOrDefault(key2, null);
        // Modify v affects only tableB,cannot affect tableA
        return v == null ? new HashMap<>() : new HashMap<>(v);
    }

    public boolean containsKeys(KEY1 key1, KEY2 key2) {
        Map<KEY2, VALUE> map = tableA.get(key1);
        return map != null && map.containsKey(key2);
    }

    public void forEach(TriConsumer<KEY1, KEY2, VALUE> run) {
        tableA.forEach((key1, map) -> map.forEach((key2, value) -> run.accept(key1, key2, value)));
    }

    public void remove(KEY1 key1, KEY2 key2) {
        tableA.computeIfPresent(key1, (k, map) -> {
            map.remove(key2);
            if (map.isEmpty()) {
                return null;
            }
            return map;
        });
        tableB.computeIfPresent(key2, (k, map) -> {
            map.remove(key1);
            if (map.isEmpty()) {
                return null;
            }
            return map;
        });
    }

    public void clear() {
        tableA.clear();
        tableB.clear();
    }

    public void computeIfAbsent(KEY1 key1, KEY2 key2, BiFunction<KEY1, KEY2, VALUE> mappingFunction) {
        if (get(key1, key2) == null) {
            VALUE value = mappingFunction.apply(key1, key2);
            if (value != null) {
                put(key1, key2, value);
            }
        }
    }

    public void computeIfPresent(KEY1 key1, KEY2 key2, TriFunction<KEY1, KEY2, VALUE, VALUE> remappingFunction) {
        VALUE value = get(key1, key2);
        if (value != null) {
            value = remappingFunction.apply(key1, key2, value);
            if (value == null) {
                remove(key1, key2);
            } else {
                put(key1, key2, value);
            }
        }
    }

    public void merge(KEY1 key1, KEY2 key2, VALUE value, BinaryOperator<VALUE> remappingFunction) {
        VALUE oldValue = get(key1, key2);
        if (oldValue == null) {
            if (value == null) {
                remove(key1, key2);
            } else {
                put(key1, key2, value);
            }
        } else {
            VALUE newValue = remappingFunction.apply(oldValue, value);
            if (newValue == null) {
                remove(key1, key2);
            } else {
                put(key1, key2, newValue);
            }
        }
    }
}
