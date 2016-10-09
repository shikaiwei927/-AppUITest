package com.d3tech.app.test;
import java.util.HashMap;
import java.util.Map;
import com.d3tech.app.unit.MD5Util;
import com.d3tech.app.unit.AESEncryptor;
import com.d3tech.app.unit.Map2Json;

public class BindGateway {
	
	public static void main(String args[]) {
		
		MD5Util temp=new MD5Util();
		HashMap  datamap=new HashMap();
		String password="12345678";
		String md5passwd=temp.MD5(password);
		datamap.put("phone", "15157111090");
		datamap.put("passwd","md5passwd" );
		datamap.put("serial", "TXAA16BAA0200017");
		String datajson=Map2Json.mapToJson(datamap);
		System.out.println(datajson);
		String encryptdata=AESEncryptor.encryptLocal(datajson);
		System.out.println(encryptdata);
		
	}

}
