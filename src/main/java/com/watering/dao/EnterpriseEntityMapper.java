package com.watering.dao;

import com.watering.domain.entity.EnterpriseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EnterpriseEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseEntity record);

    EnterpriseEntity selectByPrimaryKey(Integer id);

    List<EnterpriseEntity> selectAll();

    int updateByPrimaryKey(EnterpriseEntity record);

    EnterpriseEntity selectByUserName(String username);
}
