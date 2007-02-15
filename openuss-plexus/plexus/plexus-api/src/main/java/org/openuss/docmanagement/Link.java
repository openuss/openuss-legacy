package org.openuss.docmanagement;

import java.sql.Timestamp;

public class Link{
	
	public Node target;
	
	public Timestamp distributionDate;
	
	public String message;

	public Timestamp getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Timestamp distributionDate) {
		this.distributionDate = distributionDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}
		
	
}