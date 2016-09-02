package com.basicframe.common.exception;

/**
 * <p>Description: 系统action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class CoreException extends AbstractException {
	
	private static final long serialVersionUID = 1L;


	public CoreException(String exceptionCode){
		this(exceptionCode, null);
	}
	
	public CoreException(String exceptionCode , Throwable e){
		this(exceptionCode, null, e);
	}

	public CoreException(String exceptionCode, String[] params, Throwable e) {
		super(exceptionCode, params, e);
	}
	
	public static void handleException(Exception e) throws CoreException {
		if(e instanceof CoreException){
			CoreException ce = (CoreException)e; 
			CoreException be = new CoreException(ce.getExceptionCode(), e);
			throw be;
		}else{
			e.printStackTrace();
		}
		throw new CoreException(CoreException.SYSTEM_ERROR, e);
	}
	
}
