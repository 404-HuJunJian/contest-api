package com.watering.controller;

import com.watering.constant.LoginResponseCodeConst;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.entity.HrEntity;
import com.watering.utils.GetCurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/22/19:27
 * @Description: 测试用
 */
@Api(tags = "测试swagger用的类")
@ApiIgnore
@RestController
public class TestController {

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public ResponseDTO test(){
        return ResponseDTO.succMsg("请求test成功");
    }

    @GetMapping("/test2")
    public ResponseDTO test2(){
        System.out.println(GetCurrentUser.getUserRole());
        System.out.println(GetCurrentUser.getUser());
        return ResponseDTO.succ();
    }

    @RequestMapping("/test3")
    public ResponseDTO test3(){
        return ResponseDTO.succMsg(GetCurrentUser.getUserRole());
    }

}
