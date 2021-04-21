package com.watering.service.imp;

import com.watering.dao.AttendanceEntityMapper;
import com.watering.dao.CareerEntityMapper;
import com.watering.dao.EmployeeEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.attendance.AttendanceAddDTO;
import com.watering.domain.VO.AttendanceVO;
import com.watering.domain.entity.AttendanceEntity;
import com.watering.domain.entity.CareerEntity;
import com.watering.domain.entity.EmployeeEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.service.AttendanceService;
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
 * @Date: 2021/04/13/17:32
 * @Description:
 */
@Service
public class AttendanceServiceImp implements AttendanceService {

    @Autowired
    private AttendanceEntityMapper attendanceEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;

    @Autowired
    private CareerEntityMapper careerEntityMapper;

    public ResponseDTO<List<AttendanceVO>> findCurAttendance(Integer carid) {
        List<AttendanceVO> list = new ArrayList<>();
        List<AttendanceEntity> attendanceEntities = attendanceEntityMapper.selectAllByCarid(carid);
        for (AttendanceEntity attendanceEntity : attendanceEntities) {
            AttendanceVO attendanceVO = new AttendanceVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(attendanceEntity.getManid());
            BeanUtils.copyProperties(attendanceEntity, attendanceVO);
            attendanceVO.setManager(managerEntity.getName());
            list.add(attendanceVO);

        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO attendanceInput(AttendanceAddDTO attendance){
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(attendance.getEmpid());
        CareerEntity careerEntity = careerEntityMapper.selectLastCareerByEmpId(attendance.getEmpid());
        ManagerEntity managerEntity = (ManagerEntity) GetCurrentUser.getUser();
        if(employeeEntity.getDepid()==managerEntity.getDepid())
        {
            attendanceEntity.setCtime(new Date());
            attendanceEntity.setEmpid(attendance.getEmpid());
            attendanceEntity.setManid(managerEntity.getId());
            attendanceEntity.setAttendance(attendance.getAttendance());
            attendanceEntity.setStime(attendance.getStime());
            attendanceEntity.setEtime(attendance.getEtime());
            if(careerEntity.getDepid()==employeeEntity.getDepid())
            {
                attendanceEntity.setCarid(careerEntity.getId());
            }
            attendanceEntityMapper.insert(attendanceEntity);
            return ResponseDTO.succ();
        }
        else return ResponseDTO.succMsg("您没有权限进行此操作1");
    }

}
