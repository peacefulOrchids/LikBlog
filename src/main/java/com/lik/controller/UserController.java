package com.lik.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lik.entity.User;
import com.lik.service.UserService;
import com.lik.util.CryptographyUtil;
import com.lik.util.PropertiesUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	private static String yan = PropertiesUtil.getValue("yan");
	@RequestMapping("/login")
	public String login(User user,String veryCode,HttpServletRequest request){
		Subject currentUser = SecurityUtils.getSubject();
		//����token����,�û���/����
		//System.out.println("md5:"+CryptographyUtil.md5(user.getPassword(), yan));
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),CryptographyUtil.md5(user.getPassword(), yan));
		try {
			//�����֤
			currentUser.login(token);
			System.out.println("�����֤�ɹ�!");
			request.getSession().setAttribute("oldPwd", user.getPassword());
			return "redirect:/admin/main.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�����֤ʧ��!");
			request.setAttribute("user", user);
			request.setAttribute("errorInfo", "�û��������������!");
			return "login";
		}
	}
}
