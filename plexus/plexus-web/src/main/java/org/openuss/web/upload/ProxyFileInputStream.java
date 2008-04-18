/**
 * 
 */
package org.openuss.web.upload;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Proxy will first open the file input stream on the first access.
 * @author Ingo Dueppe
 */
public class ProxyFileInputStream extends InputStream implements Serializable {
	
	private static final long serialVersionUID = 7511812835302763404L;

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(ProxyFileInputStream.class);

	private File file;
	
	private transient FileInputStream inputStream;
	
	public ProxyFileInputStream(File file) {
		this.file = file;
	}
	
	private InputStream getInputStream() throws FileNotFoundException {
		if (inputStream == null) {
			logger.debug("open FileInputStream");
			inputStream = new FileInputStream(file);
		}
		return inputStream;
	}

	@Override
	public int read() throws IOException {
		return getInputStream().read();
	}

	@Override
	public int available() throws IOException {
		return getInputStream().available();
	}

	@Override
	public void close() throws IOException {
		if (inputStream != null) {
			inputStream.close();
		}
	}

	@Override
	public synchronized void mark(int readlimit) {
		try {
			getInputStream().mark(readlimit);
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean markSupported() {
		try {
			return getInputStream().markSupported();
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return getInputStream().read(b, off, len);
	}

	@Override
	public int read(byte[] b) throws IOException {
		return getInputStream().read(b);
	}

	@Override
	public synchronized void reset() throws IOException {
		getInputStream().reset();
	}

	@Override
	public long skip(long n) throws IOException {
		return getInputStream().skip(n);
	}
	
}