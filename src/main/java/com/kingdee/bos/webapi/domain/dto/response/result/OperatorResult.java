package com.kingdee.bos.webapi.domain.dto.response.result;

import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class OperatorResult extends Result {

    @Serial
    private static final long serialVersionUID = 7569777968767096751L;

    public OperatorResult() {
    }

    public OperatorResult(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
