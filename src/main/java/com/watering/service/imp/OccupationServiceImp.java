package com.watering.service.imp;

import com.watering.dao.OccupationEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.OccupationVO;
import com.watering.domain.entity.OccupationEntity;
import com.watering.service.OccupationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/11/17:44
 * @Description:
 */
@Service
public class OccupationServiceImp implements OccupationService {

    @Autowired
    private OccupationEntityMapper occupationEntityMapper;

    @Override
    public void addCount(Integer occid) {
        OccupationEntity occupationEntity = occupationEntityMapper.selectByPrimaryKey(occid);
        Integer pid = occupationEntity.getPid();
        OccupationEntity root = occupationEntityMapper.selectByPrimaryKey(0);
        root.setCount(occupationEntity.getCount()+1);
        while(pid!=-1){
            occupationEntity.setCount(occupationEntity.getCount()+1);
            occupationEntityMapper.updateByPrimaryKey(occupationEntity);
            occupationEntity = occupationEntityMapper.selectByPrimaryKey(pid);
            pid = occupationEntity.getPid();
        }
    }

    @Override
    public void subtractCount(Integer occid) {
        OccupationEntity occupationEntity = occupationEntityMapper.selectByPrimaryKey(occid);
        Integer pid = occupationEntity.getPid();
        while(pid!=-1){
            occupationEntity.setCount(occupationEntity.getCount()-1);
            occupationEntityMapper.updateByPrimaryKey(occupationEntity);
            occupationEntity = occupationEntityMapper.selectByPrimaryKey(pid);
            pid = occupationEntity.getPid();
        }
    }

    public ResponseDTO<OccupationEntity> findOccupation(Integer occid){
        return ResponseDTO.succData(occupationEntityMapper.selectByPrimaryKey(occid));
    }

    @Override
    public ResponseDTO<List<OccupationEntity>> listSonOccupation(Integer occid) {
        List<OccupationEntity> occupationEntities = occupationEntityMapper.listSonById(occid);
        return ResponseDTO.succData(occupationEntities);
    }

    @Override
    public ResponseDTO<List<OccupationEntity>> hotOccupation() {
        List<OccupationEntity> occupationEntities = occupationEntityMapper.listHot();
        return ResponseDTO.succData(occupationEntities);
    }

    //输入为自己的id
    public ResponseDTO<List<OccupationEntity>> listParentOccupation(Integer occid){
        OccupationEntity occupationEntity = occupationEntityMapper.selectByPrimaryKey(occid);
        Integer pid = occupationEntity.getPid();
        List<OccupationEntity> list = new ArrayList<>();
        while(pid!=-1){
            occupationEntity = occupationEntityMapper.selectByPrimaryKey(pid);
            OccupationEntity parent = new OccupationEntity();
            BeanUtils.copyProperties(occupationEntity,parent);
            list.add(parent);
            pid = occupationEntity.getPid();
        }
        return ResponseDTO.succData(list);
    }

}
