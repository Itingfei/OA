package com.dongao.oa.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "da_oa_organization")
public class Organization extends BaseEntity {
    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 组织机构代码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 组织机构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：1新建、2激活、3隐藏
     */
    private Integer status;

    /**
     * 删除状态(0默认未删除，1删除)
     */
    @Column(name = "del_flag")
    private Integer delFlag;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    private Date startCreateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    private Date endCreateDate;
    /**
     * 获取父ID
     *
     * @return parent_id - 父ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父ID
     *
     * @param parentId 父ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取组织机构代码
     *
     * @return org_code - 组织机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置组织机构代码
     *
     * @param orgCode 组织机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取组织机构名称
     *
     * @return org_name - 组织机构名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置组织机构名称
     *
     * @param orgName 组织机构名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取状态：1新建、2激活、3隐藏
     *
     * @return status - 状态：1新建、2激活、3隐藏
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：1新建、2激活、3隐藏
     *
     * @param status 状态：1新建、2激活、3隐藏
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取删除状态(0默认未删除，1删除)
     *
     * @return del_flag - 删除状态(0默认未删除，1删除)
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除状态(0默认未删除，1删除)
     *
     * @param delFlag 删除状态(0默认未删除，1删除)
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }
}