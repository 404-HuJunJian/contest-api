package com.watering.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/19:23
 * @Description:
 */
@Data
public class CrimeVO {

    private Integer id;
    private Date ctime;
    //主管id
    @ApiModelProperty("主管名称")
    private String manager;
    //违纪的描述
    private String detail;
    //违纪的等级
    private Integer rank;
    //违纪时间
    private Date critime;

}
