package com.kingdee.bos.webapi.domain.dto.request.save;

import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 表示一个模型接口，主要用于动态构建和序列化数据。
 * <p>
 * 此接口通常用于定义数据结构的基类，支持通过实现或扩展该接口
 * 来表达金蝶云星空平台的 API 数据模型。
 * <p>
 * 特性：
 * 1. **JSON序列化顺序控制**：通过注解 `@JSONType` 和 `@JsonPropertyOrder`
 * 指定字段序列化顺序，确保数据在序列化和反序列化时
 * 保持字段顺序一致。
 * 2. **可扩展性**：允许根据业务需求扩展具体的数据模型，
 * 提供灵活的接口定义。
 * <p>
 * 推荐使用场景：
 * - 业务对象的基础抽象，例如物料、单位、客户等。
 * - 动态定义的数据结构场景，如通过 `ModelMap` 配置数据。
 */
@JSONType(alphabetic = false)
@JsonPropertyOrder(alphabetic = false)
public interface Model {


}
