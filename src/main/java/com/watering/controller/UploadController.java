package com.watering.controller;


import com.watering.constant.FileTypeEnum;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/19:32
 * @Description:
 */
@Api(tags = "上传")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation("上传简历")
    @PostMapping("/resume")
    public ResponseDTO<String> uploadResume(HttpServletRequest request){
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        return fileUploadService.uploadFile(req.getFile("file"), FileTypeEnum.IMG_RESUME);
    }

    @ApiOperation("上传头像")
    @PostMapping("/photo")
    public ResponseDTO<String> uploadPhoto(HttpServletRequest request){
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        return fileUploadService.uploadFile(req.getFile("file"),FileTypeEnum.IMG_PHOTO);
    }

    @ApiOperation("修改头像")
    @PutMapping("/photo")
    public ResponseDTO updatePhoto(HttpServletRequest request) throws FileNotFoundException {
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        return fileUploadService.updateFile(req.getFile("file"),FileTypeEnum.IMG_PHOTO);
    }


}
