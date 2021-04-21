package com.watering.controller;

import com.watering.dao.OccupationEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.OccupationVO;
import com.watering.domain.entity.OccupationEntity;
import com.watering.service.OccupationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/16:46
 * @Description:
 */
@Api(tags = "职业处理")
@RestController
@RequestMapping("/occupation")
public class OccupationController {

    @Autowired
    private OccupationService occupationService;

    @ApiOperation("根据父id查询子职业")
    @GetMapping("/list/{occid}")
    public ResponseDTO<List<OccupationEntity>> findSon(@PathVariable Integer occid){
        return occupationService.listSonOccupation(occid);
    }

    @ApiOperation("热门职业")
    @GetMapping("/hot")
    public ResponseDTO<List<OccupationEntity>> hot(){
        return occupationService.hotOccupation();
    }

    @GetMapping("/{occid}")
    public ResponseDTO<OccupationEntity> findOccupation(@PathVariable Integer occid){
        return occupationService.findOccupation(occid);
    }

}
