package org.openuss.docmanagement.webdav;

import java.io.IOException;

import org.openuss.docmanagement.Resource;

/**
 * 
 * @author David Ullrich
 * @version 0.5
 *
 */
public interface IOHandler {
	/**
	 * 
	 * @param context
	 * @param isCollection
	 * @return
	 */
	public boolean canExport(ExportContext context, boolean isCollection);
	
	/**
	 * 
	 * @param context
	 * @param resource
	 * @return
	 */
	public boolean canExport(ExportContext context, Resource resource);
	
	/**
	 * 
	 * @param context
	 * @param isCollection
	 * @return
	 */
	public boolean canImport(ImportContext context, boolean isCollection);
	
	/**
	 * 
	 * @param context
	 * @param resource
	 * @return
	 */
	public boolean canImport(ImportContext context, Resource resource);
	
	/**
	 * 
	 * @param context
	 * @param isCollection
	 * @return
	 * @throws IOException
	 */
	public boolean exportContent(ExportContext context, boolean isCollection) throws IOException;
	
	/**
	 * 
	 * @param context
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public boolean exportContent(ExportContext context, Resource resource) throws IOException;

	/**
	 * @param context
	 * @param isCollection
	 * @return
	 * @throws IOException
	 */
	public boolean importContent(ImportContext context, boolean isCollection) throws IOException;

	/**
	 * @param context
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public boolean importContent(ImportContext context, Resource resource) throws IOException;

	/**
	 * @return
	 */
	public IOManager getIOManager();

	/**
	 * @param manager
	 */
	public void setIOManager(IOManager manager);

	/**
	 * @return
	 */
	public String getName();
}
