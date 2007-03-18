package org.openuss.docmanagement;

import java.sql.Timestamp;

public interface Resource {

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);
	
	public void setPath(String path);
	
	public String getPath();

	public int getVisibility();

	public void setVisibility(int visibility);

	public String getMessage();

	public void setMessage(String message);
	
	public Timestamp getCreated();
	
	public void setCreated(Timestamp created);
	
}