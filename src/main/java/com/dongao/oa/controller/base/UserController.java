package com.dongao.oa.controller.base;

import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.OrganizationService;
import com.dongao.oa.service.PositionService;
import com.dongao.oa.service.RoleService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.PasswordHelper;
import com.dongao.oa.utils.UserUtils;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * yangjie
 */
@RequestMapping("/user")
@Controller
public class UserController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "user";
	// 重定向地址
	private static final String redirectUrl = "redirect:/user";
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private PositionService positionService;
	@ModelAttribute("organizations")
	public List<Organization> organizationList(){
		Organization organization = new Organization();
		organization.setDelFlag(0);
		organization.setStatus(2);
		return organizationService.findOrganizationByOrganization(organization);
	}
	/**
	 * 用户列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(User user, PositionAndOrganization positionAndOrganization, Model model) {
		createPageHelper(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", user.getUserName());
		map.put("realName", user.getRealName());
		map.put("linkPhone", user.getLinkPhone());
		map.put("startCreateDate", user.getStartCreateDate());
		map.put("endCreateDate", user.getEndCreateDate());
		map.put("lockStatus", user.getLockStatus());
		map.put("orgId", positionAndOrganization.getOrgId());
		map.put("positionId", positionAndOrganization.getPositionId());
		map.put("userId", user.getId());
		addAttributesToModel(model,"page,user,positionAndOrganization",userService.selectAll(map),user,positionAndOrganization);
		return  prefix + "/userList";
	}
	/**
	 * 添加用户页面
	 */
	@RequestMapping("userEdit")
	public String add(Model model, Long id) {
		if(id != null && id > 0)
			addAttributesToModel(model,"user,userRoles", userService.selectUserInfoByUserId(id),roleService.selectRolesByUserId(id));
		List<Role> roleList = roleService.selectAllNoPage(new Role());
		addAttributesToModel(model,"roleList",roleList);
		return  prefix + "/userEdit";
	}
	/**
	 * 保存用户 / 更新用户
	 *
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public Result<?> save(User user, PositionAndOrganization positionAndOrganization, Long [] roles) {
		Result<User> result = new Result<User>();
		try {
			String loginName = UserUtils.getCurrentUser().getUserName();
			Date date = new Date();
			String pyname = PinyinHelper.convertToPinyinString(user.getRealName(), "", PinyinFormat.WITHOUT_TONE);
			user.setPyNmae(pyname);
			user.setSortLetters(PinyinHelper.getShortPinyin(user.getRealName()).substring(0,1).toUpperCase());
			if (user.getId() == null || user.getId() == 0) {
                User one = userService.findByLoginName(user.getUserName());
                if(one == null){
                    user.setCreateBy(loginName);
                    user.setCreateDate(date);
                    user.setUpdateBy(loginName);
                    user.setUpdateDate(date);
                    int flag = userService.createUser(user, positionAndOrganization, roles);
                    if(flag > 0) {
                        result.setMsg("添加用户成功!");
                    } else if (flag ==-1){
                        result.setCode("1");
                        result.setMsg(organizationService.selectOne(positionAndOrganization.getOrgId()).getOrgName()+"下"+positionService.selectOne(positionAndOrganization.getPositionId()).getName()+"职位已无空缺!");
                    }
                    else{
                        result.setCode("1");
                        result.setMsg("添加用户失败!");
                    }
                }else{
                    result.setCode("1");
                    result.setMsg("该用户已存在!");
                }
            }
            else {
                user.setUpdateBy(loginName);
                user.setUpdateDate(date);
                int i = userService.updateUser(user, positionAndOrganization, roles);
                if(i > 0){
                    result.setMsg("修改用户成功!");
                }else if (i ==-1){
                    result.setCode("1");
                    result.setMsg(organizationService.selectOne(positionAndOrganization.getOrgId()).getOrgName()+"下"+positionService.selectOne(positionAndOrganization.getPositionId()).getName()+"职位已无空缺!");
                }
                else {
                    result.setCode("1");
                    result.setMsg("修改用户失败!");
                }
            }
		} catch (Exception e) {
			if (user.getId() == null || user.getId() == 0) {
				result.setCode("1");
				result.setMsg("添加用户失败!");
			}else{
				result.setCode("1");
				result.setMsg("修改用户失败!");
			}
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
	public Result<?> delete(@PathVariable("id") Long id) {
		Result<User> result = new Result<User>();
		if (id==null && id<0){
			result.setCode("1");
			result.setMsg("请选择要删除的用户!");
		}else {
			User user = userService.selectOne(id);
			if (user==null){
				result.setCode("1");
				result.setMsg("要删除的用户不存在!");
			}else{
				List<Map<String, Object>> maps = roleService.selectRolesByUserId(user.getId());
				List<Map<String, Object>> positionUser = userService.selectPUOByUserId(user.getId());
				if (maps.size()==0 && positionUser.size()==0) {
					user.setDeleteFlag(1);
					int count = userService.updateUserStatus(user);
					if (count > 0) {
						result.setMsg("删除用户成功!");
					} else {
						result.setCode("1");
						result.setMsg("删除用户失败!");
					}
				}else{
					result.setCode("1");
					result.setMsg("请先解除用户和角色和职位关联关系!");
				}
			}
		}
		return result;
	}
	/**
	 * 锁定用户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/lock",method = RequestMethod.POST)
	public Result<?> lock(@PathVariable("id") Long id,@RequestParam("status") Integer status) {
		Result<User> result = new Result<User>();
		if (id==null && id<0){
			result.setCode("1");
			result.setMsg("请选择要锁定的用户!");
		}else {
			User user = userService.selectOne(id);
			if (user==null){
				result.setCode("1");
				result.setMsg("要锁定的用户不存在!");
			}else{
				user.setLockStatus(status);
				int count = userService.updateUserStatus(user);
				if (count > 0) {
					if (status==0) {
						result.setMsg("用户解锁成功!");
					}else if (status==1){
						result.setMsg("用户锁定成功!");
					}
				} else {
					result.setCode("1");
					result.setMsg("锁定用户失败!");
				}
			}
		}
		return result;
	}
	/**
	 * 当前用户修改自己的密码
	 * @return
	 */
	@RequestMapping("modifyPass")
	@ResponseBody
	public Result<Object> modifyPass(String oldp, String newp){
		Result<Object> result = new Result<Object>();
		String msg = "";
		User user = UserUtils.getCurrentUser();
		if(user != null){
			user.setPassword(oldp);
			User adminUser = userService.selectOne(user.getId());
			if(adminUser != null){
				if(adminUser.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword())+user.getSalt()))){
					try{
						user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(newp)+user.getSalt()));//设置新加密的密码
						userService.updatePassword(user);
						msg = "更新密码成功";
					}catch (Exception e) {
						result.setCode("1");
						msg = "更新密码失败";
					}
				}else{
					result.setCode("1");
					msg = "原始密码不正确";
				}
			}else{
				result.setCode("1");
				msg = "该用户不存在";
			}
		}else{
			result.setCode("1");
			msg = "用户未登录";
		}
		result.setMsg(msg);
		return result;
	}
}
