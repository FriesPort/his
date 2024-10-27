package com.example.vo;

/**
 * <p>
 * 描述：响应码枚举
 * </p>
 * <p>版权：&copy;01星球</p>
 * <p>地址：01星球总部</p>
 *
 * @author 阿伟学长
 * @version 1.0.0
 */
public enum ResultStatus {
    /**
     * Unauthorized result status.
     */
    UNAUTHORIZED("暂未登录或TOKEN已经过期", 401),
    /**
     * Forbidden result status.
     */
    FORBIDDEN("没有相关权限", 403),
    /**
     * Server error result status.
     */
    SERVER_ERROR("服务器错误", 9994),
    /**
     * Params invalid result status.
     */
    PARAMS_INVALID("上传参数异常", 9995),
    /**
     * Content type err result status.
     */
    CONTENT_TYPE_ERR("ContentType错误", 9996),
    /**
     * Api un result status.
     */
    API_UN_IMPL("功能尚未实现", 9997),
    /**
     * Server busy result status.
     */
    SERVER_BUSY("服务器繁忙", 9998),
    FAIL("操作失败", 9999),
    SUCCESS("操作成功");

    private final String message;
    private final int code;

    ResultStatus(String message, int code) {
        this.message = message;
        this.code = code;
    }

    ResultStatus(String message) {
        this(message, 200);
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }
}
