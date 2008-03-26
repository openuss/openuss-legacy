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
public class DiscussionMainPage extends AbstractDiscussionPage {

	private DiscussionDataProvider data = new DiscussionDataProvider();

	public boolean forumWatchState;

	public boolean forumReadOnly;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (courseInfo!=null){
			forumWatchState = discussionService.watchesForum(getForum());
			forumReadOnly = getForum().isReadOnly();
		}
		if (forumReadOnly){
			addMessage(i18n("discussion_forum_readonly_true_simple"));
		}
	}

	private class DiscussionDataProvider extends AbstractPagedTable<TopicInfo> {

		private static final long serialVersionUID = 5974442506189912052L;

		private DataPage<TopicInfo> page;

		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<TopicInfo> getDataPage(int startRow, int pageSize) {		
			if (page == null) {
				List<TopicInfo> forums = discussionService.getTopics(getForum());		
				setBean(Constants.DISCUSSION_THREADLENGTH, 0);
				page = new DataPage<TopicInfo>(forums.size(),0,forums);
				sort(forums);
			}
			return page;
		}
	}

	public String newTopic() {
		if (getForum().isReadOnly() && (!isAssistant())) {
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;
		}
		TopicInfo topicInfo = new TopicInfo();
		setBean(Constants.DISCUSSION_TOPIC, topicInfo);
		PostInfo postInfo = new PostInfo();
		setBean(Constants.DISCUSSION_DISCUSSIONENTRY, postInfo);
		return Constants.DISCUSSION_NEW;
	}

	public String removeTopic() {
		if (getForum().isReadOnly() && (!isAssistant())) {
			addError(i18n("discussion_readonly"));
			return Constants.FAILURE;
		}
		TopicInfo topicInfo = this.data.getRowData();
		setBean(Constants.DISCUSSION_TOPIC, topicInfo);
		return Constants.DISCUSSION_REMOVETHREAD;
	}
	
	public String search(){
		return Constants.DISCUSSION_SEARCH;
	}

	public String changeWatchState() {
		ForumInfo forum = discussionService.getForum(courseInfo);
		if (discussionService.watchesForum(forum)) {
			discussionService.removeForumWatch(forum);
			addMessage(i18n("discussion_unsubscribe_success"));
		} else if(!discussionService.watchesForum(forum)){
			discussionService.addForumWatch(forum);
			addMessage(i18n("discussion_subscribe_success"));
		} 
		return Constants.SUCCESS;
	}

	public String changeReadOnly() {
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
