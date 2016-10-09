package com.d3tech.app.test;
import java.util.HashMap;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import com.d3tech.app.unit.MD5Util; 
import com.d3tech.app.unit.AESEncryptor;
import com.d3tech.app.unit.Map2Json;

public class EncryptYihuo {
public static void main(String[]args) {
	MD5Util temp=new MD5Util();
	HashMap datamap=new HashMap();
    String password="123456789";
    String md5passwd=temp.MD5(password);
    datamap.put("phone","15157111090");
    datamap.put("passwd",md5passwd);
   datamap.put("serial", "TXAA16BAA0200017");
   datamap.put("share_phone", "18767191571");
   datamap.put("uuid", "")
   String datajson=Map2Json.mapToJson(datamap);
   System.out.println(datajson);
   
 
}
}
