package org.openuss.security;


import java.util.Arrays;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.CommandService;
import org.openuss.commands.DomainCommand;
import org.openuss.discussion.DiscussionService;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.newsletter.NewsletterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * UserDeleteCommand
 * 
 * @author Sebastian Roekens
 */
public class UserDeleteCommand extends AbstractDomainCommand implements DomainCommand, ApplicationContextAware {

	private static final Logger logger = Logger.getLogger(UserDeleteCommand.class);
	
	private CommandService commandService;

	private ApplicationContext applicationContext;
	
	private SecurityService securityService;
	
	private DiscussionService discussionService;
	
	private DocumentService documentService;
	
	private NewsletterService newsletterService;
	
	public void execute() throws Exception {
		if (getDomainObject() != null && getDomainObject().getId()!= null) {
			User user = getSecurityService().getUserObject(getSecurityService().getUser(getDomainObject().getId()));
			if (user != null) {
				deleteUser(user);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void deleteUser(User user){
		//remove user image
		Long userImageId = user.getImageId();
		FileInfo fi = new FileInfo();
		fi.setId(userImageId);
		try {
			getDocumentService().removeFileEntries(new LinkedList(Arrays.asList(new FileInfo[]{fi})));
		} catch (DocumentApplicationException e) {
			logger.error("Could not delete User Image: " + e.getMessage());
		}
		//link all foreign keys to user unknown
		removeDependencies(user);
		//remove personal information		
		getSecurityService().removePersonalInformation(user);
	}

	private void removeDependencies(User user) {
		//remove dependencies to discussion package
		getDiscussionService().removeUserFromDiscussions(user);
		logger.debug("deleted successfully discussion dependencies");
		//remove dependencies to newsletter package
		getNewsletterService().removeAllSubscriptions(user);
		logger.debug("deleted successfully newsletter dependencies");
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public CommandService getCommandService() {
		return commandService;
	}

	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}

	public NewsletterService getNewsletterService() {
		return newsletterService;
	}

	public void setNewsletterService(NewsletterService newsletterService) {
		this.newsletterService = newsletterService;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
}
