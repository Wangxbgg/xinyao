package com.xinyao.util.exception;

import com.xinyao.util.ExceptionMsgUtil;
import com.xinyao.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author zhua
 * @date 2019/7/30
 * 全局的的异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e) {
        return ExceptionMsgUtil.exceptionMsgHandle(e);
    }

    /**
     * validation Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public R bodyValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        R result = new R();
        result.setMsg(fieldErrors.get(0).getDefaultMessage());
        log.warn(fieldErrors.get(0).getDefaultMessage());
        return result;
    }

}
