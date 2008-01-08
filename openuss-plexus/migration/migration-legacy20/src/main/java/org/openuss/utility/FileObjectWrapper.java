/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  Utility Class
 * Copyright:    Copyright (c) B. Lofi Dewanto
 * Company:      University of Muenster
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
package org.openuss.utility;

/**
 * The file object.
 * This class represents a file.
 *
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
public class FileObjectWrapper implements java.io.Serializable {
	
    private static final long serialVersionUID = -3643268306174414197L;
    
	// Private state object
    private byte[] data = null;

    /**
     * Constructor with the data to save.
     *
     * @param    the data to be saved.
     */
    public FileObjectWrapper(byte[] data) {
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