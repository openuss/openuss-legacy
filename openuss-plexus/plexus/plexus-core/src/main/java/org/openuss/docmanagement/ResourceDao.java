package org.openuss.docmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

import org.openuss.docmanagement.ExportContext;
import org.openuss.docmanagement.IOManager;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ResourceDao implements Resource {

	private IOManager ioManager;
	private final ResourceLocator locator;
	private final Session session;
	private final javax.jcr.Node node;
	
	public ResourceDao(ResourceLocator locator, Session session) {
		this.locator = locator;
		this.session = session;
		// TODO Node auslesen
		this.node = null;
	}
	
	// TODO Constructor mit Id
	
	public boolean exists() {
		return (node != null);
	}
	
	public boolean isCollection() {
		// TODO
		return false;
	}

	public void spool(HttpServletResponse response) throws IOException {
		// TODO
		ExportContext context = null;
		ioManager.exportContent(context, null);
	}
	
	public void addMember(Resource member) {
		// TODO
	}
	
	public void copy(Resource destination) {
		// TODO
	}
	
	public void move(Resource destination) {
		// TODO
	}
	
	public void removeMember(Resource member) {
		// TODO
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getVisibility() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		
	}
}
