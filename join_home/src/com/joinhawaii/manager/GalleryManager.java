package com.joinhawaii.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joinhawaii.common.JoinHawaiiMapConstants;

public class GalleryManager extends BaseManager {

	public GalleryManager(SqlSession sqlsession) {
		super(sqlsession);
		// TODO Auto-generated constructor stub
	}

	public GalleryManager(SqlSession sqlsession, String user) {
		super(sqlsession, user);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getGalleryList(Map<String, Object> param){
		
		param.put(JoinHawaiiMapConstants.KEY_SQLID, "gallery.select_GalleryList");
		List<Map<String, Object>> list = this.getList(param);
		
		return list;
		
	}
	
	/**
	 * @param list
	 */
	public void saveGalleryList(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			if ("C".equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "gallery.insert_GalleryList");
				this.insert(map);
			} else if ("U".equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "gallery.update_GalleryList");
				this.update(map);
			} else if ("D".equals((String)map.get(JoinHawaiiMapConstants.KEY_JOBTYPE))){
				map.put(JoinHawaiiMapConstants.KEY_SQLID, "gallery.delete_GalleryList");
				this.delete(map);
			}			
		}
	} 
	
}
