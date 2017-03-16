package com.dongao.oa.controller.base;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.dongao.oa.pojo.RoleAndMenu;
import com.dongao.oa.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.service.MenuService;
import com.dongao.oa.utils.UserUtils;
/**
 * 菜单管理
 * yangjie
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "menu/";
	// 重定向地址
	private static final String redirectUrl = "redirect:/menu";
	@Autowired
	private MenuService menuService;
//	@Autowired
//	private UserService userService;
	@Autowired
	private RoleService roleService;
	/**
	 * 菜单列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Menu menu,Model model) {
		List<Menu> menuList = new ArrayList<Menu>();
		menu.setParentId(0l);
		List<Menu> findMenuByParentId = menuService.findMenuByParentId(menu);
		selectAllChildren(findMenuByParentId,menuList);
		//model.addAttribute("menuList", menuList);
		addAttributesToModel(model,"menuList,menu",menuList,menu);
		return  prefix + "menuList";
	}

	/**
	 * 调到添加菜单页面
	 * @param parentId
	 * @param model
     * @return
     */
	@RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
	public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
		Menu parent = menuService.selectOne(parentId);
		Menu child = new Menu();
		child.setParentId(parentId);
		List<Menu> menuList = menuService.selectAll();
		addAttributesToModel(model,"parent,menu,menuList",parent,child,menuList);
		return prefix + "menuEdit";
	}
	/**
	 * 添加子菜单数据
	 * @return
	 */
//	@RequiresPermissions("menu:create")
	@ResponseBody
	@RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
	public Result<?> create(Menu menu) {
		Result<Menu> result = new Result<Menu>();
		try {
			//创建时间 创建人  修改时间  修改人
			String loginName = UserUtils.getCurrentUser().getUserName();
			Date date = new Date();
			menu.setCreateBy(loginName);
			menu.setCreateDate(date);
			menu.setUpdateBy(loginName);
			menu.setUpdateDate(date);
			Menu parent = menuService.selectOne(menu.getParentId());
			if (parent!=null)
				menu.setParentIds(parent.getParentIds() + parent.getId() + ",");
			else
				menu.setParentIds("0");
			//删除标识
			menu.setDelFlag(0);
			menu.setLogFlag(0);
			menuService.createMenu(menu);
			result.setMsg("菜单添加成功");
		} catch (Exception e) {
			result.setCode("1");
			result.setMsg("菜单添加失败");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 取出菜单数据，进入修改菜单页面
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		Menu menu = menuService.selectOne(id);
		Menu parent = new Menu();
		if(menu.getParentId()!=0){
			parent = menuService.selectOne(menu.getParentId());
		}else{
			parent.setName("顶级菜单");
			parent.setId((long) 0);
		}
		List<Menu> menuList = menuService.selectAll();
        addAttributesToModel(model,"menuList,menu,parent",menuList,menu,parent);
		return prefix + "menuEdit";
	}
	/**
	 * 取出菜单数据，进入查看菜单详细页面
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:update")
	@RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
	public String showDetailForm(@PathVariable("id") Long id, Model model) {
		Menu menu = menuService.selectOne(id);
		Menu parent = new Menu();
		if(menu.getParentId()!=0){
			parent = menuService.selectOne(menu.getParentId());
		}else{
			parent.setName("顶级菜单");
			parent.setId((long) 0);
		}
		List<Menu> menuList = menuService.selectAll();
		addAttributesToModel(model,"menuList,menu,parent",menuList,menu,parent);
		return prefix + "menuDetail";
	}
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
//	@RequiresPermissions("menu:update")
    @ResponseBody
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public Result<?> update(Menu menu) {
        Result<Menu> result = new Result<Menu>();
        try {
            //更新修改人与修改时间
            String loginName = UserUtils.getCurrentUser().getUserName();
            menu.setUpdateBy(loginName);
            menu.setUpdateDate(new Date());
            //修改菜单数据
            menuService.updateMenu(menu);
            result.setMsg("菜单编辑成功");
        } catch (Exception e) {
			result.setCode("1");
            result.setMsg("菜单编辑失败");
            e.printStackTrace();
        }
        return result;
	}
	/**
	 * 删除菜单数据，逻辑删除
	 * @param id
	 * @return
	 */
//	@RequiresPermissions("menu:delete")
    @ResponseBody
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public Result<?> delete(@PathVariable("id") Long id) {
        Result<Object> result = new Result<Object>();
        try {
        	List<RoleAndMenu> roleAndMenus =  roleService.selectRoleAndMenuByMenuId(id);//查询该菜单是否和角色绑定
			if (roleAndMenus!=null && roleAndMenus.size()>0){
				result.setCode("1");
				result.setMsg("该菜单已经与角色关联,不能删除!");
			}else {
				menuService.deleteAllChildrenMenu(id);
				result.setMsg("删除成功!");
			}
        } catch (Exception e) {
			result.setCode("1");
            result.setMsg("删除失败!");
            e.printStackTrace();
        }
        return result;
	}
//	/**
//	 * 取出菜单数据，用于页面左侧的菜单展示
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/tree")
//	public String tree(HttpServletRequest req, Model model) {
//		List<Long> permissions = userService.getRoleIdsByUserId(getCurrentUser().getId());
//
//		List<DaSysMenu> list = roleService.findMenusByRoles(permissions);
//		String parentId = req.getParameter("parentId");
////		List<DaSysMenu> list = menuService.findAll();
//		List<DaSysMenu> menus = new ArrayList<DaSysMenu>();
//		for (DaSysMenu menu : list) {
//			if (menu.getParentId() == 0) {
//				continue;
//			}
//			if (menu.getIsShow().intValue()!=1) {
//				continue;
//			}
//
//			menus.add(menu);
//		}
//		model.addAttribute("menuList", menus);
//		model.addAttribute("menuParentId", Integer.valueOf(parentId));
//		return prefix + "menuTree";
//	}
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
				List<Menu> findMenuByParentId = menuService.findMenuByParentId(menu);
				selectAllChildren(findMenuByParentId,menuList2);
			}
		}
	}
	/**
	 * 批量修改菜单排序
	 * @param ids
	 * @param sorts
	 * @return
	 */
//	@RequiresPermissions("menu:update")
	@ResponseBody
	@RequestMapping(value = "/updateSort",method = RequestMethod.POST)
	public Result<?> updateSort(String[] ids, String[] sorts) {
		Result<Object> result = new Result<Object>();
		try {
			if (ids!=null && sorts!=null) {
				//批量处理逻辑放置到service层处理
				menuService.updateAllMenuSort(ids, sorts);
				result.setMsg("修改排序成功!");
			}else{
				result.setMsg("请传入要排序的菜单和排序顺序!");
			}
		} catch (Exception e) {
			result.setCode("1");
			result.setMsg("修改排序失败!");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequestMapping(value = "/iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return  "icon/iconselect";
	}
}
