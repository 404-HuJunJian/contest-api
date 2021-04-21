package com.watering.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/17:46
 * @Description:
 */
@Data
public class CareerVO {
    private Integer id;
    private Date ctime;
    //公司id
    @ApiModelProperty("公司名称")
    private String enterprise;
    //部门id
    @ApiModelProperty("部门名称")
    private String department;
    private Date stime;
    private Date etime;
    //职业id
    @ApiModelProperty("职业名称")
    private String occupation;
    //本次经历考勤平均分
    @ApiModelProperty("本次经历考勤平均分")
    private Double attendance;
    //本次经历绩效平均分
    @ApiModelProperty("本次经历绩效平均分")
    private Double performance;
}
