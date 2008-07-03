package org.openuss.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.braincontest.BrainContestService;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.CommandService;
import org.openuss.commands.DomainCommand;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService2;
import org.openuss.discussion.DiscussionService;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.InstituteService;
import org.openuss.newsletter.NewsletterService;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.registration.RegistrationService;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.wiki.WikiService;
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
	
	private BrainContestService brainContestService;
	
	private DesktopService2 desktopService2;
	
	private WorkspaceService workspaceService;
	
	private DiscussionService discussionService;
	
	private CourseService courseService;
	
	private InstituteService instituteService;
	
	private NewsletterService newsletterService;
	
	private PaperSubmissionService paperSubmissionService;
	
	private RegistrationService registrationService;
	
	private MembershipService membershipService;
	
	private OnlineStatisticService onlineStatisticService;
	
	private WikiService wikiService;
	
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
			
			
		//delete all permissions
		getSecurityService().removeAuthorityFromAllGroups(user);
		getSecurityService().removeAllPermissions(user);
			
		//delete user and user related objects (if existing)
			//remove desktop
			try {
				getDesktopService2().deleteDesktop(user);
			} catch (DesktopException e) {
				logger.error("could not delete user desktop");
				logger.error(e.getMessage());
			}
			//remove user
			getSecurityService().deleteUserObject(user);
		logger.debug("user deleted successfully");
	}

	private void removeDependencies(User user) {
		//remove dependencies to braincontest package
		getBrainContestService().removeUserFromAnswers(user);
		logger.debug("deleted successfully braincontest dependencies");
		//remove dependencies to collaboration package
		getWorkspaceService().removeUserFromWorkspaces(user);
		logger.debug("deleted successfully collaboration dependencies");
		//remove dependencies to discussion package
		getDiscussionService().removeUserFromDiscussions(user);
		logger.debug("deleted successfully discussion dependencies");
		//remove dependencies to lecture package
		getCourseService().removeUserDependencies(user);
		getInstituteService().removeUserDependencies(user);
		logger.debug("deleted successfully lecture dependencies");
		//remove dependencies to newsletter package
		getNewsletterService().removeAllSubscriptions(user);
		logger.debug("deleted successfully newsletter dependencies");
		//remove dependencies to paperSubmission package
		getPaperSubmissionService().removeUserFromExam(user);
		logger.debug("deleted successfully paperSubmission dependencies");
		//remove dependencies to registration package
		getRegistrationService().removeUserRegistrationCodes(user);
		logger.debug("deleted successfully registration dependencies");
		//remove dependencies to security package
		getMembershipService().removeUserDependencies(user);
		logger.debug("deleted successfully security dependencies");
		//remove dependencies to statistics package
		getOnlineStatisticService().removeUserSessions(user);
		logger.debug("deleted successfully statistics dependencies");
		//remove dependencies to wiki package
		getWikiService().removeUserDependencies(user);
		logger.debug("deleted successfully wiki dependencies");
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

	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

	public DesktopService2 getDesktopService2() {
		return desktopService2;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}

	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}

	public void setWorkspaceService(WorkspaceService workspaceService) {
		this.workspaceService = workspaceService;
	}

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public NewsletterService getNewsletterService() {
		return newsletterService;
	}

	public void setNewsletterService(NewsletterService newsletterService) {
		this.newsletterService = newsletterService;
	}

	public PaperSubmissionService getPaperSubmissionService() {
		return paperSubmissionService;
	}

	public void setPaperSubmissionService(
			PaperSubmissionService paperSubmissionService) {
		this.paperSubmissionService = paperSubmissionService;
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public MembershipService getMembershipService() {
		return membershipService;
	}

	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	public OnlineStatisticService getOnlineStatisticService() {
		return onlineStatisticService;
	}

	public void setOnlineStatisticService(
			OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

	public WikiService getWikiService() {
		return wikiService;
	}

	public void setWikiService(WikiService wikiService) {
		this.wikiService = wikiService;
	}
}
