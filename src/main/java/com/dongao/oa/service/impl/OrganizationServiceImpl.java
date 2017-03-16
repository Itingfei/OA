package com.dongao.oa.service.impl;
import com.dongao.oa.dao.OrganizationMapper;
import com.dongao.oa.dao.PositionAndOrganizationMapper;
import com.dongao.oa.pojo.Organization;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.pojo.PositionAndOrganization;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.OrganizationService;
import com.dongao.oa.utils.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2016/8/16.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private PositionAndOrganizationMapper positionAndOrganizationMapper;
    @Override
    public List<Organization> findOrganizationByOrganization(Organization organization) {
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag",0);//去除删除的职位，只查询有效职位
        if (organization.getParentId()!=null)
            criteria.andEqualTo("parentId",organization.getParentId());
        if (StringUtils.isNotEmpty(organization.getOrgName()))
            criteria.andLike("orgName","%"+organization.getOrgName()+"%");
        if (organization.getStartCreateDate()!=null)
            criteria.andGreaterThanOrEqualTo("createDate",organization.getStartCreateDate());
        if (organization.getEndCreateDate()!=null)
            criteria.andLessThanOrEqualTo("createDate",organization.getEndCreateDate());
        example.setOrderByClause(" create_date desc");
        List<Organization> selectByExample = organizationMapper.selectByExample(example);
        return selectByExample;
    }

    @Override
    public Organization selectOne(Long organizationId) {
        return organizationMapper.selectByPrimaryKey(organizationId);
    }

    @Override
    public List<Organization> selectAll() {
       return organizationMapper.selectAll();
    }

    @Override
    public int createOrganization(Organization organization) {
        String orgCode = selectMaxOrgCode(organization);
        organization.setOrgCode(orgCode);
        return organizationMapper.insert(organization);
    }

    @Override
    public int updateOrganization(Organization organization) {
        if(StringUtils.isEmpty(organization.getOrgCode())){
            String maxDeptCode = selectMaxOrgCode(organization);
            organization.setOrgCode(maxDeptCode);
        }
        return organizationMapper.updateByPrimaryKeySelective(organization);
    }

    @Override
    public void deleteAllChildrenOrganization(Long organizationId) {
        //此处删除选用逻辑删除 只是把删除标识改为1
        //修改此id的职位对象的删除标识
        Organization findOne = selectOne(organizationId);
        findOne.setDelFlag(1);
        updateOrganization(findOne);
        //条件查询父id为此值的职位，递归，修改查询出来的所有职位对象的删除标识
        List<Organization> menuList = new ArrayList<Organization>();
        Organization p = new Organization();
        p.setParentId(organizationId);
        List<Organization> findPositionByParentId= findOrganizationByOrganization(p);
        selectAllChildren(findPositionByParentId,menuList);
        for (Organization position : menuList) {
            position.setDelFlag(1);
            updateOrganization(position);
        }
    }

    @Override
    public PageInfo<Map<String,Object>> selectByCondition(Long organizationId,String name) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("organizationId",organizationId);
        map.put("positionName",name);
        return new PageInfo(organizationMapper.selectByCondition(map));
    }

    @Override
    public List<Map<String,Object>> selectByOrgId(Long orgId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("organizationId",orgId);
        return organizationMapper.selectByCondition(map);
    }

    /**
     * 递归查询所有子职位
     * @param positionList 根据id作为父id查询直接所属的子职位
     * @param positionList2   得到所有子职位
     */
    public void selectAllChildren(List<Organization> positionList,List<Organization> positionList2){
        if(positionList.size()>0){
            for (Organization position : positionList) {
                positionList2.add(position);
                Organization findPosition = new Organization();
                findPosition.setParentId(position.getId());
                List<Organization> findOrganizationByParentId = findOrganizationByOrganization(findPosition);
                selectAllChildren(findOrganizationByParentId,positionList2);
            }
        }
    }
    /** <p>功能描述： 根据组织架构上级的编码，自动分配下级编码</p>
     * @param organization
     * @return
     * @author yangjie
     * @date 2016-8-17
     */
    private String selectMaxOrgCode(Organization organization) {
        String maxDeptCode="";
        //第一步先示出上级组织架构的编码
        maxDeptCode = organizationMapper.selectMaxOrgCode(organization);
        if(maxDeptCode!=null){
            organization.setOrgCode(maxDeptCode);
            //根据上级组织架构的编码计算出下级目前最大的组织架构编码
            String maxDeptCode2 = organizationMapper.selectMaxOrgCode(organization);
            if(maxDeptCode2!=null){
                //将最大的编码加1即可
                maxDeptCode=(Long.parseLong(maxDeptCode2)+1)+"";
            }else{
                //如果没有则为01
                maxDeptCode = maxDeptCode + "01";
            }

        }else{
            maxDeptCode="10";
        }
        return maxDeptCode;
    }
}
