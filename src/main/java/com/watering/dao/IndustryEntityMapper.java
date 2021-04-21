package com.watering.dao;

import com.watering.domain.entity.IndustryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IndustryEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryEntity record);

    IndustryEntity selectByPrimaryKey(Integer id);

    List<IndustryEntity> selectAll();

    int updateByPrimaryKey(IndustryEntity record);
}
