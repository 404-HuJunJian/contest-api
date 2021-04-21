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
 * @Description: 职业实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OccupationEntity {
    private Integer id;
    private Date ctime;
    private String name;
    //父职业id
    private Integer pid;
    //引用次数
    private Integer count;
    //职业层级
    private Integer level;
}
