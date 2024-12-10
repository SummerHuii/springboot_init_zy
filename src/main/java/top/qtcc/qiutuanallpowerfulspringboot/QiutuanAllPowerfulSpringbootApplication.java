package top.qtcc.qiutuanallpowerfulspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author qiutuan
 * @date 2024/12/10
 */
@SpringBootApplication
@MapperScan("top.qtcc.qiutuanallpowerfulspringboot.mapper")
public class QiutuanAllPowerfulSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiutuanAllPowerfulSpringbootApplication.class, args);
        System.out.println("""
                                         .-----------------TTTT_-----_______         \s
                                       /''''''''''(______O] ----------____  \\______/]_
                    __...---'""\"\\_ --''   Q                               ___________@
                |'''                   ._   _______________=---------""\"""\""         \s
                |                ..--''|   l L |_l   |                               \s
                |          ..--''      .  /-___j '   '                               \s
                |    ..--''           /  ,       '   '                               \s
                |--''                /           `    \\                             \s
                                     L__'         \\    -                            \s
                                                   -    '-.                          \s
                                                    '.    /                          \s
                                                      '-./                           \s
                """);
    }

}
