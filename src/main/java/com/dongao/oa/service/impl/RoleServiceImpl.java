package com.dongao.oa.service.impl;
import com.dongao.oa.dao.RoleAndMenuMapper;
import com.dongao.oa.dao.RoleMapper;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Role;
import com.dongao.oa.pojo.RoleAndMenu;
import com.dongao.oa.service.MenuService;
import com.dongao.oa.service.RoleService;
import com.dongao.oa.utils.PageInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by yangjie on 2016/8/18.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAndMenuMapper roleAndMenuMapper;
    @Autowired
    private MenuService menuService;
    @Override
    public Role selectOne(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public PageInfo<Role> selectAll(Role role) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag", 0);//去除删除的角色，只查询有效角色
        if (StringUtils.isNotEmpty(role.getName()))
            criteria.andLike("name","%"+role.getName()+"%");
        if (StringUtils.isNotEmpty(role.getEnname()))
            criteria.andEqualTo("enname",role.getEnname());
        if (role.getStartCreateDate()!=null)
            criteria.andGreaterThanOrEqualTo("createDate",role.getStartCreateDate());
        if (role.getEndCreateDate()!=null)
            criteria.andLessThanOrEqualTo("createDate",role.getEndCreateDate());
        return new PageInfo(roleMapper.selectByExample(example));
    }

    @Override
    public int createRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role selectByName(String name) {
        Role role = new Role();
        role.setName(name);
        role.setDelFlag(0);
        return roleMapper.selectOne(role);
    }

    @Override
    public List<Map<String, Object>> menuTree() {
        return roleMapper.menuTree();
    }

    @Override
    public List<Map<String, Object>> selectMenusByRoleId(Long id) {
        return roleMapper.selectMenusByRoleId(id);
    }

    @Override
    public int deleteMenuByRoleId(Long roleId) {
        Example example = new Example(RoleAndMenu.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("roleId", roleId).andEqualTo("delFlag", 0);
        List<RoleAndMenu> roleAndMenus = roleAndMenuMapper.selectByExample(example);
        int result = 0;
        for (RoleAndMenu roleAndMenu:roleAndMenus){
            result = roleAndMenuMapper.delete(roleAndMenu);
        }
        return result;
    }

    @Override
    public int saveRoleMenus(Long roleId, Long[] menuIds) {
        int result = 0;
        if(roleId != null && roleId > 0){
            if(menuIds != null && menuIds.length > 0){
                for(Long menuId : menuIds){
                    RoleAndMenu rel = new RoleAndMenu();
                    rel.setMenuId(menuId);
                    rel.setRoleId(roleId);
                    result = roleAndMenuMapper.insert(rel);
                }
            }
        }
        return result;
    }

    @Override
    public List<Role> selectAllNoPage(Role role) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag", 0);//去除删除的角色，只查询有效角色
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> selectRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public Set<String> findPermissions(Long[] roleIds) {
        Set<Long> resourceIds = new HashSet<Long>();
        for(Long roleId : roleIds){
            Role role = selectOne(roleId);
            Example example = new Example(RoleAndMenu.class);
            example.createCriteria().andEqualTo("roleId",roleId);
            List<RoleAndMenu> roleMenuList = roleAndMenuMapper.selectByExample(example);
            for (RoleAndMenu daSysRoleMenu : roleMenuList) {
                resourceIds.add(daSysRoleMenu.getMenuId());
            }
        }
        return menuService.findPermissions(resourceIds);
    }

    @Override
    public Set<String> findRoles(Long[] roleIds) {
        Set<String> roles = new HashSet<String>();
        for (Long roleId : roleIds) {
            Role role = selectOne(roleId);
            if (role != null) {
                roles.add(role.getName());
            }
        }
        return roles;
    }

    @Override
    public List<RoleAndMenu> selectRoleAndMenuByMenuId(Long menuId) {
        Example example = new Example(RoleAndMenu.class);
        example.createCriteria().andEqualTo("menuId",menuId);
        return roleAndMenuMapper.selectByExample(example);
    }
}

