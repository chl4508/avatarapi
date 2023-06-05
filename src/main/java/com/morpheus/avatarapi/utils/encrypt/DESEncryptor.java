package com.morpheus.avatarapi.utils.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.lang.annotation.Inherited;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

public class DESEncryptor implements Encryptor {

	private static final String ENCRYPT_TYPE_DES = "DES";

	/**
	 * {@link Inherited}
	 */
	@Override
	public String decrypt(EncryptParam param) throws EncryptionException {

		if (!(param instanceof DESEncryptParam)) {
			throw new EncryptionException("param must be a instance of DESEncryptParam object.");
		}
		DESEncryptParam desParam = (DESEncryptParam) param;

		Cipher cipher = getCipher(desParam, Cipher.DECRYPT_MODE);

		try {
			byte[] input = Base64.decodeBase64(desParam.getTargetString());

			byte[] decrypted = cipher.doFinal(input);
			return new String(decrypted, CHARSET_UTF_8);

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}
	}

	/**
	 * {@link Inherited}
	 */
	@Override
	public String encrypt(EncryptParam param) throws EncryptionException {

		if (!(param instanceof DESEncryptParam)) {
			throw new EncryptionException("param must be a instance of DESEncryptParam object.");
		}

		String str = param.getTargetString();
		if (str == null || str.isEmpty()) {
			return null;
		}

		DESEncryptParam desParam = (DESEncryptParam) param;

		Cipher cipher = getCipher(desParam, Cipher.ENCRYPT_MODE);

		try {
			byte[] input = str.getBytes(CHARSET_UTF_8);
			byte[] encrypted = cipher.doFinal(input);

			return new String(Base64.encodeBase64(encrypted));

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}
	}

	/**
	 * Init Cipher and return
	 * 
	 * @param desParam
	 *            DES param
	 * @return Cipher
	 * @throws EncryptionException
	 *             Encrypt Exception
	 */
	private Cipher getCipher(DESEncryptParam desParam, int encryptMode) throws EncryptionException {

		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance(desParam.getTransformation());
			cipher.init(encryptMode, getKey(desParam), getIv(desParam));

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}

		return cipher;
	}

	/**
	 * Return DES encryption lv
	 * 
	 * @param param
	 *            Encrypt param
	 * @return Encryption lv
	 */
	private IvParameterSpec getIv(DESEncryptParam param) {

		byte[] lvBytes = param.getIv().getBytes(StandardCharsets.US_ASCII);

		return new IvParameterSpec(lvBytes);
	}

	/**
	 * Return DES encryption key
	 * 
	 * @param param
	 *            Encrypt Param
	 * @return Encryption key
	 * @throws EncryptionException
	 *             Encrypt Exception
	 */
	private SecretKey getKey(DESEncryptParam param) throws EncryptionException {

		byte[] keyBytes = param.getKey().getBytes(StandardCharsets.US_ASCII);

		try {
			KeySpec ks = new DESKeySpec(keyBytes);
			SecretKeyFactory instance = SecretKeyFactory.getInstance(ENCRYPT_TYPE_DES);

			return instance.generateSecret(ks);

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}
	}
}
