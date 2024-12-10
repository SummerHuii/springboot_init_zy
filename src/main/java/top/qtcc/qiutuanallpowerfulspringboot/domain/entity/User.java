package top.qtcc.qiutuanallpowerfulspringboot.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  用户实体类
 *
 * @author qiutuan
 * @date 2024/12/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，主键，自增
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;


    /**
     * 用户昵称
     */
    private String userName;


    /**
     * 用户头像
     */
    private String userAvatar;


    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色，默认为 'user'
     * 默认值为 'user'，表示普通用户
     */
    private String userRole;


    /**
     * 创建时间，默认值为当前时间戳
     * 记录用户创建的时间
     */
    private Timestamp createTime;


    /**
     * 更新时间，每次更新记录时自动更新为当前时间戳
     * 记录用户最后更新的时间
     */
    private Timestamp updateTime;


    /**
     * 是否删除，0表示未删除，非0表示已删除
     * 用于逻辑删除，0表示未删除，1或其他值表示已删除
     */
    private Byte isDelete;

}