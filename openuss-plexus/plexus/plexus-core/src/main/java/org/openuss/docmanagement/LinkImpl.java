package org.openuss.docmanagement;

import java.sql.Timestamp;

public class LinkImpl extends ResourceImpl implements Link {
	
	public Resource target;
	
	public Timestamp distributionDate;
	
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
	 * @see org.openuss.docmanagement.Link#getTarget()
	 */
	public Resource getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Link#setTarget(org.openuss.docmanagement.Node)
	 */
	public void setTarget(Resource target) {
		this.target = target;
	}

}