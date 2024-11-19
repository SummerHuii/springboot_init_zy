package top.qtcc.qiutuanallpowerfulspringboot.manager.file;

import java.io.File;

public interface FileManager {

    /**
     *  上传对象
     *
     * @param key 文件名
     * @param localFilePath 本地文件路径
     */
    public void putObject(String key, String localFilePath);

    /**
     *  上传对象
     *
     * @param key 文件名
     * @param file 文件
     */
    public void putObject(String key, File file);
}
