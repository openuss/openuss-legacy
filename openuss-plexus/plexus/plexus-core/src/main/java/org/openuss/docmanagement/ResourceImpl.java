package org.openuss.docmanagement;

import java.sql.Timestamp;

public class ResourceImpl implements Resource {
	
	public String name;
	
	public String path;
	
	public int visibility;
	
	public String id;
	
	public String message;
	
	public Timestamp created;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Node#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Node#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Node#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Node#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Node#getVisibility()
	 */
	public int getVisibility() {
		return visibility;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Node#setVisibility(int)
	 */
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path=path;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	
	
}