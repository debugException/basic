package com.xh.basic.bean;

/**
 * @author szq
 * @Package com.xh.basic.bean
 * @Description: 错误码
 * @date 2018/5/29:34
 */
public enum ErrorCode {

    SYSTEM_ERROR(500, "系统错误"),
    PARAMETER_CHECK_ERROR(400, "参数校验错误"),
    AUTH_VALID_ERROR(701, "用户权限不足"),
    UNLOGIN_ERROR(401, "用户未登录或登录状态超时失效");

    private final Integer value;

    private final String message;

    ErrorCode(int value, String message){
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String getCode(){
        return value.toString();
    }

    public static ErrorCode getByCode(Integer value){
        for(ErrorCode enums : values()){
            if (value.equals(enums.getValue())){
                return enums;
            }
        }
        return null;
    }
}
