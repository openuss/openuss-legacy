package org.openuss.docmanagement;

import java.sql.Timestamp;

public interface Link extends Resource{

	public Timestamp getDistributionDate();

	public void setDistributionDate(Timestamp distributionDate);

	public Resource getTarget();

	public void setTarget(Resource target);
	
	public String[] getViewed();
	
	public void setViewed(String[] viewed);

}