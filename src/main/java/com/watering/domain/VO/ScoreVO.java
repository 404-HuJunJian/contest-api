package com.watering.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/19:43
 * @Description:
 */
@Data
public class ScoreVO {
    private Integer id;
    private Date ctime;
    //是否是HR评价
    private boolean ishr;
    //评估人id
    @ApiModelProperty("评估人姓名")
    private String name;
    //态度评分
    private Double attitude;
    //能力评分
    private Double ability;
    //评价描述
    private String detail;
}
