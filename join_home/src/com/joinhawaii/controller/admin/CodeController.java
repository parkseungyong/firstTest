package com.joinhawaii.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.joinhawaii.common.ApplicationConfig;
import com.joinhawaii.common.JoinHawaiiDbFieldConstants;
import com.joinhawaii.common.JoinHawaiiMapConstants;
import com.joinhawaii.controller.BaseController;
import com.joinhawaii.manager.CodeManager;

@Controller
public class CodeController extends BaseController {

	private static final String S_CODE_GUBN = "s_code_gubn";
	private static final String CODE_ROOT = "000";
	private static final String LIST_HEAD = "listHead";

	@RequestMapping("/admin/codeList.do")
	public ModelAndView getCodeList(HttpServletRequest req, Model model){
		
		ModelAndView mav = null;

		Map<String, Object> param = this.getRequestMap(req);
		//?�� 코드 조회
		CodeManager cdmgr = new CodeManager(this.sqlSession);

		List<Map<String, Object>> rootCodeList = cdmgr.getSimpleCodeList(CODE_ROOT);
		List<Map<String, Object>> subCodeList = null; 
		
		if (param.get(S_CODE_GUBN) != null && !"".equals((String)param.get(S_CODE_GUBN))){
			subCodeList = cdmgr.getCodeList(param); 
			model.addAttribute(JoinHawaiiMapConstants.KEY_SUBCODELIST, subCodeList);
			model.addAttribute(LIST_HEAD, cdmgr.getCodeListHeader((String)param.get(S_CODE_GUBN)));
		}
		
		model.addAttribute(JoinHawaiiMapConstants.KEY_ROOTCODELIST, rootCodeList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_PARAMETER, param);
		
		mav = new ModelAndView("/admin/codeList", "model", model);
		
		return mav;
	}
	
	@RequestMapping("/admin/codeListSave.do")
	public ModelAndView setCodeList(HttpServletRequest req, Model model){
		ModelAndView mav = null;
		
		List<Map<String, Object>> list = this.getRequestList(req);
		CodeManager cdmgr = new CodeManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		cdmgr.saveCodeList(list);
		
		String code_gubn = (String)(((Map<String, Object>)list.get(0)).get(JoinHawaiiDbFieldConstants.DB_CODEGUBN)); 
		if (list.size() > 0){
			new ApplicationConfig(this.sqlSession).setSysCodeList(code_gubn, cdmgr.getSimpleCodeList(code_gubn));
		}
		
		mav = new ModelAndView("redirect:/admin/codeList.do?top_menu=5");
		
		return mav;
	}
	
}
