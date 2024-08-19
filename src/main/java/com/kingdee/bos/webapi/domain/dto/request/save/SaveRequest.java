package com.kingdee.bos.webapi.domain.dto.request.save;

import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.request.BillFormId;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 保存请求
 *
 * @author xueyu
 */
@Getter
@Setter
@JSONType(alphabetic = false)
public class SaveRequest extends BillFormId {

    /**
     * 需要更新的字段，数组类型，格式：[key1,key2,...] （非必录）注（更新字段时Model数据包中必须设置内码，若更新单据体字段还需设置分录内码）
     */
    @JsonProperty(value = "NeedUpDateFields")
    private List<String> needUpDateFields;

    /**
     * 需返回结果的字段集合，数组类型，格式：[key,entitykey.key,...]（非必录） 注（返回单据体字段格式：entitykey.key）
     */
    @JsonProperty(value = "NeedReturnFields")
    private List<String> needReturnFields;

    /**
     * 是否删除已存在的分录，布尔类型，默认true（非必录）
     */
    @JsonProperty(value = "IsDeleteEntry")
    private Boolean isDeleteEntry = true;

    /**
     * 表单所在的子系统内码，字符串类型（非必录）
     */
    @JsonProperty(value = "SubSystemId")
    private String subSystemId;

    /**
     * 是否验证所有的基础资料有效性，布尔类，默认false（非必录）
     */
    @JsonProperty(value = "IsVerifyBaseDataField")
    private Boolean isVerifyBaseDataField = false;

    /**
     * 是否批量填充分录，默认true（非必录）
     */
    @JsonProperty(value = "IsEntryBatchFill")
    private Boolean isEntryBatchFill = true;

    /**
     * 是否验证数据合法性标志，布尔类型，默认true（非必录）注（设为false时不对数据合法性进行校验）
     */
    @JsonProperty(value = "ValidateFlag")
    private Boolean validateFlag = true;

    /**
     * 是否用编码搜索基础资料，布尔类型，默认true（非必录）
     */
    @JsonProperty(value = "NumberSearch")
    private Boolean numberSearch = true;

    /**
     * 是否自动调整JSON字段顺序，布尔类型，默认false（非必录）
     */
    @JsonProperty(value = "IsAutoAdjustField")
    private Boolean isAutoAdjustField = false;

    /**
     * 交互标志集合，字符串类型，分号分隔，格式："flag1;flag2;..."（非必录） 例如（允许负库存标识：STK_InvCheckResult）
     */
    @JsonProperty(value = "InterationFlags")
    private String interationFlags;

    /**
     * 是否允许忽略交互，布尔类型，默认true（非必录）
     */
    @JsonProperty(value = "IgnoreInterationFlag")
    private Boolean ignoreInterationFlag = true;

    /**
     * 是否控制精度，为true时对金额、单价和数量字段进行精度验证，默认false（非必录）
     */
    @JsonProperty(value = "IsControlPrecision")
    private Boolean isControlPrecision = false;

    /**
     * 校验Json数据包是否重复传入，一旦重复传入，接口调用失败，默认false（非必录）
     */
    @JsonProperty(value = "ValidateRepeatJson")
    private Boolean validateRepeatJson = false;

    /**
     * 表单数据包，JSON类型（必录)
     * <p> 非必填并且业务上非必填的字段，尽量不要出现键值对
     * 没必要填写的字段 就不要出现键值对了</p>
     * <p>并且尽量保证字段的顺序</p>
     */
    @JsonProperty(value = "Model")
    private Model model;

    public SaveRequest(String formId) {
        super(formId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("needUpDateFields", needUpDateFields)
                .append("needReturnFields", needReturnFields)
                .append("isDeleteEntry", isDeleteEntry)
                .append("subSystemId", subSystemId)
                .append("isVerifyBaseDataField", isVerifyBaseDataField)
                .append("isEntryBatchFill", isEntryBatchFill)
                .append("validateFlag", validateFlag)
                .append("numberSearch", numberSearch)
                .append("isAutoAdjustField", isAutoAdjustField)
                .append("interationFlags", interationFlags)
                .append("ignoreInterationFlag", ignoreInterationFlag)
                .append("isControlPrecision", isControlPrecision)
                .append("validateRepeatJson", validateRepeatJson)
                .append("model", model)
                .toString();
    }
}
