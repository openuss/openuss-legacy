package org.openuss.lecture;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.lecture.Organisation
 * @author Ingo Düppe
 */
public abstract class OrganisationImpl extends OrganisationBase implements Organisation {
	
	/** The serial version UID of this class. Needed for serialization.	 */
	private static final long serialVersionUID = 391078913590423980L;
	
	public String getShortName() {
		if (StringUtils.isBlank(super.getShortName())) {
			return super.getName();
		} else {
			return super.getShortName();
		}
	}

}