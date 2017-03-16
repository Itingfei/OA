package com.dongao.oa.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "da_oa_position")
public class Position extends BaseEntity {
    /**
     * 上级职位ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 职位名称
     */
    private String name;

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
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 0不是领导 1.领导
     */
    @Column(name = "is_leader")
    private Integer isLeader;
    
    /**
     * 职位等级
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 获取上级职位ID
     *
     * @return parent_id - 上级职位ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级职位ID
     *
     * @param parentId 上级职位ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取职位名称
     *
     * @return name - 职位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置职位名称
     *
     * @param name 职位名称
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * 获取0不是领导 1.领导
     *
     * @return is_leader - 0不是领导 1.领导
     */
    public Integer getIsLeader() {
        return isLeader;
    }

    /**
     * 设置0不是领导 1.领导
     *
     * @param isLeader 0不是领导 1.领导
     */
    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}