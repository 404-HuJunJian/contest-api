package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.performance.PerformanceAddDTO;
import com.watering.domain.VO.PerformanceVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/13/17:35
 * @Description:
 */
public interface PerformanceService {

    public ResponseDTO<List<PerformanceVO>> findCurPerformance(Integer carid);
    public ResponseDTO performanceInput(PerformanceAddDTO performance);

}
