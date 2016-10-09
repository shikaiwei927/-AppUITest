package com.d3tech.app.test;
import java.security.PublicKey;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import com.d3tech.app.unit.*;
public class EncryptErhuo {
	public static String baseurl="https://120.55.182.40:8443/";
	public static String baseurlhttp="http://120.55.182.40:8080/";
	private static Logger logger = Logger.getLogger(EncryptErhuo.class.getName());
	
	//https方法
	public static void baseTestHttpsViaxls(String xlsname,String sheetname,String url,ArrayList<String> testparamlist,ArrayList<String> postparamlist) {
 	Iterator<Object[]> testdatatemp = new ExcelDataProvider(xlsname,sheetname);
			while(testdatatemp.hasNext()) {
			@SuppressWarnings("unchecked")
			Map<String,String> datamap =(Map<String,String>) testdatatemp.next()[0];
			logger.info("测试数据xls:"+datamap);
			Map<String,String> comparemap=new HashMap<String,String>();
			Map<String,String> postdatamap=new HashMap<String,String>();
			if(datamap.get("是否执行").equals("Y")){
				//遍历map
					for(String postparam:postparamlist) {
						if("parameter".equals(postparam)){
							String encryptdata=AESEncryptor.encrypt(datamap.get(postparam));
							postdatamap.put(postparam,encryptdata);
							
						}
						else{
							postdatamap.put(postparam,datamap.get(postparam));
						}
					}
					for(String testparam:testparamlist) {
						comparemap.put(testparam,datamap.get(testparam));
					}
					//处理比对数据
					String compareString=Map2Json.mapToJson(comparemap);
					Map<String,String> compareMaplast=Map2Json.jsonTomap(compareString);
					//处理发送数据
					String datajson =Map2Json.mapToJson(postdatamap);
					String result;
					String encryptdata=AESEncryptor.encrypt(datajson);
					//发送数据
					HttpClientUtil httpClientUtil =new HttpClientUtil();
					result=httpClientUtil.doPostViastring(url,encryptdata,"utf-8");
					logger.info("result is:"+result);
					logger.info("comparemaplast is"+compareMaplast);
					//比对数据
					Map<String,String> resultmap =Map2Json.jsonTomap(result);
					logger.info("resultmap is"+resultmap);
					boolean resultflag=true;
					if(resultmap.equals(compareMaplast)){
						logger.info("pass");
						resultflag=true;
					}
					else{
						resultflag=false;
						logger.error("failed");
					}
				if(!resultflag){
					logger.info("test failed,input data is: "+datamap);
					logger.info("结果是："+result);
					  ExcelModify modifyxls=new ExcelModify();
					  modifyxls.modifyxlsvianame(Integer.parseInt(datamap.get("编号")), xlsname, sheetname, "执行结果", "failed");
					  }
				else{
					logger.info("pass");
					
					ExcelModify modifyxls=new ExcelModify();
					modifyxls.modifyxlsvianame(Integer.parseInt(datamap.get("编号")), xlsname, sheetname, "执行结果", "pass");
				}
				}
			}
	}
	//http方法
	//http方法
	public static void basehttp(String xlsname,String sheetname,String url,ArrayList<String> testparlist,ArrayList<String> postparamlist) {
		Iterator<Object[]> 	testiterator = new ExcelDataProvider(xlsname,sheetname);
		while(testiterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map<String,String> parammap =(Map<String,String>) testiterator.next()[0];
			logger.info("测试数据xls"+parammap);
			Map<String,String> compareMap=new HashMap<String,String>();
			Map<String,String> postdataMap=new HashMap<String,String>();
				if(parammap.get("是否执行").equals("Y")){
					
						for(String postparam:postparamlist){
							postdataMap.put(postparam, parammap.get(postparam));
						}
						
						for(String testparam:testparlist){
							compareMap.put(testparam, parammap.get(testparam));
						}
						
						//处理比对数据
						String compareString=Map2Json.mapToJson(compareMap);
						Map<String,String> compareMaplast=Map2Json.jsonTomap(compareString);
						
						//发送
						
						
						
					String postjson=Map2Json.mapToJson(parammap);
					String encryptdata=AESEncryptor.encrypt(postjson);
					String result;
					result=HttpRequest.sendPost(url, encryptdata);
//					System.out.println(result);
					logger.info("result is"+result);
					logger.info("compareMaplast is"+compareMaplast);
					Map<String,String> resultmap=Map2Json.jsonTomap(result);
					logger.info("resultmap is"+resultmap);
					boolean resultYes=true;
						 if(resultmap.equals(compareMap)) {
							 logger.info("pass");
							resultYes=true;
						 }
						 else{
							 resultYes=false;
							 logger.error("failed");
						 } 
					if(!resultYes)	 {
						logger.info("测试失败,发送的数据是"+parammap);
						logger.info("结果是:"+result);
							ExcelModify modifyxls=new ExcelModify();
							modifyxls.modifyxlsvianame(Integer.parseInt(parammap.get("编号")),xlsname,sheetname,"执行结果","failed");
					}
					else {
						logger.info("测试通过:"+"pass");
						ExcelModify modifyxls = new ExcelModify();
						modifyxls.modifyxlsvianame(Integer.parseInt(parammap.get("编号")), xlsname, sheetname, "执行结果", "pass");
					}	
						
					}
					 }
				}
	public static void login(){
		ArrayList<String> testparamlist=new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("name");
		testparamlist.add("image");
		ArrayList<String> postparamlist=new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		
		
		baseTestHttpsViaxls("app/login", "login",baseurl+"user/login", testparamlist,postparamlist);
	  
	}
	public static void bindgateway() {
		ArrayList<String> testparlist=new ArrayList<String>();
		testparlist.add("reason");
		testparlist.add("state");
		ArrayList<String> postparamlist=new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		postparamlist.add("serial");
		basehttp("app/bindgateway", "bindgateway", baseurlhttp+"user/bind_gateway", testparlist,postparamlist); 
	}
	// 3.8RSA公钥
		public static void gatewayrsa() {
			ArrayList<String> testparlist = new ArrayList<String>();
			testparlist.add("reason");
			testparlist.add("state");
			testparlist.add("rsa");
			ArrayList<String> postparamlist = new ArrayList<String>();
			postparamlist.add("phone");
			postparamlist.add("password");
			postparamlist.add("serial");
			baseTestHttpsViaxls("app/gatewayrsa", "gatewayrsa", baseurl+"gw/gateway_rsa", testparlist,postparamlist);
		}
	
