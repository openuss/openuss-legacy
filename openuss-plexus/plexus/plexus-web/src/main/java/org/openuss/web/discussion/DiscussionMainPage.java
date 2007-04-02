package org.openuss.web.discussion; 

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$discussion$discussions", scope = Scope.REQUEST)
@View
public class DiscussionMainPage extends AbstractDiscussionPage{
	
	private DiscussionDataProvider data = new DiscussionDataProvider();
	
	private class DiscussionDataProvider extends AbstractPagedTable<TopicInfo> {

		private DataPage<TopicInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<TopicInfo> getDataPage(int startRow, int pageSize) {		
			List<TopicInfo> al = discussionService.getTopics(getForum());		
			
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
	
	public DiscussionDataProvider getData() {
		return data;
	}


	public void setData(DiscussionDataProvider data) {
		this.data = data;
	}
	
}