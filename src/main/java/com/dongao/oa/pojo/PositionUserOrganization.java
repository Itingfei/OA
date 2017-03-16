package com.dongao.oa.pojo;

import javax.persistence.*;

@Table(name = "da_oa_position_user_organization")
public class PositionUserOrganization extends BaseEntity {
    /**
     * 组织机构和职位关联ID
     */
    @Column(name = "position_organization_id")
    private Long positionOrganizationId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 上级领导ID
     */
    @Column(name = "leader_id")
    private Long leaderId;


    /**
     * 获取组织机构和职位关联ID
     *
     * @return position_organization_id - 组织机构和职位关联ID
     */
    public Long getPositionOrganizationId() {
        return positionOrganizationId;
    }

    /**
     * 设置组织机构和职位关联ID
     *
     * @param positionOrganizationId 组织机构和职位关联ID
     */
    public void setPositionOrganizationId(Long positionOrganizationId) {
        this.positionOrganizationId = positionOrganizationId;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取上级领导ID
     *
     * @return leader_id - 上级领导ID
     */
    public Long getLeaderId() {
        return leaderId;
    }

    /**
     * 设置上级领导ID
     *
     * @param leaderId 上级领导ID
     */
    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public PositionUserOrganization(Long positionOrganizationId, Long userId) {
        this.positionOrganizationId = positionOrganizationId;
        this.userId = userId;
    }

    public PositionUserOrganization() {
    }
}