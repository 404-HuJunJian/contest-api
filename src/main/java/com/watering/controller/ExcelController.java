package com.watering.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.analysis.ExcelAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/excel")
@ApiIgnore
@Api(tags = "Excel使用")
public class ExcelController {
    @RequestMapping("/file")
    public String upload(@RequestParam("file")MultipartFile file){
        System.out.println(file);
        try {
            ExcelReaderBuilder read = EasyExcel.read(file.getInputStream(), new AbstractIgnoreExceptionReadListener() {
                @Override
                public void invokeHead(Map headMap, AnalysisContext context) {

                }

                @Override
                public void invoke(Object data, AnalysisContext context) {
                    System.out.println(data);
                    System.out.println(context);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {

                }
            });
            read.sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }

    @RequestMapping
    public String test(){
        System.out.println("是是是");
        return "上传成功";
    }
}
