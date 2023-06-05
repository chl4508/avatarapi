package com.morpheus.avatarapi.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.spec.AlgorithmParameterSpec;

public class AES256 implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	private static final String encodeType = "AES/CBC/PKCS5Padding";
	private static final String encodeKey = "ThisIsAuthKey87ForTheLBSPortal11";
	private static final String encodeTypeSimple = "AES";
	private static final String charSet = "UTF-8";

	/**
	 * 문자열 암호화.
	 */
	public static String encodeStr(String plainString) throws Throwable {
		byte[] textBytes = plainString.getBytes(charSet);
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

		plainString = new String(Base64Coder.encode(cipher.doFinal(textBytes)));
		return plainString;
	}

	/**
	 * 문자열 복호화.
	 */
	public static String decodeStr(String encodeString) throws Throwable {
		byte[] textBytes = Base64Coder.decode(encodeString);
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

		encodeString = new String(cipher.doFinal(textBytes), charSet);
		return encodeString;
	}

	/**
	 * 암복호화 공통 처리
	 */
	private static Cipher getCipher(int mode) throws Throwable {
		SecretKeySpec key = new SecretKeySpec(encodeKey.getBytes(charSet), encodeTypeSimple);

		AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance(encodeType);
		cipher.init(mode, key, ivParameterSpec);

		return cipher;
	}
}