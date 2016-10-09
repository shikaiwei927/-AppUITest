package com.d3tech.app.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import com.d3tech.app.unit.*;
public class EncryptBase {
	// public static String baseurlhttp1="http://121.41.50.221:8080/WebServer/";
	// public static String baseurlhttp2="http://192.168.0.139:8080/";
	public static String baseurl="https://120.55.182.40:8443/";
	public static String baseurlhttp="http://120.55.182.40:8080/";
	private static Logger logger = Logger.getLogger(EncryptErhuo.class.getName());
	
	public static void baseTestViaxls(String xlsname, String sheetname, String url, ArrayList<String> testparamlist) {
		Iterator<Object[]> testdatatemp = new ExcelDataProvider(xlsname, sheetname);  //迭代器
		while (testdatatemp.hasNext()) {
			@SuppressWarnings("unchecked") 
			Map<String, String> datamap = (Map<String, String>) testdatatemp.next()[0]; //运用迭代器里的方法

			logger.info("测试数据xls:"+datamap); 
			if (datamap.get("是否执行").equals("Y")) {
			 // 声明一个Map
				Map<String, String> postdata = new HashMap<String, String>();
				// 遍历map，剔除掉不需要传给服务器的参数
//				　如果此映射将一个或多个键映射到指定值，则返回 true。
//				　　Set<Map.Entry<K,V>> entrySet()
		

					for(Map.Entry<String, String> entry:datamap.entrySet()){
					// 拿到数据map的key值
					try {
						String testdatakey = entry.getKey();
						if (testdatakey.equals("编号") || testdatakey.equals("是否执行") || testdatakey.equals("执行结果")) { // 逻辑或运算
							;
						} 
						else {
							boolean flag = true;
							
							for (String testparamkey : testparamlist) {
								if (testparamkey.equals(testdatakey)) {
									flag = false;
								}
							}
							if (flag) {
								if (entry.getKey().equals("password")) {
								
									String md5passwd = 	MD5util.MD5(entry.getValue());
									postdata.put("password", md5passwd);
								} else {

									postdata.put(entry.getKey(), entry.getValue());
								}
							}
						}
					} catch (NullPointerException e) {
					
						continue;
					}

				}

				// System.out.println(postdata);
				String datajson = Map2Json.mapToJson(postdata);
				// System.out.println(datajson);
				
				String encryptdata = AESEncryptor.encrypt(datajson);
				// System.out.println(encryptdata);
				String resultdata;
				resultdata = HttpRequest.sendPost(url, encryptdata);
				System.out.println(resultdata);
				String result=AESEncryptor.decryptLocal(resultdata);
				System.out.println(result);
				Map<String, String> resultmap = Map2Json.jsonTomap(result);
				boolean flag = false;
				for (String testparam : testparamlist) {
					try {
						if ((resultmap.get(testparam)).equals(datamap.get(testparam))) {
							flag = true;
						} else {
							flag = false;
							break;
						}

					} catch (NullPointerException e) {
					
						continue;
					}
				}

				if (!flag) {
					logger.info("test failed,input data is: "+datamap);
					logger.info("结果是："+result);
					ExcelModify modifyxls = new ExcelModify();
					modifyxls.modifyxlsvianame(Integer.parseInt(datamap.get("编号")), xlsname, sheetname, "执行结果",
							"failed");
				} else {
					logger.info("pass");
					ExcelModify modifyxls = new ExcelModify();
					modifyxls.modifyxlsvianame(Integer.parseInt(datamap.get("编号")), xlsname, sheetname, "执行结果", "pass");
				}
			}
		}

	}

	public static void baseTest(String url, Map<String, String> datamap) {
		String datajson = Map2Json.mapToJson(datamap);
		System.out.println(datajson);
		String result;
		String encryptdata = AESEncryptor.encrypt(datajson);
		System.out.println(encryptdata);
		result = HttpRequest.sendPost(url, encryptdata);
		Map<String, String> resultmap = Map2Json.jsonTomap(result);
		System.out.println(resultmap);
	}

