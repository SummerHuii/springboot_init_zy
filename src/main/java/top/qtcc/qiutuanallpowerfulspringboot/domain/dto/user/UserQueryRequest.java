package top.qtcc.qiutuanallpowerfulspringboot.domain.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.qtcc.qiutuanallpowerfulspringboot.common.PageRequest;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}