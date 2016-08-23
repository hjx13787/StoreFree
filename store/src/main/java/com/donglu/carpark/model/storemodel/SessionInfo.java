package com.donglu.carpark.model.storemodel;

/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用
 * 
 * @author 孙宇
 * 
 */
public class SessionInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -366530817264680736L;
	private String loginName;
	private String loginPassword;
	private String storeName;
	private String userName;
	
	
	
	public SessionInfo() {
	}

	public SessionInfo(String loginName, String loginPassword, String storeName, String userName) {
		super();
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.storeName = storeName;
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
