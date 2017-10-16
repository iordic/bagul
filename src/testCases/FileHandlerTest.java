package testCases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import utilities.FileHandler;

public class FileHandlerTest {

	@Test
	public void testInsertMessage() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/cat.jpg");
		byte [] message = {(byte)'M', (byte)'e', (byte)'o', (byte)'w', (byte)'!'};
		f.insertMessage(message);
		byte [] actualMessage = f.extractMessage();
		String expected = "Meow!";
		String actual = new String(actualMessage);
		f.removeMessage();
		assertEquals(expected, actual);
	}

	@Test
	public void testContainsMessage() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/catWithMessage.jpg");
		assertTrue(f.containsMessage());
	}
	
	@Test
	public void testExtractMessage() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/catWithMessage.jpg");
		byte [] message = f.extractMessage();
		String expected = "Hello World!";
		String actual = new String(message);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testNoMessage() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/cat.jpg");
		assertFalse(f.containsMessage());
	}
	
	@Test
	(expected = IOException.class)
	public void testFileDoesNotExist() throws IOException {
		FileHandler f = new FileHandler(System.getProperty("user.dir") + "/src/testCases/testImages/notCat.jpg");
		assertFalse(f.containsMessage());
	}
}