		public static void sendfeedback() {
			ArrayList<String> testparlist=new ArrayList<String>();
			testparlist.add("reason");
			testparlist.add("state");
			ArrayList<String> postparamlist=new ArrayList<String>();
			postparamlist.add("phone");
			postparamlist.add("content");
			postparamlist.add("contace");
			basehttp("app/sendfeedback", "sendfeedback", baseurlhttp+"app/send_feedback", testparlist,postparamlist); 
		}
	
	
	
	
	
	
	

	public static void sendoperate() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
//		testparamlist.add("result");
//		testparamlist.add("code");
//		testparamlist.add("desc");
		ArrayList<String> postparamlist = new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		postparamlist.add("dev_type");
		postparamlist.add("operation");
		postparamlist.add("param");
		postparamlist.add("serial");
		postparamlist.add("uuid");
		
	baseTestHttpsViaxls("app/sendoperate", "sendoperate", baseurl+"sub/send_operate", testparamlist,postparamlist);
	}

		public	static void logoutviahttps(){
		  HashMap<String , String>	datamap=new HashMap<String ,String>();
		  String password="7ujMko0";
		  String md5passwd=MD5util.MD5(password);
		  datamap.put("phone","17767188213");
		  datamap.put("password", md5passwd);
		  System.out.println(datamap);
		  String datajson=Map2Json.mapToJson(datamap);
		  System.out.println(datajson);
		  String result;
		  String encryptdata=AESEncryptor.encrypt(datajson);
		  System.out.println(encryptdata);
		  String postdata =new String(encryptdata);
		  System.out.println(postdata);
		  HttpClientUtil httpClientUtil = new HttpClientUtil();
		  //result=HttpRequest.sendPost(baseurl+"user/login", postdata);
		  result=httpClientUtil.doPostViastring(baseurl+"user/login", postdata, "utf-8");	
		  System.out.println(result);
	   }

		
		public static String testgetrsa() throws Exception{
			  HashMap<String , String>	datamap=new HashMap<String ,String>();
			  String password="7ujMko0";
			  String md5passwd=MD5util.MD5(password);
			  datamap.put("phone","17767188213");
			  datamap.put("password", md5passwd);
			  datamap.put("serial", "TEST000000000001"); 
			  System.out.println(datamap);
			  String datajson=Map2Json.mapToJson(datamap);
			  System.out.println(datajson);
			  String result;
			  String encryptdata=AESEncryptor.encrypt(datajson);
			  System.out.println(encryptdata);
			  String postdata =new String(encryptdata);
			  System.out.println(postdata);
			  HttpClientUtil httpClientUtil = new HttpClientUtil();
			  //result=HttpRequest.sendPost(baseurl+"user/login", postdata);
			  result=httpClientUtil.doPostViastring(baseurl+"gw/gateway_rsa", postdata, "utf-8");	
			  System.out.println(result);
			  Map<String, String>  resultmap=Map2Json.jsonTomap(result);
			  String rsaString=resultmap.get("rsa");
			  return rsaString; 
			  
		}

	
	public static void main(String args[]){
//			login();
//		bindgateway();
	
			
				try {
					while(true) {
					logoutviahttps();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
//		logoutviahttps();
//		gatewayrsa();
//		logoutviahttps();
//		testgetrsa();
		
	}

}
