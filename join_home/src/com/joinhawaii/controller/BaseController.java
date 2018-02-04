package com.joinhawaii.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.joinhawaii.util.StringUtil;

public abstract class BaseController {

	private static final String listPrefix = "list.";
	private static final String br_tag = "<br>";
	private static final String br_style_tag = "<br style=\"clear: both;\">";
	private static final String contentType = "Content-Type";
	private static final String chrset_utf_8 = "charset=UTF-8";
	
	protected static final String JOB_CREATE = "C";
	protected static final String JOB_UPDATE = "U";
	protected static final String JOB_DELETE = "D";
	
	@Autowired
	protected SqlSession sqlSession;
	
	@Autowired
	protected DataSourceTransactionManager transactionManager;
	
	protected transient Logger logger; 
	
	protected final String loggerName;
	
	protected HttpServletRequest request;
	
	protected BaseController(){
		loggerName = getClass().getName();
		initLogger();
	}
	
	private void initLogger(){
		if (logger == null){
			logger = Logger.getLogger(loggerName);
		}
	}
	
	protected void debug(String message){
		initLogger();
		logger.debug(message);
	}
	
	protected void debug(String message, Throwable t){
		initLogger();
		logger.debug(message, t);		
	}
	
	protected void error(String message){
		initLogger();
		logger.error(message);
	}
	
	protected void error(String message, Throwable t){
		initLogger();
		logger.error(message, t);		
	}
	
//	/**
//	 * Í¥?Î¶¨Ïûê ?Ñ∏?Öò ?ú†?ö®?Ñ± Í≤??Ç¨
//	 * @param req
//	 * @return
//	 */
//	protected boolean isValid_AdminSession(HttpServletRequest req){
//		boolean b_ss = false;
//		if (req.getSession().getAttribute("admin_user_id") != null 
//				&& !"".equals(req.getSession().getAttribute("admin_user_id"))){
//			b_ss = true;
//		}
//		return b_ss;
//	}
//	
//	/**
//	 * Í≥†Í∞ù Î°úÍ∑∏?ù∏ ?Ñ∏?Öò ?ú†?ö®?Ñ± Í≤??Ç¨
//	 * @param req
//	 * @return
//	 */
//	protected boolean isValid_CustomSession(HttpServletRequest req){
//		boolean b_ss = false;
//		if (req.getSession().getAttribute("user_id") != null 
//				&& !"".equals(req.getSession().getAttribute("user_id"))){
//			b_ss = true;
//		}
//		return b_ss;
//	}
	
	/**
	 * 
	 * @param codeGubn
	 * @return
	 *
	protected List<Map<String, Object>> getSimpleCodeList(String codeGubn){
		CodeManager mgr = new CodeManager(this.sqlSession);
		List<Map<String, Object>> list = mgr.getSimpleCodeList(codeGubn);
		return list;
	}*/
	
	/**
	 * request ?åå?ùºÎØ∏ÌÑ∞ Ï§? ?ã®?ùº Í±¥Ïùò ?ç∞?ù¥?Ñ∞Îß? Ï∂îÏ∂ú
	 * @param req
	 * @return
	 */
	protected Map<String, Object> getRequestMap(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<?> itr = req.getParameterMap().keySet().iterator();
		while(itr.hasNext()){
			String key = (String)itr.next();
			String[] value = null;
			int pos = key.indexOf(listPrefix);
			if (pos < 0){
				value = (String[])req.getParameterMap().get(key);
				if (value.length == 1){
					value[0] = StringUtil.toKor(value[0]);
					if (value[0].endsWith(br_tag)){
						value[0] = value[0].substring(0, value[0].length()-br_tag.length());
					}
					if (value[0].endsWith(br_style_tag)){
						value[0] = value[0].substring(0, value[0].length()-br_style_tag.length());
					}
					map.put(key, value[0]);
				}
			} else {
				continue;
			}
		}
		return map;
	}
	
	/**
	 * request ?åå?ùºÎØ∏ÌÑ∞ Ï§? Î¶¨Ïä§?ä∏ ?òï?Éú?ùò ?ç∞?ù¥?Ñ∞Îß? Ï∂îÏ∂ú 
	 * @param req
	 * @return
	 */
	protected List<Map<String, Object>> getRequestList(HttpServletRequest req){
		boolean isConv = false;
		if (req.getHeader(contentType).indexOf(chrset_utf_8) < 0){
			isConv = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<?> itr = req.getParameterMap().keySet().iterator();
		while(itr.hasNext()){
			String key = (String)itr.next();
			String[] value = null;
			int pos = key.indexOf(listPrefix);
			if (pos < 0){
				continue;
			} else{
				value = (String[])req.getParameterMap().get(key);
				key = key.substring(listPrefix.length() + pos);
				map.put(key, value);
			}
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		itr = map.keySet().iterator();
		while(itr.hasNext()){
			String key = (String)itr.next();
			String[] value = (String[])map.get(key);
			for (int i=0; i<value.length; i++){
				if (list.size() < value.length){
					Map<String, Object> innerMap = new HashMap<String, Object>();
					if (isConv) value[i] = StringUtil.toKor(value[i]);
					innerMap.put(key, value[i]);
					list.add(innerMap);
				}else{
					Map<String, Object> innerMap = (HashMap<String, Object>)list.get(i);
					if (isConv) value[i] = StringUtil.toKor(value[i]);
					innerMap.put(key, value[i]);
					list.set(i, innerMap);
				}
//				debug("org : [" + value[i] + "], conv : [" + StringUtil.toKor(value[i]) + "]");
			}			
		}
		
		return list;
	}
	
}
