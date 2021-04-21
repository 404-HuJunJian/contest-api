package com.watering.controller;

import com.watering.constant.LoginResponseCodeConst;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.DTO.hr.HrAddDTO;
import com.watering.domain.DTO.hr.HrUpdateDTO;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.manager.ManagerAddDTO;
import com.watering.domain.DTO.manager.ManagerUpdateDTO;
import com.watering.domain.VO.EnterpriseVO;
import com.watering.domain.VO.HrVO;
import com.watering.domain.VO.ManagerVO;
import com.watering.service.EmployeeService;
import com.watering.service.EnterpriseService;
import com.watering.service.HrService;
import com.watering.service.ManagerService;
import com.watering.utils.GetCurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ehcache.impl.internal.classes.commonslang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/15:41
 * @Description: 处理用户账号
 */
@Api(tags = "处理用户账号(修改,创建)")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private HrService hrService;

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("用sessionId获取用户的角色")
    @GetMapping("/myInfo")
    public ResponseDTO WhoAmI(){
        String role = GetCurrentUser.getUserRole();
        return ResponseDTO.succData(role);
    }

    @ApiOperation("获取验证码")
    @GetMapping("/code")
    public ResponseDTO<byte []> getCode(){
        return ResponseDTO.succData((byte[]) ArrayUtils.toPrimitive(new ArrayList<>()));
    }

    @ApiOperation("创建Hr账号")
    @PostMapping("/hr")
    public ResponseDTO createHr(@RequestBody HrAddDTO hr){
        return hrService.createHr(hr);
    }

    @ApiOperation("修改Hr账号")
    @PutMapping("/hr")
    public ResponseDTO updateHr(@RequestBody HrUpdateDTO hr){
        return hrService.updateHr(hr);
    }

    @ApiOperation("创建主管账号")
    @PostMapping("/manager")
    public ResponseDTO createManager(@RequestBody ManagerAddDTO manager){
        return managerService.createManager(manager);
    }

    @ApiOperation("修改主管账号")
    @PutMapping("/manager")
    public ResponseDTO updateManager(@RequestBody ManagerUpdateDTO manager){
        return managerService.updateManger(manager);
    }

    @ApiOperation("检查用户名")
    @GetMapping("/check/{username}")
    public ResponseDTO checkUserName(@PathVariable String username){
        return enterpriseService.checkUserName(username);
    }

}
