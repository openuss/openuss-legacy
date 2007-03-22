package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public abstract class IOContextBase implements IOContext {
    /* (non-Javadoc)
     * @see org.openuss.docmanagement.webdav.IOContext#hasStream()
     */
    public abstract boolean hasStream();

    /* (non-Javadoc)
     * @see org.openuss.docmanagement.webdav.IOContext#informCompleted(boolean)
     */
    public abstract void informCompleted(boolean success);

    /* (non-Javadoc)
     * @see org.openuss.docmanagement.webdav.IOContext#isCompleted()
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
