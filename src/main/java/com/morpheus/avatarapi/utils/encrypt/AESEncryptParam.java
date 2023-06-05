package com.morpheus.avatarapi.utils.encrypt;

/**
 * <p>
 * AES256 Encrypt and Decrypt model
 * </p>
 * <p>
 * default hash algorithm: PBKDF2WithHmacSHA1
 * </p>
 * <p>
 * default aes type: AES/CBC/PKCS5Padding
 * </p>
 * <p>
 * default key size: 128
 * </p>
 * <p>
 * default password iteration count: 128
 * </p>
 * 
 * @author hhg0104
 *
 */
public class AESEncryptParam extends EncryptParam {

	private String salt = "";

	private String iv = "";

	private int keySize = 128;

	private String hashAlgorithm = "PBKDF2WithHmacSHA1";

	private String aesType = "AES/CBC/PKCS5Padding";

	private int passwordIterationCount = 7;

	public AESEncryptParam() {
	}

	public AESEncryptParam(String targetStr, String key, String iv, String salt) {
		super.setTargetString(targetStr);
		super.setKey(key);
		this.setIv(iv);
		this.setSalt(salt);
	}

	public String getAesType() {
		return aesType;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public String getIv() {
		return iv;
	}

	public int getKeySize() {
		return keySize;
	}

	public int getPasswordIterationCount() {
		return passwordIterationCount;
	}

	public String getSalt() {
		return salt;
	}

	public void setAesType(String aesType) {
		this.aesType = aesType;
	}

	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}

	public void setPasswordIterationCount(int passwordIterationCount) {
		this.passwordIterationCount = passwordIterationCount;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
