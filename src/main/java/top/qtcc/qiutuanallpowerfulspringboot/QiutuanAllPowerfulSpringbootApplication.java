package top.qtcc.qiutuanallpowerfulspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.qtcc.qiutuanallpowerfulspringboot.mapper")
public class QiutuanAllPowerfulSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiutuanAllPowerfulSpringbootApplication.class, args);
        System.out.println("                         .-----------------TTTT_-----_______          \n" +
                "                       /''''''''''(______O] ----------____  \\______/]_\n" +
                "    __...---'\"\"\"\\_ --''   Q                               ___________@\n" +
                "|'''                   ._   _______________=---------\"\"\"\"\"\"\"          \n" +
                "|                ..--''|   l L |_l   |                                \n" +
                "|          ..--''      .  /-___j '   '                                \n" +
                "|    ..--''           /  ,       '   '                                \n" +
                "|--''                /           `    \\                              \n" +
                "                     L__'         \\    -                             \n" +
                "                                   -    '-.                           \n" +
                "                                    '.    /                           \n" +
                "                                      '-./                            \n");
    }

}
