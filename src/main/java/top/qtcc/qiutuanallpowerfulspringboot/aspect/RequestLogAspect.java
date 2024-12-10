package top.qtcc.qiutuanallpowerfulspringboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.qtcc.qiutuanallpowerfulspringboot.domain.entity.RequestLog;
import top.qtcc.qiutuanallpowerfulspringboot.domain.entity.User;
import top.qtcc.qiutuanallpowerfulspringboot.service.RequestLogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static top.qtcc.qiutuanallpowerfulspringboot.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 请求日志切面
 *
 * @author qiutuan
 * @date 2024/12/07
 */
@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Resource
    private RequestLogService requestLogService;

    //TODO 未保存请求参数
    @Around("execution(* top.qtcc.qiutuanallpowerfulspringboot.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        RequestLog requestLog = new RequestLog();
        requestLog.setRequestId(UUID.randomUUID().toString());

        // 获取请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        requestLog.setUrl(request.getRequestURI());
        requestLog.setMethod(request.getMethod());
        requestLog.setIp(request.getRemoteAddr());

        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute != null) {
            requestLog.setUserId(((User) attribute).getId());
        }

        Object result;
        try {
            result = joinPoint.proceed();
            requestLog.setStatus(200);
        } catch (Exception e) {
            requestLog.setStatus(500);
            requestLog.setErrorMsg(e.getMessage());
            throw e;
        } finally {
            requestLog.setCostTime(System.currentTimeMillis() - startTime);
            requestLogService.save(requestLog);
        }

        return result;
    }
} 