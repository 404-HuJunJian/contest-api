package com.watering.dao;

import com.watering.domain.entity.AttendanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AttendanceEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AttendanceEntity record);

    AttendanceEntity selectByPrimaryKey(Integer id);

    List<AttendanceEntity> selectAllByCarid(Integer carid);

    List<AttendanceEntity> selectAll();

    int updateByPrimaryKey(AttendanceEntity record);

    Double selectAvgByCarid(Integer carid);

}
