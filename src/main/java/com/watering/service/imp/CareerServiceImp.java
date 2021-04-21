package com.watering.service.imp;

import com.watering.dao.*;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.attendance.AttendanceAddDTO;
import com.watering.domain.DTO.crime.CrimeAddDTO;
import com.watering.domain.DTO.performance.PerformanceAddDTO;
import com.watering.domain.VO.*;
import com.watering.domain.entity.*;
import com.watering.service.CareerService;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/11/16:49
 * @Description:
 */
@Service
public class CareerServiceImp implements CareerService {

    @Autowired
    private CareerEntityMapper careerEntityMapper;

    @Autowired
    private OccupationEntityMapper occupationEntityMapper;

    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;


    @Override
    public ResponseDTO<CareerVO> findCareer(Integer carid) {
        CareerEntity careerEntity = careerEntityMapper.selectByPrimaryKey(carid);
        CareerVO careerVO = new CareerVO();
        BeanUtils.copyProperties(careerEntity,careerVO);
        careerVO.setOccupation(occupationEntityMapper.selectByPrimaryKey(careerEntity.getOccid()).getName());
        careerVO.setDepartment(departmentEntityMapper.selectByPrimaryKey(careerEntity.getDepid()).getName());
        careerVO.setEnterprise(enterpriseEntityMapper.selectByPrimaryKey(careerEntity.getEntid()).getName());
        return ResponseDTO.succData(careerVO);
    }

    public ResponseDTO<List<CareerVO>> findAllCareer(Integer empid) {
        List<CareerEntity> careerEntities = careerEntityMapper.selectAllByEmpid(empid);
        ArrayList<CareerVO> list = new ArrayList<>();
        for (CareerEntity careerEntity : careerEntities) {
            CareerVO careerVO = new CareerVO();
            BeanUtils.copyProperties(careerEntity, careerVO);
            EnterpriseEntity enterpriseEntity = enterpriseEntityMapper.selectByPrimaryKey(careerEntity.getEntid());
            DepartmentEntity departmentEntity = departmentEntityMapper.selectByPrimaryKey(careerEntity.getDepid());
            OccupationEntity occupationEntity = occupationEntityMapper.selectByPrimaryKey(careerEntity.getOccid());
            careerVO.setEnterprise(enterpriseEntity.getName());
            careerVO.setDepartment(departmentEntity.getName());
            careerVO.setOccupation(occupationEntity.getName());
            list.add(careerVO);
        }
        return ResponseDTO.succData(list);
    }







}