	// 2.1注册
	public static void register() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/register", "register", baseurl+ "user/register", testparamlist);
	}
			//https
	public static void login() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("name");
		testparamlist.add("image");
		baseTestViaxls("app/login", "login", baseurlhttp + "user/login", testparamlist);
	}

	
	//2.7绑定网关
	public static void bindgateway() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/bindgateway", "bindgateway", baseurlhttp + "user/bind_gateway", testparamlist);
	}

	public static void sharegateway() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/sharegateway", "sharegateway", baseurlhttp + "user/share_gateway", testparamlist);
	}

	public static void deletesharegateway() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/deletesharegateway", "deletesharegateway", baseurlhttp + "user/delete_gateway_share",
				testparamlist);
	}

	public static void unbindgateway() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/unbindgateway", "unbindgateway", baseurlhttp + "user/unbind_gateway", testparamlist);
	}

	
	//2.11网关列表（首页）
	public static void gatewaylist() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/gatewaylist", "gatewaylist", baseurlhttp + "user/gateway_list", testparamlist);
	}
	//2.12网关对应账户
	public static void gatewayusers() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/gatewayusers", "gatewayusers", baseurlhttp + "user/gateway_users", testparamlist);
	}
	//2.13获取对应账户日志
	public static void getuserlog() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/getuserlog", "getuserlog", baseurlhttp + "user/get_user_log", testparamlist);
	}
	
	//2.14获取用户的昵称和头像地址
	public static void getuserinfo() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/getuserinfo", "getuserinfo", baseurlhttp + "user/get_user_info", testparamlist);
	}
	
	//2.15获取用户所有子设备tag
	public static void getsubtag() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/getsubtag", "getsubtag", baseurlhttp + "user/get_sub_tag", testparamlist);
	}
	
	//2.16删除用户日志
	public static void userlogdelete() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/userlogdelete", "userlogdelete", baseurlhttp + "user/user_log_delete", testparamlist);
	}
	// 3.6升级网关
	public static void updategateway() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
//		testparamlist.add("result");
//		testparamlist.add("code");
//		testparamlist.add("desc");
		baseTestViaxls("app/updategateway", "updategateway", baseurlhttp + "gw/update_gateway", testparamlist);
	}

	// 3.8RSA公钥
	public static void gatewayrsa() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
//		testparamlist.add("rsa");
		baseTestViaxls("app/gatewayrsa", "gatewayrsa", baseurlhttp + "gw/gateway_rsa", testparamlist);
	}

	// 4.1智能设备下发指令
	public static void sendoperate() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("result");
		testparamlist.add("code");
		testparamlist.add("desc");
		baseTestViaxls("app/sendoperate", "sendoperate", baseurlhttp + "sub/send_operate", testparamlist);
	}

	// 3.1修改智能网关名称
	public static void modifygatewayname() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/modifygatewayname", "modifygatewayname", baseurlhttp + "gw/modify_gateway_name", testparamlist);
	}

	// 3.2获取网关设备状态 (待测）
	public static void gatewaystatus() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
//		testparamlist.add("gateway");
		baseTestViaxls("app/gatewaystatus", "gatewaystatus", baseurlhttp+"gw/gateway_status", testparamlist);
	}

	// 3.3获取子设备列表
	public static void gatewaydevices() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("isLastVersion");
		testparamlist.add("devices");
		testparamlist.add("uuid");
		baseTestViaxls("app/gatewaydevices", "gatewaydevices", baseurlhttp + "gw/gateway_devices", testparamlist);
	}

	// 3.4添加设备
	public static void searchdevice() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("result");
		testparamlist.add("code");
		testparamlist.add("desc");
		baseTestViaxls("app/searchdevice", "searchdevice", baseurlhttp + "gw/search_device", testparamlist);
	}

	// 3.5删除子设备
	public static void deletedevice() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
//		testparamlist.add("result");
//		testparamlist.add("code");
//		testparamlist.add("desc");
		baseTestViaxls("app/deletedevice", "deletedevice", baseurlhttp + "gw/delet_device", testparamlist);
	}

	// 3.6确认添加子设备是否完成
	public static void issearchcomplete() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("result");
		testparamlist.add("code");
		testparamlist.add("desc");
		baseTestViaxls("app/issearchcomplete", "issearchcomplete", baseurlhttp + "gw/is_search_complete", testparamlist);
	}
	
	//3.9获取网关升级状态
	public static void upgradestatus() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/upgradestatus", "upgradestatus", baseurlhttp + "gw/upgrade_status", testparamlist);
	}
	// 4.2获取子设备日志(未测）
	public static void devicelogtoday() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
