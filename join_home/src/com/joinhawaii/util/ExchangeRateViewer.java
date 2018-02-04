package com.joinhawaii.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class ExchangeRateViewer {

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> exchangView(){
		
		HttpURLConnection con = null;
		Map<String, String> retMap = null;
		
		try {
			URL url = new URL("http://fx.kebhana.com/FER1101M.web");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(3000);
			con.setReadTimeout(3000);

	        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            
            ObjectMapper mapper = new ObjectMapper();
            String retStr = new String(buffer.toString().getBytes("UTF-8"), "UTF-8");
            retStr = retStr.replaceAll("var exView = ", "").replaceAll("\t", "").replace("},]", "}]");
//            System.out.println(retStr);
            Map<String, Object> map = new HashMap<String, Object>();
            map = mapper.readValue(retStr, new TypeReference<Map<String, Object>>(){});
            ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)map.get("ë¦¬ìŠ¤?Š¸");
            
            for(Map<String,String> tempMap : list){
//            	System.out.println(tempMap.get("?†µ?™”ëª?"));
            	if ("ë¯¸êµ­ USD".equals(tempMap.get("?†µ?™”ëª?"))){
            		retMap = tempMap;
            		retMap.put("date", (String)map.get("?‚ ì§?"));
            		break;
            	}
            }
//            System.out.println(retMap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (con != null) {
            	con.disconnect();
            }
        }

		return retMap;
	}
	
	public static void main(String[] args){
		ExchangeRateViewer.exchangView();
	}
}
