package org.openuss.docmanagement.webdav;

import java.io.IOException;

import org.apache.jackrabbit.webdav.DavResource;

/**
 * 
 * @author David Ullrich
 * @version 0.8
 */
public interface IOManager {
	/**
	 * Appends the specified IOHandler to the list of managed handlers.
	 * @param ioHandler IOHandler to append to the list.
	 * @throws IllegalArgumentException
	 */
	public void addIOHandler(IOHandler ioHandler) throws IllegalArgumentException;

	/**
	 * Returns an array containing all of the IOHandlers in the correct order.
	 * @return an array containing all of the IOHandlers in the correct order.
	 */
	public IOHandler[] getIOHandlers();

	/**
	 * 
	 * @param context
	 * @param isCollection
	 * @return
	 */
	public boolean exportContent(ExportContext context, boolean isCollection) throws IOException;
	
	/**
	 * 
	 * @param context
	 * @param resource
	 * @return
	 */
	public boolean exportContent(ExportContext context, DavResource resource) throws IOException;
	
	/**
	 * 
	 * @param context
	 * @param isCollection
	 * @return
	 */
	public boolean importContent(ImportContext context, boolean isCollection) throws IOException;
	
	/**
	 * 
	 * @param context
	 * @param resource
	 * @return
	 */
	public boolean importContent(ImportContext context, DavResource resource) throws IOException;
}
