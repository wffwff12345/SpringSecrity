package com.example.project.enums;

public enum AppHttpCodeEnum {

    // 成功段0
    SUCCESS(0,"登录成功"),
    // 登录段1~50
    NEED_LOGIN(1,"需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2,"密码错误"),
    // TOKEN50~100
    TOKEN_INVALID(50,"无效的TOKEN"),
    TOKEN_EXPIRE(51,"TOKEN已过期"),
    TOKEN_REQUIRE(52,"TOKEN是必须的"),
    // SIGN验签 100~120
    SIGN_INVALID(100,"无效的SIGN"),
    SIG_TIMEOUT(101,"SIGN已过期"),
    AUTHENTICATION_ERROR(103,"用户认证失败,请重新登录"),
    ACCESS_ERROR(104,"权限不足"),
    USER_LOGOUT(105,"用户已经退出登录"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500,"缺少参数"),
    PARAM_INVALID(501,"无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502,"图片格式有误"),
    SERVER_ERROR(503,"服务器内部错误"),
    // 数据错误 1000~2000
    DATA_EXIST(1000,"数据已经存在"),
    USER_DATA_NOT_EXIST(1001,"用户数据不存在"),
    DATA_NOT_EXIST(1002,"数据不存在"),
    DATA_CAN_NOT_DELETE(1003,"数据不能删除"),
    USER_NAME_ISEMPTY(1004,"用户名为空"),
    USER_EXIST(1005,"用户已经存在!"),
    USER_STATUS(1007,"用户未授权!"),
    DATA_CAN_NOT_EMPTY(1006,"内容不能为空"),
    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000,"无权限操作");

    int code;
    String errorMessage;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}