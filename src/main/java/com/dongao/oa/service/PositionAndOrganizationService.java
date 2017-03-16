package com.dongao.oa.service;
import com.dongao.oa.pojo.PositionAndOrganization;
import java.util.List;
/**
 * Created by yangjie on 2016/8/18.
 */
public interface PositionAndOrganizationService {
    /**
     * 根据ID查询职位和组织机构关联
     * @param positionAndOrganizationId
     * @return
     */
    PositionAndOrganization selectOne(Long positionAndOrganizationId);
    /**
     * 查询全部职位和组织机构关联
     * @return
     */
    List<PositionAndOrganization> selectAll();

    int createPositionAndOrganization(PositionAndOrganization positionAndOrganization);

    int updatePositionAndOrganization(PositionAndOrganization positionAndOrganization);

    /**
     * 根据组织机构ID和职位ID查询住址机构关联关系
     * @param orgId
     * @param positionId
     * @return
     */
    PositionAndOrganization selectByOrgIdAndPositionId(Long orgId, Long positionId);

    int deletePositionAndOrganization(PositionAndOrganization positionAndOrganization);
}
