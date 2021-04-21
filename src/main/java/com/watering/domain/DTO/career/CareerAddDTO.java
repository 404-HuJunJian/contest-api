package com.watering.domain.DTO.career;

import com.watering.domain.DTO.employee.EmployeeUpdateCareerDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/20:07
 * @Description:
 */
@Data
public class CareerAddDTO {
    @ApiModelProperty("要更新员工当前经历的信息")
    private EmployeeUpdateCareerDTO employee;
    @ApiModelProperty("当前职业")
    private Integer occid;
}
