package org.openuss.docmanagement;

import java.io.InputStream;

public interface BigFile extends File {

	public InputStream getFile();

	public void setFile(InputStream file);

}