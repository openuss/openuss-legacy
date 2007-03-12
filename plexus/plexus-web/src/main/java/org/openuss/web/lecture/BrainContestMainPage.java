package org.openuss.web.lecture; 

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContest;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;


import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$enrollment$braincontestmain", scope = Scope.REQUEST)
@View
public class BrainContestMainPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestMainPage.class);
	
	private BrainContestMainDataProvider data = new BrainContestMainDataProvider();
		
	public String remove(){
		logger.debug("braincontest removed");
		return Constants.SUCCESS;
	}
	
	private class BrainContestMainDataProvider extends AbstractPagedTable<BrainContest> {

		private DataPage<BrainContest> page; 
		
		@Override 
		public DataPage<BrainContest> getDataPage(int startRow, int pageSize) {		
			ArrayList<BrainContest> al = new ArrayList<BrainContest>();			
			BrainContest bc = BrainContest.Factory.newInstance();
			bc.setReleaseDate(new Date(System.currentTimeMillis()));
			bc.setTitle("Normative Entscheidungstheorie");
			
			al.add(bc); 
			page = new DataPage <BrainContest>(al.size(),0,al);
			return page;
		}
	}

	public BrainContestMainDataProvider getData() {
		return data;
	}

	public void setData(BrainContestMainDataProvider data) {
		this.data = data;
	}
	
}