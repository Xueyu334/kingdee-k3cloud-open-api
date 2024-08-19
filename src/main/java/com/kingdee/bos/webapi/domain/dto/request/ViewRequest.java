package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
public class ViewRequest extends BillFormId {

    /**
     * 创建者组织内码（非必录）
     */
    @JsonProperty(value = "CreateOrgId")
    private Integer createOrgId;

    /**
     * 单据编码，字符串类型（使用编码时必录）
     */
    @JsonProperty(value = "Number")
    private String number;
    /**
     * 表单内码（使用内码时必录）
     */
    @JsonProperty(value = "Id")
    private String id;
    /**
     * 单据体是否按序号排序，默认false
     */
    @JsonProperty(value = "IsSortBySeq")
    private Boolean isSortBySeq = false;

    public ViewRequest(String formId) {
        super(formId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createOrgId", createOrgId)
                .append("number", number)
                .append("id", id)
                .append("isSortBySeq", isSortBySeq)
                .toString();
    }
}
