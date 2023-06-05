package com.morpheus.avatarapi.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;


@Component
public class AesCrypto {
	private static final Logger logger = LoggerFactory.getLogger("debug");
	
	
	private SecretKeySpec aeskey;
	private final String ENCTYPE_AES = "AES/CBC/PKCS5Padding";
	private final byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	
	public AesCrypto() {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			
//			String keyStr = "0seSsionkeYForOlLehiNaviserVice1";
			String keyStr = "seSsionkeYForThInkWarEmaPserVice";
			                 
			this.aeskey = new SecretKeySpec(keyStr.getBytes("UTF-8"), "AES");
			
		} catch (Exception e) {
			logger.error("[EXCEPTION] : ", e);
		}
	}

	
	public String encKeyByAES(String plainStr) throws Exception {
			byte[] textBytes = plainStr.getBytes("UTF-8");
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			Cipher cipher = Cipher.getInstance(ENCTYPE_AES);
			cipher.init(Cipher.ENCRYPT_MODE, aeskey, ivSpec);
			
			return new String(Base64Coder.encode(cipher.doFinal(textBytes)));
	}
	
	public String decKeyByAES(String encStr) throws Exception {
		byte[] textBytes = Base64Coder.decode(encStr);
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance(ENCTYPE_AES);
		cipher.init(Cipher.DECRYPT_MODE, aeskey, ivSpec);

		return new String(cipher.doFinal(textBytes), "UTF-8");
	}

}
