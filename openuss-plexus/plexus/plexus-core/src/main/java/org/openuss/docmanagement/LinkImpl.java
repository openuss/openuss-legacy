package org.openuss.docmanagement;

import java.sql.Timestamp;

public class LinkImpl implements Link{
	
	public Node target;
	
	public Timestamp distributionDate;
	
	public String message;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#getDistributionDate()
	 */
	public Timestamp getDistributionDate() {
		return distributionDate;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#setDistributionDate(java.sql.Timestamp)
	 */
	public void setDistributionDate(Timestamp distributionDate) {
		this.distributionDate = distributionDate;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#getTarget()
	 */
	public Node getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#setTarget(org.openuss.docmanagement.Node)
	 */
	public void setTarget(Node target) {
		this.target = target;
	}
		
	
}