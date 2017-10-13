package testCases;

import static org.junit.Assert.*;

import org.junit.Test;
import utilities.Crypto;

public class EncryptionTest {

	@Test
	public void testEncrypt() {
		String expected = "Hello world!";
		String password = "1234";
		byte [] ciphered = Crypto.encrypt(password, expected.getBytes());
		byte [] deciphered = Crypto.decrypt(password, ciphered);
		String actual = new String(deciphered);
		assertEquals(expected, actual);
	}

	@Test
	(expected = Exception.class)
	public void testWrongPassword() throws Exception {
		String expected = "Hello world!";
		String password = "1234";
		byte [] ciphered = Crypto.encrypt(password, expected.getBytes());
		byte [] deciphered = Crypto.decrypt("WrongPassword", ciphered);
		assertEquals(expected, new String(deciphered));
	}

}
