package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * web api 删除操作 请求参数
 *
 * @author xueyu
 */
@Setter
@Getter
public class DeleteRequest extends BillFormId {

    /**
     * 创建者组织内码（非必录）
     */
    @JsonProperty(value = "CreateOrgId")
    private Integer createOrgId;
    /**
     * 单据编码集合，数组类型，格式：[No1,No2,...]（使用编码时必录）
     */
    @JsonProperty(value = "Numbers")
    private List<String> numbers = Collections.emptyList();
    /**
     * 单据内码集合，字符串类型，格式："Id1,Id2,..."（使用内码时必录）
     */
    @JsonProperty(value = "Ids")
    private String ids;
    /**
     * 是否启用网控，布尔类型，默认false（非必录）
     */
    @JsonProperty(value = "NetworkCtrl")
    private Boolean networkCtrl = false;

    public DeleteRequest(String formId) {
        super(formId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createOrgId", createOrgId)
                .append("numbers", numbers)
                .append("ids", ids)
                .append("networkCtrl", networkCtrl)
                .toString();
    }
}


