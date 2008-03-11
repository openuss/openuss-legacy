package org.openuss.web.discussion.groups; 

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.User;
import org.openuss.web.Constants;


@Bean(name = "views$secured$discussion$groups$discussionthread", scope = Scope.REQUEST)
@View
public class GroupDiscussionThreadPage extends AbstractGroupDiscussionPage{
	
	private DiscussionThreadDataProvider data = new DiscussionThreadDataProvider();
	
	@Property(value= "#{"+Constants.FORUM_THREADLENGTH+"}")
	public Integer length;
	
	@Property(value= "#{"+Constants.SHOW_USER_PROFILE+"}")
	public User profile;
	
	public boolean topicWatchState;
	
	public boolean topicReadOnly;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	 // NOPMD by devopenuss on 11.03.08 14:21
		super.prerender();
		if ( topic != null && topic.getId() != null) {
			topic = discussionService.getTopic(topic);
			setSessionBean(Constants.DISCUSSION_TOPIC, topic);
		}
		if (topic == null || topic.getId() == null) {
			redirect(Constants.FORUM_MAIN);
		} else { 
			discussionService.addHit(topic);
			topicWatchState = discussionService.watchesTopic(topic);
			topicReadOnly = topic.isReadOnly();
			if (forum.isReadOnly()){
				addMessage(i18n("discussion_forum_readonly_true_simple"));
			}else if (!forum.isReadOnly()){
				if (topicReadOnly){ // NOPMD by devopenuss on 11.03.08 14:21
					addMessage(i18n("discussion_topic_readonly_true_simple"));
				} 
			}
		}
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(topic.getTitle());
		crumb.setHint(topic.getTitle());
		breadcrumbs.addCrumb(crumb);
	}	
	
	private class DiscussionThreadDataProvider extends AbstractPagedTable<PostInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<PostInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<PostInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<PostInfo> al = discussionService.getPosts(topic); // NOPMD by devopenuss on 11.03.08 14:21
				setSessionBean(Constants.FORUM_THREADLENGTH, al.size());
				sort(al);
				page = new DataPage<PostInfo>(al.size(),0,al);
			}
			return page;
		}
	}
	
	public String addPost(){ // NOPMD by devopenuss on 11.03.08 14:21
		if ((topic.isReadOnly()||getForum().isReadOnly())&&(!isAssistant())){
			addError(i18n("discussion_readonly")); // NOPMD by devopenuss on 11.03.08 14:21
			return Constants.FAILURE;			
		}
		TopicInfo topic = discussionService.getTopic(this.topic);
		setSessionBean(Constants.DISCUSSION_TOPIC, topic);
		PostInfo post = new PostInfo();
		String title = "Re: "+this.topic.getTitle();
		if (title.length()>250){
			title = StringUtils.abbreviate(title, 250); // NOPMD by devopenuss on 11.03.08 14:21
		}
		post.setTitle(title);
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, post);
		return Constants.FORUM_NEW;
	}
	
	public String removePost(){
		if ((topic.isReadOnly()||getForum().isReadOnly())&&(!isAssistant())){
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}		
		PostInfo pi = this.data.getRowData(); // NOPMD by devopenuss on 11.03.08 14:21
		if (this.length==1){
			discussionService.deleteTopic(topic);
			addMessage(i18n("discussion_post_deleted", pi.getTitle()));
			return Constants.FORUM_MAIN;
		}
		discussionService.deletePost(pi);
		addMessage(i18n("discussion_post_deleted", pi.getTitle()));
		return Constants.SUCCESS;
	}
	
	public String editPost(){
		if ((topic.isReadOnly()||getForum().isReadOnly())&&(!isAssistant())){
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}		
		PostInfo pi = this.data.getRowData(); // NOPMD by devopenuss on 11.03.08 14:21
		pi = discussionService.getPost(pi);
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, pi);
		return Constants.FORUM_NEW;
	}
	
	public String quote(){ // NOPMD by devopenuss on 11.03.08 14:21
		if ((topic.isReadOnly()||getForum().isReadOnly())&&(!isAssistant())){
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}
		TopicInfo topic = discussionService.getTopic(this.topic);
		setSessionBean(Constants.DISCUSSION_TOPIC, topic);
		PostInfo quoteFrom = this.data.getRowData();
		PostInfo post = new PostInfo();
		String text = "<div style=\"border: 1px solid rgb(204, 204, 204); margin: 15px 20px; padding: 4px; background-color: rgb(238, 238, 238);\"><strong>" 
		+ quoteFrom.getSubmitter()+" - " + quoteFrom.getCreated()+ ":</strong> <br/>"+
		quoteFrom.getText()+"</div><br/><br/>";
		post.setText(text);
		post.setTitle("Re: "+quoteFrom.getTitle());
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, post);
		return Constants.FORUM_NEW;
	}
	
	public String changeWatchState(){
		if (discussionService.watchesTopic(topic)){
			discussionService.removeTopicWatch(topic);
		} else if(!discussionService.watchesTopic(topic)){
			discussionService.addTopicWatch(topic);
		} 
		return Constants.SUCCESS;
	}
	
	public String changeReadOnly(){
		discussionService.changeEditState(topic);
		return Constants.SUCCESS;
	}
	
	public String linkProfile(){
		profile.setId(this.data.getRowData().getSubmitterId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public DiscussionThreadDataProvider getData() {
		return data;
	}


	public void setData(DiscussionThreadDataProvider data) {
		this.data = data;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public boolean isTopicWatchState() {
		return topicWatchState;
	}

	public void setTopicWatchState(boolean topicWatchState) {
		this.topicWatchState = topicWatchState;
	}

	public boolean isTopicReadOnly() {
		return topicReadOnly;
	}

	public void setTopicReadOnly(boolean topicReadOnly) {
		this.topicReadOnly = topicReadOnly;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

	public boolean isForumReadOnly() {
		return getDiscussionService().getForum(groupInfo).isReadOnly();
	}
	
	public boolean isForumWatchState(){
		return getDiscussionService().watchesForum(getDiscussionService().getForum(groupInfo));
	}

}