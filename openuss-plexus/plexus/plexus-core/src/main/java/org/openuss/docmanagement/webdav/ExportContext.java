package org.openuss.docmanagement.webdav;

import java.io.OutputStream;

import javax.jcr.Item;

public class ExportContext implements IOContext {

	public boolean hasStream() {
		// TODO Auto-generated method stub
		return false;
	}

	public void informCompleted(boolean success) {
		// TODO Auto-generated method stub

	}

	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	public Item getExportRoot() {
		// TODO
		return null;
	}
	
	public OutputStream getOutputStream() {
		// TODO
		return null;
	}
	
	public void setContentLanguage(String contentLanguage) {
		// TODO
	}
	
	public void setContentLength(long contentLength) {
		// TODO
	}
	
	public void setContentType(String mimeType, String encoding) {
		// TODO
	}
	
	public void setCreationTime(long creationTime) {
		// TODO
	}
	
	public void setETag(String etag) {
		// TODO
	}
	
	public void setModificationTime(long modificationTime) {
		// TODO
	}
	
	public void setProperty(Object propertyName, Object properyValue) {
		// TODO
	}
}
