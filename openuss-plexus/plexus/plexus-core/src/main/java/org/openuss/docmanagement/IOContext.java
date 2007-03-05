package org.openuss.docmanagement;

/**
 * @author David Ullrich
 * @version 0.8
 */
public interface IOContext {
    /**
     * @return
     */
    public boolean hasStream();

    /**
     * @param success
     */
    public void informCompleted(boolean success);

    /**
     * @return
     */
    public boolean isCompleted();
}
