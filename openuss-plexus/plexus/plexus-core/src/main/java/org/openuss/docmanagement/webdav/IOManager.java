package org.openuss.docmanagement.webdav;

import org.apache.jackrabbit.webdav.DavResource;

public interface IOManager {
	public void addIOHandler(IOHandler ioHandler);
	public IOHandler[] getIOHandlers();
	public boolean exportContent(ExportContext context, boolean isCollection);
	public boolean exportContent(ExportContext context, DavResource resource);
	public boolean importContent(ExportContext context, boolean isCollection);
	public boolean importContent(ExportContext context, DavResource resource);
}
