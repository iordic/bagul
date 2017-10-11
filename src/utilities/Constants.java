package utilities;

public final class Constants {
	public static final int BYTES_FOR_MESSAGE_SIZE = 4; // 4 bytes => 1 int
	public static final byte[] BAGUL_MARK = {(byte) 'b', (byte)'a', (byte)'g', (byte)'u', (byte)'l'};
	public static final byte[] JPEG_EOF = {(byte) 0xff, (byte) 0xd9};	
	public static final int AES_BLOCK_SIZE = 48;	// Bytes
	public static final int AES_KEY_SIZE = 32;
	public static final int AES_IV_SIZE = 16;	
}
