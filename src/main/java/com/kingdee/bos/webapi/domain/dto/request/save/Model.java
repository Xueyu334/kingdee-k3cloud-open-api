package com.kingdee.bos.webapi.domain.dto.request.save;

import com.alibaba.fastjson2.annotation.JSONType;

/**
 * 保存/暂存数据时的 Model 标记接口
 *
 * <p>此接口是金蝶云星空 API 数据模型的顶级抽象,支持两种使用方式:</p>
 *
 * <h3>使用方式一: 动态配置(推荐用于简单场景)</h3>
 * <pre>{@code
 * SaveRequest saveRequest = new SaveRequest("BD_MATERIAL");
 * ModelMap<String, Object> modelMap = new ModelMap<>();
 * modelMap.put("FNumber", "test001");
 * modelMap.put("FName", "测试物料001");
 * saveRequest.setModel(modelMap);
 * }</pre>
 *
 * <h3>使用方式二: 强类型实体类(推荐用于复杂业务)</h3>
 * <pre>{@code
 * public class MaterialModel implements Model {
 *     @JsonProperty("FNumber")
 *     private String number;
 *
 *     @JsonProperty("FName")
 *     private String name;
 *     // ... getters/setters
 * }
 *
 * SaveRequest saveRequest = new SaveRequest("BD_MATERIAL");
 * MaterialModel material = new MaterialModel();
 * material.setNumber("test001");
 * material.setName("测试物料001");
 * saveRequest.setModel(material);
 * }</pre>
 *
 * <p><strong>注意事项:</strong></p>
 * <ul>
 *   <li>使用 {@code @JSONType(alphabetic = false)} 确保 JSON 序列化时字段顺序可控</li>
 *   <li>字段命名需与金蝶云星空 API 文档保持一致</li>
 *   <li>非必填字段建议不要出现在 JSON 中,以提高性能</li>
 * </ul>
 *
 * @see ModelMap 动态配置实现
 * @see SaveRequest#setModel(Model)
 * @author xueyu
 */
@JSONType(alphabetic = false)
public interface Model {

}
