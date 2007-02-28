package org.openuss.docmanagement;

import java.io.InputStream;

public interface BigFile extends File {

	public abstract InputStream getFile();

	public abstract void setFile(InputStream file);

}