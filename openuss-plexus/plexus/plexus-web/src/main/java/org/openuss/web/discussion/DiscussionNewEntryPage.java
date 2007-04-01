package org.openuss.web.discussion;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.ForumInfo;
import org.openuss.repository.RepositoryFile;
import org.openuss.web.Constants;

@Bean(name = "views$secured$discussion$discussionnew", scope = Scope.REQUEST)
@View
public class DiscussionNewEntryPage extends AbstractDiscussionPage{
	private static final Logger logger = Logger.getLogger(DiscussionNewEntryPage.class);

	public String send(){
		logger.debug("new document saved");
		
		postInfo.setCreated(new Date(System.currentTimeMillis()));
		String ip = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
		logger.debug("Client IP = "+ ip);
		postInfo.setIp(ip);
		postInfo.setIsEdited(postInfo.getId()!=null);
		postInfo.setLastModification(new Date(System.currentTimeMillis()));
		postInfo.setSubmitter(user.getUsername());
		postInfo.setSubmitterId(user.getId());
		discussionService.createTopic(postInfo, getForum());		
		
		return Constants.SUCCESS;
	}
	
	public String addFormula(){
		logger.debug("formula added");
		return Constants.SUCCESS;		
	}

	public String removeAttachment(){
		return Constants.SUCCESS;		
	}

}