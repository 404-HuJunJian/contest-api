package com.watering.domain.DTO.employee;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/17:23
 * @Description:
 */
@Data
public class EmployeeAddDTO {
    private Integer id;
    private String name;
    //生日
    private Date birth;
    //新别
    private Integer gender;
    //电话
    private String tel;
    //头像url
    private String photo;
    //工龄
    private Double seniority;
    //地址
    private String address;
    //学历
    private Integer degree;
    //学校
    private String school;
    //专业
    private String major;
    //邮箱
    private String mail;
    //简历的url
    private String resume;
}
