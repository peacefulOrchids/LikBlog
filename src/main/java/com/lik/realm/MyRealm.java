package com.lik.realm;


import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.lik.entity.User;
import com.lik.service.UserService;

public class MyRealm extends AuthorizingRealm{

	@Resource
	private UserService userService;
	/**
	 * 当前登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
		//获取身份信息(用户名,从登录界面来)
		String userName = (String) token.getPrincipal();
		try {
			User user = userService.getByUserName(userName);
			if(user!=null){
				//用户名和密码从数据库取,xx:realmName,可随便写
				AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xx");
				//把当前用户信息存到session中，这个用户不一定是正确登录的用户，浪费点内存；写在controller里还要调用它；麻烦些
				SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
				return authcInfo;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 为当前登录的用户授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		return null;
	}
}
