package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.attendance.AttendanceAddDTO;
import com.watering.domain.VO.AttendanceVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/13/17:32
 * @Description:
 */
public interface AttendanceService {

    public ResponseDTO<List<AttendanceVO>> findCurAttendance(Integer carid);
    public ResponseDTO attendanceInput(AttendanceAddDTO attendance);
}
