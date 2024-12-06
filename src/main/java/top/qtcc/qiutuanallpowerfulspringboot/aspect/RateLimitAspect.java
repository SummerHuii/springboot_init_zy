package top.qtcc.qiutuanallpowerfulspringboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.qtcc.qiutuanallpowerfulspringboot.annotation.RateLimit;
import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;
import top.qtcc.qiutuanallpowerfulspringboot.exception.BusinessException;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 *  接口限流
 *
 * @author qiutuan
 * @date 2024/12/06
 */
@Aspect
@Component
@Slf4j
public class RateLimitAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint point, RateLimit rateLimit) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        
        // 限流的key为类名+方法名
        String key = method.getDeclaringClass().getName() + ":" + method.getName();
        
        // 获取限流时间
        int time = rateLimit.time();
        // 获取限流次数
        int count = rateLimit.count();
        
        // 获取当前访问次数
        Integer currentCount = (Integer) redisTemplate.opsForValue().get(key);
        if (currentCount == null) {
            // 第一次访问
            redisTemplate.opsForValue().set(key, 1, time, TimeUnit.SECONDS);
        } else if (currentCount < count) {
            // 没超出限制，访问次数+1
            redisTemplate.opsForValue().increment(key);
        } else {
            // 超出限制
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "访问过于频繁，请稍后再试");
        }
        
        return point.proceed();
    }
} 