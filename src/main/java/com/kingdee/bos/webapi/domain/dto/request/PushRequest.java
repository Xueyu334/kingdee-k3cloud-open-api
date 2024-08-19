package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 下推参数信息
 *
 * @author xueyu
 */
@Getter
@Setter
public class PushRequest extends BillFormId {

    /**
     * 单据内码集合，字符串类型，格式："Id1,Id2,..."（使用内码时必录）
     */
    @JsonProperty(value = "Ids")
    private String ids;

    /**
     * 据编码集合，数组类型，格式：[No1,No2,...]（使用编码时必录）
     */
    @JsonProperty(value = "Numbers")
    private List<String> numbers;

    /**
     * 分录内码集合，逗号分隔（分录下推时必录） 注（按分录下推时，单据内码和编码不需要填,否则按整单下推）
     */
    @JsonProperty(value = "EntryIds")
    private String entryIds;

    /**
     * 转换规则内码，字符串类型（未启用默认转换规则时，则必录）
     */
    @JsonProperty(value = "RuleId")
    private String ruleId;

    /**
     * 目标单据类型内码，字符串类型（非必录）
     */
    @JsonProperty(value = "TargetBillTypeId")
    private String targetBillTypeId;

    /**
     * 目标组织内码，整型（非必录）
     */
    @JsonProperty(value = "TargetOrgId")
    private String targetOrgId;

    /**
     * 目标单据FormId，字符串类型，（启用默认转换规则时，则必录）
     */
    @JsonProperty(value = "TargetFormId")
    private String targetFormId;

    /**
     * 是否启用默认转换规则，布尔类型，默认false（非必录）
     */
    @JsonProperty(value = "IsEnableDefaultRule")
    private Boolean isEnableDefaultRule = false;

    /**
     * 保存失败时是否暂存，布尔类型，默认false（非必录） 注（暂存的单据是没有编码的）
     */
    @JsonProperty(value = "IsDraftWhenSaveFail")
    private Boolean isDraftWhenSaveFail = false;

    /**
     * 自定义参数，字典类型，格式："{key1:value1,key2:value2,...}"（非必录） 注（传到转换插件的操作选项中，平台不会解析里面的值）
     */
    @JsonProperty(value = "CustomParams")
    private Map<String, Object> customParams;


    public PushRequest(String formId) {
        super(formId);
    }
}
