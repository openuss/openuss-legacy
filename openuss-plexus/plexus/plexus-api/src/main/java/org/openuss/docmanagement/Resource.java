package org.openuss.docmanagement;

public interface Resource {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract int getVisibility();

	public abstract void setVisibility(int visibility);

	public abstract String getMessage();

	public abstract void setMessage(String message);
	
}