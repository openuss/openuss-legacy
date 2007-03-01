package org.openuss.docmanagement.webdav;

import java.io.InputStream;

import javax.jcr.Item;

public class ImportContext implements IOContext {

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

	public String getContentLanguage() {
		// TODO
		return null;
	}
	
	public long getContentLength() {
		// TODO
		return 0;
	}

	public String getEncoding() {
		// TODO
		return null;
	}

	public InputStream getInputStream() {
		// TODO
		return null;
	}

	public Item getImportRoot() {
		// TODO
		return null;
	}

	public String getMimeType() {
		// TODO
		return null;
	}

	public long getModificationTime() {
		// TODO
		return 0;
	}

	public Object getProperty(Object propertyName) {
		// TODO
		return null;
	}

	public String getSystemId() {
		// TODO
		return null;
	}
}
