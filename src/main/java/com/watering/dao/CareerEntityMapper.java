package com.watering.dao;

import com.watering.domain.entity.CareerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CareerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CareerEntity record);

    CareerEntity selectByPrimaryKey(Integer id);

    List<CareerEntity> selectAllByEmpid(Integer empid);

    List<CareerEntity> selectAll();

    int updateByPrimaryKey(CareerEntity record);

    CareerEntity selectLastCareerByEmpId(Integer empid);



}
