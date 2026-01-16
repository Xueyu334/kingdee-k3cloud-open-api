package com.rain.example;

import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.request.save.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 物料主数据 Model 示例
 *
 * <p>此类展示如何创建强类型的 Model 实现类,用于金蝶云星空物料保存场景。</p>
 *
 * <h3>使用示例:</h3>
 * <pre>{@code
 * // 创建物料对象
 * MaterialModel material = new MaterialModel();
 * material.setNumber("MAT001");
 * material.setName("测试物料");
 * material.setSpecification("规格A");
 *
 * // 添加物料分类
 * MaterialGroupModel group = new MaterialGroupModel();
 * group.setNumber("CLS001");
 * material.setMaterialGroup(group);
 *
 * // 添加辅助属性
 * List<MaterialAuxPropertyModel> auxProperties = new ArrayList<>();
 * MaterialAuxPropertyModel auxProp = new MaterialAuxPropertyModel();
 * auxProp.setPropertyId("AUX001");
 * auxProp.setPropertyValue("属性值");
 * auxProperties.add(auxProp);
 * material.setAuxProperties(auxProperties);
 *
 * // 使用到 SaveRequest
 * SaveRequest request = new SaveRequest("BD_MATERIAL");
 * request.setModel(material);
 * }</pre>
 *
 * @author xueyu
 */
@Getter
@Setter
@JSONType(alphabetic = false)
public class MaterialModel implements Model {

    /**
     * 物料编码 (必填)
     */
    @JsonProperty("FNumber")
    private String number;

    /**
     * 物料名称 (必填)
     */
    @JsonProperty("FName")
    private String name;

    /**
     * 规格型号
     */
    @JsonProperty("FSpecification")
    private String specification;

    /**
     * 物料分类 (基础资料字段)
     */
    @JsonProperty("FMaterialGroup")
    private MaterialGroupModel materialGroup;

    /**
     * 辅助属性集合 (单据体字段)
     */
    @JsonProperty("FAuxPropertyEntity")
    private List<MaterialAuxPropertyModel> auxProperties;

    /**
     * 计量单位 (基础资料字段)
     */
    @JsonProperty("FBaseUnitId")
    private UnitModel baseUnit;

    /**
     * 是否启用
     */
    @JsonProperty("FUseOrgId")
    private UseOrgModel useOrg;

    /**
     * 创建组织
     */
    @JsonProperty("FCreateOrgId")
    private CreateOrgModel createOrg;

    /**
     * 物料分类内部类 - 展示基础资料字段的典型结构
     */
    @Getter
    @Setter
    @JSONType(alphabetic = false)
    public static class MaterialGroupModel {
        /**
         * 物料分类编码
         */
        @JsonProperty("FNumber")
        private String number;
    }

    /**
     * 辅助属性内部类 - 展示单据体字段的典型结构
     */
    @Getter
    @Setter
    @JSONType(alphabetic = false)
    public static class MaterialAuxPropertyModel {
        /**
         * 辅助属性ID
         */
        @JsonProperty("FAuxPropertyId")
        private String propertyId;

        /**
         * 辅助属性值
         */
        @JsonProperty("FAuxPropertyValue")
        private String propertyValue;
    }

    /**
     * 计量单位内部类
     */
    @Getter
    @Setter
    @JSONType(alphabetic = false)
    public static class UnitModel {
        /**
         * 计量单位编码
         */
        @JsonProperty("FNumber")
        private String number;
    }

    /**
     * 使用组织内部类
     */
    @Getter
    @Setter
    @JSONType(alphabetic = false)
    public static class UseOrgModel {
        /**
         * 组织编码
         */
        @JsonProperty("FNumber")
        private String number;
    }

    /**
     * 创建组织内部类
     */
    @Getter
    @Setter
    @JSONType(alphabetic = false)
    public static class CreateOrgModel {
        /**
         * 组织编码
         */
        @JsonProperty("FNumber")
        private String number;
    }
}
