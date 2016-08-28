package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <h1>MyCompressorOutputStream</h1> A class that compress an array of bytes.
 * The script unit the repeated byte sequences and keep it's length.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 * @implements OutputStream
 *
 */
public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	private final int FIXED_DATA_SIZE = 10;

	/**
	 * Initialize the output stream.
	 * <p>
	 * 
	 * @param out
	 */
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
	}

	/**
	 * <h1>write</h1> Get an array of bytes and compress it.
	 * <p>
	 * 
	 * @param b
	 *            Array of bytes to compress
	 */
	public void write(byte b[]) throws IOException {
		int count = 0;
		for (int i = 0; i < FIXED_DATA_SIZE; i++) {
			out.write(b[i]);
		}

		byte previewbyte = b[FIXED_DATA_SIZE];
		for (int i = FIXED_DATA_SIZE; i < b.length; i++) {
			if (b[i] == previewbyte) {
				count++;
			} else {
				while (count > 255) {
					out.write((byte) 255);
					out.write(previewbyte);
					count = count - 255;
				}
				out.write((byte) count);
				out.write(previewbyte);
				count = 1;
				previewbyte = b[i];
			}
		}
		out.write((byte) count);
		out.write(previewbyte);
	}
}
