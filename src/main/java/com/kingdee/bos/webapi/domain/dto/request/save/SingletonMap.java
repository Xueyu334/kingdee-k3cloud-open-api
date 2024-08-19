package com.kingdee.bos.webapi.domain.dto.request.save;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 实现一个只有一个键值对的map 用来存储Number对象
 *
 * @see SingletonMap#FNumberPut(String)
 * @see SingletonMap#FNUMBERPUT(String)
 */
public class SingletonMap extends AbstractMap<String, String> {

    private final String k;

    private final String v;

    private transient Set<String> keySet;

    private transient Set<Entry<String, String>> entrySet;

    private transient Collection<String> values;

    /**
     * 构造函数 构造一个SingletonMap对象
     *
     * @param k key
     * @param v value
     */
    public SingletonMap(String k, String v) {
        this.k = k;
        this.v = v;
    }

    /**
     * 构建一个键名为"FNumber"的对象
     *
     * @param v 值
     * @return {"FNumber":"v"}
     */
    public static SingletonMap FNumberPut(String v) {
        return new SingletonMap("FNumber", v);
    }

    /**
     * 构建一个键名为"FNUMBER"的对象
     *
     * @param v 值
     * @return {"FNUMBER":"v"}
     */
    public static SingletonMap FNUMBERPUT(String v) {
        return new SingletonMap("FNUMBER", v);
    }

    /**
     * 构建一个键名为"FUserID"的对象
     *
     * @param v 值
     * @return <pre>
     * {@code
     * {
     *  "FUserID": 100313
     * }
     * }
     * </pre>
     */
    public static SingletonMap FUserIDPut(String v) {
        return new SingletonMap("FUserID", v);
    }

    private static boolean eq(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    /**
     * 获取该单例Map的值
     *
     * @return 该单例Map的值
     */
    public String getValue() {
        return this.v;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return eq(key, k);
    }

    @Override
    public boolean containsValue(Object value) {
        return eq(value, v);
    }

    @Override
    public String get(Object key) {
        return (eq(key, k) ? v : null);
    }

    @Override
    @Nonnull
    public Set<String> keySet() {
        if (keySet == null) {
            keySet = Collections.singleton(k);
        }
        return keySet;
    }

    @Override
    @Nonnull
    public Set<Entry<String, String>> entrySet() {
        if (entrySet == null) {
            entrySet = Collections.singleton(
                    new SimpleImmutableEntry<>(k, v));
        }
        return entrySet;
    }

    @Override
    @Nonnull
    public Collection<String> values() {
        if (values == null) {
            values = Collections.singleton(v);
        }
        return values;
    }

    // Override default methods in Map
    @Override
    public String getOrDefault(Object key, String defaultValue) {
        return eq(key, k) ? v : defaultValue;
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super String> action) {
        action.accept(k, v);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        throw new UnsupportedOperationException("replaceAll is not supported");
    }

    @Override
    public String putIfAbsent(String key, String value) {
        throw new UnsupportedOperationException("putIfAbsent is not supported");
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException("remove is not supported");
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        throw new UnsupportedOperationException("replace is not supported");
    }

    @Override
    public String replace(String key, String value) {
        throw new UnsupportedOperationException("replace is not supported");
    }

    @Override
    public String computeIfAbsent(String key,
                                  @Nonnull Function<? super String, ? extends String> mappingFunction) {
        throw new UnsupportedOperationException("computeIfAbsent is not supported");
    }

    @Override
    public String computeIfPresent(String key,
                                   @Nonnull BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        throw new UnsupportedOperationException("computeIfAbsent is not supported");
    }

    @Override
    public String compute(String key,
                          @Nonnull BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        throw new UnsupportedOperationException("compute is not supported");
    }

    @Override
    public String merge(String key,
                        @Nonnull String value,
                        @Nonnull BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        throw new UnsupportedOperationException("merge is not supported");
    }
}
