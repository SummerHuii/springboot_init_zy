package top.qtcc.qiutuanallpowerfulspringboot.exception;

import lombok.Getter;
import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;

/**
 * 自定义异常类
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Getter
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

}
