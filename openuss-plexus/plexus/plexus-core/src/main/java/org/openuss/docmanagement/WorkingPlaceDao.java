package org.openuss.docmanagement;

import java.util.Collection;

import javax.jcr.Session;

import org.openuss.lecture.Enrollment;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class WorkingPlaceDao extends FolderDao implements WorkingPlace {
	public WorkingPlaceDao(ResourceLocator locator, Session session) {
		// TODO
		super(locator, session);
	}

	public Enrollment getEnrollment() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEnrollment(Enrollment enrollment) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Resource> getSubnodes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSubnodes(Collection<Resource> subnodes) {
		// TODO Auto-generated method stub
		
	}

}
