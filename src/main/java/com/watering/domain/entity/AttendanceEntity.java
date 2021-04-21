package com.watering.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/21/17:01
 * @Description: 出勤率实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceEntity {

    private Integer id;
    private Date ctime;
    //雇员id
    private Integer empid;
    //主管id
    private Integer manid;
    //出勤率
    private Double attendance;
    private Date stime;
    private Date etime;
    //经历id
    private Integer carid;
}
