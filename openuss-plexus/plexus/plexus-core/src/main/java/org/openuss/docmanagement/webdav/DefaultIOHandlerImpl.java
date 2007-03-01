package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.webdav.DavResource;

public class DefaultIOHandlerImpl implements IOHandler {

	public boolean canExport(ExportContext context, boolean isCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canExport(ExportContext context, DavResource resource) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canImport(ExportContext context, boolean isCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canImport(ExportContext context, DavResource resource) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean exportContent(ExportContext context, boolean isCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean exportContent(ExportContext context, DavResource resource) {
		// TODO Auto-generated method stub
		return false;
	}

	public IOManager getIOManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean importContent(ExportContext context, boolean isCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean importContent(ExportContext context, DavResource resource) {
		// TODO Auto-generated method stub
		return false;
	}

}
