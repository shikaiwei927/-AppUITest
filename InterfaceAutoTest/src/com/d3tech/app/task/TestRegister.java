package com.h3tech.app.task;


import org.testng.annotations.Test;

import com.h3tech.app.constants.TestAppConstants;
import com.h3tech.app.unit.AESEncryptor;
import com.h3tech.app.unit.ExcelDataProvider;
import com.h3tech.app.unit.HttpRequest;
import com.h3tech.app.unit.Map2Json;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestRegister {
	
	  @Test(dataProvider = "dp")
	  public void TestLogin(Map<String,String> data) {
		  
		  String datajson=Map2Json.mapToJson(data);
		  System.out.println(datajson);
		  String encryptdata=AESEncryptor.encryptLocal(datajson);
		  System.out.println(encryptdata);
		  String postdata=new String(encryptdata);
		  System.out.println(postdata);
		  HttpRequest.sendPost("http://192.168.1.1:8080/WebServer/user/register", postdata);
	  }

	  @DataProvider(name = "dp")
	  public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
	      return new ExcelDataProvider("app/register",method.getName());
	  }
	  
	  @BeforeClass
	  public void beforeClass() {
		  
	  }

	  @AfterClass(alwaysRun = true)
	  public void afterClass() {
	  }

	  @BeforeSuite
	  public void beforeSuite() {
	  }

	  @AfterSuite
	  public void afterSuite() {
	  }
	

}
