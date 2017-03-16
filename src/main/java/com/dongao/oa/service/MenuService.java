package com.dongao.oa.service;
import com.dongao.oa.pojo.Menu;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangjie on 2016/8/9.
 */
public interface MenuService {
    /**
     * 根据父级ID查询所有的子菜单
     * @param menu
     * @return
     */
    List<Menu> findMenuByParentId(Menu menu);

    /**
     * 根据ID查询菜单
     * @param menuId
     * @return
     */
    Menu selectOne(Long menuId);

    /**
     * 查询全部菜单
     * @return
     */
    List<Menu> selectAll();

    int createMenu(Menu menu);

    int updateMenu(Menu menu);
    /**
     * 根据id删除该菜单以及菜单下的所有子菜单
     * @param menuId
     */
    void deleteAllChildrenMenu(Long menuId);

    /**
     * 批量修改菜单的排序
     * @param ids
     * @param sorts
     */
    public void updateAllMenuSort(String[] ids, String[] sorts);
    /**
     * 得到资源对应的权限字符串
     * @param menuIds
     * @return
     */
    Set<String> findPermissions(Set<Long> menuIds);
    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<Menu> findMenus(Set<String> permissions);

    /**
     * 根据用户ID查询用户所属菜单
     * @param map
     * @return
     */
    List<Menu> findMenusByUserId(Map map);
}
