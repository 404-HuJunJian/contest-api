package com.watering.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/19:32
 * @Description:
 */
@Data
public class AttendanceVO {
    private Integer id;
    private Date ctime;
    //主管id
    @ApiModelProperty("主管名称")
    private String manager;
    //出勤率
    private Double attendance;
    private Date stime;
    private Date etime;
}
