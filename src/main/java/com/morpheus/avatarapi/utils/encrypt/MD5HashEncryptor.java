package com.morpheus.avatarapi.utils.encrypt;


import javax.xml.bind.DatatypeConverter;
import java.lang.annotation.Inherited;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 Hash Encrypt class
 * 
 * @author hhg0104
 *
 */
public class MD5HashEncryptor extends OneWayEncryptor {

	private static final String MD5 = "MD5";

	/**
	 * {@link Inherited}
	 * 
	 * @param param
	 *            EnctypeParam object itself. There is no child param object
	 *            for MD5Hash.
	 */
	@Override
	public String encrypt(EncryptParam param) throws EncryptionException {

		String str = param.getTargetString();
		if (str == null || str.isEmpty()) {
			return null;
		}

		try {

			MessageDigest md = MessageDigest.getInstance(MD5);

			byte[] byteData = md.digest(str.getBytes(StandardCharsets.US_ASCII));

			return DatatypeConverter.printHexBinary(byteData);

		} catch (NoSuchAlgorithmException e) {
			// never occur
		}

		return null;
	}

}
