package com.watering.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/19:37
 * @Description:
 */
@Data
public class PerformanceVO {
    private Integer id;
    private Date ctime;
    @ApiModelProperty("主管名称")
    private String manager;
    //绩效
    private Integer performance;
    private Date stime;
    private Date etime;
}
