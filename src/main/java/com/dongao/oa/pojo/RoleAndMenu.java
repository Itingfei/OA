package com.dongao.oa.pojo;

import javax.persistence.*;

@Table(name = "da_oa_sys_role_menu")
public class RoleAndMenu extends BaseEntity {
    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单编号
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 删除标记(0默认未删除，1删除)
     */
    @Column(name = "del_flag")
    private Integer delFlag=0;

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
     * 获取菜单编号
     *
     * @return menu_id - 菜单编号
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单编号
     *
     * @param menuId 菜单编号
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取删除标记(0默认未删除，1删除)
     *
     * @return del_flag - 删除标记(0默认未删除，1删除)
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记(0默认未删除，1删除)
     *
     * @param delFlag 删除标记(0默认未删除，1删除)
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}