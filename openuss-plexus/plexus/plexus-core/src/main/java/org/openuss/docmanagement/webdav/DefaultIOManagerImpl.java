package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jackrabbit.webdav.DavResource;


/**
 * Default implementation of interface IOManager
 * @author David Ullrich
 * @version 0.9
 */
public class DefaultIOManagerImpl implements IOManager {

	// internal list of IOHandlers
	private final List<IOHandler> ioHandlerList;
	
	/**
	 * Constructor
	 */
	public DefaultIOManagerImpl() {
		ioHandlerList = new ArrayList<IOHandler>();
	}

	/**
	 * @see org.openuss.docmanagement.webdav.IOManager#addIOHandler(IOHandler)
	 */
	public void addIOHandler(IOHandler ioHandler) throws IllegalArgumentException {
		// test parameters
		if (ioHandler == null) {
			throw new IllegalArgumentException("The parameter ioHandler must not be null!");
		}
		
		// add handler, if not in list
		if (ioHandlerList.contains(ioHandler)) {
			// set the IOManager-property to this instance and append it to list of handlers
			ioHandler.setIOManager(this);
			ioHandlerList.add(ioHandler);
		}
	}

	/**
	 * @see org.openuss.docmanagement.webdav.IOManager#exportContent(ExportContext, boolean)
	 */
	public boolean exportContent(ExportContext context, boolean isCollection) throws IOException {
		// internal variable for export status
		boolean success = false;
		
		if (context != null) {
			// get an iterator for the list of IOHandlers
			Iterator<IOHandler> iterator = ioHandlerList.iterator();
			IOHandler handler;
			
			// try to export for every handler, if not already successful
			while (!success && iterator.hasNext()) {
				handler = iterator.next();
				// check if handler can handle export
				if (handler.canExport(context, isCollection)) {
					// export and store export status
					success = handler.exportContent(context, isCollection);
				}
			}
			
			// export successful or list of handlers iterated -> terminate context operation
			context.informCompleted(success);
		}
		
		// return final export status
		return success;
	}

	/**
	 * @see org.openuss.docmanagement.webdav.IOManager#exportContent(ExportContext, DavResource)
	 */
	public boolean exportContent(ExportContext context, DavResource resource) throws IOException {
		// internal variable for export status
		boolean success = false;
		
		if (context != null && resource != null) {
			// get an iterator for the list of IOHandlers
			Iterator<IOHandler> iterator = ioHandlerList.iterator();
			IOHandler handler;
			
			// try to export for every handler, if not already successful
			while (!success && iterator.hasNext()) {
				handler = iterator.next();
				// check if handler can handle export
				if (handler.canExport(context, resource)) {
					// export and store export status
					success = handler.exportContent(context, resource);
				}
			}
			
			// export successful or list of handlers iterated -> terminate context operation
			context.informCompleted(success);
		}
		
		// return final export status
		return success;
	}

	/**
	 * @see org.openuss.docmanagement.webdav.IOManager#getIOHandlers()
	 */
	public IOHandler[] getIOHandlers() {
		// return the list of handlers as an array
		return (IOHandler[])ioHandlerList.toArray();
	}

	/**
	 * @see org.openuss.docmanagement.webdav.IOManager#importContent(ImportContext, boolean)
	 */
	public boolean importContent(ImportContext context, boolean isCollection) throws IOException {
		// internal variable for import status
		boolean success = false;
		
		if (context != null) {
			// get an iterator for the list of IOHandlers
			Iterator<IOHandler> iterator = ioHandlerList.iterator();
			IOHandler handler;
			
			// try to export for every handler, if not already successful
			while (!success && iterator.hasNext()) {
				handler = iterator.next();
				// check if handler can handle import
				if (handler.canImport(context, isCollection)) {
					// export and store import status
					success = handler.importContent(context, isCollection);
				}
			}
			
			// import successful or list of handlers iterated -> terminate context operation
			context.informCompleted(success);
		}
		
		// return final import status
		return success;
	}

	/**
	 * @see org.openuss.docmanagement.webdav.IOManager#importContent(ImportContext, DavResource)
	 */
	public boolean importContent(ImportContext context, DavResource resource) throws IOException {
		// internal variable for import status
		boolean success = false;
		
		if (context != null) {
			// get an iterator for the list of IOHandlers
			Iterator<IOHandler> iterator = ioHandlerList.iterator();
			IOHandler handler;
			
			// try to export for every handler, if not already successful
			while (!success && iterator.hasNext()) {
				handler = iterator.next();
				// check if handler can handle import
				if (handler.canImport(context, resource)) {
					// export and store import status
					success = handler.importContent(context, resource);
				}
			}
			
			// import successful or list of handlers iterated -> terminate context operation
			context.informCompleted(success);
		}
		
		// return final import status
		return success;
	}
}
