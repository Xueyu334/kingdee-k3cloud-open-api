package com.kingdee.bos.webapi.domain.dto.response.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 错误列表
 *
 * @author xueyu
 */
@Setter
@Getter
public class Errors {

    /**
     * 原始数据行号
     */
    @JsonProperty(value = "DIndex")
    private int dIndex;

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

    public Errors() {
    }

    public Errors(int dIndex, String fieldName, String message) {
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
