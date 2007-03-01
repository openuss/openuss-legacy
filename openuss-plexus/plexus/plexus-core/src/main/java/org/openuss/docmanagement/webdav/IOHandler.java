package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.webdav.DavResource;

public interface IOHandler {
	public boolean canExport(ExportContext context, boolean isCollection);
	public boolean canExport(ExportContext context, DavResource resource);
	public boolean canImport(ExportContext context, boolean isCollection);
	public boolean canImport(ExportContext context, DavResource resource);
	public boolean exportContent(ExportContext context, boolean isCollection);
	public boolean exportContent(ExportContext context, DavResource resource);
	public boolean importContent(ExportContext context, boolean isCollection);
	public boolean importContent(ExportContext context, DavResource resource);
	public IOManager getIOManager();
	public String getName();
}
