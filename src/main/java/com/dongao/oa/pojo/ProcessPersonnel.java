package com.dongao.oa.pojo;
import java.util.Date;
import javax.persistence.*;
@Table(name = "da_oa_process_personnel")
public class ProcessPersonnel extends BaseEntity {
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "position_id")
    private Long positionId;

    /**
     * 任务名称(和流程图中框中名称保持一致)
     */
    @Column(name = "task_name")
    private String taskname;

    /**
     * 流程定义key
     */
    @Column(name = "process_key")
    private String processKey;

    /**
     * 分支变量 用于存储一个审批人有多个上级审批人时用流程变量和次变量比较来确定是哪个人事下个审批人
     */
    @Column(name = "branch_variable")
    private String branchVariable;

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
    @Transient
    private String orgName;
    @Transient
    private String positionName;
    /**
     * @return org_id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return position_id
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId
     */
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * 获取任务名称(和流程图中框中名称保持一致)
     *
     * @return taskName - 任务名称(和流程图中框中名称保持一致)
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * 设置任务名称(和流程图中框中名称保持一致)
     *
     * @param taskname 任务名称(和流程图中框中名称保持一致)
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * 获取流程定义key
     *
     * @return process_key - 流程定义key
     */
    public String getProcessKey() {
        return processKey;
    }

    /**
     * 设置流程定义key
     *
     * @param processKey 流程定义key
     */
    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    /**
     * 获取分支变量 用于存储一个审批人有多个上级审批人时用流程变量和次变量比较来确定是哪个人事下个审批人
     *
     * @return branch_variable - 分支变量 用于存储一个审批人有多个上级审批人时用流程变量和次变量比较来确定是哪个人事下个审批人
     */
    public String getBranchVariable() {
        return branchVariable;
    }

    /**
     * 设置分支变量 用于存储一个审批人有多个上级审批人时用流程变量和次变量比较来确定是哪个人事下个审批人
     *
     * @param branchVariable 分支变量 用于存储一个审批人有多个上级审批人时用流程变量和次变量比较来确定是哪个人事下个审批人
     */
    public void setBranchVariable(String branchVariable) {
        this.branchVariable = branchVariable;
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

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getPositionName() {
        return positionName;
    }
}