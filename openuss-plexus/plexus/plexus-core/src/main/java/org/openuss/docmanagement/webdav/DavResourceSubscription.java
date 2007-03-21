package org.openuss.docmanagement.webdav;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.util.Text;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.openuss.docmanagement.DocConstants;

public class DavResourceSubscription extends DavResource {
	public DavResourceSubscription(DavResourceFactory factory, Session session,
			DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public void exportContent(ExportContext context) throws DavException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean importContent(ImportContext context) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean importData(ImportContext context, Node node) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean importProperties(ImportContext context, Node node) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCollection() {
		return true;
	}

	@Override
	public String getCreationDate() throws DavException {
		// HACK
		return System.currentTimeMillis() + "";
	}

	@Override
	public String getDisplayName() throws DavException {
		// HACK ?
		return Text.getName(getLocator().getRepositoryPath());
	}

	@Override
	public List<DavResource> getMembers() throws DavException {
		List<DavResource> members = new LinkedList<DavResource>();

		// add link to distribution folders
		String distributionPath = getLocator().getRepositoryPath() + "/" + DocConstants.DISTRIBUTION;
		DavResourceLocator distributionLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, distributionPath);
		DavResource distributionResource = getFactory().createResource(session, distributionLocator, true);
		if (distributionResource.exists()) {
			members.add(distributionResource);
		}
		
		// look for workingplace
		String workingPlacePath = getLocator().getRepositoryPath() + "/" + DocConstants.WORKINGPLACE;
		DavResourceLocator workingPlaceLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, workingPlacePath);
		DavResource workingPlaceResource = getFactory().createResource(session, workingPlaceLocator, true);
		if (workingPlaceResource.exists()) {
			members.add(workingPlaceResource);
		}
		
		// look for examarea
		String examAreaPath = getLocator().getRepositoryPath() + "/" + DocConstants.EXAMAREA;
		DavResourceLocator examAreaLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, examAreaPath);
		DavResource examAreaResource = getFactory().createResource(session, examAreaLocator, true);
		if (examAreaResource.exists()) {
			members.add(examAreaResource);
		}

		return members;
	}
}
