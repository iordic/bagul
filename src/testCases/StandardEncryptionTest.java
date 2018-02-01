package testCases;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.StandardCrypto;


public class StandardEncryptionTest {

	@Test
	public void testEncryptAndDecrypt() {
		String expected = "Testing encryption";
		String password = "Aloha";
		byte [] ciphered = StandardCrypto.encrypt(password, expected.getBytes());
		byte [] deciphered = StandardCrypto.decrypt(password, ciphered);
		String actual = new String(deciphered);
		assertEquals(expected, actual);
	}

}
