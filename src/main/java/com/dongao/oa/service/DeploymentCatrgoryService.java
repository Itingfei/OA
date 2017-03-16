package com.dongao.oa.service;

import com.dongao.oa.pojo.DeploymentCategory;

import java.util.List;

/**
 * Created by fengjifei on 2016/8/4.
 */
public interface DeploymentCatrgoryService {
    public DeploymentCategory save(DeploymentCategory deploymentCategory);

    public int delete(Long id);

    public int update(DeploymentCategory deploymentCategory);

    public List<DeploymentCategory> selectAll();

    public DeploymentCategory selectOne(DeploymentCategory deploymentCategory);

    public DeploymentCategory selectOne(Long id);
}
