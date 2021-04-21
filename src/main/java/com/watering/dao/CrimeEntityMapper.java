package com.watering.dao;

import com.watering.domain.entity.CrimeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CrimeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrimeEntity record);

    CrimeEntity selectByPrimaryKey(Integer id);

    List<CrimeEntity> selectAllByEmpid(Integer empid);

    List<CrimeEntity> selectAllByCarid(Integer carid);

    List<CrimeEntity> selectAll();

    int updateByPrimaryKey(CrimeEntity record);
}
