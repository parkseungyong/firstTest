package com.joinhawaii.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.joinhawaii.common.JoinHawaiiDbFieldConstants;
import com.joinhawaii.common.JoinHawaiiMapConstants;

public abstract class BaseManager {

	@Autowired
	protected SqlSession sqlSession;
	
	protected String regist_user;
	
	protected String change_user;
	
	protected static final String QUERY_INSERT = "C";
	protected static final String QUERY_UPDATE = "U";
	protected static final String QUERY_DELETE = "D";
	
	public BaseManager(SqlSession sqlsession){
		this.sqlSession = sqlsession;
	}
	
	public BaseManager(SqlSession sqlsession, String user){
		this.sqlSession = sqlsession;
		this.regist_user = user;
		this.change_user = user;
	}
	
	/**
	 * @param param
	 * @return
	 */
	protected List<Map<String, Object>> getList(Map<String, Object> param){
		
		String sqlId = (String)param.get(JoinHawaiiMapConstants.KEY_SQLID);
		List<Map<String, Object>> list =  this.sqlSession.selectList(sqlId, param);
		
		return list;
	}
	
	/**
	 * @param param
	 * @return
	 */
	protected Map<String, Object> getMap(Map<String, Object> param){
		
		String sqlId = (String)param.get(JoinHawaiiMapConstants.KEY_SQLID); 
		Map<String, Object> map =  this.sqlSession.selectOne(sqlId, param);
		
		return map;
	}
	
	protected int insert(Map<String, Object> param) {
		String sqlId = (String)param.get(JoinHawaiiMapConstants.KEY_SQLID);
		param.put(JoinHawaiiDbFieldConstants.DB_REGISTER, this.regist_user);
		int retVal = this.sqlSession.insert(sqlId, param);
		
		return retVal;
	}
	
	protected int update(Map<String, Object> param) {
		String sqlId = (String)param.get(JoinHawaiiMapConstants.KEY_SQLID);
		param.put(JoinHawaiiDbFieldConstants.DB_CHANGER, this.change_user);
		int retVal = this.sqlSession.update(sqlId, param);
		return retVal;
	}
	
	protected int delete(Map<String, Object> param) {
		String sqlId = (String)param.get(JoinHawaiiMapConstants.KEY_SQLID);
		int retVal = this.sqlSession.delete(sqlId, param);
		
		return retVal;
	}
}
