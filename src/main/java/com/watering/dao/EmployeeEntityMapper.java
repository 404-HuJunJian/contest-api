package com.watering.dao;

import com.github.pagehelper.Page;
import com.watering.domain.DTO.search.SearchDTO;
import com.watering.domain.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface EmployeeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeEntity record);

    EmployeeEntity selectByPrimaryKey(Integer id);

    List<EmployeeEntity> selectAll();

    int updateByPrimaryKey(EmployeeEntity record);

    Page<EmployeeEntity> listBySearchDTO(SearchDTO searchDTO);

    int insertHistoryOccupation(@Param("ctime")Date ctime,@Param("empid")Integer empid,@Param("occid")Integer occid);

    List<Integer> listEmpidByHistoryOccid(Integer occid);

}
