package com.joinhawaii.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joinhawaii.common.JoinHawaiiMapConstants;

public class AirManager extends BaseManager {

	public AirManager(SqlSession sqlsession) {
		super(sqlsession);
		// TODO Auto-generated constructor stub
	}

	public AirManager(SqlSession sqlsession, String user) {
		super(sqlsession, user);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getAirList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.select_AirList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
		
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getAirInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.select_AirInfo");
		Map<String, Object> retMap = this.getMap(param);
		
		return retMap;
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public int insertAirInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.insert_AirInfo");
		int retVal = this.insert(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateAirInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.update_AirInfo");
		int retVal = this.update(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateAirImage(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.update_AirImage");
		int retVal = this.update(param);
		
		return retVal;		
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int deleteAirInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.delete_AirInfo");
		int retVal = this.delete(param);
		
		return retVal;
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getAirPriceList(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "air.select_AirPriceList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
	}
	

	/**
	 * @param list
	 */
	public void saveAirPriceList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "air.insert_AirPriceList");
				this.insert(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "air.update_AirPriceList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "air.delete_AirPriceList");
				this.delete(map);
			}
		}
	}
}
