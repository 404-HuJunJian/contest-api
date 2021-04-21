package com.watering.domain.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/14:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "公司VO模型")
public class EnterpriseVO {

    private Integer id;
    private Date ctime;
    private String username;
    @ApiModelProperty("行业名")
    private String industry;
    private String name;
    private Date stime;
    private Date etime;
    private String photo;

}
