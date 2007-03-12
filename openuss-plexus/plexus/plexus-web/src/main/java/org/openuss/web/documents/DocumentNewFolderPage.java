package org.openuss.web.documents;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.Folder;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$newfolder", scope = Scope.REQUEST)
@View
public class DocumentNewFolderPage{
	private static final Logger logger = Logger.getLogger(DocumentNewFolderPage.class);
	
	@Property(value = "#{newFolder}")
	private Folder newFolder;


	public String save(){
		logger.debug("new folder saved");
		return Constants.SUCCESS;
	}


	public Folder getNewFolder() {
		return newFolder;
	}


	public void setNewFolder(Folder newFolder) {
		this.newFolder = newFolder;
	}
	
} 