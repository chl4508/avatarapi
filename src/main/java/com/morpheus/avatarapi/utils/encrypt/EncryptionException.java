package com.morpheus.avatarapi.utils.encrypt;

/**
 * 암호화, 복호화 Exception
 * 
 * @author hhg0104
 *
 */
public class EncryptionException extends Exception {

	private static final long serialVersionUID = 3766945404756526359L;

	public EncryptionException() {
		super();
	}

	public EncryptionException(Exception e) {
		super(e.getMessage(), e);
	}

	public EncryptionException(Exception e, String message) {
		super(message, e);
	}

	public EncryptionException(String message) {
		super(message);
	}
}
