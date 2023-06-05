package com.morpheus.avatarapi.utils.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.annotation.Inherited;
import java.security.spec.KeySpec;

/**
 * AES256 Encrypt class
 * 
 * @author hhg0104
 *
 */
public class AESEncryptor implements Encryptor {

	private static final String ALGORITHM_AES = "AES";

	/**
	 * {@link Inherited}
	 */
	@Override
	public String decrypt(EncryptParam param) throws EncryptionException {

		String str = param.getTargetString();
		if (str == null || str.isEmpty()) {
			throw new EncryptionException("targetString must not be null or empty.");
		}
		
		
		if (!(param instanceof AESEncryptParam)) {
			throw new EncryptionException("param must be a instance of AESEncryptParam object.");
		}
		AESEncryptParam aesParam = (AESEncryptParam) param;

		try {
			// ECB 방식을 위해
			Cipher cipher = Cipher.getInstance(aesParam.getAesType());

			SecretKey keySpec = getKeySpec(aesParam);

			cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(aesParam.getIv().getBytes(CHARSET_UTF_8)));

			byte[] byteStr = Base64.decodeBase64(str.getBytes(CHARSET_UTF_8));

			return new String(cipher.doFinal(byteStr), CHARSET_UTF_8);

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}
	}

	/**
	 * {@link Inherited}
	 */
	@Override
	public String encrypt(EncryptParam param) throws EncryptionException {

		String str = param.getTargetString();
		if (str == null || str.isEmpty()) {
			throw new EncryptionException("targetString must not null or empty.");
		}

		if (!(param instanceof AESEncryptParam)) {
			throw new EncryptionException("param must be a instance of AESEncryptParam object.");
		}
		AESEncryptParam aesParam = (AESEncryptParam) param;

		byte[] encrypted = new byte[0];

		try {
			Cipher cipher = Cipher.getInstance(aesParam.getAesType());

			cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(aesParam),
					new IvParameterSpec(aesParam.getIv().getBytes(CHARSET_UTF_8)));

			encrypted = cipher.doFinal(str.getBytes(CHARSET_UTF_8));

			return new String(Base64.encodeBase64(encrypted));

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}

	}

	/**
	 * generate keyspec
	 * 
	 * @param param
	 *            properties to generate keyspec
	 * @return generated keyspec
	 * @throws EncryptionException
	 *             Enctypt exception
	 */
	private SecretKey getKeySpec(AESEncryptParam param) throws EncryptionException {

		byte[] salt = param.getSalt().getBytes();
		char[] password = param.getKey().toCharArray();

		try {
			SecretKeyFactory sf = SecretKeyFactory.getInstance(param.getHashAlgorithm());
			KeySpec keySpec = new PBEKeySpec(password, salt, param.getPasswordIterationCount(), param.getKeySize());

			SecretKey secretKey = sf.generateSecret(keySpec);

			return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM_AES);

		} catch (Exception e) {
			throw new EncryptionException(e, e.getMessage());
		}
	}

}
