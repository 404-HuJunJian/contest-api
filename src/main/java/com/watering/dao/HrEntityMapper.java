package com.watering.dao;

import com.watering.domain.entity.HrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HrEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrEntity record);

    HrEntity selectByPrimaryKey(Integer id);

    List<HrEntity> selectAll();

    int updateByPrimaryKey(HrEntity record);

    HrEntity selectByUserName(String username);

    List<HrEntity> listByEntid(Integer entid);

//
//    String selectEntNameById(Integer id);
}
