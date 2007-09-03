/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  Business Object
 * Copyright:    Copyright (c) B. Lofi Dewanto
 * Company:      University of Muenster
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
package org.openuss.business.extension.discussion.discussionfiledescription.discussionfilebase;

/**
 * The file object.
 *
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
public class DiscussionFileObject implements java.io.Serializable {
	
    private static final long serialVersionUID = 1262478207336409599L;
    
	// Private state object
    private byte[] data = null;

    /**
     * Constructor with the data to save.
     *
     * @param    the data to be saved.
     */
    public DiscussionFileObject(byte[] data) {
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