//		testparamlist.add("result");
//		testparamlist.add("logs");

	}

	// 4.7.4修改智能门锁长期密码备注
	public static void lockmodifyvalidname() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/lockmodifyvalidname", "lockmodifyvalidname", baseurlhttp + "gw/lock_modify_valid_name",
				testparamlist);
	}

	// 4.7.5长期密码增加(https)
	public static void lockcreatevalid() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/lockcreatevalid", "lockcreatevalid", baseurlhttp + "gw/lock_create_valid", testparamlist);
	}

	// 4.7.6长期密码重置(https)
	public static void lockresetvalids() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/lockresetvalid", "lockresetvalid", baseurlhttp + "gw/lock_reset_valids", testparamlist);
	}

	// 4.7.7长期密码删除(https)
	public static void lockdeletevalid() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/lockdeletevalid", "lockdeletevalid", baseurlhttp + "gw/lock_delete_valid", testparamlist);
	}
	// 4.7.8短期密码获取

	public static void lockgettemp() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("id");
		testparamlist.add("name");
		testparamlist.add("mode");
		// testparamlist.add("start");
		// testparamlist.add("end");
		baseTestViaxls("app/lockgettemp", "lockgettemp", baseurlhttp + "sub/lock_get_temp", testparamlist);
	}

	// 4.7.9短期密码历史记录
	public static void lockgettemphis() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("temps");
		baseTestViaxls("app/lockgettemphis", "lockgettemphis", baseurlhttp + "sub/lock_get_temp_his", testparamlist);
	}

	// 4.7.10临时密码创建Https
	public static void lockcreatetemp() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");

		baseTestViaxls("app/lockcreatetemp", "lockcreatetemp", baseurlhttp + "gw/lock_create_temp", testparamlist);
	}

	// 4.7.11删除有效临时密码https
	public static void lockdeletetemp() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");

		baseTestViaxls("app/lockdeletetemp", "lockdeletetemp", baseurlhttp + "sub/lock_delete_temp", testparamlist);
	}

	// 4.7.12短期密码重置https
	public static void lockresettemp() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");

		baseTestViaxls("app/lockresettemp", "lockresettemp", baseurlhttp + "sub/lock_reset_temp", testparamlist);
	}

	// 4.7.13短期密码失效临时(密码历史记录)删除
	public static void lockdeletetemphis() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");

		baseTestViaxls("app/lockdeletetemphis", "lockdeletetemphis", baseurlhttp + "sub/lock_delete_temp_his",
				testparamlist);
	}

	// 4.7.14获取指纹列表
	public static void lockgetfingerprint() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		// testparamlist.add("fingerprints")
		testparamlist.add("keynum");
		baseTestViaxls("app/lockgetfingerprint", "lockgetfingerprint", baseurlhttp + "sub/lock_get_fingerprint",
				testparamlist);
	}

	// 4.7.16修改指纹信息
	public static void modifyfingerprintlabel() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");

		baseTestViaxls("app/modifyfingerprintlabel", "modifyfingerprintlabel",baseurlhttp + "gw/lock_modify_fingerprint_label", testparamlist);

	}

	// 4.10.6 修改智能开关按钮名称(不用改了,下面对的）
	public static void switchmodifyname() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/switchmodifyname", "switchmodifyname", baseurlhttp + "sub/switch_modify_sub_name",testparamlist);
	}

	// 4.10.1获取智能开关当前信息
	public static void switchstatus() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/switchstatus", "switchstatus", baseurlhttp + "sub/switch_status", testparamlist);
	}

	// 4.10.3
	// 4.10.6 修改智能开关名称
	public static void switchmodifyName() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/switchmodifyName1", "switchmodifyName1", baseurlhttp + "sub/switch_modify_name", testparamlist);
	}

	// 4.10.3设置定时
	public static void switchsettiming() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/switchsettiming", "switchsettiming", baseurlhttp + "sub/switch_set_timing", testparamlist);
	}

	// 4.10.4关闭定时
	public static void switchofftiming() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/switchofftiming", "switchofftiming", baseurlhttp + "sub/switch_off_timing", testparamlist);
	}

	// 4.10.5控制智能开关制定按钮
	public static void switchcontrol() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/switchcontrol", "switchcontrol", baseurlhttp + "sub/switch_control", testparamlist);
	}

	// 4.7.18增加锁的标签
	public static void createpasswordlabel() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/createpasswordlabel", "createpasswordlabel", baseurlhttp + "sub/lock_create_password_label",
				testparamlist);
	}

	// 4.7.19删除标签
	public static void deletepasswordlabel() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("id");
		baseTestViaxls("app/deletepasswordlabel", "deletepasswordlabel", baseurlhttp + "sub/lock_delete_password_label",
				testparamlist);
	}

	// 4.7.20查询标签
	public static void getpasswordlabel() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		testparamlist.add("labels");
		baseTestViaxls("app/getpasswordlabel", "getpasswordlabel", baseurlhttp + "sub/lock_get_password_label",
				testparamlist);
	}

	// 5.1.1创建联动https
	public static void createlink() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/createlink", "createlink", baseurlhttp + "sl/create_link", testparamlist);
	}
			//5.1.2修改联动https
	public static void modifylink() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/modifylink", "modifylink", baseurlhttp + "sl/modify_link", testparamlist);
	}
			//5.1.3删除联动HTTPS
	public static void deletelink() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/deletelink", "deletelink", baseurlhttp + "sl/delete_link", testparamlist);
	}
	
				//5.1.4打开关闭联动https
	public static void enablelink() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/enablelink", "enablelink", baseurlhttp + "sl/enable_link", testparamlist);
	}
			//5.1.5  获取单个联动
	public static void getlink() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/getlink", "getlink", baseurlhttp + "sl/get_link", testparamlist);
	}
	// 5.1.6获取联动列表
	public static void getlinklist() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		// testparamlist.add("allLinks");
		baseTestViaxls("app/getlinklist", "getlinklist", baseurlhttp + "sl/get_link_list", testparamlist);
	}
			//5.1.7获取联动日志
	public static void getlinklog() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		
		baseTestViaxls("app/getlinklog", "getlinklog", baseurlhttp + "sl/get_link_log", testparamlist);
	}
		//5.1.8删除联动日志
	public static void deletelinklog() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");

		baseTestViaxls("app/deletelinklog", "deletelinklog", baseurlhttp + "sl/delete_link_log", testparamlist);
	}
			//5.1.9获取联动中冲突的开关
	public static void linkcrashswitches() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
	
		baseTestViaxls("app/linkcrashswitches", "linkcrashswitches", baseurlhttp + "sl/get_link_crash_switches", testparamlist);
	}
	
	//5.2.1创建场景https
		public static void createscene() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/createscene", "createscene", baseurlhttp + "sl/create_scene", testparamlist);
		}
		
		//5.2.2修改场景https
		public static void modifyscene() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/modifyscene", "modifyscene", baseurlhttp + "sl/modify_scene", testparamlist);
		}
		//5.2.3删除场景https
		public static void deletescene() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/deletescene", "deletescene", baseurlhttp + "sl/delete_scene", testparamlist);
		}
		//5.2.4打开关闭场景
		public static void enablescene() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/enablescene", "enablescene", baseurlhttp + "sl/enable_scene", testparamlist);
		}
			//5.2.5获取场景
		public static void getscene() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/getscene", "getscene", baseurlhttp + "sl/get_scene", testparamlist);
		}
		//5.2.6获取场景列表
		public static void getscenelist() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/getscenelist", "getscenelist", baseurlhttp + "sl/get_scene_list", testparamlist);
		}
		//5.2.7运行场景https
		public static void runscene() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/runscene", "runscene", baseurlhttp + "sl/run_scene", testparamlist);
		}
		//5.2.8获取场景日志
		public static void getscenelog() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/getscenelog", "getscenelog", baseurlhttp + "sl/getscenelog", testparamlist);
		}
		//5.2.9删除场景日志
		public static void deletescenelog() {
			ArrayList<String> testparamlist = new ArrayList<String>();
			testparamlist.add("reason");
			testparamlist.add("state");
			baseTestViaxls("app/deletescenelog", "deletescenelog", baseurlhttp + "sl/deletescenelog", testparamlist);
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		//7.1验证码
	public static void appsecurity() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		
		baseTestViaxls("app/appsecurity", "appsecurity", baseurlhttp + "app/security", testparamlist);
	}
		
	
		//7.2上传图片
	public static void appupload () {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		// testparamlist.add("allLinks");
		baseTestViaxls("app/appupload ", "appupload ", baseurlhttp + "app/upload", testparamlist);
	}
			//7.3获取andriod版本信息
	public static void appversion () {
		ArrayList<String> testparamlist = new ArrayList<String>();
//		testparamlist.add("app_version_app");
		testparamlist.add("app_version_code");
		// testparamlist.add("app_version_info");
		baseTestViaxls("app/appversion", "appversion", baseurlhttp + "app/newest_version_app", testparamlist);
	}
	//7.4获取网关固件版本信息
	public static void gatewayversion () {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("gateway_version_name");
		testparamlist.add("gagteway_version_code");
		 testparamlist.add("gateway_version_info");
		 testparamlist.add("gateway_address");
		baseTestViaxls("app/gatewayversion ", "gatewayversion ", baseurlhttp + "app/newest_version_gateway", testparamlist);
	}
	//7.5 ios版本信息
	public static void appversionios () {
		ArrayList<String> testparamlist = new ArrayList<String>();
//		testparamlist.add("app_version_app");
		testparamlist.add("ios_version_code");
		// testparamlist.add("app_version_info");
		baseTestViaxls("app/appversionios ", "appversionios ", baseurlhttp + "app/newest_version_ios", testparamlist);
	}
	//7.6提交反馈信息
	public void sendfeedback() {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("reason");
		testparamlist.add("state");
		baseTestViaxls("app/appversionios ", "appversionios ", baseurlhttp + "app/newest_version_ios", testparamlist);
	}
	
	
	
	
	
	
	
	
		//7.7发送短信到手机
	public static void sendmessagetophone () {
		ArrayList<String> testparamlist = new ArrayList<String>();
		testparamlist.add("state");
		testparamlist.add("reason");

		baseTestViaxls("app/sendmessagetophone", "sendmessagetophone", baseurlhttp + "app/send_message_to_phone ", testparamlist);
	}
	
	public static void logout() {
		MD5util temp = new MD5util();
		HashMap<String, String> datamap = new HashMap<String, String>();
		String password = "12345678";
		String md5passwd = temp.MD5(password);
		datamap.put("phone", "18767191571");
		datamap.put("password", md5passwd);
		System.out.println(datamap);
		String datajson = Map2Json.mapToJson(datamap);
//		System.out.println(datajson);
		String result;
		String encryptdata = AESEncryptor.encrypt(datajson);
//		System.out.println(encryptdata);
		String postdata = new String(encryptdata);
		System.out.println(postdata);
		result = HttpRequest.sendPost(baseurlhttp + "user/login", postdata);
//		System.out.println(result);

	}

		
	public static void main(String args[]) {
//									register();
//									 login();
//							      bindgateway();
//					             sharegateway();
								// logout();
				// deletesharegateway();
				// updategateway();
				// gatewayrsa();
//				 modifygatewayname(); pass
//				 gatewaystatus();
//				 gatewaydevices();
//					 searchdevice();
				// register();
				// switchmodifyname();
				// switchofftiming();
				// lockgettemp();
					//		lockgetfingerprint();
				//		appsecurity();
					//	createlink();
						//sendmessagetophone();
				//		 lockdeletetemp();
		
						// modifylink();
			//			createlink();
						//getlink();
//	                      getlinklist();
//		getlinklog();
//		deletelinklog();
//		createscene();
//		modifyscene();
//		getscene();
//		getscenelist();
//		deletescene();
//		deletescenelog();
//		enablescene();
//		getscene();
//		appversion();
//		logout();
//		linkcrashswitches();
//		updategateway();
//		gatewayrsa();
//		gatewaylist();
//		gatewayusers(); pass
//		getuserlog();pass
//		getuserinfo(); pass
//		getsubtag(); pass
//		userlogdelete();
//		deletedevice();
//		upgradestatus();
		devicelogtoday();
	}

}
