package com.joinhawaii.controller.admin;

import java.util.ArrayList;
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
import com.joinhawaii.manager.HotelManager;
import com.joinhawaii.util.ImageUtil;
import com.joinhawaii.util.StringUtil;

@Controller
public class HotelController extends BaseController{
	
	private static final String day_first = "01";
	private static final String listOfDay = "daylist";
	private static final String hotel = "hotel";
	
	/**
	 * ?ò∏?Öî Î™©Î°ù Ï°∞Ìöå
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/hotelList.do")
	public ModelAndView getHotelList(HttpServletRequest req, Model model){

		Map<String, Object> param = this.getRequestMap(req);
		
		HotelManager hotelmgr = new HotelManager(sqlSession); 
		//?ò∏?Öî Î™©Î°ù Ï°∞Ìöå
		List<Map<String, Object>> hotelList = hotelmgr.getHotelList(param);
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		//?Ñ¨ ÏΩîÎìú Ï°∞Ìöå
		List<Map<String, Object>> islandCdList = cfg.getIslandCodeList(); //this.getSimpleCodeList(JoinHawaiiMapConstants.CODE_ISLAND);
		//?ò∏?Öî ?ì±Í∏? Ï°∞Ìöå
		List<Map<String, Object>> gradeCdList = cfg.getHotelGradeCodeList(); //this.getSimpleCodeList(JoinHawaiiMapConstants.CODE_GRADE);
		
		ModelAndView mav = null;

		model.addAttribute(JoinHawaiiMapConstants.KEY_HOTELLIST, hotelList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_ISLANDCODELIST, islandCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_GRADECODELIST, gradeCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_PARAMETER, param);
		
		mav = new ModelAndView("/admin/hotelList", "model", model);
		
		return mav;
	}
	
	/**
	 * ?ò∏?Öî ?ÉÅ?Ñ∏?†ïÎ≥? Ï°∞Ìöå, ?ò∏?Öî?ã†Í∑úÏûÖ?†• ?ôîÎ©¥Ï≤òÎ¶?
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/admin/hotelDetail.do", "/admin/hotelNew.do"})
	public ModelAndView getHotelInfo(HttpServletRequest req, Model model){
		
		Map<String, Object> param = this.getRequestMap(req);
		Map<String, Object> retMap = null;
		
		String cur_year = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_SEARCH_STOPSELL_YEAR), StringUtil.getStrCurrentYear());
		String cur_month = StringUtil.nvl((String)param.get(JoinHawaiiMapConstants.KEY_SEARCH_STOPSELL_MONTH), StringUtil.getStrCurrentMonth());
		int maxday = StringUtil.getMaxDay(cur_year + cur_month + day_first);
		
		ApplicationConfig cfg = new ApplicationConfig(this.sqlSession);
		//Î∂??Çπ?öå?Ç¨ÏΩîÎìú Ï°∞Ìöå
		List<Map<String, Object>> corpCdList = cfg.getCorpCodeList(); //this.getSimpleCodeList(JoinHawaiiMapConstants.CODE_CORP);
		//?Ñ¨ ÏΩîÎìú Ï°∞Ìöå
		List<Map<String, Object>> islandCdList = cfg.getIslandCodeList(); //this.getSimpleCodeList(JoinHawaiiMapConstants.CODE_ISLAND);
		//?ò∏?Öî ?ì±Í∏? Ï°∞Ìöå
		List<Map<String, Object>> gradeCdList = cfg.getHotelGradeCodeList(); //this.getSimpleCodeList(JoinHawaiiMapConstants.CODE_GRADE);
		//Ï°∞ÏãùÏΩîÎìú Ï°∞Ìöå
		List<Map<String, Object>> breakfastCdList = cfg.getBreakfastCodeList(); //this.getSimpleCodeList(JoinHawaiiMapConstants.CODE_BREAKFAST);
		
		HotelManager hotelMgr = new HotelManager(this.sqlSession);
		GalleryManager galleryMgr = new GalleryManager(this.sqlSession);
		ModelAndView mav = null;
		
		if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
			retMap = new HashMap<String, Object>();
			retMap.put(JoinHawaiiDbFieldConstants.DB_RECOMEND_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_USE_YN, "Y");
			retMap.put(JoinHawaiiDbFieldConstants.DB_WEEKLYDEAL_YN, "Y");
		} else {
			if (param.get(JoinHawaiiMapConstants.KEY_HOTELID) != null && !"".equals((String)param.get(JoinHawaiiMapConstants.KEY_HOTELID))){
				retMap = hotelMgr.getHotelInfo(param);
				
				List<Map<String, Object>> roomlist = hotelMgr.getRoomList(param);
				List<Map<String, Object>> promotionlist = hotelMgr.getPromotionList(param);
				List<Map<String, Object>> pricelist = hotelMgr.getPriceList(param);
				List<Map<String, Object>> gallerylist = galleryMgr.getGalleryList(param);
				
				model.addAttribute(JoinHawaiiMapConstants.KEY_ROOMLIST, roomlist);
				model.addAttribute(JoinHawaiiMapConstants.KEY_PROMOTIONLIST, promotionlist);
				model.addAttribute(JoinHawaiiMapConstants.KEY_PRICELIST, pricelist);
				model.addAttribute(JoinHawaiiMapConstants.KEY_GALLERYLIST, gallerylist);
				
				List<String> dayList = new ArrayList<String>();
				for(int i = 1; i <= maxday; i++) dayList.add(StringUtil.lpad(String.valueOf(i), 2, "0"));
				
				Map<String, Object> sqlParam = new HashMap<String, Object>();
				sqlParam.put(JoinHawaiiDbFieldConstants.DB_REG_YEAR, cur_year);
				sqlParam.put(JoinHawaiiDbFieldConstants.DB_REG_MONTH, cur_month);
				sqlParam.put(JoinHawaiiDbFieldConstants.DB_HOTEL_ID, (String)param.get(JoinHawaiiMapConstants.KEY_HOTELID));
				sqlParam.put(listOfDay, dayList);
				
				List<Map<String, Object>> stopsellList = hotelMgr.getStopSellList(sqlParam);
				model.addAttribute(JoinHawaiiMapConstants.KEY_STOPSELLLIST, stopsellList);
			}
		}
		model.addAttribute(JoinHawaiiMapConstants.KEY_JOBTYPE, param.get(JoinHawaiiMapConstants.KEY_JOBTYPE));
		model.addAttribute(JoinHawaiiMapConstants.KEY_HOTELINFO, retMap);
		
		model.addAttribute(JoinHawaiiMapConstants.KEY_CURRENTYEAR, Integer.valueOf(cur_year));
		model.addAttribute(JoinHawaiiMapConstants.KEY_CURRENTMONTH, Integer.valueOf(cur_month));
		model.addAttribute(JoinHawaiiMapConstants.KEY_MAXDAY, maxday);
		
		model.addAttribute(JoinHawaiiMapConstants.KEY_ISLANDCODELIST, islandCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_GRADECODELIST, gradeCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_CORPCODELIST, corpCdList);
		model.addAttribute(JoinHawaiiMapConstants.KEY_BREAKFASTCODELIST, breakfastCdList);

		mav = new ModelAndView("/admin/hotelDetail", "model", model);
		return mav;
	}
	
	/**
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/admin/HotelInfoSave.do", "/admin/hotelInfoDelete.do"})
	public ModelAndView saveHotelInfo(MultipartHttpServletRequest req, Model model){
		Map<String, Object> param = this.getRequestMap(req);
		
		HotelManager hotelMgr = new HotelManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));

		DefaultTransactionDefinition deftran = new DefaultTransactionDefinition();
		deftran.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus ts = transactionManager.getTransaction(deftran);
		
		try {
			if (JOB_UPDATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				hotelMgr.updateHotelInfo(param);
			} else if (JOB_DELETE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				hotelMgr.deleteHotelInfo(param);
			} else if (JOB_CREATE.equals((String)param.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){	
				hotelMgr.insertHotelInfo(param);
			}
			
			MultipartFile mainPic = req.getFile(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE);
			if (!mainPic.isEmpty()){
				String filenm = (new ImageUtil()).uplaodImage(mainPic, req.getSession().getServletContext().getRealPath("/"), hotel);

				param.put(JoinHawaiiDbFieldConstants.DB_MAIN_PICTURE, filenm);
				hotelMgr.updateHotelImage(param);
			}
			transactionManager.commit(ts);
		} catch(Exception e) {
			e.printStackTrace();
			transactionManager.rollback(ts);
		}
		
		model.addAttribute(JoinHawaiiMapConstants.KEY_SEARCHISLAND, (String)param.get(JoinHawaiiDbFieldConstants.DB_ISALDCODE));
		
		return new ModelAndView("redirect:/admin/hotelList.do", "model", model);
	}
	
	/**
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/hotelListSave.do")
	public ModelAndView saveHotelList(HttpServletRequest req, Model model){
		ModelAndView mav = null;
		
		List<Map<String, Object>> list = this.getRequestList(req);
		HotelManager hotelMgr = new HotelManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		hotelMgr.saveHotelList(list);
		
		mav = new ModelAndView("redirect:/admin/hotelList.do?top_menu=2");
		
		return mav;
	}
	
	/**
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/roomListSave.do")
	public @ResponseBody Map<String, Object> saveRoomList(HttpServletRequest req, Model model){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = this.getRequestList(req);
		HotelManager hotelMgr = new HotelManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		hotelMgr.saveRoomList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
//		mav = new ModelAndView("redirect:/admin/hotelList.do?top_menu=2");
		
		return retMap;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/promotionListSave.do")
	public @ResponseBody Map<String, Object> savePromotionList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req);
		HotelManager hotelMgr = new HotelManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		hotelMgr.savePromotionList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/priceListSave.do")
	public @ResponseBody Map<String, Object> savePriceList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req);
		HotelManager hotelMgr = new HotelManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		hotelMgr.savePriceList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	} 
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/hotelStopsellSave.do")
	public @ResponseBody Map<String, Object> saveStopSellList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req);
		HotelManager hotelMgr = new HotelManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		hotelMgr.saveStopSellList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	} 
	
	
	/*
	@RequestMapping("/admin/hotelExcelSave.do")
	public ModelAndView xlsxSave(HttpServletRequest req, Model model){
		try {
			LoadExcelToDB.excelLoad(this.sqlSession);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	*/
}
