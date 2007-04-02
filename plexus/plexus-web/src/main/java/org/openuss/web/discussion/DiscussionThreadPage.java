package org.openuss.web.discussion; 

import java.util.List;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
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
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (!isPostBack()) {
			if ( topic != null && topic.getId() != null) {
				topic = discussionService.getTopic(topic);
				setSessionBean(Constants.DISCUSSION_TOPIC, topic);
				discussionService.addHit(topic);
			}
			if (topic == null || topic.getId() == null) {
				addError(i18n("braincontest_message_contest_not_found"));
				redirect(Constants.DISCUSSION_MAIN);
			}
		} 
	}	
	
	
	private class DiscussionThreadDataProvider extends AbstractPagedTable<PostInfo> {

		private DataPage<PostInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<PostInfo> getDataPage(int startRow, int pageSize) {		
			List<PostInfo> al = discussionService.getPosts(topic);
			page = new DataPage<PostInfo>(al.size(),0,al);
			return page;
		}
	}
	
	public String addPost(){
		TopicInfo topic = discussionService.getTopic(this.topic);
		setSessionBean(Constants.DISCUSSION_TOPIC, topic);
		PostInfo post = new PostInfo();
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, post);
		return Constants.DISCUSSION_NEW;
	}
	
	public String removePost(){
		PostInfo pi = this.data.getRowData();
		//TODO test if pi is the last element in topic, the return to main discussion view
		discussionService.deletePost(pi);
		addMessage(i18n("discussion_post_deleted", pi.getTitle()));
		return Constants.SUCCESS;
	}
	
	public String editPost(){
		PostInfo pi = this.data.getRowData();
		pi = discussionService.getPost(pi);
		setSessionBean(Constants.DISCUSSION_DISCUSSIONENTRY, pi);
		return Constants.DISCUSSION_NEW;
	}
	
	public DiscussionThreadDataProvider getData() {
		return data;
	}


	public void setData(DiscussionThreadDataProvider data) {
		this.data = data;
	}
	
}