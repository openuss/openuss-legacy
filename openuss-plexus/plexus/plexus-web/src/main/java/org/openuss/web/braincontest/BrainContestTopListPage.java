package org.openuss.web.braincontest; 

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.AnswerInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$braincontest$braincontesttoplist", scope = Scope.REQUEST)
@View
public class BrainContestTopListPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestTopListPage.class);
	
	private BrainContestTopListDataProvider data = new BrainContestTopListDataProvider();

	private class BrainContestTopListDataProvider extends AbstractPagedTable<AnswerInfo> {

		private DataPage<AnswerInfo> page; 
		
		@Override 
		public DataPage<AnswerInfo> getDataPage(int startRow, int pageSize) {		
			ArrayList<AnswerInfo> al = new ArrayList<AnswerInfo>();			
			page = new DataPage<AnswerInfo>(al.size(),0,al);
			return page;
		}
	}

	public BrainContestTopListDataProvider getData() {
		return data;
	}

	public void setData(BrainContestTopListDataProvider data) {
		this.data = data;
	}
	
}