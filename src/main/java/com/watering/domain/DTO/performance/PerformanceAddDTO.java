package com.watering.domain.DTO.performance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/16:01
 * @Description:
 */
@Data
public class PerformanceAddDTO {
    //雇员id
    @ApiModelProperty("员工id")
    private Integer empid;
    //绩效
    @ApiModelProperty("绩效得分")
    private Integer performance;
    @ApiModelProperty("开始时间")
    private Date stime;
    @ApiModelProperty("结束时间")
    private Date etime;
}
