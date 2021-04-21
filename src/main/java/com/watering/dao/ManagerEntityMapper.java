package com.watering.dao;

import com.watering.domain.entity.ManagerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ManagerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerEntity record);

    ManagerEntity selectByPrimaryKey(Integer id);

    List<ManagerEntity> selectAll();

    int updateByPrimaryKey(ManagerEntity record);

    ManagerEntity selectByUserName(String username);

    List<ManagerEntity> listByEntid(Integer entid);
//
//    String selectEntNameById(Integer id);
//
//    String selectDepNameById(Integer id);

}
