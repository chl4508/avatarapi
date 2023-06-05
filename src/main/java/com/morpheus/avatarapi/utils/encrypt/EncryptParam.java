package com.morpheus.avatarapi.utils.encrypt;

/**
 * Encrypt and Decrypt model
 * 
 * @author hhg0104
 *
 */
public class EncryptParam {

	private String key;

	private String targetString;

	public EncryptParam() {
		// default constructor
	}

	public EncryptParam(String targetString) {
		this.targetString = targetString;
	}
	
	public String getKey() {
		return key;
	}

	public String getTargetString() {
		return targetString;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}

}
