package org.openuss.web.discussion; 

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.PostInfo;
import org.openuss.documents.FileInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.repository.RepositoryFileImpl;
import org.openuss.security.UserImpl;
import org.openuss.web.Constants;


@Bean(name = "views$secured$discussion$discussionthread", scope = Scope.REQUEST)
@View
public class DiscussionThreadPage extends AbstractDiscussionPage{
	private static final Logger logger = Logger.getLogger(DiscussionThreadPage.class);
	
	private DiscussionThreadDataProvider data = new DiscussionThreadDataProvider();
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() {		
		if (!isPostBack()) {
			if ( topic != null && topic.getId() != null) {
				topic = discussionService.getTopic(topic);
				setSessionBean(Constants.DISCUSSION_TOPIC, topic);
			}
			if (topic == null || topic.getId() == null) {
				addError(i18n("braincontest_message_contest_not_found"));
				redirect(Constants.DISCUSSION_MAIN);
			}
		} 
	}	
	
	
	private class DiscussionThreadDataProvider extends AbstractPagedTable<PostInfo> {

		private DataPage<PostInfo> page; 
		
		@Override 
		public DataPage<PostInfo> getDataPage(int startRow, int pageSize) {		
			List<PostInfo> al = discussionService.getPosts(topic);
			page = new DataPage<PostInfo>(al.size(),0,al);
			return page;
		}
	}
	

	public DiscussionThreadDataProvider getData() {
		return data;
	}


	public void setData(DiscussionThreadDataProvider data) {
		this.data = data;
	}
	
}