package com.joinhawaii.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joinhawaii.controller.BaseController;
import com.joinhawaii.util.StringUtil;

@Controller
public class LoginController extends BaseController{
//	@Autowired
//	private SqlSession sqlSession;

//	@Autowired
//	private DataSourceTransactionManager transactionManager;

	@RequestMapping("/admin/login.do")
	public @ResponseBody Map<String, Object> admin_login(HttpServletRequest req){
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		String passwd = StringUtil.nvl(req.getParameter("user_pwd"), "");
		String message = "";
		Map<String, String> map = new HashMap<String, String>();

		map.put("user_id", req.getParameter("user_id"));
		
		Map<String, Object> rs_map = sqlSession.selectOne("admin.select_user_list", map);
		if (rs_map == null || rs_map.isEmpty()){
			message = "ì¡´ì¬?•˜ì§? ?•Š?Š” ?‚¬?š©??…?‹ˆ?‹¤";
		} else { 
			if (!passwd.equals((String)rs_map.get("user_pw"))){
				message = "ë¹„ë??ë²ˆí˜¸ê°? ?¼ì¹˜í•˜ì§? ?•Š?Šµ?‹ˆ?‹¤.";
			}
			if (!"Y".equals((String)rs_map.get("use_yn"))){
				message = "?‚¬?š©ì¤‘ì???œ ?‚¬?š©??…?‹ˆ?‹¤.";
			}
		}
		
		if (message.equals("")){
			
			req.getSession().setAttribute("admin_user_id", rs_map.get("user_id"));
			req.getSession().setAttribute("admin_user_name", rs_map.get("user_name"));
			req.getSession().setAttribute("admin_user_auth", rs_map.get("user_auth"));
			req.getSession().setAttribute("admin_auth_name", rs_map.get("auth_name"));
			req.getSession().setAttribute("admin_dept_code", rs_map.get("dept_code"));
			
//			req.getSession().setAttribute("admin_code_list", CommUtil.getBigCodeList(sqlSession));
			jsonObject.put("result", true);
			jsonObject.put("next_url", "/admin/main.do");		
			
		} else {
			jsonObject.put("result", false);
			jsonObject.put("message", message);
		}
		
		
		return jsonObject;
	}
}
