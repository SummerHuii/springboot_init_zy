package top.qtcc.qiutuanallpowerfulspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.qtcc.qiutuanallpowerfulspringboot.mapper")
public class QiutuanAllPowerfulSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiutuanAllPowerfulSpringbootApplication.class, args);
    }

}
