package org.openuss.docmanagement;

import java.io.InputStream;

public class BigFile extends File{
	
	public InputStream file;

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}
}