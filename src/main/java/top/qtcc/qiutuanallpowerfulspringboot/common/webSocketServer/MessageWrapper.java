package top.qtcc.qiutuanallpowerfulspringboot.common.webSocketServer;

import lombok.Data;

@Data
public class MessageWrapper {
    private String messageId;
    private String message;

    public MessageWrapper(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
}