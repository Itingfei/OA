package com.dongao.oa.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "da_category")
public class Category extends BaseEntity {
    /**
     * 申请人id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 流程id
     */
    @Column(name = "deployment_id")
    private String deploymentId;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 采购专员id
     */
    @Column(name = "buyer_id")
    private Long buyerId;

    /**
     * 标题
     */
    private String title;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 审批状态 0 未审核 1 审核中  2 审核结束 3.已放款,4.驳回,5.同意,
     */
    private String status;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;
    /**
     * 删除状态0未删除，1，已删除
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag = 0;
    /**
     * 期望交付日期
     */
    @Column(name = "expect_date")
    private String expectDate;
    /**
     * 询价前总价
     */
    @Column(name = "before_total_price")
    private BigDecimal beforeTotalPrice;
    /**
     * 询价后总价
     */
    @Column(name = "after_total_price")
    private BigDecimal afterTotalPrice;

    /**
     * 当前审批人
     */
    @Column(name = "assignee")
    private Long assignee;
    /**
     * 申请人部门
     */
    @Column(name = "org_id")
    private Long orgId;
    @Transient
    private List<CategoryItem> categoryItems;
    @Transient
    private List<String> orgIds;
    @Transient
    private String userName;
    @Transient
    private List<String> ids;
    /**
     * 库存状态 0,库存不足1,库存充足
     */
    @Column(name = "inventory_status")
    private Integer inventoryStatus;
    /**
     * 采购渠道
     */
    @Column(name = "channels")
    private String channels;
    /**
     * 支付方式
     */
    @Column(name = "payment")
    private String payment;
    /**
     * 获取申请人id
     *
     * @return user_id - 申请人id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置申请人id
     *
     * @param userId 申请人id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取流程id
     *
     * @return deployment_id - 流程id
     */
    public String getDeploymentId() {
        return deploymentId;
    }

    /**
     * 设置流程id
     *
     * @param deploymentId 流程id
     */
    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    /**
     * 获取用途
     *
     * @return purpose - 用途
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * 设置用途
     *
     * @param purpose 用途
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * 获取采购专员id
     *
     * @return buyer_id - 采购专员id
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     * 设置采购专员id
     *
     * @param buyerId 采购专员id
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取申请理由
     *
     * @return reason - 申请理由
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置申请理由
     *
     * @param reason 申请理由
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取审批状态 0 未审核 1 审核中  2已转入下一流程 3审核结束
     *
     * @return status - 审批状态 0 未审核 1 审核中  2已转入下一流程 3审核结束
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置审批状态 0 未审核 1 审核中  2已转入下一流程 3审核结束
     *
     * @param status 审批状态 0 未审核 1 审核中  2已转入下一流程 3审核结束
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建者
     *
     * @return create_by - 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者
     *
     * @param createBy 创建者
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
     * 获取更新者
     *
     * @return update_by - 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者
     *
     * @param updateBy 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_date - 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public String getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }
    public BigDecimal getBeforeTotalPrice() {
        return beforeTotalPrice;
    }

    public void setBeforeTotalPrice(BigDecimal beforeTotalPrice) {
        this.beforeTotalPrice = beforeTotalPrice;
    }

    public BigDecimal getAfterTotalPrice() {
        return afterTotalPrice;
    }

    public void setAfterTotalPrice(BigDecimal afterTotalPrice) {
        this.afterTotalPrice = afterTotalPrice;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public Integer getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(Integer inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}