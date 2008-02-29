package org.openuss.web.documents;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$document", scope = Scope.REQUEST)
@View
public class DocumentPage extends AbstractDocumentPage {

	private FileInfo entry = new FileInfo();
	

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		entry = documentService.getFileEntry(entry.getId(), false);
		if (entry==null){
			addError(Constants.FILE_NOT_FOUND);
			redirect(i18n(Constants.COURSE_PAGE));
		}
		setCurrentFolder(documentService.getFolder(entry));
		//FIXME isFolderOfDomainObject is not working
//		if (!documentService.isFolderOfDomainObejct(currentFolder, courseInfo)){
//			addError(Constants.FILE_NOT_FOUND);
//			redirect(i18n(Constants.COURSE_PAGE));		
//		}
	}


	public FileInfo getEntry() {
		return entry;
	}


	public void setEntry(FileInfo entry) {
		this.entry = entry;
	}
}