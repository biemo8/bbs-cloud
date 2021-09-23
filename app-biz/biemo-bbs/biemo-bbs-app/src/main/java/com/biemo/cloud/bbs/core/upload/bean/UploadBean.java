package com.biemo.cloud.bbs.core.upload.bean;

import io.swagger.annotations.ApiModelProperty;

public class UploadBean {

    @ApiModelProperty("附件地址")
    private String url;
    @ApiModelProperty("缩略图地址")
    private String preview;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
