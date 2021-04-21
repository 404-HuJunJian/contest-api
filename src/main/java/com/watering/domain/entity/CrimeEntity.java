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
 * @Description: 违纪记录实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrimeEntity {
    private Integer id;
    private Date ctime;
    //雇员id
    private Integer empid;
    //主管id
    private Integer manid;
    //违纪的描述
    private String detail;
    //违纪的等级
    private Integer rank;
    //违纪时间
    private Date critime;
    //经历id
    private Integer carid;
}
