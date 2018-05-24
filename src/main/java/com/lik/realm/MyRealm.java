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
	 * ��ǰ��¼���û�
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
		//��ȡ�����Ϣ(�û���,�ӵ�¼������)
		String userName = (String) token.getPrincipal();
		try {
			User user = userService.getByUserName(userName);
			if(user!=null){
				//�û�������������ݿ�ȡ,xx:realmName,�����д
				AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xx");
				//�ѵ�ǰ�û���Ϣ�浽session�У�����û���һ������ȷ��¼���û����˷ѵ��ڴ棻д��controller�ﻹҪ���������鷳Щ
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
	 * Ϊ��ǰ��¼���û������ɫ��Ȩ��
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		return null;
	}
}
