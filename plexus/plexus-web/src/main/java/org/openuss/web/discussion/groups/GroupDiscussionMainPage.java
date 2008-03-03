package org.openuss.web.discussion.groups; 

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$discussion$groups$discussions", scope = Scope.REQUEST)
@View
public class GroupDiscussionMainPage extends AbstractGroupDiscussionPage{
	
	private static final Logger logger = Logger.getLogger(GroupDiscussionMainPage.class);
	
	private DiscussionDataProvider data = new DiscussionDataProvider();
	
	public boolean forumWatchState;
	
	public boolean forumReadOnly;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (groupInfo != null) {
			forumWatchState = discussionService.watchesForum(getForum());
			forumReadOnly = getForum().isReadOnly();
		}
		if (forum.isReadOnly()){
			addMessage(i18n("discussion_forum_readonly_true_simple"));
		}
	}	
	
	private class DiscussionDataProvider extends AbstractPagedTable<TopicInfo> {

		private static final long serialVersionUID = 5974442506189912052L;
		
		private DataPage<TopicInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<TopicInfo> getDataPage(int startRow, int pageSize) {		
			List<TopicInfo> al = discussionService.getTopics(getForum());		
			setSessionBean(Constants.FORUM_THREADLENGTH, 0);
			page = new DataPage<TopicInfo>(al.size(),0,al);
			sort(al);
			return page;
		}
	}
	
	public String newTopic(){
		if (getForum().isReadOnly()&&(!isAssistant())){
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}
		TopicInfo ti = new TopicInfo();
		setSessionBean(Constants.DISCUSSION_TOPIC, ti);
		PostInfo pi = new PostInfo();
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, pi);
		return Constants.FORUM_NEW;
	}

	public String removeTopic(){
		if (getForum().isReadOnly()&&(!isAssistant())){
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;			
		}		
		TopicInfo t = this.data.getRowData();
		setSessionBean(Constants.DISCUSSION_TOPIC, t);
		return Constants.FORUM_REMOVETHREAD;
	}
	
	public String search(){
		logger.debug("DiscussionMainPage - search");
		
		return Constants.FORUM_SEARCH;
	}

	public String changeWatchState(){
		ForumInfo forum = discussionService.getForum(groupInfo);
		if (discussionService.watchesForum(forum)){
			discussionService.removeForumWatch(forum);
		} else if(!discussionService.watchesForum(forum)){
			discussionService.addForumWatch(forum);
		} 
		return Constants.SUCCESS;
	}
	
	public String changeReadOnly(){
		discussionService.changeEditState(getForum());
		return Constants.SUCCESS;
	}
	
	public DiscussionDataProvider getData() {
		return data;
	}

	public void setData(DiscussionDataProvider data) {
		this.data = data;
	}

	public boolean isForumWatchState() {
		return forumWatchState;
	}

	public void setForumWatchState(boolean forumWatchState) {
		this.forumWatchState = forumWatchState;
	}

	public boolean isForumReadOnly() {
		return forumReadOnly;
	}

	public void setForumReadOnly(boolean forumReadOnly) {
		this.forumReadOnly = forumReadOnly;
	}
	
}