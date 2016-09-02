package com.ws.client;

import com.ws.model.*;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import javax.xml.namespace.QName;


public class TestMain {

	public static void main(String args[]) throws AxisFault {
		
		// getUserInfo();
		 
		//2.1	订购关系查询接口
		//getOrderInfo();
		// 业务退订（单个）
		// unorderSingleService();

		// 2.3 业务退定（批量）接口
		// batchUnorderService();

		// 2.4 用户清单查询接口
		//getUserDets();

		// 2.5 消费限额修改接口
		//modLimitation();

		// 2.6 童锁密码修改接口
		changeAccountPwd();

	}


	public static void getOrderInfo() throws AxisFault {

		/*GetOrderInfoReq req = new GetOrderInfoReq();
		req.setSPID("99999999");
		req.setTimeStamp("20150112090202"); 
		req.setTransactionID("1221111");
		req.setUserID("sunbb");
		req.setUserIDType(0);
		req.setUserToken("");*/

        //WSBaseServiceImpl<GetOrderInfoRsp> service = new WSBaseServiceImpl<>();
       // GetOrderInfoRsp rsp = service.getResult(req,GetOrderInfoRsp.class, "http://124.232.135.227:8297/services/SPSysInterface",
		//       "http://ui.server.spsys.vas.soap.interfaces.lcsmp.linkage.com", "getOrderInfo");
       /* WSOrderInfoServiceImpl wsOrderInfoService = new WSOrderInfoServiceImpl();
        try {
            GetOrderInfoRsp getOrderInfoReturn = wsOrderInfoService.queryOrderInfo("99999999", "sunbb", 0, "");
            System.out.println(getOrderInfoReturn.toString());
            System.out.println("---" + wsOrderInfoService.queryOrderInfo("99999999","sunbb",0,"").toString());
			//  System.out.println("---" + wsOrderInfoService.queryOrderInfo("99999999","ztezte",0,"").toString());
			// System.out.println("end");
		} catch (Exception e) {
            e.printStackTrace();
        }*/


        GetOrderInfoReq req = new GetOrderInfoReq();
		req.setSPID("99999999");
		req.setTimeStamp("20150112090202");
		req.setTransactionID("1221111");
		req.setUserID("ztezte");
		req.setUserIDType(0);
		req.setUserToken("");
        // 使用RPC方式调用WebService
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();

        // WebService的URL
        EndpointReference targetEPR = new EndpointReference(
                "http://124.232.135.227:8297/services/SPSysInterface");
        options.setTo(targetEPR);
        // 方法的参数值
        Object[] opAddEntryArgs = new Object[] { req };

        // 返回值的数据类型的Class对象
        Class<?>[] classes = new Class[] {GetOrderInfoRsp.class};
        // 指定要调用的方法及WSDL文件的命名空间
        QName opAddEntry = new QName(
                "http://ui.server.spsys.vas.soap.interfaces.lcsmp.linkage.com",
                "getOrderInfo");
        // 返回值
        GetOrderInfoRsp rsp = (GetOrderInfoRsp)serviceClient
                .invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];

