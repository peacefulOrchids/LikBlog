package com.lik.controller.admin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lik.entity.PageBean;
import com.lik.entity.User;
import com.lik.service.UserService;
import com.lik.util.CryptographyUtil;
import com.lik.util.DateJsonValueProcessor;
import com.lik.util.PropertiesUtil;
import com.lik.util.ResponseUtil;
import com.lik.util.StringUtil;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
	@Resource
	private UserService userService;

	private static String yan = PropertiesUtil.getValue("yan");
	private int page=1;//��ǰҳ
	private int pageSize=10;//һҳ���ж��ٸ�

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute("currentUser");
		return "login";
	}
	@RequestMapping("/updatePwd")
	public void updatePwd(User user,HttpServletRequest request,HttpServletResponse response){
		user.setPassword(CryptographyUtil.md5(user.getPassword(), yan));
		int result = userService.updatePwd(user);
		JSONObject resultJson = new JSONObject();
		if(result>0){
			resultJson.put("success", true);
			request.getSession().setAttribute("oldPwd", user.getPassword());
		}else{
			resultJson.put("errorMsg", "�޸�ʧ��");
		}
		try {
			ResponseUtil.write(resultJson, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**�鿴�����û�*/
	@RequestMapping("/list")
	public String list(User user,@RequestParam(value="page",required=false)String page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		if(user!=null){
			if(StringUtil.isNotEmpty(user.getUserName())){
				map.put("userName", "%"+user.getUserName()+"%");
			}
		}
		if(page!=null){
			this.page = Integer.parseInt(page);
		}
		request.setAttribute("s_user", user);
		PageBean pageBean = new PageBean(this.page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("pageSize",pageBean.getPageSize());
		List<User> userList = userService.find(map);
		/**Json����*/
		JsonConfig jsonConfig = new JsonConfig();
		/**setExcludes:�ų�*/
		//jsonConfig.setExcludes(new String[]{"orderList"});
		/**��jsonֵ�ӹ�һ��*/
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
		JSONArray rows=JSONArray.fromObject(userList,jsonConfig);
		/**json����*/
		JSONObject resultJson = new JSONObject();
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		resultJson.put("rows", rows);
		long total = userService.countUser(map);
		resultJson.put("total", total);
		/**��ҳ��д��*/
		ResponseUtil.write(resultJson,response);//��ҳ�����
		return null;
	}

	@RequestMapping("/saveUser")
	public String saveUser(User user,@RequestParam("proPic") MultipartFile proPic,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(proPic!=null){
			String filePath = request.getServletContext().getRealPath("/");
			System.out.println(filePath);
			proPic.transferTo(new File(filePath+"static/images/" + proPic.getOriginalFilename()));
			user.setImage("/static/images/" + proPic.getOriginalFilename());
		}
		int result = 0;
		if(user.getId()!=null){
			result = userService.update(user);
		}else{
			result = userService.add(user);
		}
		/**json����*/
		JSONObject resultJson = new JSONObject();
		/**total:�ܼ�¼����,rows:���ݼ�¼����*/
		if(result>0){
			resultJson.put("success",true);
		}else{
			resultJson.put("errorMsg","�����쳣");
		}
		/**��ҳ��д��*/
		ResponseUtil.write(resultJson, response);//��ҳ�����
		return null;
	}
	@RequestMapping("/delUser")
	public String delUser(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] userIds = ids.split(",");
		for(String userId:userIds){
			userService.delete(Integer.parseInt(userId));
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson, response);//��ҳ�����
		return null;
	}
	
}
