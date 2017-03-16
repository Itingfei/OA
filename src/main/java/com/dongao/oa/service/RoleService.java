package com.dongao.oa.service;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Role;
import com.dongao.oa.pojo.RoleAndMenu;
import com.dongao.oa.pojo.User;
import com.dongao.oa.utils.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangjie on 2016/8/18.
 * 角色管理
 */
public interface RoleService {
    /**
     * 根据ID查询角色
     * @param roleId
     * @return
     */
    Role selectOne(Long roleId);

    /**
     * 查询全部角色
     * @return
     */
    PageInfo<Role> selectAll(Role role);

    int createRole(Role Role);

    int updateRole(Role Role);

    Role selectByName(String name);

    List<Map<String,Object>> menuTree();

    List<Map<String,Object>> selectMenusByRoleId(Long id);

    int deleteMenuByRoleId(Long roleId);

    int saveRoleMenus(Long roleId, Long[] menuIds);

    List<Role> selectAllNoPage(Role role);
    List<Map<String,Object>> selectRolesByUserId(Long userId);
    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Long[] roleIds);
    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(Long[] roleIds);

    List<RoleAndMenu> selectRoleAndMenuByMenuId(Long menuId);
}
