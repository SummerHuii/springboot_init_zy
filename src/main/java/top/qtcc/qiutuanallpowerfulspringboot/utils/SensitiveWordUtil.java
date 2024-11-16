package top.qtcc.qiutuanallpowerfulspringboot.utils;

import cn.hutool.core.io.FileUtil;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 敏感词工具类
 *
 * @author qiutuan
 * @date 2024/11/13
 */
//@Component
public class SensitiveWordUtil {



    private static JedisPool jedisPool;
    private static final String SENSITIVE_WORDS_KEY = "sensitive_words";

    private static String redisHost;
    private static int redisPort;



    @Value("${rjxh.redis.host}")
    public void setRedisHost(String redisHost) {
        SensitiveWordUtil.redisHost = redisHost;
    }

    @Value("${rjxh.redis.port}")
    public void setRedisPort(int redisPort) {
        SensitiveWordUtil.redisPort = redisPort;
    }

    /**
     * 初始化敏感词库，连接 Redis 和加载敏感词
     * 如果需要使用数据库中敏感词 请自行实现获取敏感词方法
     */
//    @PostConstruct
    public void initSensitiveWordsFromSql() {
        // 配置 Redis 连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort);


        //TODO  自行实现从数据库中读取敏感词
        List<String> sensitiveWords = new ArrayList<>();

        if (sensitiveWords.isEmpty()) {
            throw new RuntimeException("敏感词库为空");
        } else try (Jedis jedis = jedisPool.getResource()) {
            // 将敏感词集合存储到 Redis 中
            jedis.sadd(SENSITIVE_WORDS_KEY, sensitiveWords.toArray(new String[0]));
        }

    }


    /**
     * 初始化敏感词库，连接 Redis 和加载敏感词
     * 从文件中读取敏感词
     */
    @PostConstruct
    public void initSensitiveWordsFromFile(){
        // 配置 Redis 连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort);

        // 从文件中读取敏感词
        File file = new File("sensitive_words.txt");
        List<String> sensitiveWords = FileUtil.readUtf8Lines(file);

        //去除list中无效数据
        sensitiveWords.removeAll(Collections.singleton(null));
        sensitiveWords.removeAll(Collections.singleton(""));
        sensitiveWords.removeAll(Collections.singleton(" "));
        sensitiveWords.removeAll(Collections.singleton("\n"));
        sensitiveWords.removeAll(Collections.singleton("\r"));

        if (sensitiveWords.isEmpty()) {
            throw new RuntimeException("敏感词库为空");
        } else try (Jedis jedis = jedisPool.getResource()) {
            // 将敏感词集合存储到 Redis 中
            jedis.sadd(SENSITIVE_WORDS_KEY, sensitiveWords.toArray(new String[0]));
        }
    }


    /**
     * 添加敏感词
     *
     * @param sensitiveWords 敏感词
     * @return {@link Integer }
     */
    public static int addSensitiveWords(List<String> sensitiveWords) {
        if (sensitiveWords == null || sensitiveWords.isEmpty()) {
            return 0;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(SENSITIVE_WORDS_KEY, sensitiveWords.toArray(new String[0])).intValue();
        }
    }

    /**
     * 添加敏感词
     *
     * @param sensitiveWords 敏感词
     * @return {@link Integer }
     */
    public static int addSensitiveWords(String sensitiveWords) {
        if (sensitiveWords == null || sensitiveWords.isEmpty()) {
            return 0;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(SENSITIVE_WORDS_KEY, sensitiveWords).intValue();
        }
    }

    /**
     *  删除敏感词
     *
     * @param sensitiveWord 敏感词
     */
    public static int removeSensitiveWords(String sensitiveWord) {
        if (sensitiveWord == null || sensitiveWord.isEmpty()) {
            return 0;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(SENSITIVE_WORDS_KEY, sensitiveWord).intValue();
        }
    }

    /**
     * 判断一个词语是否为敏感词
     *
     * @param word 输入字符串
     * @return {@link String }
     */
    public static String wordCheckBadWord(String word) {
        if (word == null || word.isEmpty()) {
            return null;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            if (jedis.sismember(SENSITIVE_WORDS_KEY, word)) {
                return word;
            }
        }
        return null;
    }

    /**
     * 检查一段文本是否包含敏感词，文本以空格分隔词语
     *
     * @param content 文本
     * @return {@link List }<{@link String }>
     */
    public static List<String> contentCheckBadWord(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }

        List<String> badWords = new ArrayList<>();
        String[] words = content.split(" ");
        try (Jedis jedis = jedisPool.getResource()) {
            for (String word : words) {
                if (jedis.sismember(SENSITIVE_WORDS_KEY, word)) {
                    badWords.add(word);
                }
            }
        }
        return badWords.isEmpty() ? null : badWords;
    }

    /**
     * 分词并检查敏感词
     *
     * @param content 文本
     * @return {@link List }<{@link String }>
     */
    public static List<String> contentSplitCheckBadWord(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }

        List<String> badWords = new ArrayList<>();
        List<String> words = getAnsj(content);
        try (Jedis jedis = jedisPool.getResource()) {
            for (String word : words) {
                if (jedis.sismember(SENSITIVE_WORDS_KEY, word)) {
                    badWords.add(word);
                }
            }
        }
        return badWords.isEmpty() ? null : badWords;
    }


    /**
     * 检查一个集合中是否包含敏感词
     *
     * @param words 单词集合
     * @return {@link List }<{@link String }>
     */
    public static List<String> contentCheckBadWord(List<String> words) {
        if (words == null || words.isEmpty()) {
            return null;
        }

        List<String> badWords = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            for (String word : words) {
                if (jedis.sismember(SENSITIVE_WORDS_KEY, word)) {
                    badWords.add(word);
                }
            }
        }
        return badWords.isEmpty() ? null : badWords;
    }

    /**
     * 敏感词替换
     *
     * @param content
     * @return {@link String }
     */
    public static String contentSplitDesensitization(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        List<String> words = getAnsj(content);
        try (Jedis jedis = jedisPool.getResource()) {
            for (String word : words) {
                if (jedis.sismember(SENSITIVE_WORDS_KEY, word)) {
                    int length = word.length();
                    StringBuilder wordBuilder = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        wordBuilder.append("*");
                    }
                    content = content.replace(word, wordBuilder.toString());
                }
            }
        }
        return content;
    }


    /**
     * 敏感词替换
     *
     * @param contents 词句集合
     * @return {@link List }<{@link String }>
     */
    public static List<String> contentSplitDesensitization(List<String> contents) {
        List<String> list = new ArrayList<>();
        for (String content : contents) {
            list.add(contentSplitDesensitization(content));
        }
        return list;
    }

    /**
     * 获取分词结果
     *
     * @param str 输入字符串
     * @return {@link List }
     */
    public static List<String> getAnsj(String str) {
        List<String> list = new ArrayList<>();
        List<Term> terms = NlpAnalysis.parse(str).recognition(new StopRecognition()).getTerms();
        for (Term term : terms) {
            //去除符号空格等
            if (term.getName().matches("[\\pP\\p{Punct}\\s]+")) {
                continue;
            }
            list.add(term.getName());
        }
        return list;
    }


    /**
     * 关闭 Redis 连接池
     */
    public static void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}
