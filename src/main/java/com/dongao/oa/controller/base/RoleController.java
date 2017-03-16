package com.dongao.oa.controller.base;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.pojo.Role;
import com.dongao.oa.service.RoleService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.UserUtils;
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

import java.util.*;

/**
 * 角色管理
 * yangjie
 */
@RequestMapping("/role")
@Controller
public class RoleController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "role";
	// 重定向地址
	private static final String redirectUrl = "redirect:/role";
	@Autowired
	private RoleService roleService;
	/**
	 * 角色列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Role role, Model model) {
	    createPageHelper(role);
		addAttributesToModel(model,"page,role",roleService.selectAll(role),role);
		return  prefix + "/roleList";
	}
	/**
	 * 添加角色页面
	 */
	@RequestMapping("roleEdit")
	public String add(Model model, Long id) {
		if(id != null && id > 0)
			model.addAttribute("role", roleService.selectOne(id));
		return  prefix + "/roleEdit";
	}
	/**
	 * 保存角色 / 更新角色
	 *
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public Result<?> save(Role role) {
		Result<Role> result = new Result<Role>();
		String loginName = UserUtils.getCurrentUser().getUserName();
		if (role.getId() == null || role.getId() == 0) {
			role.setDelFlag(0);
			Date date = new Date();
			role.setCreateBy(loginName);
			role.setCreateDate(date);
			role.setUpdateBy(loginName);
			role.setUpdateDate(date);
			Role one = roleService.selectByName(role.getName());
			if(one == null){
				if(roleService.createRole(role) > 0) {
					result.setMsg("添加角色成功!");
				}
				else{
					result.setCode("1");
					result.setMsg("添加角色失败!");
				}
			}else{
				result.setCode("1");
				result.setMsg("该角色已存在!");
			}
		} else {
			if(roleService.updateRole(role) > 0){
				result.setMsg("修改角色成功!");
			}
			else {
				result.setCode("1");
				result.setMsg("修改角色失败!");
			}
		}
		return result;
	}
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
	public Result<?> update(@PathVariable("id") Long id) {
		Result<Role> result = new Result<Role>();
		if (id==null && id<0){
			result.setCode("1");
			result.setMsg("请选择要删除的角色!");
		}else {
			Role role = roleService.selectOne(id);
			if (role==null){
				result.setCode("1");
				result.setMsg("要删除的角色不存在!");
			}else{
				List<Map<String, Object>> maps = roleService.selectMenusByRoleId(role.getId());
				if (maps.size()==0) {
					role.setDelFlag(1);
					int count = roleService.updateRole(role);
					if (count > 0) {
						result.setMsg("删除角色成功!");
					} else {
						result.setCode("1");
						result.setMsg("删除角色失败!");
					}
				}else{
					result.setCode("1");
					result.setMsg("请先解除和菜单关联关系!");
				}
			}
		}
		return result;
	}
	/**
	 * 获取菜单树 数据
	 */
	@RequestMapping("menuTree")
	@ResponseBody
	public Map<String, Object> menuTree() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> menuTree = roleService.menuTree();
		if(menuTree != null && menuTree.size() > 0){
			map.put("flag", true);
			map.put("tree", menuTree);
		}else{
			map.put("flag", false);
			map.put("msg", "菜单树为空");
		}
		return map;
	}
	/**
	 * 通过角色id，获取其下的 所属菜单
	 * @return
	 */
	@RequestMapping("getMenusByRoleId")
	@ResponseBody
	public Map<String, Object> getMenusByRoleId(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> menus = roleService.selectMenusByRoleId(id);
		if(menus != null && menus.size() > 0){
			map.put("chk", menus);
		}
		return map;
	}
	/**
	 * 保存角色关联的菜单
	 * @return
	 */
	@RequestMapping("saveRoleMenus")
	@ResponseBody
	public Result<?> saveRoleMenus(Long role_id, Long[] menu_id){
		Result<Object> result = new Result<Object>();
//		// 先删除 该角色 已有的菜单，
		int d = roleService.deleteMenuByRoleId(role_id);
//		// 再重新保存 该角色 的权限菜单
		int s = 0;
		if (role_id!=null && menu_id!=null) {
			s = roleService.saveRoleMenus(role_id, menu_id);
			if (s>0){
				result.setMsg("授权菜单成功!");
			}else{
				result.setCode("1");
				result.setMsg("授权菜单失败!");
			}
		}else if (role_id!=null && menu_id==null){
			result.setMsg("解除角色菜单关联关系成功!");
		}
		return result;
	}
}
