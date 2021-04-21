package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.OccupationVO;
import com.watering.domain.entity.OccupationEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/11/17:28
 * @Description:
 */
public interface OccupationService {

    void addCount(Integer occid);
    void subtractCount(Integer occid);
    public ResponseDTO<OccupationEntity> findOccupation(Integer occid);
    ResponseDTO<List<OccupationEntity>> listSonOccupation(Integer occid);
    ResponseDTO<List<OccupationEntity>> hotOccupation();
    ResponseDTO<List<OccupationEntity>> listParentOccupation(Integer occid);

}
