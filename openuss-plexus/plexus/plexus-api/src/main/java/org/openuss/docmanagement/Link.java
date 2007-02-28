package org.openuss.docmanagement;

import java.sql.Timestamp;

public interface Link extends Node{

	public abstract Timestamp getDistributionDate();

	public abstract void setDistributionDate(Timestamp distributionDate);

	public abstract String getMessage();

	public abstract void setMessage(String message);

	public abstract Node getTarget();

	public abstract void setTarget(Node target);

}