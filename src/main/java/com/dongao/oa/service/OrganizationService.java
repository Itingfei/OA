package com.dongao.oa.service;
import com.dongao.oa.pojo.Organization;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.pojo.User;
import com.dongao.oa.utils.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2016/8/16.
 * 组织机构管理
 */
public interface OrganizationService {
    /**
     * 根据条件查询所有的子组织机构
     * @param position
     * @return
     */
    List<Organization> findOrganizationByOrganization(Organization position);

    /**
     * 根据ID查询组织机构
     * @param organizationId
     * @return
     */
    Organization selectOne(Long organizationId);

    /**
     * 查询全部组织机构
     * @return
     */
    List<Organization> selectAll();

    int createOrganization(Organization organization);

    int updateOrganization(Organization organization);
    /**
     * 根据id删除该组织机构以及组织机构下的所有子组织机构
     * @param organizationId
     */
    void deleteAllChildrenOrganization(Long organizationId);

    /**
     * 查询组织机构下职位
     * @param organizationId
     * @param name
     * @return
     */
    PageInfo<Map<String,Object>> selectByCondition(Long organizationId,String name);

    List<Map<String,Object>> selectByOrgId(Long orgId);
}
