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
 * @Date: 2021/04/01/16:07
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "雇员VO模型详细信息")
public class EmployeeVO extends EmployeeSimpleVO{
    //是否被雇佣
    private boolean hired;
    //头像url
    private String photo;
    //地址
    private String address;
    //简历的url
    private String resume;
    //公司id
//    private EnterpriseVO enterprise;

    @ApiModelProperty("公司名称")
    private String enterprise;

    @ApiModelProperty("部门名称")
    private String department;
    //招聘的hrid
    private HrVO hr;
    //公司内部员工id
    private String innid;


}
