package top.qtcc.qiutuanallpowerfulspringboot.manager.file;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.stereotype.Component;
import top.qtcc.qiutuanallpowerfulspringboot.config.CosClientConfig;

import javax.annotation.Resource;
import java.io.File;

/**
 * Cos 对象存储操作
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Component("CosManager")
public class CosManager implements FileManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    /**
     * 上传对象
     *
     * @param key 唯一键
     * @param localFilePath 本地文件路径
     * @return 上传结果
     */
    @Override
    public void putObject(String key, String localFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                new File(localFilePath));
        cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     */
    @Override
    public void putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        cosClient.putObject(putObjectRequest);
    }
}
