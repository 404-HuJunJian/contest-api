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
 * @Date: 2021/04/01/14:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "HR VO模型")
public class HrVO {
    private Integer id;
    private Date ctime;
    private String username;
//    private EnterpriseVO enterprise;
    @ApiModelProperty("公司名称")
    private String enterprise;
    private String name;
    private String photo;
}
