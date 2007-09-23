package org.openuss.web.documents;

import java.io.Serializable;
import java.util.Date;

public class DocumentTableEntry implements Serializable {

	private static final long serialVersionUID = -3053386731692416783L;

	public String filename;

	public String mimeType;

	public String size;

	public Date lastChange;

	public Date created;

	public Boolean viewed;

	public Boolean marked;

	public DocumentTableEntry(String filename, String mimeType, String size, Date lastChange, Date created,
			boolean viewed) {
		this.filename = filename;
		this.mimeType = mimeType;
		this.size = size;
		this.lastChange = lastChange;
		this.created = created;
		this.viewed = viewed;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
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