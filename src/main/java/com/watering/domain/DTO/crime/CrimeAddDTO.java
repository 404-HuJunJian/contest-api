package com.watering.domain.DTO.crime;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/16:35
 * @Description:
 */
@Data
public class CrimeAddDTO {
    //雇员id
    private Integer empid;
    //违纪的描述
    private String detail;
    //违纪的等级
    private Integer rank;
    //违纪时间
    private Date critime;
}
