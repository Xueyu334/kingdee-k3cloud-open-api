package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 整单反关闭
 *
 * @author xueyu
 */
@Getter
@Setter
public class BillUnCloseRequest extends BillFormId {

    /**
     * 创建者组织内码（非必录）
     */
    @JsonProperty(value = "CreateOrgId")
    private Integer createOrgId;

    /**
     * 单据编码集合，数组类型，格式：[No1,No2,...]（使用编码时必录）
     */
    @JsonProperty(value = "Numbers")
    private List<String> numbers;

    /**
     * 单据内码集合，字符串类型，格式："Id1,Id2,..."（使用内码时必录）
     */
    @JsonProperty(value = "Ids")
    private String ids;

    /**
     * 单据内码与分录内码对应关系的集合，字符串类型，格式：[{"Id":"Id1","EntryIds":"EntryId1,EntryId2,..."}] (使用分录状态转换时必录)
     */
    @JsonProperty(value = "PkEntryIds")
    private List<String> pkEntryIds;

    /**
     * 使用者组织内码（非必录）
     */
    @JsonProperty(value = "UseOrgId")
    private Integer useOrgId;


    /**
     * 是否启用网控，布尔类型，默认false（非必录）
     */
    @JsonProperty(value = "NetworkCtrl")
    private Boolean networkCtrl = false;

    /**
     * 是否允许忽略交互，布尔类型，默认true（非必录）
     */
    @JsonProperty(value = "IgnoreInterationFlag")
    private Boolean ignoreInterationFlag = true;


    public BillUnCloseRequest(String formId) {
        super(formId);
    }
}
