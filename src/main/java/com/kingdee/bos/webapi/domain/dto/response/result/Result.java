package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 结果基类
 *
 * @author xueyu
 */

@Setter
@Getter
public class Result implements Serializable {

    @Serial
    private static final long serialVersionUID = -3943769207522678105L;

    /**
     * 返回结果信息
     */
    @JsonProperty(value = "ResponseStatus")
    protected ResponseStatus responseStatus;

    public Result() {
    }

    public Result(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("responseStatus", responseStatus)
                .toString();
    }
}
