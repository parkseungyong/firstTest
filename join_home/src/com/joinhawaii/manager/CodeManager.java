package com.joinhawaii.manager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joinhawaii.common.JoinHawaiiDbFieldConstants;
import com.joinhawaii.common.JoinHawaiiMapConstants;
import com.joinhawaii.manager.BaseManager;

public class CodeManager extends BaseManager {

	public CodeManager(SqlSession sqlsession) {
		super(sqlsession);
		// TODO Auto-generated constructor stub
	}
	
	public CodeManager(SqlSession sqlsession, String user) {
		super(sqlsession, user);
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> getSimpleCodeList(String codeGubn){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JoinHawaiiDbFieldConstants.DB_CODEGUBN, codeGubn);
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "code.select_simpleCodeList");
		
		List<Map<String, Object>> list = this.getList(param);
		return list;
	}
	
	public List<Map<String, Object>> getCodeList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "code.select_CodeList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
		
	}
	
	public void saveCodeList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if ("C".equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "code.insert_Code");
				this.insert(map);
			} else if ("U".equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "code.update_Code");
				this.update(map);
			} else if ("D".equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "code.delete_Code");
				this.update(map);
			}
		}
	}
	
	
	public Map<String, Object> getCodeListHeader(String code){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JoinHawaiiDbFieldConstants.DB_CODE, code);
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "code.select_CodeTitle");
		Map<String, Object> map = this.getMap(param);
		return map;
		
	}
}
