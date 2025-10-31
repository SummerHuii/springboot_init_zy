package top.qtcc.qiutuanallpowerfulspringboot.manager.file.proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.qtcc.qiutuanallpowerfulspringboot.manager.file.FileManager;

/**
 * 文件管理器代理
 *
 * @author qiutuan
 * @date 2024/11/18
 */
@Configuration
public class FileManagerProxy {

    @Value("${file.manager}")
    private String fileManagerClassName;

    @Bean
    public FileManager fileManager(@Qualifier("CosManager") FileManager cosManager,
                                   @Qualifier("MinioManager") FileManager minioManager) {
        if ("CosManager".equals(fileManagerClassName)) {
            return cosManager;
        }
        return minioManager;
    }
}
