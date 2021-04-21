package com.watering.service.imp;

import com.watering.dao.DepartmentEntityMapper;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.DTO.department.DepartmentDTO;
import com.watering.domain.DTO.department.DepartmentUpdateDTO;
import com.watering.domain.entity.DepartmentEntity;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.service.DepartmentService;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/13/17:40
 * @Description:
 */
@Service
public class DepartmentServiceImp implements DepartmentService {

    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    public ResponseDTO<List<DepartmentEntity>> departmentSearch(){Integer entid = null;
        String role = GetCurrentUser.getUserRole();
        Object object = GetCurrentUser.getUser();
        if(role.equals(RoleDTO.Type.ROLE_HR.getType())){
            HrEntity hrEntity = (HrEntity) object;
            entid = hrEntity.getEntid();
        }else if(role.equals(RoleDTO.Type.ROLE_MANAGER.getType())){
            ManagerEntity managerEntity = (ManagerEntity) object;
            entid = managerEntity.getEntid();
        }else{
            EnterpriseEntity enterpriseEntity = (EnterpriseEntity) object;
            entid = enterpriseEntity.getId();
        }
        List<DepartmentEntity> list = departmentEntityMapper.listByEntid(entid);
        return ResponseDTO.succData(list);
    }

    public ResponseDTO addDepartment(DepartmentDTO department){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        EnterpriseEntity enterpriseEntity =(EnterpriseEntity)GetCurrentUser.getUser();
        departmentEntity.setCtime(new Date());
        departmentEntity.setName(department.getName());
        departmentEntity.setEntid(enterpriseEntity.getId());
        departmentEntityMapper.insert(departmentEntity);
        return ResponseDTO.succ();
    }

    public ResponseDTO updateDepartment(DepartmentUpdateDTO department){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        DepartmentEntity departmentEntity1 = departmentEntityMapper.selectByPrimaryKey(department.getId());
        departmentEntity.setId(department.getId());
        departmentEntity.setCtime(new Date());
        departmentEntity.setName(department.getName());
        departmentEntity.setEntid(departmentEntity1.getEntid());
        departmentEntityMapper.updateByPrimaryKey(departmentEntity);
        return ResponseDTO.succ();
    }
}
