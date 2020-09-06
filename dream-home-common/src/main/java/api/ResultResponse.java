package api;

import com.dream.home.common.constant.ResultConstant;
import com.dream.home.common.exception.BizException;
import com.dream.home.common.exception.BizRuntimeException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.nullSafeEquals;

/**
 * @author root
 * <p>
 * <p>
 * <p>
 * 统一API响应结果封装
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
@NoArgsConstructor
public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID = -5295873637991546008L;
    @ApiModelProperty(value = "状态码", required = true)
    private int code;

    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value = "承载数据")
    private transient T data;

    @ApiModelProperty(value = "返回消息", required = true)
    private String msg;

    private ResultResponse(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private ResultResponse(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private ResultResponse(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private ResultResponse(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private ResultResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        success = ResultCode.SUCCESS.code == code;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable ResultResponse<?> result) {
        return Optional.ofNullable(result)
                .map(x -> nullSafeEquals(ResultCode.SUCCESS.code, x.code))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable ResultResponse<?> result) {
        return !ResultResponse.isSuccess(result);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> data(T data) {
        return data(data, ResultConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> data(T data, String msg) {
        return data(HttpServletResponse.SC_OK, data, msg);
    }

    /**
     * 返回R
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> data(int code, T data, String msg) {
        return new ResultResponse<>(code, data, data == null ? ResultConstant.DEFAULT_NULL_MESSAGE : msg);
    }

    /**
     * 返回R
     *
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> success() {
        return new ResultResponse<>(ResultCode.SUCCESS);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> success(String msg) {
        return new ResultResponse<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> success(IResultCode resultCode) {
        return new ResultResponse<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> success(IResultCode resultCode, String msg) {
        return new ResultResponse<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> fail(String msg) {
        return new ResultResponse<>(ResultCode.FAILURE, msg);
    }


    /**
     * 返回R
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> fail(int code, String msg) {
        return new ResultResponse<>(code, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultResponse 返回对象
     * @param <T>            T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> fail(ResultResponse resultResponse) {
        return fail(resultResponse.getCode(), resultResponse.getMsg());
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> fail(IResultCode resultCode) {
        return new ResultResponse<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultResponse<T> fail(IResultCode resultCode, String msg) {
        return new ResultResponse<>(resultCode, msg);
    }

    /**
     * @param e: 业务异常
     * @author Caesar Liu
     * @date 2019/5/7 16:34
     */
    public static <T> ResultResponse<T> fail(BizException e) {
        return ResultResponse.fail(e.getCode(), e.getMessage());
    }

    public static <T> ResultResponse<T> fail(BizRuntimeException e) {
        return ResultResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * 返回R
     *
     * @param flag 成功状态
     * @return R
     */
    public static <T> ResultResponse<T> status(boolean flag) {
        return flag ? success(ResultConstant.DEFAULT_SUCCESS_MESSAGE) : fail(ResultConstant.DEFAULT_FAILURE_MESSAGE);
    }

}
