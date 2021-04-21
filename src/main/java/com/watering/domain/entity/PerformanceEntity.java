package com.watering.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: LKM
 * @Date: 2021/03/23/13:29
 * @Description: 绩效实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PerformanceEntity {
    private Integer id;
    private Date ctime;
    //雇员id
    private Integer empid;
    //主管id
    private Integer manid;
    //绩效
    private Integer performance;
    private Date stime;
    private Date etime;
    //经历id
    private Integer carid;
}
