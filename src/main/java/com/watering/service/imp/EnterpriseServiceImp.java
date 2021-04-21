package com.watering.service.imp;

import com.watering.constant.LoginResponseCodeConst;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.IndustryEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.EnterpriseVO;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.service.EnterpriseService;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/10/18:04
 * @Description:
 */
@Service
public class EnterpriseServiceImp implements EnterpriseService {

    @Autowired
    private IndustryEntityMapper industryEntityMapper;
    @Autowired
    private HrEntityMapper hrEntityMapper;
    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Override
    public ResponseDTO<EnterpriseVO> getInfo() {
        EnterpriseVO enterpriseVO =new EnterpriseVO();
        EnterpriseEntity enterpriseEntity = (EnterpriseEntity) GetCurrentUser.getUser();
        BeanUtils.copyProperties(enterpriseEntity,enterpriseVO);
        enterpriseVO.setIndustry(industryEntityMapper.selectByPrimaryKey(enterpriseEntity.getIndid()).getName());
        return ResponseDTO.succData(enterpriseVO);
    }

    public ResponseDTO checkUserName(String username){
        EnterpriseEntity enterpriseEntity = (EnterpriseEntity) GetCurrentUser.getUser();
        String FullUserName = enterpriseEntity.getUsername()+"_"+username;
        HrEntity hrEntity = hrEntityMapper.selectByUserName(FullUserName);
        ManagerEntity managerEntity = managerEntityMapper.selectByUserName(FullUserName);
        if(null == hrEntity && null == managerEntity)
            return ResponseDTO.succMsg("用户名可用");
        return ResponseDTO.wrap(LoginResponseCodeConst.USERNAME_REPEAT);
    }
}
