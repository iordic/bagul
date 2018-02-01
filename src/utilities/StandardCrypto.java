package utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Avoid using this if possible. In this class the encryption used is AES-128 instead AES-256.
 * This class removes the need of using third party libraries (like BouncyCastle) for the encryption tasks.
 * @author Jordi Castelló
 *
 */
public class StandardCrypto {
	
	private final static int keySize = 16;	// Java API policy doesn't allow greater sizes with AES.
	private final static int ivSize = 16;
	
	private static byte[][] BytesToKey(String password) {
		MessageDigest shaDigest;
		try {
			shaDigest = MessageDigest.getInstance("SHA-256");	// 32 bytes, key has 16 and IV another 16.
			byte [][] keys = new byte[2][];
			keys[0] = new byte[keySize];
			keys[1] = new byte[ivSize];
			byte [] shaSum = shaDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			System.arraycopy(shaSum, 0, keys[0], 0, keySize);
			System.arraycopy(shaSum, keySize, keys[1], 0, ivSize);
			return keys;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}		
	}
	
	public static byte[] encrypt(String password, byte[] plainText) {
		try {
			byte[][] keys = BytesToKey(password);
			SecretKeySpec key = new SecretKeySpec(keys[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keys[1]);
			Cipher aes = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			aes.init(Cipher.ENCRYPT_MODE, key, iv);
			return aes.doFinal(plainText);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] decrypt(String password, byte[] encryptedData) {
		try {
			byte[][] keys = BytesToKey(password);
			SecretKeySpec key = new SecretKeySpec(keys[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keys[1]);
			Cipher aes = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			aes.init(Cipher.DECRYPT_MODE, key, iv);
			return aes.doFinal(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
