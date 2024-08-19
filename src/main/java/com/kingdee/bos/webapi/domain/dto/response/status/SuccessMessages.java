package com.kingdee.bos.webapi.domain.dto.response.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 提示信息
 */
@Getter
@Setter
public class SuccessMessages {

    /**
     * 原始数据行号
     */
    @JsonProperty(value = "DIndex")
    private Integer dIndex;

    /**
     * 字段名称
     */
    @JsonProperty(value = "FieldName")
    private String fieldName;

    /**
     * 消息内容
     */
    @JsonProperty(value = "Message")
    private String message;

    public SuccessMessages() {
    }

    public SuccessMessages(Integer dIndex, String fieldName, String message) {
        this.dIndex = dIndex;
        this.fieldName = fieldName;
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dIndex", dIndex)
                .append("fieldName", fieldName)
                .append("message", message)
                .toString();
    }
}
