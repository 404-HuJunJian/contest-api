package com.watering.constant;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/05/16:42
 * @Description:
 */
public class FileResponseCodeConst extends ResponseCodeConst{

    public static final FileResponseCodeConst FILE_EMPTY = new FileResponseCodeConst(301,"文件为空");
    public static final FileResponseCodeConst FILE_SIZE_ERROR = new FileResponseCodeConst(302,"文件大小超出范围");

    public FileResponseCodeConst(int code, String msg, boolean success){
        super(code, msg, success);
    }

    public FileResponseCodeConst(int code, String msg){
        super(code, msg);
    }

}
