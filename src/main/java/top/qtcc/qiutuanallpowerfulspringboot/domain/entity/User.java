package top.qtcc.qiutuanallpowerfulspringboot.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户ID，主键，自增
    private Long id;

    // 用户账号
    private String userAccount;

    // 用户密码
    private String userPassword;

    // 用户昵称
    private String userName;

    // 用户头像URL
    private String userAvatar;

    // 用户简介
    private String userProfile;

    // 用户角色，默认为'user'
    private String userRole; // 默认值为'user'，表示普通用户

    // 创建时间，默认值为当前时间戳
    private Timestamp createTime; // 记录用户创建的时间

    // 更新时间，每次更新记录时自动更新为当前时间戳
    private Timestamp updateTime; // 记录用户最后更新的时间

    // 是否删除，0表示未删除，非0表示已删除
    private Byte isDelete; // 用于逻辑删除，0表示未删除，1或其他值表示已删除

}