package com.watering.service.imp;

import com.watering.dao.CareerEntityMapper;
import com.watering.dao.CrimeEntityMapper;
import com.watering.dao.EmployeeEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.crime.CrimeAddDTO;
import com.watering.domain.VO.CrimeVO;
import com.watering.domain.entity.CareerEntity;
import com.watering.domain.entity.CrimeEntity;
import com.watering.domain.entity.EmployeeEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.service.CrimeService;
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
 * @Date: 2021/04/13/17:29
 * @Description:
 */
@Service
public class CrimeServiceImp implements CrimeService {

    @Autowired
    private CrimeEntityMapper crimeEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;

    @Autowired
    private CareerEntityMapper careerEntityMapper;

    public ResponseDTO<List<CrimeVO>> findAllCrime(Integer empid) {
        List<CrimeVO> list = new ArrayList<>();
        List<CrimeEntity> crimeEntities = crimeEntityMapper.selectAllByEmpid(empid);
        for (CrimeEntity crimeEntity : crimeEntities) {
            CrimeVO crimeVO = new CrimeVO();
            BeanUtils.copyProperties(crimeEntity, crimeVO);
            crimeVO.setManager(managerEntityMapper.selectByPrimaryKey(crimeEntity.getManid()).getName());
            list.add(crimeVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO<List<CrimeVO>> findCurCrime(Integer carid) {
        List<CrimeVO> list = new ArrayList<>();
        List<CrimeEntity> crimeEntities = crimeEntityMapper.selectAllByCarid(carid);
        for (CrimeEntity crimeEntity : crimeEntities) {
            CrimeVO crimeVO = new CrimeVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(crimeEntity.getManid());
            BeanUtils.copyProperties(crimeEntity, crimeVO);
            crimeVO.setManager(managerEntity.getName());
            list.add(crimeVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO crimeInput(CrimeAddDTO crime){
        CrimeEntity crimeEntity = new CrimeEntity();
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(crime.getEmpid());
        CareerEntity careerEntity = careerEntityMapper.selectLastCareerByEmpId(crime.getEmpid());
        ManagerEntity managerEntity =(ManagerEntity) GetCurrentUser.getUser();
        if(employeeEntity.getDepid()==managerEntity.getDepid()){
            crimeEntity.setCtime(new Date());
            crimeEntity.setEmpid(crime.getEmpid());
            crimeEntity.setManid(managerEntity.getId());
            crimeEntity.setDetail(crime.getDetail());
            crimeEntity.setRank(crime.getRank());
            crimeEntity.setCritime(crime.getCritime());
            if(careerEntity.getDepid()==employeeEntity.getDepid())
            {
                crimeEntity.setCarid(careerEntity.getId());
            }
            crimeEntityMapper.insert(crimeEntity);
            return ResponseDTO.succ();
        }
        else return ResponseDTO.succMsg("您没有权限进行此操作1");
    }


}
