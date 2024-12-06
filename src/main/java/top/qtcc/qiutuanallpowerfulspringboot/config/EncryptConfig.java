package top.qtcc.qiutuanallpowerfulspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.qtcc.qiutuanallpowerfulspringboot.interceptor.EncryptInterceptor;

/**
 * 加密配置类
 */
//TODO 如需使用请注释掉下面的注解
//@Configuration
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