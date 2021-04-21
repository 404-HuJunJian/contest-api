package com.watering.domain.VO;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/19:26
 * @Description:
 */
@Data
public class OccupationVO {
    private Integer id;
    private Date ctime;
    private String occupation;
}
