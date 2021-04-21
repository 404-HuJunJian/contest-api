package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.department.DepartmentDTO;
import com.watering.domain.DTO.department.DepartmentUpdateDTO;
import com.watering.domain.entity.DepartmentEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/13/17:40
 * @Description:
 */
public interface DepartmentService {

    public ResponseDTO<List<DepartmentEntity>> departmentSearch();

    public ResponseDTO addDepartment(DepartmentDTO department);

    public ResponseDTO updateDepartment(DepartmentUpdateDTO department);
}
