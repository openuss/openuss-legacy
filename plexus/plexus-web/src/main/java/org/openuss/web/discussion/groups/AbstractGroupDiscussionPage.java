package org.openuss.web.discussion.groups;

import org.acegisecurity.Authentication;
import org.acegisecurity.acl.AclEntry;
import org.acegisecurity.acl.AclManager;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.groups.components.AbstractGroupPage;

public class AbstractGroupDiscussionPage extends AbstractGroupPage{

	@Property(value = "#{"+Constants.DISCUSSION_DISCUSSIONENTRY+"}")
	protected PostInfo postInfo;

	@Property(value = "#{"+Constants.DISCUSSION_TOPIC+"}")
	protected TopicInfo topic;
	
	@Property(value = "#{discussionService}")
	protected DiscussionService discussionService;

	@Property(value = "#{aclManager}")
	protected AclManager aclManager;
	
	public ForumInfo forum;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (groupInfo!=null&&groupInfo.getId()!=null){
			forum = getDiscussionService().getForum(groupInfo);
			setSessionBean(Constants.FORUM_FORUM, forum);
		}
		addDiscussionCrumb();
	}

	protected boolean isAssistant(){
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AclEntry[] acls = aclManager.getAcls(groupInfo, auth);
		Integer required = LectureAclEntry.ASSIST;
		if ((acls != null) && acls.length > 0) {
			for (AclEntry aclEntry : acls) {
				if (aclEntry instanceof BasicAclEntry) {
					BasicAclEntry processableAcl = (BasicAclEntry) aclEntry;
					if (processableAcl.isPermitted(required)) {
						return true;
					}
				}
			}
		}
		return false;

	}
	
	private void addDiscussionCrumb() {
		BreadCrumb discussionMain = new BreadCrumb();
		discussionMain.setName(i18n("course_command_discussion"));
		discussionMain.setHint(i18n("course_command_discussion"));
		discussionMain.setLink(PageLinks.DISCUSSION_MAIN);
		discussionMain.addParameter("group",groupInfo.getId());
		breadcrumbs.loadGroupCrumbs(groupInfo);
		breadcrumbs.addCrumb(discussionMain);
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
		this.forum = discussionService.getForum(groupInfo);
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

	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}
}