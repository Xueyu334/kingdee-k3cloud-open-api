package com.kingdee.bos.webapi.domain.dto.request.save;

/**
 * 默认模型映射类。
 * <p>
 * 继承自 {@code ModelMap<String, Object>}, 提供更具体的类型定义，简化动态构建数据的操作。
 * {@code DefaultModelMap} 通常用于保存金蝶云星空 API 数据请求中的模型数据。
 * <p>
 * 特性:
 * 1. 基于 {@code LinkedHashMap<String, Object>} 实现，保证键值对的插入顺序。
 * 2. 支持动态扩展数据，以满足复杂的金蝶 API 请求模型需求。
 * 3. 对于简单场景，使用键值对形式快速构建数据。
 */
public class DefaultModelMap extends ModelMap<String, Object> {


    /**
     * 默认构造方法，用于创建一个空的 {@code DefaultModelMap} 实例。
     * <p>
     * 该构造方法调用父类 {@code ModelMap} 的无参构造函数，生成一个具有默认初始容量和负载因子的空模型映射。
     * 可用于动态构建金蝶云星空 API 请求中的模型数据。
     */
    public DefaultModelMap() {
        super();
    }


    /**
     * 构造一个带有指定初始容量的 DefaultModelMap 实例。
     *
     * @param initialCapacity 初始容量，用于指定映射的初始存储空间大小。
     */
    public DefaultModelMap(int initialCapacity) {
        super(initialCapacity);
    }


    /**
     * 构造一个带有指定初始容量和负载因子的 DefaultModelMap 实例。
     *
     * @param initialCapacity 映射的初始存储空间大小。
     * @param loadFactor      负载因子，用于控制映射在扩展存储空间前的填充程度。
     */
    public DefaultModelMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
}
