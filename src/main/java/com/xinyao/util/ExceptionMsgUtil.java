package com.xinyao.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常分返回消息处理
 *
 * @author ZhangFZ
 */
@Slf4j
public class ExceptionMsgUtil {

    public static R exceptionMsgHandle(Throwable e) {

        String exceptionMsg = e.getMessage();

        log.error("系统异常信息 ex={}", e.getMessage(), e);

        if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
            String split = "'";
            if (exceptionMsg != null && exceptionMsg.contains(split)) {
                return R.failed("该数据已存在，不可以重复使用");
            } else {
                return R.failed(e.getMessage());
            }
        }
        if (e instanceof DataIntegrityViolationException) {
            String split = "Data truncation";
            //校验数据库表精度
            if (exceptionMsg != null && exceptionMsg.contains(split)) {
                return R.failed("数据输入过长，请修改");
            } else {
                return R.failed(e.getMessage());
            }
        }
        if (e.getCause() instanceof RuntimeException) {
            return R.failed(e.getMessage());
        }
        return R.failed(e.getMessage());
//        return R.failed("系统繁忙，请稍候再试");
    }
}
