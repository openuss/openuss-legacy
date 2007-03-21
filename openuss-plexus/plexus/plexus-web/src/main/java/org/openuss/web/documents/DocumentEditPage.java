package org.openuss.web.documents;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.repository.RepositoryFile;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$documentnew", scope = Scope.REQUEST)
@View
public class DocumentEditPage{
	private static final Logger logger = Logger.getLogger(DocumentEditPage.class);
	
	@Property(value = "#{newDocument}")
	private RepositoryFile newDocument;


	public String save(){
		logger.debug("new document saved");
		return Constants.SUCCESS;
	}


	public RepositoryFile getNewDocument() {
		return newDocument;
	}


	public void setNewDocument(RepositoryFile newDocument) {
		this.newDocument = newDocument;
	}
	
}