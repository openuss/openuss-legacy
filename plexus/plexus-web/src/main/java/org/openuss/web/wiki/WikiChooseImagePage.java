package org.openuss.web.wiki;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIInput;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

/**
 * Backing Bean for wikichooseimage.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikichooseimage", scope = Scope.REQUEST)
@View
public class WikiChooseImagePage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiChooseImagePage.class);
	
	private final WikiImageProvider data = new WikiImageProvider();
	
	private UIInput fileUpload;
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	/**
	 * Saves an Image and returns the Wiki Choose Image Page.
	 * @return Wiki Choose Image Page.
	 * @throws IOException Signals that an I/O exception of some sort has occurred.
	 */
	public String save() throws IOException {		
		final FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		
		if (fileInfo != null) {
			LOGGER.debug("Saving Image " + fileInfo.getFileName() + ".");
			getWikiService().saveImage(this.siteVersionInfo, fileInfo);		
		}
		
		return Constants.WIKI_CHOOSE_IMAGE_PAGE;
	}
	
	/**
	 * Deletes an Image and returns the Wiki Delete Image Page. 
	 * @return Wiki Delete Image Page.
	 */
	public String deleteImage() {
		FolderEntryInfo entry = data.getRowData();
		setSessionBean(Constants.WIKI_IMAGE, entry);
		
		return Constants.WIKI_REMOVE_IMAGE_PAGE;
	}
	
	/**
	 * Data Provider for Images.
	 * @author Projektseminar WS 07/08, Team Collaboration
	 *
	 */
	private class WikiImageProvider extends AbstractPagedTable<FolderEntryInfo> {
		private static final long serialVersionUID = -1886479086904372812L;
		
		private DataPage<FolderEntryInfo> page;
		
		@Override
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				final List<FolderEntryInfo> entries = loadImages();
				page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
			}
			
			return page;
		}
	}

	@SuppressWarnings("unchecked") 
	private List<FolderEntryInfo> loadImages() {
		return getWikiService().findImagesByDomainId(courseInfo.getId());
	}
	
	public WikiImageProvider getImagesData() {
		return data;
	}
	
	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public UIInput getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(UIInput fileUpload) {
		this.fileUpload = fileUpload;
	}
	
}