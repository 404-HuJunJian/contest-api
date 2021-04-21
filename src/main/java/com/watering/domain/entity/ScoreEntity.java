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
 * @Description: 评价得分实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreEntity {
    private Integer id;
    private Date ctime;
    //雇员id
    private Integer empid;
    //是否是HR评价
    private boolean ishr;
    //评估人id
    private Integer valid;
    //态度评分
    private Double attitude;
    //能力评分
    private Double ability;
    //评价描述
    private String detail;
    //经历id
    private Integer carid;

}
