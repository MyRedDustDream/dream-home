package com.dream.home.common.exception;

import api.IResultCode;
import api.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author chenl
 * @date 2019/6/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 6519141442752107753L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 错误数据
     */
    private Object errorData;

    public BizRuntimeException(Integer code, String message, Object errorData) {
        super(message);
        this.code = code;
        this.message = message;
        this.errorData = errorData;
    }

    public BizRuntimeException(String message) {
        this(ResultCode.FAILURE.getCode(), message, null);
    }

    public BizRuntimeException(String message, Object errorData) {
        this(ResultCode.FAILURE.getCode(), message, errorData);
    }

    public BizRuntimeException(IResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public BizRuntimeException(IResultCode resultCode, Object errorData) {
        this(resultCode.getCode(), resultCode.getMessage(), errorData);
    }

    public BizRuntimeException(Object errorData) {
        this(ResultCode.FAILURE.getCode(), ResultCode.FAILURE.getMessage(), errorData);
    }
}
