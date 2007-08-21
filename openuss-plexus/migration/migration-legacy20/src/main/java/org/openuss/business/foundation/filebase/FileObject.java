/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  Business Object
 * Copyright:    Copyright (c) B. Lofi Dewanto
 * Company:      University of Muenster
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
package org.openuss.business.foundation.filebase;

/**
 * The file object.
 *
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FileObject implements java.io.Serializable {
    // Private state object
    private byte[] data = null;

    /**
     * Constructor with the data to save.
     *
     * @param    the data to be saved.
     */
    public FileObject(byte[] data) {
        this.data = data;
    }

    /**
     * Gets the data of the file.
     *
     * @return    the data of the file.
     */
    public byte[] getData() {
        return data;
    }
}