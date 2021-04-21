package com.watering.controller;

import com.github.pagehelper.PageInfo;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.attendance.AttendanceAddDTO;
import com.watering.domain.DTO.career.CareerAddDTO;
import com.watering.domain.DTO.crime.CrimeAddDTO;
import com.watering.domain.DTO.performance.PerformanceAddDTO;
import com.watering.domain.DTO.score.ScoreAddDTO;
import com.watering.domain.VO.*;
import com.watering.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/17:42
 * @Description:
 */
@Api(tags = "处理工作经历")
@RestController
@RequestMapping("/career")
public class CareerController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CareerService careerService;

    @Autowired
    private CrimeService crimeService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PerformanceService performanceService;

    @ApiOperation("根据empid查询员工所有经历")
    @GetMapping("/employee/{empid}")
    public ResponseDTO<List<CareerVO>> findAllCareer(@PathVariable Integer empid){
        return careerService.findAllCareer(empid);
    }

    @ApiOperation("根据empid查询员工全部经历的两项平均分")
    @GetMapping("/avgscore/{empid}")
    public ResponseDTO<AvgScoreVO> findAvgScore(@PathVariable Integer empid){
        return scoreService.findAvgScore(empid);
    }

    @ApiOperation("根据empid查询全部的违纪记录")
    @GetMapping("/crime/{empid}")
    public ResponseDTO<List<CrimeVO>> findAllCrime(@PathVariable Integer empid){
        return crimeService.findAllCrime(empid);
    }

    @ApiOperation("根据carid查询该次经历信息")
    @GetMapping("/{carid}")
    public ResponseDTO<CareerVO> findCareer(@PathVariable Integer carid){
        return careerService.findCareer(carid);
    }

    @ApiOperation("根据carid查询该经历考勤记录")
    @GetMapping("/attendance/{carid}")

    public ResponseDTO<List<AttendanceVO>> findCurAttendance(@PathVariable Integer carid){
        return attendanceService.findCurAttendance(carid);
    }

    @ApiOperation("根据carid查询该经历绩效记录")
    @GetMapping("/performance/{carid}")
    public ResponseDTO<List<PerformanceVO>> findCurPerformance(@PathVariable Integer carid){
         return performanceService.findCurPerformance(carid);
    }

    @ApiOperation("根据carid查询该经历违纪记录")
    @GetMapping("/curcrime/{carid}")
    public ResponseDTO<List<CrimeVO>> findCurCrime(@PathVariable Integer carid){
        return crimeService.findCurCrime(carid);
    }

    @ApiOperation("根据carid,page,pageSize查询评价分页")
    @GetMapping("/score")
    public ResponseDTO<PageInfo<ScoreVO>> pageCurScore(@RequestParam Integer carid, @RequestParam Integer page, @RequestParam Integer pageSize){
        return scoreService.pageCurScore(carid,page,pageSize);
    }

    @ApiOperation("Hr或主管评价员工-详细评价")
    @PostMapping("/score")
    public ResponseDTO evaluate(@RequestBody ScoreAddDTO score){
        return scoreService.evaluate(score);
    }

    @ApiOperation("根据carid查询该经历两项平均分")
    @GetMapping("/curavgscore/{carid}")
    public ResponseDTO<AvgScoreVO> findCurAvgScore(@PathVariable Integer carid){
        return scoreService.findCurAvgScore(carid);
    }

    @ApiOperation("录用员工")
    @PostMapping("/employ")
    public ResponseDTO employ(@RequestBody CareerAddDTO career){
        return employeeService.employ(career);
    }

    @ApiOperation("员工离职")
    @GetMapping("/quit/{empid}")
    public ResponseDTO quit(@PathVariable Integer empid){
        return employeeService.quit(empid);
    }

    @ApiOperation("录入绩效记录")
    @PostMapping("/performance")
    public ResponseDTO performanceInput(@RequestBody PerformanceAddDTO performance){
        return performanceService.performanceInput(performance);
    }

    @ApiOperation("录入考勤记录")
    @PostMapping("/attendance")
    public ResponseDTO attendanceInput(@RequestBody AttendanceAddDTO attendance){
        return attendanceService.attendanceInput(attendance);
    }

    @ApiOperation("录入违纪记录")
    @PostMapping("/crime")
    public ResponseDTO crimeInput(@RequestBody CrimeAddDTO crime){
        return crimeService.crimeInput(crime);
    }



}
