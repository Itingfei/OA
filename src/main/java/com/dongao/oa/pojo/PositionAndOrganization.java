package com.dongao.oa.pojo;

import javax.persistence.*;

@Table(name = "da_oa_position_organization")
public class PositionAndOrganization extends BaseEntity {
    /**
     * 职位ID
     */
    @Column(name = "position_id")
    private Long positionId;

    /**
     * 组织架构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 删除标记(0默认未删除，1删除)
     */
    @Column(name = "del_flag")
    private Integer delFlag =0;


    /**
     * 获取职位ID
     *
     * @return position_id - 职位ID
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * 设置职位ID
     *
     * @param positionId 职位ID
     */
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * 获取组织架构ID
     *
     * @return org_id - 组织架构ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织架构ID
     *
     * @param orgId 组织架构ID
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public PositionAndOrganization(Long positionId, Long orgId) {
        this.positionId = positionId;
        this.orgId = orgId;
    }

    public PositionAndOrganization() {
    }
}