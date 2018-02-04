package com.joinhawaii.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joinhawaii.common.JoinHawaiiMapConstants;

public class HotelManager extends BaseManager {

	public HotelManager(SqlSession sqlsession) {
		super(sqlsession);
		// TODO Auto-generated constructor stub
	}

	public HotelManager(SqlSession sqlsession, String user) {
		super(sqlsession, user);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getHotelList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.select_hotel_list");
		List<Map<String, Object>> list = this.getList(param);
		return list;
		
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public Map<String, Object> getHotelInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.select_hotel_info");
		Map<String, Object> retMap = this.getMap(param);
		
		return retMap;
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public int insertHotelInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.insert_Hotel");
		int retVal = this.insert(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateHotelInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_Hotel");
		int retVal = this.update(param);
		
		return retVal;
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int updateHotelImage(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_HotelImage");
		int retVal = this.update(param);
		
		return retVal;		
	}
	
	/**
	 * @param param
	 * @return
	 */
	public int deleteHotelInfo(Map<String, Object> param){
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.delete_Hotel");
		int retVal = this.delete(param);
		
		return retVal;
	}
	
	/**
	 * @param list
	 */
	public void saveHotelList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_HotelList");
				this.update(map);
			}
		}
	}
	
	/**
	 * @param list
	 */
	public void saveRoomList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.insert_RoomList");
				this.insert(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_RoomList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.delete_RoomList");
				this.delete(map);
			}
		}
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getRoomList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.select_RoomList");
		List<Map<String, Object>> list = this.getList(param);
		return list;
		
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getPromotionList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.select_PromotionList");
		List<Map<String, Object>> list = this.getList(param);
		
		return list;
	}
	
	/**
	 * @param list
	 */
	public void savePromotionList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.insert_PromotionList");
				this.insert(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_PromotionList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.delete_PromotionList");
				this.delete(map);
			}			
		}
	}
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getPriceList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.select_PriceList");
		List<Map<String, Object>> list = this.getList(param);
		
		return list;
		
	}
	
	/**
	 * @param list
	 */
	public void savePriceList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.insert_PriceList");
				this.insert(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_PriceList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.delete_PriceList");
				this.delete(map);
			}			
		}
	}
	
	
	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStopSellList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.select_StopSellList");
		List<Map<String, Object>> list = this.getList(param);
		
		return list;
		
	} 
	
	/**
	 * @param list
	 */
	public void saveStopSellList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if (QUERY_INSERT.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.insert_StopSellList");
				this.insert(map);
			} else if (QUERY_UPDATE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.update_StopSellList");
				this.update(map);
			} else if (QUERY_DELETE.equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "hotel.delete_StopSellList");
				this.delete(map);
			}			
		}
	} 
}
