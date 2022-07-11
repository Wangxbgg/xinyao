package com.xinyao.bean.common;

/**
 *
 * @auther xuyaohui
 * @date 2018/9/18
 */
public enum ErrorEnum {

    /**
     * 系统错误编码
     */
    E_400(400, "请求处理异常，请稍后再试"),
    ERROR_401(401,"账号权限不足"),
    ERROR_500(500,"系统内部错误"),
    ERROR_10000(10000,"登录信息已失效，请重新登录"),
    ERROR_10001(10001,"未登录"),
    ERROR_10002(10002,"账号权限发生变化，请重新登录"),
    ERROR_10003(10003,"密码发生改变，请重新登录"),
    ERROR_10004(10004,"帐号在其他地方登录，请重新登录"),
    ERROR_20001(20001,"认证失效"),
    ERROR_20002(20002,"参数不能为空");

    private Integer errorCode;
    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
