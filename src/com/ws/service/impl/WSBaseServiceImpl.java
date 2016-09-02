package com.ws.service.impl;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import javax.xml.namespace.QName;

/**
 * Created by Administrator on 2016/5/24.
 */

public abstract class WSBaseServiceImpl  {


    public Object getResult(Object req, Class clazz, String wsdlUrl, String targetNamespace, String method) throws AxisFault {
        // 使用RPC方式调用WebService
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();

        // WebService的URL
        EndpointReference targetEPR = new EndpointReference(
                wsdlUrl);
        options.setTo(targetEPR);
        // 方法的参数值
        Object[] opAddEntryArgs = new Object[] { req };

        // 返回值的数据类型的Class对象
        Class<?>[] classes = new Class[] {clazz};
        // 指定要调用的方法及WSDL文件的命名空间
        QName opAddEntry = new QName(
                targetNamespace,
                method);
        // 返回值
        Object rsp = serviceClient
                .invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];

        return rsp;
    }
}
