package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Getter
@Setter
public class SubmitRequest extends BillFormId {

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
     * 工作流发起员工岗位内码，整型（非必录） 注（员工身兼多岗时不传参默认取第一个岗位）
     */
    @JsonProperty(value = "SelectedPostId")
    private Integer selectedPostId;

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

    public SubmitRequest(String formId) {
        super(formId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createOrgId", createOrgId)
                .append("numbers", numbers)
                .append("ids", ids)
                .append("selectedPostId", selectedPostId)
                .append("useOrgId", useOrgId)
                .append("networkCtrl", networkCtrl)
                .append("ignoreInterationFlag", ignoreInterationFlag)
                .toString();
    }
}
