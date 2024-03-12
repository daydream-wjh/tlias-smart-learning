package com.demo.tlias.exception;

import com.demo.tlias.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wjh
 * @date 2024/3/4 17:14
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("操作失败，请联系管理员");
    }
}
