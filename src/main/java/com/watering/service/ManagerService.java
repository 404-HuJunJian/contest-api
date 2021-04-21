package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.manager.ManagerAddDTO;
import com.watering.domain.DTO.manager.ManagerUpdateDTO;
import com.watering.domain.VO.ManagerVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/19:17
 * @Description:
 */
public interface ManagerService {

    public ResponseDTO createManager(ManagerAddDTO managerAddDTO);

    public ResponseDTO updateManger(ManagerUpdateDTO managerUpdateDTO);

    public ResponseDTO<ManagerVO> getInfo();

    public ResponseDTO<List<ManagerVO>> managerSearch();

}
