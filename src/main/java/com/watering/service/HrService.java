package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.hr.HrAddDTO;
import com.watering.domain.DTO.hr.HrUpdateDTO;
import com.watering.domain.VO.HrVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/19:17
 * @Description:
 */
public interface HrService {

    public ResponseDTO createHr(HrAddDTO hrAddDTO);
    public ResponseDTO updateHr(HrUpdateDTO hrUpdateDTO);
    public ResponseDTO<HrVO> getInfo();
    public ResponseDTO<List<HrVO>> hrSearch();
}
