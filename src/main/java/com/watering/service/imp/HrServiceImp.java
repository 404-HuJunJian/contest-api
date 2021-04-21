package com.watering.service.imp;

import com.watering.constant.LoginResponseCodeConst;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.hr.HrAddDTO;
import com.watering.domain.DTO.hr.HrUpdateDTO;
import com.watering.domain.VO.HrVO;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.service.HrService;
import com.watering.utils.EncryptUtil;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/19:19
 * @Description:
 */
@Service
public class HrServiceImp implements HrService {

    @Value("${default-photo-hr}")
    private String HrDefaultPhotoUrl;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private HrEntityMapper hrEntityMapper;

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;

    @Override
    public ResponseDTO createHr(HrAddDTO hrAddDTO) {
        HrEntity hrEntity = new HrEntity();
        EnterpriseEntity enterpriseEntity = (EnterpriseEntity) GetCurrentUser.getUser();
        String FullUserName = enterpriseEntity.getUsername()+"_"+hrAddDTO.getUsername();
        if(checkUserName(FullUserName)){
            hrEntity.setUsername(FullUserName);
            hrEntity.setPassword(EncryptUtil.encrypt(hrAddDTO.getPassword()));
            hrEntity.setName(hrAddDTO.getName());
            hrEntity.setCtime(new Date());
            hrEntity.setEntid(enterpriseEntity.getId());
            hrEntity.setPhoto(HrDefaultPhotoUrl);
            hrEntityMapper.insert(hrEntity);
            return ResponseDTO.succMsg("创建Hr成功");
        }
        return ResponseDTO.wrap(LoginResponseCodeConst.USERNAME_REPEAT);
    }

    @Override
    public ResponseDTO updateHr(HrUpdateDTO hrUpdateDTO) {
        HrEntity hrEntity = hrEntityMapper.selectByPrimaryKey(hrUpdateDTO.getId());
        hrEntity.setPassword(EncryptUtil.encrypt(hrUpdateDTO.getPassword()));
        hrEntityMapper.updateByPrimaryKey(hrEntity);
        return ResponseDTO.succMsg("修改密码成功");
    }

    @Override
    public ResponseDTO<HrVO> getInfo() {
        HrVO hrVO = new HrVO();
        HrEntity hrEntity = (HrEntity) GetCurrentUser.getUser();
        BeanUtils.copyProperties(hrEntity,hrVO);
        hrVO.setEnterprise(enterpriseEntityMapper.selectByPrimaryKey(hrEntity.getEntid()).getName());
        return ResponseDTO.succData(hrVO);
    }


    public ResponseDTO<List<HrVO>> hrSearch() {
        List<HrVO> list = new ArrayList<>();
        EnterpriseEntity enterpriseEntity = (EnterpriseEntity) GetCurrentUser.getUser();
        List<HrEntity> hrEntities = hrEntityMapper.listByEntid(enterpriseEntity.getId());
        for (HrEntity hrEntity : hrEntities) {
            HrVO hrVO = new HrVO();
            BeanUtils.copyProperties(hrEntity, hrVO);
            hrVO.setEnterprise(enterpriseEntity.getName());
            list.add(hrVO);
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
