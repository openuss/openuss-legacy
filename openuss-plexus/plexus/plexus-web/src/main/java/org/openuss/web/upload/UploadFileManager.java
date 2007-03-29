package org.openuss.web.upload;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Destroy;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;


/**
 * This class manage all temporary uploaded files and secures that they will be deleted 
 * if the user session ends.
 * @author Ingo Dueppe
 */
@Bean (name=Constants.UPLOAD_FILE_MANAGER, scope=Scope.SESSION)
@View
public class UploadFileManager extends BaseBean{
	private static final Logger logger = Logger.getLogger(UploadFileManager.class);

	private static final long serialVersionUID = -842372090394813149L;
	
	private List<UploadedDocument> files;
	
	public UploadFileManager() {
		files = new ArrayList<UploadedDocument>();
	}
	
	@Destroy
	public void destroy() {
		logger.debug("destroying temporary files");
		for (UploadedDocument document : files) {
			document.remove();
		}
		files.clear();
	}
	
	/**
	 * Register a temporary file to be deleted from the repository if the session ends
	 * @param file
	 */
	public void registerDocument(UploadedDocument document) {
		Validate.notNull(document, "Parameter document must not be null!");
		
		logger.debug("Adding document "+document.getFileName()+" to upload manager");
		files.add(document);
	}
	
	/**
	 * Removes the file from the associated repository
	 * @param file
	 */
	public void removeDocument(UploadedDocument document) {
		Validate.notNull(document, "Parameter fileInfo must not be null!");

		files.remove(document);
		document.remove();
	}
	
}
