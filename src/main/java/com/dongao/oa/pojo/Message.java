package com.dongao.oa.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "da_oa_message")
public class Message extends BaseEntity {
    /**
     * 流程表单ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 接收者
     */
    private Long recipient;

    /**
     * 接收者名称
     */
    @Transient
    private String recipientName;
    /**
     * 发送人
     */
    private Long sender;
    /**
     * 发送者名称
     */
    @Transient
    private String senderName;

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
     * 删除标记(0默认未删除，1删除)
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

    /**
     * 状态 0,未读,1已读
     */
    private Integer status = 0;

    /**
     * 0,流程消息1.系统消息
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;
    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;
    /**
     * 获取流程表单ID
     *
     * @return category_id - 流程表单ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置流程表单ID
     *
     * @param categoryId 流程表单ID
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取接收者
     *
     * @return recipient - 接收者
     */
    public Long getRecipient() {
        return recipient;
    }

    /**
     * 设置接收者
     *
     * @param recipient 接收者
     */
    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    /**
     * 获取发送人
     *
     * @return sender - 发送人
     */
    public Long getSender() {
        return sender;
    }

    /**
     * 设置发送人
     *
     * @param sender 发送人
     */
    public void setSender(Long sender) {
        this.sender = sender;
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
     * 获取状态 0,未读,1已读
     *
     * @return status - 状态 0,未读,1已读
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0,未读,1已读
     *
     * @param status 状态 0,未读,1已读
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取0,流程消息1.系统消息
     *
     * @return type - 0,流程消息1.系统消息
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0,流程消息1.系统消息
     *
     * @param type 0,流程消息1.系统消息
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}