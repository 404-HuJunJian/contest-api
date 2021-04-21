package com.watering.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: LKM
 * @Date: 2021/03/23/13:29
 * @Description: 雇员信息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeEntity {
    private Integer id;
    private Date ctime;
    private String name;
    //生日
    private Date birth;
    //新别
    private Integer gender;
    //电话
    private String tel;
    //是否被雇佣
    private boolean hired;
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
    //公司id
    private Integer entid;
    //部门id
    private Integer depid;
    //招聘的hrid
    private Integer hrid;
    //公司内部员工id
    private String innid;

}
