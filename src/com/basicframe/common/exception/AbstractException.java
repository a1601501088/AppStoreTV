package com.basicframe.common.exception;

/**
 * <p>Description: 异常</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public abstract class AbstractException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8075292968018607665L;
	/**
	 * 入参错误
	 */
	public static final String PARAM_IS_ERROR = "000001";
	public static final String PARAM_NULL = "参数为空";						//参数为空
	public static final String SELECT_PARAM_NULL = "010102";				//未选择任何数据
	/**
	 * 系统错误
	 */
	public static final String SYSTEM_ERROR = "系统错误";
	
	
	/**
	 * 异常编码
	 */
	private String exceptionCode;
	/**
	 * 异常中文描述
	 */
	private String messageCHN;
	/**
	 * 补充信息描述
	 */
	private String messageExt;
	
   
    public AbstractException(String exceptionCode, String[] params, Throwable e) {
    	super(e);
		this.exceptionCode = exceptionCode;
		messageCHN = "";
		if (params != null){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < params.length; i++) {
				String element = params[i];
				sb.append("\"");
				sb.append(element);
				sb.append("\"");
				sb.append("\t");
			}
			messageExt  = sb.toString();
		}
	}

	public String toString() {
        String s = getClass().getSimpleName();
        String message =exceptionCode+"\t" +messageCHN +"\t" +(messageExt==null?"":messageExt);
        return (message != null) ? (s + ": " + message) : s;
    }

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getMessageCHN() {
		return messageCHN;
	}

	public void setMessageCHN(String messageCHN) {
		this.messageCHN = messageCHN;
	}

	public String getMessageExt() {
		return messageExt;
	}

	public void setMessageExt(String messageExt) {
		this.messageExt = messageExt;
	}

}
