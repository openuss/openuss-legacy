package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface IOContext {
    /**
     * Checks, if context owns a stream.
     * @return True, if context owns a stream.
     */
    public boolean hasStream();

    /**
     * Ends transaction with transaction success.
     * @param success Indicates a successful completion of data transfer.
     */
    public void informCompleted(boolean success);

    /**
     * Checks, if context transaction is completed.
     * @return True, if transaction is completed.
     */
    public boolean isCompleted();
}
