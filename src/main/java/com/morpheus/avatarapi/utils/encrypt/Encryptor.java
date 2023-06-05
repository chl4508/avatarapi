package com.morpheus.avatarapi.utils.encrypt;

/**
 * Encrypt and decrypt support interface
 * 
 * @author hhg0104
 *
 */
public interface Encryptor {

	public static final String CHARSET_UTF_8 = "UTF-8";

	public String decrypt(EncryptParam param) throws EncryptionException;

	public String encrypt(EncryptParam param) throws EncryptionException;
}
