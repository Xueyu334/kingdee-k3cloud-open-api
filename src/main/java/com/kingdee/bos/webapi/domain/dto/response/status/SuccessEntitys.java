package com.kingdee.bos.webapi.domain.dto.response.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 成功列表
 *
 * @author xueyu
 */
@Getter
@Setter
public class SuccessEntitys {

    /**
     * 原始数据行号
     */
    @JsonProperty(value = "DIndex")
    private Integer dIndex;

    /**
     * 内码
     */
    @JsonProperty(value = "Id")
    private String id;
    /**
     * 编码
     */
    @JsonProperty(value = "Number")
    private String number;

    public SuccessEntitys() {
    }

    public SuccessEntitys(Integer dIndex, String id, String number) {
        this.dIndex = dIndex;
        this.id = id;
        this.number = number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dIndex", dIndex)
                .append("id", id)
                .append("number", number)
                .toString();
    }

}
