package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;

/**
 * 保存或者暂存的操作结果
 *
 * @author xueyu
 */
@Getter
@Setter
public class SaveResult extends BatchSaveResult {

    @Serial
    private static final long serialVersionUID = 4648412583913073449L;

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

    public SaveResult() {
    }

    public SaveResult(String id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("number", number)
                .append("needReturnData", needReturnData)
                .append("responseStatus", responseStatus)
                .toString();
    }
}
