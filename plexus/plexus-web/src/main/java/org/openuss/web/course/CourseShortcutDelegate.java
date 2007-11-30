package org.openuss.web.course;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.course.newsletter.CourseNewsletterService;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.discussion.DiscussionService;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.User;
import org.openuss.security.UserProfile;
import org.openuss.web.Constants;

/**
 * This class is responsible for bookmarks (aka shortcuts) of courses.
 * It must be the only entry point for any UI operations handling these bookmarks.
 * 
 * @author Marius Lietmeyer
 * @author Stefan Weber
 * @author Philipp Hagemeister
 */
@Bean(name = "courseShortcutDelegate", scope = Scope.REQUEST)
public class CourseShortcutDelegate extends BaseBean {

	private static final Logger logger = Logger.getLogger(CourseShortcutDelegate.class);

	@Property(value = "#{courseNewsletterService}")
	protected CourseNewsletterService courseNewsletterService;

	@Property(value = "#{discussionService}")
	protected DiscussionService discussionService;

	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * 
	 * @return Outcome as defined in {@link Constants}.
	 */
	public String shortcutCourse(DesktopService2 desktopService, DesktopInfo desktopInfo, CourseInfo courseInfo, User user) {
		try {
			desktopService.linkCourse(desktopInfo.getId(), courseInfo.getId());
			
			// user.getProfile() is deprecated, but there is no replacement as of now.
			UserProfile profile = user.getProfile();
			if (profile.isNewsletterSelected()) {
				logger.debug("Newsletter isSelected = true");
				courseNewsletterService.subscribe(courseInfo, user);
				logger.debug("Newsletter subcribed");
			}
			
			if (profile.isDiscussionSelected()) {
				discussionService.addForumWatch(discussionService.getForum(courseInfo));
			}
			
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	/**
	 * Removes a bookmark.
	 * 
	 * @return Outcome as defined in {@link Constants}.
	 */
	public String removeCourseShortcut(DesktopService2 desktopService, DesktopInfo desktopInfo, CourseInfo courseInfo, User user) {
		try {
			courseNewsletterService.unsubscribe(courseInfo, user);
			discussionService.removeForumWatch(discussionService.getForum(courseInfo));
			desktopService.unlinkCourse(desktopInfo.getId(), courseInfo.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	public CourseNewsletterService getCourseNewsletterService() {
		return courseNewsletterService;
	}

	public void setCourseNewsletterService(CourseNewsletterService courseNewsletterService) {
		this.courseNewsletterService = courseNewsletterService;
	}

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}
}
