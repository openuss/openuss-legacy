package org.openuss.web.documents; 

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentService;
import org.openuss.documents.Folder;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$documents$documents", scope = Scope.REQUEST)
@View
public class DocumentsMainPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(DocumentsMainPage.class);
	
	private DocumentDataProvider data = new DocumentDataProvider();
	
	@Property(value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value="#{sessionScope.folder}")
	private Folder currentFolder;
	
	private class DocumentDataProvider extends AbstractPagedTable<FolderEntryInfo> {

		private DataPage<FolderEntryInfo> page; 
		
		@Override 
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<FolderEntryInfo> entries = documentService.getFolderEntries(enrollment, currentFolder);
				page = new DataPage<FolderEntryInfo>(entries.size(),0,entries);
			}
			return page;
		}
	}
	
	public String download(){
		logger.debug("document download");
		return Constants.SUCCESS;
	}
	
	public String delete(){
		logger.debug("document deleted");
		return Constants.SUCCESS;
	}

	public DocumentDataProvider getData() {
		return data;
	}


	public void setData(DocumentDataProvider data) {
		this.data = data;
	}

	public String getPath() {
		//return path;
		return "> KLR > Veranstaltung > Folien";
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public Folder getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(Folder currentFolder) {
		this.currentFolder = currentFolder;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	
}