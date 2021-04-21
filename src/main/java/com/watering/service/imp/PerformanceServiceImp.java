package com.watering.service.imp;

import com.watering.dao.CareerEntityMapper;
import com.watering.dao.EmployeeEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.dao.PerformanceEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.performance.PerformanceAddDTO;
import com.watering.domain.VO.PerformanceVO;
import com.watering.domain.entity.CareerEntity;
import com.watering.domain.entity.EmployeeEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.domain.entity.PerformanceEntity;
import com.watering.service.PerformanceService;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/13/17:35
 * @Description:
 */
@Service
public class PerformanceServiceImp implements PerformanceService {

    @Autowired
    private PerformanceEntityMapper performanceEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;

    @Autowired
    private CareerEntityMapper careerEntityMapper;

    public ResponseDTO<List<PerformanceVO>> findCurPerformance(Integer carid) {
        List<PerformanceVO> list = new ArrayList<>();
        List<PerformanceEntity> performanceEntities = performanceEntityMapper.selectAllByCarid(carid);
        for (PerformanceEntity performanceEntity : performanceEntities) {
            PerformanceVO performanceVO = new PerformanceVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(performanceEntity.getManid());
            BeanUtils.copyProperties(performanceEntity, performanceVO);
            performanceVO.setManager(managerEntity.getName());
            list.add(performanceVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO performanceInput(PerformanceAddDTO performance){
        PerformanceEntity performanceEntity = new PerformanceEntity();
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(performance.getEmpid());
        CareerEntity careerEntity = careerEntityMapper.selectLastCareerByEmpId(performance.getEmpid());
        ManagerEntity managerEntity = (ManagerEntity) GetCurrentUser.getUser();
        if(employeeEntity.getDepid() == managerEntity.getDepid())
        {
            performanceEntity.setCtime(new Date());
            performanceEntity.setEmpid(performance.getEmpid());
            performanceEntity.setManid(managerEntity.getId());
            performanceEntity.setPerformance(performance.getPerformance());
            performanceEntity.setStime(performance.getStime());
            performanceEntity.setEtime(performance.getEtime());
            if(careerEntity.getDepid()==employeeEntity.getDepid())
            {
                performanceEntity.setCarid(careerEntity.getId());
            }
            performanceEntityMapper.insert(performanceEntity);
            return ResponseDTO.succ();
        }
        else return ResponseDTO.succMsg("您没有权限进行此操作1");
    }


}
