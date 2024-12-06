package top.qtcc.qiutuanallpowerfulspringboot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.qtcc.qiutuanallpowerfulspringboot.annotation.RepeatSubmit;
import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;
import top.qtcc.qiutuanallpowerfulspringboot.exception.BusinessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交切面
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(repeatSubmit)")
    public Object around(ProceedingJoinPoint point, RepeatSubmit repeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取请求token，如果没有token，使用请求地址
        String token = request.getHeader("token");
        String key = token == null ? request.getRequestURI() : token;

        // 构建Redis键
        String repeatKey = "repeat_submit:" + key + ":" + request.getRequestURI();

        // 判断是否重复提交
        if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(repeatKey, 1,
                repeatSubmit.interval(), TimeUnit.MILLISECONDS))) {
            // 不是重复提交，执行方法
            return point.proceed();
        } else {
            // 重复提交
            throw new BusinessException(ErrorCode.OPERATION_ERROR, repeatSubmit.message());
        }
    }
} 