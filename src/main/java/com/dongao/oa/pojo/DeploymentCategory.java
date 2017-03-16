package com.dongao.oa.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "da_deployment_category")
public class DeploymentCategory extends BaseEntity {
    /**
     * 流程分类的名称
     */
    private String name;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "table_name")
    private String tableName;

    /**
     * 获取流程分类的名称
     *
     * @return name - 流程分类的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置流程分类的名称
     *
     * @param name 流程分类的名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}