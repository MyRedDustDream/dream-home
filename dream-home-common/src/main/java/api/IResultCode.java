package api;

import java.io.Serializable;

/**
 * @author root
 * 业务代码接口
 */
public interface IResultCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();

}
