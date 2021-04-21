package com.watering.domain.DTO.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/17:04
 * @Description:
 */
@Data
@ApiModel(description = "搜索用模型")
public class SearchDTO {
    @ApiModelProperty("关键字")
    private KeyWord key;
    @ApiModelProperty("过滤器")
    private List<SearchFilter> filters;
    @ApiModelProperty("排序规则")
    private Integer order;
    @ApiModelProperty("第几页")
    private Integer page;
    @ApiModelProperty("每一页的大小")
    private Integer pageSize;
}
