package com.kingdee.bos.webapi.domain.dto.request.save;

import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * 键值对类型的 Model 数据类型,用于动态构建金蝶云星空 API 请求数据
 *
 * <p>继承 {@link LinkedHashMap} 以保证插入顺序,这对于金蝶 API 字段顺序敏感的场景非常重要。</p>
 *
 * <h3>使用示例:</h3>
 * <pre>{@code
 * // 方式1: 传统方式
 * ModelMap<String, Object> map = new ModelMap<>();
 * map.put("FNumber", "test001");
 * map.put("FName", "测试物料");
 *
 * // 方式2: 静态工厂方法
 * ModelMap<String, Object> map = ModelMap.of();
 *
 * // 方式3: 链式调用(推荐)
 * ModelMap<String, Object> map = ModelMap.<String, Object>of()
 *     .with("FNumber", "test001")
 *     .with("FName", "测试物料")
 *     .with("FDetailEntity", detailList);
 * }</pre>
 *
 * @param <K> 键的类型,通常为 String
 * @param <V> 值的类型,通常为 Object
 * @author xueyu
 */
public class ModelMap<K, V> extends LinkedHashMap<K, V> implements Model {

    @Serial
    private static final long serialVersionUID = 2336557478389154316L;

    /**
     * 构造一个空的 ModelMap 实例。
     * 此构造函数创建一个默认初始容量和负载因子的 ModelMap。
     * ModelMap 是用于动态构建金蝶云星空 API 数据模型的 Map 实现，通常作为 SaveRequest 中 Model 接口的动态配置方式使用。
     * 它继承自 LinkedHashMap，以保持键值对的插入顺序，确保 JSON 序列化时字段顺序可控。
     * 建议在简单场景下使用 ModelMap 动态配置数据，复杂业务场景则推荐实现强类型的 Model 接口。
     */
    public ModelMap() {
    }

    /**
     * 构造一个指定初始容量的 ModelMap
     *
     * @param initialCapacity 初始容量
     */
    public ModelMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 构造一个指定初始容量和负载因子的 ModelMap
     *
     * @param initialCapacity 初始容量
     * @param loadFactor      负载因子
     */
    public ModelMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * 创建一个空的 ModelMap 实例
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 新的 ModelMap 实例
     */
    public static <K, V> ModelMap<K, V> of() {
        return new ModelMap<>();
    }

    /**
     * 创建包含单个键值对的 ModelMap
     *
     * @param k1  第一个键
     * @param v1  第一个值
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 包含一个键值对的 ModelMap 实例
     */
    public static <K, V> ModelMap<K, V> of(K k1, V v1) {
        ModelMap<K, V> map = new ModelMap<>();
        map.put(k1, v1);
        return map;
    }

    /**
     * 创建包含两个键值对的 ModelMap
     *
     * @param k1  第一个键
     * @param v1  第一个值
     * @param k2  第二个键
     * @param v2  第二个值
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 包含两个键值对的 ModelMap 实例
     */
    public static <K, V> ModelMap<K, V> of(K k1, V v1, K k2, V v2) {
        ModelMap<K, V> map = new ModelMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    /**
     * 创建包含三个键值对的 ModelMap
     *
     * @param k1  第一个键
     * @param v1  第一个值
     * @param k2  第二个键
     * @param v2  第二个值
     * @param k3  第三个键
     * @param v3  第三个值
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 包含三个键值对的 ModelMap 实例
     */
    public static <K, V> ModelMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        ModelMap<K, V> map = new ModelMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    /**
     * 创建一个默认的 DefaultModelMap 实例。
     * <p>
     * 此方法返回一个新的 DefaultModelMap，用于动态构建金蝶云星空 API 请求中的模型数据。
     * DefaultModelMap 是一种扩展了 ModelMap 的具体实现，提供了更加便捷的操作方式，适用于需要动态构建数据模型的场景。
     *
     * @return 一个新的 DefaultModelMap 实例
     */
    public static DefaultModelMap defaultModelMap() {
        return new DefaultModelMap();
    }

    /**
     * 链式添加键值对
     *
     * <p>示例:</p>
     * <pre>{@code
     * ModelMap<String, Object> map = ModelMap.<String, Object>of()
     *     .with("FNumber", "test001")
     *     .with("FName", "测试物料");
     * }</pre>
     *
     * @param key   键
     * @param value 值
     * @return 当前 ModelMap 实例,支持链式调用
     */
    public ModelMap<K, V> with(K key, V value) {
        put(key, value);
        return this;
    }

    /**
     * 条件性添加键值对,仅当值不为 null 时才添加
     *
     * @param key   键
     * @param value 值
     * @return 当前 ModelMap 实例,支持链式调用
     */
    public ModelMap<K, V> withIfNotNull(K key, V value) {
        if (Objects.nonNull(value)) {
            put(key, value);
        }
        return this;
    }

}
