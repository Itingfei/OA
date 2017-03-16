package com.dongao.oa.config;
import com.dongao.oa.pojo.PositionAndOrganization;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.MySimpleByteSource;
import com.dongao.oa.utils.PageInfo;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*
 * 自实现用户与权限查询.
 * 演示关系，密码用明文存储，因此使用默认 的SimpleCredentialsMatcher.
*/


public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	public UserService userService;

/**
	 * 认证回调函数, 登录时调用.
	*/

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("进入验证");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Session session = SecurityUtils.getSubject().getSession();
		User user = userService.findByLoginName(token.getUsername());
			if (user != null) {
				if (user.getLockStatus()==0) {
					System.out.println("用户存在");
					PageHelper.startPage(user.getPageNum(),user.getPageSize());
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("userName",user.getUserName());
					PageInfo<Map<String, Object>> pageInfo = userService.selectAll(map);
					List<Map<String, Object>> list = pageInfo.getList();
					try {
					if (list!=null && list.size()>0){
						for (Object key :session.getAttributeKeys()){
							System.out.println(key.toString());
							if(key.equals("shiroSavedRequest")) {
								SavedRequest savedRequest = (SavedRequest) session.getAttribute(key.toString());
								System.out.println(savedRequest.getMethod());
								System.out.println(savedRequest.getQueryString());
								System.out.println(savedRequest.getRequestURI());
								System.out.println(savedRequest.getRequestUrl());
								if(!savedRequest.getRequestURI().equals("/index")||!savedRequest.getRequestUrl().equals("/index")){
									session.removeAttribute("shiroSavedRequest");
								}
							}else{
								System.out.println(session.getAttribute(key.toString()));
							}
						}
						session.setAttribute("userInfo", list.get(0));
					}
					session.setAttribute("admin", user);
					}catch (UnknownSessionException e){
						SecurityUtils.getSubject().logout();
						throw new UnknownSessionException(); // 帐号锁定
					}
					return new SimpleAuthenticationInfo(user, user.getPassword(), new MySimpleByteSource(user.getSalt()), getName());
				}else{ //该用户已被锁定
					throw new LockedAccountException(); // 帐号锁定
				}
			} else {
				return null;
			}
	}

/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		String name = (String) principals.fromRealm(getName()).iterator().next();
//		User user = userDao.findUserByLoginName(name);
//		if (user != null) {
		System.out.println("进入验证2");
//			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//			info.addStringPermission("test:test");
//			info.addStringPermission("menu:view");
		//查询用户授权字符串
//			for (Group group : user.getGroupList()) {
//				//基于Permission的权限信息
//				info.addStringPermissions(group.getPermissionList());
//			}
		User user = (User) principals.getPrimaryPrincipal();
		String username = user.getUserName();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.findRoles(username));
		authorizationInfo.setStringPermissions(userService.findPermissions(username));
		return authorizationInfo;
//		} else {
//			return null;
//		}
	}

/*
*
	 * 更新用户授权信息缓存.
*/


	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

/*
*
	 * 清除所有用户授权信息缓存.
*/


	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

//	public UserDao getUserDao() {
//		return userDao;
//	}
//
//	public void setUserDao(UserDao userDao) {
//		this.userDao = userDao;
//	}
}
