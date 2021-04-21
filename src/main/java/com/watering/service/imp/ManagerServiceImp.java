package com.watering.service.imp;

import com.watering.constant.LoginResponseCodeConst;
import com.watering.dao.DepartmentEntityMapper;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.manager.ManagerAddDTO;
import com.watering.domain.DTO.manager.ManagerUpdateDTO;
import com.watering.domain.VO.ManagerVO;
import com.watering.domain.entity.DepartmentEntity;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.service.ManagerService;
import com.watering.utils.EncryptUtil;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/20:04
 * @Description:
 */
@Service
public class ManagerServiceImp implements ManagerService {

    @Value("${default-photo-manager}")
    private String ManagerDefaultPhotoUrl;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private HrEntityMapper hrEntityMapper;

    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;


    @Override
    public ResponseDTO createManager(ManagerAddDTO managerAddDTO) {
        ManagerEntity managerEntity = new ManagerEntity();
        EnterpriseEntity enterpriseEntity = (EnterpriseEntity) GetCurrentUser.getUser();
        String FullUserName = enterpriseEntity.getUsername()+"_"+managerAddDTO.getUsername();
        if(checkUserName(FullUserName)){
            managerEntity.setUsername(FullUserName);
            managerEntity.setPassword(EncryptUtil.encrypt(managerAddDTO.getPassword()));
            managerEntity.setName(managerAddDTO.getName());
            managerEntity.setCtime(new Date());
            managerEntity.setDepid(managerAddDTO.getDepid());
            managerEntity.setEntid(enterpriseEntity.getId());
            managerEntity.setPhoto(ManagerDefaultPhotoUrl);
            managerEntityMapper.insert(managerEntity);
            return ResponseDTO.succMsg("创建主管成功");
        }
        return ResponseDTO.wrap(LoginResponseCodeConst.USERNAME_REPEAT);
    }

    @Override
    public ResponseDTO updateManger(ManagerUpdateDTO managerUpdateDTO) {
        ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(managerUpdateDTO.getId());
        managerEntity.setPassword(EncryptUtil.encrypt(managerUpdateDTO.getPassword()));
        managerEntityMapper.updateByPrimaryKey(managerEntity);
        return ResponseDTO.succMsg("修改密码成功");
    }

    @Override
    public ResponseDTO<ManagerVO> getInfo() {
        ManagerVO managerVO =new ManagerVO();
        ManagerEntity managerEntity = (ManagerEntity) GetCurrentUser.getUser();
        BeanUtils.copyProperties(managerEntity,managerVO);
        managerVO.setDepartment(departmentEntityMapper.selectByPrimaryKey(managerEntity.getDepid()).getName());
        managerVO.setEnterprise(enterpriseEntityMapper.selectByPrimaryKey(managerEntity.getEntid()).getName());
        return ResponseDTO.succData(managerVO);
    }

    public ResponseDTO<List<ManagerVO>> managerSearch() {
        List<ManagerVO> list = new ArrayList<>();
        EnterpriseEntity enterpriseEntity = (EnterpriseEntity) GetCurrentUser.getUser();
        List<ManagerEntity> managerEntities = managerEntityMapper.listByEntid(enterpriseEntity.getId());
        for (ManagerEntity managerEntity : managerEntities) {
            ManagerVO managerVO = new ManagerVO();
            DepartmentEntity departmentEntity = departmentEntityMapper.selectByPrimaryKey(managerEntity.getDepid());
            BeanUtils.copyProperties(managerEntity, managerVO);
            managerVO.setEnterprise(enterpriseEntity.getName());
            managerVO.setDepartment(departmentEntity.getName());
            list.add(managerVO);
        }
        return ResponseDTO.succData(list);
    }

    private boolean checkUserName(String FullUserName){
        HrEntity hrEntity = hrEntityMapper.selectByUserName(FullUserName);
        ManagerEntity managerEntity = managerEntityMapper.selectByUserName(FullUserName);
        if(null == hrEntity && null == managerEntity)
            return true;
        return false;
    }


}
