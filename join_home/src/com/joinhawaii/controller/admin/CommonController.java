package com.joinhawaii.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joinhawaii.common.JoinHawaiiMapConstants;
import com.joinhawaii.controller.BaseController;
import com.joinhawaii.manager.GalleryManager;
import com.joinhawaii.util.ImageUtil;

@Controller
public class CommonController extends BaseController {

	private static final String dir = "dir";
	
	/**
	 * ajax Î∞©Ïãù?úºÎ°? ?åå?ùº?ùÑ ?óÖÎ°úÎìú Ï≤òÎ¶¨ 
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/admin/ajaxfileUp.do")
	public @ResponseBody Map<String, Object> ajaxFileUpload(MultipartHttpServletRequest req){
		Map<String, Object> param = this.getRequestMap(req);
		
		MultipartFile ajaxFiledata = req.getFile(JoinHawaiiMapConstants.KEY_AJAXFILEDATA);
		if (!ajaxFiledata.isEmpty()){
			String filenm = (new ImageUtil()).uplaodImage(ajaxFiledata, req.getSession().getServletContext().getRealPath("/"), (String)param.get(dir));
		
			param.put(JoinHawaiiMapConstants.KEY_RESULT, true);
			param.put(JoinHawaiiMapConstants.KEY_AJAXSAVEFILE, filenm);
		}
		
		return param;
	}
	
	
	/**
	 * ?ò∏?Öî, ?ÉÅ?íà Í∞§Îü¨Î¶? ?ù¥ÎØ∏Ï?? ?†ïÎ≥? ????û•
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/gallerySave.do")
	public @ResponseBody Map<String, Object> saveGalleryList(HttpServletRequest req){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getRequestList(req);
		GalleryManager mgr = new GalleryManager(this.sqlSession, (String)req.getSession().getAttribute(JoinHawaiiMapConstants.KEY_ADMIN_ID));
		mgr.saveGalleryList(list);
		
		retMap.put(JoinHawaiiMapConstants.KEY_RESULT, true);
		
		return retMap;
	}
}
