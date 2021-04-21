package com.watering.service;

import com.github.pagehelper.PageInfo;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.career.CareerAddDTO;
import com.watering.domain.DTO.employee.EmployeeAddDTO;
import com.watering.domain.DTO.employee.EmployeeUpdateBaseDTO;
import com.watering.domain.DTO.search.SearchDTO;
import com.watering.domain.VO.EmployeeSimpleVO;
import com.watering.domain.VO.EmployeeVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/10/15:55
 * @Description:
 */
public interface EmployeeService {

    public ResponseDTO<EmployeeVO> findEmployeeBasicInfo(Integer empid);
    public ResponseDTO updateEmployeeBasicInfo(EmployeeUpdateBaseDTO employeeUpdateBaseDTO) throws FileNotFoundException;
    public ResponseDTO<Integer> addEmployeeBasicInfo(EmployeeAddDTO employeeAddDTO) throws FileNotFoundException;
    public ResponseDTO employ(CareerAddDTO careerAddDTO);
    public ResponseDTO quit(Integer empid);
    public ResponseDTO<PageInfo<EmployeeSimpleVO>> innerEmployeeSearch(SearchDTO searchDTO);
    public ResponseDTO<PageInfo<EmployeeSimpleVO>> dropEmployeeSearch(SearchDTO searchDTO);

}
