package org.openuss.web.discussion; 

import java.util.List;

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

@Bean(name = "views$secured$discussion$discussions", scope = Scope.REQUEST)
@View
public class DiscussionMainPage extends AbstractDiscussionPage{
	
	private DiscussionDataProvider data = new DiscussionDataProvider();
	
	public boolean forumWatchState;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		forumWatchState = discussionService.watchesForum(getForum());
	}	
	
	private class DiscussionDataProvider extends AbstractPagedTable<TopicInfo> {

		private DataPage<TopicInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<TopicInfo> getDataPage(int startRow, int pageSize) {		
			List<TopicInfo> al = discussionService.getTopics(getForum());		
			setSessionBean(Constants.DISCUSSION_THREADLENGTH, 0);
			page = new DataPage<TopicInfo>(al.size(),0,al);
			return page;
		}
	}
	
	public String newTopic(){
		TopicInfo ti = new TopicInfo();
		setSessionBean(Constants.DISCUSSION_TOPIC, ti);
		PostInfo pi = new PostInfo();
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, pi);
		return Constants.DISCUSSION_NEW;
	}

	public String removeTopic(){
		TopicInfo t = this.data.getRowData();
		discussionService.deleteTopic(t);
		addMessage(i18n("discussion_topic_deleted", t.getTitle()));
		return Constants.SUCCESS;
	}

	public String changeWatchState(){
		ForumInfo forum = discussionService.getForum(enrollment.getId());
		if (discussionService.watchesForum(forum)){
			discussionService.removeForumWatch(forum);
		} else if(!discussionService.watchesForum(forum)){
			discussionService.addForumWatch(forum);
		} 
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
	
}