package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Getter
@Setter
public class AuditRequest extends BillFormId {

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
     * 交互标志集合，字符串类型，分号分隔，格式："flag1;flag2;..."（非必录） 例如（允许负库存标识：STK_InvCheckResult）
     */
    @JsonProperty(value = "InterationFlags")
    private String interationFlags;

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
     * 是否检验单据关联运行中的工作流实例，布尔类型，默认true（非必录）
     */
    @JsonProperty(value = "IsVerifyProcInst")
    private Boolean isVerifyProcInst = true;

    /**
     * 是否允许忽略交互，布尔类型，默认true（非必录）
     */
    @JsonProperty(value = "IgnoreInterationFlag")
    private Boolean ignoreInterationFlag = true;
    /**
     * 是否应用单据参数设置分批处理，默认false
     */
    @JsonProperty(value = "UseBatControlTimes")
    private Boolean useBatControlTimes = false;

    public AuditRequest(String formId) {
        super(formId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createOrgId", createOrgId)
                .append("numbers", numbers)
                .append("ids", ids)
                .append("interationFlags", interationFlags)
                .append("useOrgId", useOrgId)
                .append("networkCtrl", networkCtrl)
                .append("isVerifyProcInst", isVerifyProcInst)
                .append("ignoreInterationFlag", ignoreInterationFlag)
                .append("useBatControlTimes", useBatControlTimes)
                .toString();
    }
}
