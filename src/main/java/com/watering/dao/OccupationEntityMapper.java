package com.watering.dao;

import com.watering.domain.entity.OccupationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OccupationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OccupationEntity record);

    OccupationEntity selectByPrimaryKey(Integer id);

    List<OccupationEntity> selectAll();

    int updateByPrimaryKey(OccupationEntity record);

    List<OccupationEntity> listSonById(Integer pid);

    OccupationEntity selectByPid(Integer id);

    List<OccupationEntity> listHot();

}
