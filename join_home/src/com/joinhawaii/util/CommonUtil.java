package com.joinhawaii.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

	/**
	 * request parameterë¥? Map?œ¼ë¡? ë°˜í™˜
	 * @param request
	 * @return
	 */
	public static Map<String, Object> convParameterMap(HttpServletRequest request){

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<?> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
			String paramName = (String)enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

		// Parameterê°? ë°°ì—´?¼ ê²½ìš°
			if(parameters.length > 1){
				parameterMap.put(paramName, parameters);
		// Parameterê°? ë°°ì—´?´ ?•„?‹Œ ê²½ìš°
			}else{
				parameterMap.put(paramName, parameters[0]);
			}
		}

		return parameterMap;
	}
}
