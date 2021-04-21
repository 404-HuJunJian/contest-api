package com.watering.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.watering.constant.LoginResponseCodeConst;
import com.watering.constant.ResponseCodeConst;
import com.watering.dao.CareerEntityMapper;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.dao.ScoreEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.DTO.score.ScoreAddDTO;
import com.watering.domain.VO.AvgScoreVO;
import com.watering.domain.VO.ScoreVO;
import com.watering.domain.entity.CareerEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import com.watering.domain.entity.ScoreEntity;
import com.watering.service.ScoreService;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/10/20:19
 * @Description:
 */
@Service
public class ScoreServiceImp implements ScoreService {

    @Autowired
    private CareerEntityMapper careerEntityMapper;

    @Autowired
    private ScoreEntityMapper scoreEntityMapper;

    @Autowired
    private HrEntityMapper hrEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Override
    public ResponseDTO evaluate(ScoreAddDTO scoreAddDTO) {
        ScoreEntity scoreEntity = new ScoreEntity();
        CareerEntity careerEntity = careerEntityMapper.selectLastCareerByEmpId(scoreAddDTO.getEmpid());
        if(careerEntity.getEtime()!=null){
            return ResponseDTO.wrap(ResponseCodeConst.NOT_EXISTS);
        }
        BeanUtils.copyProperties(scoreAddDTO,scoreEntity);
        String role = GetCurrentUser.getUserRole();
        if(role.equals(RoleDTO.Type.ROLE_HR.getType())){
            HrEntity hrEntity = (HrEntity) GetCurrentUser.getUser();
            scoreEntity.setIshr(true);
            scoreEntity.setValid(hrEntity.getId());
        }
        if (role.equals(RoleDTO.Type.ROLE_MANAGER.getType())){
            ManagerEntity managerEntity = (ManagerEntity) GetCurrentUser.getUser();
            scoreEntity.setIshr(false);
            scoreEntity.setValid(managerEntity.getId());
        }
        scoreEntity.setCtime(new Date());
        scoreEntity.setCarid(careerEntity.getId());
        scoreEntityMapper.insert(scoreEntity);
        return ResponseDTO.succMsg("评价成功");
    }

    @Override
    public ResponseDTO<PageInfo<ScoreVO>> pageCurScore(Integer carid, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<ScoreEntity> scoreEntities = scoreEntityMapper.listByCarid(carid);
        List<ScoreVO> scoreVOS = new ArrayList<>();
        for (ScoreEntity scoreEntity:scoreEntities){
            ScoreVO scoreVO = new ScoreVO();
            BeanUtils.copyProperties(scoreEntity,scoreVO);
            if(scoreEntity.isIshr()){
                scoreVO.setName(hrEntityMapper.selectByPrimaryKey(scoreEntity.getValid()).getName());
            }else{
                scoreVO.setName(managerEntityMapper.selectByPrimaryKey(scoreEntity.getValid()).getName());
            }
            scoreVOS.add(scoreVO);
        }
        PageInfo<ScoreVO> pageInfo = new PageInfo<>(scoreVOS);
        return ResponseDTO.succData(pageInfo);
    }


    public ResponseDTO<AvgScoreVO> findAvgScore(Integer empid) {
        Double attitude = new Double(0);
        Double ability = new Double(0);
        AvgScoreVO avgScoreVO = new AvgScoreVO();
        List<ScoreEntity> scoreEntities = scoreEntityMapper.listByEmpid(empid);
        for (ScoreEntity scoreEntity : scoreEntities) {
            attitude += scoreEntity.getAttitude();
            ability += scoreEntity.getAbility();
        }
        avgScoreVO.setAttitude(scoreEntities.size() > 0 ? attitude / scoreEntities.size(): attitude);
        avgScoreVO.setAbility(scoreEntities.size() > 0 ? ability / scoreEntities.size() : ability);
        return ResponseDTO.succData(avgScoreVO);
    }

    public ResponseDTO<AvgScoreVO> findCurAvgScore(Integer carid){
        Double attitude = new Double(0);
        Double ability = new Double(0);
        AvgScoreVO avgScoreVO = new AvgScoreVO();
        List<ScoreEntity> scoreEntities = scoreEntityMapper.listByCarid(carid);
        for (ScoreEntity scoreEntity : scoreEntities) {
            attitude += scoreEntity.getAttitude();
            ability += scoreEntity.getAbility();
        }
        avgScoreVO.setAttitude(attitude / scoreEntities.size());
        avgScoreVO.setAbility(ability / scoreEntities.size());
        return ResponseDTO.succData(avgScoreVO);
    }

}
