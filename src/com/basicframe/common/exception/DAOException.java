package com.basicframe.common.exception;

/**
 * <p>Description: DAO异常</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class DAOException extends CoreException {

	private static final long serialVersionUID = 4412756742624607889L;
	
	/**
	 * 操作失败
	 */
	public static final String DATA_ERROR = "操作失败";

	public DAOException(String exceptionCode){
		this(exceptionCode, null);
	}
	
	public DAOException(String exceptionCode , Throwable e){
		this(exceptionCode, null, e);
	}

	public DAOException(String exceptionCode, String[] params, Throwable e) {
		super(exceptionCode, params, e);
	}
	
	/**
	 * 操作数据库失败异常
	 * @author tyj
	 * @param row
	 * @throws DAOException
	 * @date Feb 23, 2011
	 * @modify
	 */
	public static void updateCheck(int row) throws DAOException {
		if(row < 1){
			throw new DAOException(DAOException.DATA_ERROR);
		}
	}
	
}
