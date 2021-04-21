package com.watering.domain.DTO.employee;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/20:13
 * @Description:
 */
@Data
public class EmployeeUpdateCareerDTO {

    //雇员id
    private Integer empid;

    //部门id
    private Integer depid;

    //公司内部员工id
    private String innid;
}
