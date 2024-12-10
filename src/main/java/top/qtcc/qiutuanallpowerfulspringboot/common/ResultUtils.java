package top.qtcc.qiutuanallpowerfulspringboot.common;

import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;

/**
 * 返回工具类
 *
 * @author qiutuan
 * @date 2024/11/02
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data 返回数据
     * @param <T> 数据类型
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return BaseResponse
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code   错误码
     * @param message 错误信息
     * @return BaseResponse
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return BaseResponse
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
