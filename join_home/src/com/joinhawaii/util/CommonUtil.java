package com.joinhawaii.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

	/**
	 * request parameter�? Map?���? 반환
	 * @param request
	 * @return
	 */
	public static Map<String, Object> convParameterMap(HttpServletRequest request){

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<?> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
			String paramName = (String)enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

		// Parameter�? 배열?�� 경우
			if(parameters.length > 1){
				parameterMap.put(paramName, parameters);
		// Parameter�? 배열?�� ?��?�� 경우
			}else{
				parameterMap.put(paramName, parameters[0]);
			}
		}

		return parameterMap;
	}
}
