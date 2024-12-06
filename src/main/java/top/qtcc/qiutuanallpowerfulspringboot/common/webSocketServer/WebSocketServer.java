package top.qtcc.qiutuanallpowerfulspringboot.common.webSocketServer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;


/**
 * WebSocket服务
 *
 * @author qiutuan
 * @date 2024/11/22
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{key}")
public class WebSocketServer {
    /**
     * 日志工具
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 连接标识
     */
    private String key;
    /**
     * 用来存放每个客户端对应的MyWebSocket对象
     */
    private static final CopyOnWriteArraySet<WebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    /**
     * 用来存在线连接用户信息
     */
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 链接成功调用的方法
     *
     * @param session 会话
     * @param key     连接标识
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("key") String key) {
        try {
            this.session = session;
            this.key = key;
            webSockets.add(this);
            sessionPool.put(key, session);
            logger.info("【websocket消息】有新的连接，总数为:{}", webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.key);
            logger.info("【websocket消息】连接断开，总数为:{}", webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("【websocket消息】收到客户端消息:{}", message);
    }

    /**
     * 发送错误时的处理
     *
     * @param session 会话
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误,原因:{}", error.getMessage());
        error.printStackTrace();
    }

    /**
     * 此为广播消息
     *
     * @param message 消息
     */
    public void sendAllMessage(String message) {
        logger.info("【websocket消息】广播消息:{}", message);
        for (WebSocketServer webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 此为单点消息
     *
     * @param key     连接标识
     * @param message 消息
     */
    public void sendOneMessage(String key, String message) {
        Session session = sessionPool.get(key);
        if (session != null && session.isOpen()) {
            try {
                logger.info("【websocket消息】 单点消息:{}", message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 此为单点消息
     *
     * @param key     连接标识
     * @param message 消息
     */
    public void sendOneMessage(String key, Object message) {
        Session session = sessionPool.get(key);
        if (session != null && session.isOpen()) {
            try {
                logger.info("【websocket消息】 单点消息:{}", message);
                session.getAsyncRemote().sendText(JSONUtil.toJsonStr(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 此为单点消息(多人)
     *
     * @param keys    连接标识
     * @param message 消息
     */
    public void sendMoreMessage(String[] keys, String message) {
        for (String key : keys) {
            Session session = sessionPool.get(key);
            if (session != null && session.isOpen()) {
                try {
                    logger.info("【websocket消息】 单点消息:{}", message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 心跳检测
     */
    @Scheduled(fixedRate = 30000)
    public void heartbeat() {
        for (WebSocketServer webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText("heartbeat");
                }
            } catch (Exception e) {
                log.error("心跳检测异常", e);
            }
        }
    }

    /**
     * 发送消息确认机制
     *
     * @param key
     * @param message
     */
    public void sendOneMessageWithAck(String key, String message) {
        Session session = sessionPool.get(key);
        if (session != null && session.isOpen()) {
            try {
                String messageId = UUID.randomUUID().toString();
                MessageWrapper wrapper = new MessageWrapper(messageId, message);
                session.getAsyncRemote().sendText(JSONUtil.toJsonStr(wrapper));
                // 存储消息到Redis，等待确认
                redisTemplate.opsForValue().set("msg:" + messageId, message, 5, TimeUnit.MINUTES);
            } catch (Exception e) {
                log.error("发送消息异常", e);
            }
        }
    }
}
