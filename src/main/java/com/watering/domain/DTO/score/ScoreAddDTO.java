package com.watering.domain.DTO.score;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/19:54
 * @Description:
 */
@Data
public class ScoreAddDTO {
    private Integer empid;
    //态度评分
    private Double attitude;
    //能力评分
    private Double ability;
    //评价描述
    private String detail;
}
