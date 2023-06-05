package com.morpheus.avatarapi.utils.encrypt;

/**
 * <p>
 * DES Encrypt and Decrypt model
 * </p>
 * <p>
 * default transformation: DES/CBC/PKCS5Padding
 * </p>
 * @author hhg0104
 *
 */
public class DESEncryptParam extends EncryptParam {

	private String iv = "";

	private String transformation = "DES/CBC/PKCS5Padding";

	public DESEncryptParam(String targetStr, String key, String lv) {
		super.setTargetString(targetStr);
		super.setKey(key);
		this.setIv(lv);
	}

	/**
	 * Constructor ke, lv, transformation(default = DES/CBC/PKCS5Padding)
	 */
	public DESEncryptParam(String targetStr, String key, String lv, String transformation) {
		setTargetString(targetStr);
		setKey(key);
		setIv(lv);
		setTransformation(transformation);
	}

	public String getIv() {
		return iv;
	}

	public String getTransformation() {
		return transformation;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public void setTransformation(String transformation) {
		this.transformation = transformation;
	}

}
