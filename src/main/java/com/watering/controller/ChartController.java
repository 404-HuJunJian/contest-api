package com.watering.controller;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.entity.OccupationEntity;
import com.watering.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private OccupationService occupationService;

    @RequestMapping("/amount")
    public ResponseDTO getAmount(
            @RequestParam("occid") Integer occid,
            @RequestParam(value = "size",defaultValue = "7") Integer size
    ){
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        ResponseDTO<OccupationEntity> occupation = occupationService.findOccupation(occid);
        HashMap<Object, Object> map = new HashMap<>();
        OccupationEntity occupationData = occupation.getData();
        Integer count = occupationData.getCount();
        for (int i = 0; i < size; i++) {
            double factor = random.nextDouble() * 0.1 + 0.95;
            list.add((int)(count * factor));
        }
        map.put("occupation", occupation.getData());
        map.put("series", list);
        return ResponseDTO.succData(map);
    }
}
