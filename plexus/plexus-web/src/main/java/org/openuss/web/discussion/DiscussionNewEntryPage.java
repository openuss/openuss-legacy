package org.openuss.web.discussion;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.repository.RepositoryFile;
import org.openuss.web.Constants;

@Bean(name = "discussionNew", scope = Scope.REQUEST)
@View
public class DiscussionNewEntryPage{
	private static final Logger logger = Logger.getLogger(DiscussionNewEntryPage.class);
	
	private RepositoryFile attachment;
	
	private String text;
	
	private String title;
	
	


	public String send(){
		logger.debug("new document saved");
		return Constants.SUCCESS;
	}
	
	public String addFormula(){
		logger.debug("formula added");
		return Constants.SUCCESS;		
	}

	public String removeAttachment(){
		logger.debug("formula added");
		return Constants.SUCCESS;		
	}

	public RepositoryFile getAttachment() {
		return attachment;
	}

	public void setAttachment(RepositoryFile attachment) {
		if (this.attachment==null)
		this.attachment = attachment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}