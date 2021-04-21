package com.watering.controller;

import com.watering.constant.LoginResponseCodeConst;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.DTO.employee.EmployeeAddDTO;
import com.watering.domain.DTO.employee.EmployeeUpdateBaseDTO;
import com.watering.domain.VO.EmployeeVO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/16:33
 * @Description:
 */
@Api(tags = "处理基本信息")
@RestController
@RequestMapping("/info")
public class BasicInfoController {

    @Autowired
    private HrService hrService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("根据员工id查找员工基本信息")
    @GetMapping("/employee/{empid}")
    public ResponseDTO<EmployeeVO> findEmployeeBasicInfo(@PathVariable Integer empid){
        return employeeService.findEmployeeBasicInfo(empid);
    }

    @ApiOperation("修改employee的基本信息")
    @PutMapping("/employee")
    public ResponseDTO updateEmployeeBasicInfo(@RequestBody EmployeeUpdateBaseDTO employee) throws FileNotFoundException {
        return employeeService.updateEmployeeBasicInfo(employee);
    }

    @ApiOperation("新建employee档案")
    @PostMapping("/employee")
    public ResponseDTO<Integer> addEmployeeBasicInfo(@RequestBody EmployeeAddDTO employee) throws FileNotFoundException {
        return employeeService.addEmployeeBasicInfo(employee);
    }

    @ApiOperation("公司账号登录查询公司基本信息")
    @GetMapping("/enterprise")
    public ResponseDTO<EnterpriseVO> findEnterpriseBasicInfo(){
        return enterpriseService.getInfo();
    }

    @ApiOperation("Hr账号登录查询Hr基本信息")
    @GetMapping("/hr")
    public ResponseDTO<HrVO> findHrBasicInfo(){
        return hrService.getInfo();
    }

    @ApiOperation("主管账号登录查询主管基本信息")
    @GetMapping("/manager")
    public ResponseDTO<ManagerVO> findManagerBasicInfo(){
        return managerService.getInfo();
    }

}
