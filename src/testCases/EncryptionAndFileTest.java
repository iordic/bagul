package testCases;

import static org.junit.Assert.*;

import java.io.IOException;

import utilities.Crypto;
import utilities.FileHandler;

import org.junit.Test;

public class EncryptionAndFileTest {

	@Test
	public void testInsertEncryptedMessage() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/cat.jpg");
		byte [] message = {(byte)'M', (byte)'e', (byte)'o', (byte)'w', (byte)'!'};
		String password = "1234";
		byte [] cipheredMessage = Crypto.encrypt(password, message);
		f.insertMessage(cipheredMessage);
		byte [] actualCipheredMessage = f.extractMessage();
		byte [] actualMessage = Crypto.decrypt(password, actualCipheredMessage);
		String expected = "Meow!";
		String actual = new String(actualMessage);
		f.removeMessage();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testExtractEncryptedMessage() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/catWithEncryptedMessage.jpg");
		String password = "12345678";
		byte [] encryptedMessage = f.extractMessage();
		byte [] plainMessage = Crypto.decrypt(password, encryptedMessage);
		String expected = "All your base are belong to us";
		String actual = new String(plainMessage);
		assertEquals(expected, actual);
	}
}
