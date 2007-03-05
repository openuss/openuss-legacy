package org.openuss.docmanagement;

import java.sql.Timestamp;

public interface Link extends Resource{

	public abstract Timestamp getDistributionDate();

	public abstract void setDistributionDate(Timestamp distributionDate);

	public abstract Resource getTarget();

	public abstract void setTarget(Resource target);

}