package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentUploadResult extends Result {

    /**
     * 上传成功后的附件id
     */
    @JsonProperty(value = "FileId")
    private String fileId;

    /**
     * 消息
     */
    @JsonProperty(value = "Message")
    private String message;

    public AttachmentUploadResult() {
    }

    public AttachmentUploadResult(String fileId, String message) {
        this.fileId = fileId;
        this.message = message;
    }

    public AttachmentUploadResult(ResponseStatus responseStatus, String fileId, String message) {
        super(responseStatus);
        this.fileId = fileId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "AttachmentUploadResult{" +
                "responseStatus=" + responseStatus +
                ", message='" + message + '\'' +
                ", fileId='" + fileId + '\'' +
                "} " + super.toString();
    }
}
