package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	private final int SIZE = 9;

	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
	}

	public void write(byte b[]) throws IOException {
		int count = 0;
		for (int i = 0; i < SIZE; i++) {
			out.write(b[i]);
		}

		byte previewbyte = b[SIZE];
		for (int i = SIZE; i < b.length; i++) {
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
