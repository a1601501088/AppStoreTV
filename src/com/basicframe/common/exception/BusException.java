package com.basicframe.common.exception;

/**
 * <p>Description:业务异常</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class BusException extends CoreException {

	private static final long serialVersionUID = -4730637755893461497L;

	public static final String ADD_FAILURE = "添加失败!";
	public static final String EDIT_FAILURE = "修改失败!";
	public static final String DELETE_FAILURE= "删除失败!";
	public static final String EDIT_STATUS_FAILURE = "修改状态失败";
	public static final String DELETE_DATA_IS_NULL = "没有选择要删除的信息!";
	
	
	//日志
	public static final String LOG_LOGIN_LOGIN_SUCCESS = "登录成功";
	public static final String LOG_LOGIN_LOGIN_ERROR = "用户名或密码不正确";
	public static final String LOG_LOGIN_LOGIN_STATUS_ERROR = "该管理员己被冻结";
	
	//管理员
	public static final String ADMIN_LOGIN_ERROR = "登录失败";
	public static final String ADMIN_LOGIN_STATUS_ERROR = "管理员己被冻结!";
	public static final String ADMIN_NOT_EXISTS = "管理员不存在!";
	public static final String LOGIN_NAME_IS_EXISTS = "用户名己存在,请更换!";
	public static final String REAL_NAME_IS_EXISTS = "姓名己存在,请更换姓名!";
	
	//附件
	public static final String ATTACHMENT_NOT_EXISTS = "附件不存在!";
	
	//菜单
	public static final String MENU_NOT_EXISTS = "菜单不存在!";
	public static final String MENU_NAME_IS_EXISTS = "菜单名己存在!";
	public static final String MENU_WAS_QUOTED = "该菜单存在子模块!";
	
	//角色
	public static final String ROLE_NOT_EXISTS = "角色不存在!";
	public static final String ROLE_NAME_IS_EXISTS = "角色名己存在,请更换!";
	public static final String SYS_ROLE_NOT_OPERATOR = "不能操作系统内置角色!";
	
	//角色权限关联
	public static final String ROLE_RALATION_NOT_EXISTS = "用户角色关联不存在!";
	public static final String ROLE_PERMISSIONS_NOT_EXISTS = "角色权限关联不存在!";
	
	//权限
	public static final String PERMISSIONS_NOT_EXISTS = "权限不存在!";
	public static final String PERMISSIONS_NOT_ADD = "该目录下不能增加权限!";
	public static final String PERNAME_NOT_NULL = "权限名不能为空!";
	public static final String PERACTION_NOT_NULL = "权限动作不能为空!";
	
	//栏目
	public static final String CHANNEL_NOT_EXISTS = "栏目不存在!";
	public static final String CHANNEL_WAS_QUOTED = "该栏目存在子栏目,请先删除子栏目再进行操作!";
	public static final String CHANNEL_NAME_IS_EXISTS = "栏目名己存在,请更换!";
	public static final String CHANNEL_EXIST_NEWS = "栏目下存在新闻,请删除该栏目下所有新闻再进行操作!";
	
	//新闻
	public static final String NEWS_DATA_NULL = "新闻数据为空!";
	public static final String NEWS_NOT_SELECT_CHANNEL = "请选择栏目!";
	public static final String NEWS_CHANNEL_NOT_ADD_NEWS = "节点栏目下不能添加新闻!";
	
	//产品
	public static final String PRODUCT_CATEGORY_NOT_EXISTS = "产品类别不存在!";
	public static final String PRODUCT_CATEGORY_WAS_QUOTED = "该产品类别存在子类别!";
	public static final String PRODUCT_CATEGORY_NAME_IS_EXISTS = "产品类别名己存在,请更换!";
	public static final String PRODUCT_CATEGORY_IS_EXISTS_PRODUCT = "该类别下存在产品,请删除该类别下所有产品再进行操作!";
	public static final String PRODUCT_NOT_SELECT_CATEGORY = "请选择产品类别!";
	public static final String PRODUCT_CATEGORY_NOT_ADD_PRODUCT = "节点类别下不能添加产品!";
	public static final String PRODUCT_IMG_SIZE_ERROR = "产品图片尺寸不能小于150x150!";
	
	//区域
	public static final String REGION_NAME_IS_EXISTS = "区域名称己存在,请更换!";
	public static final String REGION_WAS_QUOTED = "请先删除下级区域，再进行操作!";
	
	
	
	
	
	
	
	public BusException(String exceptionCode) {
		this(exceptionCode,null);
	}

	public BusException(String exceptionCode, Throwable e) {
		super(exceptionCode,null, e);
	}
	
	public BusException(String[] params ,String exceptionCode) {
		super(exceptionCode,params, null);
	}
	
	/**
	 * 暂未考虑对异常进行特殊处理
	 * @param e
	 * @throws BusException
	 */
	public static void handleException(Exception e) throws BusException{
		if (e instanceof BusException){
			throw (BusException)e;
		}
		if(e instanceof CoreException){
			CoreException ce = (CoreException)e; 
			BusException be = new BusException(ce.getExceptionCode(), e);
			throw be;
		}else{
			e.printStackTrace();
		}
		throw new BusException(BusException.SYSTEM_ERROR, e);
	}
	
	
}
