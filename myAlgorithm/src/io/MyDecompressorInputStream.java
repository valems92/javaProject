package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * <h1>public class MyDecompressorInputStream</h1> A class that get a compressed
 * array of bytes and decompress it.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 * @implements InputStream
 *
 */
public class MyDecompressorInputStream extends InputStream {
	private InputStream in;
	private final int FIXED_DATA_SIZE = 11;

	/**
	 * Initialize the input stream.
	 * <p>
	 * 
	 * @param in
	 */
	public MyDecompressorInputStream(InputStream in) {
		super();
		this.in = in;
	}

	/**
	 * <h1>read</h1> Get a compressed array of bytes and decompress it.
	 * <p>
	 * 
	 * @param b
	 *            Array of bytes to decompress
	 */
	@Override
	public int read(byte[] b) throws IOException {
		byte[] compressed = new byte[b.length];
		in.read(compressed);
		
		int i, index = FIXED_DATA_SIZE, count = 0;
		byte byteToWrite = 0;
		
		for (i = 0; i < FIXED_DATA_SIZE; i++)
			b[i] = compressed[i];
		
		for (i = FIXED_DATA_SIZE; i < compressed.length; i = i + 2) {
			count = compressed[i];

			if (count != 0) {
				byteToWrite = compressed[i + 1];
				for (int j = 0; j < count; j++) {
					b[index] = byteToWrite;
					index++;
				}
			} else
				return 0;
		}
		return 0;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

}
