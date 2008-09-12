package org.openuss.security;


import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.CommandService;
import org.openuss.commands.DomainCommand;
import org.openuss.discussion.DiscussionService;
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
	
	private NewsletterService newsletterService;
	
	public void execute() throws Exception {
		if (getDomainObject() != null && getDomainObject().getId()!= null) {
			User user = getSecurityService().getUserObject(getSecurityService().getUser(getDomainObject().getId()));
			if (user != null) {
				deleteUser(user);
			}
		}
	}
	
	protected void deleteUser(User user){
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
}