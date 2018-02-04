package com.joinhawaii.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.joinhawaii.common.ApplicationConfig;
import com.joinhawaii.common.JoinHawaiiDbFieldConstants;
import com.joinhawaii.common.JoinHawaiiMapConstants;
import com.joinhawaii.controller.BaseController;
import com.joinhawaii.manager.AirManager;
import com.joinhawaii.util.ImageUtil;
import com.joinhawaii.util.StringUtil;

@Controller
public class AirController extends BaseController {
	private static final String air = "air";
	
	@RequestMapping("/admin/airList.do")
	public ModelAndView getAirList(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		
		AirManager airMgr = new AirManager(sqlSession);
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		
		ModelAndView mav = null;
		
		//
		model.addAttribute(JoinHawaiiMapConstants.KEY_AIRLIST, airMgr.getAirList(param));
		//?òà?ïΩ?öå?Ç¨ÏΩîÎìú Ï°∞Ìöå
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, cfg.getCorpCodeList());
		//?ï≠Í≥µÏÇ¨ÏΩîÎìú Ï°∞Ìöå
		model.addAttribute(JoinHawaiiMapConstants.KEY_CATEGORYCODELIST, cfg.getAirCorpCodeList());
		model.addAttribute(JoinHawaiiMapConstants.KEY_PARAMETER, param);
		
		mav = new ModelAndView("/admin/airList", "model", model);
		
		return mav;
	}
	
	
	@RequestMapping(value = {"/admin/airDetail.do", "/admin/airNew.do"})
	public ModelAndView getAirInfo(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		Map<String, Object> retMap = null;
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		AirManager airMgr = new AirManager(sqlSession);
		
		String job_type = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE), JOB_CREATE);
		String tabs_code = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_TABSCODE), "1");
		
		if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
			retMap = new HashMap<String, Object>();
			retMap.put(JoinHawaiiDbFieldConstants.DB_RECOMEND_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_USE_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_WEEKLYDEAL_YN, "Y");
		} else {
			if (param.get(JoinHawaiiMapConstants.KEY_OPTIONID) != null && !"".equals((String)param.get(JoinHawaiiMapConstants.KEY_OPTIONID))){
				model.addAttribute(JoinHawaiiMapConstants.KEY_AIRINFO, airMgr.getAirInfo(param));
				model.addAttribute(JoinHawaiiMapConstants.KEY_PRICELIST, airMgr.getAirPriceList(param));
			}
		}
		
		model.addAttribute(param);
		model.addAttribute(JoinHawaiiMapConstants.KEY_JOBTYPE, job_type);
		model.addAttribute(JoinHawaiiMapConstants.KEY_TABSCODE, tabs_code);
		
		//?òà?ïΩ?öå?Ç¨ÏΩîÎìú Ï°∞Ìöå
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, cfg.getCorpCodeList());
		//?ï≠Í≥µÏÇ¨ÏΩîÎìú Ï°∞Ìöå
		model.addAttribute(JoinHawaiiMapConstants.KEY_CATEGORYCODELIST, cfg.getAirCorpCodeList());
		//Ï¢åÏÑù?ì±Í∏àÏΩî?ìú Ï°∞Ìöå
		model.addAttribute(JoinHawaiiMapConstants.KEY_AIRCLASSCODELIST, cfg.getAirClassCodeList());
		
		ModelAndView mav = new ModelAndView("/admin/airDetail", "model", model);
		
		return mav;
	}
	
	
	@RequestMapping(value = {"/admin/airInfoSave.do", "/admin/airInfoDelete.do"})
	public ModelAndView saveAirInfo(MultipartHttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		AirManager airMgr = new AirManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
	
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		try{
			if (JOB_UPDATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				airMgr.updateAirInfo(param);
			} else if (JOB_DELETE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				airMgr.deleteAirInfo(param);
			} else if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){	
				airMgr.insertAirInfo(param);
			}
			
			MultipartFile mainPic = req.getFile(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE);
			if (!mainPic.isEmpty()){
				String filenm = (new ImageUtil()).uplaodImage(mainPic, req.getSession().getServletContext().getRealPath("/"), air);

				param.put(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE, filenm);
				airMgr.updateAirImage(param);
			}
			transactionManager.commit(ts);
		} catch (Exception e) {
			error("saveAirInfo-ERROR", e);
			transactionManager.rollback(ts);
		} 
		
		return new ModelAndView("redirect:/admin/airList.do", "model", model);
		
	}
	
	
	@RequestMapping("/admin/airPriceListSave.do")
	public @ResponseBody Map<String, Object> saveCarPriceList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req); 
		AirManager airMgr = new AirManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		airMgr.saveAirPriceList(list);
	
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	}
	
}
