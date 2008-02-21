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


@Bean(name = "views$secured$wiki$wikichooseimage", scope = Scope.REQUEST)
@View
public class WikiChooseImagePage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiChooseImagePage.class);
	
	private WikiImageProvider data = new WikiImageProvider();
	
	private UIInput fileUpload;
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
		
	public String save() {
		FileInfo fileInfo;
		try {
			fileInfo = uploadFileManager.lastUploadAsFileInfo();
			
			getWikiService().saveImage(this.siteVersionInfo, fileInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "wiki_choose_image";
	}
	
	public String deleteImage() {
		FolderEntryInfo entry = data.getRowData();
		setSessionBean(Constants.WIKI_IMAGE, entry);
		
		return "wiki_remove_image";
	}
	
	private class WikiImageProvider extends AbstractPagedTable<FolderEntryInfo> {

		private static final long serialVersionUID = -1886479086904372812L;
		
		private DataPage<FolderEntryInfo> page;
		
		@Override
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<FolderEntryInfo> entries = loadImages();
				//sort(entries);
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
	
	private String getExtension(String fileName) {
		if (fileName != null) {
			return fileName.substring(fileName.lastIndexOf('.')+1).trim();
		} else {
			return "";
		}
	}
	
}