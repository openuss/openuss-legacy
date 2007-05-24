package org.openuss.web.discussion;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

public class AbstractDiscussionPage extends AbstractEnrollmentPage{

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
		this.forum = discussionService.getForum(enrollment);
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