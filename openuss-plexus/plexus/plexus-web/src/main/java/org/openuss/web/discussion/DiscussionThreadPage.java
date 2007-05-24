package org.openuss.web.discussion; 

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;


@Bean(name = "views$secured$discussion$discussionthread", scope = Scope.REQUEST)
@View
public class DiscussionThreadPage extends AbstractDiscussionPage{
	
	private DiscussionThreadDataProvider data = new DiscussionThreadDataProvider();
	
	@Property(value= "#{"+Constants.DISCUSSION_THREADLENGTH+"}")
	public int length;
	
	public boolean topicWatchState;
	
	public boolean topicReadOnly;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if ( topic != null && topic.getId() != null) {
			topic = discussionService.getTopic(topic);
			setSessionBean(Constants.DISCUSSION_TOPIC, topic);
			discussionService.addHit(topic);
		}
		if (topic == null || topic.getId() == null) {
			redirect(Constants.DISCUSSION_MAIN);
		}
		topicWatchState = discussionService.watchesTopic(topic);
		topicReadOnly = topic.isReadOnly();
	}	
	
	
	private class DiscussionThreadDataProvider extends AbstractPagedTable<PostInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<PostInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<PostInfo> getDataPage(int startRow, int pageSize) {		
			List<PostInfo> al = discussionService.getPosts(topic);
			setSessionBean(Constants.DISCUSSION_THREADLENGTH, al.size());
			page = new DataPage<PostInfo>(al.size(),0,al);
			return page;
		}
	}
	
	public String addPost(){
		if (topic.isReadOnly()||getForum().isReadOnly()){
			addMessage(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}
		TopicInfo topic = discussionService.getTopic(this.topic);
		setSessionBean(Constants.DISCUSSION_TOPIC, topic);
		PostInfo post = new PostInfo();
		String title = "Re: "+this.topic.getTitle();
		if (title.length()>250) title = StringUtils.abbreviate(title, 250);
		post.setTitle(title);
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, post);
		return Constants.DISCUSSION_NEW;
	}
	
	public String removePost(){
		if (topic.isReadOnly()||getForum().isReadOnly()){
			addMessage(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}		
		PostInfo pi = this.data.getRowData();
		if (this.length==1){
			discussionService.deleteTopic(topic);
			addMessage(i18n("discussion_post_deleted", pi.getTitle()));
			return Constants.DISCUSSION_MAIN;
		}
		discussionService.deletePost(pi);
		addMessage(i18n("discussion_post_deleted", pi.getTitle()));
		return Constants.SUCCESS;
	}
	
	public String editPost(){
		if (topic.isReadOnly()||getForum().isReadOnly()){
			addMessage(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}		
		PostInfo pi = this.data.getRowData();
		pi = discussionService.getPost(pi);
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, pi);
		return Constants.DISCUSSION_NEW;
	}
	
	public String quote(){
		if (topic.isReadOnly()||getForum().isReadOnly()){
			addMessage(i18n("discussion_readonly"));
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
		return Constants.DISCUSSION_NEW;
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
	
	public DiscussionThreadDataProvider getData() {
		return data;
	}


	public void setData(DiscussionThreadDataProvider data) {
		this.data = data;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
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
	
}