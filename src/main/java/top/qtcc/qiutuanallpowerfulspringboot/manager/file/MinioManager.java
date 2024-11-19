package top.qtcc.qiutuanallpowerfulspringboot.manager.file;

import cn.hutool.core.io.FileUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.stereotype.Component;
import top.qtcc.qiutuanallpowerfulspringboot.config.MinioClientConfig;

import javax.annotation.Resource;
import java.io.File;

/**
 * COS 管理器
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Component("MinioManager")
public class MinioManager implements FileManager {

    @Resource
    private MinioClientConfig minioClientConfig;

    @Resource
    private MinioClient minioClient;


    /**
     * 上传对象
     *
     * @param key           唯一键
     * @param localFilePath 本地文件路径
     */
    public void putObject(String key, String localFilePath) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioClientConfig.getBucket()).object(key)
                    .stream(FileUtil.getInputStream(localFilePath), new File(localFilePath).length(), -1).contentType("application/octet-stream").build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     */
    public void putObject(String key, File file) {

        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioClientConfig.getBucket()).object(key)
                    .stream(FileUtil.getInputStream(file), file.length(), -1).contentType("application/octet-stream").build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     *
     * @param fileName 文件名
     * @return 是否删除成功
     */
    public boolean remove(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioClientConfig.getBucket()).object(fileName).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

