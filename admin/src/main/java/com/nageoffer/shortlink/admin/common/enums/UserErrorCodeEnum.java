package com.nageoffer.shortlink.admin.common.enums;

import com.nageoffer.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum  UserErrorCodeEnum implements IErrorCode {
    USER_TOKEN_FAIL("A000200","用户token验证失败"),
    USER_NULL("B000200","User is not exsited"),
    USER_NAME_EXSIT("B000201","Username is exsited"),
    USER_EXSIT("B000202","User is exsited"),
    USER_SAVE_ERROR("B000203","Fail to insert a new user");


    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
