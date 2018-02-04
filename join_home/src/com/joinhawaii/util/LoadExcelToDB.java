package com.joinhawaii.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadExcelToDB {

	public static void main(String[] args) throws Exception{
//		excelLoad();
	}
	public static void excelLoad(SqlSession sqlsession) throws Exception{
//	public static void excelLoad() throws Exception{
		FileInputStream fis=new FileInputStream("D:/psy_work/joinhawaii/HOTEL_2017-03-20T15_32_25.xlsx");

		 XSSFWorkbook workbook=new XSSFWorkbook(fis);

		 int rowindex=0;

		 int columnindex=0;

		 //?ãú?ä∏ ?àò (Ï≤´Î≤àÏß∏ÏóêÎß? Ï°¥Ïû¨?ïòÎØ?Î°? 0?ùÑ Ï§??ã§) 
		 //ÎßåÏïΩ Í∞? ?ãú?ä∏Î•? ?ùΩÍ∏∞ÏúÑ?ï¥?Ñú?äî FORÎ¨∏ÏùÑ ?ïúÎ≤àÎçî ?èå?†§Ï§??ã§
		 XSSFSheet sheet=workbook.getSheetAt(0);

		 //?ñâ?ùò ?àò 
		 int rows=sheet.getPhysicalNumberOfRows();

		 for(rowindex = 1; rowindex < rows ; rowindex++){ //?ñâ?ùÑ?ùΩ?äî?ã§ 
			 XSSFRow row = sheet.getRow(rowindex);
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("regist_user", "roam73");
			 if(row !=null){ //????ùò ?àò 
				 int cells = row.getPhysicalNumberOfCells();
				 for(columnindex=1; columnindex <= 42; columnindex++){ //???Í∞íÏùÑ ?ùΩ?äî?ã§
					 XSSFCell cell = row.getCell(columnindex);
					 String value = "";
					 if(cell == null){ 
//						 continue;
					 }else{ //????ûÖÎ≥ÑÎ°ú ?Ç¥?ö© ?ùΩÍ∏?
						 switch (cell.getCellType()){ 
						 case XSSFCell.CELL_TYPE_FORMULA: 
							 value=cell.getCellFormula();
							 break;
						 case XSSFCell.CELL_TYPE_NUMERIC: 
							 value=cell.getNumericCellValue()+"";
							 break;
						 case XSSFCell.CELL_TYPE_STRING: 
							 value=cell.getStringCellValue()+"";
							 break;
						 case XSSFCell.CELL_TYPE_BLANK: 
							 value=cell.getBooleanCellValue()+"";
							 break;
						 case XSSFCell.CELL_TYPE_ERROR: 
							 value=cell.getErrorCellValue()+"";
							 break;
						 }
						 if (value.endsWith("<br>")){
							 value = value.substring(0, value.length() - "<br>".length());
						 }
					 }
					 switch (columnindex) 
					 {
					 case 1 :
						 map.put("hotel_nm_kr", value);
						 break;
					 case 2 :
						 map.put("hotel_nm_eng", value);
						 break;
					 case 4 :
						 map.put("island_cd", value);
						 break;
					 case 5 :
						 map.put("resort_fee", value);
						 break;
					 case 6 :
						 map.put("porterage", value);
						 break;
					 case 7 :
						 map.put("grade_cd", value);
						 break;
					 case 8 :
						 map.put("area_nm", value);
						 break;
					 case 9 :
						 map.put("recommend_yn", value);
						 break; 
					 case 10 :
						 map.put("show_special", value);
						 break; 
					 case 11 :
						 map.put("sale_title_1", value);
						 break;
					 case 12 :
						 map.put("sale_title_2", value);
						 break;
					 case 13 :
						 map.put("min_sales_price", value);
						 break;
					 case 15 :
						 map.put("promotion_info", value);
						 break; 
					 case 16 :
						 map.put("tel_no", value);
						 break; 
					 case 17 :
						 map.put("address", value);
						 break; 
					 case 18 :
						 map.put("default_info", value);
						 break; 
					 case 19 :
						 map.put("resort_fee_info", value);
						 break; 
					 case 20 :
						 map.put("hotel_convenience", value);
						 break;
					 case 21 :
						 map.put("room_convenience", value);
						 break; 
					 case 22 :
						 map.put("check_in_time", value);
						 break; 
					 case 23 :
						 map.put("check_out_time", value);
						 break;
					 case 24 :
						 map.put("parking_info", value);
						 break;
					 case 25 :
						 map.put("wifi_info", value);
						 break;
					 case 29 :
						 map.put("use_yn", value);
						 break;
					 case 35 :
						 map.put("cancel_policy", value);
						 break;
					 case 37 :
						 map.put("cancel_able_day", value);
						 break; 
					 case 38 :
						 map.put("child_limit", value);
						 break; 
					 case 39 :
						 map.put("keyword", value);
						 break;
					 case 40 :
						 map.put("weekly_deal_yn", value);
						 break; 
					 case 41 :
						 map.put("weekly_deal_info", value);
						 break; 
						 
					 default:
						 break;
					 }
					 
//					 System.out.println("Í∞? ??? ?Ç¥?ö© (" + columnindex + "): "  +value);
				 } 
			}
//			System.out.println(map);
			 sqlsession.insert("hotel.insert_Hotel", map);
		} 		
	}
}
