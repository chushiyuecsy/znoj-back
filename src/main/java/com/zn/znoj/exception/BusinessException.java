package com.zn.znoj.exception;

import com.zn.znoj.common.ErrorCode;

/**
 * 自定义异常类
 *
 * @author <a href="https://github.com/Chushiyuecsy">程序员狗哥</a>
 * @from <a href="https://1098756598.qzone.qq.com/">狗哥的企鹅空间</a>
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
