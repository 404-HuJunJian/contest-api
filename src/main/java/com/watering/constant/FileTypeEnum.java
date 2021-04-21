package com.watering.constant;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/05/16:50
 * @Description:
 */
public enum FileTypeEnum {


    IMG_RESUME("img/resume","temp/img","http://zjnu404.top:8081/resume_img/"),
    IMG_PHOTO("img/photo","temp/img","http://zjnu404.top:8081/head_img/");

    //存储的子路径
    private String path;
    //临时存储的子路径
    private String tempPath;
    //访问的url
    private String url;


    FileTypeEnum(String path,String tempPath,String url) {
        this.path = path;
        this.tempPath = tempPath;
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public String getTempPath() {
        return tempPath;
    }

    public String getUrl() {
        return url;
    }
}
