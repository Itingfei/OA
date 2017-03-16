package com.dongao.oa.service.impl;
import com.dongao.oa.dao.MenuMapper;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by yangjie on 2016/8/9.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> findMenuByParentId(Menu menu) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",menu.getParentId()).andEqualTo("delFlag",0);//去除删除的菜单，只查询有效菜单
        if (StringUtils.isNotEmpty(menu.getName()))
            criteria.andLike("name","%"+menu.getName()+"%");
        example.setOrderByClause(" sort");
        List<Menu> selectByExample = menuMapper.selectByExample(example);
        return selectByExample;
    }

    @Override
    public Menu selectOne(Long menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public List<Menu> selectAll() {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag", 0);
        return menuMapper.selectByExample(example);
    }

    @Override
    public int createMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu); //空值也会添加进去
    }

    @Override
    public void deleteAllChildrenMenu(Long menuId) {
        //此处删除选用逻辑删除 只是把删除标识改为1
        //修改此id的菜单对象的删除标识
        Menu findOne = selectOne(menuId);
        findOne.setDelFlag(1);
        updateMenu(findOne);
        //条件查询父id为此值的菜单，递归，修改查询出来的所有菜单对象的删除标识
        List<Menu> menuList = new ArrayList<Menu>();
        Menu menu = new Menu();
        menu.setParentId(menuId);
        List<Menu> findMenuByParentId = findMenuByParentId(menu);
        selectAllChildren(findMenuByParentId,menuList);
        for (Menu daSysMenu : menuList) {
            daSysMenu.setDelFlag(1);
            updateMenu(daSysMenu);
        }
    }

    @Override
    public void updateAllMenuSort(String[] ids, String[] sorts) {
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu();
            menu.setId(Long.valueOf(ids[i]));
            menu.setSort(Long.valueOf(sorts[i]));
            updateMenu(menu);
        }
    }

    @Override
    public Set<String> findPermissions(Set<Long> menuIds) {
        Set<String> permissions = new HashSet<String>();
        for (Long menuId : menuIds) {
            Menu menu = selectOne(menuId);
            if (menu != null && !org.springframework.util.StringUtils.isEmpty(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<Menu> findMenus(Set<String> permissions) {
        List<Menu> allMenus = selectAll();
        List<Menu> menus = new ArrayList<Menu>();
        for (Menu menu : allMenus) {
            // 判断是否是根节点
			/*if (menu.getParentId() == 0) {
				continue;
			}*/
//            if (menu.getIsShow().intValue()!=1) {
//                continue;
//            }
//            if (!hasPermission(permissions, menu)) {
//                continue;
//            }
            menus.add(menu);
        }
        return menus;
    }

    @Override
    public List<Menu> findMenusByUserId(Map map) {
        return menuMapper.selectMenusByUserId(map);
    }

    private boolean hasPermission(Set<String> permissions, Menu menu) {
        if (org.springframework.util.StringUtils.isEmpty(menu.getPermission())) {
            return true;
        }
        for (String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(menu.getPermission());
            if (p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 递归查询所有子菜单
     * @param menuList 根据id作为父id查询直接所属的子菜单
     * @param menuList2   得到所有子菜单
     */
    public void selectAllChildren(List<Menu> menuList,List<Menu> menuList2){
        if(menuList.size()>0){
            for (Menu daSysMenu : menuList) {
                menuList2.add(daSysMenu);
                Menu menu = new Menu();
                menu.setParentId(daSysMenu.getId());
                List<Menu> findMenuByParentId = findMenuByParentId(menu);
                selectAllChildren(findMenuByParentId,menuList2);
            }
        }
    }
}
