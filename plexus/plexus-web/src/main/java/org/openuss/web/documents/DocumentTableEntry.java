package org.openuss.web.documents;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Ingo Dueppe
 */
public class DocumentTableEntry implements Serializable {

	private static final long serialVersionUID = -3053386731692416783L;

	public String fileName;

	public String mimeType;

	public String size;

	public Date lastChange;

	public Date created;

	public Boolean viewed;

	public Boolean marked;

	public DocumentTableEntry(String filename, String mimeType, String size, Date lastChange, Date created, boolean viewed) {
		this.fileName = filename;
		this.mimeType = mimeType;
		this.size = size;
		this.lastChange = new Date(lastChange.getTime());
		this.created = new Date(created.getTime());
		this.viewed = viewed;
	}

	public Date getCreated() {
		return new Date(created.getTime());
	}

	public void setCreated(Date created) {
		this.created = new Date(created.getTime());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public Date getLastChange() {
		return new Date(lastChange.getTime());
	}

	public void setLastChange(Date lastChange) {
		this.lastChange = new Date(lastChange.getTime());
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}