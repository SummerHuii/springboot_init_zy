package top.qtcc.qiutuanallpowerfulspringboot.common.websocket;

import lombok.Data;

/**
 *  消息包装类
 *
 * @author qiutuan
 * @date 2024/12/09
 */
@Data
public class MessageWrapper {
    private String messageId;
    private String message;

    public MessageWrapper(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
}