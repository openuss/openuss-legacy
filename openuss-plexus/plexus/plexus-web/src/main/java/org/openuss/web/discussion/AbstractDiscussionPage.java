package org.openuss.web.discussion;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;

public class AbstractDiscussionPage extends AbstractCoursePage{

	@Property(value = "#{"+Constants.DISCUSSION_DISCUSSIONENTRY+"}")
	protected PostInfo postInfo;

	@Property(value = "#{"+Constants.DISCUSSION_TOPIC+"}")
	protected TopicInfo topic;
	
	@Property(value = "#{discussionService}")
	protected DiscussionService discussionService;
	
	public ForumInfo forum;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addDiscussionCrumb();
	}

	private void addDiscussionCrumb() {
		BreadCrumb discussionMain = new BreadCrumb();
		discussionMain.setName(i18n("course_command_discussion"));
		discussionMain.setHint(i18n("course_command_discussion"));
		discussionMain.setLink(PageLinks.DISCUSSION_MAIN);
		discussionMain.addParameter("course",courseInfo.getId());
		crumbs.add(discussionMain);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	public PostInfo getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(PostInfo postInfo) {
		this.postInfo = postInfo;
	}

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}
	
	public ForumInfo getForum() {
		this.forum = discussionService.getForum(courseInfo);
		return this.forum;
	}

	public void setForum(ForumInfo forum) {
		this.forum = forum;
	}

	public TopicInfo getTopic() {
		return topic;
	}

	public void setTopic(TopicInfo topic) {
		this.topic = topic;
	}
}