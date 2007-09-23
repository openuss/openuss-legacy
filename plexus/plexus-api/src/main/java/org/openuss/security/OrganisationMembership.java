package org.openuss.security;

import org.openuss.lecture.Organisation;

public class OrganisationMembership implements MembershipParameters {

	private Organisation organisation;
	
	public OrganisationMembership(Organisation organisation) {
		this.organisation = organisation;
	}
	
	public String getName() {
		return organisation.getName();
	}

	public String getShortcut() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
