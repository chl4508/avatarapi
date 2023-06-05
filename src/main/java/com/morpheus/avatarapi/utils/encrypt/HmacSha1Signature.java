package com.morpheus.avatarapi.utils.encrypt;



import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

public class HmacSha1Signature {
	
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	   /**
     * @description byte 배열?�� 16진수�? �??��?��?��.
     */
    public static String toHexString(byte[] bytes) {
         
        Formatter formatter = new Formatter();
         
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
  
        return formatter.toString();
         
    }
  
    /**
     * @description byte 배열?�� Base64�? ?��코딩?��?��.
     */
    public static String toBase64String(byte[] bytes){
         
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
         
    }
     
    /**
     * @description HmacSHA1�? ?��?��?��?��?��. (HmacSHA1?? hash algorism?��?��?�� 복호?��?�� 불�??��)
     */
    public static String encryption(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
         
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
         
        return toHexString(mac.doFinal(data.getBytes()));
         
    }
     
    public static void main(String[] args) throws Exception {
         
        String encryptedStr = encryption("hi there~", "UDn3AE4fHoltof0XjcdUQ");
        System.out.println("encryptedStr : " + encryptedStr);
         
    }
     
}
