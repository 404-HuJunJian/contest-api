package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.attendance.AttendanceAddDTO;
import com.watering.domain.DTO.crime.CrimeAddDTO;
import com.watering.domain.DTO.performance.PerformanceAddDTO;
import com.watering.domain.VO.AvgScoreVO;
import com.watering.domain.VO.CareerVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/11/16:49
 * @Description:
 */
public interface CareerService {

    public ResponseDTO<CareerVO> findCareer(Integer carid);
    public ResponseDTO<List<CareerVO>> findAllCareer(Integer empid);

}
