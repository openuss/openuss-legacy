package org.openuss.docmanagement.webdav;

import java.io.IOException;

import javax.jcr.Node;

import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceFile extends DavResource {
	public DavResourceFile(DavResourceFactory factory, DavResourceLocator locator, Node representedNode) {
		super(factory, locator, representedNode);		
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
