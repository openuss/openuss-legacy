package freestyleLearning.homeCore.usersManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

public class FSLUserPassEncrypter {
	// variables for encryption
	//private Cipher encryptCipher;
    //private Cipher decryptCipher;
    //private BASE64Encoder base64Encoder = new BASE64Encoder();
    //private BASE64Decoder base64Decoder = new BASE64Decoder();
    
    /** example for encryption with key ***
    FSLUserPassEncrypter() {
    	try {
             // Make sure SUN JCE are a provider
             Security.addProvider(new com.sun.crypto.provider.SunJCE());
             
             // The DES EDE Key - any 24 bytes will do though beware of weak keys.
             // This could be read from a file.
             final byte[] DESedeKeyBytes = {
                 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10,
                 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
             };
             
             // IV For CBC mode
             // Again, could be read from a file.
             final byte[] ivBytes = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
   
             SecretKey key = new SecretKeySpec(DESedeKeyBytes, "DESede");
             IvParameterSpec iv = new IvParameterSpec(ivBytes);
             encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "SunJCE");
             encryptCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, iv);
             decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "SunJCE");
             decryptCipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, iv);
    	} catch (Exception e) {
             e.printStackTrace(System.out);
        }

    }
 	
    public String encryptOld(String password) throws Exception {
    	byte[] passwordBytes = password.getBytes();
        byte[] encryptedPasswordBytes = encryptCipher.doFinal(passwordBytes);
        String encodedEncryptedPassword = base64Encoder.encode(encryptedPasswordBytes);
        return encodedEncryptedPassword;
    }
    
    public String decryptOld(String encodedEncryptedPassword) throws Exception {	
    	byte[] encryptedPasswordBytes = base64Decoder.decodeBuffer(encodedEncryptedPassword);
        byte[] passwordBytes = decryptCipher.doFinal(encryptedPasswordBytes);
        String recoveredPassword = new String(passwordBytes);
        return recoveredPassword;
    }
    **/
    
	/**
	 * Creates hash value for given text.
	 * @param <code>String</code> plain text.
	 * @return <code>String</code> has value.
	 */
    public String encrypt(String plaintext) throws Exception {
      MessageDigest md = null;
      try {
        md = MessageDigest.getInstance("SHA");
      } catch(NoSuchAlgorithmException e) {
    	  throw new Exception(e.getMessage());
      }
      try {
        md.update(plaintext.getBytes("UTF-8"));
      } catch(UnsupportedEncodingException e) {
    	  throw new Exception(e.getMessage());
      }
      byte raw[] = md.digest();
      String hash = (new BASE64Encoder()).encode(raw);
      return hash;
    }
}
