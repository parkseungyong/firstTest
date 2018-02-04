package com.joinhawaii.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joinhawaii.common.JoinHawaiiMapConstants;

public class OptionManager extends BaseManager {

	public OptionManager(SqlSession sqlsession) {
		super(sqlsession);
		// TODO Auto-generated constructor stub
	}

	public OptionManager(SqlSession sqlsession, String user) {
		super(sqlsession, user);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getOptionList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.select_OptionList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
		
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOptionInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.select_OptionInfo");
		Map<String, Object> retMap = this.getMap(param);
		
		return retMap;
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public int insertOptionInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.insert_OptionInfo");
		int retVal = this.insert(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateOptionInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionInfo");
		int retVal = this.update(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateOptionImage(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionImage");
		int retVal = this.update(param);
		
		return retVal;		
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int deleteOptionInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.delete_OptionInfo");
		int retVal = this.delete(param);
		
		return retVal;
	}
	
	/**
	 * @param list
	 */
	public void saveOptionList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionList");
				this.update(map);
			}
		}
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getOptionProducList(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.select_OptionProductList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOptionProductInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.select_OptionProductInfo");
		Map<String, Object> retMap = this.getMap(param);
		
		return retMap;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int insertOptionProduct(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.insert_OptionProduct");
		int retVal = this.insert(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateOptionProduct(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionProduct");
		int retVal = this.update(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateOptionProductImage(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionProductImage");
		int retVal = this.update(param);
		
		return retVal;		
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int deleteOptionProduct(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.delete_OptionProduct");
		int retVal = this.delete(param);
		
		return retVal;
	}
	
	/**
	 * @param list
	 */
	public void saveOptionProductList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionProductList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "option.delete_OptionProduct");
				this.delete(map);
			}
		}
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public int copyOptionProduct(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.copy_OptionProductInfo");
		int retVal = this.insert(param);
		
		return retVal;
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getOptionBlackoutList(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "option.select_OptionBlackoutList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
	}
	
	
	/**
	 * @param list
	 */
	public void saveOptionBlackoutList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "option.insert_OptionBlackoutList");
				this.update(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "option.update_OptionBlackoutList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "option.delete_OptionBlackoutList");
				this.delete(map);	
			}
		}
	}
}
