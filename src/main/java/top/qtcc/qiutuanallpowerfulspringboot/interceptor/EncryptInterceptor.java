package top.qtcc.qiutuanallpowerfulspringboot.interceptor;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import top.qtcc.qiutuanallpowerfulspringboot.domain.enums.ErrorCode;
import top.qtcc.qiutuanallpowerfulspringboot.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 加密拦截器
 *
 * @author qiutuan
 * @date 2024/12/10
 */
public class EncryptInterceptor implements HandlerInterceptor {

    /**
     * 16位密钥
     */
    private static final String SECRET_KEY = "your_secret_key_16";
    private static final AES AES = SecureUtil.aes(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        try {
            // 获取加密的请求数据
            String encryptedData = request.getHeader("X-Encrypted-Data");
            if (encryptedData != null) {
                // 解密数据
                String decryptedData = AES.decryptStr(encryptedData);
                // 将解密后的数据设置到request属性中
                request.setAttribute("decryptedData", decryptedData);
            }
            return true;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "数据解密失败");
        }
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                @NotNull Object handler, Exception ex) {
        try {
            // 获取响应数据
            String responseData = response.getHeader("X-Response-Data");
            if (responseData != null) {
                // 加密响应数据
                String encryptedData = AES.encryptHex(responseData);
                // 设置加密后的响应数据
                response.setHeader("X-Encrypted-Response", encryptedData);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据加密失败");
        }
    }
} 