package com.joinhawaii.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joinhawaii.common.JoinHawaiiMapConstants;

public class CarManager extends BaseManager {

	public CarManager(SqlSession sqlsession) {
		super(sqlsession);
		// TODO Auto-generated constructor stub
	}

	public CarManager(SqlSession sqlsession, String user) {
		super(sqlsession, user);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCarList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.select_CarList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
		
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getCarInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.select_CarInfo");
		Map<String, Object> retMap = this.getMap(param);
		
		return retMap;
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public int insertCarInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.insert_CarInfo");
		int retVal = this.insert(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateCarInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.update_CarInfo");
		int retVal = this.update(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateCarImage(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.update_CarImage");
		int retVal = this.update(param);
		
		return retVal;		
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int deleteCarInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.delete_CarInfo");
		int retVal = this.delete(param);
		
		return retVal;
	}
	
	/**
	 * @param list
	 */
	public void saveCarList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.update_CarList");
				this.update(map);
			}
		}
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCarPromotionList(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.select_CarPromotionList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
	}
	

	/**
	 * @param list
	 */
	public void saveCarPromotionList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.insert_CarPromotionList");
				this.insert(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.update_CarPromotionList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.delete_CarPromotionList");
				this.delete(map);
			}
		}
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCarPriceList(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "car.select_CarPriceList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
	}
	
	
	/**
	 * @param list
	 */
	public void saveCarPriceList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.insert_CarPriceList");
				this.update(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.update_CarPriceList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "car.delete_CarPriceList");
				this.delete(map);	
			}
		}
	}
}