        System.out.println(rsp.toString());

    }

	private static void changeAccountPwd() throws AxisFault {

		AccountPwdChangReq req = new AccountPwdChangReq();
		req.setTimeStamp("20150112090202");
		req.setTransactionID("11232311111");
		String userToken = "06146643958132189750030803151436";
		req.setUserToken(userToken);
		req.setUserID("0615002");
		req.setNewPassword("1111");
		req.setPassword("3457");

		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		// WebService的URL
		EndpointReference targetEPR = new EndpointReference(
				"http://124.232.135.227:8297/services/AccountPwdChangInterface");
		options.setTo(targetEPR);
		// 方法的参数值
		Object[] opAddEntryArgs = new Object[] { req };
		// 返回值的数据类型的Class对象
		Class<?>[] classes = new Class[] { AccountPwdChangRsp.class };
		// 指定要调用的方法及WSDL文件的命名空间
		QName opAddEntry = new QName(
				"http://ui.server.accountpwdchang.vas.soap.interfaces.lcsmp.linkage.com",
				"changeAccountPwd");
		// 返回值
		AccountPwdChangRsp rsp = (AccountPwdChangRsp) serviceClient
				.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];

		System.out.println("---" + rsp.toString());
		System.out.println("---" + rsp.getDescription());
	}

	private static void modLimitation() throws AxisFault {
		LimitationModReq req = new LimitationModReq();
		req.setTimeStamp("20150112090202");
		req.setTransactionID("11112222");
		req.setUserToken("");
		req.setUserID("ztezte");
		req.setLimitation("200");

		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		// WebService的URL
		EndpointReference targetEPR = new EndpointReference(
				"http://124.232.135.227:8297/services/LimitationModInterface");
		options.setTo(targetEPR);
		// 方法的参数值
		Object[] opAddEntryArgs = new Object[] { req };
		// 返回值的数据类型的Class对象
		Class<?>[] classes = new Class[] { LimitationModRsp.class };
		// 指定要调用的方法及WSDL文件的命名空间
		QName opAddEntry = new QName(
				"http://ui.server.limitationmod.vas.soap.interfaces.lcsmp.linkage.com",
				"modLimitation");
		// 返回值
		LimitationModRsp rsp = (LimitationModRsp) serviceClient.invokeBlocking(
				opAddEntry, opAddEntryArgs, classes)[0];

		System.out.println("---" + rsp.toString());
		System.out.println("---" + rsp.getDescription());
	}

	// 2.4 用户清单查询接口
	private static void getUserDets() throws AxisFault {
		UserDetInfoReq req = new UserDetInfoReq();
		req.setSPID("99999999");
		req.setTimeStamp("20150112090202");
		req.setTransactionID("111113333");
		req.setUserToken("");
		req.setUserID("ztezte");
		req.setUserIDType(0);
		req.setBeginTime("20160601");
		req.setEndTime("20160630");
		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		// WebService的URL
		EndpointReference targetEPR = new EndpointReference(
				"http://124.232.135.227:8297/services/UserDetQuery");
		options.setTo(targetEPR);
		// 方法的参数值
		Object[] opAddEntryArgs = new Object[] { req };
		// 返回值的数据类型的Class对象
		Class<?>[] classes = new Class[] { UserDetInfoRsp.class };
		// 指定要调用的方法及WSDL文件的命名空间
		QName opAddEntry = new QName(
				"http://ui.server.userdetqueryapk.vas.soap.interfaces.lcsmp.linkage.com",
				"getUserDets");
		// 返回值
		UserDetInfoRsp rsp = (UserDetInfoRsp) serviceClient.invokeBlocking(
				opAddEntry, opAddEntryArgs, classes)[0];

		System.out.println("---" + rsp.toString());
		System.out.println("---" + rsp.getDescription());

	}
	// 2.3 业务退定（批量）接口
	private static void batchUnorderService() throws AxisFault {
		
		BatchUnorderServiceReq req = new BatchUnorderServiceReq();
		req.setSPID("99999999");
		req.setTimeStamp("20150112090202");
		req.setTransactionID("1111");
		req.setUserToken("");
		req.setUserID("sunbb");
		req.setUserIDType(0);
		req.setProductIDList("packageIDa1000000000000000000051");

		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		// WebService的URL
		EndpointReference targetEPR = new EndpointReference(
				"http://124.232.135.227:8297/services/BatchUnOrder");
		options.setTo(targetEPR);
		// 方法的参数值
		Object[] opAddEntryArgs = new Object[] { req };
		// 返回值的数据类型的Class对象
		Class<?>[] classes = new Class[] { BatchUnorderServiceRsp.class };
		// 指定要调用的方法及WSDL文件的命名空间
		QName opAddEntry = new QName(
				"http://ui.server.batchunorder.vas.soap.interfaces.lcsmp.linkage.com",
				"batchUnorderService");
		// 返回值
		BatchUnorderServiceRsp rsp = (BatchUnorderServiceRsp) serviceClient
				.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];

		System.out.println("---" + rsp.getResult());
		System.out.println("---" + rsp.getDescription());
	}

	private static void unorderSingleService() throws AxisFault {
		UnorderSingleServiceReq req = new UnorderSingleServiceReq();
		req.setSPID("spa00033");
		req.setTimeStamp("20150112090202");
		req.setTransactionID("111113333");
		req.setUserToken("");
		req.setProductID("packageIDa1000000000000000000051"); 
		req.setUserID("sunbb");
		req.setUserIDType(0);
		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		// WebService的URL
		EndpointReference targetEPR = new EndpointReference(
				"http://124.232.135.227:8297/services/SPSysInterface");
		options.setTo(targetEPR);
		// 方法的参数值
		Object[] opAddEntryArgs = new Object[] { req };
		// 返回值的数据类型的Class对象
		Class<?>[] classes = new Class[] { UnorderSingleServiceRsp.class };
		// 指定要调用的方法及WSDL文件的命名空间
		QName opAddEntry = new QName(
				"http://ui.server.spsys.vas.soap.interfaces.lcsmp.linkage.com",
				"unorderSingleService");
		// 返回值
		UnorderSingleServiceRsp rsp = (UnorderSingleServiceRsp) serviceClient
				.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];

		System.out.println("---" + rsp.getResult());
		System.out.println("---" + rsp.getDescription());
		System.out.println("---" + rsp.toString());
	}

	private static void getUserInfo() throws AxisFault {
		GetUserInfoReq req = new GetUserInfoReq();
		req.setSPID("spa00033");
		req.setTimeStamp("20150112090202");
		req.setTransactionID("1234");
		req.setUserToken("aaa");
		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		// WebService的URL
		EndpointReference targetEPR = new EndpointReference(
				"http://124.232.135.227:8297/services/SPSysInterface");
		options.setTo(targetEPR);
		// 方法的参数值
		Object[] opAddEntryArgs = new Object[] { req };
		// 返回值的数据类型的Class对象
		Class<?>[] classes = new Class[] { GetUserInfoRsp.class };
		// 指定要调用的方法及WSDL文件的命名空间
		QName opAddEntry = new QName(
				"http://ui.server.spsys.vas.soap.interfaces.lcsmp.linkage.com",
				"getUserInfo");
		// 返回值
		GetUserInfoRsp rsp = (GetUserInfoRsp) serviceClient.invokeBlocking(
				opAddEntry, opAddEntryArgs, classes)[0];

		System.out.println("---" + rsp.getResult());
		System.out.println("---" + rsp.getDescription());
	}

}
