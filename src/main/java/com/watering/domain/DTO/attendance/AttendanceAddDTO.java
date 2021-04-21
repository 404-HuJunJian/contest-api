package com.watering.domain.DTO.attendance;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/16:04
 * @Description:
 */
@Data
public class AttendanceAddDTO {
    //雇员id
    private Integer empid;
    //出勤率
    private Double attendance;
    private Date stime;
    private Date etime;
}
