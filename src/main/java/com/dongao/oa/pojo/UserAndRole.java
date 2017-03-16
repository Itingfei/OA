package com.dongao.oa.pojo;

import javax.persistence.*;

@Table(name = "da_oa_sys_user_role")
public class UserAndRole extends BaseEntity {
    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 删除标记(0默认未删除，1删除)
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * 获取用户编号
     *
     * @return user_id - 用户编号
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取角色编号
     *
     * @return role_id - 角色编号
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     *
     * @param roleId 角色编号
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取删除标记(0默认未删除，1删除)
     *
     * @return delete_flag - 删除标记(0默认未删除，1删除)
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除标记(0默认未删除，1删除)
     *
     * @param deleteFlag 删除标记(0默认未删除，1删除)
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public UserAndRole(Long userId, Long roleId, Integer deleteFlag) {
        this.userId = userId;
        this.roleId = roleId;
        this.deleteFlag = deleteFlag;
    }

    public UserAndRole() {
    }
}