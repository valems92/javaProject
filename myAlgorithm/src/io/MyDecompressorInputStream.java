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
	private final int FIXED_DATA_SIZE = 9;

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
		in.read(b);

		int i, index = FIXED_DATA_SIZE, count = 0;
		byte byteToWrite = 0;
		byte[] decompressB = new byte[b.length];

		for (i = 0; i < FIXED_DATA_SIZE; i++)
			decompressB[i] = b[i];

		for (i = FIXED_DATA_SIZE; i < b.length; i = i + 2) {
			count = b[i];

			if (count != 0) {
				byteToWrite = b[i + 1];
				for (int j = 0; j < count; j++) {
					decompressB[index] = byteToWrite;
					index++;
				}
			} else {
				for (int k = 0; k < b.length; k++) {
					b[k] = decompressB[k];
				}
				return 0;
			}
		}
		for (int k = 0; k < b.length; k++) {
			b[k] = (b[k] != 0) ? decompressB[k] : 0;
		}
		return 0;
	}

	@Override
	public int read() throws IOException {
		return 0;
	}

}
