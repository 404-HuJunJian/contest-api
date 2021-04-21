package com.watering.config;

import com.watering.constant.FileTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/05/21:40
 * @Description: 配置绝对路径和访问的url
 */
@Configuration
public class UploadConfig {

    @Value("${upload-file-parent-path}")
    private String parentPath;

    @Value("${static-server-root}")
    private String staticServerRoot;

    //获得绝对路径
    public String getAbsolutePath(FileTypeEnum fileTypeEnum){
        File file = new File(parentPath+fileTypeEnum.getPath());
        String path = file.getAbsolutePath()+"/";
        path = path.replace("\\","/");
        return "file:///"+path;
    }

    //获得访问的Url
    public String getUrl(FileTypeEnum fileTypeEnum){
        return fileTypeEnum.getUrl().replaceFirst(staticServerRoot, "")+"**";
    }

}
