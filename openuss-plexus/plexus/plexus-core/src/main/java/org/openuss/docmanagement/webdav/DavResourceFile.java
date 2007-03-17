package org.openuss.docmanagement.webdav;

import java.io.IOException;

import javax.jcr.Node;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceFile extends DavResource {
	public DavResourceFile(Node representedNode) {
		super(representedNode);		
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public void exportContent(ExportContext context) throws IOException {
		if (!canExport(context)) {
			// TODO exception message
			throw new IOException();
		}
		
		// TODO
		
		context.informCompleted(true);
	}

	@Override
	public void importContent(ImportContext context) throws IOException {
		if (!canImport(context)) {
			// TODO exception message
			throw new IOException();
		}
		
		// TODO
		
		context.informCompleted(true);
	}
}
