package org.openuss.web.discussion;

import org.apache.shale.tiger.managed.Property;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

public class AbstractDiscussionPage extends AbstractEnrollmentPage{

	@Property(value = "#{discussion_discussionentry}")
	protected PostInfo postInfo;
	
	@Property(value = "#{discussionService}")
	protected DiscussionService discussionService;
	
	public ForumInfo forum;
	
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
		this.forum = discussionService.getForum(enrollment.getId());
		return this.forum;
	}

	public void setForum(ForumInfo forum) {
		this.forum = forum;
	}	
}