package com.d3tech.app.test;
import java.security.PublicKey;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import com.d3tech.app.unit.*;

public class TestRSA {

	public static String baseurl="https://120.55.182.40:8443/";
	private static Logger logger = Logger.getLogger(EncryptErhuo.class.getName());
	public static String httpurl="http://120.55.182.40:8080/";
	//https方法
	public static void baseTestHttpsViaxls(String xlsname,String sheetname,String url,ArrayList<String> testparamlist,ArrayList<String> postparamlist){//ArrayList 链表类
		Iterator<Object[]> testdatatemp = new ExcelDataProvider(xlsname,sheetname);
		while(testdatatemp.hasNext()) {
		@SuppressWarnings("unchecked")
		Map<String,String> datamap =(Map<String,String>) testdatatemp.next()[0];
		logger.info("测试数据xls:"+datamap);
		Map<String,String> compareMap=new HashMap<String,String>();
		Map<String,String> postdatamap=new HashMap<String,String>();
		if(datamap.get("是否执行").equals("Y")){
				for(String postparam:postparamlist){
					postdatamap.put(postparam, datamap.get(postparam));
				}
				for(String testparam:testparamlist){
					compareMap.put(testparam, datamap.get(testparam));
				}
				//处理比对数据
				String compareString=Map2Json.mapToJson(compareMap);
				Map<String,String> compareMaplast=Map2Json.jsonTomap(compareString);
				//处理发送数据
				String datajson=Map2Json.mapToJson(postdatamap);
				String result;
				String encryptdata=AESEncryptor.encrypt(datajson);
				//发送数据
				HttpClientUtil httpClientUtil = new HttpClientUtil();
				result=httpClientUtil.doPostViastring(url, encryptdata, "utf-8");
				logger.info("result is "+result);
				logger.info("comparemaplast is "+compareMaplast);
				//比对数据
				Map<String, String> resultmap=Map2Json.jsonTomap(result);
				logger.info("resultmap is "+resultmap);
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
				logger.info("test failed,input data is "+datamap);
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
	
//	    keySet()会生成KeyIterator迭代器,其next方法只返回其key值. 
//    entrySet()方法会生成EntryIterator 迭代器,其next方法返回一个Entry对象的一个实例,其中包含key和value. 

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
					logger.info("result is："+result);
					logger.info("compareMaplast is："+compareMaplast);
					Map<String,String> resultmap=Map2Json.jsonTomap(result);
					logger.info("resultmap is："+resultmap);
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
		//testparamlist.add("name");
		//testparamlist.add("image");
		ArrayList<String> postparamlist=new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		baseTestHttpsViaxls("app/login", "login", baseurl+"user/login", testparamlist,postparamlist); 
	  
	}
	public static void bindgateway() {
		ArrayList<String> testparlist=new ArrayList<String>();
		testparlist.add("reason");
		testparlist.add("state");
		ArrayList<String> postparamlist=new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		postparamlist.add("serial");
		basehttp("app/bindgateway", "bindgateway", httpurl+"user/bind_gateway", testparlist,postparamlist); 
	}
	
	public static void sharegateway() {
		ArrayList<String> testparlist=new ArrayList<String>();
		ArrayList<String> postparamlist=new ArrayList<String>();
		testparlist.add("reason");
		testparlist.add("state");
		postparamlist.add("phone");
		postparamlist.add("password");
		basehttp("app/sharegateway", "sharegateway", baseurl+"user/share_gateway", testparlist,postparamlist); 
	}
	public static void deletesharegateway() {
		ArrayList<String> testparlist=new ArrayList<String>();
		
		testparlist.add("reason");
		testparlist.add("state");
		ArrayList<String> testparamlist=new ArrayList<String>();
		testparamlist.add("phone");
		basehttp("app/deletesharegateway", "deletesharegateway", baseurl+"user/delete_gateway_share", testparlist,testparamlist); 
	}
	
	public static void unbindgateway() {
		ArrayList<String> testparlist=new ArrayList<String>();
		ArrayList<String> postparamlist=new ArrayList<String>();
		testparlist.add("reason");
		testparlist.add("state");
		postparamlist.add("phone");
		basehttp("app/unbindgateway", "unbindgateway", baseurl+"user/unbind_gateway", testparlist,postparamlist); 
	}
	public static void updategateway() {
		ArrayList<String> testparamlist=new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("result");
		testparamlist.add("code");
		testparamlist.add("desc");
		ArrayList<String> postparamlist=new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		
		basehttp("app/updategateway", "updategateway", baseurl+"gw/update_gateway", testparamlist,postparamlist); 
	}
//	3.8RSA公钥
	public static void gatewayrsa() {
		ArrayList<String> testparlist=new ArrayList<String>();
		testparlist.add("reason");
		testparlist.add("state");
		testparlist.add("rsa");
		ArrayList<String> postparamlist=new ArrayList<String>();
		postparamlist.add("phone");
		postparamlist.add("password");
		
		basehttp("app/gatewayrsa", "gatewayrsa", baseurl+"gw/gateway_rsa", testparlist,postparamlist); 
	}
//	4.1智能设备下发指令
	/*
	public static void sendoperate() {
		ArrayList<String> testparamlist=new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("result");
		testparamlist.add("code");
		testparamlist.add("desc");
		ArrayList<String> postparamlist=new ArrayList<String>();
		basehttp("app/sendoperate", "sendoperate", baseurl+"sub/send_operate", testparamlist,postparamlist); 
	}
	*/
	//3.1修改智能网关名称
	public static void modifygatewayname() {
	ArrayList<String> testparamlist=new ArrayList<String>();
	testparamlist.add("reason");
	testparamlist.add("state");
	ArrayList<String> postparamlist=new ArrayList<String>();
	basehttp("app/modifygatewayname", "modifygatewayname", baseurl+"gw/modify_gateway_name", testparamlist,postparamlist); 
}

//	3.2获取网关设备状态 (待测）
	public static void gatewaystatus() {
	ArrayList<String> testparamlist=new ArrayList<String>();
	testparamlist.add("reason");
	testparamlist.add("state");
	testparamlist.add("gateway");
	ArrayList<String> postparamlist=new ArrayList<String>();
	basehttp("app/gatewaystatus", "gatewaystatus", baseurl+"gw/gateway_status", testparamlist,postparamlist); 
}

//3.3获取子设备列表
	public static void gatewaydevices() {
	ArrayList<String> testparamlist=new ArrayList<String>();
	testparamlist.add("reason");
	testparamlist.add("state");
	testparamlist.add("isLastVersion");
	testparamlist.add("devices");
	testparamlist.add("uuid");
	ArrayList<String> postparamlist=new ArrayList<String>();
	basehttp("app/gatewaydevices", "gatewaydevices", baseurl+"gw/gateway_devices", testparamlist,postparamlist); 
}

	public static void logoutviahttps(){
		  HashMap<String , String>	datamap=new HashMap<String ,String>();
		  String password="123456";
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
	
	
	public static void testviahttp()  {
		  try {
			HashMap<String , String>	datamap=new HashMap<String ,String>();
			  String password="7ujMko0";
			  String md5passwd=MD5util.MD5(password);
			  datamap.put("phone","17767188213");
			  datamap.put("password", md5passwd);
			  datamap.put("serial", "TEST000000000001");
//			  datamap.put("uuid", "aae409dc827a46f4a4939b7ae6bd2c3b");//江
			  //datamap.put("uuid", "b4def46a5a6a4289a4a07f8040d44ed3");
//			  datamap.put("uuid", "6e1ef5216ba34196ad1d7d2d44f1e539"); //阿拉丁
//			  datamap.put("uuid", "f9068e33a02e4a30bfb71b960a69cfd9"); //

		  		datamap.put ( "uuid","30f5d2ef318148b18b3f4c4bafc81890");//阿拉丁
			  //datamap.put("index", "1");
			  //datamap.put("name", "aaaa");
			  datamap.put("dev_type", "1");
			  datamap.put("operation", "Unlock");
			  //rsa加密

			  String params="pwd=2580";
			  PublicKey pk = RSAUtil.loadPublicKey(testgetrsa());
			  byte[] rawparam = RSAUtil.encryptData(params.getBytes(), pk);
			  String finalparam = Base64.encode(rawparam).replace(" ", "");
			  System.out.println(finalparam); 
			  datamap.put("parameter", finalparam);
			  System.out.println(datamap);
			  String datajson=Map2Json.mapToJson(datamap);
			  System.out.println(datajson);
			  String result;
			  String encryptdata=AESEncryptor.encrypt(datajson);
			  System.out.println(encryptdata);
			  String postdata =new String(encryptdata);
			  System.out.println(postdata);
			  HttpClientUtil httpClientUtil = new HttpClientUtil();
			  result=httpClientUtil.doPostViastring(baseurl+"sub/send_operate", postdata, "utf-8");	
			  System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//4.7.5
	public static String testgetrsa() throws Exception{
		  HashMap<String , String>	datamap=new HashMap<String ,String>();
		  String password="7ujMko0";
		  String md5passwd=MD5util.MD5(password);
		  datamap.put("phone","17767188213");
		  datamap.put("password", md5passwd);
		  datamap.put("serial", "TEST000000000001"); 
		  datamap.put("uuid","30f5d2ef318148b18b3f4c4bafc81890");
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
		  result=httpClientUtil.doPostViastring(httpurl+"sub/lock_status", postdata, "utf-8");	
		  System.out.println(result);
		  Map<String, String>  resultmap=Map2Json.jsonTomap(result);
		  String rsaString=resultmap.get("pub_key");
		  return rsaString; 
	}
	
	//4.7.5
		public static void resetvalidscode() {
			try {
				HashMap<String , String>	datamap=new HashMap<String ,String>();
				  String password="7ujMko0";
				  String md5passwd=MD5util.MD5(password);
				  datamap.put("phone","17767188213");
				  datamap.put("password", md5passwd);
				  datamap.put("serial", "TEST000000000001");
				  datamap.put ( "uuid","30f5d2ef318148b18b3f4c4bafc81890");//阿拉丁
				  datamap.put("index", "1");
				  datamap.put("name", "001号密码");
				  String params="1234=pwd&2345=pwd&1=1";
				  PublicKey pk = RSAUtil.loadPublicKey(testgetrsa());
				  byte[] rawparam = RSAUtil.encryptData(params.getBytes(), pk);
				  String finalparam = Base64.encode(rawparam).replace(" ", "");
				  System.out.println(finalparam); 
				  datamap.put("param", finalparam);
				  System.out.println(datamap);
				  String datajson=Map2Json.mapToJson(datamap);
				  System.out.println(datajson);
				  String result;
				  String encryptdata=AESEncryptor.encrypt(datajson);
				  System.out.println(encryptdata);
				  String postdata =new String(encryptdata);
				  System.out.println(postdata);
				  HttpClientUtil httpClientUtil = new HttpClientUtil();
				  result=httpClientUtil.doPostViastring(baseurl+"sub/lock_reset_valid", postdata, "utf-8");	
				  System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	public static void main(String args[]){
		try {
			while(true){
				
				testviahttp();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
