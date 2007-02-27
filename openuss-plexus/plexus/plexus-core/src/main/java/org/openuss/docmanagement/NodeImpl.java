package org.openuss.docmanagement;

public class NodeImpl implements Node{
	
	public String name;
	
	public int visibility;
	
	public String id;

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
	
	
	
}