package com.watering.controller;

import com.github.pagehelper.PageInfo;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.search.KeyWord;
import com.watering.domain.DTO.search.SearchDTO;
import com.watering.domain.VO.EmployeeSimpleVO;
import com.watering.domain.VO.HrVO;
import com.watering.domain.VO.ManagerVO;
import com.watering.domain.entity.DepartmentEntity;
import com.watering.service.DepartmentService;
import com.watering.service.HrService;
import com.watering.service.ManagerService;
import com.watering.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/16:46
 * @Description:
 */
@Api(tags = "搜索处理")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private HrService hrService;

    @Autowired
    private ManagerService managerService;

    @ApiOperation("企业内员工查询")
    @PostMapping("/inner")
    public ResponseDTO<PageInfo<EmployeeSimpleVO>> innerEmployeeSearch(@RequestBody SearchDTO search){
        return employeeService.innerEmployeeSearch(search);
    }

    @ApiOperation("离职员工查询")
    @PostMapping("/drop")
    public ResponseDTO<PageInfo<EmployeeSimpleVO>> dropEmployeeSearch(@RequestBody SearchDTO search){
        return employeeService.dropEmployeeSearch(search);
    }

    @ApiOperation("查找hr 再看看要不要改模型")
    @GetMapping("/hr")
    public ResponseDTO<List<HrVO>> hrSearch(){
        return hrService.hrSearch();
    }

    @ApiOperation("查找manager 再看看要不要改模型")
    @GetMapping("/manager")
    public ResponseDTO<List<ManagerVO>> managerSearch(){
        return managerService.managerSearch();
    }

    @ApiOperation("查找所有部门")
    @GetMapping("/department")
    public ResponseDTO<List<DepartmentEntity>> departmentSearch(){
        return departmentService.departmentSearch();
    }

}
