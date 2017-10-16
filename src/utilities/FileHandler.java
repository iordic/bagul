package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;


public class FileHandler {	
	File imageFile;
	
	public FileHandler(String file) {
		imageFile = new File(file);
	}
	
	/**
	 * Insert in file a message passed as bytearray. It doesn't encrypt, only inserts.
	 * @param encrypted bytearray message
	 */
	public void insertMessage(byte [] message) {
		int wrapSize = message.length + Constants.BYTES_FOR_MESSAGE_SIZE + 
				Constants.BAGUL_MARK.length + Constants.JPEG_EOF.length;
		byte [] wrapped = new byte[wrapSize];
		byte [] intBytes = ByteBuffer.allocate(Constants.BYTES_FOR_MESSAGE_SIZE).putInt(message.length).array();	// This generates a big endian order :(
		System.arraycopy(message, 0, wrapped, 0, message.length);
		System.arraycopy(intBytes, 0, wrapped, message.length, intBytes.length);
		System.arraycopy(Constants.BAGUL_MARK, 0, wrapped, message.length + intBytes.length, Constants.BAGUL_MARK.length);
		System.arraycopy(Constants.JPEG_EOF, 0, wrapped, message.length + intBytes.length + Constants.BAGUL_MARK.length, Constants.JPEG_EOF.length);
		
		FileOutputStream output;
		try {
			output = new FileOutputStream(imageFile.getPath(), true);
			output.write(wrapped);
			output.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Extract message contained within the image without remove it.
	 * @return message bytearray, without decrypt
	 * @throws IOException
	 */
	public byte [] extractMessage() throws IOException {
		if(!containsMessage())
			return null;
		byte [] data = Files.readAllBytes(imageFile.toPath());
		byte [] intBytes = new byte[Constants.BYTES_FOR_MESSAGE_SIZE];
		int i = data.length - (Constants.JPEG_EOF.length + Constants.BAGUL_MARK.length + Constants.BYTES_FOR_MESSAGE_SIZE);
		System.arraycopy(data, i, intBytes, 0, intBytes.length);
		int messageLength = ByteBuffer.wrap(intBytes).order(ByteOrder.BIG_ENDIAN).getInt(); // Should be LITTLE_ENDIAN but doesn't work
		byte [] message = new byte[messageLength];
		i = data.length - (messageLength + Constants.BYTES_FOR_MESSAGE_SIZE + Constants.BAGUL_MARK.length + Constants.JPEG_EOF.length);
		System.arraycopy(data, i, message, 0, message.length);
		return message;
	}
	
	/**
	 * Removes message from image, doesn't return anything neither display it.
	 * @throws IOException
	 */
	public void  removeMessage() throws IOException {
		if(!containsMessage())
			return;
		byte [] data = Files.readAllBytes(imageFile.toPath());
		byte [] intBytes = new byte[Constants.BYTES_FOR_MESSAGE_SIZE];
		int i = data.length - (Constants.JPEG_EOF.length + Constants.BAGUL_MARK.length + Constants.BYTES_FOR_MESSAGE_SIZE);
		System.arraycopy(data, i, intBytes, 0, intBytes.length);
		int messageLength = ByteBuffer.wrap(intBytes).order(ByteOrder.BIG_ENDIAN).getInt(); // Should be LITTLE_ENDIAN but doesn't work
		i = data.length - (messageLength + Constants.BYTES_FOR_MESSAGE_SIZE + Constants.BAGUL_MARK.length + Constants.JPEG_EOF.length);
		byte [] cleanData = new byte[i];
		System.arraycopy(data, 0, cleanData, 0, cleanData.length);
		FileOutputStream output;
		try {
			output = new FileOutputStream(imageFile.getPath(), false);
			output.write(cleanData);
			output.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks if image has the mark.
	 * @return true if contains and false if not
	 * @throws IOException
	 */
	public boolean containsMessage() throws IOException {
		byte [] data = Files.readAllBytes(imageFile.toPath());
		byte [] mark = new byte[Constants.BAGUL_MARK.length];
		int position = data.length - (Constants.BAGUL_MARK.length + Constants.JPEG_EOF.length);
		System.arraycopy(data, position, mark, 0, mark.length);
		for (int i = 0; i < mark.length; i++) {
			if (mark[i] != Constants.BAGUL_MARK[i]) {
				return false;
			}
		}
		return true;
	}
}
