package com;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Config {


    /**
     * 建议使用 JFinal 手册推荐的方式启动项目
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        //JFinal.start("C:\\Users\\Administrator\\Desktop\\onlyWeb\\webAppStoreTV\\AppStoreTV\\WebRoot", 80, "/AppStoreTV", 5); //http://127.0.0.1/demo/index.jsp

    }

    /**
     * 上传到的根路径  目前为 AppStoreTV这个项目下
     * /home/appStore/tomcat6035/webapps/AppStoreTV/upload
     */
    //正式平台服务器文件上传路径
  public static final String UPLOAD_BASE_PATH = "/home/appStore/tomcat6035/webapps/AppStoreTV";
    //测试平台服务器文件上传路径
    //public static final String UPLOAD_BASE_PATH = "/home/changs/tomcat6045/webapps/image";
    //正式地址
    public static final String BASE_WS_URL = "http://222.246.132.231:8297/services";

    // webservice外网测试ip
    // public static final String BASE_WS_URL = "http://124.232.135.227:8297/services";
    //webservice内网测试ip
    // public static final String BASE_WS_URL = "http://192.168.141.10:8297/services";
    //2.1	订购关系查询接口
    public static final String GETORDERINFO_URL = BASE_WS_URL + "/SPSysInterface";
    public static final String GETORDERINFO_SPACE = "http://ui.server.spsys.vas.soap.interfaces.lcsmp.linkage.com";
    public static final String GETORDERINFO_METHOD = "getOrderInfo";

    // 业务退订（单个）
    public static final String UNORDERSINGLE_URL = BASE_WS_URL + "/SPSysInterface";
    public static final String UNORDERSINGLE_SPACE = "http://ui.server.spsys.vas.soap.interfaces.lcsmp.linkage.com";
    public static final String UNORDERSINGLE_METHOD = "unorderSingleService";

    // 2.3 业务退定（批量）接口
    public static final String BATCHUNORDER_URL = BASE_WS_URL + "/BatchUnOrder";
    public static final String BATCHUNORDER_SPACE = "http://ui.server.batchunorder.vas.soap.interfaces.lcsmp.linkage.com";
    public static final String BATCHUNORDER_METHOD = "batchUnorderService";

    // 2.4 用户清单查询接口
    public static final String USERDET_URL = BASE_WS_URL + "/UserDetQuery";
    public static final String USERDET_SPACE = "http://ui.server.userdetqueryapk.vas.soap.interfaces.lcsmp.linkage.com";
    public static final String USERDET_METHOD = "getUserDets";


    // 2.6 童锁密码修改接口
    public static final String CHANGEACCOUNTPWD_URL = BASE_WS_URL + "/AccountPwdChangInterface";
    public static final String CHANGEACCOUNTPWD_SPACE = "http://ui.server.accountpwdchang.vas.soap.interfaces.lcsmp.linkage.com";
    public static final String CHANGEACCOUNTPWD_METHOD = "changeAccountPwd";


    //芒果获取sing 的key
    public static final String SGIN_KEY = "fd167af75f8fffde768538e3c7fd3c95";
    //芒果的接口地址
    public static final String MG_URL = "http://113.247.251.136:28080/notify/orther_notify";


    //spId
    public static final String SP_ID = "99999999";
    /*用户帐号类型
        0：普通用户（需要绑定机顶盒）
        1：测试用户(无需绑定机顶盒)
        */
    public static final String USERID_TYPE = "0";


}
