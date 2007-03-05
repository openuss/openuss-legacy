package org.openuss.docmanagement;

import java.io.InputStream;
import java.sql.Timestamp;

import javax.jcr.Session;

public class BigFileDao extends FileDao implements BigFile {
	public BigFileDao(ResourceLocator locator, Session session) {
		// TODO
		super(locator, session);
	}

	public InputStream getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFile(InputStream file) {
		// TODO Auto-generated method stub

	}

	public Timestamp getDistributionTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getLastModification() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMimeType() {
		// TODO Auto-generated method stub
		return null;
	}

	public File getPredecessor() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setDistributionTime(Timestamp distributionTime) {
		// TODO Auto-generated method stub

	}

	public void setLastModification(Timestamp lastModification) {
		// TODO Auto-generated method stub

	}

	public void setMimeType(String mimeType) {
		// TODO Auto-generated method stub

	}

	public void setPredecessor(File predecessor) {
		// TODO Auto-generated method stub

	}

	public void setSize(long size) {
		// TODO Auto-generated method stub

	}

	public void setVersion(int version) {
		// TODO Auto-generated method stub

	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getVisibility() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	public void setMessage(String message) {
		// TODO Auto-generated method stub

	}

	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub

	}

}
