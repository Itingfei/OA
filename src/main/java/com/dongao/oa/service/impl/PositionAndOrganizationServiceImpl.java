package com.dongao.oa.service.impl;
import com.dongao.oa.dao.PositionAndOrganizationMapper;
import com.dongao.oa.pojo.PositionAndOrganization;
import com.dongao.oa.service.PositionAndOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
/**
 * Created by yangjie on 2016/8/18.
 */
@Service
public class PositionAndOrganizationServiceImpl implements PositionAndOrganizationService {
    @Autowired
    private PositionAndOrganizationMapper positionAndOrganizationMapper;

    @Override
    public PositionAndOrganization selectOne(Long positionAndOrganizationId) {
        return positionAndOrganizationMapper.selectByPrimaryKey(positionAndOrganizationId);
    }

    @Override
    public List<PositionAndOrganization> selectAll() {
        return positionAndOrganizationMapper.selectByExample(new Example(PositionAndOrganization.class).createCriteria().andEqualTo("delFlag",0));
    }

    @Override
    public int createPositionAndOrganization(PositionAndOrganization positionAndOrganization) {
        return positionAndOrganizationMapper.insert(positionAndOrganization);
    }

    @Override
    public int updatePositionAndOrganization(PositionAndOrganization positionAndOrganization) {
        return positionAndOrganizationMapper.updateByPrimaryKeySelective(positionAndOrganization);
    }

    @Override
    public PositionAndOrganization selectByOrgIdAndPositionId(Long orgId, Long positionId) {
        PositionAndOrganization positionAndOrganization = new PositionAndOrganization();
        positionAndOrganization.setOrgId(orgId);
        positionAndOrganization.setPositionId(positionId);
        return positionAndOrganizationMapper.selectOne(positionAndOrganization);
    }

    @Override
    public int deletePositionAndOrganization(PositionAndOrganization positionAndOrganization) {
        return positionAndOrganizationMapper.delete(positionAndOrganization);
    }
}
