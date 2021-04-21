package com.watering.service;

import com.watering.dao.PermissionMapper;
import com.watering.domain.DTO.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/15:58
 * @Description:
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<PermissionDTO> findAllPermission(){
        return permissionMapper.selectAll();
    }

}
