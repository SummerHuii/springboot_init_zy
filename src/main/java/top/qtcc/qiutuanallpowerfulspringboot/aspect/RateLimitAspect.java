package top.qtcc.qiutuanallpowerfulspringboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import top.qtcc.qiutuanallpowerfulspringboot.annotation.RateLimit;
import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;
import top.qtcc.qiutuanallpowerfulspringboot.exception.BusinessException;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;

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

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
        
        if (!tryAcquire(key, count, time)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "访问过于频繁，请稍后再试");
        }
        
        return point.proceed();
    }

    private boolean tryAcquire(String key, int count, int time) {
        String script = "local current = redis.call('incr', KEYS[1]) " +
                "if current == 1 then " +
                "   redis.call('expire', KEYS[1], ARGV[1]) " +
                "end " +
                "if current <= tonumber(ARGV[2]) then " +
                "   return 1 " +
                "else " +
                "   return 0 " +
                "end";
        
        return Boolean.TRUE.equals(stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class),
                Collections.singletonList(key), String.valueOf(time), String.valueOf(count)));
    }
} 