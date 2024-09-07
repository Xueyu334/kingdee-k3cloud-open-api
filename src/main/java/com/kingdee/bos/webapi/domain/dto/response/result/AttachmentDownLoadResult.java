package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDownLoadResult extends Result {

    /**
     * 文件开始下标
     */
    @JsonProperty(value = "StartIndex")
    private Long startIndex;

    /**
     * 是否最后一块
     */
    @JsonProperty(value = "IsLast")
    private Boolean isLast;

    /**
     * 文件大小
     */
    @JsonProperty(value = "FileSize")
    private Long fileSize;

    /**
     * 文件名称
     */
    @JsonProperty(value = "FileName")
    private String fileName;

    /**
     * 文件部分 一个base64字符串
     */
    @JsonProperty(value = "FilePart")
    private String filePart;

    /**
     * 消息
     */
    @JsonProperty(value = "Message")
    private String message;


    public AttachmentDownLoadResult(Long startIndex, Boolean isLast, Long fileSize, String fileName, String filePart, String message) {
        this.startIndex = startIndex;
        this.isLast = isLast;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.filePart = filePart;
        this.message = message;
    }

    public AttachmentDownLoadResult(ResponseStatus responseStatus, Long startIndex, Boolean isLast, Long fileSize, String fileName, String filePart, String message) {
        super(responseStatus);
        this.startIndex = startIndex;
        this.isLast = isLast;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.filePart = filePart;
        this.message = message;
    }

    @Override
    public String toString() {
        return "AttachmentDownLoadResult{" +
                "startIndex=" + startIndex +
                ", isLast=" + isLast +
                ", fileSize=" + fileSize +
                ", fileName='" + fileName + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
