/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  Business Object
 * Copyright:    Copyright (c) B. Lofi Dewanto
 * Company:      University of Muenster
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
package org.openuss.business.extension.lecture.file.filedescription.lecturefilebase;

/**
 * The file object.
 *
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
public class LectureFileObject implements java.io.Serializable {
    
	private static final long serialVersionUID = 5552674523494984965L;
	
	// Private state object
    private byte[] data = null;

    /**
     * Constructor with the data to save.
     *
     * @param    the data to be saved.
     */
    public LectureFileObject(byte[] data) {
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