package top.qtcc.qiutuanallpowerfulspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.qtcc.qiutuanallpowerfulspringboot.interceptor.EncryptInterceptor;

/**
 * 加密配置类
 *
 * @author qiutuan
 * @date 2024/12/10
 * 如需使用拦截器，必须加上该注解Configuration
 */
public class EncryptConfig implements WebMvcConfigurer {

    @Bean
    public EncryptInterceptor encryptInterceptor() {
        return new EncryptInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(encryptInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/public/**");
    }
} 