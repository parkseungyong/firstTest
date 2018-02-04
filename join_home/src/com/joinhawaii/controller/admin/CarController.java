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
import com.joinhawaii.manager.CarManager;
import com.joinhawaii.util.ImageUtil;
import com.joinhawaii.util.StringUtil;

@Controller
public class CarController extends BaseController {

	private static final String car = "car";
	
	@RequestMapping("/admin/carList.do")
	public ModelAndView getCarList(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		
		CarManager carMgr = new CarManager(sqlSession);
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		
		ModelAndView mav = null;
		
		//
		model.addAttribute(JoinHawaiiMapConstants.KEY_CARLIST, carMgr.getCarList(param));
		//?šŒ?‚¬ì½”ë“œ ì¡°íšŒ
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, cfg.getCorpCodeList());
		//? Œ?„°ì¹´ì¹´?…Œê³ ë¦¬ ì¡°íšŒ
		model.addAttribute(JoinHawaiiMapConstants.KEY_CATEGORYCODELIST, cfg.getCarCorpCodeList());
		model.addAttribute(param);
		
		mav = new ModelAndView("/admin/carList", "model", model);
		
		return mav;
	}
	
	
	@RequestMapping(value = {"/admin/carDetail.do", "/admin/carNew.do"})
	public ModelAndView getCarInfo(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		Map<String, Object> retMap = null;
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		CarManager carMgr = new CarManager(sqlSession);
		
		String job_type = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE), JOB_CREATE);
		String tabs_code = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_TABSCODE), "1");
		
		if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
			retMap = new HashMap<String, Object>();
			retMap.put(JoinHawaiiDbFieldConstants.DB_RECOMEND_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_USE_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_WEEKLYDEAL_YN, "Y");
		} else {
			if (param.get(JoinHawaiiMapConstants.KEY_OPTIONID) != null && !"".equals((String)param.get(JoinHawaiiMapConstants.KEY_OPTIONID))){
				model.addAttribute(JoinHawaiiMapConstants.KEY_CARINFO, carMgr.getCarInfo(param));
				model.addAttribute(JoinHawaiiMapConstants.KEY_PROMOTIONLIST, carMgr.getCarPromotionList(param));
				model.addAttribute(JoinHawaiiMapConstants.KEY_PRICELIST, carMgr.getCarPriceList(param));
			}
		}
		
		model.addAttribute(param);
		model.addAttribute(JoinHawaiiMapConstants.KEY_JOBTYPE, job_type);
		model.addAttribute(JoinHawaiiMapConstants.KEY_TABSCODE, tabs_code);
		//?šŒ?‚¬ì½”ë“œ ì¡°íšŒ
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, cfg.getCorpCodeList());
		//? Œ?„°ì¹´ì¹´?…Œê³ ë¦¬ ì¡°íšŒ
		model.addAttribute(JoinHawaiiMapConstants.KEY_CATEGORYCODELIST, cfg.getCarCorpCodeList());
		
		ModelAndView mav = new ModelAndView("/admin/carDetail", "model", model);
		
		return mav;
	}
	
	
	@RequestMapping(value = {"/admin/carInfoSave.do", "/admin/carInfoDelete.do"})
	public ModelAndView saveCarInfo(MultipartHttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		CarManager carMgr = new CarManager(sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		
		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		try{
			if (JOB_UPDATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				carMgr.updateCarInfo(param);
			} else if (JOB_DELETE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				carMgr.deleteCarInfo(param);
			} else if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){	
				carMgr.insertCarInfo(param);
			}
			
			MultipartFile mainPic = req.getFile(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE);
			if (!mainPic.isEmpty()){
				String filenm = (new ImageUtil()).uplaodImage(mainPic, req.getSession().getServletContext().getRealPath("/"), car);

				param.put(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE, filenm);
				carMgr.updateCarImage(param);
			}
			transactionManager.commit(ts);
		} catch (Exception e) {
			error("saveCarInfo-ERROR", e);
			transactionManager.rollback(ts);
		} 
		
		return new ModelAndView("redirect:/admin/carList.do", "model", model);
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/carPromotionListSave.do")
	public @ResponseBody Map<String, Object> saveCarPromotionList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req);
		CarManager carMgr = new CarManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		carMgr.saveCarPromotionList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	} 
	
	
	@RequestMapping("/admin/carPriceListSave.do")
	public @ResponseBody Map<String, Object> saveCarPriceList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req);
		CarManager carMgr = new CarManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		carMgr.saveCarPriceList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	}
}
