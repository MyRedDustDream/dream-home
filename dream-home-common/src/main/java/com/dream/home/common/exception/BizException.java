package com.dream.home.common.exception;

import api.IResultCode;
import api.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author Caesar Liu
 * @date 2019/5/7 16:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends Exception {

    private static final long serialVersionUID = -3668175015033355502L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 错误数据
     */
    private final Object errorData;

    public BizException(Integer code, String message, Object errorData) {
        super(message);
        this.code = code;
        this.message = message;
        this.errorData = errorData;
    }

    public BizException(String message) {
        this(ResultCode.FAILURE.getCode(), message, null);
    }

    public BizException(String message, Object errorData) {
        this(ResultCode.FAILURE.getCode(), message, errorData);
    }

    public BizException(IResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public BizException(IResultCode resultCode, Object errorData) {
        this(resultCode.getCode(), resultCode.getMessage(), errorData);
    }

    public BizException(Object errorData) {
        this(ResultCode.FAILURE.getCode(), ResultCode.FAILURE.getMessage(), errorData);
    }

}
