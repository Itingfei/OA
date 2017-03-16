package com.dongao.oa.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "da_oa_user_organization")
public class UserAndOrganization extends BaseEntity {
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 组织机构id
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 是否负责人0否1是
     */
    @Column(name = "is_manager")
    private Integer isManager;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 删除标记(0默认未删除，1删除)
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取组织机构id
     *
     * @return org_id - 组织机构id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织机构id
     *
     * @param orgId 组织机构id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取是否负责人0否1是
     *
     * @return is_manager - 是否负责人0否1是
     */
    public Integer getIsManager() {
        return isManager;
    }

    /**
     * 设置是否负责人0否1是
     *
     * @param isManager 是否负责人0否1是
     */
    public void setIsManager(Integer isManager) {
        this.isManager = isManager;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改人
     *
     * @return update_by - 修改人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人
     *
     * @param updateBy 修改人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
}