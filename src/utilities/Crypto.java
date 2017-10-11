package utilities;

import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

public final class Crypto {

	private static byte[] getSha256(String text) {
		// TODO: return a bytearray with the SHA256 sum of the password
		
		return null;
	}
	
	private static Keys EVP_BytesToKey(String password, int keySize, int ivSize) {
		// TODO: D_i = HASH^count(D_(i-1) || data)

		return null;
	}
	
	public static byte[] encrypt(String password, String plainText) {
		// TODO:

		return null;
	}
	
	public static byte[] decrypt(String password, byte[] encryptedData) {
		// TODO:
		
		return null;
	}
	
	private static byte[] cipherData(PaddedBufferedBlockCipher aes, byte[] data) throws Exception {
		//TODO: this method is called by encrypt and decrypt methods and do the operations.
		
		return null;
	}
	
	private class Keys {
		private byte [] key;
		private byte [] iv;
		
		private Keys(byte [] key, byte [] iv) {
			this.key = key;
			this.iv = iv;
		}
		
		private byte[] getKey() {
			return key;
		}
		
		private byte[] getIV() {
			return iv;
		}
	}
}
