package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 附件下载请求参数
 *
 * @author xueyu
 */
@Getter
@Setter
public class AttachmentDownLoadRequest {

    /**
     * 文件id
     */
    @JsonProperty(value = "FileId")
    private String fileId;

    /**
     * 下载起始位置 第一次为0
     */
    @JsonProperty(value = "StartIndex")
    private Long startIndex;

    public AttachmentDownLoadRequest() {
    }

    public AttachmentDownLoadRequest(String fileId, Long startIndex) {
        this.fileId = fileId;
        this.startIndex = startIndex;
    }

    @Override
    public String toString() {
        return "AttachmentDownLoadRequest{" +
                "fileId='" + fileId + '\'' +
                ", startIndex=" + startIndex +
                '}';
    }
}
