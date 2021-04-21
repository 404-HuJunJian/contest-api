package com.watering.dao;

import com.watering.domain.DTO.PermissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/15:55
 * @Description:
 */
@Mapper
@Repository
public interface PermissionMapper {

    List<PermissionDTO> selectAll();

}
