package com.d3tech.app.unit;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Map2Json {
	
	
	/**
	 * ��Mapת��ΪJson
	 * 
	 * @param map
	 * @return String
	 */
	public static <T> String mapToJson(Map<String, T> map) {
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(map);
		 return jsonStr;
	}
	
	public static Map<String, String> jsonTomap(String jsondata) {
		 Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, String> jsonMap = gson.fromJson(jsondata, HashMap.class);
		return jsonMap;
	}
	

}
