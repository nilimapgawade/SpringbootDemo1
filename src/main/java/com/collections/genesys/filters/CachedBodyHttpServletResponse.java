package com.collections.genesys.filters;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

	private final ByteArrayOutputStream cachedBody = new ByteArrayOutputStream();

	public CachedBodyHttpServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return new CachedBodyServletOutputStream(cachedBody);
	}

	public byte[] getContentAsByteArray() {
		return cachedBody.toByteArray();
	}

	private static class CachedBodyServletOutputStream extends ServletOutputStream {

		private final ByteArrayOutputStream outputStream;

		public CachedBodyServletOutputStream(ByteArrayOutputStream outputStream) {
			this.outputStream = outputStream;
		}

		@Override
		public void write(int b) {
			outputStream.write(b);
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener listener) {
			throw new UnsupportedOperationException();
		}
	}
}
