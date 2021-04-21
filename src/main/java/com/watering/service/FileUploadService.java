package com.watering.service;

import com.alibaba.druid.util.StringUtils;
import com.watering.constant.FileResponseCodeConst;
import com.watering.constant.FileTypeEnum;
import com.watering.constant.ResponseCodeConst;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/05/16:12
 * @Description:
 */
@Service
public class FileUploadService {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${upload-file-parent-path}")
    private String parentPath;

    @Autowired
    private HrEntityMapper hrEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;

    //默认大小10M
    private static final Long DEFAULT_SIZE = 10 * 1024 * 1024L;

    //上传至临时文件
    public ResponseDTO<String> uploadFile(MultipartFile multipartFile, FileTypeEnum fileTypeEnum){
        if(null == multipartFile){
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_EMPTY);
        }
        Long maxSize = DEFAULT_SIZE;
        if (!StringUtils.isEmpty(maxFileSize)){
            String maxSizeString = maxFileSize.replace("MB","");
            maxSize = Integer.valueOf(maxSizeString) * 1024 * 1024L;
        }
        if (multipartFile.getSize() > maxSize){
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_SIZE_ERROR);
        }
        //文件目录是父目录加自己的目录，结尾加/
        String filePath = parentPath+fileTypeEnum.getTempPath()+"/";
        File directory = new File(filePath);
        //文件目录不存在
        if(!directory.exists()){
            //创建全部的路径文件夹
            directory.mkdirs();
        }
        //获取文件的完整名字
        String originalFileName = multipartFile.getOriginalFilename();
        //生成文件的新名字
        String newFileName = generateFileName(originalFileName);
        //文件位置
//        System.out.println(new File(filePath + newFileName).getAbsolutePath());
        File tempFile = new File(new File(filePath + newFileName).getAbsolutePath());
        //不带后缀名称
//        String viewName = newFileName.substring(0,newFileName.lastIndexOf("."));
        try {
            multipartFile.transferTo(tempFile);

        } catch (IOException e) {
            return null;
        }
//        return ResponseDTO.succData(viewName);
        return ResponseDTO.succData(newFileName);
    }

    //确认临时文件
    public boolean checkFile(String originalFileName,FileTypeEnum fileTypeEnum) throws FileNotFoundException {
        if(null == originalFileName || originalFileName.length()==0){
            throw new FileNotFoundException("文件不存在");
        }
        String oldFilePath = this.parentPath+fileTypeEnum.getTempPath()+"/"+originalFileName;
        File oldFile = new File(new File(oldFilePath).getAbsolutePath());
        if(!oldFile.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        String newFilePath = this.parentPath+fileTypeEnum.getPath()+"/";
        File directory = new File(newFilePath);
        //文件目录不存在
        if(!directory.exists()){
            //创建全部的路径文件夹
            directory.mkdirs();
        }
        newFilePath += originalFileName;
        File newFile = new File(new File(newFilePath).getAbsolutePath());
        return oldFile.renameTo(newFile);
    }


    public ResponseDTO updateFile(MultipartFile multipartFile, FileTypeEnum fileTypeEnum) throws FileNotFoundException {
        String originalFileName = uploadFile(multipartFile, fileTypeEnum).getData();
        if(checkFile(originalFileName,fileTypeEnum)){
            String fileUrl = FileTypeEnum.IMG_PHOTO.getUrl() + originalFileName ;
            String role = GetCurrentUser.getUserRole();
            Object object = GetCurrentUser.getUser();
            if(role.equals(RoleDTO.Type.ROLE_HR.getType())){
                HrEntity hrEntity = (HrEntity) object;
                hrEntity.setPhoto(fileUrl);
                hrEntityMapper.updateByPrimaryKey(hrEntity);
            }else if(role.equals(RoleDTO.Type.ROLE_MANAGER.getType())){
                ManagerEntity managerEntity = (ManagerEntity) object;
                managerEntity.setPhoto(fileUrl);
                managerEntityMapper.updateByPrimaryKey(managerEntity);
            }else{
                EnterpriseEntity enterpriseEntity = (EnterpriseEntity) object;
                enterpriseEntity.setPhoto(fileUrl);
                enterpriseEntityMapper.updateByPrimaryKey(enterpriseEntity);
            }
            return ResponseDTO.succMsg("修改成功");
        }
        return ResponseDTO.wrap(FileResponseCodeConst.FILE_EMPTY);
    }

    //生成文件名
    private String generateFileName(String originalFileName){
        //当前时间戳
        String time = String.valueOf(System.currentTimeMillis());
        //获得uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //获得文件扩展名后缀
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        return time +"_"+ uuid + fileType;
    }


}
