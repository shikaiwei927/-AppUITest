package com.d3tech.app.test;

import java.awt.List;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.d3tech.app.unit.AESEncryptor;
import com.d3tech.app.unit.ExcelDataProvider;
import com.d3tech.app.unit.HttpRequest;
import com.d3tech.app.unit.Map2Json;

import bsh.This;

import com.d3tech.app.unit.MD5Util;

public class ErDianLing{
	public static String baseurl="http://121.41.50.221:8080/WebServer/";
	
	public static void baseTest(String xlsname,String sheetname,String url,ArrayList<String> testparamlist){
		Iterator<Object[]> testdatatemp=new ExcelDataProvider(xlsname,sheetname);
		while(testdatatemp.hasNext()){
			@SuppressWarnings("unchecked")
			Map<String,String> datamap =(Map<String,String>) testdatatemp.next()[0];
			if(datamap.get("是否执行").equals("Y")){
				String datajson=Map2Json.mapToJson(datamap);
				  System.out.println(datajson);
				  String result;
				  String encryptdata=AESEncryptor.encrypt(datajson);
				  System.out.println(encryptdata);
				  
				  result=HttpRequest.sendPost(url, encryptdata);
				  Map<String, String> resultmap=Map2Json.jsonTomap(result);
				  boolean flag=false;
				  for(String testparam:testparamlist){
					  if((resultmap.get(testparam)).equals(datamap.get(testparam))){
						  flag=true;
					  }
					  else{
						  flag=false;
						  break;
					  }
				  }
				  
				  if(!flag){
					  System.out.println("test failed,input data is "+datamap);
					  System.out.println(result);
				  }
				  else{
					  System.out.println("pass");
					  System.out.println(result);
				  }
			}
		}
		
		
	}
	
	
	public static void login(){
		
		ArrayList<String> testparamlist=new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTest("app/login", "login", baseurl+"user/login", testparamlist); 
	  
	}
	

	
	public static void logout(){
		
		  MD5util temp=new MD5util();
		  HashMap datamap=new HashMap();
		  String password="123456";
		  String md5passwd=temp.MD5(password);
		  datamap.put("phone","17767188213");
		  datamap.put("passwd", md5passwd);
		  String datajson=Map2Json.mapToJson(datamap);
		  System.out.println(datajson);
		  String result;
		  String encryptdata=AESEncryptor.encrypt(datajson);
		  System.out.println(encryptdata);
		  
		  result=HttpRequest.sendPost(baseurl+"user/login", encryptdata);
		  System.out.println(result);

	}
	
	
	
	
	public static void main(String args[]){
		
		login();
		

	}

}
