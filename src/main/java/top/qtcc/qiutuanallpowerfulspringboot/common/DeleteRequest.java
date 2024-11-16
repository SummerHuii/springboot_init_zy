package top.qtcc.qiutuanallpowerfulspringboot.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}