package org.openuss.web.discussion; 

import java.util.ArrayList;
import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.repository.RepositoryFileImpl;
import org.openuss.security.UserImpl;


@Bean(name = "views$secured$discussion$discussionthread", scope = Scope.REQUEST)
@View
public class DiscussionThreadPage extends AbstractDiscussionPage{
	private static final Logger logger = Logger.getLogger(DiscussionThreadPage.class);
	
	private DiscussionThreadDataProvider data = new DiscussionThreadDataProvider();
	
	private class DiscussionThreadDataProvider extends AbstractPagedTable<ThreadEntry> {

		private DataPage<ThreadEntry> page; 
		
		@Override 
		public DataPage<ThreadEntry> getDataPage(int startRow, int pageSize) {		
			ArrayList<ThreadEntry> al = new ArrayList<ThreadEntry>();
			UserImpl ui = new UserImpl();
			RepositoryFileImpl rf = new RepositoryFileImpl();			
			ThreadEntry te1 = new ThreadEntry(ui, "test", new Date(System.currentTimeMillis()),"testtitle", rf);
			al.add(te1); 
			page = new DataPage<ThreadEntry>(al.size(),0,al);
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