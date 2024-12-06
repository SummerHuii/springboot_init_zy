package top.qtcc.qiutuanallpowerfulspringboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.qtcc.qiutuanallpowerfulspringboot.common.BaseResponse;
import top.qtcc.qiutuanallpowerfulspringboot.common.ResultUtils;
import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *  处理自定义异常
     *
     * @param e {@link BusinessException }
     * @return {@link BaseResponse }<{@link ? }>
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     *  处理参数校验异常
     *
     * @param e {@link MethodArgumentNotValidException }
     * @return {@link BaseResponse }<{@link ? }>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(), message);
    }

    /**
     *  处理参数校验异常
     *
     * @param e {@link ConstraintViolationException }
     * @return {@link BaseResponse }<{@link ? }>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(), message);
    }

    /**
     *  处理参数校验异常
     *
     * @param e {@link BindException }
     * @return {@link BaseResponse }<{@link ? }>
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<?> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(), message);
    }

    /**
     *  处理系统异常
     *
     * @param e {@link Exception }
     * @return {@link BaseResponse }<{@link ? }>
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR.getCode(), "系统异常");
    }
}
