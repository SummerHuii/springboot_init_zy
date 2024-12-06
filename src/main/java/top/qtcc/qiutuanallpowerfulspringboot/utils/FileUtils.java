package top.qtcc.qiutuanallpowerfulspringboot.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件工具类
 *
 * @author qiutuan
 * @date 2024/12/07
 */
public class FileUtils {

    /**
     * 获取文件扩展名
     */
    public static String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    /**
     * 生成唯一文件名
     */
    public static String generateUniqueFileName(String originalFileName) {
        String extension = getExtension(originalFileName);
        return UUID.randomUUID().toString() + (extension.isEmpty() ? "" : "." + extension);
    }

    /**
     * 保存文件
     */
    public static String saveFile(MultipartFile file, String directory) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }

        // 确保目录存在
        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // 生成唯一文件名
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = directoryPath.resolve(fileName);

        // 保存文件
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 获取文件大小的可读性表示
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
} 