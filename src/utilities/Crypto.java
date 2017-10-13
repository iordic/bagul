package utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class Crypto {
	
	private static byte[][] BytesToKey(String password) {
		MessageDigest shaDigest;
		MessageDigest md5Digest;
		try {
			shaDigest = MessageDigest.getInstance("SHA-256");
			md5Digest = MessageDigest.getInstance("MD5");
			byte [][] keys = new byte[2][];
			keys[0] = shaDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			keys[1] = md5Digest.digest(password.getBytes(StandardCharsets.UTF_8));
			return keys;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	public static byte[] encrypt(String password, byte[] plainText) {
		try {
			PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
			byte[][] keys = BytesToKey(password);
			CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(keys[0]), keys[1]);
			aes.init(true, ivAndKey);
			return cipherData(aes, plainText);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] decrypt(String password, byte[] encryptedData) {
		try {
			PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
			byte[][] keys = BytesToKey(password);
			CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(keys[0]), keys[1]);
			aes.init(false, ivAndKey);
			return cipherData(aes, encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static byte[] cipherData(PaddedBufferedBlockCipher aes, byte[] data) throws Exception {
		int minSize = aes.getOutputSize(data.length);
		byte[] outBuf = new byte[minSize];
		int length1 = aes.processBytes(data, 0, data.length, outBuf, 0);
		int length2 = aes.doFinal(outBuf, length1);
		int actualLength = length1 + length2;
		byte[] result = new byte[actualLength];
		System.arraycopy(outBuf, 0, result, 0, result.length);
		return result;
	}
}
