package org.openuss.web.lecture; 

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.Forum;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.enrollment.AbstractEnrollmentPage;



@Bean(name = "views$secured$enrollment$discussiontable", scope = Scope.REQUEST)
@View
public class DiscussionTablePage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(DiscussionTablePage.class);
	
	private DiscussionTableDataProvider data = new DiscussionTableDataProvider();
		
	private class DiscussionTableDataProvider extends AbstractPagedTable<Forum> {

		private DataPage<Forum> page; 
		
		@Override 
		public DataPage<Forum> getDataPage(int startRow, int pageSize) {		
			ArrayList<Forum> al = new ArrayList<Forum>();			
			Forum f = Forum.Factory.newInstance();
			
			al.add(f); 
			page = new DataPage <Forum>(al.size(),0,al);
			return page;
		}
	}

	public DiscussionTableDataProvider getData() {
		return data;
	}

	public void setData(DiscussionTableDataProvider data) {
		this.data = data;
	}

	
}