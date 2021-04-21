package com.watering.controller;

import com.watering.constant.ResponseCodeConst;
import com.watering.domain.DTO.ResponseDTO;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/20:13
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseDTO  exceptionHandler(Exception e) {
        // 请求参数错误
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResponseDTO.wrap(ResponseCodeConst.REQUEST_METHOD_ERROR);
        }

        // 参数类型错误
        if (e instanceof TypeMismatchException) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        // json 格式错误(也可能是参数错误吧)
        if (e instanceof HttpMessageNotReadableException) {
            return ResponseDTO.wrap(ResponseCodeConst.JSON_FORMAT_ERROR);
        }

        if (e instanceof MissingServletRequestParameterException){
            return ResponseDTO.wrap(ResponseCodeConst.PARAM_NOT_NULL);
        }

//        if(null!=e.getMessage()){
//            return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR,e.getMessage());
//        }
        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR);
    }

}
