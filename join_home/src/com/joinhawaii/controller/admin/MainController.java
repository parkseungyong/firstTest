package com.joinhawaii.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.joinhawaii.controller.BaseController;
import com.joinhawaii.util.ExchangeRateViewer;

@Controller
public class MainController extends BaseController{

	
	@RequestMapping("/admin/main.do")
	public ModelAndView display_main(HttpServletRequest req, Model model){
		
		ModelAndView mav = null;

		mav = new ModelAndView("/admin/main", "model", model);
		return mav;
	}
	
	@RequestMapping("/admin/exchangeRateUpdate.do")
	public @ResponseBody Map<String, String> getExchg_rate(HttpServletRequest req){
		Map<String, String> retMap = ExchangeRateViewer.exchangView();
		return retMap;
	}
}
