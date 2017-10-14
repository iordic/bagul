package utilities;

import java.io.File;

public class FileHandler {	
	File imageFile;
	String filePath;
	
	public void insertMessage(byte [] encrypted) {
	//TODO: Open file and append bytearray encrypted info + JFIF tail at final of it.
	}
		
	public String extractMessage() {
	//TODO: Read message from processed file.	
		return null;
	}
	
	public void  removeMessage() {
	//TODO: Remove message from processed file.	
	}
	
	public boolean containsMessage() {
	//TODO: Check if file has been processed and contains a message.	
		return false;
	}
}
