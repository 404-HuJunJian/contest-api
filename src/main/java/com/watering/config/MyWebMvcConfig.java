package com.watering.config;

import com.watering.constant.FileTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/05/20:27
 * @Description:
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadConfig.getUrl(FileTypeEnum.IMG_RESUME)).addResourceLocations(uploadConfig.getAbsolutePath(FileTypeEnum.IMG_RESUME));
        registry.addResourceHandler(uploadConfig.getUrl(FileTypeEnum.IMG_PHOTO)).addResourceLocations(uploadConfig.getAbsolutePath(FileTypeEnum.IMG_PHOTO));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/Api1").setViewName("redirect:/doc.html");
        registry.addViewController("/Api2").setViewName("redirect:/swagger-ui.html");
    }
}
