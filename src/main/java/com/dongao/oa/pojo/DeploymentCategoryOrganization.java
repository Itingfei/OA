package com.dongao.oa.pojo;

import javax.persistence.*;

@Table(name = "da_deployment_category_organization")
public class DeploymentCategoryOrganization extends BaseEntity {
    private Long deploymentid;

    private Long categoryid;

    /**
     * @return deploymentid
     */
    public Long getDeploymentid() {
        return deploymentid;
    }

    /**
     * @param deploymentid
     */
    public void setDeploymentid(Long deploymentid) {
        this.deploymentid = deploymentid;
    }

    /**
     * @return categoryid
     */
    public Long getCategoryid() {
        return categoryid;
    }

    /**
     * @param categoryid
     */
    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }
}