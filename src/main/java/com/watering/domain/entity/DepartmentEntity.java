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
 * @Description: 部门实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentEntity {
    private Integer id;
    private Date ctime;
    private String name;
    //公司id
    private Integer entid;
}
