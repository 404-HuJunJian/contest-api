package com.watering.dao;

import com.watering.domain.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DepartmentEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentEntity record);

    DepartmentEntity selectByPrimaryKey(Integer id);

    List<DepartmentEntity> selectAll();

    int updateByPrimaryKey(DepartmentEntity record);

    List<DepartmentEntity> listByEntidLikeName(@Param("entid")Integer entid,@Param("name")String name);

    List<DepartmentEntity> listByEntid(Integer entid);

}
