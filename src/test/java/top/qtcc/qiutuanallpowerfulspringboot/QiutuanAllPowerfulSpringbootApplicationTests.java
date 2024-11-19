

package top.qtcc.qiutuanallpowerfulspringboot;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.qtcc.qiutuanallpowerfulspringboot.manager.file.FileManager;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
class QiutuanAllPowerfulSpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource(name = "FileManager")
    private FileManager minioManager;

    @Test
    public void MinioManagerTest() {
//        MultipartFile file = null;

        minioManager.putObject("2024-11-18 16:47:06/hngy_rjxh.sql", "C:\\Users\\qiutu\\Desktop\\hngy_rjxh.sql");


        //读取文件C:\Users\qiutu\Desktop\springboot.zip

        File file = new File("C:\\Users\\qiutu\\Pictures\\00016-2372822254.png");



//        System.out.println(minioManager.upload(file));
//        String preview = minioManager.preview("2024-11-18 16:47:06/00016-2372822254.png");

//        minioManager.download("2024-11-18 16:47:06/springboot.zip", null);


    }

}
