package api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author root
 * 业务代码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(HttpServletResponse.SC_OK, "操作成功"),

    /**
     * 业务异常
     */
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "业务异常"),

    /**
     * token为空
     */
    NO_TOKEN(HttpServletResponse.SC_UNAUTHORIZED, "token为空"),

    /**
     * 未获取到当前登录用户
     */
    NO_USER(HttpServletResponse.SC_UNAUTHORIZED, "未获取到当前登录用户"),

    /**
     * 当前登录用户未分配权限
     */
    NO_SET_AUTH(HttpServletResponse.SC_PAYMENT_REQUIRED, "当前登录用户未分配权限"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(HttpServletResponse.SC_PAYMENT_REQUIRED, "请求未授权"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 没找到请求"),

    /**
     * 消息不能读取
     */
    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "消息不能读取"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法"),

    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型"),

    /**
     * 请求被拒绝
     */
    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "请求被拒绝"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),

    /**
     * 缺少必要的请求参数
     */
    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "缺少必要的请求参数"),

    /**
     * 请求参数类型错误
     */
    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数类型错误"),

    /**
     * 请求参数绑定错误
     */
    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数绑定错误"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数校验失败"),
    /**
     * 租户可用性校验失败
     */
    TENANT_VAILD_ERROR(90001, "租户可用性校验失败"),
    ;

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

}
