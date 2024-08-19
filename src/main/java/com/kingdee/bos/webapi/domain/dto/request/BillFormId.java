package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 顶级抽象类
 * 用于存储表示单据的FormId
 *
 * @author xueyu
 */
public abstract class BillFormId {

    /**
     * 单据的FormId
     * 提交 审核 保存等方式需要使用该字段
     */
    @JsonIgnore
    private final String formId;

    public BillFormId(String formId) {
        this.formId = formId;
    }

    @JsonIgnore
    public String getFormId() {
        return formId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("formId", formId)
                .toString();
    }
}
