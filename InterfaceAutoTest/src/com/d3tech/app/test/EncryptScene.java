package com.d3tech.app.test;
import java.awt.List;
import java.io.ObjectInputStream.GetField;
import java.net.DatagramPacket;
import java.util.HashMap;
import javax.naming.spi.DirStateFactory.Result;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;
import com.d3tech.app.unit.MD5Util;
import com.d3tech.app.unit.AESEncryptor;
import com.d3tech.app.unit.HttpRequest;
import com.d3tech.app.unit.Map2Json;
import com.google.gson.Gson;
public class EncryptScene {
	
	//创建场景
	public static void CreateScene() {
		MD5Util temp=new MD5Util(); 
		HashMap<String , String>	datamap=new HashMap<String ,String>();
		
	  String password=new String("123456789"); 
	  String md5passwd=temp.MD5(password);
	  datamap.put("phone","15157111090");
	  datamap.put("password",md5passwd);
	  datamap.put("serial","TXAA16BAA0200022");
	  datamap.put("name","智能场景");
	  datamap.put("type","manually");
	  datamap.put("starttime","2016-05-28 18:16:40");
	  datamap.put("details","{\"deviceType\":0,\"name\":\"韩国测试请勿升级05\",\"operate\":\"closebreathLed\",\"param\":\"\",\"uuid\":\"TXAA16BAA0200022\"}");
	  String datajson=Map2Json.mapToJson(datamap);
      System.out.println(datajson);
      String encryptdata=AESEncryptor.encrypt(datajson);
      System.out.println(encryptdata);
      String postdata =new String(encryptdata);
      String  result=HttpRequest.sendPost("http://121.41.50.221:8080/WebServer/sl/create_scene", postdata);
	  System.out.println(result);
	}
}
