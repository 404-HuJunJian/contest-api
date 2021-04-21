package com.watering.domain.DTO.employee;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/17:17
 * @Description:
 */
@Data
public class EmployeeUpdateBaseDTO {
    private Integer id;
    //电话
    private String tel;
    //地址
    private String address;
    //学历
    private Integer degree;
    //学校
    private String school;
    //专业
    private String major;
    //头像url
    private String photo;
    //邮箱
    private String mail;
    //简历的url
    private String resume;

}
