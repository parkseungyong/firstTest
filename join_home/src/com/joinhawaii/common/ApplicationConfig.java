package com.joinhawaii.common;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.joinhawaii.manager.CodeManager;

public class ApplicationConfig {

	private SqlSession sqlSession;

	public static final String CODE_CORP = "003";
	public static final String CODE_ISLAND = "101";
	public static final String CODE_CATEGORY = "102";
	public static final String CODE_GRADE = "103";
	public static final String CODE_BREAKFAST = "104";
	public static final String CODE_CARCORP = "105";
	public static final String CODE_AIRCORP = "114";
	public static final String CODE_AIRCLASS = "115";
	
	public static final String KEY_ROOTCTXPATH = "rootContextPath"; 
	
	private static ConcurrentHashMap<String, Object> configMap = null;
	
	static{
		configMap = new ConcurrentHashMap<String, Object>();
	}
	
	public ApplicationConfig(SqlSession sqlsession){
		this.sqlSession = sqlsession;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSysCodeList(String code_gubn){
		if (!configMap.containsKey(code_gubn)){
			CodeManager mgr = new CodeManager(sqlSession);
			configMap.put(code_gubn, mgr.getSimpleCodeList(code_gubn));		
		}

		return (List<Map<String, Object>>)configMap.get(code_gubn);
	}
	
	public void setSysCodeList(String code_gubn, List<Map<String, Object>> list){
		configMap.put(code_gubn, list);
	}
	
	public void setConfigMap(String key, Object obj){
		configMap.put(key, obj);
	}
	
	public Object getConfigMap(String key){
		return configMap.get(key);
	}
	
	public List<Map<String, Object>> getCorpCodeList(){
		return getSysCodeList(CODE_CORP);
	}
	
	public List<Map<String, Object>> getIslandCodeList(){
		return getSysCodeList(CODE_ISLAND);
	}
	
	public List<Map<String, Object>> getCategoryCodeList(){
		return getSysCodeList(CODE_CATEGORY);
	}
	
	public List<Map<String, Object>> getHotelGradeCodeList(){
		return getSysCodeList(CODE_GRADE);
	}
	
	public List<Map<String, Object>> getBreakfastCodeList(){
		return getSysCodeList(CODE_BREAKFAST);
	}
	
	public List<Map<String, Object>> getCarCorpCodeList(){
		return getSysCodeList(CODE_CARCORP);
	}
	
	public List<Map<String, Object>> getAirCorpCodeList(){
		return getSysCodeList(CODE_AIRCORP);
	}
	
	public List<Map<String, Object>> getAirClassCodeList(){
		return getSysCodeList(CODE_AIRCLASS);
	}
}
