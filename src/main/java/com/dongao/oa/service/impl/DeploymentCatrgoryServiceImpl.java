package com.dongao.oa.service.impl;

import com.dongao.oa.dao.DeploymentCategoryMapper;
import com.dongao.oa.dao.DeploymentCategoryOrganizationMapper;
import com.dongao.oa.pojo.DeploymentCategory;
import com.dongao.oa.service.DeploymentCatrgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by fengjifei on 2016/8/4.
 */
@Service
@CacheConfig(cacheNames = "DeploymentCatrgory")
public class DeploymentCatrgoryServiceImpl implements DeploymentCatrgoryService {
    @Autowired
    private DeploymentCategoryMapper deploymentCategoryMapper;
    @Autowired
    private DeploymentCategoryOrganizationMapper deploymentCategoryOrganizationMapper;

    @Override
    public DeploymentCategory save(DeploymentCategory deploymentCategory) {
         int i = deploymentCategoryMapper.insert(deploymentCategory);
        if(i>0){
            return deploymentCategory;
        }
        return null;
    }
    @Override
    public int delete(Long id) {
        return deploymentCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(DeploymentCategory deploymentCategory) {

        return deploymentCategoryMapper.updateByPrimaryKeySelective(deploymentCategory);
    }

    @Override
    public List<DeploymentCategory> selectAll() {
        return deploymentCategoryMapper.selectAll();
    }

    @Override
    public DeploymentCategory selectOne(DeploymentCategory deploymentCategory) {
        return deploymentCategoryMapper.selectOne(deploymentCategory);
    }

    @Override
    public DeploymentCategory selectOne(Long id) {
        return deploymentCategoryMapper.selectByPrimaryKey(id);
    }
}
