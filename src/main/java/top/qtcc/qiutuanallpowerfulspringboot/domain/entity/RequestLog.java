package top.qtcc.qiutuanallpowerfulspringboot.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  请求日志
 *
 * @author qiutuan
 * @date 2024/12/10
 */
@Data
@TableName("request_log")
public class RequestLog {
    private Long id;
    private String requestId;
    private String url;
    private String method;
    private String params;
    private String ip;
    private Long userId;
    private Integer status;
    private String errorMsg;
    private Long costTime;
    private LocalDateTime createTime;
} 