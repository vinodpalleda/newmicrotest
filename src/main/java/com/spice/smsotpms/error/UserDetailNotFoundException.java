package com.spice.smsotpms.error;

public class UserDetailNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserDetailNotFoundException() {
		super();
	}

	public UserDetailNotFoundException(String msg){
		super(msg);
	}
	
	public UserDetailNotFoundException(String msg, Throwable th) {
		super(msg,th);
	}
	
	public UserDetailNotFoundException(Throwable th) {
		super(th);
	}

}
