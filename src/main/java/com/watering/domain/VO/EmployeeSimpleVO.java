package com.watering.domain.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/16:54
 * @Description:
 */
@Data
@ApiModel(description = "雇员简单VO模型用于搜索展示")
public class EmployeeSimpleVO {
    private Integer id;
    private Date ctime;
    private String name;
    //生日
    private Date birth;
    //性别
    private Integer gender;
    //工龄
    private Double seniority;
    //学历
    private Integer degree;
    //学校
    private String school;
    //专业
    private String major;
    //电话
    private String tel;
    //邮箱
    private String mail;
}
