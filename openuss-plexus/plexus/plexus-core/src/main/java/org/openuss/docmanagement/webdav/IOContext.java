package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public abstract class IOContext {
    /**
     * Checks, if context owns a stream.
     * @return True, if context owns a stream.
     */
    public abstract boolean hasStream();

    /**
     * Ends transaction with transaction success.
     * @param success Indicates a successful completion of data transfer.
     */
    public abstract void informCompleted(boolean success);

    /**
     * Checks, if context transaction is completed.
     * @return True, if transaction is completed.
     */
    public abstract boolean isCompleted();

	
	/**
	 * Copies data from {@link InputStream} to {@link OutputStream}.
	 * @param inputStream The data source.
	 * @param outputStream The data target.
	 * @throws IOException
	 */
	protected void transferData(InputStream inputStream, OutputStream outputStream) throws IOException {
		try {
			// read in 8k-blocks until end of input stream is reached
			byte[] buffer = new byte[8192];
			int readBytes;
			while ((readBytes = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, readBytes);
			}
		} finally {
			inputStream.close();
		}
	}
}
