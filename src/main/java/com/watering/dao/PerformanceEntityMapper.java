package com.watering.dao;

import com.watering.domain.entity.PerformanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PerformanceEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PerformanceEntity record);

    PerformanceEntity selectByPrimaryKey(Integer id);

    List<PerformanceEntity> selectAllByCarid(Integer carid);

    List<PerformanceEntity> selectAll();

    int updateByPrimaryKey(PerformanceEntity record);

    Double selectAvgByCarid(Integer carid);

}
