package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户 . <br>
 * 
 * @author wangtw <br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserEntity implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 8700289406389592824L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0：禁用 1：正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createDate;

}
