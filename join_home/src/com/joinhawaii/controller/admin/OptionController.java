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
import com.joinhawaii.manager.GalleryManager;
import com.joinhawaii.manager.OptionManager;
import com.joinhawaii.util.ImageUtil;
import com.joinhawaii.util.StringUtil;

@Controller
public class OptionController extends BaseController{
	
	private static final String option = "option";

	/**
	 * ?òµ?ÖòÎ™©Î°ù Ï°∞Ìöå
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/optionList.do")
	public ModelAndView getOptionList(HttpServletRequest req, Model model){

		Map<String, Object> param = this.getRequestMap(req);
		
		OptionManager optionMgr = new OptionManager(sqlSession); 
		//?òµ?ÖòÎ™©Î°ù Ï°∞Ìöå
		List<Map<String, Object>> optionList = optionMgr.getOptionList(param);
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		//?öå?Ç¨ÏΩîÎìú Ï°∞Ìöå
		List<Map<String, Object>> corpCdList = cfg.getCorpCodeList();
		//?òµ?ÖòÏπ¥ÌÖåÍ≥†Î¶¨ Ï°∞Ìöå
		List<Map<String, Object>> categoryCdList = cfg.getCategoryCodeList();
		
		ModelAndView mav = null;

		model.addAttribute(JoinHawaiiMapConstants.KEY_OPTIONLIST, optionList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, corpCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_CATEGORYCODELIST, categoryCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_PARAMETER, param);
		
		mav = new ModelAndView("/admin/optionList", "model", model);
		
		return mav;
	}
	
	@RequestMapping("/admin/optionListSave.do")
	public ModelAndView saveOptionList(HttpServletRequest req, Model model){
		
		List<Map<String, Object>> list = this.getRequestList(req);
		OptionManager optionMgr = new OptionManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		optionMgr.saveOptionList(list);
		
		model.addAttribute(this.getRequestMap(req));
		
		ModelAndView mav = new ModelAndView("redirect:/admin/optionList.do", "model", model);
		
		return mav;
	}
	
	/**
	 * ?òµ?Öò?ÉÅ?Ñ∏?†ïÎ≥? Ï°∞Ìöå, ?òµ?Öò?ã†Í∑úÏûÖ?†• ?ôîÎ©¥Ï≤òÎ¶?
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/admin/optionDetail.do", "/admin/optionNew.do"})
	public ModelAndView getOptionInfo(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		Map<String, Object> retMap = null;
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		//?öå?Ç¨ÏΩîÎìú Ï°∞Ìöå
		List<Map<String, Object>> corpCdList = cfg.getCorpCodeList();
		//?òµ?ÖòÏπ¥ÌÖåÍ≥†Î¶¨ Ï°∞Ìöå
		List<Map<String, Object>> categoryCdList = cfg.getCategoryCodeList();
		
		String job_type = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE), JOB_CREATE);
		String tabs_code = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_TABSCODE), "1");
		
		if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
			retMap = new HashMap<String, Object>();
			retMap.put(JoinHawaiiDbFieldConstants.DB_RECOMEND_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_USE_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_WEEKLYDEAL_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_DISPLAY_TYPE, "0");
		} else {
			if (param.get(JoinHawaiiMapConstants.KEY_OPTIONID) != null && !"".equals((String)param.get(JoinHawaiiMapConstants.KEY_OPTIONID))){
				OptionManager optionMgr = new OptionManager(sqlSession);
				GalleryManager galleryMgr = new GalleryManager(this.sqlSession);

				retMap = optionMgr.getOptionInfo(param);
				
				model.addAttribute(JoinHawaiiMapConstants.KEY_OPTPRODUCTLIST, optionMgr.getOptionProducList(param));
				model.addAttribute(JoinHawaiiMapConstants.KEY_GALLERYLIST, galleryMgr.getGalleryList(param));
				model.addAttribute(JoinHawaiiMapConstants.KEY_OPTIONBLACKOUTLIST, optionMgr.getOptionBlackoutList(param));
			}
		}
		model.addAttribute(JoinHawaiiMapConstants.KEY_JOBTYPE, job_type);
		model.addAttribute(JoinHawaiiMapConstants.KEY_TABSCODE, tabs_code);
		model.addAttribute(JoinHawaiiMapConstants.KEY_OPTIONINFO, retMap);
		
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, corpCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_CATEGORYCODELIST, categoryCdList);
		
		ModelAndView mav = new ModelAndView("/admin/optionDetail", "model", model);
		return mav;
	}
	
	/**
	 * ?òµ?Öò?†ïÎ≥? ????û•(?ã†Í∑?/?àò?†ï/?Ç≠?†ú)
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/admin/optionInfoSave.do", "/admin/optionInfoDelete.do"})
	public ModelAndView saveOptionInfo(MultipartHttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		OptionManager optionMgr = new OptionManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		try{
			if (JOB_UPDATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				optionMgr.updateOptionInfo(param);
			} else if (JOB_DELETE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				optionMgr.deleteOptionInfo(param);
			} else if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){	
				optionMgr.insertOptionInfo(param);
			}
			
			MultipartFile mainPic = req.getFile(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE);
			if (!mainPic.isEmpty()){
				String filenm = (new ImageUtil()).uplaodImage(mainPic, req.getSession().getServletContext().getRealPath("/"), option);

				param.put(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE, filenm);
				optionMgr.updateOptionImage(param);
			}
			transactionManager.commit(ts);
		} catch (Exception e) {
			error("saveOptionInfo-ERROR", e);
			transactionManager.rollback(ts);
		} 
		
		return new ModelAndView("redirect:/admin/optionList.do", "model", model);
	}
	
	/**
	 * ?òµ?Öò?ÉÅ?íà Ï°∞Ìöå, ?òµ?Öò?ÉÅ?íà ?ã†Í∑úÌôîÎ©? Ï≤òÎ¶¨
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/admin/optionProductInfo.do", "/admin/optionProductNew.do"})
	public ModelAndView getOptionProductInfo(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		
		String job_type = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE), JOB_CREATE);
		
		Map<String, Object> productInfo = null;
		
		if (JOB_CREATE.equals(job_type)){
			productInfo = new HashMap<String, Object>();
			productInfo.put(JoinHawaiiDbFieldConstants.DB_USE_YN, "Y"); 
		} else {
			if (param.get(JoinHawaiiMapConstants.KEY_OPTIONID) != null && !"".equals((String)param.get(JoinHawaiiMapConstants.KEY_OPTIONID))){
				OptionManager optionMgr = new OptionManager(sqlSession);
				productInfo = optionMgr.getOptionProductInfo(param);
			}
		} 
		model.addAttribute(JoinHawaiiMapConstants.KEY_JOBTYPE, job_type);
		model.addAttribute(JoinHawaiiMapConstants.KEY_PRODUCTINFO, productInfo);
		model.addAttribute(JoinHawaiiMapConstants.KEY_OPTIONID, param.get(JoinHawaiiMapConstants.KEY_OPTIONID));
		
		ModelAndView mav = new ModelAndView("/admin/optionProductInfo", "model", model);
		return mav;
	}

	/**
	 * ?òµ?Öò?ÉÅ?íà ????û•(?ã†Í∑?/????û•/?Ç≠?†ú)
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/admin/optionProductSave.do", "/admin/optionProductDelete.do"})
	public ModelAndView saveOptionProduct(MultipartHttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		OptionManager optionMgr = new OptionManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		String job_type = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE), JOB_CREATE);
		
		try{
			if (JOB_UPDATE.equals(job_type)){
				optionMgr.updateOptionProduct(param);
			} else if (JOB_DELETE.equals(job_type)){
				optionMgr.deleteOptionProduct(param);
			} else if (JOB_CREATE.equals(job_type)){	
				optionMgr.insertOptionProduct(param);
			}
			
			MultipartFile mainPic = req.getFile(JoinHawaiiDbFieldConstants.DB_ADDR_IMG_URL);
			if (!mainPic.isEmpty()){
				String filenm = (new ImageUtil()).uplaodImage(mainPic, req.getSession().getServletContext().getRealPath("/"), option);

				param.put(JoinHawaiiDbFieldConstants.DB_ADDR_IMG_URL, filenm);
				optionMgr.updateOptionProductImage(param);
			}
			transactionManager.commit(ts);
		} catch (Exception e) {
			error("saveOptionProductInfo-ERROR", e);
			transactionManager.rollback(ts);
		} 
		
		model.addAttribute(JoinHawaiiMapConstants.KEY_PRODUCTINFO, param);
		
		return new ModelAndView("redirect:/admin/optionProductInfo.do", "model", model);
	}
	
	
	@RequestMapping("/admin/optionProductListSave.do")
	public @ResponseBody Map<String, Object> saveOptionProductList(HttpServletRequest req, Model model){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = this.getRequestList(req);
		OptionManager optionMgr = new OptionManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		try{
		
			optionMgr.saveOptionProductList(list);
			
			transactionManager.commit(ts);
			retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
			
		}catch (Exception e) {
			
			error("saveOptionProductList-ERROR", e);
			retMap.put(JoinHawaiiMapConstants.KEY_RESULT, false);
			retMap.put(JoinHawaiiMapConstants.KEY_MESSAGE, e.getMessage());
			transactionManager.rollback(ts);
			
		} 
		
		return retMap;
		
	}
	
	/**
	 * ?òµ?Öò?ÉÅ?íà Î≥µÏÇ¨ - Í∏? ?Ñ†?Éù?êú ?ÉÅ?íà?ùÑ DB?óê Insert
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/optionProductCopy.do")
	public @ResponseBody Map<String, Object> copyOptionProduct(HttpServletRequest req, Model model){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		OptionManager mgr = new OptionManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		
		List<Map<String, Object>> list = this.getRequestList(req);
		try {
			for(Map<String, Object> map : list){
				if (map.get(JoinHawaiiMapConstants.KEY_CHECK) != null && !"".equals(map.get(JoinHawaiiMapConstants.KEY_CHECK))){
					map.put(JoinHawaiiMapConstants.KEY_PRODUCTID, map.get(JoinHawaiiMapConstants.KEY_CHECK));
					mgr.copyOptionProduct(map);
				} 
			}
			transactionManager.commit(ts);
			retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		} catch (Exception e) {
			error("copyOptionProduct-ERROR", e);
			retMap.put(JoinHawaiiMapConstants.KEY_RESULT, false);
			retMap.put(JoinHawaiiMapConstants.KEY_MESSAGE, e.getMessage());
			transactionManager.rollback(ts);
		} 
		
		return retMap;
	}
	
	@RequestMapping("/admin/optionBlackoutListSave.do")
	public @ResponseBody Map<String, Object> saveOptionBlackoutList(HttpServletRequest req, Model model){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = this.getRequestList(req);
		OptionManager optionMgr = new OptionManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		try{
		
			optionMgr.saveOptionBlackoutList(list);
			
			transactionManager.commit(ts);
			retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
			
		}catch (Exception e) {
			
			error("saveOptionBlackoutList-ERROR", e);
			retMap.put(JoinHawaiiMapConstants.KEY_RESULT, false);
			retMap.put(JoinHawaiiMapConstants.KEY_MESSAGE, e.getMessage());
			transactionManager.rollback(ts);
			
		} 
		
		return retMap;
		
	}
}
